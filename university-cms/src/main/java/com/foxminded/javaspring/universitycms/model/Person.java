package com.foxminded.javaspring.universitycms.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKeyEnumerated;
import javax.persistence.Table;

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
	@Column (name = "id")
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
	@MapKeyEnumerated(value = EnumType.STRING)
	protected Role role;

}
