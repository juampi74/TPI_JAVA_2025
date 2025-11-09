-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: tpjava
-- ------------------------------------------------------
-- Server version	8.0.37

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
-- Table structure for table `association`
--

DROP TABLE IF EXISTS `association`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `association` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `creation_date` date NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `association`
--

LOCK TABLES `association` WRITE;
/*!40000 ALTER TABLE `association` DISABLE KEYS */;
INSERT INTO `association` VALUES (1,'AFA','1893-02-21'),(3,'CONMEBOL','1916-07-09'),(4,'CBF','1914-06-08');
/*!40000 ALTER TABLE `association` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `club`
--

DROP TABLE IF EXISTS `club`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `club` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `foundation_date` date NOT NULL,
  `phone_number` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `badge_image` varchar(250) NOT NULL,
  `budget` double unsigned NOT NULL,
  `id_stadium` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `idx_club_stadium` (`id_stadium`),
  CONSTRAINT `fk_club_stadium` FOREIGN KEY (`id_stadium`) REFERENCES `stadium` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `club`
--

LOCK TABLES `club` WRITE;
/*!40000 ALTER TABLE `club` DISABLE KEYS */;
INSERT INTO `club` VALUES (1,'Boca Juniors','1905-04-03','(011) 5777-1200','infosocios@bocajuniors.com.ar','https://upload.wikimedia.org/wikipedia/commons/thumb/e/e3/Boca_Juniors_logo18.svg/270px-Boca_Juniors_logo18.svg.png',116000000,2),(5,'River Plate','1901-05-25','(011) 4789-1157','socios@cariverplate.com.ar','https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/Escudo_del_C_A_River_Plate.svg/330px-Escudo_del_C_A_River_Plate.svg.png',200000000,3),(9,'Rosario Central','1889-12-24','(341) 0472-8500','socios@rosariocentral.com','https://upload.wikimedia.org/wikipedia/en/thumb/c/ce/Rosario_Central_logo.svg/300px-Rosario_Central_logo.svg.png',6970000,6),(14,'Aldosivi','1913-01-01','3333333333','socios@aldosivi.com','https://upload.wikimedia.org/wikipedia/en/thumb/2/2a/Aldosivi_logo.svg/1170px-Aldosivi_logo.svg.png',5000000,99),(15,'Argentinos Juniors','1904-01-01','3333333333','socios@argentinosjuniors.com','https://upload.wikimedia.org/wikipedia/commons/thumb/5/57/Asociacion_Atletica_Argentinos_Juniors.svg/250px-Asociacion_Atletica_Argentinos_Juniors.svg.png',5000000,100),(16,'Atlético Tucumán','1902-01-01','3333333333','socios@atléticotucumán.com','https://upload.wikimedia.org/wikipedia/commons/thumb/9/92/Atletico_tucuman_nuevo2.svg/250px-Atletico_tucuman_nuevo2.svg.png',5000000,101),(17,'Banfield','1896-01-01','3333333333','socios@banfield.com','https://upload.wikimedia.org/wikipedia/commons/thumb/1/16/CA_Banfield_%282014%29.svg/120px-CA_Banfield_%282014%29.svg.png',5000000,102),(18,'Barracas Central','1904-01-01','3333333333','socios@barracascentral.com','https://upload.wikimedia.org/wikipedia/commons/thumb/9/99/Barracas_central_logo.svg/250px-Barracas_central_logo.svg.png',5000000,103),(19,'Belgrano de Córdoba','1905-01-01','3333333333','socios@belgranodecórdoba.com',' https://upload.wikimedia.org/wikipedia/commons/thumb/8/85/Escudo_Oficial_del_Club_Atl%C3%A9tico_Belgrano.png/250px-Escudo_Oficial_del_Club_Atl%C3%A9tico_Belgrano.png',5000000,104),(20,'Central Córdoba (Stgo del Est)','1919-01-01','3333333333','socios@centralcórdoba(stgodelest).com','https://upload.wikimedia.org/wikipedia/commons/thumb/9/92/Central_Cordoba_SdE_crest_%282025%29.svg/250px-Central_Cordoba_SdE_crest_%282025%29.svg.png',5000000,105),(21,'Defensa y Justicia','1935-01-01','3333333333','socios@defensayjusticia.com','https://upload.wikimedia.org/wikipedia/commons/thumb/3/3a/Escudo_del_Club_Defensa_y_Justicia.svg/1077px-Escudo_del_Club_Defensa_y_Justicia.svg.png',5000000,106),(22,'Deportivo Riestra','1931-01-01','3333333333','socios@deportivoriestra.com','https://upload.wikimedia.org/wikipedia/commons/thumb/a/ab/Escudo_del_Club_Deportivo_Riestra.svg/250px-Escudo_del_Club_Deportivo_Riestra.svg.png',5000000,107),(23,'Estudiantes de La Plata','1905-01-01','3333333333','socios@estudiantesdelaplata.com','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRjfCm1ejlptS2KnEyJ-BtjDvTee_qmHz61JA&s',5000000,108),(24,'Gimnasia y Esgrima La Plata','1887-01-01','3333333333','socios@gimnasiayesgrimalaplata.com','https://upload.wikimedia.org/wikipedia/commons/thumb/9/9e/Gimnasia_y_Esgrima_La_Plata_logo.png/250px-Gimnasia_y_Esgrima_La_Plata_logo.png',5000000,109),(25,'Godoy Cruz Antonio Tomba','1921-01-01','3333333333','socios@godoycruzantoniotomba.com','https://upload.wikimedia.org/wikipedia/commons/thumb/8/89/Logo_of_CD_Godoy_Cruz_Antonio_Tomba.png/250px-Logo_of_CD_Godoy_Cruz_Antonio_Tomba.png',5000000,110),(26,'Huracán','1908-01-01','3333333333','socios@huracán.com','https://upload.wikimedia.org/wikipedia/commons/thumb/d/dd/Emblema_oficial_del_Club_Atl%C3%A9tico_Hurac%C3%A1n.svg/739px-Emblema_oficial_del_Club_Atl%C3%A9tico_Hurac%C3%A1n.svg.png',5000000,111),(27,'Independiente','1905-01-01','3333333333','socios@independiente.com','https://upload.wikimedia.org/wikipedia/commons/thumb/d/db/Escudo_del_Club_Atl%C3%A9tico_Independiente.svg/1140px-Escudo_del_Club_Atl%C3%A9tico_Independiente.svg.png',5000000,112),(28,'Independiente Rivadavia (Mendoza)','1913-01-01','3333333333','socios@independienterivadavia(mendoza).com','https://upload.wikimedia.org/wikipedia/commons/thumb/7/7f/Escudo_del_Club_Independiente_Rivadavia.svg/1192px-Escudo_del_Club_Independiente_Rivadavia.svg.png',5000000,113),(29,'Platense','1905-01-01','3333333333','socios@platense.com','https://upload.wikimedia.org/wikipedia/commons/thumb/d/db/Club_Alt%C3%A9tico_Platense_crest_%282025%29.svg/120px-Club_Alt%C3%A9tico_Platense_crest_%282025%29.svg.png',5000000,114),(30,'Racing Club','1903-01-01','3333333333','socios@racingclub.com','https://upload.wikimedia.org/wikipedia/commons/thumb/5/56/Escudo_de_Racing_Club_%282014%29.svg/988px-Escudo_de_Racing_Club_%282014%29.svg.png',5000000,115),(31,'San Lorenzo de Almagro','1908-01-01','3333333333','socios@sanlorenzodealmagro.com','https://upload.wikimedia.org/wikipedia/commons/thumb/7/77/Escudo_del_Club_Atl%C3%A9tico_San_Lorenzo_de_Almagro.svg/1057px-Escudo_del_Club_Atl%C3%A9tico_San_Lorenzo_de_Almagro.svg.png',5000000,116),(32,'San Martín de San Juan','1907-01-01','3333333333','socios@sanmartíndesanjuan.com','https://upload.wikimedia.org/wikipedia/commons/thumb/5/57/San_Mart%C3%ADn_de_San_Juan.png/250px-San_Mart%C3%ADn_de_San_Juan.png',5000000,117),(33,'Sarmiento de Junín','1911-01-01','3333333333','socios@sarmientodejunín.com','https://upload.wikimedia.org/wikipedia/commons/thumb/9/92/Escudo_del_Club_Atl%C3%A9tico_Sarmiento_de_Jun%C3%ADn.svg/250px-Escudo_del_Club_Atl%C3%A9tico_Sarmiento_de_Jun%C3%ADn.svg.png',5000000,118),(34,'Talleres','1918-01-01','3333333333','socios@talleres.com','https://upload.wikimedia.org/wikipedia/commons/thumb/9/9b/Escudo_Talleres_2015.svg/250px-Escudo_Talleres_2015.svg.png',5000000,119),(35,'Tigre','1902-01-01','3333333333','socios@tigre.com','https://upload.wikimedia.org/wikipedia/commons/thumb/4/47/Escudo_del_Club_Atl%C3%A9tico_Tigre_-_2019.svg/250px-Escudo_del_Club_Atl%C3%A9tico_Tigre_-_2019.svg.png',5000000,120),(36,'Unión de Santa Fe','1907-01-01','3333333333','socios@unióndesantafe.com','https://upload.wikimedia.org/wikipedia/commons/thumb/7/7c/Escudo_club_Atl%C3%A9tico_Uni%C3%B3n_de_santa_fe.svg/250px-Escudo_club_Atl%C3%A9tico_Uni%C3%B3n_de_santa_fe.svg.png',5000000,121);
/*!40000 ALTER TABLE `club` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contract`
--

DROP TABLE IF EXISTS `contract`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `contract` (
  `id` int NOT NULL AUTO_INCREMENT,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `salary` decimal(10,2) NOT NULL,
  `release_clause` decimal(12,2) DEFAULT NULL,
  `release_date` date DEFAULT NULL,
  `id_person` int unsigned NOT NULL,
  `id_club` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_person` (`id_person`),
  KEY `id_club` (`id_club`),
  CONSTRAINT `contract_ibfk_1` FOREIGN KEY (`id_person`) REFERENCES `person` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `contract_ibfk_2` FOREIGN KEY (`id_club`) REFERENCES `club` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contract`
--

LOCK TABLES `contract` WRITE;
/*!40000 ALTER TABLE `contract` DISABLE KEYS */;
INSERT INTO `contract` VALUES (1,'2025-11-08','2025-12-10',500.00,10000.00,'2025-11-08',32356436,1),(2,'2023-07-19','2025-12-31',50000.00,50.00,'2025-11-08',91180180,5),(3,'2025-11-08','2025-11-27',400.00,5000.00,'2025-11-08',33472815,9),(4,'2025-10-31','2025-11-05',400000.00,500000000.00,NULL,33730749,1),(5,'2024-12-05','2026-12-06',400000.00,500000000.00,'2025-11-08',24976295,5),(6,'2024-12-04','2025-10-30',60.00,912.00,NULL,12487283,1);
/*!40000 ALTER TABLE `contract` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `person`
--

DROP TABLE IF EXISTS `person`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `person` (
  `id` int unsigned NOT NULL,
  `fullname` varchar(100) NOT NULL,
  `birthdate` date NOT NULL,
  `address` varchar(150) NOT NULL,
  `role` enum('PLAYER','TECHNICAL_DIRECTOR','PRESIDENT') NOT NULL,
  `dominant_foot` varchar(15) DEFAULT NULL,
  `jersey_number` int DEFAULT NULL,
  `height` double DEFAULT NULL,
  `weight` double DEFAULT NULL,
  `preferred_formation` varchar(50) DEFAULT NULL,
  `coaching_license` varchar(100) DEFAULT NULL,
  `license_obtained_date` date DEFAULT NULL,
  `management_policy` text,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `person`
--

LOCK TABLES `person` WRITE;
/*!40000 ALTER TABLE `person` DISABLE KEYS */;
INSERT INTO `person` VALUES (12487283,'Russo, Miguel Angel','1956-04-09','Corrientes 1726','TECHNICAL_DIRECTOR',NULL,NULL,NULL,NULL,'4-2-3-1','Licencia PRO - AFA','1988-11-17',NULL),(13788904,'Astore, Ignacio Enrique','1960-04-13','San Sebastian','PRESIDENT',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'Formacion integral de jugadores y compromiso social con la comunidad'),(14201845,'Holan, Ariel Enrique','1960-09-14','Lago Mascardi','TECHNICAL_DIRECTOR',NULL,NULL,NULL,NULL,'4-2-3-1','Licencia PRO - AFA','2015-03-02',NULL),(23760742,'Belloso, Gonzalo Luis','1974-03-30','Av. Pellegrini 294','PRESIDENT',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'Revalorizacion de las divisiones juveniles y fortalecimiento del sentido de pertenencia'),(24976295,'Gallardo, Marcelo Daniel','1976-01-18','Arroyo 738','TECHNICAL_DIRECTOR',NULL,NULL,NULL,NULL,'4-3-3 Ofensivo','Licencia PRO - AFA','2011-07-08',NULL),(26752869,'Riquelme, Juan Roman','1978-06-24','Montevideo 721','PRESIDENT',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'Desarrollo de juveniles y sostenibilidad economica'),(27287180,'Brito, Jorge Pablo','1979-06-29','9 de Julio 1824','PRESIDENT',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'Fortalecimiento de divisiones inferiores y desarrollo de talentos propios'),(30354328,'Fabbiani, Cristian Gaston','1983-09-03','Bv. Orono 361','TECHNICAL_DIRECTOR',NULL,NULL,NULL,NULL,'4-4-2','Licencia PRO - AFA','2021-04-23',NULL),(32356436,'Paredes, Leandro','1994-06-29','Dorrego 761','PLAYER','Derecho',5,1.82,75,NULL,NULL,NULL,NULL),(33472815,'Di Maria, Angel Fabian','1988-02-14','Funes Hills San Marino','PLAYER','Izquierdo',11,1.78,75,NULL,NULL,NULL,NULL),(33730749,'Banega Hernandez, Ever Maximiliano David','1988-06-29','Puerto Roldan','PLAYER','Derecho',5,1.75,73,NULL,NULL,NULL,NULL),(39413829,'Driussi, Sebastian','1996-02-09','Balcarce 493','PLAYER','Ambidiestro',15,1.79,82,NULL,NULL,NULL,NULL),(91180180,'Borja, Miguel Angel','1993-01-26','Colombia','PLAYER','No tiene',9,183,89,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `person` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stadium`
--

DROP TABLE IF EXISTS `stadium`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stadium` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `capacity` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=122 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stadium`
--

LOCK TABLES `stadium` WRITE;
/*!40000 ALTER TABLE `stadium` DISABLE KEYS */;
INSERT INTO `stadium` VALUES (2,'La Bombonera',57200),(3,'Monumental',85018),(6,'Gigante de Arroyito',51000),(7,'Coloso Marcelo Bielsa',42000),(8,'Monumental Antonio Vespucio Liberti',72054),(9,'Alberto J. Armando',59000),(10,'José Amalfitani',49540),(11,'Nuevo Gasómetro',43995),(12,'Ciudad de Lanús',47027),(13,'Ciudad de La Plata',53000),(14,'Marcelo Bielsa',42000),(15,'Centenario',25000),(16,'Julio Humberto Grondona',16300),(99,'José María Minella',35180),(100,'Diego Armando Maradona',24000),(101,'Monumental José Fierro',35200),(102,'Florencio Sola',34900),(103,'Claudio Chiqui Tapia',16300),(104,'Julio César Villagra',38500),(105,'Único Madre de Ciudades',30000),(106,'Norberto Tomaghello',20000),(107,'Guillermo Laza',8000),(108,'Jorge Luis Hirschi',32530),(109,'Juan Carmelo Zerillo',30973),(110,'Feliciano Gambarte',21000),(111,'Tomás Adolfo Ducó',48314),(112,'Libertadores de América',49592),(113,'Bautista Gargantini',24000),(114,'Ciudad de Vicente López',34530),(115,'Presidente Juan Domingo Perón',55880),(116,'Pedro Bidegain',47964),(117,'Ingeniero Hilario Sánchez',21500),(118,'Eva Perón',23156),(119,'Mario Alberto Kempes',57000),(120,'José Dellagiovanna',26282),(121,'15 de Abril',30000);
/*!40000 ALTER TABLE `stadium` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tournament`
--

DROP TABLE IF EXISTS `tournament`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tournament` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `format` varchar(100) NOT NULL,
  `season` varchar(50) NOT NULL,
  `id_association` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tournament_association` (`id_association`),
  KEY `idx_tournament_name` (`name`),
  KEY `idx_tournament_dates` (`start_date`,`end_date`),
  KEY `idx_tournament_season` (`season`),
  CONSTRAINT `fk_tournament_association` FOREIGN KEY (`id_association`) REFERENCES `association` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tournament`
--

LOCK TABLES `tournament` WRITE;
/*!40000 ALTER TABLE `tournament` DISABLE KEYS */;
INSERT INTO `tournament` VALUES (1,'Liga Profesional de Futbol - Torneo Clausura','2025-07-11','2025-12-14','Liga con Playoffs','2025',1),(2,'Brasileirao','2025-03-29','2025-12-21','Liga','2025',4);
/*!40000 ALTER TABLE `tournament` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-11-09 14:55:31
