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
-- Table structure for table `tipboardattach`
--

DROP TABLE IF EXISTS `tipboardattach`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipboardattach` (
  `uuid` varchar(100) NOT NULL,
  `uploadpath` varchar(200) DEFAULT NULL,
  `filename` varchar(100) DEFAULT NULL,
  `filetype` char(1) DEFAULT NULL,
  `bno` int(11) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipboardattach`
--

LOCK TABLES `tipboardattach` WRITE;
/*!40000 ALTER TABLE `tipboardattach` DISABLE KEYS */;
INSERT INTO `tipboardattach` VALUES ('03331e9f-6390-4b6a-ae0a-b2b58ff3e295',NULL,'간식.jpg','I',4),('10456a6f-cd94-4f06-8b60-68dbd57332bd',NULL,'10456a6f-cd94-4f06-8b60-68dbd57332bd_감기.jpg',NULL,21),('28f92f5c-772e-44b6-820c-6ec13a1629a7',NULL,'dog.jpg','I',14),('34d47b46-f865-476d-8daf-a1d9561b3949',NULL,'떡볶이2.jpg','I',1),('350ef458-660c-43b1-bb7b-e1f82d7a2141',NULL,'201908121759787664_4.jpg','I',10),('4652dea5-4fbe-4061-9e10-a4ac182ebfcc',NULL,'허브.jpg','I',7),('8166d6c5-de43-4f2a-9922-74a981963099',NULL,'허브요리.jpg','I',9),('a51ab2c0-8e57-4d30-ae55-31e8c79e27cc',NULL,'간식3.jpg','I',6),('a51f0aef-494c-4ae2-ba2f-5fe31d6a61c0',NULL,'허브티2.jpg','I',3),('acfcd168-91bb-4630-9c6c-5c09554c8a6c',NULL,'26.jpg','I',19),('b336c4bd-f8cb-4357-b7ff-8130ef55a968',NULL,'b336c4bd-f8cb-4357-b7ff-8130ef55a968_허브요리.jpg',NULL,20),('c43ddd2b-f59a-4578-b671-b66aab155adf',NULL,'natepann_temp14893209013931471459899.gif','I',18),('c6e776b7-9c40-4dc7-990b-a19d1a891752',NULL,'환절기.jpg','I',5),('ccbbb83d-2fd9-45ee-a3a9-af1c8d2b506c',NULL,'2.png','I',17),('e37a46b5-7e88-45b3-a5ed-21b2172b9f41',NULL,'잠든영민형.jpg','I',13),('f057fe08-51dd-432b-8ce2-6a43d9e4b8b7',NULL,'69679785_695725734267557_9010912791679385704_n.jpg','I',11);
/*!40000 ALTER TABLE `tipboardattach` ENABLE KEYS */;
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
