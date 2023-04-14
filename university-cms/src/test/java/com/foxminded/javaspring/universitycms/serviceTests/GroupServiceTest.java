package com.foxminded.javaspring.universitycms.serviceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import com.foxminded.javaspring.universitycms.dao.GroupDao;
import com.foxminded.javaspring.universitycms.model.Group;
import com.foxminded.javaspring.universitycms.service.GroupService;

@SpringBootTest
class GroupServiceTest {

	private GroupDao groupDao;
	private GroupService groupService;

	@Autowired
	public GroupServiceTest(GroupDao groupDao, GroupService groupService) {
		this.groupDao = groupDao;
		this.groupService = groupService;
	}

	@Test
	@Transactional
	@WithMockUser(username = "test", roles = { "ADMIN" })
	void testUpdateGroup() throws SQLException {
		Group testGroup = new Group();
		testGroup.setGroupName("tt-11");
		testGroup.setCourses(new HashSet<>());
		groupDao.save(testGroup);
		testGroup.setGroupName("tt-22");
		groupService.updateGroup(testGroup);
		List<Group> groups = groupDao.findAll();
		Group savedGroup = groups.get(0);
		assertEquals("tt-22", savedGroup.getGroupName());
		Group testGroup2 = new Group(testGroup.getGroupID() + 1, "TestGroup2", new HashSet<>(), new HashSet<>(), new HashSet<>());
		assertEquals(null, groupService.updateGroup(testGroup2));
	}

}
