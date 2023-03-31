package com.foxminded.javaspring.universitycms.service;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.foxminded.javaspring.universitycms.dao.StudentDao;
import com.foxminded.javaspring.universitycms.model.Student;

import lombok.var;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class StudentService {
	
	private StudentDao studentDao;

	@Autowired
	public StudentService(StudentDao studentDao) {
		this.studentDao = studentDao;
	}
	
	@Secured("ROLE_ADMIN")
	public Student saveNewStudent(Student student) {
		var savingStudent = studentDao.save(student);
		log.info("New student " + student.getPerson().getFirstName() + " " + student.getPerson().getLastName() + " saved");
		return savingStudent;
	}
	
	public Student findStudentById (Long studentId) {
		var student = studentDao.findById(studentId);
		if (student.isPresent()) {
			log.info("Student with Id " + studentId + " found");
			return student.get();
		}
		log.info("Student with Id " + studentId + " not found");
		return null;
	}
	
	public List<Student> findAllStudents(){
		return studentDao.findAll();
	}
	
	@RolesAllowed("ROLE_ADMIN")
	public Student updateStudent(Student student) {
		var updatingStudent = studentDao.findById(student.getStudentID());
		if (updatingStudent.isPresent()) {
			studentDao.save(student);
			log.info("Student " +  student.getPerson().getFirstName() + " " + student.getPerson().getLastName() + " updated");
			return student;
		}
		log.info("This student does not exist in the database");
		return null;
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void deleteStudentById(Long studentId) {
		log.info("Student with Id " + studentId + " deleted");
		studentDao.deleteById(studentId);
	}

}
