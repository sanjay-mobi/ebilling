
CREATE TABLE `company_master` (
  `company_id` int(11) NOT NULL AUTO_INCREMENT,
  `gst_applicable` bit(1) NOT NULL,
  `gst_no` varchar(255) DEFAULT NULL,
  `invoice_counter` int(11) DEFAULT NULL,
  `invoice_prefix` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`company_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

CREATE TABLE `address` (
  `address_id` int(11) NOT NULL AUTO_INCREMENT,
  `address1` varchar(255) DEFAULT NULL,
  `address2` varchar(255) DEFAULT NULL,
  `address_type` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `company_id` int(11) DEFAULT NULL,
  `pincode` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`address_id`),
  KEY `FK7s7d4g64blom5p0oboo4ivu7w` (`company_id`),
  CONSTRAINT `FK7s7d4g64blom5p0oboo4ivu7w` FOREIGN KEY (`company_id`) REFERENCES `company_master` (`company_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;


CREATE TABLE `company_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `type` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKf1n4ck27bgosfmyfq00eepjdb` (`company_id`),
  CONSTRAINT `FKf1n4ck27bgosfmyfq00eepjdb` FOREIGN KEY (`company_id`) REFERENCES `company_master` (`company_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;


CREATE TABLE `product_master` (
  `product_id` int(11) NOT NULL AUTO_INCREMENT,
  `mrp` float DEFAULT NULL,
  `brand` varchar(255) DEFAULT NULL,
  `color` varchar(255) DEFAULT NULL,
  `company_id` int(11) DEFAULT NULL,
  `cost_price` float DEFAULT NULL,
  `ean_code` varchar(255) DEFAULT NULL,
  `hsn_code` varchar(255) DEFAULT NULL,
  `sizes` varchar(255) DEFAULT NULL,
  `style_code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;


INSERT INTO `ebhumi_billing`.`company_master`
(`company_id`,
`gst_applicable`,
`gst_no`,
`invoice_counter`,
`invoice_prefix`,
`name`)
VALUES
(1,
1,
"gst_no",
31,
"myntra/1234/",
"non one"
);

INSERT INTO `ebhumi_billing`.`company_type`
(`id`,
`company_id`,
`type`)
VALUES(
1,
1,
"suplier"
);

INSERT INTO `ebhumi_billing`.`address`
(`address_id`,
`address1`,
`address2`,
`address_type`,
`city`,
`company_id`,
`pincode`,
`state`)
VALUES
(1,
"khasra no - 1824/25, First Floor",
"Opposite Post office, wazirabaad",
"billing",
"gurugram",
1,
122003,
"Haryana");

INSERT INTO `ebhumi_billing`.`address`
(`address_id`,
`address1`,
`address2`,
`address_type`,
`city`,
`company_id`,
`pincode`,
`state`)
VALUES
(2,
"khasra no - 1824/25, First Floor",
"Opposite Post office, wazirabaad",
"shipping",
"gurugram",
1,
122003,
"Haryana");
