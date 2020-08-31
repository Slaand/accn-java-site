INSERT INTO `tbl_user` (`name`, `surname`, `email`, `mobile`, `role`, `password`) VALUES
	('kek', 'lol', 'owner@slaand.com', '27272727', 'ADMIN', '$2a$08$5uH21HJI/gPLJn5flf5gVuLdvic4NeUIedQ1zzAOrI0S.1ST8X4VO');
ALTER TABLE `tbl_user` AUTO_INCREMENT = 2;

CREATE TABLE IF NOT EXISTS `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT IGNORE INTO `hibernate_sequence` (`next_val`) VALUES
	(2),
	(2),
	(2),
	(2),
	(2);