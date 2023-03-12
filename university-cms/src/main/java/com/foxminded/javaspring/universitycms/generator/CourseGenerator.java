package com.foxminded.javaspring.universitycms.generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxminded.javaspring.universitycms.dao.CourseDao;
import com.foxminded.javaspring.universitycms.model.Course;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class CourseGenerator {

	private Random random;
	private CourseDao courseDao;

	@Autowired
	public CourseGenerator(CourseDao courseDao, Random random) {
		this.courseDao = courseDao;
		this.random = random;
	}

	public final List<String> courseNames = Arrays.asList("Mathematics", "Science", "Health", "Handwriting", "Art",
			"Music", "Leadership", "Speech", "English", "Algebra");

	public List<Course> generateCourses() {
		List<Course> coursesLocal = new ArrayList<>();
		for (int i = 1; i <= courseNames.size(); i++) {
			Course generatedCourse = new Course();
			generatedCourse.setCourseName(courseNames.get(i - 1));
			courseDao.save(generatedCourse);
			coursesLocal.add(generatedCourse);
		}
		log.info("Courses generated");
		return coursesLocal;
	}

	public Set<Course> getNRandomCourses(int numberOfCourses) {
		List<Course> courses = courseDao.findAll();
		Set<Course> nRandomCourses = new HashSet<>();
		while (nRandomCourses.size() < numberOfCourses) {
			nRandomCourses.add(courses.get(random.nextInt(courses.size())));		
		}
		return nRandomCourses;
	}

}
