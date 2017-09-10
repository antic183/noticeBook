DROP SCHEMA IF EXISTS `ntb`;

-- -----------------------------------------------------
-- Schema ntb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `ntb` DEFAULT CHARACTER SET utf8 ;
USE `ntb` ;

-- -----------------------------------------------------
-- Table `ntb`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ntb`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `created_at` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ntb`.`notice`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ntb`.`notice` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(255) NOT NULL,
  `text` VARCHAR(255) NOT NULL,
  `created_at` DATETIME NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ntb`.`user_notice`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ntb`.`user_notice` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `notice_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_user_notice_user_idx` (`user_id` ASC),
  INDEX `fk_user_notice_notice1_idx` (`notice_id` ASC),
  CONSTRAINT `fk_user_notice_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `ntb`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_user_notice_notice1`
    FOREIGN KEY (`notice_id`)
    REFERENCES `ntb`.`notice` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


drop USER ntb_user@localhost;

CREATE USER ntb_user@localhost IDENTIFIED BY '1234';
GRANT SELECT, INSERT, DELETE, UPDATE on ntb.* TO ntb_user@localhost;
#SELECT * FROM mysql.user; -- check user; #DROP USER ntb_user@localhost
#SHOW GRANTS FOR ntb_user@localhost;
