package com.foxminded.javaspring.universitycms.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxminded.javaspring.universitycms.dao.GroupDao;

@Service
@Transactional
public class GroupService {
	
	private GroupDao groupDao;

	@Autowired
	public GroupService(GroupDao groupDao) {
		this.groupDao = groupDao;
	}

}
