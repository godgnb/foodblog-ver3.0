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
-- Table structure for table `tipboard`
--

DROP TABLE IF EXISTS `tipboard`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipboard` (
  `num` int(11) NOT NULL COMMENT '글번호',
  `id` varchar(20) DEFAULT NULL COMMENT '아이디',
  `passwd` varchar(20) DEFAULT NULL COMMENT '패스워드',
  `subject` varchar(200) DEFAULT NULL COMMENT '글제목',
  `content` mediumtext COMMENT '글내용',
  `readcount` int(11) DEFAULT NULL COMMENT '조회수',
  `commcount` int(11) DEFAULT NULL COMMENT '댓글수',
  `ip` varchar(20) DEFAULT NULL COMMENT '작성자IP',
  `reg_date` datetime DEFAULT NULL COMMENT '글작성일',
  PRIMARY KEY (`num`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipboard`
--

LOCK TABLES `tipboard` WRITE;
/*!40000 ALTER TABLE `tipboard` DISABLE KEYS */;
INSERT INTO `tipboard` VALUES (1,'ccccc','1234','테스트 테스트 테스트  ','테\r\n스\r\n트\r\n입\r\n니\r\n다\r\n뾰\r\n룡',57,1,'0:0:0:0:0:0:0:1','2019-10-28 11:48:45'),(3,'bbbbb','1234','미세먼지를 걷어내는 건강 차','미\r\n세\r\n먼\r\n지',137,0,'0:0:0:0:0:0:0:1','2019-10-28 22:49:22'),(4,'fffff','1234','간단한 간식','맛잇겠다...',2,0,'0:0:0:0:0:0:0:1','2019-10-28 22:51:29'),(5,'ccccc','1234','환절기 목에 좋은 음식','환절기\r\n감기\r\n조심하세요',14,0,'0:0:0:0:0:0:0:1','2019-10-28 23:05:47'),(6,'aaaaa','1234','추운 겨울 몸을 목이는 간식','겨울엔 호떡',2,0,'0:0:0:0:0:0:0:1','2019-10-28 23:06:37'),(7,'fffff','1234','요리에 어울리는 허브','허브\r\n허브\r\n허브',6,0,'0:0:0:0:0:0:0:1','2019-10-28 23:07:24'),(9,'ddddd','1234','스테이크에 어울리는 허브','테스트',136,0,'0:0:0:0:0:0:0:1','2019-10-28 23:08:40'),(10,'pengsoo','123','펭하','펭하',4,0,'192.168.0.102','2019-11-05 10:12:29'),(11,'pengsoo','123','펭귄음식은 왜 없나요?ㅠㅡㅠ','ㅎㅇ',9,0,'192.168.0.102','2019-11-05 10:29:07'),(13,'godrlarlxo','798255aa','FC403이요','깨어나세요 용사여',17,1,'192.168.0.7','2019-11-05 10:38:07'),(14,'hyunseok2','123456','내이름은 현석잉','꺄',5,0,'192.168.0.17','2019-11-05 10:38:21'),(17,'펭귄펭펭펭','123444','몸에좋은 알파카음식','은없어',4,0,'192.168.0.14','2019-11-05 10:41:59'),(18,'펭귄펭펭펭','1234444','형 저 국주예요','asd',7,0,'192.168.0.14','2019-11-05 10:42:20'),(19,'펭귄펭펭펭','12344','펭하~!','펭하~!',10,1,'192.168.0.14','2019-11-05 10:42:55'),(20,'aaaaa','1234','테스트 입니다 테스트','테스트\r\n제발\r\n되라',2,0,'0:0:0:0:0:0:0:1','2019-11-17 15:49:50'),(21,'aaaaa','1234','테스트트트트','절반\r\n테스트\r\n성공',105,0,'0:0:0:0:0:0:0:1','2019-11-17 16:04:05');
/*!40000 ALTER TABLE `tipboard` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-11-19 11:38:13
