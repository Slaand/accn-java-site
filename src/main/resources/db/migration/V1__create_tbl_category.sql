CREATE TABLE IF NOT EXISTS `tbl_category`
(

    `id`           int(11)      NOT NULL auto_increment,
    `name`         varchar(250) NOT NULL,
    `is_hidden`    bit(1)       NOT NULL default 0,
    `is_parent`    bit(1)       NOT NULL default 1,
    `child_of`     int(11)      NULL,
    PRIMARY KEY (`id`)

);