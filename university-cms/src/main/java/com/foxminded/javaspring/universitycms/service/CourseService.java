package com.foxminded.javaspring.universitycms.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

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
	
	public Course saveNewCourse (Course course) throws SQLException {
		var savedNewCourse = courseDao.save(course);
		log.info("New course " + savedNewCourse.getCourseName() + " saved");
		return savedNewCourse;
	}
	
	public Optional<Course> findCourseById (Long Id) throws SQLException {
		var course = courseDao.findById(Id);
		if (course.isPresent()) {
			log.info("Course with Id " + Id + " is found");
			return course;
		}
		log.info("Course with Id " + Id + " is not found");
		return Optional.empty();
	}
	
	public List<Course> findAllCourses() throws SQLException {
		var courses = courseDao.findAll();
		if (courses.isEmpty()) {
			log.info("No courses found in the database");
		}
		return courses;
	}
	
	public Course updateCourse (Course course) {
		var updatingCourse = courseDao.findById(course.getCourseID());
		if (updatingCourse.isPresent()) {
			courseDao.save(course);
			log.info("Course " + course.getCourseName() + " updated");
			return course;
		}
		throw new NullPointerException("This course is not found in the database");
	}
	
	public void deleteCourseById (Long Id) throws SQLException {
		var deletingCourse = courseDao.findById(Id);
		if (deletingCourse.isPresent()) {
		courseDao.deleteById(Id);
		}
		throw new NullPointerException("This course is not found in the database");
	}
	
}
