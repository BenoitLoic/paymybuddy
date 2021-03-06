-- -----------------------------------------------------
-- Clear all tables
-- -----------------------------------------------------
TRUNCATE `pay_my_buddy_test`.`user`;
TRUNCATE `pay_my_buddy_test`.`contact`;
TRUNCATE `pay_my_buddy_test`.`transaction`;

-- -----------------------------------------------------
-- populate DB
-- -----------------------------------------------------

-- -----------------------------------------------------
-- table user
-- -----------------------------------------------------
INSERT INTO pay_my_buddy_test.user (id, email, last_name, first_name, password, balance, phone, address_prefix,
                                    address_number, address_street, zip, city, account_creation, last_update)
VALUES (1, 'paymybuddy', 'XXX', 'XXX', 'XXXX', null, null, null, null, null, null, null, null, null);
INSERT INTO pay_my_buddy_test.user (id, email, last_name, first_name, password, balance, phone, address_prefix,
                                    address_number, address_street, zip, city, account_creation, last_update)
VALUES (2, 'frodo@mail.com', 'Baggins', 'Frodo', '$2a$10$qiVpUqQKa5Truu0j3QpRvuJVTpiTyWbyDGR.NvSG5hFBEH8N3hFaG', '5000',
        null, null, null, null, null, null, null, null);
INSERT INTO pay_my_buddy_test.user (id, email, last_name, first_name, password, balance, phone, address_prefix,
                                    address_number, address_street, zip, city, account_creation, last_update)
VALUES (3, 'sam@mail.com', 'Gamgee', 'Sam', '$2a$10$qiVpUqQKa5Truu0j3QpRvuJVTpiTyWbyDGR.NvSG5hFBEH8N3hFaG', '20', null,
        null, null, null, null, null, null, null);
INSERT INTO pay_my_buddy_test.user (id, email, last_name, first_name, password, balance, phone, address_prefix,
                                    address_number, address_street, zip, city, account_creation, last_update)
VALUES (4, 'peregrin@mail.com', 'Took', 'Pipin', '$2a$10$qiVpUqQKa5Truu0j3QpRvuJVTpiTyWbyDGR.NvSG5hFBEH8N3hFaG',
        '998.475', null, null, null, null, null, null, null, null);
INSERT INTO pay_my_buddy_test.user (id, email, last_name, first_name, password, balance, phone, address_prefix,
                                    address_number, address_street, zip, city, account_creation, last_update)
VALUES (5, 'merry@mail.com', 'Took', 'Merry', '$2a$10$qiVpUqQKa5Truu0j3QpRvuJVTpiTyWbyDGR.NvSG5hFBEH8N3hFaG', '1500.58',
        null, null, null, null, null, null, null, null);
INSERT INTO pay_my_buddy_test.user (id, email, last_name, first_name, password, balance, phone, address_prefix,
                                    address_number, address_street, zip, city, account_creation, last_update)
VALUES (8, 'bilbo@mail.com', 'Baggins', 'Bilbo', '$2a$10$qiVpUqQKa5Truu0j3QpRvuJVTpiTyWbyDGR.NvSG5hFBEH8N3hFaG',
        '500000', null, null, null, null, null, null, null, null);
INSERT INTO pay_my_buddy_test.user (id, email, last_name, first_name, password, balance, phone, address_prefix,
                                    address_number, address_street, zip, city, account_creation, last_update)
VALUES (7, 'smaug@mail.com', 'Ancient', 'Smaug', '$2a$10$qiVpUqQKa5Truu0j3QpRvuJVTpiTyWbyDGR.NvSG5hFBEH8N3hFaG',
        '999999999.999', null, null, null, null, null, null, null, null);

-- -----------------------------------------------------
-- table contact
-- -----------------------------------------------------
INSERT INTO `pay_my_buddy_test`.`contact` (`user_id`, `contact_id`)
VALUES ('2', '3');
INSERT INTO `pay_my_buddy_test`.`contact` (`user_id`, `contact_id`)
VALUES ('2', '4');
INSERT INTO `pay_my_buddy_test`.`contact` (`user_id`, `contact_id`)
VALUES ('2', '5');


-- -----------------------------------------------------
-- table transaction
-- -----------------------------------------------------
INSERT INTO `pay_my_buddy_test`.`transaction` (`id`, `user_debtor_id`, `user_creditor_id`, `description`, `amount`,
                                               `date`)
VALUES ('1', '2', '3', 'gazon', '20', '2021-07-19 11:59:00');
INSERT INTO `pay_my_buddy_test`.`transaction` (`id`, `user_debtor_id`, `user_creditor_id`, `description`, `amount`,
                                               `date`)
VALUES ('2', '2', '4', 'tabac', '50', '2021-07-19 12:59:00');
INSERT INTO `pay_my_buddy_test`.`transaction` (`id`, `user_debtor_id`, `user_creditor_id`, `description`, `amount`,
                                               `date`)
VALUES ('3', '8', '2', 'pr??paratif anniv', '10000', '2021-07-19 12:59:20');
INSERT INTO `pay_my_buddy_test`.`transaction` (`id`, `user_debtor_id`, `user_creditor_id`, `description`, `amount`,
                                               `date`)
VALUES ('4', '2', '3', 'fleurs', '40', '2021-07-19 14:39:50');

