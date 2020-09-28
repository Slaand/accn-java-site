ALTER TABLE tbl_user
  ADD `is_subscribed` bit(1) NOT NULL default 0
    AFTER role;