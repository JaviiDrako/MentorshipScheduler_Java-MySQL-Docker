
CREATE TABLE Users (
    user_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(50) NOT NULL,
    role ENUM('Student', 'Tutor') NOT NULL,
    password VARCHAR(50) NOT NULL
);

CREATE TABLE Students (
    student_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    term INT,
    phone_number VARCHAR(50),
    name VARCHAR(50),
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES Users(user_id)
);

CREATE TABLE Tutors (
    tutor_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50),
    phone_number VARCHAR(50),
    area VARCHAR(50),
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES Users(user_id)
);

CREATE TABLE Subjects (
    subject_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50),
    term INT
);

CREATE TABLE TutorsSubjects (
	subject_id INT NOT NULL,
	tutor_id INT NOT NULL,
	PRIMARY KEY (subject_id, tutor_id),
	FOREIGN KEY (subject_id) REFERENCES Subjects(subject_id),
	FOREIGN KEY (tutor_id) REFERENCES Tutors(tutor_id)
);


CREATE TABLE Mentorships (
    mentorship_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    state VARCHAR(20) DEFAULT 'Available',
    date_time DATETIME,
    student_id INT NULL,
    tutor_id INT,
    subject_id INT,
    FOREIGN KEY (student_id) REFERENCES Students(student_id),
    FOREIGN KEY (tutor_id) REFERENCES Tutors(tutor_id),
    FOREIGN KEY (subject_id) REFERENCES Subjects(subject_id)
);


-- DATA

-- USERS
INSERT INTO Users (email, role, password) VALUES
('alice.smith@student.edu', 'Student', 'passAlice1'),
('bob.jones@student.edu', 'Student', 'passBob2'),
('carla.martin@student.edu', 'Student', 'passCarla3'),
('daniel.lee@student.edu', 'Student', 'passDaniel4'),
('emily.torres@student.edu', 'Student', 'passEmily5'),
('frank.harris@student.edu', 'Student', 'passFrank6'),
('grace.wilson@student.edu', 'Student', 'passGrace7'),
('hugo.brown@student.edu', 'Student', 'passHugo8'),
('irene.diaz@student.edu', 'Student', 'passIrene9'),
('john.carter@student.edu', 'Student', 'passJohn10'),
('lucas.mendez@tutor.edu', 'Tutor', 'passLucas11'),
('maria.gomez@tutor.edu', 'Tutor', 'passMaria12'),
('natalia.perez@tutor.edu', 'Tutor', 'passNatalia13'),
('oscar.ruiz@tutor.edu', 'Tutor', 'passOscar14'),
('paula.santos@tutor.edu', 'Tutor', 'passPaula15'),
('sofia.ramirez@tutor.edu', 'Tutor', 'passSofia16'),
('carlos.fernandez@tutor.edu', 'Tutor', 'passCarlos17'),
('laura.mendoza@tutor.edu', 'Tutor', 'passLaura18'),
('diego.morales@tutor.edu', 'Tutor', 'passDiego19'),
('valeria.castillo@tutor.edu', 'Tutor', 'passValeria20');

-- STUDENTS (userID 1-10)
INSERT INTO Students (term, phone_number, name, user_id) VALUES
(1, '555-1001', 'Alice Smith', 1),
(2, '555-1002', 'Bob Jones', 2),
(1, '555-1003', 'Carla Martin', 3),
(3, '555-1004', 'Daniel Lee', 4),
(2, '555-1005', 'Emily Torres', 5),
(1, '555-1006', 'Frank Harris', 6),
(2, '555-1007', 'Grace Wilson', 7),
(3, '555-1008', 'Hugo Brown', 8),
(1, '555-1009', 'Irene Diaz', 9),
(2, '555-1010', 'John Carter', 10);

-- TUTORS (userID 11-15)
INSERT INTO Tutors (name, phone_number, area, user_id) VALUES
('Lucas Mendez', '555-2001', 'Mathematics', 11),
('Maria Gomez', '555-2002', 'Physics', 12),
('Natalia Perez', '555-2003', 'Computer Science', 13),
('Oscar Ruiz', '555-2004', 'Chemistry', 14),
('Paula Santos', '555-2005', 'Biology', 15),
('Sofia Ramirez', '555-2006', 'Mathematics', 16),
('Carlos Fernandez', '555-2007', 'Physics', 17),
('Laura Mendoza', '555-2008', 'Computer Science', 18),
('Diego Morales', '555-2009', 'Chemistry', 19),
('Valeria Castillo', '555-2010', 'Biology', 20);

-- SUBJECTS
INSERT INTO Subjects (name, term) VALUES
('Calculus I', 1),
('Calculus II', 2),
('Physics I', 1),
('Physics II', 2),
('Chemistry', 1),
('Operating Systems', 2),
('Linear Algebra', 1),
('Programming', 1),
('Biology', 1),
('Genetics', 2),
('Discrete Mathematics', 1),
('Data Base', 2),
('Algorithms', 2);

-- TUTORS AND SUBJECTS
INSERT INTO TutorsSubjects (subject_id, tutor_id) VALUES
(1, 1),  -- Lucas Mendez - Calculus I
(2, 1),  -- Lucas Mendez - Calculus II
(7, 1),  -- Lucas Mendez - Linear Algebra
(11, 1), -- Lucas Mendez - Discrete Mathematics

