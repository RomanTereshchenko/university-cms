CREATE SCHEMA IF NOT EXISTS university;

CREATE TABLE IF NOT EXISTS university.groups
(
	id SERIAL PRIMARY KEY,
	group_name VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS university.persons
(
	id SERIAL PRIMARY KEY,
    login VARCHAR(20) NOT NULL,
    password VARCHAR(20) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    role VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS university.students
(
	id SERIAL PRIMARY KEY,
	person_id INT,
	group_id INT,
	FOREIGN KEY (person_id) REFERENCES university.persons(id),
	FOREIGN KEY (group_id) REFERENCES university.groups(id)
);

CREATE TABLE IF NOT EXISTS university.teachers
(
  id SERIAL PRIMARY KEY,
	person_id INT,
	FOREIGN KEY (person_id) REFERENCES university.persons(id)
);

CREATE TABLE IF NOT EXISTS university.courses
(
	id SERIAL PRIMARY KEY,
	course_name VARCHAR(30) NOT NULL,
	course_description VARCHAR(300) 
);

CREATE TABLE IF NOT EXISTS university.lessons
(
	id SERIAL PRIMARY KEY,
	lesson_date DATE,
	lesson_time TIME,
	teacher_id INT,
	course_id INT,
	group_id INT,
	FOREIGN KEY (teacher_id) REFERENCES university.teachers(id),
	FOREIGN KEY (course_id) REFERENCES university.courses(id),
	FOREIGN KEY (group_id) REFERENCES university.groups(id)
);

CREATE TABLE IF NOT EXISTS university.groups_courses
(
	group_id INT NOT NULL,
	course_id INT NOT NULL,
	FOREIGN KEY (group_id) REFERENCES university.groups(id),
	FOREIGN KEY (course_id) REFERENCES university.courses(id),
	UNIQUE (group_id, course_id)
);

CREATE TABLE IF NOT EXISTS university.teachers_courses
(
	teacher_id INT NOT NULL,
	course_id INT NOT NULL,
	FOREIGN KEY (teacher_id) REFERENCES university.teachers(id),
	FOREIGN KEY (course_id) REFERENCES university.courses(id),
	UNIQUE (teacher_id, course_id)
);
