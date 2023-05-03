package com.foxminded.javaspring.universitycms.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.foxminded.javaspring.universitycms.model.Course;
import com.foxminded.javaspring.universitycms.model.Group;
import com.foxminded.javaspring.universitycms.model.Person;
import com.foxminded.javaspring.universitycms.model.Teacher;
import com.foxminded.javaspring.universitycms.service.CourseService;
import com.foxminded.javaspring.universitycms.service.GroupService;
import com.foxminded.javaspring.universitycms.service.PersonService;
import com.foxminded.javaspring.universitycms.service.TeacherService;

@Controller
@RequestMapping("/courses")
public class CourseController {

	private CourseService courseService;
	private PersonService personService;
	private GroupService groupService;
	private TeacherService teacherService;

	@Autowired
	public CourseController(CourseService courseService, PersonService personService, GroupService groupService,
			TeacherService teacherService) {
		this.courseService = courseService;
		this.personService = personService;
		this.groupService = groupService;
		this.teacherService = teacherService;
	}

	@GetMapping
	public String courses(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentUserName = authentication.getName();
		Person userPerson = personService.findPersonByLogin(currentUserName);
		model.addAttribute("userPerson", userPerson);
		return "courses/courses";
	}

	@GetMapping("/all")
	public String showAll(Model model) {
		List<Course> courses = courseService.findAllCourses();
		model.addAttribute("courses", courses);
		return "courses/coursesAll";
	}

	@GetMapping("/create")
	public String coursesCreate(Model model) {
		List<Course> courses = courseService.findAllCourses();
		model.addAttribute("courses", courses);
		return "courses/coursesCreate";
	}

	@PostMapping("/createCourse")
	public String createNewCourse(@RequestParam Map<String, String> courseParams, Model model) throws SQLException {
		Course course = new Course();
		course.setCourseName(courseParams.get("name"));
		course.setCourseDescription(courseParams.get("description"));
		courseService.saveNewCourse(course);
		List<Course> courses = courseService.findAllCourses();
		model.addAttribute("courses", courses);
		return "courses/coursesCreate";
	}

	@GetMapping("/update")
	public String coursesUpdate(Model model) {
		List<Course> courses = courseService.findAllCourses();
		model.addAttribute("courses", courses);
		return "courses/coursesUpdate";
	}

	@PostMapping("/updateCourse")
	public String updateCourse(@RequestParam Map<String, String> courseParams, Model model) throws SQLException {		
		Course course = courseService.findCourseById(Long.parseLong(courseParams.get("courseId")));
		Course updatedCourse = new Course();
		updatedCourse.setCourseID(course.getCourseID());
		updatedCourse.setCourseName(courseParams.get("name"));
		updatedCourse.setCourseDescription(courseParams.get("description"));
		updatedCourse.setGroups(course.getGroups());
		updatedCourse.setTeachers(course.getTeachers());
		courseService.updateCourse(updatedCourse);
		List<Course> courses = courseService.findAllCourses();
		model.addAttribute("courses", courses);
		return "courses/coursesUpdate";
	}

	@GetMapping("/delete")
	public String coursesDelete(Model model) {
		List<Course> courses = courseService.findAllCourses();
		model.addAttribute("courses", courses);
		return "courses/coursesDelete";
	}

	@PostMapping("/deleteCourse")
	public String deleteCourse(@RequestParam Long courseId, Model model) throws SQLException {
		courseService.deleteCourseById(courseId);
		List<Course> courses = courseService.findAllCourses();
		model.addAttribute("courses", courses);
		return "courses/coursesDelete";
	}

	@GetMapping("/assignGroup")
	public String coursesAssignGroup(Model model) {
		List<Course> courses = courseService.findAllCourses();
		List<Group> groups = groupService.findAllGroups();
		model.addAttribute("courses", courses);
		model.addAttribute("groups", groups);
		return "courses/coursesAssignGroup";
	}

	@PostMapping("/assignGroupToCourse")
	public String coursesAssignGroupToCourse(@RequestParam Long courseId, @RequestParam Long assignedGroupId, Model model)
			throws SQLException {
		Set<Group> courseGroups = courseService.findCourseById(courseId).getGroups();
		Group assignedGroup = groupService.findGroupById(assignedGroupId);
		courseGroups.add(assignedGroup);
		List<Course> courses = courseService.findAllCourses();
		List<Group> groups = groupService.findAllGroups();
		model.addAttribute("courses", courses);
		model.addAttribute("groups", groups);
		return "courses/coursesAssignGroup";
	}
	
	@PostMapping("/unassignGroupToCourse")
	public String coursesUnassignGroupToCourse(@RequestParam Long courseId, @RequestParam Long unassignedGroupId, Model model)
			throws SQLException {
		Set<Group> courseGroups = courseService.findCourseById(courseId).getGroups();
		Group unassignedGroup = groupService.findGroupById(unassignedGroupId);
		courseGroups.remove(unassignedGroup);
		List<Course> courses = courseService.findAllCourses();
		List<Group> groups = groupService.findAllGroups();
		model.addAttribute("courses", courses);
		model.addAttribute("groups", groups);
		return "courses/coursesAssignGroup";
	}
	
	@GetMapping("/assignTeacher")
	public String coursesAssignTeacher(Model model) {
		List<Course> courses = courseService.findAllCourses();
		List<Teacher> teachers = teacherService.findAllTeachers();
		model.addAttribute("courses", courses);
		model.addAttribute("teachers", teachers);
		return "courses/coursesAssignTeacher";
	}
	
	@PostMapping("/assignTeacherToCourse")
	public String coursesAssignTeacherToCourse(@RequestParam Long courseId, @RequestParam Long assignedTeacherId, Model model)
			throws SQLException {
		Set<Teacher> courseTeachers = courseService.findCourseById(courseId).getTeachers();
		Teacher assignedTeacher = teacherService.findTeacherById(assignedTeacherId);
		courseTeachers.add(assignedTeacher);
		List<Course> courses = courseService.findAllCourses();
		List<Teacher> teachers = teacherService.findAllTeachers();
		model.addAttribute("courses", courses);
		model.addAttribute("teachers", teachers);
		return "courses/coursesAssignTeacher";
	}
	
	@PostMapping("/unassignTeacherToCourse")
	public String coursesUnassignTeacherToCourse(@RequestParam Long courseId, @RequestParam Long unassignedTeacherId, Model model)
			throws SQLException {
		Set<Teacher> courseTeachers = courseService.findCourseById(courseId).getTeachers();
		Teacher unassignedTeacher = teacherService.findTeacherById(unassignedTeacherId);
		courseTeachers.remove(unassignedTeacher);
		List<Course> courses = courseService.findAllCourses();
		List<Teacher> teachers = teacherService.findAllTeachers();
		model.addAttribute("courses", courses);
		model.addAttribute("teachers", teachers);
		return "courses/coursesAssignTeacher";
	}

}
