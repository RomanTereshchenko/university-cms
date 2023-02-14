package com.foxminded.javaspring.universitycms.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxminded.javaspring.universitycms.dao.LessonDao;

@Service
@Transactional
public class LessonService {
	
	private LessonDao lessonDao;

	@Autowired
	public LessonService(LessonDao lessonDao) {
		this.lessonDao = lessonDao;
	}

}
