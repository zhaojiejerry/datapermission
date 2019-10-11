CREATE TABLE `table_condition` (
  `id` varchar(40) NOT NULL,
  `table_name` varchar(255) DEFAULT NULL,
  `operator` varchar(255) DEFAULT NULL,
  `field_name` varchar(255) DEFAULT NULL,
  `field_value` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `application` (
  `id` varchar(50) NOT NULL,
  `name` varchar(64) CHARACTER SET utf8 DEFAULT NULL,
  `type_id` int(11) DEFAULT NULL,
  `deleted` int(2) DEFAULT NULL,
  `created_at` date DEFAULT NULL,
  `number` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;