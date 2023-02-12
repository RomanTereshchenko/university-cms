package com.foxminded.javaspring.universitycms.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foxminded.javaspring.universitycms.model.Person;

@Repository
public interface PersonDao extends JpaRepository<Person, Long> {

}
