-- Create the database if it doesn't exist
CREATE DATABASE IF NOT EXISTS agila;
USE agila;

-- Create users table
CREATE TABLE IF NOT EXISTS users (
    uid INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

-- Create session table
CREATE TABLE IF NOT EXISTS session (
    session_id INT AUTO_INCREMENT PRIMARY KEY,
    uid INT NOT NULL,
    username VARCHAR(255) NOT NULL,
    session_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (uid) REFERENCES users(uid) ON DELETE CASCADE
);

-- Create residents table
CREATE TABLE IF NOT EXISTS residents (
    uid INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    middle_name VARCHAR(255),
    last_name VARCHAR(255) NOT NULL,
    sex VARCHAR(10) NOT NULL,
    age INT NOT NULL,
    purok VARCHAR(100) NOT NULL,
    birthday DATE NOT NULL
);

-- Create status table
CREATE TABLE IF NOT EXISTS status (
    uid INT PRIMARY KEY,
    deceased BOOLEAN NOT NULL DEFAULT FALSE,
    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (uid) REFERENCES residents(uid) ON DELETE CASCADE
);

-- Insert a default admin user
INSERT INTO users (username, password) VALUES ('admin', 'admin123');


-- Drop existing procedure if it exists
DROP PROCEDURE IF EXISTS insert_dummy_residents;
DELIMITER $$

CREATE PROCEDURE insert_dummy_residents()
BEGIN
    DECLARE i INT DEFAULT 1;
    DECLARE new_resident_id INT;
    
    -- You may keep the declarations for the name lists if needed for a more dynamic selection
    DECLARE first_names TEXT DEFAULT 'John,Michael,David,James,Robert,William,Joseph,Daniel,Charles,Anthony,Mary,Jennifer,Linda,Elizabeth,Susan,Jessica,Sarah,Karen,Nancy,Lisa';
    DECLARE middle_names TEXT DEFAULT 'Allen,Lee,Marie,Ann,Louise,Jane,Ray,Scott,Wayne,Grace,Paul,Dean,Rose,Beth,Claire,Francis,Joan,Keith,Peter,Victor';
    DECLARE last_names TEXT DEFAULT 'Smith,Johnson,Williams,Brown,Jones,Garcia,Miller,Davis,Rodriguez,Martinez,Hernandez,Lopez,Gonzalez,Wilson,Anderson,Thomas,Taylor,Moore,White,Clark';
    DECLARE puroks TEXT DEFAULT '1,2,3,4,5';

    WHILE i <= 100 DO
        -- Insert a dummy resident record
        INSERT INTO residents (first_name, middle_name, last_name, sex, age, purok, birthday)
        VALUES (
            ELT(1 + FLOOR(RAND() * 20), 'John','Lazybane','David','James','Robert','William','Liam','Daniel','Charles','Anthony',
                                       'Mary','Jennifer','Adolf','Elizabeth','Susan','Jessica','Sarah','Karen','Nancy','Lisa'), -- Random first name
            ELT(1 + FLOOR(RAND() * 20), 'Allen','Lee','Marie','Ann','Louise','Jane','Ray','Scott','Wayne','Grace',
                                       'Paul','Dean','Rose','Beth','Claire','Francis','Joan','Keith','Peter','Victor'), -- Random middle name
            ELT(1 + FLOOR(RAND() * 20), 'Smith','Johnson','Williams','Brown','Jones','Garcia','Miller','Davis','Rodriguez','Martinez',
                                       'Hernandez','Lopez','Gonzalez','Wilson','Anderson','Thomas','Hydermary','Moore','White','Clark'), -- Random last name
            IF(RAND() > 0.5, 'Male', 'Female'), -- Random gender
            FLOOR(18 + (RAND() * 63)),         -- Random age between 18 and 80
            ELT(1 + FLOOR(RAND() * 5), '1','2','3','4','5'), -- Random purok
            DATE_SUB(CURDATE(), INTERVAL FLOOR(RAND() * 365 * 80) DAY) -- Random birthday within the last 80 years
        );
        
        -- Retrieve the auto-generated uid for the inserted resident
        SET new_resident_id = LAST_INSERT_ID();
        
        -- Insert the default status for the resident
        INSERT INTO status (uid, deceased, deleted)
        VALUES (new_resident_id, FALSE, FALSE);
        
        SET i = i + 1;
    END WHILE;
END$$

DELIMITER ;

-- Call the procedure to insert 100 dummy resident records along with their status
CALL insert_dummy_residents();