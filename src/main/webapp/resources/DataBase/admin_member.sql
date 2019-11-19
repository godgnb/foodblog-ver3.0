-- MySQL dump 10.13  Distrib 8.0.18, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: admin
-- ------------------------------------------------------
-- Server version	8.0.18

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
-- Table structure for table `member`
--

DROP TABLE IF EXISTS `member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member` (
  `id` varchar(20) NOT NULL COMMENT '아이디',
  `passwd` varchar(20) NOT NULL COMMENT '패스워드',
  `name` varchar(20) NOT NULL COMMENT '이름',
  `phone` varchar(20) NOT NULL COMMENT '휴대폰번호',
  `email` varchar(20) NOT NULL COMMENT '이메일',
  `reg_date` datetime DEFAULT NULL COMMENT '가입날짜',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member`
--

LOCK TABLES `member` WRITE;
/*!40000 ALTER TABLE `member` DISABLE KEYS */;
INSERT INTO `member` VALUES ('aaaaa','12345678','나풀이','01011111111','aaa@naver.com','2019-10-28 22:15:09'),('admin','1234567890','관리자','01000000000','admin@naver.com','2019-10-29 22:17:30'),('ames1118','a12341234','명','0100000000','ames1118@naver.com','2019-11-05 10:39:00'),('bbbbb','12345678','감자','01022222222','bbb@naver.com','2019-10-28 22:15:45'),('ccccc','12345678','도갠','01033333333','ccc@naver.com','2019-10-25 20:08:34'),('ddddd','12345678','버즈','01044444444','ddd@naver.com','2019-10-25 20:09:44'),('eeeee','12345678','호롤로','01021458856','eee@naver.com','2019-11-13 21:46:19'),('fffff','12345678','나물','01055478325','fff@naver.com','2019-10-28 22:45:03'),('godrlarlxo','798255aa','김기태','01076697982','haraguyo@daum.net','2019-11-05 10:36:08'),('hyunseok2','rlarkdgh7','김현석','01089895094','rkdgh3527@naver.com','2019-11-05 10:37:44'),('pengsoo','vpdtnvpdvpd','펭수','1122','ehdgh3511@naver.com','2019-11-05 10:11:44'),('sslon','12345678','나고미','01022569666','esq@naver.com','2019-11-14 22:09:42'),('wwwww','12345678','나모두','01022548956','www@naver.com','2019-11-16 16:55:28'),('zzzzz','12345678','나풀이','01022222222','godgnb91@gmail.com','2019-11-04 14:48:55'),('펭귄펭펭펭','12345678','펭귄','010-0000-0000','chea__@naver.com','2019-11-05 10:41:08'),('하얀알파카','12345678','알파카','01000000000','wjdwlgs@naver.com','2019-11-05 10:36:44');
/*!40000 ALTER TABLE `member` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-11-19 11:38:12
