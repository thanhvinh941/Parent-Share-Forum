CREATE DATABASE IF NOT EXISTS `db01`;
CREATE DATABASE IF NOT EXISTS `db02`;
CREATE DATABASE IF NOT EXISTS `db03`;
CREATE DATABASE IF NOT EXISTS `db04`;
CREATE DATABASE IF NOT EXISTS `db05`;

-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: localhost    Database: db05
-- ------------------------------------------------------
-- Server version	5.7.37

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed
-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: localhost    Database: db04
-- ------------------------------------------------------
-- Server version	5.7.37

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed
-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: localhost    Database: db02
-- ------------------------------------------------------
-- Server version	5.7.37

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed
-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: localhost    Database: db01
-- ------------------------------------------------------
-- Server version	5.7.37

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `db01.authorization`
--

DROP TABLE IF EXISTS db01.authorization;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE db01.authorization (
  author_id int(11) NOT NULL AUTO_INCREMENT,
  member_id int(11) DEFAULT NULL,
  role varchar(255) DEFAULT NULL,
  PRIMARY KEY (author_id)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `db01.authorization`
--


--
-- Table structure for table `member`
--

DROP TABLE IF EXISTS db01.member;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE db01.member (
  member_id int(11) NOT NULL AUTO_INCREMENT,
  avatar_url varchar(255) DEFAULT NULL,
  backgroud_url varchar(255) DEFAULT NULL,
  create_dt datetime(6) DEFAULT NULL,
  dbo_dt datetime(6) DEFAULT NULL,
  del_flg tinyint(4) DEFAULT NULL,
  display_name varchar(255) DEFAULT NULL,
  last_login_time datetime(6) DEFAULT NULL,
  login_id varchar(255) DEFAULT NULL,
  member_email varchar(255) DEFAULT NULL,
  memberfname varchar(255) DEFAULT NULL,
  memberlname varchar(255) DEFAULT NULL,
  member_phone_number varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  status_flg tinyint(4) DEFAULT NULL,
  update_dt datetime(6) DEFAULT NULL,
  valid_flg tinyint(4) DEFAULT NULL,
  PRIMARY KEY (member_id)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member`
--

INSERT INTO db01.member VALUES (1,'http://dummyimage.com/100x100.png/dddddd/000000','http://dummyimage.com/100x100.png/dddddd/000000','2023-01-09 04:27:41.000000','1981-12-18 07:21:56.000000',1,'Barney Richard',NULL,'brichard0','brichard0@xinhuanet.com','Barney','Richard','352 861 9961','40cc63dca7ae47ce4dfd75a6d83a57c6004c7b45',0,'2023-02-07 08:53:12.000000',1);
INSERT INTO db01.member VALUES (2,'http://dummyimage.com/100x100.png/5fa2dd/ffffff','http://dummyimage.com/100x100.png/dddddd/000000','2022-12-31 08:55:33.000000','1978-01-14 17:32:48.000000',0,'Jody Burt',NULL,'jburt1','jburt1@imgur.com','Jody','Burt','372 136 6900','40cc63dca7ae47ce4dfd75a6d83a57c6004c7b45',1,'2023-02-13 17:10:17.000000',0);
INSERT INTO db01.member VALUES (3,'http://dummyimage.com/100x100.png/ff4444/ffffff','http://dummyimage.com/100x100.png/dddddd/000000','2023-01-03 20:53:32.000000','2000-09-11 08:35:44.000000',1,'Faunie Tilte',NULL,'ftilte2','ftilte2@diigo.com','Faunie','Tilte','476 139 7045','40cc63dca7ae47ce4dfd75a6d83a57c6004c7b45',0,'2023-01-07 18:08:45.000000',1);
INSERT INTO db01.member VALUES (4,'http://dummyimage.com/100x100.png/ff4444/ffffff','http://dummyimage.com/100x100.png/5fa2dd/ffffff','2023-01-07 05:43:03.000000','1975-10-05 08:07:34.000000',1,'Travus Shiell',NULL,'tshiell3','tshiell3@youtu.be','Travus','Shiell','971 346 5314','40cc63dca7ae47ce4dfd75a6d83a57c6004c7b45',1,'2023-02-02 10:02:38.000000',1);
INSERT INTO db01.member VALUES (5,'http://dummyimage.com/100x100.png/dddddd/000000','http://dummyimage.com/100x100.png/dddddd/000000','2023-01-05 03:16:06.000000','2016-06-20 19:26:02.000000',1,'Ky Williscroft',NULL,'kwilliscroft4','kwilliscroft4@fastcompany.com','Ky','Williscroft','660 943 2710','40cc63dca7ae47ce4dfd75a6d83a57c6004c7b45',0,'2023-02-14 05:22:50.000000',0);
INSERT INTO db01.member VALUES (6,'http://dummyimage.com/100x100.png/5fa2dd/ffffff','http://dummyimage.com/100x100.png/ff4444/ffffff','2023-01-05 11:10:14.000000','2015-11-06 23:29:44.000000',0,'Brook Woolf',NULL,'bwoolf5','bwoolf5@blogspot.com','Brook','Woolf','456 177 5871','40cc63dca7ae47ce4dfd75a6d83a57c6004c7b45',1,'2023-01-25 04:39:28.000000',1);
INSERT INTO db01.member VALUES (7,'http://dummyimage.com/100x100.png/dddddd/000000','http://dummyimage.com/100x100.png/dddddd/000000','2023-01-10 13:54:50.000000','1997-06-28 07:02:12.000000',0,'Lolita Denning',NULL,'ldenning6','ldenning6@fda.gov','Lolita','Denning','760 960 4880','40cc63dca7ae47ce4dfd75a6d83a57c6004c7b45',1,'2023-02-03 12:45:44.000000',1);
INSERT INTO db01.member VALUES (8,'http://dummyimage.com/100x100.png/ff4444/ffffff','http://dummyimage.com/100x100.png/5fa2dd/ffffff','2023-01-03 00:32:13.000000','2008-10-01 12:12:24.000000',1,'Atlante Tingcomb',NULL,'atingcomb7','atingcomb7@ucoz.com','Atlante','Tingcomb','605 444 5718','40cc63dca7ae47ce4dfd75a6d83a57c6004c7b45',0,'2023-01-27 12:17:58.000000',0);
INSERT INTO db01.member VALUES (9,'http://dummyimage.com/100x100.png/cc0000/ffffff','http://dummyimage.com/100x100.png/ff4444/ffffff','2023-01-05 04:08:00.000000','1987-04-25 15:08:38.000000',0,'Ermengarde Hartop',NULL,'ehartop8','ehartop8@smugmug.com','Ermengarde','Hartop','399 911 6609','40cc63dca7ae47ce4dfd75a6d83a57c6004c7b45',1,'2023-01-31 00:49:26.000000',0);
INSERT INTO db01.member VALUES (10,'http://dummyimage.com/100x100.png/ff4444/ffffff','http://dummyimage.com/100x100.png/cc0000/ffffff','2023-01-01 11:03:56.000000','1972-12-25 18:21:53.000000',0,'Valry Chittenden',NULL,'vchittenden9','vchittenden9@unesco.org','Valry','Chittenden','859 988 8567','40cc63dca7ae47ce4dfd75a6d83a57c6004c7b45',1,'2022-12-28 23:20:08.000000',1);

--
-- Table structure for table `verification`
--

DROP TABLE IF EXISTS db01.verification;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE db01.verification (
  verifi_id int(11) NOT NULL AUTO_INCREMENT,
  del_flg tinyint(4) DEFAULT NULL,
  expiration_time datetime(6) DEFAULT NULL,
  member_id int(11) DEFAULT NULL,
  token varchar(255) DEFAULT NULL,
  valid_flg tinyint(4) DEFAULT NULL,
  PRIMARY KEY (verifi_id)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `verification`
--

INSERT INTO db01.verification VALUES (1,1,'2022-02-21 14:49:03.000000',1,'d680c946-0f17-47eb-b7a6-5b2d29fc464b',1);
INSERT INTO db01.verification VALUES (2,1,'2022-07-27 23:13:28.000000',2,'7782df95-c762-4220-b846-4eaa78f8b41a',1);
INSERT INTO db01.verification VALUES (3,1,'2022-02-05 20:18:04.000000',3,'b5e51650-40b6-4886-9996-e0c74bfd808d',1);
INSERT INTO db01.verification VALUES (4,1,'2022-09-07 08:52:16.000000',4,'e29c291d-60c6-4e16-9f62-fa5fc7c2b9af',1);
INSERT INTO db01.verification VALUES (5,1,'2022-07-31 22:43:49.000000',5,'aecae641-d92c-4092-9483-c3f75ea291d7',1);
INSERT INTO db01.verification VALUES (6,1,'2022-02-18 01:38:06.000000',6,'67bbdbaa-573b-4514-ac28-de727db4a202',1);
INSERT INTO db01.verification VALUES (7,1,'2022-03-09 08:03:59.000000',7,'1194209e-d980-4a2f-adb2-a5b9d6a1ceaf',1);
INSERT INTO db01.verification VALUES (8,1,'2022-11-17 21:06:34.000000',8,'84264f51-625f-464e-a87f-cb1f1ce3af86',1);
INSERT INTO db01.verification VALUES (9,1,'2022-02-23 13:56:49.000000',9,'1c79db82-5fb2-4d36-8fec-5fe494708208',1);
INSERT INTO db01.verification VALUES (10,1,'2022-04-19 22:39:50.000000',10,'9a1b0f83-e13f-4b79-a225-f51ee74fac77',1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed
-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: localhost    Database: db03
-- ------------------------------------------------------
-- Server version	5.7.37

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed
