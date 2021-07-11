-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema pay_my_buddy
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema pay_my_buddy
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `pay_my_buddy` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `pay_my_buddy` ;

-- -----------------------------------------------------
-- Table `pay_my_buddy`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pay_my_buddy`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(60) NOT NULL,
  `last_name` VARCHAR(50) NOT NULL,
  `first_name` VARCHAR(100) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `balance` DECIMAL NULL DEFAULT NULL,
  `phone` VARCHAR(12) NULL DEFAULT NULL,
  `address_prefix` VARCHAR(5) NULL DEFAULT NULL,
  `address_number` VARCHAR(6) NULL DEFAULT NULL,
  `address_street` VARCHAR(50) NULL DEFAULT NULL,
  `zip` VARCHAR(10) NULL DEFAULT NULL,
  `city` VARCHAR(50) NULL DEFAULT NULL,
  `account_creation` DATETIME NULL DEFAULT NULL,
  `last_update` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `pay_my_buddy`.`contact`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pay_my_buddy`.`contact` (
  `user_id` INT NOT NULL,
  `contact_id` INT NOT NULL,
  PRIMARY KEY (`user_id`, `contact_id`),
  INDEX `user_contact_fk` (`contact_id` ASC) VISIBLE,
  INDEX `user_user_fk` (`user_id` ASC) VISIBLE,
  CONSTRAINT `user_contact_fk`
    FOREIGN KEY (`contact_id`)
    REFERENCES `pay_my_buddy`.`user` (`id`),
  CONSTRAINT `user_user_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `pay_my_buddy`.`user` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_cs_0900_as_cs;


-- -----------------------------------------------------
-- Table `pay_my_buddy`.`transaction`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pay_my_buddy`.`transaction` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_debtor_id` INT NOT NULL,
  `user_creditor_id` INT NOT NULL,
  `description` VARCHAR(100) NULL DEFAULT NULL,
  `amount` DECIMAL NULL DEFAULT NULL,
  `date` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `user_transaction_fk` (`user_creditor_id` ASC) VISIBLE,
  INDEX `user_transaction_fk1` (`user_debtor_id` ASC) VISIBLE,
  CONSTRAINT `user_transaction_fk`
    FOREIGN KEY (`user_creditor_id`)
    REFERENCES `pay_my_buddy`.`user` (`id`),
  CONSTRAINT `user_transaction_fk1`
    FOREIGN KEY (`user_debtor_id`)
    REFERENCES `pay_my_buddy`.`user` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
