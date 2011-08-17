-- creating the schema
CREATE SCHEMA `direct` ;
-- creating the nouns table
CREATE  TABLE `direct`.`nouns` (

  `lhs` VARCHAR(512) NOT NULL ,

  `rhs` VARCHAR(512) NOT NULL ,

  `score` DECIMAL(64,30) UNSIGNED NOT NULL ,

  PRIMARY KEY (`lhs`, `rhs`) ,

  INDEX `rhs` (`rhs` ASC) )

DEFAULT CHARACTER SET = ascii;
-- creating the verbs table
CREATE TABLE direct.verbs LIKE direct.nouns;
-- filling the nouns table
LOAD DATA INFILE
'C:/Documents and Settings/mishraki/git/89565de/89565de/DIRECT_nouns_200.txt'
INTO TABLE `direct`.`nouns`
FIELDS TERMINATED BY '\t' OPTIONALLY ENCLOSED BY '"'
  LINES TERMINATED BY '\r\n'
;
-- filling the verbs table
LOAD DATA INFILE
'C:/Documents and Settings/mishraki/git/89565de/89565de/DIRECT_verbs_200.txt'
INTO TABLE `direct`.`verbs`
FIELDS TERMINATED BY '\t' OPTIONALLY ENCLOSED BY '"'
  LINES TERMINATED BY '\r\n'
;
-- grant
GRANT SELECT ON direct.* TO de@"%" IDENTIFIED BY '1111';