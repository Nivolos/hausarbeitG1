package de.nordakademie.iaa.roommanagement.service;

import de.nordakademie.iaa.roommanagement.model.Course;
import de.nordakademie.iaa.roommanagement.model.Lecture;

import java.util.Date;
import java.util.List;

public interface LectureService {

    /**
     * Saves the lecture in the database.
     *
     * @param lecture The lecture to save.
     * @throws ServiceException If saving fails, a ServiceException is thrown
     */
    Lecture saveLecture(Lecture lecture);

    /**
     * List all lectures currently stored in the database.
     *
     * @return a list of lecture entities. If no lecture was found an empty list
     * is returned.
     */
    List<Lecture> listLectures();

    /**
     * Returns the lecture identified by the given id.
     *
     * @param lectureId The identifier.
     * @return the found entity.
     * @throws ServiceException if no lecture was found using the given identifier.
     */
    Lecture loadLecture(Long lectureId);

    /**
     * Deletes the lecture with the given id.
     *
     * @param lectureId The identifier.
     * @throws ServiceException if no lecture could be fount for the given id.
     */
    void deleteLecture(Long lectureId);

    /**
     * Returs the list of lectures for a given course.
     *
     * @param courseId The identifier.
     */
    List<Lecture> findLecturesByCourse(Long courseId);

    /**
     * Returs the list of lectures for a given room.
     *
     * @param roomId The identifier.
     */
    List<Lecture> findLecturesByRoom(Long roomId);

    /**
     * Returns a lecture by it's natural id.
     *
     * @param course    The course of a lecture.
     * @param beginDate The begin date of a lecture.
     * @return the lecture matching the given criteria or <code>null</code> if none exists
     */
    Lecture findLectureByCourseAndBeginDate(Course course, Date beginDate);
}