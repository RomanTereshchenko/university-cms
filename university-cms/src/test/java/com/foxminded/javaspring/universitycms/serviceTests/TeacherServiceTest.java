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

import com.foxminded.javaspring.universitycms.dao.TeacherDao;
import com.foxminded.javaspring.universitycms.model.Person;
import com.foxminded.javaspring.universitycms.model.Teacher;
import com.foxminded.javaspring.universitycms.service.TeacherService;

@ExtendWith(MockitoExtension.class)
class TeacherServiceTest {
	
	@Mock
	private TeacherDao teacherDao;
	
	@InjectMocks
	private TeacherService teacherService;
	
	@Test
	void testSaveNewTeacher() throws SQLException {
		Teacher teacher = new Teacher();
		Person person = new Person();
		person.setFirstName("TestName");
		teacher.setPerson(person);
		Mockito.when(teacherDao.save(any(Teacher.class))).thenReturn(teacher);
		Teacher savedTeacher = teacherService.saveNewTeacher(teacher);
		assertEquals("TestName", savedTeacher.getPerson().getFirstName());
	}
	
	@Test
	void testFindTeacherById() throws SQLException {
		Teacher savedTeacher = new Teacher();
		savedTeacher.setTeacherID(1L);
		Mockito.when(teacherDao.findById(savedTeacher .getTeacherID())).thenReturn(Optional.of(savedTeacher ));
		Teacher foundTeacher = teacherService.findTeacherById(savedTeacher.getTeacherID());
		assertEquals(1L, foundTeacher.getTeacherID());
		Teacher notFoundTeacher = teacherService.findTeacherById(0L);
		assertEquals(null, notFoundTeacher);
	}
	
	@Test
	void testFindAllTeachers() throws SQLException {
		List<Teacher> allTeachersList = new ArrayList<>();
		Teacher teacher = new Teacher();
		Person person = new Person();
		person.setFirstName("TestName");
		teacher.setPerson(person);
		allTeachersList.add(teacher);
		Mockito.when(teacherDao.findAll()).thenReturn(allTeachersList);
		List<Teacher> foundTeachers = teacherService.findAllTeachers();
		assertEquals("TestName", foundTeachers.get(0).getPerson().getFirstName());
	}
	
	@Test
	void testUpdateTeacher() throws SQLException {
		Teacher dBTeacher = new Teacher();
		dBTeacher.setTeacherID(1L);
		Teacher updatingTeacher = new Teacher();
		updatingTeacher.setTeacherID(dBTeacher.getTeacherID());
		Person person = new Person();
		person.setFirstName("TestName");
		updatingTeacher.setPerson(person);
		Teacher nonUpdatingTeacher = new Teacher();
		nonUpdatingTeacher.setTeacherID(0L);
		Mockito.when(teacherDao.findById(dBTeacher.getTeacherID())).thenReturn(Optional.of(dBTeacher));
		Teacher updatedTeacher = teacherService.updateTeacher(updatingTeacher);
		assertEquals("TestName", updatedTeacher.getPerson().getFirstName());
		Teacher nonUpdatedTeacher = teacherService.updateTeacher(nonUpdatingTeacher);
		assertEquals(null, nonUpdatedTeacher);
	}
	
	@Test
	void testDeleteTeacher() throws SQLException {
		teacherService.deleteTeacherById(anyLong());
		Mockito.verify(teacherDao).deleteById(anyLong());
	}

}
