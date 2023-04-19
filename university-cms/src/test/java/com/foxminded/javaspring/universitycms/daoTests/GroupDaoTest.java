package com.foxminded.javaspring.universitycms.daoTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.foxminded.javaspring.universitycms.dao.CourseDao;
import com.foxminded.javaspring.universitycms.dao.GroupDao;
import com.foxminded.javaspring.universitycms.dao.LessonDao;
import com.foxminded.javaspring.universitycms.dao.PersonDao;
import com.foxminded.javaspring.universitycms.dao.StudentDao;
import com.foxminded.javaspring.universitycms.dao.TeacherDao;
import com.foxminded.javaspring.universitycms.model.Group;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class GroupDaoTest {
	
	private CourseDao courseDao;
	private GroupDao groupDao;
	private LessonDao lessonDao;
	private PersonDao personDao;
	private StudentDao studentDao;
	private TeacherDao teacherDao;

	@Autowired
	public GroupDaoTest(CourseDao courseDao, GroupDao groupDao, LessonDao lessonDao, PersonDao personDao,
			StudentDao studentDao, TeacherDao teacherDao) {
		this.courseDao = courseDao;
		this.groupDao = groupDao;
		this.lessonDao = lessonDao;
		this.personDao = personDao;
		this.studentDao = studentDao;
		this.teacherDao = teacherDao;
	}

	@BeforeEach
	public void cleanDB() {
		lessonDao.deleteAll();
		studentDao.deleteAll();
		teacherDao.deleteAll();
		personDao.deleteAll();
		groupDao.deleteAll();
		courseDao.deleteAll();
		log.info("Database cleaned");
	}
	
	@Test
	@Transactional
	void testSaveAndFindAll() {
		Group group = new Group();
		group.setGroupName("TestGroup");
		groupDao.save(group);
		Group savedGroup = groupDao.findAll().get(0);
		assertNotNull(savedGroup);
		assertEquals("TestGroup", savedGroup.getGroupName());
	}
	
	@Test
	@Transactional
	void testFindById() {
		Group group = new Group();
		group.setGroupName("TestGroup");
		groupDao.save(group);
		Group savedGroup = groupDao.findAll().get(0);
		Long savedGroupId = savedGroup.getGroupID();
		Group foundGroup = groupDao.findById(savedGroupId).get();
		assertNotNull(foundGroup);
		assertEquals(savedGroupId, foundGroup.getGroupID());
	}

	@Test
	@Transactional
	void testDeleteById() {
		Group group = new Group();
		group.setGroupName("TestGroup");
		groupDao.save(group);
		Group savedGroup = groupDao.findAll().get(0);
		Long savedGroupId = savedGroup.getGroupID();
		Group foundGroup = groupDao.findById(savedGroupId).get();
		assertNotNull(foundGroup);
		assertEquals(savedGroupId, foundGroup.getGroupID());
		groupDao.deleteById(savedGroupId);
		Optional<Group> deletedGroup = groupDao.findById(savedGroupId);
		assertEquals((Object)deletedGroup, Optional.empty());
	}
}
