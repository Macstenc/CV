DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NULL,
  `description` VARCHAR(800) NULL,
  `created` DATETIME,
  `updated` DATETIME,
  `price` DECIMAL(5,2),
  `type` VARCHAR(10),
  PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `review`;
CREATE TABLE IF NOT EXISTS `review` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `product_id` BIGINT NOT NULL,
  `content` VARCHAR(400) NULL,
  `rating` INT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_review_product`
    FOREIGN KEY (`product_id`)
    REFERENCES `product` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NULL,
  `description` VARCHAR(800) NULL,
  PRIMARY KEY (`id`)
);

ALTER TABLE `product`
ADD COLUMN `category_id` BIGINT NULL;

CREATE TABLE `attribute` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NULL,
  `value` VARCHAR(800) NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `product_attribute` (
  `product_id` BIGINT NOT NULL,
  `attribute_id` BIGINT NOT NULL,
  PRIMARY KEY (`product_id`, `attribute_id`),
  CONSTRAINT `fk_product_attribute_product_id`
    FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `fk_product_attribute_attribute_id`
    FOREIGN KEY (`attribute_id`) REFERENCES `attribute` (`id`)
);