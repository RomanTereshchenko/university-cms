package com.foxminded.javaspring.universitycms.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foxminded.javaspring.universitycms.dao.GroupDao;
import com.foxminded.javaspring.universitycms.model.Group;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class GroupGenerator {

	private Random random;
	private GroupDao groupDao;
	private CourseGenerator courseGenerator;

	@Autowired
	public GroupGenerator(Random random, GroupDao groupDao, CourseGenerator courseGenerator) {
		this.random = random;
		this.groupDao = groupDao;
		this.courseGenerator = courseGenerator;
	}

	@Transactional
	public List<Group> generateNGroups(int countToGenerate) {
		List<Group> groupsLocal = new ArrayList<>();
		for (int i = 0; i < countToGenerate; i++) {
			Group group = new Group();
			group.setGroupName(generateGroupName());
			group.setCourses(courseGenerator.getNRandomCourses(3));
			groupDao.save(group);
			groupsLocal.add(group);
		}
		log.info(countToGenerate + " groups generated");
		return groupsLocal;
	}


	private String generateGroupName() {
		return (new StringBuilder().append(generateRandomChar()).append(generateRandomChar()) + "-"
				+ generateRandomInt() + generateRandomInt()).toString();
	}

	private int generateRandomInt() {
		return random.nextInt(10);
	}

	private char generateRandomChar() {
		return (char) (random.nextInt(26) + 'a');
	}

}
