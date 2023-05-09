package com.foxminded.javaspring.universitycms.controller;

import java.security.Principal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
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
	public String courses(Model model, Principal principal) {
		String currentUserName = principal.getName();
		Person userPerson = personService.findPersonByLogin(currentUserName);
		model.addAttribute("userPerson", userPerson);
		return "courses/courses";
	}

	@GetMapping("/all")
	public String showAll(Model model) {
		List<Course> courses = courseService.findAllCourses();
		model.addAttribute("courses", courses);
		return "courses/all";
	}

	@GetMapping("/create")
	public String coursesCreate(Model model) {
		List<Course> courses = courseService.findAllCourses();
		model.addAttribute("courses", courses);
		return "courses/create";
	}

	@PostMapping("/create-course")
	public String createNewCourse(@RequestParam Map<String, String> courseParams, Model model) throws SQLException {
		Course course = new Course();
		course.setCourseName(courseParams.get("name"));
		course.setCourseDescription(courseParams.get("description"));
		courseService.saveNewCourse(course);
		List<Course> courses = courseService.findAllCourses();
		model.addAttribute("courses", courses);
		return "courses/create";
	}

	@GetMapping("/update")
	public String coursesUpdate(Model model) {
		List<Course> courses = courseService.findAllCourses();
		model.addAttribute("courses", courses);
		return "courses/update";
	}

	@PostMapping("/update-course")
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
		return "courses/update";
	}

	@GetMapping("/delete")
	public String coursesDelete(Model model) {
		List<Course> courses = courseService.findAllCourses();
		model.addAttribute("courses", courses);
		return "courses/delete";
	}

	@PostMapping("/delete-course")
	public String deleteCourse(@RequestParam Long courseId, Model model) throws SQLException {
		courseService.deleteCourseById(courseId);
		List<Course> courses = courseService.findAllCourses();
		model.addAttribute("courses", courses);
		return "courses/delete";
	}

	@GetMapping("/assign-group")
	public String coursesAssignGroup(Model model) {
		List<Course> courses = courseService.findAllCourses();
		List<Group> groups = groupService.findAllGroups();
		model.addAttribute("courses", courses);
		model.addAttribute("groups", groups);
		return "courses/assign-group";
	}

	@PostMapping("/assign-group-to-course")
	public String coursesAssignGroupToCourse(@RequestParam Long courseId, @RequestParam Long assignedGroupId, Model model)
			throws SQLException {
		Set<Group> courseGroups = courseService.findCourseById(courseId).getGroups();
		Group assignedGroup = groupService.findGroupById(assignedGroupId);
		courseGroups.add(assignedGroup);
		List<Course> courses = courseService.findAllCourses();
		List<Group> groups = groupService.findAllGroups();
		model.addAttribute("courses", courses);
		model.addAttribute("groups", groups);
		return "courses/assign-group";
	}
	
	@PostMapping("/unassign-group-from-course")
	public String coursesUnassignGroupToCourse(@RequestParam Long courseId, @RequestParam Long unassignedGroupId, Model model)
			throws SQLException {
		Set<Group> courseGroups = courseService.findCourseById(courseId).getGroups();
		Group unassignedGroup = groupService.findGroupById(unassignedGroupId);
		courseGroups.remove(unassignedGroup);
		List<Course> courses = courseService.findAllCourses();
		List<Group> groups = groupService.findAllGroups();
		model.addAttribute("courses", courses);
		model.addAttribute("groups", groups);
		return "courses/assign-group";
	}
	
	@GetMapping("/assign-teacher")
	public String coursesAssignTeacher(Model model) {
		List<Course> courses = courseService.findAllCourses();
		List<Teacher> teachers = teacherService.findAllTeachers();
		model.addAttribute("courses", courses);
		model.addAttribute("teachers", teachers);
		return "courses/assign-teacher";
	}
	
	@PostMapping("/assign-teacher-to-course")
	public String coursesAssignTeacherToCourse(@RequestParam Long courseId, @RequestParam Long assignedTeacherId, Model model)
			throws SQLException {
		Set<Teacher> courseTeachers = courseService.findCourseById(courseId).getTeachers();
		Teacher assignedTeacher = teacherService.findTeacherById(assignedTeacherId);
		courseTeachers.add(assignedTeacher);
		List<Course> courses = courseService.findAllCourses();
		List<Teacher> teachers = teacherService.findAllTeachers();
		model.addAttribute("courses", courses);
		model.addAttribute("teachers", teachers);
		return "courses/assign-teacher";
	}
	
	@PostMapping("/unassign-teacher-from-course")
	public String coursesUnassignTeacherToCourse(@RequestParam Long courseId, @RequestParam Long unassignedTeacherId, Model model)
			throws SQLException {
		Set<Teacher> courseTeachers = courseService.findCourseById(courseId).getTeachers();
		Teacher unassignedTeacher = teacherService.findTeacherById(unassignedTeacherId);
		courseTeachers.remove(unassignedTeacher);
		List<Course> courses = courseService.findAllCourses();
		List<Teacher> teachers = teacherService.findAllTeachers();
		model.addAttribute("courses", courses);
		model.addAttribute("teachers", teachers);
		return "courses/assign-teacher";
	}

}
