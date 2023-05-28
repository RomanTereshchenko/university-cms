package com.foxminded.javaspring.universitycms.service;

import java.util.Comparator;

import org.springframework.stereotype.Service;

import com.foxminded.javaspring.universitycms.model.Lesson;


@Service
public class LessonComparator implements Comparator<Lesson> {

	public int compare(Lesson l1, Lesson l2) {
		Comparator<Lesson> c = Comparator.comparing(Lesson::getLessonDate);
		c = c.thenComparing(Lesson::getLessonTime);
		return c.compare(l1, l2);
	}
}
