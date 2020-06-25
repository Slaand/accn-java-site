CREATE TABLE IF NOT EXISTS `tbl_order`
(

    `id`           int(11)       NOT NULL auto_increment,
    `user_id`      int(11)       NOT NULL,
    `item_id`      int(11)       NOT NULL,
    `created`      varchar(250)  NULL,
    `updated`      varchar(250)  NULL,
    `status`       varchar(250)  NOT NULL default 'NEW',
    `address`      varchar(250)  NULL,
    FOREIGN KEY (user_id)  REFERENCES tbl_user(id),
    FOREIGN KEY (item_id)  REFERENCES tbl_item(id),
    PRIMARY KEY (`id`)

);