(3, 2),  -- Maria Gomez - Physics I
(4, 2),  -- Maria Gomez - Physics II

(6, 3),  -- Natalia Perez - Operating Systems
(8, 3),  -- Natalia Perez - Programming
(13, 3), -- Natalia Perez - Algorithms

(5, 4),  -- Oscar Ruiz - Chemistry

(9, 5),  -- Paula Santos - Biology
(10, 5), -- Paula Santos - Genetics

(1, 6), -- Sofia Ramirez - Calculus I
(11, 6), -- Sofia Ramirez - Discrete Mathematics
(7, 6), -- Sofia Ramirez - Linear Algebra

(3, 7), -- Carlos Fernandez - Physics I
(4, 7), -- Carlos Fernandez - Physics II

(8, 8), -- Laura Mendoza - Programming
(12, 8), -- Laura Mendoza - Operating Systems

(5, 9), -- Diego Morales - Chemistry
(6, 9), -- Diego Morales - Operating Systems

(9, 10),  -- Valeria Castillo - Biology
(10, 10); -- Valeria Castillo - Genetics

-- MENTORSHIPS
INSERT INTO Mentorships (state, date_time, student_id, tutor_id, subject_id) VALUES
-- Mentorships for Lucas Mendez 
('Scheduled', '2025-06-11 09:00:00', 1, 1, 1),
('Available', '2025-06-11 10:00:00', NULL, 1, 1),
('Available', '2025-06-12 09:00:00', NULL, 1, 2),
('Available', '2025-06-12 10:00:00', NULL, 1, 2),
('Available', '2025-06-13 09:00:00', NULL, 1, 7),
('Available', '2025-06-13 10:00:00', NULL, 1, 7),
('Available', '2025-06-14 09:00:00', NULL, 1, 11),
('Available', '2025-06-14 10:00:00', NULL, 1, 11),

-- Mentorships for Maria Gomez 
('Scheduled', '2025-06-11 11:00:00', 2, 2, 3),
('Available', '2025-06-11 12:00:00', NULL, 2, 3),
('Available', '2025-06-12 11:00:00', NULL, 2, 4),
('Available', '2025-06-12 12:00:00', NULL, 2, 4),

-- Mentorships for Natalia Perez 
('Available', '2025-06-13 11:00:00', NULL, 3, 6),
('Available', '2025-06-13 12:00:00', NULL, 3, 6),
('Booked', '2025-06-14 11:00:00', 3, 3, 8),
('Available', '2025-06-14 12:00:00', NULL, 3, 8),
('Available', '2025-06-15 11:00:00', NULL, 3, 13),
('Available', '2025-06-15 12:00:00', NULL, 3, 13),

-- Mentorships for Oscar Ruiz 
('Available', '2025-06-11 13:00:00', NULL, 4, 5),
('Available', '2025-06-12 13:00:00', NULL, 4, 5),
('Available', '2025-06-13 13:00:00', NULL, 4, 5),

-- Mentorships for Paula Santos 
('Scheduled', '2025-06-11 14:00:00', 1, 5, 9),
('Available', '2025-06-11 15:00:00', NULL, 5, 9),
('Available', '2025-06-12 14:00:00', NULL, 5, 10),
('Available', '2025-06-12 15:00:00', NULL, 5, 10),

-- Mentorships for Sofia Ramirez 
('Available', '2025-06-13 13:00:00', NULL, 6, 1),
('Available', '2025-06-13 14:00:00', NULL, 6, 1),
('Available', '2025-06-14 13:00:00', NULL, 6, 11),
('Available', '2025-06-14 14:00:00', NULL, 6, 11),
('Available', '2025-06-15 13:00:00', NULL, 6, 7),
('Available', '2025-06-15 14:00:00', NULL, 6, 7),

-- Mentorships for Carlos Fernandez 
('Available', '2025-06-16 09:00:00', NULL, 7, 3),
('Available', '2025-06-16 10:00:00', NULL, 7, 3),
('Available', '2025-06-17 09:00:00', NULL, 7, 4),
('Available', '2025-06-17 10:00:00', NULL, 7, 4),

-- Mentorships for Laura Mendoza 
('Available', '2025-06-16 11:00:00', NULL, 8, 8),
('Available', '2025-06-16 12:00:00', NULL, 8, 8),
('Available', '2025-06-17 11:00:00', NULL, 8, 12),
('Available', '2025-06-17 12:00:00', NULL, 8, 12),

-- Mentorships for Diego Morales 
('Scheduled', '2025-06-18 09:00:00', 1, 9, 5),
('Available', '2025-06-18 10:00:00', NULL, 9, 5),
('Available', '2025-06-19 09:00:00', NULL, 9, 6),
('Available', '2025-06-19 10:00:00', NULL, 9, 6),

-- Mentorships for Valeria Castillo 
('Available', '2025-06-18 11:00:00', NULL, 10, 9),
('Available', '2025-06-18 12:00:00', NULL, 10, 9),
('Scheduled', '2025-06-19 11:00:00', 2, 10, 10),
('Available', '2025-06-19 12:00:00', NULL, 10, 10);


