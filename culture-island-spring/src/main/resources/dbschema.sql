-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema cultureisland
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema cultureisland
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `cultureisland` DEFAULT CHARACTER SET utf8 ;
USE `cultureisland` ;

-- -----------------------------------------------------
-- Table `cultureisland`.`member`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cultureisland`.`member` ;

CREATE TABLE IF NOT EXISTS `cultureisland`.`member` (
                                                        `id` INT NOT NULL AUTO_INCREMENT,
                                                        `email` VARCHAR(32) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    `name` VARCHAR(16) NOT NULL,
    `number` VARCHAR(16) NOT NULL,
    `gender` INT NOT NULL,
    `birth` VARCHAR(32) NOT NULL,
    `member_rank` INT NOT NULL DEFAULT 1,
    `imglink` VARCHAR(64) NULL,
    `intro` VARCHAR(128) NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `unique_email` (`email` ASC),
    UNIQUE INDEX `unique_name` (`name` ASC))
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cultureisland`.`culture`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cultureisland`.`culture` ;

CREATE TABLE IF NOT EXISTS `cultureisland`.`culture` (
                                                         `title` VARCHAR(64) NOT NULL,
    `date` VARCHAR(32) NOT NULL,
    `codename` VARCHAR(16) NOT NULL,
    `guname` VARCHAR(16) NOT NULL,
    `place` VARCHAR(64) NOT NULL,
    `use_trgt` VARCHAR(64) NOT NULL,
    `use_fee` VARCHAR(256) NULL,
    `start_date` DATETIME NOT NULL,
    `log` DECIMAL(20,17) NOT NULL,
    `lat` DECIMAL(20,17) NOT NULL,
    `is_free` VARCHAR(8) NOT NULL,
    `hmpg_addr` VARCHAR(64) NOT NULL,
    `score` DECIMAL(3,2) NULL,
    PRIMARY KEY (`title`, `date`))
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cultureisland`.`like`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cultureisland`.`like` ;

CREATE TABLE IF NOT EXISTS `cultureisland`.`like` (
                                                      `id` INT NOT NULL AUTO_INCREMENT,
                                                      `member_id` INT NOT NULL,
                                                      `culture_title` VARCHAR(64) NOT NULL,
    `culture_date` VARCHAR(32) NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_like_member_idx` (`member_id` ASC) VISIBLE,
    INDEX `fk_like_culture1_idx` (`culture_title` ASC, `culture_date` ASC) VISIBLE,
    CONSTRAINT `fk_like_member`
    FOREIGN KEY (`member_id`)
    REFERENCES `cultureisland`.`member` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_like_culture1`
    FOREIGN KEY (`culture_title` , `culture_date`)
    REFERENCES `cultureisland`.`culture` (`title` , `date`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cultureisland`.`article`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cultureisland`.`article` ;

CREATE TABLE IF NOT EXISTS `cultureisland`.`article` (
                                                         `id` INT NOT NULL AUTO_INCREMENT,
                                                         `subject` VARCHAR(64) NOT NULL,
    `content` VARCHAR(5096) NOT NULL,
    `hit` INT NULL DEFAULT 0,
    `register_time` DATETIME NULL,
    `member_id` INT NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_article_member1_idx` (`member_id` ASC) VISIBLE,
    CONSTRAINT `fk_article_member1`
    FOREIGN KEY (`member_id`)
    REFERENCES `cultureisland`.`member` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cultureisland`.`comments`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cultureisland`.`comments` ;

CREATE TABLE IF NOT EXISTS `cultureisland`.`comments` (
                                                          `id` INT NOT NULL AUTO_INCREMENT,
                                                          `parent_id` INT NULL,
                                                          `depth` INT NULL,
                                                          `comment` VARCHAR(512) NOT NULL,
    `regist_time` DATETIME NULL,
    `article_id` INT NOT NULL,
    `member_id` INT NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_comments_article1_idx` (`article_id` ASC) VISIBLE,
    INDEX `fk_comments_member1_idx` (`member_id` ASC) VISIBLE,
    CONSTRAINT `fk_comments_article1`
    FOREIGN KEY (`article_id`)
    REFERENCES `cultureisland`.`article` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_comments_member1`
    FOREIGN KEY (`member_id`)
    REFERENCES `cultureisland`.`member` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cultureisland`.`file`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cultureisland`.`file` ;

CREATE TABLE IF NOT EXISTS `cultureisland`.`file` (
                                                      `id` INT NOT NULL AUTO_INCREMENT,
                                                      `save_folde` VARCHAR(64) NULL,
    `original_file` VARCHAR(64) NULL,
    `save_file` VARCHAR(64) NULL,
    `article_id` INT NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_file_article1_idx` (`article_id` ASC) VISIBLE,
    CONSTRAINT `fk_file_article1`
    FOREIGN KEY (`article_id`)
    REFERENCES `cultureisland`.`article` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
