package com.foxminded.javaspring.universitycms.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxminded.javaspring.universitycms.dao.TeacherDao;
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
	
	public Teacher saveNewTeacher(Teacher teacher) {
		var savingTeacher = teacherDao.save(teacher);
		log.info("New teacher " + teacher.getPerson().getFirstName() + " " + teacher.getPerson().getLastName() + " saved");
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
	
	public List<Teacher> findAllTeachers(){
		return teacherDao.findAll();
	}

	public Teacher updateTeacher(Teacher teacher) {
		var updatingTeacher = teacherDao.findById(teacher.getTeacherID());
		if (updatingTeacher.isPresent()) {
			teacherDao.save(teacher);
			log.info("Teacher " + teacher.getPerson().getFirstName() + " " + teacher.getPerson().getLastName() + " updated");
			return teacher;
		}
		log.info("This teacher does not exist in the database");
		return null;
	}
	
	public void deleteTeacherById(Long teacherId) {
		log.info("Teacher with Id " + teacherId + " deleted");
		teacherDao.deleteById(teacherId);
	}
}
