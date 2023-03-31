package com.foxminded.javaspring.universitycms.service;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.foxminded.javaspring.universitycms.dao.LessonDao;
import com.foxminded.javaspring.universitycms.model.Lesson;

import lombok.var;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class LessonService {
	
	private LessonDao lessonDao;

	@Autowired
	public LessonService(LessonDao lessonDao) {
		this.lessonDao = lessonDao;
	}

	@Secured({"ROLE_TEACHER", "ROLE_ADMIN"})
	public Lesson saveNewLesson(Lesson lesson) {
		var savingLesson = lessonDao.save(lesson);
		log.info("New lesson " + lesson.getCourse() + lesson.getLessonDate() + lesson.getLessonTime() + " saved");
		return savingLesson;
	}
	
	public Lesson findLessonById(Long lessonId) throws SQLException {
		var lesson = lessonDao.findById(lessonId);
		if (lesson.isPresent()) {
			log.info("Lesson with Id " + lessonId + " found");
			return lesson.get();
		}
		log.info("Lesson with Id " + lessonId + " not found");
		return null;
	}
	
	public List<Lesson> findAllLessons(){
		return lessonDao.findAll();
	}
	
	@RolesAllowed({"ROLE_TEACHER", "ROLE_ADMIN"})
	public Lesson updateLesson(Lesson lesson) {
		var updatingLesson = lessonDao.findById(lesson.getLessonID());
		if(updatingLesson.isPresent()) {
			lessonDao.save(lesson);
			log.info("Lesson is updated");
			return lesson;
		}
		log.info("This lesson does not exists in the database");
		return null;
	}
	
	@PreAuthorize("hasRole('ROLE_TEACHER') or hasRole('ROLE_ADMIN')")
	public void deleteLessonById(Long lessonId) {
		log.info("Lesson with Id" + lessonId + " deleted");
		lessonDao.deleteById(lessonId);	
	}
	
}
