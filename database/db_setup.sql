DROP DATABASE IF EXISTS network;
CREATE DATABASE network;
USE network;

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET GLOBAL time_zone = '+02:00';

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    registered_at TIMESTAMP NOT NULL DEFAULT NOW()
)
ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE _groups (
    gid BIGINT AUTO_INCREMENT PRIMARY KEY,
    group_name VARCHAR(255) UNIQUE NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
)
ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE sites (
    sid BIGINT AUTO_INCREMENT PRIMARY KEY,
    site_name VARCHAR(255) NOT NULL UNIQUE,
    location POINT NOT NULL
)
ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE posts (
    pid BIGINT AUTO_INCREMENT PRIMARY KEY,
    post VARCHAR(255) NOT NULL,
    category VARCHAR(255),
    location POINT,
    group_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    posted_at TIMESTAMP NOT NULL DEFAULT NOW(),
    FOREIGN KEY(group_id) REFERENCES _groups(gid),
    FOREIGN KEY(user_id) REFERENCES users(id)
)
ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE comments (
    cid BIGINT AUTO_INCREMENT PRIMARY KEY,
    comment_text VARCHAR(255) NOT NULL,
    user_id BIGINT NOT NULL,
    post_id BIGINT NOT NULL,
    made_at TIMESTAMP NOT NULL DEFAULT NOW(),
    FOREIGN KEY(user_id) REFERENCES users(id),
    FOREIGN KEY(post_id) REFERENCES posts(pid)
)
ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE members_info (
    member_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    group_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    admin_rights BOOLEAN NOT NULL,
    joined_at TIMESTAMP NOT NULL DEFAULT NOW(),
    FOREIGN KEY(group_id) REFERENCES _groups(gid),
    FOREIGN KEY(user_id) REFERENCES users(id)
)
ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE messages (
    mid BIGINT AUTO_INCREMENT PRIMARY KEY,
    message VARCHAR(255) NOT NULL,
    sender BIGINT NOT NULL,
    sent_at TIMESTAMP NOT NULL DEFAULT NOW(),
    FOREIGN KEY(sender) REFERENCES users(id)
)
ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE recipient_info (
    rid BIGINT AUTO_INCREMENT PRIMARY KEY,
    message_id BIGINT NOT NULL,
    recipient BIGINT NOT NULL,
    FOREIGN KEY(message_id) REFERENCES messages(mid),
    FOREIGN KEY(recipient) REFERENCES users(id)
)
ENGINE=InnoDB DEFAULT CHARSET=latin1;
