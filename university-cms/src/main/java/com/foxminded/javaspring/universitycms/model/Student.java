package com.foxminded.javaspring.universitycms.model;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
public class Student extends User {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "group_id")
	private Group group;

	@Autowired
	public Student(Group group) {
		this.group = group;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Student))
			return false;
		return userID != null && userID.equals(((Student) o).getUserID());
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}



}
