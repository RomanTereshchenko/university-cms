CREATE SCHEMA IF NOT EXISTS university;

CREATE TABLE IF NOT EXISTS university.users
(
	user_id SERIAL PRIMARY KEY,
    login VARCHAR(20) NOT NULL,
    password VARCHAR(20) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    role VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS university.courses
(
	course_id SERIAL PRIMARY KEY,
	course_name VARCHAR(30) NOT NULL
);

CREATE TABLE IF NOT EXISTS university.groups
(
	group_id SERIAL PRIMARY KEY,
	group_name VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS university.schedules
(
	schedule_id SERIAL PRIMARY KEY,
	start_date DATE,
	end_date DATE
);

CREATE TABLE IF NOT EXISTS university.lessons
(
	lesson_id SERIAL PRIMARY KEY,
	lesson_date DATE,
	start_time TIME
);

CREATE TABLE IF NOT EXISTS university.teachers_courses
(
	user_id INT NOT NULL,
	course_id INT NOT NULL,
	FOREIGN KEY (user_id) REFERENCES university.users(user_id),
	FOREIGN KEY (course_id) REFERENCES university.courses(course_id),
	UNIQUE (user_id, course_id)
);

CREATE TABLE IF NOT EXISTS university.groups_courses
(
	group_id INT NOT NULL,
	course_id INT NOT NULL,
	FOREIGN KEY (group_id) REFERENCES university.groups(group_id),
	FOREIGN KEY (course_id) REFERENCES university.courses(course_id),
	UNIQUE (group_id, course_id)
);

CREATE TABLE IF NOT EXISTS university.schedules_lessons
(
	schedule_id INT NOT NULL,
	lesson_id INT NOT NULL,
	FOREIGN KEY (schedule_id) REFERENCES university.schedules(schedule_id),
	FOREIGN KEY (lesson_id) REFERENCES university.lessons(lesson_id),
	UNIQUE (schedule_id, lesson_id)
);
