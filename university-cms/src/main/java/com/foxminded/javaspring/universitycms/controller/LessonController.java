package com.foxminded.javaspring.universitycms.controller;

import java.security.Principal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.foxminded.javaspring.universitycms.generator.LessonGenerator;
import com.foxminded.javaspring.universitycms.generator.ScheduleGenerator;
import com.foxminded.javaspring.universitycms.model.Course;
import com.foxminded.javaspring.universitycms.model.Group;
import com.foxminded.javaspring.universitycms.model.Lesson;
import com.foxminded.javaspring.universitycms.model.Person;
import com.foxminded.javaspring.universitycms.model.Teacher;
import com.foxminded.javaspring.universitycms.service.CourseService;
import com.foxminded.javaspring.universitycms.service.GroupService;
import com.foxminded.javaspring.universitycms.service.LessonComparator;
import com.foxminded.javaspring.universitycms.service.LessonService;
import com.foxminded.javaspring.universitycms.service.PersonService;
import com.foxminded.javaspring.universitycms.service.TeacherService;

@Controller
@RequestMapping("/lessons")
public class LessonController {

	private LessonService lessonService;
	private LessonGenerator lessonGenerator;
	private ScheduleGenerator scheduleGenerator;
	private LessonComparator lessonComparator;
	private CourseService courseService;
	private TeacherService teacherService;
	private GroupService groupService;
	private PersonService personService;

	@Autowired
	public LessonController(LessonService lessonService, LessonGenerator lessonGenerator,
			LessonComparator lessonComparator, CourseService courseService, TeacherService teacherService,
			GroupService groupService, PersonService personService, ScheduleGenerator scheduleGenerator) {
		this.lessonService = lessonService;
		this.lessonGenerator = lessonGenerator;
		this.lessonComparator = lessonComparator;
		this.courseService = courseService;
		this.teacherService = teacherService;
		this.groupService = groupService;
		this.personService = personService;
		this.scheduleGenerator = scheduleGenerator;
	}

	@GetMapping
	public String lessons(Model model, Principal principal) {
		String currentUserName = principal.getName();
		Person userPerson = personService.findPersonByLogin(currentUserName);
		model.addAttribute("userPerson", userPerson);
		return "lessons/lessons";
	}

	@GetMapping("/all")
	public String showAll(Model model) {
		List<Lesson> lessons = lessonService.findAllLessons();
		Collections.sort(lessons, lessonComparator);
		model.addAttribute("lessons", lessons);
		return "lessons/all";
	}

	@GetMapping("/create")
	public String lessonsCreate(Model model) {
		List<Lesson> lessons = lessonService.findAllLessons();
		Collections.sort(lessons, lessonComparator);
		model.addAttribute("lessons", lessons);
		return "lessons/create";
	}

	@PostMapping("/create-schedule")
	public String createSchedule(@RequestParam Map<String, String> lessonParams, Model model) throws SQLException {
		List<Lesson> lessons = lessonService.findAllLessons();
		for (Lesson lesson : lessons) {
			lessonService.deleteLessonById(lesson.getLessonID());
		}
		lessonGenerator.generateNLessons(Integer.parseInt(lessonParams.get("lessonsNumber")));
		scheduleGenerator.applySessionsNumber(Integer.parseInt(lessonParams.get("sessionsNumber")));
		LocalDate startDate = LocalDate.of(Integer.parseInt(lessonParams.get("startYear")),
				Integer.parseInt(lessonParams.get("startMonth")), (Integer.parseInt(lessonParams.get("startDay"))));
		LocalDate endDate = LocalDate.of(Integer.parseInt(lessonParams.get("endYear")),
				Integer.parseInt(lessonParams.get("endMonth")), (Integer.parseInt(lessonParams.get("endDay"))));
		scheduleGenerator.setLessonsDatesTimes(startDate, endDate);
		List<Lesson> lessonsNew = lessonService.findAllLessons();
		Collections.sort(lessonsNew, lessonComparator);
		model.addAttribute("lessons", lessonsNew);
		return "lessons/create";
	}

