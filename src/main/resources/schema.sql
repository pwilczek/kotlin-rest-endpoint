
DROP TABLE IF EXISTS `User`;

CREATE TABLE `User` (
  `id` VARCHAR NOT NULL DEFAULT 'NULL',
  `name` VARCHAR NOT NULL DEFAULT 'NULL',
  `surname` VARCHAR NOT NULL DEFAULT 'NULL',
  `mail` VARCHAR NOT NULL DEFAULT 'NULL',
  PRIMARY KEY (`id`)
);