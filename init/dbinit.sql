CREATE DATABASE IF NOT EXISTS `db01`;
CREATE DATABASE IF NOT EXISTS `db02`;
CREATE DATABASE IF NOT EXISTS `db03`;
CREATE DATABASE IF NOT EXISTS `db04`;
CREATE DATABASE IF NOT EXISTS `db05`;

ALTER DATABASE db01 CHARACTER SET utf8 COLLATE utf8_general_ci;
ALTER DATABASE db02 CHARACTER SET utf8 COLLATE utf8_general_ci;
ALTER DATABASE db03 CHARACTER SET utf8 COLLATE utf8_general_ci;
ALTER DATABASE db04 CHARACTER SET utf8 COLLATE utf8_general_ci;
ALTER DATABASE db05 CHARACTER SET utf8 COLLATE utf8_general_ci;

-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: localhost    Database: db04
-- ------------------------------------------------------
-- Server version	8.0.29

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `contacts`
--

DROP TABLE IF EXISTS `contacts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `contacts` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) NOT NULL,
  `status` int NOT NULL,
  `topic_id` varchar(255) NOT NULL,
  `user_reciver_id` bigint NOT NULL,
  `user_sender_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKby2d67ypq6vnmpwgvoka0jn95` (`user_reciver_id`,`user_sender_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contacts`
--

LOCK TABLES `contacts` WRITE;
/*!40000 ALTER TABLE `contacts` DISABLE KEYS */;
INSERT INTO `contacts` VALUES (1,'2023-04-15 18:25:52.118000',2,'24399549-89cc-40e9-a548-8c66f985941f',8,12);
/*!40000 ALTER TABLE `contacts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ratings`
--

DROP TABLE IF EXISTS `ratings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ratings` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) NOT NULL,
  `rating` int NOT NULL,
  `user_rated_id` bigint DEFAULT NULL,
  `user_rating_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ratings`
--

LOCK TABLES `ratings` WRITE;
/*!40000 ALTER TABLE `ratings` DISABLE KEYS */;
/*!40000 ALTER TABLE `ratings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subscribe`
--

DROP TABLE IF EXISTS `subscribe`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subscribe` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) NOT NULL,
  `user_expert_id` bigint DEFAULT NULL,
  `user_subscriber_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subscribe`
--

