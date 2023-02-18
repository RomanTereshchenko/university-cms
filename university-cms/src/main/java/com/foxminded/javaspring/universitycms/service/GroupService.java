package com.foxminded.javaspring.universitycms.service;

import java.sql.SQLException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxminded.javaspring.universitycms.dao.GroupDao;
import com.foxminded.javaspring.universitycms.model.Group;

import lombok.var;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class GroupService {

	private GroupDao groupDao;

	@Autowired
	public GroupService(GroupDao groupDao) {
		this.groupDao = groupDao;
	}

	public Group saveNewGroup(Group group) {
		var savingGroup = groupDao.save(group);
		log.info("New group " + savingGroup.getGroupName() + " saved");
		return savingGroup;
	}

	public Group findGroupById(Long groupId) throws SQLException {
		var group = groupDao.findById(groupId);
		if(group.isPresent()) {
			log.info("Group with Id " + groupId + " is found");
			return group.get();
		}
		log.info("Group with Id " + groupId + " is not found");
		return null;
	}
	
	public List<Group> findAllGroups() {
		return groupDao.findAll();		
	}
	
	public Group updateGroup(Group group) throws SQLException {
		var updatingGroup = groupDao.findById(group.getGroupID());
		if (updatingGroup.isPresent()) {
			groupDao.save(group);
			log.info("Group " + group.getGroupName() + "is updated");
			return group;
		}
		log.info("This group does not exist in the database");
		return null;
	}
	
	public void deleteGroupById (Long groupId) {
		log.info("Group with Id " + groupId + "is deleted");
		groupDao.deleteById(groupId);
	}
}
