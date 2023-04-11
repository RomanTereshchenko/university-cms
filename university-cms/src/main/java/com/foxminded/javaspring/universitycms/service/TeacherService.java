package com.foxminded.javaspring.universitycms.service;

import java.util.List;
import java.util.Set;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.foxminded.javaspring.universitycms.dao.TeacherDao;
import com.foxminded.javaspring.universitycms.model.Course;
import com.foxminded.javaspring.universitycms.model.Teacher;

import lombok.var;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class TeacherService {

	private TeacherDao teacherDao;

	@Autowired
	public TeacherService(TeacherDao teacherDao) {
		this.teacherDao = teacherDao;
	}

	@Secured("ROLE_ADMIN")
	public Teacher saveNewTeacher(Teacher teacher) {
		var savingTeacher = teacherDao.save(teacher);
		log.info("New teacher " + teacher.getPerson().getFirstName() + " " + teacher.getPerson().getLastName()
				+ " saved");
		return savingTeacher;
	}

	public Teacher findTeacherById(Long teacherId) {
		var teacher = teacherDao.findById(teacherId);
		if (teacher.isPresent()) {
			log.info("Teacher with Id " + teacherId + " found");
			return teacher.get();
		}
		log.info("Teacher with Id " + teacherId + " not found");
		return null;
	}

	public List<Teacher> findAllTeachers() {
		return teacherDao.findAll();
	}

	@RolesAllowed("ROLE_ADMIN")
	public Teacher updateTeacher(Teacher teacher) {
		var updatingTeacher = teacherDao.findById(teacher.getTeacherID());
		if (updatingTeacher.isPresent()) {
			teacherDao.save(teacher);
			log.info("Teacher " + teacher.getPerson().getFirstName() + " " + teacher.getPerson().getLastName()
					+ " updated");
			return teacher;
		}
		log.info("This teacher does not exist in the database");
		return null;
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void deleteTeacherById(Long teacherId) {
		log.info("Teacher with Id " + teacherId + " deleted");
		teacherDao.deleteById(teacherId);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Teacher removeCourseFromTeacher(Course course, Teacher teacher) {
		var updatingTeacher = teacherDao.findById(teacher.getTeacherID());
		Set<Course> teacherCourses = updatingTeacher.get().getCourses();
		teacherCourses.remove(course);
		updatingTeacher.get().setCourses(teacherCourses);
		teacherDao.save(updatingTeacher.get());
		log.info("Course " + course.getCourseName() + "is removed from teacher " + teacher.getPerson().getFirstName()
				+ " " + teacher.getPerson().getLastName());
		return updatingTeacher.get();
	}
}
