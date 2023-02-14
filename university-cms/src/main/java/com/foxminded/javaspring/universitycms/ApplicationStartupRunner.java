package com.foxminded.javaspring.universitycms;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.foxminded.javaspring.universitycms.dao.GroupDao;
import com.foxminded.javaspring.universitycms.dao.PersonDao;
import com.foxminded.javaspring.universitycms.dao.StudentDao;
import com.foxminded.javaspring.universitycms.dao.TeacherDao;
import com.foxminded.javaspring.universitycms.model.Group;
import com.foxminded.javaspring.universitycms.model.Person;
import com.foxminded.javaspring.universitycms.model.Role;
import com.foxminded.javaspring.universitycms.model.Student;
import com.foxminded.javaspring.universitycms.model.Teacher;

@Component
public class ApplicationStartupRunner implements CommandLineRunner {

    @Autowired
    TeacherDao teacherDao;
    @Autowired
    PersonDao personDao;
    @Autowired
    StudentDao studentDao;
    @Autowired
    GroupDao groupDao;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        Person personForTeacher = new Person();
        personForTeacher.setFirstName("test");
        personForTeacher.setLastName("teacher");
        personForTeacher.setLogin("teacher");
        personForTeacher.setPassword("teacher_password");
        personForTeacher.setRole(Role.TEACHER);
        Person savedForTeacher = personDao.save(personForTeacher);

        Teacher teacher = new Teacher();
        teacher.setPerson(savedForTeacher);
        teacherDao.save(teacher);

        List<Teacher> teachers = teacherDao.findAll();
        System.out.println(teachers);

        Person personForStudent = new Person();
        personForStudent.setFirstName("test");
        personForStudent.setLastName("student");
        personForStudent.setLogin("with_group");
        personForStudent.setPassword("student_password");
        personForStudent.setRole(Role.STUDENT);
        Person savedForStudent = personDao.save(personForStudent);

        Group group = new Group();
        group.setGroupName("GR-1");
        groupDao.save(group);

        Student student = new Student();
        student.setGroup(group);
        student.setPerson(savedForStudent);

        studentDao.save(student);
        List<Student> studentList = studentDao.findAll();
        System.out.println(studentList);
    }

	
}
