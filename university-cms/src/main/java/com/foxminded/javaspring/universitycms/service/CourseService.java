package com.foxminded.javaspring.universitycms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxminded.javaspring.universitycms.dao.CourseDao;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CourseService {
	
	private CourseDao courseDao;

	@Autowired
	public CourseService(CourseDao courseDao) {
		this.courseDao = courseDao;
	}
	
}
