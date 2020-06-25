CREATE TABLE IF NOT EXISTS `tbl_image`
(

    `id`          int(11)      NOT NULL auto_increment,
    `item_id`     int(11)      NOT NULL,
    `base64`      longtext     NULL,
    FOREIGN KEY (item_id)  REFERENCES tbl_item(id),
    PRIMARY KEY (`id`)

);