LOCK TABLES `subscribe` WRITE;
/*!40000 ALTER TABLE `subscribe` DISABLE KEYS */;
/*!40000 ALTER TABLE `subscribe` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_des`
--

DROP TABLE IF EXISTS `user_des`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_des` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` text,
  `title` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_des`
--

LOCK TABLES `user_des` WRITE;
/*!40000 ALTER TABLE `user_des` DISABLE KEYS */;
INSERT INTO `user_des` VALUES (1,'Quá trình đào tạo','Năm 2010: Tốt nghiệp ĐH Y Dược TPHCM'),(2,'Quá trình đào tạo','Năm 2016: Bác sĩ Chuyên khoa I Da Liễu'),(48,'Quá trình đào tạo','Năm 2020: Tốt nghiệp loại Giỏi Chuyên khoa II Da Liễu'),(49,'Quá trình công tác','Năm 2011- 2017: Bệnh Viện CKPTTH Thẩm mỹ Á Âu'),(50,'Quá trình công tác','Năm 2018- Nay: Bệnh Viện Đại Học Y Dược TPHCM'),(51,'Các khóa đào tạo','20/5- 30/5/2014: Ứng dụng Laser trong Y học tại Viện vật lý Y Sinh học - TPHCM'),(52,'Các khóa đào tạo','17/07/2014:  Khóa học Liệu pháp thẩm mỹ nội khoa để ngăn ngừa lão hóa khuôn mặt tại Bệnh viện Chợ Rẫy, TPHCM'),(53,'Các khóa đào tạo','25- 26/11/2016:  Khóa học về thẩm mỹ da, ICAD 2016 tại Bangkok, Thái Lan'),(54,'Các khóa đào tạo','29/11/2016: Khóa học về Lý thuyết giải phẫu vùng mặt ứng dụng trong thẫm mỹ nội khoa tại Đại Học Y Dược TPHCM'),(55,'Các khóa đào tạo','05/9 – 08/12/17: Thủ thuật cơ bản và tiểu phẩu da tại Bệnh viện Da Liễu TPHCM'),(56,'Các khóa đào tạo','04 – 05/10/2018: Khóa học của The Allergan Medical Institute, MD CodesTM Visionary / Distinction tại Taipei, Taiwan'),(57,'Các khóa đào tạo','06/8 – 09/11/18: Tham gia khóa học Ứng dụng laser và ánh sáng trong da liễu tại bệnh viện Da Liễu TPHCM'),(58,'Các khóa đào tạo','17-19/11/2019: Tham gia khóa học căng chỉ của Mint, Hansbiomed corp tại Seoul, Hàn Quốc'),(59,'Các khóa đào tạo','28/09-16/10/20: Căng chỉ cơ bản trong thẩm mỹ da tại Bệnh viện Đại học Y Dược TPHCM'),(60,'Các công trình khoa học','19 - 20/07/2019:  Báo cáo “Kỹ thuật tiêm Hyaluronic acid trong tạo hình V-line cho khuôn mặt” trong “Hội nghị da liễu thẫm mỹ toàn quốc lần thứ 3” tại Thành phố Quy Nhơn - Bình Định'),(61,'Các công trình khoa học','20- 21/12/2019:  Báo cáo “Update in treatment for sensitive skin” trong “Hội nghị Da liễu Đông Dương” tại Viêng Chăn, Lào.'),(62,'Các công trình khoa học','20- 21/12/2019:  Báo cáo “The effect of combining botulinum toxin and hyaluronic acid in facial rejuvenation” trong “Hội nghị Da Liễu Đông Dương” tại Viêng Chăn, Lào'),(63,'Các công trình khoa học','20-21/5/2022: Báo cáo “Tối ưu hoá quản lý sẹo không cần phẫu thuật” tại Hội nghị Da Liễu toàn quốc năm 2022'),(64,'Các công trình khoa học','Immunohistochemical expression of interleukin 1 beta in papule biopsies from patients with acne vulgaris: IL-1beta expression in acne vulgaris in Dermatology Reports (03/2022)'),(65,'Các công trình khoa học','Đã tham gia nhiều báo cáo trong chuyên ngành da liễu, thẩm mỹ da trong các hội nghị, hội thảo tại Việt Nam'),(66,'Đào tạo & Nghiên cứu khoa học','Năm 2020: Đề tài nghiên cứu” Biểu hiện interleukin-1 beta tại sang thương sẩn viêm của mụn trứng cá” Đại học Y Dược TPHCM'),(67,'Đào tạo & Nghiên cứu khoa học','9/2020 - nay: là thành viên Ban Giảng Huấn của các chương trình đào tạo cấp chứng chỉ các lớp đào tạo \"Căng chỉ cơ bản trong thẩm mỹ da\"; \"Kiến thức và thực hành cơ bản Laser, ánh sáng, sóng cao tần và siêu âm hội tụ trong Da Liễu - Thẩm mỹ da”; “Tiêm chất làm đầy trong thẩm mỹ da\" tại BV Đại học Y Dược phối hợp Đại học Y Dược TPHCM'),(68,'Đào tạo & Nghiên cứu khoa học','Đang tham gia một số đề tài nghiên cứu cấp cơ sở tại BV Đại học Y Dược TPHCM'),(69,'Kinh nghiệm/ Chức vụ','Hội viên Hội Da Liễu TPHCM'),(70,'Kinh nghiệm/ Chức vụ','Hội viên Hội Da Liễu Việt Nam'),(71,'Nổi bật','Là một bác sĩ đầy nhiệt huyết trong ứng dụng công nghệ ánh sáng điều trị sắc tố da, lão hóa da và có kỹ năng cao trong mesotherapy (lăn kim, tiêm dưỡng chất và dược chất như chất trắng sáng, chất chống nhăn, chất làm đầy…) trong da.');
/*!40000 ALTER TABLE `user_des` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) NOT NULL,
  `del_flg` bit(1) NOT NULL,
  `email` varchar(255) NOT NULL,
  `email_verified` bit(1) NOT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `is_expert` bit(1) NOT NULL,
  `job_title` varchar(255) DEFAULT NULL,
  `last_time` datetime(6) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `provider` varchar(255) NOT NULL,
  `provider_id` varchar(255) DEFAULT NULL,
  `role` varchar(255) NOT NULL,
  `specialist` varchar(255) DEFAULT NULL,
  `status` tinyint NOT NULL,
  `topic_id` varchar(255) NOT NULL,
  `update_at` datetime(6) NOT NULL,
  `work_place` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'2023-04-15 04:14:47.000000',_binary '\0','hoanghiep@gmail.com',_binary '',NULL,_binary '',NULL,NULL,'ThS.BS Hoàng Hiệp','$2a$10$kCXPVd.kyRpi1Ib0EXQoTOMwvkhToIMO.9M5hS7YRVWow7dAStUiO',NULL,'local',NULL,'expert',NULL,1,'2758f3d1-8e67-486f-bdcf-31401a2f0d31','2023-04-15 04:14:47.000000',NULL),(2,'2023-04-15 04:17:47.000000',_binary '\0','hongdung@gmail.com',_binary '',NULL,_binary '',NULL,NULL,'BS.CK1 Nguyễn Hồng Dũng','$2a$10$kCXPVd.kyRpi1Ib0EXQoTOMwvkhToIMO.9M5hS7YRVWow7dAStUiO',NULL,'local',NULL,'expert',NULL,1,'2758f3d1-8e67-486f-bdcf-31401a2f0d30','2023-04-15 04:17:47.000000',NULL),(3,'2023-04-15 04:19:36.000000',_binary '\0','dinhhuan@gmail.com',_binary '',NULL,_binary '',NULL,NULL,'ThS.BS Nguyễn Đình Huấn','$2a$10$kCXPVd.kyRpi1Ib0EXQoTOMwvkhToIMO.9M5hS7YRVWow7dAStUiO',NULL,'local',NULL,'expert',NULL,1,'915c26c6-011c-4f83-b8dd-5b91455367a8','2023-04-15 04:19:36.000000',NULL),(4,'2023-04-15 04:20:55.000000',_binary '\0','thuhau@gmail.com',_binary '',NULL,_binary '',NULL,NULL,'TS.BS.CK2 Nguyễn Thị Thu Hậu','$2a$10$kCXPVd.kyRpi1Ib0EXQoTOMwvkhToIMO.9M5hS7YRVWow7dAStUiO',NULL,'local',NULL,'expert',NULL,1,'aaa71e46-fade-4fd0-b9b0-30fdee8d43f0','2023-04-15 04:20:55.000000',NULL),(5,'2023-04-15 04:22:33.000000',_binary '\0','levianh@gmail.com',_binary '','a4d03166-4f4b-4285-b313-ed45c218db27.jpeg',_binary '','BS chuyên khoa Thẩm mỹ da, BV ĐHYD ',NULL,'BS.CK2 Lê Vi Anh','$2a$10$kCXPVd.kyRpi1Ib0EXQoTOMwvkhToIMO.9M5hS7YRVWow7dAStUiO',NULL,'local',NULL,'expert','Da liễu / Phẫu thuật thẩm mỹ',1,'51423a42-7a89-4947-a6e8-047dbcd32323','2023-04-15 04:22:33.000000','Bệnh viện Đại học Y dược TP.HCM'),(6,'2023-04-15 04:32:28.000000',_binary '\0','baoadminvbbc@gmail.com',_binary '',NULL,_binary '\0',NULL,NULL,'Quỳnh Bảo','$2a$10$kCXPVd.kyRpi1Ib0EXQoTOMwvkhToIMO.9M5hS7YRVWow7dAStUiO',NULL,'local',NULL,'admmin',NULL,1,'2145e6e7-1776-43df-8d0a-d6b8165c6270','2023-04-15 04:32:28.000000',NULL),(7,'2023-04-15 04:33:08.000000',_binary '\0','vinhadminvbbc@gmail.com',_binary '',NULL,_binary '\0',NULL,NULL,'Thanh Vinh','$2a$10$kCXPVd.kyRpi1Ib0EXQoTOMwvkhToIMO.9M5hS7YRVWow7dAStUiO',NULL,'local',NULL,'admmin',NULL,1,'7e59dc3d-ee31-4605-b239-af860bc49646','2023-04-15 04:33:08.000000',NULL),(8,'2023-04-15 12:27:10.326000',_binary '\0','truongthanhvinh941@gmail.com',_binary '',NULL,_binary '\0',NULL,NULL,'Thanh Vinh','$2a$10$baPyFW5ueaVKlty23B.RHuzgx1rhpfr/wzFrI074UB6uAebI5aZCy',NULL,'local',NULL,'user',NULL,2,'ba3505c2-2c39-48f8-9ea5-5a89ba2897cd','2023-04-15 12:27:10.326000',NULL),(9,'2023-04-15 14:35:32.374000',_binary '\0','quochuy235@gmail.com',_binary '',NULL,_binary '\0',NULL,NULL,'Quốc Huy','$2a$10$YonGM215QbgwEqolCi.YduhjGFtMXMe51TZUCkV5pCJMo89CTziaG',NULL,'local',NULL,'user',NULL,2,'78c4cd70-c634-44d8-8510-92a0af0a7980','2023-04-15 14:35:32.374000',NULL),(10,'2023-04-15 14:38:51.675000',_binary '\0','anhkhang238@gmail.com',_binary '',NULL,_binary '\0',NULL,NULL,'Anh Khang','$2a$10$X6iCcTtWDh58tXvCbJs.Yui8uqD41Xc.jnkIMgXU0QFU1ep/Gw8uS',NULL,'local',NULL,'user',NULL,2,'8a764117-a4ab-44a8-b047-6ec4e0594b14','2023-04-15 14:38:51.675000',NULL),(11,'2023-04-15 14:43:01.411000',_binary '\0','huynhkhang242@gmail.com',_binary '',NULL,_binary '\0',NULL,NULL,'Huỳnh Khang','$2a$10$WRIoq44V6R3RA76ZM8xmkep.xTEqafOszLsUUFpiKboFV4F/wVJI.',NULL,'local',NULL,'user',NULL,2,'24c32cb6-9a78-4aa8-ac84-03e4c09fff25','2023-04-15 14:43:01.411000',NULL),(12,'2023-04-15 14:45:28.510000',_binary '\0','baotran245@gmail.com',_binary '',NULL,_binary '\0',NULL,NULL,'Bảo Trấn','$2a$10$pYUfWBzdTFdxLyETTUOsyePfTpYN1hGc064NNLnwIM51FOGuNXzI6',NULL,'local',NULL,'user',NULL,2,'fe7b2a5c-051f-491d-af13-22def87eee8e','2023-04-15 14:45:28.510000',NULL),(13,'2023-04-15 14:48:42.866000',_binary '\0','minhphuc248@gmail.com',_binary '',NULL,_binary '\0',NULL,NULL,'Minh Phúc','$2a$10$oM9CjxWooSX3AxfvBvLhhOHgSPTOelCrF3Pnp2tjGBP1GT89gE56a',NULL,'local',NULL,'user',NULL,2,'6e1fd785-3f32-4554-bffe-2802bf47c5fc','2023-04-15 14:48:42.866000',NULL),(14,'2023-04-16 17:42:11.976000',_binary '\0','trongnam418@gmail.com',_binary '\0',NULL,_binary '\0',NULL,NULL,'Trọng Nam','$2a$10$sHKj9k6xTzrxHf3EVlwgYOfOzVUVGCbmgCFK8p7IgDhXhO3A07sKy',NULL,'local',NULL,'user',NULL,2,'e1aedcf8-75c1-46ca-a586-f5842ec705ff','2023-04-16 17:42:11.976000',NULL),(15,'2023-04-16 17:45:35.211000',_binary '\0','duydon545@gmail.com',_binary '\0',NULL,_binary '\0',NULL,NULL,'Duy Đòn','$2a$10$CNsTCdWxCa6eYvWdQQonKeRNnKE53mJbNJOuCnpHjUV1ieOXEt8ta',NULL,'local',NULL,'user',NULL,2,'ea7aa5bc-cb9e-4d1c-9f44-4abf24dee53c','2023-04-16 17:45:35.211000',NULL),(16,'2023-04-16 17:58:43.455000',_binary '\0','duynhat558@gmail.com',_binary '\0',NULL,_binary '\0',NULL,NULL,'Duy Nhất','$2a$10$8p99JWEH9NvZgkODmjK/POGlhHUJCKFegpLK2ac7yt5eOLV01h5BG',NULL,'local',NULL,'user',NULL,2,'3eefdad8-4650-46a9-a0d5-5bee903c0830','2023-04-16 17:58:43.455000',NULL),(17,'2023-04-17 20:50:54.153000',_binary '\0','duynhat550@gmail.com',_binary '\0',NULL,_binary '\0',NULL,NULL,'Duy Nhất','$2a$10$yMkPgzw5ZeJIRfwh7vCT3eOG1zioMJdzEiX.mHM27DZylaoy3/aDC',NULL,'local',NULL,'user',NULL,2,'757f58c4-b4ed-4e1b-9b59-90862fae0700','2023-04-17 20:50:54.153000',NULL),(18,'2023-04-18 07:15:11.168000',_binary '\0','duytham714@gmail.com',_binary '\0',NULL,_binary '\0',NULL,NULL,'Duy Thẩm','$2a$10$gfFmcNS7LXhZPL8VeGZDTOYkLV9D.GEHFpjhksh5oa8GjoChWC8VC',NULL,'local',NULL,'user',NULL,2,'f96a1253-b283-4ab2-876d-88e7ddd2cd6d','2023-04-18 07:15:11.168000',NULL),(19,'2023-04-18 07:18:58.412000',_binary '\0','tamcam718@gmail.com',_binary '\0',NULL,_binary '\0',NULL,NULL,'Tấm Cám','$2a$10$V6hJMdAK8Ag9To6tUSYI6OFUZ6jjAhrSs2kD6wbOtwVC3tshso5xa',NULL,'local',NULL,'user',NULL,2,'359be5cb-a9a6-4b63-8010-bee16423fbf0','2023-04-18 07:18:58.412000',NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_description`
--

DROP TABLE IF EXISTS `users_description`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users_description` (
  `user_id` bigint NOT NULL,
  `description_id` bigint NOT NULL,
  UNIQUE KEY `UK_q1124bw8mikk6cxp59297kg39` (`description_id`),
  KEY `FKkkqlg8ujknieqcmydajtiy62n` (`user_id`),
  CONSTRAINT `FK4378ff7dcdebdu9hcrk11d1c0` FOREIGN KEY (`description_id`) REFERENCES `user_des` (`id`),
  CONSTRAINT `FKkkqlg8ujknieqcmydajtiy62n` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_description`
--

LOCK TABLES `users_description` WRITE;
/*!40000 ALTER TABLE `users_description` DISABLE KEYS */;
INSERT INTO `users_description` VALUES (5,1),(5,2),(5,48),(5,49),(5,50),(5,51),(5,52),(5,53),(5,54),(5,55),(5,56),(5,57),(5,58),(5,59),(5,60),(5,61),(5,62),(5,63),(5,64),(5,65),(5,66),(5,67),(5,68),(5,69),(5,70),(5,71);
/*!40000 ALTER TABLE `users_description` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `verification`
--

DROP TABLE IF EXISTS `verification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `verification` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `expiration_time` datetime(6) DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `valid_flg` tinyint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `verification`
--

LOCK TABLES `verification` WRITE;
/*!40000 ALTER TABLE `verification` DISABLE KEYS */;
INSERT INTO `verification` VALUES (1,'2023-04-15 17:35:32.522000','927b60ff-1563-4df9-8ec7-662f4f7192a5',9,1),(2,'2023-04-15 17:39:30.169000','3a50182e-db9b-412f-8ba1-571e907a3257',10,1),(3,'2023-04-15 17:43:11.081000','5e0c9bdd-1e43-4a3d-9605-722774095829',11,1),(4,'2023-04-15 17:45:43.736000','734a2eb8-02c3-4784-b268-c2926cfd6ec6',12,1),(5,'2023-04-15 17:48:46.681000','733c1c6b-aeda-4684-bb1e-3711640617c8',13,1),(6,'2023-04-16 20:42:53.396000','303b45d7-0906-400d-8103-55698873fc93',14,1),(7,'2023-04-16 20:45:37.291000','6bb3169b-359c-4a58-99af-b8d5204f6f16',15,1),(8,'2023-04-16 20:58:44.669000','5312721a-d3e1-4a7a-b9d5-df2649770c49',16,1),(9,'2023-04-17 23:50:54.348000','3809e3c5-4657-4cd7-b4dd-94f1c00faee1',17,1),(10,'2023-04-18 10:15:30.225000','67bf6d1f-36f4-4f2f-86af-45df6bdf81ae',18,1),(11,'2023-04-18 10:19:21.958000','c925de45-071c-4d02-8d51-302d1cdb7298',19,1);
/*!40000 ALTER TABLE `verification` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-04-18 18:59:09
