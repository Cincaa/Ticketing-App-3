CREATE TABLE IF NOT EXISTS `tickets` (
	`ticketNo` INT NOT NULL,
	`name` VARCHAR(50) NULL DEFAULT NULL,
	`discount` INT NULL DEFAULT NULL,
	`promoCode` VARCHAR(50) NULL DEFAULT NULL,
	`extraFee` INT NULL DEFAULT NULL,
	PRIMARY KEY (`ticketNo`)
);