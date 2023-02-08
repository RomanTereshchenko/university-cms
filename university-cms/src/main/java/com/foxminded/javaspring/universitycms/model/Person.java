package com.foxminded.javaspring.universitycms.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "persons", schema = "university")
public class Person {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column (name = "person_id")
	protected Long personID;
	
	@Column (name = "login")
	protected String login;
	
	@Column (name = "password")
	protected String password;
	
	@Column (name = "first_name")
	protected String firstName;
	
	@Column (name = "last_name")
	protected String lastName;
	
	@Column (name = "role")
	protected Role role;

}
