package com.foxminded.javaspring.universitycms.model;

import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
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
	
	@OneToMany (mappedBy = "teacher", cascade = CascadeType.ALL)
	private Set<Lesson> lessons;

	public Teacher(Person person, Set<Course> courses) {
		this.person = person;
		this.courses = courses;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Teacher teacher = (Teacher) o;
		return Objects.equals(teacherID, teacher.teacherID);
	}

	@Override
	public int hashCode() {
		return Objects.hash(teacherID);
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
		if (this.courses == null) {return "N/A";}
		StringBuilder teacherCoursesNames = new StringBuilder();
		for (Course course : courses) {
			teacherCoursesNames.append(course.getCourseName()).append(", ");
		}
		return teacherCoursesNames.toString();
	}

}
