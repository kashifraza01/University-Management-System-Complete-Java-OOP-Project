-- Drop and recreate the database
DROP DATABASE IF EXISTS universitymanagementsystem;
CREATE DATABASE universitymanagementsystem;
USE universitymanagementsystem;

-- Login Table
CREATE TABLE login (
	username VARCHAR(25) NOT NULL PRIMARY KEY,
	password VARCHAR(255) NOT NULL
);

INSERT INTO login VALUES ('admin', '12345');

-- Degree & Campus Tables
CREATE TABLE degree (
	degree_name VARCHAR(50) PRIMARY KEY
);

CREATE TABLE campus (
	campus_name VARCHAR(100) PRIMARY KEY
);

-- Student Table
CREATE TABLE student (
	name VARCHAR(40) NOT NULL,
	fname VARCHAR(40) NOT NULL,
	rollno VARCHAR(20) PRIMARY KEY,
	current_semester VARCHAR(20),
	dob DATE NOT NULL,
	address VARCHAR(100),
	phone VARCHAR(20) NOT NULL UNIQUE,
	email VARCHAR(40) UNIQUE,
	class_x VARCHAR(20),
	class_xii VARCHAR(20),
	nic VARCHAR(20),
	degree VARCHAR(50) NOT NULL,
	campus VARCHAR(100),
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	FOREIGN KEY (degree) REFERENCES degree(degree_name),
	FOREIGN KEY (campus) REFERENCES campus(campus_name)
);

