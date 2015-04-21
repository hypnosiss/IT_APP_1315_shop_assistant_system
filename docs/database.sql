-- MySQL Script generated by MySQL Workbench
-- 04/21/15 22:40:09
-- Model: New Model    Version: 1.0
SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema shop_assistant
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `shop_assistant` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `shop_assistant` ;

-- -----------------------------------------------------
-- Table `shop_assistant`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `shop_assistant`.`users` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(64) NOT NULL,
  `password` VARCHAR(64) NOT NULL,
  `salt` VARCHAR(64) NOT NULL,
  `name` VARCHAR(64) NOT NULL,
  `address` VARCHAR(512) NOT NULL,
  `phone` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `shop_assistant`.`products`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `shop_assistant`.`products` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `ean` VARCHAR(13) NOT NULL,
  `brand` VARCHAR(45) NOT NULL,
  `name` VARCHAR(128) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `shop_assistant`.`events`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `shop_assistant`.`events` (
  `id` INT NOT NULL,
  `user_id` INT NOT NULL,
  `product_id` INT NOT NULL,
  `uuid` VARCHAR(64) NOT NULL,
  `trigger_timestamp` TIMESTAMP NOT NULL,
  `type` ENUM('in', 'out') NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_events_users1_idx` (`user_id` ASC),
  INDEX `fk_events_products1_idx` (`product_id` ASC),
  CONSTRAINT `fk_events_users1`
    FOREIGN KEY (`user_id`)
    REFERENCES `shop_assistant`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_events_products1`
    FOREIGN KEY (`product_id`)
    REFERENCES `shop_assistant`.`products` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `shop_assistant`.`shops`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `shop_assistant`.`shops` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `address` VARCHAR(512) NOT NULL,
  `phone` VARCHAR(45) NULL,
  `api_key` VARCHAR(255) NOT NULL,
  `api_url` VARCHAR(255) NOT NULL,
  `payment_method_cash` TINYINT(1) NOT NULL DEFAULT 1,
  `payment_method_card` TINYINT(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `shop_assistant`.`orders`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `shop_assistant`.`orders` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `shop_id` INT NOT NULL,
  `total_price` DECIMAL(7,2) NOT NULL,
  `delivery_date` DATE NOT NULL,
  `delivery_hour_from` TIME NOT NULL,
  `delivery_hour_to` TIME NOT NULL,
  `delivery_address` VARCHAR(512) NOT NULL,
  `order_timestamp` TIMESTAMP NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_orders_users_idx` (`user_id` ASC),
  INDEX `fk_orders_shops1_idx` (`shop_id` ASC),
  CONSTRAINT `fk_orders_users`
    FOREIGN KEY (`user_id`)
    REFERENCES `shop_assistant`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_orders_shops1`
    FOREIGN KEY (`shop_id`)
    REFERENCES `shop_assistant`.`shops` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `shop_assistant`.`orders_products`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `shop_assistant`.`orders_products` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `order_id` INT NOT NULL,
  `product_id` INT NOT NULL,
  `price` DECIMAL(7,2) NOT NULL,
  `quantity` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_orders_has_products_products1_idx` (`product_id` ASC),
  INDEX `fk_orders_has_products_orders1_idx` (`order_id` ASC),
  CONSTRAINT `fk_orders_has_products_orders1`
    FOREIGN KEY (`order_id`)
    REFERENCES `shop_assistant`.`orders` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_orders_has_products_products1`
    FOREIGN KEY (`product_id`)
    REFERENCES `shop_assistant`.`products` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `shop_assistant`.`users_products`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `shop_assistant`.`users_products` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `product_id` INT NOT NULL,
  `status` ENUM('in', 'out', 'unknown') NOT NULL DEFAULT 'unknown',
  `quantity` INT NOT NULL DEFAULT 0,
  `name` VARCHAR(128) NULL,
  `last_order_timestamp` TIMESTAMP NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_users_has_products_products1_idx` (`product_id` ASC),
  INDEX `fk_users_has_products_users1_idx` (`user_id` ASC),
  CONSTRAINT `fk_users_has_products_users1`
    FOREIGN KEY (`user_id`)
    REFERENCES `shop_assistant`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_users_has_products_products1`
    FOREIGN KEY (`product_id`)
    REFERENCES `shop_assistant`.`products` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
