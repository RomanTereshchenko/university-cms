package com.foxminded.javaspring.universitycms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxminded.javaspring.universitycms.dao.GroupDao;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class GroupService {
	
	private GroupDao groupDao;

	@Autowired
	public GroupService(GroupDao groupDao) {
		this.groupDao = groupDao;
	}

}