-- Teacher Table
CREATE TABLE teacher (
	name VARCHAR(40) NOT NULL,
	fname VARCHAR(40) NOT NULL,
	empId VARCHAR(20) PRIMARY KEY,
	dob DATE NOT NULL,
	address VARCHAR(100),
	phone VARCHAR(20) UNIQUE,
	email VARCHAR(40) UNIQUE,
	education VARCHAR(40),
	gpa VARCHAR(40),
	nic VARCHAR(20),
	campus VARCHAR(100),
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Subject Table
CREATE TABLE subject (
	id INT AUTO_INCREMENT PRIMARY KEY,
	template_id VARCHAR(20),
	semester VARCHAR(20),
	subject1 VARCHAR(50),
	subject2 VARCHAR(50),
	subject3 VARCHAR(50),
	subject4 VARCHAR(50),
	subject5 VARCHAR(50),
	UNIQUE(template_id, semester)
);

-- Marks Table
CREATE TABLE marks (
	rollno VARCHAR(20),
	semester VARCHAR(20),
	subject1 VARCHAR(100),
	subject2 VARCHAR(100),
	subject3 VARCHAR(100),
	subject4 VARCHAR(100),
	subject5 VARCHAR(100),
	marks1 INT DEFAULT 0,
	marks2 INT DEFAULT 0,
	marks3 INT DEFAULT 0,
	marks4 INT DEFAULT 0,
	marks5 INT DEFAULT 0,
	PRIMARY KEY (rollno, semester),
	FOREIGN KEY (rollno) REFERENCES student(rollno) ON DELETE CASCADE
);

-- Fee Structure Table
CREATE TABLE fee (
	courses VARCHAR(20) PRIMARY KEY,
	semester1 INT DEFAULT 0,
	semester2 INT DEFAULT 0,
	semester3 INT DEFAULT 0,
	semester4 INT DEFAULT 0,
	semester5 INT DEFAULT 0,
	semester6 INT DEFAULT 0,
	semester7 INT DEFAULT 0,
	semester8 INT DEFAULT 0
);

-- College Fee Table
CREATE TABLE collegefee (
	id INT AUTO_INCREMENT PRIMARY KEY,
	rollno VARCHAR(20),
	degree VARCHAR(20),
	campus VARCHAR(100),
	semester VARCHAR(20),
	installment VARCHAR(20),
	total INT,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	FOREIGN KEY (rollno) REFERENCES student(rollno) ON DELETE CASCADE
);

-- Indexes
CREATE INDEX idx_marks_rollno_semester ON marks(rollno, semester);
CREATE INDEX idx_subject_template_id ON subject(template_id);
CREATE INDEX idx_fee_courses ON fee(courses);

-- Insert Degrees & Campuses
INSERT INTO degree VALUES ('CS'), ('BBA'), ('ADB'), ('SE');
INSERT INTO campus VALUES ('Airport Campus'), ('Main Campus'), ('North Campus');

-- Subject Templates
-- CS
INSERT INTO subject (template_id, semester, subject1, subject2, subject3, subject4, subject5) VALUES
('CS', 'Semester 1', 'Calculus', 'Programming Fundamentals', 'Intro to Computing', 'Applied Physics', 'English Composition'),
('CS', 'Semester 2', 'Object Oriented Programming', 'Probability & Statistics', 'Digital Logic Design', 'Pak Studies', 'Communication Skills'),
('CS', 'Semester 3', 'Data Structures', 'Discrete Mathematics', 'Computer Organization', 'HCI', 'Graphics'),
('CS', 'Semester 4', 'Database Systems', 'Automata Theory', 'Linear Algebra', 'Algorithm Design', 'Computer Architecture'),
('CS', 'Semester 5', 'Operating Systems', 'Compiler Construction', 'Software Engineering', 'Advanced Databases', 'Entrepreneurship'),
('CS', 'Semester 6', 'Technical Writing', 'Computer Networks', 'AI', 'CS Elective 1', 'CS Elective 2'),
('CS', 'Semester 7', 'FYP Part 1', 'CS Elective 3', 'Information Security', 'Professional Practice', 'University Elective'),
('CS', 'Semester 8', 'FYP Part 2', 'Parallel Computing', 'CS Elective 4', 'CS Elective 5', 'CS Elective 6');

-- BBA
INSERT INTO subject (template_id, semester, subject1, subject2, subject3, subject4, subject5) VALUES
('BBA', 'Semester 1', 'Business English', 'Basic Mathematics', 'Human Behavior', 'Principles of Management', 'Principles of Accounting'),
('BBA', 'Semester 2', 'Business Communication', 'Financial Accounting', 'Principles of Marketing', 'Calculus', 'Microeconomics'),
('BBA', 'Semester 3', 'Marketing Management', 'Cost Accounting', 'Macroeconomics', 'Statistics', 'Community Development'),
('BBA', 'Semester 4', 'Business Finance', 'Development Economics', 'Supply Chain Management', 'HRM', 'Pakistan Studies'),
('BBA', 'Semester 5', 'Financial Management', 'Strategic Management', 'Business Research Methods', 'HRM', 'Business Ethics'),
('BBA', 'Semester 6', 'Decision Making', 'Special Communication', 'E-Business', 'International Business', 'Corporate Planning'),
('BBA', 'Semester 7', 'Global Marketing', 'Brand Marketing', 'Digital Marketing', 'Social Entrepreneurship', 'Leadership'),
('BBA', 'Semester 8', 'Final Year Project', 'Internship', 'Case Studies', 'Strategic HRM', 'Finance Project');

-- SE
INSERT INTO subject (template_id, semester, subject1, subject2, subject3, subject4, subject5) VALUES
('SE', 'Semester 1', 'Intro to Programming', 'Calculus I', 'Communication Skills', 'Applied Physics', 'Discrete Mathematics'),
('SE', 'Semester 2', 'Object Oriented Programming', 'Digital Logic Design', 'Linear Algebra', 'Probability & Statistics', 'Pak Studies'),
('SE', 'Semester 3', 'Data Structures & Algorithms', 'Software Requirements Engineering', 'Computer Architecture', 'Database Systems', 'Operating Systems'),
('SE', 'Semester 4', 'Software Design & Architecture', 'Web Technologies', 'Human Computer Interaction', 'SE Economics', 'Compiler Design'),
('SE', 'Semester 5', 'Software Quality Engineering', 'Mobile App Development', 'Software Project Management', 'AI', 'Cloud Computing'),
('SE', 'Semester 6', 'Cyber Security', 'DevOps', 'Advanced Databases', 'Information Retrieval', 'Design Patterns'),
('SE', 'Semester 7', 'FYP Part 1', 'Blockchain Fundamentals', 'Software Metrics', 'Agile Methodology', 'System Simulation'),
('SE', 'Semester 8', 'FYP Part 2', 'Ethics in IT', 'Software Reuse', 'CS Elective', 'Capstone Project');

-- ADB
INSERT INTO subject (template_id, semester, subject1, subject2, subject3, subject4, subject5) VALUES
('ADB', 'Semester 1', 'Business English', 'Fundamentals of Business', 'Intro to Accounting', 'IT for Business', 'Business Mathematics'),
('ADB', 'Semester 2', 'Organizational Behavior', 'Marketing Principles', 'Financial Accounting', 'E-Commerce', 'Microeconomics'),
('ADB', 'Semester 3', 'Business Communication', 'HRM', 'Business Law', 'Macroeconomics', 'Management Information Systems'),
('ADB', 'Semester 4', 'Strategic Management', 'Entrepreneurship', 'Operations Management', 'Digital Marketing', 'Capstone Project');

-- Fee Table
INSERT INTO fee (courses, semester1, semester2, semester3, semester4, semester5, semester6, semester7, semester8) VALUES 
('CS', 120000, 110000, 110000, 110000, 110000, 110000, 110000, 110000),
('BBA', 48000, 43000, 43000, 43000, 43000, 43000, 43000, 43000),
('SE', 187500, 150000, 150000, 150000, 150000, 150000, 150000, 150000),
('ADB', 40000, 35000, 35000, 35000, 0, 0, 0, 0);

ALTER TABLE marks
ADD COLUMN gpa DOUBLE DEFAULT 0,
ADD COLUMN cgpa DOUBLE DEFAULT 0;
