package com.foxminded.javaspring.universitycms.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxminded.javaspring.universitycms.dao.TeacherDao;

@Service
@Transactional
public class TeacherService {
	
	private TeacherDao teacherDao;

	@Autowired
	public TeacherService(TeacherDao teacherDao) {
		this.teacherDao = teacherDao;
	}

}
