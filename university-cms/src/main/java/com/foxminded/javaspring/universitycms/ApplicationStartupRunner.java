package com.foxminded.javaspring.universitycms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.foxminded.javaspring.universitycms.generator.CourseGenerator;
import com.foxminded.javaspring.universitycms.generator.DBCleaner;
import com.foxminded.javaspring.universitycms.generator.GroupGenerator;
import com.foxminded.javaspring.universitycms.generator.PersonGenerator;
import com.foxminded.javaspring.universitycms.generator.StudentGenerator;

@Component
@ConditionalOnProperty(prefix = "application", name = "env", havingValue = "prod")
public class ApplicationStartupRunner implements CommandLineRunner {

	private DBCleaner dbCleaner;
	private CourseGenerator courseGenerator;
	private GroupGenerator groupGenerator;
	private PersonGenerator personGenerator;
	private StudentGenerator studentGenerator;

	private static final int NUMBER_OF_GROUPS = 5;
	private static final int NUMBER_OF_STUDENTS = 50;
	private static final int NUMBER_OF_TEACHERS = 10;

	@Autowired
	public ApplicationStartupRunner(DBCleaner dbCleaner, CourseGenerator courseGenerator, GroupGenerator groupGenerator,
			PersonGenerator personGenerator, StudentGenerator studentGenerator) {
		this.dbCleaner = dbCleaner;
		this.courseGenerator = courseGenerator;
		this.groupGenerator = groupGenerator;
		this.personGenerator = personGenerator;
		this.studentGenerator = studentGenerator;
	}

	@Override
	public void run(String... args) throws Exception {
		dbCleaner.cleanDB();
		courseGenerator.generateCourses();
		groupGenerator.generateNGroups(NUMBER_OF_GROUPS);
		personGenerator.generateNPersonsForStudent(NUMBER_OF_STUDENTS);
		personGenerator.generateNPersonsForTeacher(NUMBER_OF_TEACHERS);
		studentGenerator.generateStudents();
		
	}

}
