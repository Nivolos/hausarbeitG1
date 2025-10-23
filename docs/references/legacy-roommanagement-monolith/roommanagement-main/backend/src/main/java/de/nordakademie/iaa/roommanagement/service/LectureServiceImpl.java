package de.nordakademie.iaa.roommanagement.service;

import de.nordakademie.iaa.roommanagement.dao.CourseNotFoundException;
import de.nordakademie.iaa.roommanagement.dao.LectureDAO;
import de.nordakademie.iaa.roommanagement.dao.LectureNotFoundException;
import de.nordakademie.iaa.roommanagement.dao.RoomNotFoundException;
import de.nordakademie.iaa.roommanagement.model.Course;
import de.nordakademie.iaa.roommanagement.model.Lecture;
import de.nordakademie.iaa.roommanagement.model.Room;

import javax.inject.Inject;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * The lecture service that manages all business functionality.
 */
public class LectureServiceImpl implements LectureService {

    /**
     * The {@link LectureDAO} to use for db access.
     */
    private LectureDAO lectureDAO;

    /**
     * The {@link RoomService} to use.
     */
    private RoomService roomService;

    /**
     * The {@link CourseService} to use.
     */
    private CourseService courseService;

    @Inject
    public void setRoomService(RoomService roomService) {
        this.roomService = roomService;
    }

    @Inject
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    @Inject
    public void setLectureDAO(LectureDAO lectureDAO) {
        this.lectureDAO = lectureDAO;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Lecture saveLecture(Lecture lecture) {
        try {
            checkVacancyOfRoomInTimespan(lecture.getRoom(), lecture.getBeginDate(), lecture.getEndDate());
            return lectureDAO.saveLecture(lecture);
        } catch (RoomNotFoundException | CourseNotFoundException exception) {
            throw new ServiceException(exception.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Lecture> listLectures() {
        return lectureDAO.listLectures();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Lecture loadLecture(Long lectureId) {
        try {
            return lectureDAO.loadLecture(lectureId);
        } catch (LectureNotFoundException exception) {
            throw new ServiceException(exception.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteLecture(Long lectureId) {
        try {
            lectureDAO.deleteLecture(lectureId);
        } catch (LectureNotFoundException exception) {
            throw new ServiceException(exception.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Lecture> findLecturesByCourse(Long courseId) {
        return lectureDAO.findLecturesByCourse(courseId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Lecture> findLecturesByRoom(Long roomId) {
        return lectureDAO.findLecturesByRoom(roomId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Lecture findLectureByCourseAndBeginDate(Course course, Date beginDate) {
        return lectureDAO.findLectureByCourseAndBeginDate(course, beginDate);
    }

    private void checkVacancyOfRoomInTimespan(Room room, Date beginDate, Date endDate) {
        List<Lecture> lectures = lectureDAO.findLecturesInRoomInTimespan(room, beginDate, endDate);
        DateFormat format = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, Locale.GERMANY);
        if (!lectures.isEmpty()) {
            StringBuilder coursesBuilder = new StringBuilder();
            lectures.forEach(lecture -> coursesBuilder
                    .append(lecture.getCourse().getFieldOfStudy())
                    .append(lecture.getCourse().getNumber())
                    .append(" ")
                    .append(format.format(lecture.getBeginDate()))
                    .append(" - ")
                    .append(format.format(lecture.getEndDate()))
                    .append("; "));
            throw new ServiceException(String.format("Konflikt mit %d Kurs(en) in Raum %s%s : %s", lectures.size(), room.getBuilding(), room.getRoomNumber(), coursesBuilder));
        }
    }
}
