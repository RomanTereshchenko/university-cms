package com.foxminded.javaspring.universitycms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxminded.javaspring.universitycms.dao.TeacherDao;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class TeacherService {
	
	private TeacherDao teacherDao;

	@Autowired
	public TeacherService(TeacherDao teacherDao) {
		this.teacherDao = teacherDao;
	}

}
