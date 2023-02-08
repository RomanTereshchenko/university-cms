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
	student_id INT PRIMARY KEY,
	group_id INT,
	FOREIGN KEY (student_id) REFERENCES university.persons(person_id),
	FOREIGN KEY (group_id) REFERENCES university.groups(group_id)
);

CREATE TABLE IF NOT EXISTS university.teachers
(
	teacher_id INT PRIMARY KEY,
	FOREIGN KEY (teacher_id) REFERENCES university.persons(person_id)
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
	group_name VARCHAR(20) NOT NULL
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

CREATE TABLE IF NOT EXISTS university.groups_courses
(
	group_id INT NOT NULL,
	course_id INT NOT NULL,
	FOREIGN KEY (group_id) REFERENCES university.groups(group_id),
	FOREIGN KEY (course_id) REFERENCES university.courses(course_id),
	UNIQUE group_id, course_id
);

CREATE TABLE IF NOT EXISTS university.teachers_courses
(
	teacher_id INT NOT NULL,
	course_id INT NOT NULL,
	FOREIGN KEY (teacher_id) REFERENCES university.teachers(teacher_id),
	FOREIGN KEY (course_id) REFERENCES university.courses(course_id),
	UNIQUE teacher_id, course_id
);
