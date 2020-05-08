CREATE TABLE IF NOT EXISTS `tbl_images`
(

    `id`          int(11)      NOT NULL auto_increment,
    `item_id`     int(11)      NOT NULL,
    `base64`      longtext     NOT NULL default '',
    FOREIGN KEY (item_id)  REFERENCES tbl_items(id),
    PRIMARY KEY (`id`)

);