-- Drop existing tables if they exist
DROP TABLE IF EXISTS reward_redemptions;
DROP TABLE IF EXISTS rewards;
DROP TABLE IF EXISTS game_progress;
DROP TABLE IF EXISTS assessments;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;

-- Table for roles
CREATE TABLE roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL COMMENT 'Role name (e.g., GENERAL, PATIENT)'
);

-- Table for users
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL COMMENT 'Username',
    password VARCHAR(255) NOT NULL COMMENT 'Password',
    role_id BIGINT NOT NULL COMMENT 'Foreign key for role',
    access_code VARCHAR(10) DEFAULT NULL COMMENT 'Short access code for patients',
    fear_level INT DEFAULT 0 COMMENT 'Fear level',
    fear_percentage DOUBLE DEFAULT NULL COMMENT 'Fear percentage as a decimal value',
    coins INT DEFAULT 0 COMMENT 'Coins for rewards',
    is_doctor BOOLEAN DEFAULT FALSE NOT NULL COMMENT 'Indicates if the user is a doctor',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Account creation date',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Last update',
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);


-- Table for assessments
CREATE TABLE assessments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL COMMENT 'Foreign key to user',
    score INT COMMENT 'Assessment score',
    fear_percentage DOUBLE COMMENT 'Fear percentage',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Assessment date',
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Table for game progress
CREATE TABLE game_progress (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL COMMENT 'Foreign key to user',
    animal_type VARCHAR(50) COMMENT 'Animal type the user interacts with',
    current_level INT DEFAULT 1 COMMENT 'Current level in game',
    completed BOOLEAN DEFAULT FALSE COMMENT 'Game completion status',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Last updated',
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Last update timestamp',
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Table for rewards
CREATE TABLE rewards (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL COMMENT 'Reward name',
    description TEXT NOT NULL COMMENT 'Reward description',
    coin_cost INT NOT NULL COMMENT 'Coin cost for the reward',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Reward creation date',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Last updated'
);

-- Table for reward redemptions
CREATE TABLE reward_redemptions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL COMMENT 'Foreign key to user',
    reward_id BIGINT NOT NULL COMMENT 'Foreign key to reward',
    redeemed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Redemption date',
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (reward_id) REFERENCES rewards(id) ON DELETE CASCADE
);
