 
CREATE DATABASE IF NOT EXISTS user_service_db;
USE user_service_db;
 
CREATE TABLE users (
    user_id CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role ENUM('student', 'teacher', 'admin') NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
 
CREATE INDEX idx_users_role ON users (role);
 
CREATE DATABASE IF NOT EXISTS class_service_db;
USE class_service_db;
 
CREATE TABLE classes (
    class_id CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    name VARCHAR(255) NOT NULL,
    description TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);
 
CREATE TABLE enrollments (
    enrollment_id CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    class_id CHAR(36) NOT NULL,
    user_id CHAR(36) NOT NULL, -- Reference to user_service_db.users.user_id
    role ENUM('student', 'teacher') NOT NULL,
    enrolled_at DATETIME DEFAULT CURRENT_TIMESTAMP
);
 
CREATE INDEX idx_enrollments_class_id ON enrollments (class_id);
CREATE INDEX idx_enrollments_user_id ON enrollments (user_id);
 
 
CREATE DATABASE IF NOT EXISTS homework_service_db;
USE homework_service_db;
 
 
CREATE TABLE homeworks (
    homework_id CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    title VARCHAR(255) NOT NULL,
    description TEXT,
    deadline DATETIME NOT NULL,
    class_id CHAR(36) NOT NULL, -- Reference to class_service_db.classes.class_id
    created_by CHAR(36) NOT NULL, -- Reference to user_service_db.users.user_id
    is_published BOOLEAN DEFAULT FALSE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
 
 
CREATE TABLE submissions (
    submission_id CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    homework_id CHAR(36) NOT NULL,
    student_id CHAR(36) NOT NULL, -- Reference to user_service_db.users.user_id
    content TEXT,
    attachment_url VARCHAR(512),
    submitted_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (homework_id) REFERENCES homeworks(homework_id)
);
 
 
CREATE INDEX idx_homeworks_class_id ON homeworks (class_id);
CREATE INDEX idx_submissions_student_id ON submissions (student_id);
 
 
CREATE DATABASE IF NOT EXISTS grading_service_db;
USE grading_service_db;
 
 
CREATE TABLE grades (
    grade_id CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    submission_id CHAR(36) UNIQUE NOT NULL, -- Reference to homework_service_db.submissions.submission_id
    score DECIMAL(5,2) CHECK (score >= 0 AND score <= 100),
    feedback TEXT,
    graded_by CHAR(36) NOT NULL, -- Reference to user_service_db.users.user_id
    graded_at DATETIME DEFAULT CURRENT_TIMESTAMP
);
 
 
CREATE INDEX idx_grades_submission_id ON grades (submission_id);