ALTER TABLE tbl_image RENAME TO tbl_image_item;
ALTER TABLE tbl_image_item MODIFY base64 longtext NOT NULL;

CREATE TABLE IF NOT EXISTS `tbl_image_category`
(

    `id`          int(11)      NOT NULL auto_increment,
    `category_id` int(11)      NOT NULL,
    `base64`      longtext     NOT NULL,
    FOREIGN KEY (category_id)  REFERENCES tbl_category(id),
    PRIMARY KEY (`id`)

);