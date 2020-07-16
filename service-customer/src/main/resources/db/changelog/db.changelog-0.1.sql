--liquibase formatted sql

--changeset thinhnguyen:1
CREATE TABLE customer (id VARCHAR(32) NOT NULL,  PRIMARY KEY (id));
CREATE TABLE voucher (id INT NOT NULL AUTO_INCREMENT,customer VARCHAR(32) NOT NULL,voucher_code VARCHAR(45) NOT NULL,expired_date TIMESTAMP NOT NULL,PRIMARY KEY (id));

--changeset thinhnguyen:2
ALTER TABLE voucher ADD COLUMN completed TINYINT(1) NULL

--changeset thinhnguyen:3
ALTER TABLE customer ADD COLUMN secret_code VARCHAR(50) NULL;