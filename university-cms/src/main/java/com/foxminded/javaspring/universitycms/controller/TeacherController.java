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
import com.foxminded.javaspring.universitycms.model.Person;
import com.foxminded.javaspring.universitycms.model.Role;
import com.foxminded.javaspring.universitycms.model.Teacher;
import com.foxminded.javaspring.universitycms.service.CourseService;
import com.foxminded.javaspring.universitycms.service.PersonService;
import com.foxminded.javaspring.universitycms.service.TeacherService;

@Controller
@RequestMapping("/teachers")
public class TeacherController {

	private TeacherService teacherService;
	private PersonService personService;
	private CourseService courseService;

	@Autowired
	public TeacherController(TeacherService teacherService, PersonService personService, CourseService courseService) {
		this.teacherService = teacherService;
		this.personService = personService;
		this.courseService = courseService;
	}

	@GetMapping
	public String teachers(Model model, Principal principal) {
		String currentUserName = principal.getName();
		Person userPerson = personService.findPersonByLogin(currentUserName);
		model.addAttribute("userPerson", userPerson);
		return "teachers/teachers";
	}

	@GetMapping("/all")
	public String showAll(Model model) {
		List<Teacher> teachers = teacherService.findAllTeachers();
		model.addAttribute("teachers", teachers);
		return "teachers/all";
	}

	@GetMapping("/create")
	public String teachersCreate(Model model) {
		List<Teacher> teachers = teacherService.findAllTeachers();
		model.addAttribute("teachers", teachers);
		return "teachers/create";
	}

	@PostMapping("/create-teacher")
	public String createNewTeacher(@RequestParam Map<String, String> teacherParams, Model model) throws SQLException {
		Person person = new Person();
		Teacher teacher = new Teacher();
		person.setFirstName(teacherParams.get("firstName"));
		person.setLastName(teacherParams.get("lastName"));
		person.setLogin(teacherParams.get("login"));
		person.setPassword(teacherParams.get("password"));
		person.setRole(Role.TEACHER);
		teacher.setPerson(person);
		personService.saveNewPerson(person);
		teacherService.saveNewTeacher(teacher);
		List<Teacher> teachers = teacherService.findAllTeachers();
		model.addAttribute("teachers", teachers);
		return "teachers/create";
	}

	@GetMapping("/update")
	public String teachersUpdate(Model model) {
		List<Teacher> teachers = teacherService.findAllTeachers();
		model.addAttribute("teachers", teachers);
		return "teachers/update";
	}

	@PostMapping("/update-teacher")
	public String updateTeacher(@RequestParam Map<String, String> teacherParams, Model model) throws SQLException {		
		Teacher teacher = teacherService.findTeacherById(Long.parseLong(teacherParams.get("teacherId")));
		Person person = personService.findPersonById(teacher.getPerson().getPersonID());
		Teacher updatedTeacher = new Teacher();
		Person updatedPerson = new Person();
		updatedPerson.setPersonID(person.getPersonID());
		updatedPerson.setFirstName(teacherParams.get("firstName"));
		updatedPerson.setLastName(teacherParams.get("lastName"));
		updatedPerson.setLogin(teacherParams.get("login"));
		updatedPerson.setPassword(teacherParams.get("password"));
		updatedPerson.setRole(Role.TEACHER);
		personService.updatePerson(updatedPerson);
		updatedTeacher.setTeacherID(teacher.getTeacherID());
		updatedTeacher.setPerson(updatedPerson);
		teacherService.updateTeacher(updatedTeacher);
		List<Teacher> teachers = teacherService.findAllTeachers();
		model.addAttribute("teachers", teachers);
		return "teachers/update";
	}

	@GetMapping("/delete")
	public String teachersDelete(Model model) {
		List<Teacher> teachers = teacherService.findAllTeachers();
		model.addAttribute("teachers", teachers);
		return "teachers/delete";
	}

	@PostMapping("/delete-teacher")
	public String deleteTeacher(@RequestParam Long teacherId, Model model) throws SQLException {
		teacherService.deleteTeacherById(teacherId);
		List<Teacher> teachers = teacherService.findAllTeachers();
		model.addAttribute("teachers", teachers);
		return "teachers/delete";
	}

	@GetMapping("/assign-course")
	public String teachersAssignCourse(Model model) {
		List<Teacher> teachers = teacherService.findAllTeachers();
		List<Course> courses = courseService.findAllCourses();
		model.addAttribute("teachers", teachers);
		model.addAttribute("courses", courses);
		return "teachers/assign-course";
	}

	@PostMapping("/assign-course-to-teacher")
	public String teachersAssignCourseToTeacher(@RequestParam Long teacherId, @RequestParam Long assignedCourseId, Model model)
			throws SQLException {
		Set<Course> teacherCourses = teacherService.findTeacherById(teacherId).getCourses();
		Course assignedCourse = courseService.findCourseById(assignedCourseId);
		teacherCourses.add(assignedCourse);
		List<Teacher> teachers = teacherService.findAllTeachers();
		List<Course> courses = courseService.findAllCourses();
		model.addAttribute("teachers", teachers);
		model.addAttribute("courses", courses);
		return "teachers/assign-course";
	}
	
	@PostMapping("/unassign-course-from-teacher")
	public String teachersUnassignCourseFromTeacher(@RequestParam Long teacherId, @RequestParam Long unassignedCourseId, Model model)
			throws SQLException {
		Set<Course> teacherCourses = teacherService.findTeacherById(teacherId).getCourses();
		Course unassignedCourse = courseService.findCourseById(unassignedCourseId);
		teacherCourses.remove(unassignedCourse);
		List<Teacher> teachers = teacherService.findAllTeachers();
		List<Course> courses = courseService.findAllCourses();
		model.addAttribute("teachers", teachers);
		model.addAttribute("courses", courses);
		return "teachers/assign-course";
	}

}
