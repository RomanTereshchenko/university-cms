package com.foxminded.javaspring.universitycms.model;

import jakarta.persistence.Column;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import java.util.Set;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
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
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "teacher_id")
	private Person person;
	
	@ManyToMany
	@JoinTable(name = "teachers_courses", schema = "university", joinColumns = @JoinColumn(name = "teacher_id"), inverseJoinColumns = @JoinColumn(name = "course_id"))
	private Set<Course> courses;
	
	public Teacher(Person person, Set<Course> courses) {
		this.person = person;
		this.courses = courses;
	}
	
	public void addCourse (Course course) {
		courses.add(course);
		course.getTeachers().add(this);
	}
	
	public void removeCourse (Course course) {
		courses.remove(course);
		course.getTeachers().remove(this);
	}

}
