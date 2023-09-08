DROP USER IF EXISTS 'mahfuz'@'%';
CREATE USER 'mahfuz'@'%' IDENTIFIED BY '2265';
CREATE DATABASE IF NOT EXISTS devops_db;
GRANT ALL PRIVILEGES ON devops_db.* TO 'mahfuz'@'%';
USE devops_db;

CREATE TABLE IF NOT EXISTS dev_ops_engineer (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255),
    location VARCHAR(255),
    PRIMARY KEY (id)
);

