package com.foxminded.javaspring.universitycms.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxminded.javaspring.universitycms.dao.StudentDao;

@Service
@Transactional
public class StudentService {
	
	private StudentDao studentDao;

	@Autowired
	public StudentService(StudentDao studentDao) {
		this.studentDao = studentDao;
	}

}
