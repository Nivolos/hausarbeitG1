package de.nordakademie.iaa.roommanagement.dao;

import de.nordakademie.iaa.roommanagement.model.Course;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * The course dao that manages data access.
 */
public class CourseDAOImpl implements CourseDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Course saveCourse(Course course) {
        if (course.getId() == null && existsCourseByFieldOfStudyAndNumber(course.getFieldOfStudy(), course.getNumber())) {
            throw new CourseAlreadyExistsException();
        }
        if (course.getId() != null) {
            // update the course
            return entityManager.merge(course);
        }
        // save the course
        entityManager.persist(course);
        return course;
    }

    @Override
    public List<Course> listCourses() {
        return entityManager.createQuery("from Course", Course.class).getResultList();
    }

    @Override
    public Course loadCourse(Long courseId) {
        Course course = entityManager.find(Course.class, courseId);
        if (course == null) {
            throw new CourseNotFoundException();
        }
        return course;
    }

    @Override
    public void deleteCourse(Long courseId) {
        entityManager.remove(loadCourse(courseId));
    }

    @Override
    public Course findCourseByFieldOfStudyAndNumber(String fieldOfStudy, int number) {
        TypedQuery<Course> query = entityManager.createQuery("from Course as course where course.fieldOfStudy = :fieldOfStudy and course.number = :number", Course.class);
        query.setParameter("fieldOfStudy", fieldOfStudy);
        query.setParameter("number", number);
        List<Course> courses = query.getResultList();
        if (courses.isEmpty()) {
            throw new CourseNotFoundException();
        }
        return courses.get(0);
    }

    private boolean existsCourseByFieldOfStudyAndNumber(String fieldOfStudy, int number) {
        TypedQuery<Long> query = entityManager.createQuery("select count(course) from Course as course where course.fieldOfStudy = :fieldOfStudy and course.number = :number", Long.class);
        query.setParameter("fieldOfStudy", fieldOfStudy);
        query.setParameter("number", number);
        return query.getSingleResult() > 0;
    }

}
