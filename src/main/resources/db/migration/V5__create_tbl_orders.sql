CREATE TABLE IF NOT EXISTS `tbl_users`
(

    `id`           int(11)       NOT NULL auto_increment,
    `user_id`      int(11)       NOT NULL,
    `created`      varchar(250)  NULL,
    `updated`      varchar(250)  NULL,
    `status`       varchar(250)  NOT NULL default 'USER',
    `address`      varchar(250)  NULL,
    `item_id`      int(11)       NOT NULL,
    FOREIGN KEY (user_id)  REFERENCES tbl_users(id),
    FOREIGN KEY (item_id)  REFERENCES tbl_items(id),
    PRIMARY KEY (`id`)

);