package com.foxminded.javaspring.universitycms.controller;

import java.security.Principal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.foxminded.javaspring.universitycms.model.Group;
import com.foxminded.javaspring.universitycms.model.Person;
import com.foxminded.javaspring.universitycms.model.Role;
import com.foxminded.javaspring.universitycms.model.Student;
import com.foxminded.javaspring.universitycms.service.GroupService;
import com.foxminded.javaspring.universitycms.service.PersonService;
import com.foxminded.javaspring.universitycms.service.StudentService;

@Controller
@RequestMapping("/students")
public class StudentController {

	private StudentService studentService;
	private PersonService personService;
	private GroupService groupService;

	@Autowired
	public StudentController(StudentService studentService, PersonService personService, GroupService groupService) {
		this.studentService = studentService;
		this.personService = personService;
		this.groupService = groupService;
	}

	@GetMapping
	public String students(Model model, Principal principal) {
		String currentUserName = principal.getName();
		Person userPerson = personService.findPersonByLogin(currentUserName);
		model.addAttribute("userPerson", userPerson);
		return "students/students";
	}

	@GetMapping("/all")
	public String showAll(Model model) {
		List<Student> students = studentService.findAllStudents();
		model.addAttribute("students", students);
		return "students/all";
	}

	@GetMapping("/create")
	public String studentsCreate(Model model) {
		List<Student> students = studentService.findAllStudents();
		model.addAttribute("students", students);
		return "students/create";
	}

	@PostMapping("/create-student")
	public String createNewStudent(@RequestParam Map<String, String> studentParams, Model model) throws SQLException {
		Person person = new Person();
		Student student = new Student();
		person.setFirstName(studentParams.get("firstName"));
		person.setLastName(studentParams.get("lastName"));
		person.setLogin(studentParams.get("login"));
		person.setPassword(studentParams.get("password"));
		person.setRole(Role.STUDENT);
		student.setPerson(person);
		personService.saveNewPerson(person);
		studentService.saveNewStudent(student);
		List<Student> students = studentService.findAllStudents();
		model.addAttribute("students", students);
		return "students/create";
	}

	@GetMapping("/update")
	public String studentsUpdate(Model model) {
		List<Student> students = studentService.findAllStudents();
		model.addAttribute("students", students);
		return "students/update";
	}

	@PostMapping("/update-student")
	public String updateStudent(@RequestParam Map<String, String> studentParams, Model model) throws SQLException {		
		Student student = studentService.findStudentById(Long.parseLong(studentParams.get("studentId")));
		Person person = personService.findPersonById(student.getPerson().getPersonID());
		Student updatedStudent = new Student();
		Person updatedPerson = new Person();
		updatedPerson.setPersonID(person.getPersonID());
		updatedPerson.setFirstName(studentParams.get("firstName"));
		updatedPerson.setLastName(studentParams.get("lastName"));
		updatedPerson.setLogin(studentParams.get("login"));
		updatedPerson.setPassword(studentParams.get("password"));
		updatedPerson.setRole(Role.STUDENT);
		personService.updatePerson(updatedPerson);
		updatedStudent.setStudentID(student.getStudentID());
		updatedStudent.setPerson(updatedPerson);
		studentService.updateStudent(updatedStudent);
		List<Student> students = studentService.findAllStudents();
		model.addAttribute("students", students);
		return "students/update";
	}

	@GetMapping("/delete")
	public String studentsDelete(Model model) {
		List<Student> students = studentService.findAllStudents();
		model.addAttribute("students", students);
		return "students/delete";
	}

	@PostMapping("/delete-student")
	public String deleteStudent(@RequestParam Long studentId, Model model) throws SQLException {
		studentService.deleteStudentById(studentId);
		List<Student> students = studentService.findAllStudents();
		model.addAttribute("students", students);
		return "students/delete";
	}

	@GetMapping("/assign-group")
	public String studentsAssignGroup(Model model) {
		List<Student> students = studentService.findAllStudents();
		List<Group> groups = groupService.findAllGroups();
		model.addAttribute("students", students);
		model.addAttribute("groups", groups);
		return "students/assign-group";
	}

	@PostMapping("/assign-group-to-student")
	public String studentsAssignGroupToStudent(@RequestParam Long studentId, @RequestParam Long assignedGroupId, Model model)
			throws SQLException {
		Student student = studentService.findStudentById(studentId);
		Group assignedGroup = groupService.findGroupById(assignedGroupId);
		student.setGroup(assignedGroup);
		studentService.updateStudent(student);
		List<Student> students = studentService.findAllStudents();
		List<Group> groups = groupService.findAllGroups();
		model.addAttribute("students", students);
		model.addAttribute("groups", groups);
		return "students/assign-group";
	}
	
	@PostMapping("/unassign-group-from-student")
	public String studentsUnassignGroupFromStudent(@RequestParam Long studentId, Model model)
			throws SQLException {
		Student student = studentService.findStudentById(studentId);
		student.setGroup(null);
		studentService.updateStudent(student);
		List<Student> students = studentService.findAllStudents();
		List<Group> groups = groupService.findAllGroups();
		model.addAttribute("students", students);
		model.addAttribute("groups", groups);
		return "students/assign-group";
	}

}
