package com.foxminded.javaspring.universitycms.model;

import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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

	@OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
	private Set<Student> students;

	@OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
	private Set<Lesson> lessons;

	public Group(Set<Course> courses) {
		this.courses = courses;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Group group = (Group) o;
		return Objects.equals(groupName, group.groupName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(groupName);
	}

	public void addCourse(Course course) {
		courses.add(course);
		course.getGroups().add(this);
	}

	public void removeCourse(Course course) {
		courses.remove(course);
		course.getGroups().remove(this);
	}

	public String getCoursesNames() {
		if (this.courses == null) {return "N/A";}
		StringBuilder groupCoursesNames = new StringBuilder();
		for (Course course : courses) {
			groupCoursesNames.append(course.getCourseName()).append(", ");
		}
		return groupCoursesNames.toString();
	}

	public String getStudentsNames() {
		if (this.students == null) {return "N/A";}
		StringBuilder groupStudentsNames = new StringBuilder();
		for (Student student : students) {
			groupStudentsNames.append(student.getPerson().getFirstName()).append(" ")
					.append(student.getPerson().getLastName()).append(", ");
		}
		return groupStudentsNames.toString();
	}

}
