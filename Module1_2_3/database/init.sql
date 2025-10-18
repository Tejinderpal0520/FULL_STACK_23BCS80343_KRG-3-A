-- Form Management System Database Initialization
-- This script creates the database and initial tables

CREATE DATABASE IF NOT EXISTS form_management;
USE form_management;

-- Users table
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role ENUM('USER', 'ADMIN') NOT NULL DEFAULT 'USER',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Forms table
CREATE TABLE IF NOT EXISTS forms (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    creator_id BIGINT NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    is_public BOOLEAN DEFAULT TRUE,
    submission_limit INT,
    allow_duplicate BOOLEAN DEFAULT TRUE,
    require_login BOOLEAN DEFAULT FALSE,
    expires_at TIMESTAMP NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (creator_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Form fields table
CREATE TABLE IF NOT EXISTS form_fields (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    form_id BIGINT NOT NULL,
    label VARCHAR(255) NOT NULL,
    field_type ENUM('TEXT', 'TEXTAREA', 'EMAIL', 'NUMBER', 'DATE', 'RADIO', 'CHECKBOX', 'DROPDOWN', 'FILE') NOT NULL,
    is_required BOOLEAN DEFAULT FALSE,
    field_order INT NOT NULL,
    placeholder VARCHAR(255),
    help_text TEXT,
    options TEXT,
    validation_rules TEXT,
    FOREIGN KEY (form_id) REFERENCES forms(id) ON DELETE CASCADE
);

-- Responses table
CREATE TABLE IF NOT EXISTS responses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    form_id BIGINT NOT NULL,
    user_id BIGINT NULL,
    respondent_email VARCHAR(100),
    respondent_name VARCHAR(100),
    ip_address VARCHAR(45),
    user_agent TEXT,
    submitted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_duplicate BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (form_id) REFERENCES forms(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL
);

-- Response entries table
CREATE TABLE IF NOT EXISTS response_entries (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    response_id BIGINT NOT NULL,
    form_field_id BIGINT NOT NULL,
    field_value TEXT,
    FOREIGN KEY (response_id) REFERENCES responses(id) ON DELETE CASCADE,
    FOREIGN KEY (form_field_id) REFERENCES form_fields(id) ON DELETE CASCADE
);

-- Indexes for better performance
CREATE INDEX idx_forms_creator ON forms(creator_id);
CREATE INDEX idx_forms_active ON forms(is_active);
CREATE INDEX idx_forms_public ON forms(is_public);
CREATE INDEX idx_form_fields_form ON form_fields(form_id);
CREATE INDEX idx_responses_form ON responses(form_id);
CREATE INDEX idx_responses_user ON responses(user_id);
CREATE INDEX idx_response_entries_response ON response_entries(response_id);
CREATE INDEX idx_response_entries_field ON response_entries(form_field_id);

-- Insert sample admin user (password: admin123)
INSERT INTO users (username, email, password, role) VALUES 
('admin', 'admin@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'ADMIN')
ON DUPLICATE KEY UPDATE username=username;
