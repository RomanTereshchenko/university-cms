package com.foxminded.javaspring.universitycms.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "teachers", schema = "university")
public class Teacher {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long teacherID;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "person_id")
	private Person person;

	@ManyToMany
	@JoinTable(name = "teachers_courses", schema = "university", joinColumns = @JoinColumn(name = "teacher_id"), inverseJoinColumns = @JoinColumn(name = "course_id"))
	private Set<Course> courses;

	public Teacher(Person person, Set<Course> courses) {
		this.person = person;
		this.courses = courses;
	}

	public void addCourse(Course course) {
		courses.add(course);
		course.getTeachers().add(this);
	}

	public void removeCourse(Course course) {
		courses.remove(course);
		course.getTeachers().remove(this);
	}

	public String getCoursesNames() {
		StringBuilder teacherCoursesNames = new StringBuilder();
		for (Course course : courses) {
			teacherCoursesNames.append(course.getCourseName()).append(", ");
		}
		return teacherCoursesNames.toString();
	}

}
