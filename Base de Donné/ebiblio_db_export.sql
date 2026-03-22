-- MySQL dump 10.13  Distrib 8.0.45, for Linux (x86_64)
--
-- Host: localhost    Database: ebiblio_db
-- ------------------------------------------------------
-- Server version	8.0.45-0ubuntu0.24.04.1

-- Création de la base de données
CREATE DATABASE IF NOT EXISTS ebiblio_db;
USE ebiblio_db;
/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Adherents`
--

DROP TABLE IF EXISTS `Adherents`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Adherents` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(100) NOT NULL,
  `prenom` varchar(100) NOT NULL,
  `type` enum('ETUDIANT','ENSEIGNANT','VISITEUR') NOT NULL,
  `idUnique` varchar(100) DEFAULT NULL,
  `adresse` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Adherents`
--

LOCK TABLES `Adherents` WRITE;
/*!40000 ALTER TABLE `Adherents` DISABLE KEYS */;
INSERT INTO `Adherents` VALUES (5,'Kassamba ','Souleymane','ENSEIGNANT',NULL,'Secteur 4 '),(6,'Zagre','Dieudonne','ETUDIANT',NULL,'Secteur 22'),(7,'Bambara ','Steve','VISITEUR',NULL,'Village');
/*!40000 ALTER TABLE `Adherents` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Emprunts`
--

DROP TABLE IF EXISTS `Emprunts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Emprunts` (
  `id` int NOT NULL AUTO_INCREMENT,
  `adherent_id` int NOT NULL,
  `livre_id` int NOT NULL,
  `date_emprunt` date NOT NULL,
  `date_limite` date NOT NULL,
  `date_retour` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `adherent_id` (`adherent_id`),
  KEY `livre_id` (`livre_id`),
  CONSTRAINT `Emprunts_ibfk_1` FOREIGN KEY (`adherent_id`) REFERENCES `Adherents` (`id`) ON DELETE CASCADE,
  CONSTRAINT `Emprunts_ibfk_2` FOREIGN KEY (`livre_id`) REFERENCES `Livres` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Emprunts`
--

LOCK TABLES `Emprunts` WRITE;
/*!40000 ALTER TABLE `Emprunts` DISABLE KEYS */;
INSERT INTO `Emprunts` VALUES (13,5,3,'2026-03-19','2026-03-26',NULL),(15,6,3,'2026-03-19','2026-03-26',NULL),(16,6,3,'2026-03-19','2026-03-26',NULL),(17,5,3,'2026-03-19','2026-03-26',NULL),(19,7,3,'2026-03-19','2026-03-26',NULL),(20,5,3,'2026-01-10','2025-12-10',NULL);
/*!40000 ALTER TABLE `Emprunts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Livres`
--

DROP TABLE IF EXISTS `Livres`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Livres` (
  `id` int NOT NULL AUTO_INCREMENT,
  `titre` varchar(200) NOT NULL,
  `auteur` varchar(100) NOT NULL,
  `type` enum('SCIENTIFIQUE','LITTERAIRE') NOT NULL,
  `editeur` varchar(255) DEFAULT NULL,
  `dateEdition` date DEFAULT NULL,
  `codeUnique` varchar(100) DEFAULT NULL,
  `nbExemplaires` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Livres`
--

LOCK TABLES `Livres` WRITE;
/*!40000 ALTER TABLE `Livres` DISABLE KEYS */;
INSERT INTO `Livres` VALUES (3,'Le Petit Prince','Machiavelle','LITTERAIRE','Square Enix','2000-09-25','001',18),(4,'L\'Etranger','Camus','SCIENTIFIQUE','Take Two','2000-04-15','002',5);
/*!40000 ALTER TABLE `Livres` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-03-19  3:58:22
