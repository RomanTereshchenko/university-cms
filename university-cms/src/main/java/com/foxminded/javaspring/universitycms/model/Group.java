package com.foxminded.javaspring.universitycms.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "groups", schema = "university")
public class Group {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long groupID;

	@Column(name = "group_name")
	private String groupName;

	@ManyToMany
	@JoinTable(name = "groups_courses", schema = "university", joinColumns = @JoinColumn(name = "group_id"), inverseJoinColumns = @JoinColumn(name = "course_id"))
	private Set<Course> courses;

	public Group(Set<Course> courses) {
		this.courses = courses;
	}

	public void addCourse(Course course) {
		courses.add(course);
		course.getGroups().add(this);
	}

	public void removeCourse(Course course) {
		courses.add(course);
		course.getGroups().remove(this);
	}

	public String getCoursesNames() {
		StringBuilder groupCoursesNames = new StringBuilder();
		for (Course course : courses) {
			groupCoursesNames.append(course.getCourseName()).append(", ");
		}
		return groupCoursesNames.toString();
	}

}
