package com.foxminded.javaspring.universitycms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foxminded.javaspring.universitycms.model.Student;
import com.foxminded.javaspring.universitycms.service.StudentService;

@Controller
@RequestMapping("/api/students")
public class StudentController {
	
	private StudentService studentService;

	@Autowired
	public StudentController(StudentService studentService) {
		this.studentService = studentService;
	}
	
	@GetMapping
	public @ResponseBody List<Student> findAll(){
		return studentService.findAllStudents();
	}
	
	@GetMapping("/all")
	public String showAll (Model model) {
		List<Student> students = studentService.findAllStudents();
		model.addAttribute("students", students);
		return "students";
	}

}
