
-- Create db sql--
CREATE SCHEMA `calendar_connect_app`;

-- Create user table SQL ---
CREATE TABLE `calendar_connect_app`.`tb_user` (
          `id` INT NOT NULL AUTO_INCREMENT,
          `email` VARCHAR(300) NULL,
          `user_name` VARCHAR(300) NULL,
          `google_refresh_token` VARCHAR(300) NULL,
          `google_access_token` LONGTEXT NULL,
          `google_token_expired_at` INT NULL,
          PRIMARY KEY (`id`));