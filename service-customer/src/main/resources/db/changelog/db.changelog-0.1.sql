--liquibase formatted sql

--changeset thinhnguyen:1
CREATE TABLE service_customer.customer (id VARCHAR(32) NOT NULL,  PRIMARY KEY (id));
CREATE TABLE service_customer.voucher (id INT NOT NULL AUTO_INCREMENT,customer VARCHAR(32) NOT NULL,voucher_code VARCHAR(45) NOT NULL,expired_date TIMESTAMP NOT NULL,PRIMARY KEY (id));

--changeset thinhnguyen:2
ALTER TABLE service_customer.voucher ADD COLUMN completed TINYINT(1) NULL