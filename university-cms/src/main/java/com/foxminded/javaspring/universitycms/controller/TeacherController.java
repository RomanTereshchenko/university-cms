package com.foxminded.javaspring.universitycms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foxminded.javaspring.universitycms.model.Teacher;
import com.foxminded.javaspring.universitycms.service.TeacherService;

@Controller
@RequestMapping("/api/teachers")
public class TeacherController {
	
	private TeacherService teacherService;

	@Autowired
	public TeacherController(TeacherService teacherService) {
		this.teacherService = teacherService;
	}
	
	@GetMapping
	public @ResponseBody List<Teacher> findAll() {
		return teacherService.findAllTeachers();
	}
	
	@GetMapping("/all")
	public String showAll (Model model) {
		List<Teacher> teachers = teacherService.findAllTeachers();
		model.addAttribute("teachers", teachers);
		return "teachers";
	}

}
