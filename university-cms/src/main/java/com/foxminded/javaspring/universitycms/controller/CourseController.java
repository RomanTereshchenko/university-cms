package com.foxminded.javaspring.universitycms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foxminded.javaspring.universitycms.model.Course;
import com.foxminded.javaspring.universitycms.service.CourseService;

@Controller
@RequestMapping("/courses")
public class CourseController {

	private CourseService courseService;

	@Autowired
	public CourseController(CourseService courseService) {
		this.courseService = courseService;
	}

	@GetMapping
	public @ResponseBody List<Course> findAll() {
		return courseService.findAllCourses();
	}

	@GetMapping("/all")
	public String showAll(Model model) {
		List<Course> courses = courseService.findAllCourses();
		model.addAttribute("courses", courses);
		return "courses";
	}
}
