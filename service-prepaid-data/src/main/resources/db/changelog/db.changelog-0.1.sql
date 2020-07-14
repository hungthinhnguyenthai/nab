--liquibase formatted sql

--changeset thinhnguyen:1
CREATE TABLE service_prepaid_data.voucher (id INT NOT NULL AUTO_INCREMENT,customer VARCHAR(32) NOT NULL,voucher_code VARCHAR(45) NOT NULL,expired_date TIMESTAMP NOT NULL,PRIMARY KEY (id));