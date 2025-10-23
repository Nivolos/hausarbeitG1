package de.nordakademie.iaa.roommanagement.dao;

import de.nordakademie.iaa.roommanagement.model.Course;
import de.nordakademie.iaa.roommanagement.model.Lecture;
import de.nordakademie.iaa.roommanagement.model.Room;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

/**
 * The lecture dao that manages data access.
 */
public class LectureDAOImpl implements LectureDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Lecture saveLecture(Lecture lecture) {
        if (lecture.getId() == null && existsLectureByCourseAndBeginDate(lecture.getCourse(), lecture.getBeginDate())) {
            throw new LectureAlreadyExistsException();
        }
        if (lecture.getId() != null) {
            // update the lecture
            return entityManager.merge(lecture);
        }
        // save the lecture
        entityManager.persist(lecture);
        return lecture;
    }

    @Override
    public List<Lecture> listLectures() {
        List<Lecture> lectures = entityManager.createQuery("from Lecture")
                .getResultList();
        return lectures;
    }

    @Override
    public Lecture loadLecture(Long lectureId) {
        Lecture lecture = (Lecture) entityManager.find(Lecture.class, lectureId);
        if (lecture == null) {
            throw new LectureNotFoundException();
        }
        return lecture;
    }

    @Override
    public void deleteLecture(Long lectureId) {
        entityManager.remove(loadLecture(lectureId));
    }

    @Override
    public List<Lecture> findLecturesByCourse(Long courseId) {
        String queryString = "SELECT lecture FROM Lecture AS lecture JOIN lecture.course AS course WHERE course.id = :courseId";
        TypedQuery<Lecture> query = entityManager.createQuery(queryString, Lecture.class);
        query.setParameter("courseId", courseId);
        return query.getResultList();
    }

    @Override
    public List<Lecture> findLecturesByRoom(Long roomId) {
        String queryString = "SELECT lecture FROM Lecture AS lecture JOIN lecture.room AS room WHERE room.id = :roomId";
        TypedQuery<Lecture> query = entityManager.createQuery(queryString, Lecture.class);
        query.setParameter("roomId", roomId);
        return query.getResultList();
    }

    @Override
    public List<Lecture> findLecturesInRoomInTimespan(Room room, Date beginDate, Date endDate) {
        TypedQuery<Lecture> query = entityManager.createQuery("from Lecture as lecture where lecture.room = :room and lecture.beginDate < :endDate and lecture.endDate > :beginDate order by lecture.beginDate", Lecture.class);
        query.setParameter("room", room);
        query.setParameter("beginDate", beginDate, TemporalType.TIMESTAMP);
        query.setParameter("endDate", endDate, TemporalType.TIMESTAMP);
        return query.getResultList();
    }

    @Override
    public Lecture findLectureByCourseAndBeginDate(Course course, Date beginDate) {
        TypedQuery<Lecture> query = entityManager.createQuery("from Lecture as lecture where lecture.course = :course and lecture.beginDate = :beginDate", Lecture.class);
        query.setParameter("course", course);
        query.setParameter("beginDate", beginDate, TemporalType.TIMESTAMP);
        List<Lecture> lectures = query.getResultList();
        if (lectures.isEmpty()) {
            throw new LectureNotFoundException();
        }
        return lectures.get(0);
    }

    private boolean existsLectureByCourseAndBeginDate(Course course, Date beginDate) {
        TypedQuery<Long> query = entityManager.createQuery("select count(lecture) from Lecture as lecture where lecture.course = :course and lecture.beginDate = :beginDate", Long.class);
        query.setParameter("course", course);
        query.setParameter("beginDate", beginDate, TemporalType.TIMESTAMP);
        return query.getSingleResult() > 0;
    }
}
