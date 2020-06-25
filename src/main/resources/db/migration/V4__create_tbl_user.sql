CREATE TABLE IF NOT EXISTS `tbl_user`
(

    `id`           int(11)       NOT NULL auto_increment,
    `name`         varchar(250)  NOT NULL,
    `surname`      varchar(250)  NOT NULL,
    `email`        varchar(250)  NOT NULL,
    `mobile`       varchar(250)  NULL,
    `role`         varchar(250)  NOT NULL DEFAULT 'USER',
    `password`     varchar(250)  NOT NULL,
    PRIMARY KEY (`id`)

);