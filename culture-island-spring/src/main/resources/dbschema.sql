-- MySQL Script generated by MySQL Workbench
-- Thu May 16 10:04:43 2024
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`member`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`member` ;

CREATE TABLE IF NOT EXISTS `mydb`.`member` (
                                               `id` INT NOT NULL AUTO_INCREMENT,
                                               `email` VARCHAR(100) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    `name` VARCHAR(45) NOT NULL,
    `number` VARCHAR(20) NOT NULL,
    `gender` ENUM('M', 'F', 'O') NULL,
    `birth` DATE NULL,
    `rank` ENUM('0', '1', '2', '3') NOT NULL DEFAULT '1',
    `imglink` VARCHAR(255) NULL,
    `intro` TEXT NULL,
    PRIMARY KEY (`id`))
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`article`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`article` ;

CREATE TABLE IF NOT EXISTS `mydb`.`article` (
                                                `id` INT NOT NULL,
                                                `subject` VARCHAR(100) NOT NULL,
    `content` TEXT NOT NULL,
    `hit` INT NULL,
    `register_time` DATETIME NULL,
    `member_id` INT NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `member_id_idx` (`member_id` ASC) VISIBLE,
    CONSTRAINT `member_id`
    FOREIGN KEY (`member_id`)
    REFERENCES `mydb`.`member` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`comments`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`comments` ;

CREATE TABLE IF NOT EXISTS `mydb`.`comments` (
                                                 `id` INT NOT NULL,
                                                 `parent_id` INT NULL,
                                                 `depth` INT NULL,
                                                 `comment` TEXT NOT NULL,
                                                 `register_time` DATETIME NULL,
                                                 `article_id` INT NOT NULL,
                                                 `member_id` INT NOT NULL,
                                                 PRIMARY KEY (`id`),
    INDEX `article_id_idx` (`article_id` ASC) VISIBLE,
    INDEX `member_id_idx` (`member_id` ASC) VISIBLE,
    CONSTRAINT `article_id`
    FOREIGN KEY (`article_id`)
    REFERENCES `mydb`.`article` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    CONSTRAINT `member_comment_id`
    FOREIGN KEY (`member_id`)
    REFERENCES `mydb`.`member` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
