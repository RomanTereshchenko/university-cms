package com.foxminded.javaspring.universitycms.service;

import java.sql.SQLException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxminded.javaspring.universitycms.dao.CourseDao;
import com.foxminded.javaspring.universitycms.model.Course;

import lombok.var;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class CourseService {

	private CourseDao courseDao;

	@Autowired
	public CourseService(CourseDao courseDao) {
		this.courseDao = courseDao;
	}

	public Course saveNewCourse(Course course) throws SQLException {
		var savedNewCourse = courseDao.save(course);
		log.info("New course " + savedNewCourse.getCourseName() + " saved");
		return savedNewCourse;
	}

	public Course findCourseById(Long courseId) throws SQLException {
		var course = courseDao.findById(courseId);
		if (course.isPresent()) {
			log.info("Course with Id " + courseId + " is found");
			return course.get();
		}
		log.info("Course with Id " + courseId + " is not found");
		return null;
	}
	public List<Course> findAllCourses() {
		return courseDao.findAll();
	}

	public Course updateCourse(Course course) {
		var updatingCourse = courseDao.findById(course.getCourseID());
		if (updatingCourse.isPresent()) {
			courseDao.save(course);
			log.info("Course " + course.getCourseName() + " updated");
			return course;
		}
		log.info("This course does not exist in the database");
		return null;
	}

	public void deleteCourseById(Long courseId) throws SQLException {
		courseDao.deleteById(courseId);
		log.info("Course with ID " + courseId + " is deleted");
	}

}
