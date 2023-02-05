CREATE SCHEMA IF NOT EXISTS university;

CREATE TABLE IF NOT EXISTS university.persons
(
	person_id SERIAL PRIMARY KEY,
    login VARCHAR(20) NOT NULL,
    password VARCHAR(20) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    role VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS university.students
(
	student_id INT NOT NULL,
	group_id INT,
	course_id INT,
	FOREIGN KEY (student_id) REFERENCES university.persons(person_id),
	FOREIGN KEY (group_id) REFERENCES university.groups(group_id),
	FOREIGN KEY (course_id) REFERENCES university.courses(course_id)
);

CREATE TABLE IF NOT EXISTS university.teachers
(
	teacher_id INT NOT NULL,
	course_id INT,
	FOREIGN KEY (teacher_id) REFERENCES university.persons(person_id),
	FOREIGN KEY (course_id) REFERENCES university.courses(course_id)
);

CREATE TABLE IF NOT EXISTS university.courses
(
	course_id SERIAL PRIMARY KEY,
	course_name VARCHAR(30) NOT NULL,
	course_description VARCHAR(300) 
);

CREATE TABLE IF NOT EXISTS university.groups
(
	group_id SERIAL PRIMARY KEY,
	group_name VARCHAR(20) NOT NULL,
	student_id INT,
	FOREIGN KEY (student_id) REFERENCES university.students(student_id)
);

CREATE TABLE IF NOT EXISTS university.lessons
(
	lesson_id SERIAL PRIMARY KEY,
	lesson_date DATE,
	lesson_time TIME,
	teacher_id INT,
	course_id INT,
	group_id INT,
	FOREIGN KEY (teacher_id) REFERENCES university.teachers(teacher_id),
	FOREIGN KEY (course_id) REFERENCES university.courses(course_id),
	FOREIGN KEY (group_id) REFERENCES university.groups(group_id)
);

CREATE TABLE IF NOT EXISTS university.schedules
(
	schedule_id SERIAL PRIMARY KEY,
	schedule_date DATE,
	lesson_id INT,
	FOREIGN KEY (lesson_id) REFERENCES university.lessons(lesson_id)
);

CREATE TABLE IF NOT EXISTS university.students_courses
(
	student_id INT NOT NULL,
	course_id INT NOT NULL,
	FOREIGN KEY (student_id) REFERENCES university.students(student_id),
	FOREIGN KEY (course_id) REFERENCES university.courses(course_id),
	UNIQUE student_id, course_id
);
