package de.nordakademie.iaa.roommanagement.dao;

import de.nordakademie.iaa.roommanagement.model.Course;

import java.util.List;

public interface CourseDAO {
    /**
     * Creates a new course in the database.
     *
     * @param course The course to save
     * @throws CourseAlreadyExistsException if the course already exists in the database.
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
     * @throws CourseNotFoundException if no room could be found for the given id.
     */
    Course loadCourse(Long courseId);

    /**
     * Deletes the course with the given id.
     *
     * @param courseId The identifier.
     * @throws CourseNotFoundException if no course could be fount for the given id.
     */
    void deleteCourse(Long courseId);

    /**
     * Returns a course by it's natural id.
     *
     * @param fieldOfStudy The field of study of a course.
     * @param number       The number of a course.
     * @return the course matching the given criteria or <code>null</code> if none exists
     */
    Course findCourseByFieldOfStudyAndNumber(String fieldOfStudy, int number);
}
