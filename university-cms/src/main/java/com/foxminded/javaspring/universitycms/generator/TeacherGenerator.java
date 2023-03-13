package com.foxminded.javaspring.universitycms.generator;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	private PersonDao personDao;
	private TeacherDao teacherDao;
	private CourseGenerator courseGenerator;

	@Autowired
	public TeacherGenerator(PersonDao personDao, TeacherDao teacherDao, CourseGenerator courseGenerator) {
		this.personDao = personDao;
		this.teacherDao = teacherDao;
		this.courseGenerator = courseGenerator;
	}

	public void generateTeachers() {
		List<Person> persons = personDao.findAll();
		for (Person person : persons) {
			if (person.getRole() == Role.TEACHER) {
				Teacher teacher = new Teacher();
				teacher.setPerson(person);
				teacher.setCourses(courseGenerator.getNRandomCourses(3));
				teacherDao.save(teacher);
			}
		}
		log.info("Teachers generated");
	}
}
