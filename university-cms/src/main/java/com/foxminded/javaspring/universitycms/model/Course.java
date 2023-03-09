package com.foxminded.javaspring.universitycms.model;

import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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

	@ManyToMany(mappedBy = "courses")
	private Set<Group> groups;

	@ManyToMany(mappedBy = "courses")
	private Set<Teacher> teachers;
	
//	public Course(String courseName) {
//		this.courseName = courseName;
//	}
//	
//	public Course(Long courseID, String courseName) {
//		this.courseID = courseID;
//		this.courseName = courseName;
//	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(courseName, course.courseName);
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(courseName);
    }
}
