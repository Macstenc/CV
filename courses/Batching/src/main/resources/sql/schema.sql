DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `firstname` VARCHAR(100) NOT NULL,
  `lastname` VARCHAR(800) NOT NULL,
  `created` DATETIME,
  `updated` DATETIME,
  PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `customer_id` BIGINT NOT NULL,
  `created` DATETIME,
  `total` DECIMAL(5,2),
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_order_customer_id`
    FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`)
);

DROP TABLE IF EXISTS `order_row`;
CREATE TABLE `order_row` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `order_id` BIGINT NOT NULL,
  `product_id` BIGINT NOT NULL,
  `price` DECIMAL(5,2),
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_order_row_order_id`
    FOREIGN KEY (`order_id`) REFERENCES `order` (`id`),
  CONSTRAINT `fk_order_row_product_id`
    FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
);
