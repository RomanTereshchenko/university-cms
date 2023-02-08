package com.foxminded.javaspring.universitycms.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foxminded.javaspring.universitycms.model.Lesson;

@Repository
public interface LessonDao extends JpaRepository<Lesson, Long> {

}
