package de.nordakademie.iaa.roommanagement.dao;

import de.nordakademie.iaa.roommanagement.model.Course;
import de.nordakademie.iaa.roommanagement.model.Lecture;
import de.nordakademie.iaa.roommanagement.model.Room;

import java.util.Date;
import java.util.List;

public interface LectureDAO {
    /**
     * Creates and stores a new lecture entity.
     *
     * @param lecture The lecture to save
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
     * @throws LectureNotFoundException if no lecture was found using the given identifier.
     */
    Lecture loadLecture(Long lectureId);

    /**
     * Deletes the lecture with the given id.
     *
     * @param lectureId The identifier.
     * @throws LectureNotFoundException if no lecture could be fount for the given id.
     */
    void deleteLecture(Long lectureId);

    /**
     * Returns all lectures for the given course id.
     *
     * @param courseId The identifier.
     * @return the list of lecture entities for the given course
     */
    List<Lecture> findLecturesByCourse(Long courseId);

    /**
     * Returns all lectures for the given room id.
     *
     * @param roomId The identifier.
     * @return the list of lecture entities for the given room
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

    /**
     * Returns a list of lectures that occupy a room in a given timespan.
     *
     * @param room      The room to check.
     * @param beginDate The begin of the timespan to check.
     * @param endDate   The end of the timespan to check.
     * @return the list of lectures in the room in the given timespan
     */
    List<Lecture> findLecturesInRoomInTimespan(Room room, Date beginDate, Date endDate);
}
