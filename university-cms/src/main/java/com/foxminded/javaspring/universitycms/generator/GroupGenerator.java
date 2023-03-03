package com.foxminded.javaspring.universitycms.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foxminded.javaspring.universitycms.dao.CourseDao;
import com.foxminded.javaspring.universitycms.dao.GroupDao;
import com.foxminded.javaspring.universitycms.model.Course;
import com.foxminded.javaspring.universitycms.model.Group;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class GroupGenerator {

	private Random random;
	private GroupDao groupDao;
	private CourseDao courseDao;

	@Autowired
	public GroupGenerator(Random random, GroupDao groupDao, CourseDao courseDao) {
		this.random = random;
		this.groupDao = groupDao;
		this.courseDao = courseDao;
	}

	@Transactional
	public List<Group> generateNGroups(int countToGenerate) {
		List<Group> groupsLocal = new ArrayList<>();
		for (int i = 0; i < countToGenerate; i++) {
			Group group = new Group();
			group.setGroupName(generateGroupName());
//			group.setCourses(getNRandomCourses(3));
			groupDao.save(group);
			groupsLocal.add(group);
		}
		log.info(countToGenerate + " groups generated");
		return groupsLocal;
	}

	private Set<Course> getNRandomCourses(int numberOfCoursesInGroup) {
		List<Course> courses = courseDao.findAll();

		return IntStream.rangeClosed(1, numberOfCoursesInGroup)
				.mapToObj(courseInGroupID -> courses.get(random.nextInt(courses.size()))).collect(Collectors.toSet());
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
