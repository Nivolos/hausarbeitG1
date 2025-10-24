package de.nordakademie.iaa.librarysystem.dao;

import de.nordakademie.iaa.librarysystem.dao.exception.ModelNotFoundException;
import de.nordakademie.iaa.librarysystem.dao.exception.PersistException;
import de.nordakademie.iaa.librarysystem.model.Publication;
import org.hibernate.annotations.QueryHints;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;
/**
 * Die Klasse PublicationDAOImpl ist für die Datenbankverarbeitung der Publikaion zuständig.
 * Mit ihr können:
 *      - bereits angelegte Publikaion abgefragt
 *      - Publikaion angelegt
 *      - Publikaion bearbeiten
 *      - Publikaion gelöscht
 *      - alle Publikaion ausgegeben werden
 *      - eine Suche erstellt
 * werden.
 * @author Lena Bunge, Felix Groer
 * @version 1.0
 */
@Repository
public class PublicationDAOImpl implements PublicationDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Publication> load(Long id) throws ModelNotFoundException {
        Publication publication = null;
        try{
            if(id != null){
                publication = entityManager.find(Publication.class, id);
            }
        } catch (IllegalArgumentException e) {
            throw new ModelNotFoundException();
        }
        return ofNullable(publication);
    }

    @Override
    public void create(Publication publication) throws PersistException {
        try {
            entityManager.persist(publication);
        } catch(EntityExistsException
                | IllegalArgumentException
                | TransactionRequiredException
                | DataIntegrityViolationException e) {
            throw new PersistException();
        }
    }

    @Override
    public Publication update(Publication publication) throws PersistException {
        try{
            return entityManager.merge(publication);
        } catch (IllegalArgumentException | TransactionRequiredException e){
            throw new PersistException();
        }
    }

    @Override
    public void delete(Long id) throws ModelNotFoundException {
        Publication publication = load(id).orElseThrow(ModelNotFoundException::new);
        entityManager.remove(publication);
    }

    @Override
    public List<Publication> findAll(){
        return entityManager
                .createQuery("SELECT p FROM Publication p", Publication.class)
                .getResultList();
    }

    public List<Publication> search(Publication publication,
                                    List<Long> keywordIdList,
                                    List<Long> publicationTypeIdList,
                                    List<Long> authorIdList){
        String title = publication.getTitle();
        Date date = publication.getPublicationDate();
        String publisher = publication.getPublisher();
        String isbn = publication.getIsbn();
        Boolean keywordFlag = true;
        Boolean publicationTypeFlag = true;
        Boolean authorFlag = true;

        if(title == null || title.isEmpty()){
            title = "";
        }
        if(isbn == null || isbn.isEmpty()){
            isbn = "";
        }
        if(publisher == null || publisher.isEmpty()){
            publisher = "";
        }
        if(date == null){
            date = new Date(1);
        }
        if(authorIdList == null || authorIdList.parallelStream().allMatch(a -> a == -1)){
            authorFlag = false;
        }
        if(keywordIdList == null || keywordIdList.parallelStream().allMatch(k -> k == -1)){
            keywordFlag = false;
        }
        if(publicationTypeIdList == null || publicationTypeIdList.parallelStream().allMatch(pt -> pt == -1)){
            publicationTypeFlag = false;
        }

        return getSearchQuery(title, authorIdList, authorFlag , isbn, publisher, date, keywordIdList,
                publicationTypeIdList, keywordFlag, publicationTypeFlag);

    }

    private List<Publication> getSearchQuery(String title, List<Long> authorIdList, Boolean authorFlag,
                                             String isbn, String publisher, Date date,
                                             List<Long> keywordIdList, List<Long> publicationTypeIdList,
                                             Boolean keywordFlag, Boolean publicationTypeFlag){
        /* Match by publication attributes and keyword entity */
        List<Publication> publicationList = entityManager.createQuery("""
                                SELECT DISTINCT p FROM Publication p
                                LEFT JOIN FETCH p.keywords k 
                                WHERE UPPER(p.title) LIKE CONCAT('%',UPPER(:publicationTitle),'%')
                                AND p.isbn LIKE CONCAT('%',:publicationIsbn,'%')
                                AND UPPER(p.publisher) LIKE CONCAT('%',UPPER(:publicationPublisher),'%')
                                AND (p.publicationDate > :publicationDate) 
                                AND (:publicationFlag IS FALSE OR k.id IN :publicationKeywords)
                                """
                , Publication.class)
                .setParameter("publicationTitle", title)
                .setParameter("publicationIsbn", isbn)
                .setParameter("publicationPublisher", publisher)
                .setParameter("publicationDate", date)
                .setParameter("publicationKeywords", keywordIdList)
                .setParameter("publicationFlag", keywordFlag)
                .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
                .getResultList();

        /* Then, filter by publication types */
        publicationList = entityManager.createQuery("""
                        SELECT DISTINCT p FROM Publication p
                        LEFT JOIN FETCH p.publicationType pt
                        WHERE (:publicationTypeFlag IS FALSE OR pt.id IN :publicationTypes)
                        AND p in :publicationList
                        """, Publication.class)
                .setParameter("publicationTypes", publicationTypeIdList)
                .setParameter("publicationTypeFlag", publicationTypeFlag)
                .setParameter("publicationList", publicationList)
                .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
                .getResultList();

        /* Then, filter by authors */
        publicationList = entityManager.createQuery("""
                        SELECT DISTINCT p FROM Publication p
                        LEFT JOIN FETCH p.author a 
                        WHERE (:publicationAuthorFlag IS FALSE OR a.id IN :publicationAuthor)
                        AND p in :publicationList
                        """
                , Publication.class).setParameter("publicationAuthor", authorIdList)
                .setParameter("publicationAuthorFlag", authorFlag)
                .setParameter("publicationList", publicationList)
                .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
                .getResultList();

        return publicationList;

    }

    @Override
    public List<Publication> findAvailable() {
        return entityManager.createQuery("SELECT p FROM Publication p WHERE p.availableStock >= 1").getResultList();
    }

    @Override
    public List<Publication> findByAuthor(Long authorId) {
        TypedQuery<Publication> query = entityManager.createQuery("SELECT DISTINCT p FROM Publication p" +
                " LEFT JOIN FETCH p.author a" +
                " WHERE a.id = :authorId", Publication.class);
        List<Publication> result = query.setParameter("authorId", authorId)
        .getResultList();
        return result;
    }

    @Override
    public List<Publication> findByKeyword(Long keywordId) {
        List<Publication> result = new ArrayList<>();
        try{
            TypedQuery<Publication> query = entityManager.createQuery("SELECT DISTINCT p FROM Publication p" +
                    " LEFT JOIN FETCH p.keywords k" +
                    " WHERE k.id = :keywordId", Publication.class);
            result = query.setParameter("keywordId", keywordId)
                    .getResultList();
        } catch(IllegalStateException | PersistenceException e){
            // Ignore and return empty list
        }
        return result;
    }

    @Override
    public List<Publication> findByPublicationType(Long publicationTypeId) {
        TypedQuery<Publication> query = entityManager.createQuery("SELECT DISTINCT p FROM Publication p" +
                " LEFT JOIN FETCH p.publicationType pt" +
                " WHERE pt.id = :publicationTypeId", Publication.class);
        List<Publication> result = query.setParameter("publicationTypeId", publicationTypeId)
                .getResultList();
        return result;
    }

}