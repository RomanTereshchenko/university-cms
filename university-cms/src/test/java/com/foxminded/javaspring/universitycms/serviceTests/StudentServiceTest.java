package com.foxminded.javaspring.universitycms.serviceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.foxminded.javaspring.universitycms.dao.StudentDao;
import com.foxminded.javaspring.universitycms.model.Person;
import com.foxminded.javaspring.universitycms.model.Student;
import com.foxminded.javaspring.universitycms.service.StudentService;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
	
	@Mock
	private StudentDao studentDao;
	
	@InjectMocks
	private StudentService studentService;
	
	@Test
	void testSaveNewStudent() throws SQLException {
		Student student = new Student();
		Person person = new Person();
		person.setFirstName("TestName");
		student.setPerson(person);
		Mockito.when(studentDao.save(any(Student.class))).thenReturn(student);
		Student savedStudent = studentService.saveNewStudent(student);
		assertEquals("TestName", savedStudent.getPerson().getFirstName());
	}
	
	@Test
	void testFindStudentById() throws SQLException {
		Student savedStudent = new Student();
		savedStudent.setStudentID(1L);
		Mockito.when(studentDao.findById(savedStudent .getStudentID())).thenReturn(Optional.of(savedStudent ));
		Student foundStudent = studentService.findStudentById(savedStudent.getStudentID());
		assertEquals(1L, foundStudent.getStudentID());
		Student notFoundStudent = studentService.findStudentById(0L);
		assertEquals(null, notFoundStudent);
	}
	
	@Test
	void testFindAllStudents() throws SQLException {
		List<Student> allStudentsList = new ArrayList<>();
		Student student = new Student();
		Person person = new Person();
		person.setFirstName("TestName");
		student.setPerson(person);
		allStudentsList.add(student);
		Mockito.when(studentDao.findAll()).thenReturn(allStudentsList);
		List<Student> foundStudents = studentService.findAllStudents();
		assertEquals("TestName", foundStudents.get(0).getPerson().getFirstName());
	}
	
	@Test
	void testUpdateStudent() throws SQLException {
		Student dBStudent = new Student();
		dBStudent.setStudentID(1L);
		Student updatingStudent = new Student();
		updatingStudent.setStudentID(dBStudent.getStudentID());
		Person person = new Person();
		person.setFirstName("TestName");
		updatingStudent.setPerson(person);
		Student nonUpdatingStudent = new Student();
		nonUpdatingStudent.setStudentID(0L);
		Mockito.when(studentDao.findById(dBStudent.getStudentID())).thenReturn(Optional.of(dBStudent));
		Student updatedStudent = studentService.updateStudent(updatingStudent);
		assertEquals("TestName", updatedStudent.getPerson().getFirstName());
		Student nonUpdatedStudent = studentService.updateStudent(nonUpdatingStudent);
		assertEquals(null, nonUpdatedStudent);
	}
	
	@Test
	void testDeleteStudent() throws SQLException {
		studentService.deleteStudentById(anyLong());
		Mockito.verify(studentDao).deleteById(anyLong());
	}

}
