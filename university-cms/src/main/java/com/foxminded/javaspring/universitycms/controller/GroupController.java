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
import com.foxminded.javaspring.universitycms.model.Student;
import com.foxminded.javaspring.universitycms.service.CourseService;
import com.foxminded.javaspring.universitycms.service.GroupService;
import com.foxminded.javaspring.universitycms.service.PersonService;
import com.foxminded.javaspring.universitycms.service.StudentService;

@Controller
@RequestMapping("/groups")
public class GroupController {

	private GroupService groupService;	
	private CourseService courseService;
	private PersonService personService;
	private StudentService studentService;

	@Autowired
	public GroupController(CourseService courseService, PersonService personService, GroupService groupService,
			StudentService studentService) {
		this.courseService = courseService;
		this.personService = personService;
		this.groupService = groupService;
		this.studentService = studentService;
	}

	@GetMapping
	public String groups(Model model, Principal principal) {
		String currentUserName = principal.getName();
		Person userPerson = personService.findPersonByLogin(currentUserName);
		model.addAttribute("userPerson", userPerson);
		return "groups/groups";
	}

	@GetMapping("/all")
	public String showAll(Model model) {
		List<Group> groups = groupService.findAllGroups();
		model.addAttribute("groups", groups);
		return "groups/all";
	}

	@GetMapping("/create")
	public String groupsCreate(Model model) {
		List<Group> groups = groupService.findAllGroups();
		model.addAttribute("groups", groups);
		return "groups/create";
	}

	@PostMapping("/create-group")
	public String createNewGroup(@RequestParam Map<String, String> groupParams, Model model) throws SQLException {
		Group group = new Group();
		group.setGroupName(groupParams.get("name"));
		groupService.saveNewGroup(group);
		List<Group> groups = groupService.findAllGroups();
		model.addAttribute("groups", groups);
		return "groups/create";
	}

	@GetMapping("/update")
	public String groupsUpdate(Model model) {
		List<Group> groups = groupService.findAllGroups();
		model.addAttribute("groups", groups);
		return "groups/update";
	}

	@PostMapping("/update-group")
	public String updateGroup(@RequestParam Map<String, String> groupParams, Model model) throws SQLException {		
		Group group = groupService.findGroupById(Long.parseLong(groupParams.get("groupId")));
		Group updatedGroup = new Group();
		updatedGroup.setGroupID(group.getGroupID());
		updatedGroup.setGroupName(groupParams.get("name"));
		updatedGroup.setCourses(group.getCourses());
		updatedGroup.setStudents(group.getStudents());
		updatedGroup.setLessons(group.getLessons());
		groupService.updateGroup(updatedGroup);
		List<Group> groups = groupService.findAllGroups();
		model.addAttribute("groups", groups);
		return "groups/update";
	}

	@GetMapping("/delete")
	public String groupsDelete(Model model) {
		List<Group> groups = groupService.findAllGroups();
		model.addAttribute("groups", groups);
		return "groups/delete";
	}

	@PostMapping("/delete-group")
	public String deleteGroup(@RequestParam Long groupId, Model model) throws SQLException {
		groupService.deleteGroupById(groupId);
		List<Group> groups = groupService.findAllGroups();
		model.addAttribute("groups", groups);
		return "groups/delete";
	}

	@GetMapping("/assign-course")
	public String groupsAssignCourse(Model model) {
		List<Group> groups = groupService.findAllGroups();
		List<Course> courses = courseService.findAllCourses();
		model.addAttribute("groups", groups);
		model.addAttribute("courses", courses);
		return "groups/assign-course";
	}

	@PostMapping("/assign-course-to-group")
	public String groupsAssignCourseToGroup(@RequestParam Long groupId, @RequestParam Long assignedCourseId, Model model)
			throws SQLException {
		Set<Course> groupCourses = groupService.findGroupById(groupId).getCourses();
		Course assignedCourse = courseService.findCourseById(assignedCourseId);
		groupCourses.add(assignedCourse);
		List<Group> groups = groupService.findAllGroups();
		List<Course> courses = courseService.findAllCourses();
		model.addAttribute("groups", groups);
		model.addAttribute("courses", courses);
		return "groups/assign-course";
	}
	
	@PostMapping("/unassign-course-from-group")
	public String groupsUnassignCourseToGroup(@RequestParam Long groupId, @RequestParam Long unassignedCourseId, Model model)
			throws SQLException {
		Set<Course> groupCourses = groupService.findGroupById(groupId).getCourses();
		Course unassignedCourse = courseService.findCourseById(unassignedCourseId);
		groupCourses.remove(unassignedCourse);
		List<Group> groups = groupService.findAllGroups();
		List<Course> courses = courseService.findAllCourses();
		model.addAttribute("groups", groups);
		model.addAttribute("courses", courses);
		return "groups/assign-course";
	}
	
	@GetMapping("/assign-student")
	public String groupsAssignStudent(Model model) {
		List<Group> groups = groupService.findAllGroups();
		List<Student> students = studentService.findAllStudents();
		model.addAttribute("groups", groups);
		model.addAttribute("students", students);
		return "groups/assign-student";
	}
	
	@PostMapping("/assign-student-to-group")
	public String groupsAssignStudentToGroup(@RequestParam Long groupId, @RequestParam Long assignedStudentId, Model model)
			throws SQLException {
		Set<Student> groupStudents = groupService.findGroupById(groupId).getStudents();
		Student assignedStudent = studentService.findStudentById(assignedStudentId);
		groupStudents.add(assignedStudent);
		List<Group> groups = groupService.findAllGroups();
		List<Student> students = studentService.findAllStudents();
		model.addAttribute("groups", groups);
		model.addAttribute("students", students);
		return "groups/assign-student";
	}
	
	@PostMapping("/unassign-student-from-group")
	public String groupsUnasssignStudentToGroup(@RequestParam Long groupId, @RequestParam Long unassignedStudentId, Model model)
			throws SQLException {
		Set<Student> groupStudents = groupService.findGroupById(groupId).getStudents();
		Student unassignedStudent = studentService.findStudentById(unassignedStudentId);
		groupStudents.remove(unassignedStudent);
		List<Group> groups = groupService.findAllGroups();
		List<Student> students = studentService.findAllStudents();
		model.addAttribute("groups", groups);
		model.addAttribute("students", students);
		return "groups/assign-student";
	}

}
