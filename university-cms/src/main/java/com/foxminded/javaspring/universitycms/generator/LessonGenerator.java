package com.foxminded.javaspring.universitycms.generator;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxminded.javaspring.universitycms.dao.CourseDao;
import com.foxminded.javaspring.universitycms.dao.GroupDao;
import com.foxminded.javaspring.universitycms.dao.LessonDao;
import com.foxminded.javaspring.universitycms.dao.TeacherDao;
import com.foxminded.javaspring.universitycms.model.Course;
import com.foxminded.javaspring.universitycms.model.Group;
import com.foxminded.javaspring.universitycms.model.Lesson;
import com.foxminded.javaspring.universitycms.model.Teacher;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class LessonGenerator {

	private Random random;
	private LessonDao lessonDao;
	private CourseDao courseDao;
	private TeacherDao teacherDao;
	private GroupDao groupDao;

	private LocalDate lessonDate = LocalDate.of(2023, Month.MARCH, 04);
	private LocalTime lessonTime = LocalTime.of(9, 00);

	@Autowired
	public LessonGenerator(Random random, CourseDao courseDao, TeacherDao teacherDao, GroupDao groupDao,
			LessonDao lessonDao) {
		this.random = random;
		this.courseDao = courseDao;
		this.teacherDao = teacherDao;
		this.groupDao = groupDao;
		this.lessonDao = lessonDao;
	}

	public void generateNLessons(int countToGenerate) {
		IntStream.rangeClosed(1, countToGenerate).forEach(lessonID -> lessonDao.save(createLesson()));
		log.info(countToGenerate + " lessons generated");
	}

	private Lesson createLesson() {
		List<Course> courses = courseDao.findAll();
		List<Teacher> teachers = teacherDao.findAll();
		List<Group> groups = groupDao.findAll();
		Lesson lesson = new Lesson();
		lesson.setLessonDate(lessonDate);
		lesson.setLessonTime(lessonTime);
		lesson.setCourse(courses.get(random.nextInt(courses.size()-1)));
		lesson.setTeacher(teachers.get(random.nextInt(teachers.size()-1)));
		lesson.setGroup(groups.get(random.nextInt(groups.size()-1)));
		return lesson;
	}

}
