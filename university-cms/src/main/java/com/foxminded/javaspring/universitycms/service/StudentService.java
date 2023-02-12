package com.foxminded.javaspring.universitycms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxminded.javaspring.universitycms.dao.StudentDao;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class StudentService {
	
	private StudentDao studentDao;

	@Autowired
	public StudentService(StudentDao studentDao) {
		this.studentDao = studentDao;
	}

}
