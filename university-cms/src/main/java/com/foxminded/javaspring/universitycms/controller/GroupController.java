package com.foxminded.javaspring.universitycms.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foxminded.javaspring.universitycms.model.Group;
import com.foxminded.javaspring.universitycms.service.GroupService;

@Controller
@RequestMapping("api/groups/")
public class GroupController {
	
	private GroupService groupService;

	public GroupController(GroupService groupService) {
		this.groupService = groupService;
	}
	
	@GetMapping
	public @ResponseBody List<Group> findAll(){
		return groupService.findAllGroups();
	}
	
    @GetMapping("/all")
    public String showAll(Model model) {
    	List<Group> groups = groupService.findAllGroups();
        model.addAttribute("groups", groups);
        return "groups";
    }

}
