package de.nordakademie.iaa.roommanagement.service;

import de.nordakademie.iaa.roommanagement.dao.CourseAlreadyExistsException;
import de.nordakademie.iaa.roommanagement.dao.CourseDAO;
import de.nordakademie.iaa.roommanagement.dao.CourseNotFoundException;
import de.nordakademie.iaa.roommanagement.model.Course;

import javax.inject.Inject;
import java.util.List;

/**
 * The course service that manages all business functionality.
 */
public class CourseServiceImpl implements CourseService {

    /**
     * The {@link CourseDAO} to use for db access.
     */
    private CourseDAO courseDAO;

    @Inject
    public void setCourseDAO(CourseDAO courseDAO) {
        this.courseDAO = courseDAO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Course saveCourse(Course course) {
        try {
            courseDAO.saveCourse(course);
        } catch (CourseAlreadyExistsException exception) {
            throw new ServiceException(exception.getMessage());
        }
        return course;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Course> listCourses() {
        return courseDAO.listCourses();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Course loadCourse(Long courseId) {
        try {
            return courseDAO.loadCourse(courseId);
        } catch (CourseNotFoundException exception) {
            throw new ServiceException(exception.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteCourse(Long courseId) {
        try {
            courseDAO.deleteCourse(courseId);
        } catch (CourseNotFoundException exception) {
            throw new ServiceException(exception.getMessage());
        }
    }

    @Override
    public Course findCourseByFieldOfStudyAndNumber(String fieldOfStudy, int number) {
        return courseDAO.findCourseByFieldOfStudyAndNumber(fieldOfStudy, number);
    }

}
