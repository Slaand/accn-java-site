CREATE TABLE IF NOT EXISTS `tbl_item`
(

    `id`          int(11)      NOT NULL auto_increment,
    `category_id` int(11)      NOT NULL,
    `name`        varchar(250) NOT NULL,
    `price`       float(11, 2) NOT NULL,
    `warranty`    int(2)       NOT NULL default 0,
    `description` longtext     NOT NULL default '{}',

    FOREIGN KEY (category_id)  REFERENCES tbl_category(id),
    PRIMARY KEY (`id`)

);