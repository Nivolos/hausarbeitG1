package de.nordakademie.iaa.roommanagement.service;

import de.nordakademie.iaa.roommanagement.model.Course;

import java.util.List;

public interface CourseService {

    /**
     * Saves the course in the database.
     *
     * @param course The Course to save
     * @throws ServiceException if the course already exists in the database.
     */
    Course saveCourse(Course course);

    /**
     * List all courses currently stored in the database.
     *
     * @return a list of course entities. If no course was found an empty list
     * is returned.
     */
    List<Course> listCourses();

    /**
     * Returns the course identified by the given id.
     *
     * @param courseId The identifier.
     * @return the found entity.
     * @throws ServiceException if no room could be found for the given id.
     */
    Course loadCourse(Long courseId);

    /**
     * Deletes the course with the given id.
     *
     * @param courseId The identifier.
     * @throws ServiceException if no course could be fount for the given id.
     */
    void deleteCourse(Long courseId);

    Course findCourseByFieldOfStudyAndNumber(String fieldOfStudy, int number);
}