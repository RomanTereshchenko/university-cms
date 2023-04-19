package com.foxminded.javaspring.universitycms.serviceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.foxminded.javaspring.universitycms.dao.LessonDao;
import com.foxminded.javaspring.universitycms.model.Lesson;
import com.foxminded.javaspring.universitycms.service.LessonService;

@ExtendWith(MockitoExtension.class)
class LessonServiceTest {
	
	@Mock
	private LessonDao lessonDao;
	
	@InjectMocks
	private LessonService lessonService;
	
	@Test
	void testSaveNewLesson() throws SQLException {
		Lesson lesson = new Lesson();
		lesson.setLessonDate(LocalDate.of(2000, 1, 1));
		Mockito.when(lessonDao.save(any(Lesson.class))).thenReturn(lesson);
		Lesson savedLesson = lessonService.saveNewLesson(lesson);
		assertEquals(LocalDate.of(2000, 1, 1), savedLesson.getLessonDate());
	}
	
	@Test
	void testFindLessonById() throws SQLException {
		Lesson savedLesson = new Lesson();
		savedLesson.setLessonID(1L);
		Mockito.when(lessonDao.findById(savedLesson .getLessonID())).thenReturn(Optional.of(savedLesson ));
		Lesson foundLesson = lessonService.findLessonById(savedLesson.getLessonID());
		assertEquals(1L, foundLesson.getLessonID());
		Lesson notFoundLesson = lessonService.findLessonById(0L);
		assertEquals(null, notFoundLesson);
	}
	
	@Test
	void testFindAllLessons() throws SQLException {
		List<Lesson> allLessonsList = new ArrayList<>();
		Lesson lesson = new Lesson();
		lesson.setLessonDate(LocalDate.of(2000, 1, 1));
		allLessonsList.add(lesson);
		Mockito.when(lessonDao.findAll()).thenReturn(allLessonsList);
		List<Lesson> foundLessons = lessonService.findAllLessons();
		assertEquals(LocalDate.of(2000, 1, 1), foundLessons.get(0).getLessonDate());
	}
	
	@Test
	void testUpdateLesson() throws SQLException {
		Lesson dBLesson = new Lesson();
		dBLesson.setLessonID(1L);
		Lesson updatingLesson = new Lesson();
		updatingLesson.setLessonID(dBLesson.getLessonID());
		updatingLesson.setLessonDate(LocalDate.of(2000, 1, 1));
		Lesson nonUpdatingLesson = new Lesson();
		nonUpdatingLesson.setLessonID(0L);
		Mockito.when(lessonDao.findById(dBLesson.getLessonID())).thenReturn(Optional.of(dBLesson));
		Lesson updatedLesson = lessonService.updateLesson(updatingLesson);
		assertEquals(LocalDate.of(2000, 1, 1), updatedLesson.getLessonDate());
		Lesson nonUpdatedLesson = lessonService.updateLesson(nonUpdatingLesson);
		assertEquals(null, nonUpdatedLesson);
	}
	
	@Test
	void testDeleteLesson() throws SQLException {
		lessonService.deleteLessonById(anyLong());
		Mockito.verify(lessonDao).deleteById(anyLong());
	}

}