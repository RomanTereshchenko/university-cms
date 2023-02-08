package com.foxminded.javaspring.universitycms.model;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.persistence.CascadeType;
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
public class Teacher {
	
	@Id
	@GeneratedValue
	private Long teacherID;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "person_id")
	private Person person;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "teachers_courses", schema = "university", joinColumns = @JoinColumn(name = "person_id"), inverseJoinColumns = @JoinColumn(name = "course_id"))
	private Set<Course> courses;
	
	@Autowired
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
