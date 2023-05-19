package com.foxminded.javaspring.universitycms.generator;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxminded.javaspring.universitycms.dao.LessonDao;
import com.foxminded.javaspring.universitycms.model.Lesson;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class ScheduleGenerator {

	private Random random;
	private LessonDao lessonDao;

	@Autowired
	public ScheduleGenerator(Random random, LessonDao lessonDao) {
		this.random = random;
		this.lessonDao = lessonDao;
	}

	public void generateSchedule(LocalDate startDate, LocalDate endDate, int lessonSessionsNumber) {
		applySessionsNumber(lessonSessionsNumber);
		setLessonsDatesTimes(startDate, endDate);
		log.info("Initial schedule generated");
	}

	public void applySessionsNumber(int lessonSessionsNumber) {
		List<Lesson> lessons = lessonDao.findAll();
		for (int i = 0; i < lessonSessionsNumber - 1; i++) {
			for (Lesson lesson : lessons) {
				Lesson additionalLesson = new Lesson();
				additionalLesson.setLessonDate(lesson.getLessonDate());
				additionalLesson.setLessonTime(lesson.getLessonTime());
				additionalLesson.setCourse(lesson.getCourse());
				additionalLesson.setTeacher(lesson.getTeacher());
				additionalLesson.setGroup(lesson.getGroup());
				lessonDao.save(additionalLesson);
			}
		}
	}

	public void setLessonsDatesTimes(LocalDate startDate, LocalDate endDate) {
		List<Lesson> lessons = lessonDao.findAll();
		for (Lesson lesson : lessons) {
			lesson.setLessonDate(generateRandomDate(startDate, endDate));
			lesson.setLessonTime(generateRandomTime());
			lessonDao.save(lesson);
		}
	}

	public LocalDate generateRandomDate(LocalDate startDate, LocalDate endDate) {
		long startDay = startDate.toEpochDay();
		long endDay = endDate.toEpochDay();
		long randomDay = ThreadLocalRandom.current().nextLong(startDay, endDay);
		LocalDate randomDate = LocalDate.ofEpochDay(randomDay);
		return randomDate;
	}

	public LocalTime generateRandomTime() {
		LocalTime time1 = LocalTime.of(9, 0);
		LocalTime time2 = LocalTime.of(12, 0);
		LocalTime time3 = LocalTime.of(15, 0);
		LocalTime time4 = LocalTime.of(18, 0);
		List<LocalTime> startTimes = Arrays.asList(time1, time2, time3, time4);
		LocalTime randomTime = startTimes.get(random.nextInt(4));
		return randomTime;
	}

}
