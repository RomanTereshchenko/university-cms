package com.foxminded.javaspring.universitycms.generator;

import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxminded.javaspring.universitycms.dao.CourseDao;
import com.foxminded.javaspring.universitycms.dao.PersonDao;
import com.foxminded.javaspring.universitycms.dao.TeacherDao;
import com.foxminded.javaspring.universitycms.model.Person;
import com.foxminded.javaspring.universitycms.model.Role;
import com.foxminded.javaspring.universitycms.model.Teacher;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class TeacherGenerator {

	private Random random;
	private PersonDao personDao;
	private TeacherDao teacherDao;
	private CourseDao courseDao;
	private CourseGenerator courseGenerator;

	@Autowired
	public TeacherGenerator(Random random, PersonDao personDao, TeacherDao teacherDao, CourseDao courseDao,
			CourseGenerator courseGenerator) {
		this.random = random;
		this.personDao = personDao;
		this.teacherDao = teacherDao;
		this.courseDao = courseDao;
		this.courseGenerator = courseGenerator;
	}

	public void generateTeachers() {
		List<Person> persons = personDao.findAll();
		for (Person person : persons) {
			if (person.getRole() == Role.TEACHER) {
				Teacher teacher = new Teacher();
				teacher.setPerson(person);
//				teacher.setCourses(courseGenerator.getNRandomCourses(3));
				teacherDao.save(teacher);
			}
		}
		log.info("Teachers generated");
	}

}
