package com.foxminded.javaspring.universitycms.model;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Teacher extends User {
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "teachers_courses", schema = "university", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "course_id"))
	private Set<Course> courses;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "schedule_id")
	private Schedule schedule;
	
	public void addCourse (Course course) {
		courses.add(course);
		course.getTeachers().add(this);
	}
	
	public void removeCourse (Course course) {
		courses.remove(course);
		course.getTeachers().remove(this);
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Teacher))
			return false;
		return userID != null && userID.equals(((Teacher) o).getUserID());
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}

}
