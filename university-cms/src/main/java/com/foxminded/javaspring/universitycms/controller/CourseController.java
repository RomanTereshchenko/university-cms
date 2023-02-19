package com.foxminded.javaspring.universitycms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.foxminded.javaspring.universitycms.dao.CourseDao;
import com.foxminded.javaspring.universitycms.model.Course;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
	
	@Autowired
    private CourseDao courseDao;

    @GetMapping
    public List<Course> findAll() {
        return courseDao.findAll();
    }
//
//    @GetMapping("/title/{bookTitle}")
//    public List findByTitle(@PathVariable String bookTitle) {
//        return bookRepository.findByTitle(bookTitle);
//    }
//
//    @GetMapping("/{id}")
//    public Book findOne(@PathVariable Long id) {
//        return bookRepository.findById(id)
//          .orElseThrow(BookNotFoundException::new);
//    }
//
//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public Book create(@RequestBody Book book) {
//        return bookRepository.save(book);
//    }
//
//    @DeleteMapping("/{id}")
//    public void delete(@PathVariable Long id) {
//        bookRepository.findById(id)
//          .orElseThrow(BookNotFoundException::new);
//        bookRepository.deleteById(id);
//    }
//
//    @PutMapping("/{id}")
//    public Book updateBook(@RequestBody Book book, @PathVariable Long id) {
//        if (book.getId() != id) {
//          throw new BookIdMismatchException();
//        }
//        bookRepository.findById(id)
//          .orElseThrow(BookNotFoundException::new);
//        return bookRepository.save(book);

}
