package com.foxminded.javaspring.universitycms.model;

import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "courses", schema = "university")
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long courseID;

	@Column(name = "course_name")
	private String courseName;

	@Column(name = "course_description")
	private String courseDescription;

	@ManyToMany(mappedBy = "courses", cascade = CascadeType.ALL)
	private Set<Group> groups;

	@ManyToMany(mappedBy = "courses", cascade = CascadeType.ALL)
	private Set<Teacher> teachers;
	
	@OneToMany (mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Lesson> lessons;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Course course = (Course) o;
		return Objects.equals(courseName, course.courseName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(courseName);
	}

	public String getGroupsNames() {
		if (this.groups == null) {return "-";}
		StringBuilder courseGroupsNames = new StringBuilder();
		for (Group group : groups) {
			courseGroupsNames.append(group.getGroupName()).append(", ");
		}
		return courseGroupsNames.toString();
	}

	public String getTeachersNames() {
		if (this.groups == null) {return "-";}
		StringBuilder courseTeachersNames = new StringBuilder();
		for (Teacher teacher : teachers) {
			courseTeachersNames.append(teacher.getPerson().getFirstName()).append(" ")
					.append(teacher.getPerson().getLastName()).append(", ");
		}
		return courseTeachersNames.toString();
	}
}
