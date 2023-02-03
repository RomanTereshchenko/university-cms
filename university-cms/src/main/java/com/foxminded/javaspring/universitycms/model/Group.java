package com.foxminded.javaspring.universitycms.model;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
	@Column(name = "group_id")
	private Integer groupID;

	@Column(name = "group_name")
	private String groupName;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "groups_courses", schema = "university", joinColumns = @JoinColumn(name = "group_id"), inverseJoinColumns = @JoinColumn(name = "course_id"))
	private Set<Course> courses;
	
	@OneToOne(fetch = FetchType.LAZY)
	private Schedule schedule;
	
	public void addCourse(Course course) {
		courses.add(course);
		course.getGroups().add(this);
	}
	
	public void removeCourse(Course course) {
		courses.add(course);
		course.getGroups().remove(this);
	}
}
