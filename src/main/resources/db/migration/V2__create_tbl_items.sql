CREATE TABLE IF NOT EXISTS `tbl_items`
(

    `id`          int(11)      NOT NULL auto_increment,
    `name`        varchar(250) NOT NULL,
    `price`       bit(1)       NOT NULL default 0,
    `warranty`    int(2)       NOT NULL default 0,
    `description` longtext     NOT NULL default '{}',
    `category_id` int(11)      NOT NULL,
    FOREIGN KEY (category_id)  REFERENCES tbl_category(id),
    PRIMARY KEY (`id`)

);