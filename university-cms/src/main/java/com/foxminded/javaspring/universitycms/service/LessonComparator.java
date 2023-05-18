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
	
//	@Override
//	public int compare(Lesson l1, Lesson l2) {
//		int result = ((Long)(l1.getLessonDate().toEpochDay())).compareTo(l2.getLessonDate().toEpochDay());
//		if (result != 0) return result;
//		return l1.getLessonTime().toSecondOfDay() - l2.getLessonTime().toSecondOfDay();
//	}
	
//	public int compare(Lesson l1, Lesson l2) {
//		return l1.getTeacher().getPerson().getLastName().compareTo(l2.getTeacher().getPerson().getLastName());
//	}
}
