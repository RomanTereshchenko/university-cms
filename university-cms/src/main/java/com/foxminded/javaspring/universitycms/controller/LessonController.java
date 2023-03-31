package com.foxminded.javaspring.universitycms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foxminded.javaspring.universitycms.model.Lesson;
import com.foxminded.javaspring.universitycms.service.LessonService;

@Controller
@RequestMapping("/lessons")
public class LessonController {
	
    private LessonService lessonService;
    
    @Autowired
    public LessonController(LessonService lessonService) {
		this.lessonService = lessonService;
	}

	@GetMapping
    public @ResponseBody List<Lesson> findAll() {
        return lessonService.findAllLessons();
    }
	
    @GetMapping("/all")
    public String showAll(Model model) {
    	List<Lesson> lessons = lessonService.findAllLessons();
        model.addAttribute("lessons", lessons);
        return "lessons";
    }
}
