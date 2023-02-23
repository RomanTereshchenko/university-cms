package com.foxminded.javaspring.universitycms.dto;

import java.util.List;

import com.foxminded.javaspring.universitycms.model.Person;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonDto {
	private List<Person> persons;
	
	public void addPerson (Person person) {
		this.persons.add(person);
	}

}
