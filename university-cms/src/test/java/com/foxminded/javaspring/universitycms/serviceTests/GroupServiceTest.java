package com.foxminded.javaspring.universitycms.serviceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.foxminded.javaspring.universitycms.dao.GroupDao;
import com.foxminded.javaspring.universitycms.model.Group;
import com.foxminded.javaspring.universitycms.service.GroupService;

@ExtendWith(MockitoExtension.class)
class GroupServiceTest {
	
	@Mock
	private GroupDao groupDao;
	
	@InjectMocks
	private GroupService groupService;
	
	@Test
	void testSaveNewGroup() throws SQLException {
		Group group = new Group();
		group.setGroupName("TestGroup");
		Mockito.when(groupDao.save(any(Group.class))).thenReturn(group);
		Group savedGroup = groupService.saveNewGroup(group);
		assertEquals("TestGroup", savedGroup.getGroupName());
	}
	
	@Test
	void testFindGroupById() throws SQLException {
		Group savedGroup = new Group();
		savedGroup.setGroupID(1L);
		Mockito.when(groupDao.findById(savedGroup .getGroupID())).thenReturn(Optional.of(savedGroup ));
		Group foundGroup = groupService.findGroupById(savedGroup.getGroupID());
		assertEquals(1L, foundGroup.getGroupID());
		Group notFoundGroup = groupService.findGroupById(0L);
		assertEquals(null, notFoundGroup);
	}
	
	@Test
	void testFindAllGroups() throws SQLException {
		List<Group> allGroupsList = new ArrayList<>();
		Group group = new Group();
		group.setGroupName("TestGroup");
		allGroupsList.add(group);
		Mockito.when(groupDao.findAll()).thenReturn(allGroupsList);
		List<Group> foundGroups = groupService.findAllGroups();
		assertEquals("TestGroup", foundGroups.get(0).getGroupName());
	}
	
	@Test
	void testUpdateGroup() throws SQLException {
		Group dBGroup = new Group();
		dBGroup.setGroupID(1L);
		Group updatingGroup = new Group();
		updatingGroup.setGroupID(dBGroup.getGroupID());
		updatingGroup.setGroupName("UpdatingGroup");
		Group nonUpdatingGroup = new Group();
		nonUpdatingGroup.setGroupID(0L);
		Mockito.when(groupDao.findById(dBGroup.getGroupID())).thenReturn(Optional.of(dBGroup));
		Group updatedGroup = groupService.updateGroup(updatingGroup);
		assertEquals("UpdatingGroup", updatedGroup.getGroupName());
		Group nonUpdatedGroup = groupService.updateGroup(nonUpdatingGroup);
		assertEquals(null, nonUpdatedGroup);
	}
	
	@Test
	void testDeleteGroup() throws SQLException {
		groupService.deleteGroupById(anyLong());
		Mockito.verify(groupDao).deleteById(anyLong());
	}

}