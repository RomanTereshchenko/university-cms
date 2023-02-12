package com.foxminded.javaspring.universitycms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxminded.javaspring.universitycms.dao.LessonDao;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class LessonService {
	
	private LessonDao lessonDao;

	@Autowired
	public LessonService(LessonDao lessonDao) {
		this.lessonDao = lessonDao;
	}

}
