package com.foxminded.javaspring.universitycms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.foxminded.javaspring.universitycms.generator.CourseGenerator;
import com.foxminded.javaspring.universitycms.generator.DBCleaner;
import com.foxminded.javaspring.universitycms.generator.GroupGenerator;
import com.foxminded.javaspring.universitycms.generator.LessonGenerator;
import com.foxminded.javaspring.universitycms.generator.PersonGenerator;
import com.foxminded.javaspring.universitycms.generator.StudentGenerator;
import com.foxminded.javaspring.universitycms.generator.TeacherGenerator;

@Component
@ConditionalOnProperty(prefix = "application", name = "env", havingValue = "prod")
public class ApplicationStartupRunner implements CommandLineRunner {

	private DBCleaner dbCleaner;
	private CourseGenerator courseGenerator;
	private GroupGenerator groupGenerator;
	private PersonGenerator personGenerator;
	private StudentGenerator studentGenerator;
	private TeacherGenerator teacherGenerator;
	private LessonGenerator lessonGenerator;

	private static final int NUMBER_OF_GROUPS = 5;
	private static final int NUMBER_OF_STUDENTS = 20;
	private static final int NUMBER_OF_TEACHERS = 5;
	private static final int NUMBER_OF_LESSONS = 5;

	@Autowired
	public ApplicationStartupRunner(DBCleaner dbCleaner, CourseGenerator courseGenerator, GroupGenerator groupGenerator,
			PersonGenerator personGenerator, StudentGenerator studentGenerator, TeacherGenerator teacherGenerator,
			LessonGenerator lessonGenerator) {
		this.dbCleaner = dbCleaner;
		this.courseGenerator = courseGenerator;
		this.groupGenerator = groupGenerator;
		this.personGenerator = personGenerator;
		this.studentGenerator = studentGenerator;
		this.teacherGenerator = teacherGenerator;
		this.lessonGenerator = lessonGenerator;
	}

	@Override
	public void run(String... args) throws Exception {
		dbCleaner.cleanDB();
		courseGenerator.generateCourses();
		groupGenerator.generateNGroups(NUMBER_OF_GROUPS);
		personGenerator.generateNPersonsForStudent(NUMBER_OF_STUDENTS);
		personGenerator.generateNPersonsForTeacher(NUMBER_OF_TEACHERS);
		personGenerator.generatePersonForAdmin();
		studentGenerator.generateStudents();
		studentGenerator.assignGroupsToStudents();
		teacherGenerator.generateTeachers();
		lessonGenerator.generateNLessons(NUMBER_OF_LESSONS);
	}

}