	@GetMapping("/add")
	public String lessonsAdd(Model model) {
		List<Lesson> lessons = lessonService.findAllLessons();
		Collections.sort(lessons, lessonComparator);
		List<Course> courses = courseService.findAllCourses();
		List<Teacher> teachers = teacherService.findAllTeachers();
		List<Group> groups = groupService.findAllGroups();
		model.addAttribute("lessons", lessons);
		model.addAttribute("courses", courses);
		model.addAttribute("teachers", teachers);
		model.addAttribute("groups", groups);
		return "lessons/add";
	}

	@PostMapping("/add-lesson")
	public String addNewLesson(@RequestParam Map<String, String> lessonParams, Model model) throws SQLException {
		Lesson lesson = new Lesson();
		lesson.setLessonDate(LocalDate.of(Integer.parseInt(lessonParams.get("lessonYear")), 
				Integer.parseInt(lessonParams.get("lessonMonth")), Integer.parseInt(lessonParams.get("lessonDay"))));
		lesson.setLessonTime(LocalTime.of(Integer.parseInt(lessonParams.get("lessonTime")), 0));
		Course assignedCourse = courseService.findCourseById(Long.parseLong(lessonParams.get("courseId")));
		lesson.setCourse(assignedCourse);
		Teacher assignedTeacher = teacherService.findTeacherById(Long.parseLong(lessonParams.get("teacherId")));
		lesson.setTeacher(assignedTeacher);
		Group assignedGroup = groupService.findGroupById(Long.parseLong(lessonParams.get("groupId")));
		lesson.setGroup(assignedGroup);
		lessonService.saveNewLesson(lesson);
		List<Lesson> lessons = lessonService.findAllLessons();
		Collections.sort(lessons, lessonComparator);
		List<Course> courses = courseService.findAllCourses();
		List<Teacher> teachers = teacherService.findAllTeachers();
		List<Group> groups = groupService.findAllGroups();
		model.addAttribute("lessons", lessons);
		model.addAttribute("courses", courses);
		model.addAttribute("teachers", teachers);
		model.addAttribute("groups", groups);
		return "lessons/add";
	}

	@GetMapping("/update")
	public String lessonsUpdate(Model model) {
		List<Lesson> lessons = lessonService.findAllLessons();
		Collections.sort(lessons, lessonComparator);
		model.addAttribute("lessons", lessons);
		return "lessons/update";
	}

	@PostMapping("/update-lesson")
	public String updateLesson(@RequestParam Map<String, String> lessonParams, Model model) throws SQLException {
		Lesson lesson = lessonService.findLessonById(Long.parseLong(lessonParams.get("lessonId")));
		Lesson updatedLesson = new Lesson();
		updatedLesson.setLessonID(lesson.getLessonID());
		updatedLesson.setLessonDate(LocalDate.of(Integer.parseInt(lessonParams.get("lessonYear")), 
				Integer.parseInt(lessonParams.get("lessonMonth")), Integer.parseInt(lessonParams.get("lessonDay"))));
		updatedLesson.setLessonTime(LocalTime.of(Integer.parseInt(lessonParams.get("lessonTime")), 0));
		updatedLesson.setCourse(lesson.getCourse());
		updatedLesson.setTeacher(lesson.getTeacher());
		updatedLesson.setGroup(lesson.getGroup());
		lessonService.updateLesson(updatedLesson);
		List<Lesson> lessons = lessonService.findAllLessons();
		Collections.sort(lessons, lessonComparator);
		model.addAttribute("lessons", lessons);
		return "lessons/update";
	}

	@GetMapping("/delete")
	public String lessonsDelete(Model model) {
		List<Lesson> lessons = lessonService.findAllLessons();
		Collections.sort(lessons, lessonComparator);
		model.addAttribute("lessons", lessons);
		return "lessons/delete";
	}

	@PostMapping("/delete-lesson")
	public String deleteLesson(@RequestParam Long lessonId, Model model) throws SQLException {
		lessonService.deleteLessonById(lessonId);
		List<Lesson> lessons = lessonService.findAllLessons();
		Collections.sort(lessons, lessonComparator);
		model.addAttribute("lessons", lessons);
		return "lessons/delete";
	}


}
