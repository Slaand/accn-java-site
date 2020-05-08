CREATE TABLE IF NOT EXISTS `tbl_users`
(

    `id`           int(11)       NOT NULL auto_increment,
    `name`         varchar(250)  NOT NULL,
    `surname`      varchar(250)  NOT NULL default 0,
    `email`        varchar(250)  NOT NULL default 1,
    `mobile`       varchar(250)  NULL,
    `role`         varchar(250)  NULL,
    `password`     varchar(250)  NULL,
    PRIMARY KEY (`id`)

);