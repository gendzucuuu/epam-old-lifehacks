
 USE lifehacks;

 CREATE TABLE user (
   user_id BIGINT(20) NOT NULL AUTO_INCREMENT,
   is_blocked TINYINT(1) NOT NULL,
   username VARCHAR(255) DEFAULT NULL,
   first_name VARCHAR(255) DEFAULT NULL,
   second_name VARCHAR(255) DEFAULT NULL,
   email VARCHAR(255) DEFAULT NULL,
   password VARCHAR(255) DEFAULT NULL,
   role VARCHAR(20) CHARACTER SET utf8 NOT NULL DEFAULT 'USER',
   PRIMARY KEY (user_id)
 );

 CREATE TABLE life_hack (
   life_hack_id BIGINT(20) NOT NULL AUTO_INCREMENT,
   user_id BIGINT NOT NULL,
   category VARCHAR(255) DEFAULT NULL,
   name VARCHAR(255) DEFAULT NULL,
   description TEXT DEFAULT NULL,
   picture MEDIUMBLOB DEFAULT NULL,
   status TINYINT(1) NOT NULL DEFAULT false,
   date_of_posting BIGINT(20) NOT NULL ,

   PRIMARY KEY (life_hack_id),
   FOREIGN KEY (user_id) REFERENCES user(user_id)
 );

 CREATE TABLE  comment (
   comment_id BIGINT(20) NOT NULL AUTO_INCREMENT,
   user_id BIGINT NOT NULL,
   life_hack_id BIGINT NOT NULL,
   description TEXT NOT NULL,
   date_of_comment BIGINT(20) NOT NULL ,

   PRIMARY KEY (comment_id),
   FOREIGN KEY (user_id) REFERENCES user(user_id),
   FOREIGN KEY (life_hack_id) REFERENCES life_hack(life_hack_id)
 );


 CREATE TABLE favorite_life_hack (
   liked_life_hack_id BIGINT(20) NOT NULL AUTO_INCREMENT,
   user_id BIGINT NOT NULL,
   life_hack_id BIGINT NOT NULL,

   PRIMARY KEY (liked_life_hack_id),
   FOREIGN KEY (user_id) REFERENCES user(user_id),
   FOREIGN KEY (life_hack_id) REFERENCES life_hack(life_hack_id)
 );