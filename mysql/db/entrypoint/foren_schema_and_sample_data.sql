-- MySQL dump 10.13  Distrib 8.0.19, for Linux (x86_64)
--
-- Host: localhost    Database: foren
-- ------------------------------------------------------
-- Server version	5.7.29

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
-- Table structure for table `forum`
--

DROP TABLE IF EXISTS `forum`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `forum` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `forum`
--

LOCK TABLES `forum` WRITE;
/*!40000 ALTER TABLE `forum` DISABLE KEYS */;
INSERT INTO `forum` VALUES (1,'orga1 ist hier Admin, studentin1 Moderator, das Topic \"Informationen zur Klausur\" ist moderiert','Algorithmen und Datenstrukturen'),(2,'orga1 ist hier Admin, studentin2 Moderator, das Topic \"schlechte Witze aus dem Internet\" ist anonym, \"Ankündigungen\" ist moderiert','Programmierung'),(3,'orga2 ist hier Admin','Datenbanksysteme'),(4,'Wir lehren sie in diesem Modul die Betriebssysteme.\n         Bitte besuchen sie regelmäßig die Vorlesung, da es kein Skript geben wird!','Betriebssysteme'),(5,'Wir lehren sie in diesem Modul das Machine Learning.\n         Bitte besuchen sie regelmäßig die Vorlesung, da es kein Skript geben wird!','Machine Learning');
/*!40000 ALTER TABLE `forum` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post`
--

DROP TABLE IF EXISTS `post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `anonymous` bit(1) DEFAULT NULL,
  `date_time` datetime DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  `visible` bit(1) DEFAULT NULL,
  `author_username` varchar(255) DEFAULT NULL,
  `forum_id` bigint(20) DEFAULT NULL,
  `thread_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKieifb49vahhff48aej7jyinav` (`author_username`),
  KEY `FKjtlw3jkcj6wdccgfxbrk5jgmo` (`forum_id`),
  KEY `FKsq0vax8pchtnqe2fdwthd3xeu` (`thread_id`)
) ENGINE=MyISAM AUTO_INCREMENT=76 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post`
--

LOCK TABLES `post` WRITE;
/*!40000 ALTER TABLE `post` DISABLE KEYS */;
INSERT INTO `post` VALUES (1,_binary '\0','2020-03-27 10:23:51','Die Klausur findet am 05.05.2020 statt\nDie Räume werden noch bekannt gegeben.',_binary '','orga1',1,1),(2,_binary '\0','2020-03-27 10:25:23','Die Räume werden wie üblich nach Nachnamen verteilt :\nA-M : Hörsaal 5D\nN-Z : Hörsaal 5C',_binary '','studentin1',1,2),(3,_binary '\0','2020-03-27 10:26:13','Aufgrund von Corona wird die Klausur auf den 08.08.2020 verschoben',_binary '','orga1',1,3),(4,_binary '\0','2020-03-27 10:30:52','War die erste Woche noch im Urlaub.',_binary '','studentin2',1,4),(5,_binary '\0','2020-03-27 10:31:42','Das findest du im Ilias.',_binary '','studentin3',1,4),(6,_binary '\0','2020-03-27 10:33:05','Bald geht auch die Materialsammlung online, dann muss man nicht mehr auf das langsame Ilias warten.',_binary '','studentin1',1,4),(7,_binary '\0','2020-03-27 10:34:03','Hauptsache besser als Ilias !',_binary '','orga1',1,4),(8,_binary '\0','2020-03-27 10:35:27','danke euch :)',_binary '','studentin2',1,4),(9,_binary '\0','2020-03-27 10:36:56','Kann mir da jemand helfen ?',_binary '','studentin3',1,5),(10,_binary '\0','2020-03-27 10:38:07','Hier ist nen gutes Video dazu :\n\nhttps://www.youtube.com/watch?v=Hoixgm4-P4M',_binary '','studentin2',1,5),(11,_binary '\0','2020-03-27 10:40:23','Komme da einfach nicht auf den Baum ?',_binary '','studentin2',1,6),(12,_binary '\0','2020-03-27 10:41:21','Hallo ? Bitte irgendjemand ?',_binary '','studentin2',1,6),(13,_binary '\0','2020-03-27 10:42:25','Ganz ruhig ! Schau dir Seite 183 nochmal an, das Beispiel kannst du quasi direkt so anweden.',_binary '','studentin1',1,6),(14,_binary '','2020-03-27 10:51:41','Zum Glück ist das hier anonym !',_binary '','studentin1',2,7),(15,_binary '','2020-03-27 10:54:35','Did you hear about the monkeys who shared an Amazon account? They were Prime mates.',_binary '','studentin3',2,8),(16,_binary '','2020-03-27 10:55:11','We’ll we’ll we’ll…if it isn’t autocorrect.',_binary '','orga1',2,9),(17,_binary '','2020-03-27 10:56:54','The guy who invented auto-correct for smart phones passed away today.\n\nRestaurant in peace.\n',_binary '','studentin2',2,9),(18,_binary '\0','2020-03-27 11:00:04','nano ist beste !',_binary '','studentin3',2,10),(19,_binary '\0','2020-03-27 11:00:35','Dieser Beitrag wurde entfernt.',_binary '','Unbekannt',2,10),(20,_binary '\0','2020-03-27 11:01:08','Dieser Beitrag wurde entfernt.',_binary '','Unbekannt',2,10),(21,_binary '\0','2020-03-27 11:02:17','Nick hat mich überzeugt, IntelliJ kann einfach alles !',_binary '','studentin1',2,11),(22,_binary '\0','2020-03-27 11:02:45','Dieser Beitrag wurde entfernt.',_binary '','Unbekannt',2,11),(23,_binary '\0','2020-03-27 11:04:40','Wenn der Tonfall sich hier nicht ändert, wird moderiert ! ',_binary '','orga1',2,12),(24,_binary '\0','2020-03-27 11:05:24','Ja man ! Hauptsache kein nano !',_binary '','studentin2',2,11),(25,_binary '\0','2020-03-27 11:06:15','Aber Nano sieht doch so schön aus <3',_binary '','studentin3',2,11),(31,_binary '\0','2020-03-27 11:27:31','HAHA warum ist das falsch rum ? :D\n',_binary '','studentin2',3,14),(30,_binary '\0','2020-03-27 11:26:07','nice mir auch !',_binary '','studentin1',3,14),(29,_binary '\0','2020-03-27 11:25:56','Mir ist langweilig',_binary '','studentin2',3,14),(32,_binary '\0','2020-03-27 11:28:36','Hä das ist ja total weird',_binary '','studentin1',3,14),(33,_binary '\0','2020-03-27 11:29:33','passiert das , wenn ich sehr schnell Beiträge schreibe ?',_binary '','studentin1',3,14),(34,_binary '\0','2020-03-27 11:29:33','man das probiere ich mal aus !',_binary '','studentin2',3,14),(35,_binary '\0','2020-03-27 11:30:47','Andere Idee : was ist wenn du nen neuen Post vor dem Reload schickst ?',_binary '','studentin1',3,14),(36,_binary '\0','2020-03-27 11:31:15','dann schicke ich jetzt mal ohne reload ab !',_binary '','studentin2',3,14),(37,_binary '\0','2020-03-27 11:31:35','also kann ich das nicht reproduzieren ?',_binary '','studentin1',3,14),(38,_binary '\0','2020-03-27 11:31:48','Es scheint nicht so zu sein',_binary '','studentin2',3,14),(39,_binary '\0','2020-03-27 11:32:13','komisch, aber immerhin schlagen wir so die Vorlesungszeit tot',_binary '','studentin1',3,14),(40,_binary '\0','2020-03-27 11:32:35','Und was passiert jetzt ?',_binary '','studentin2',3,14),(41,_binary '\0','2020-03-27 11:32:51','DAs formular sollte doch gar nicht mehr das sein ?',_binary '','studentin1',3,14),(42,_binary '\0','2020-03-27 11:34:45','Ihr sollt der Vorlesung folgen nicht Quatsch machen !',_binary '','orga2',3,14),(43,_binary '\0','2020-03-27 11:35:05','Jetzt schreiben SIe ja auch schon hier',_binary '','studentin1',3,14),(51,_binary '\0','2020-03-27 11:54:19','das',_binary '','studentin2',1,17),(50,_binary '\0','2020-03-27 11:54:14','sieht',_binary '','studentin2',1,17),(49,_binary '\0','2020-03-27 11:54:10','wie',_binary '','studentin2',1,17),(48,_binary '\0','2020-03-27 11:54:02','Also',_binary '','studentin2',1,17),(52,_binary '\0','2020-03-27 11:54:28','aus',_binary '','studentin2',1,17),(53,_binary '\0','2020-03-27 11:54:48','wenn',_binary '','studentin2',1,17),(54,_binary '\0','2020-03-27 11:54:58','ich',_binary '','studentin2',1,17),(61,_binary '\0','2020-03-27 11:56:13','auf',_binary '','studentin2',1,17),(62,_binary '\0','2020-03-27 11:56:18','einem',_binary '','studentin2',1,17),(63,_binary '\0','2020-03-27 11:56:25','Baum',_binary '','studentin2',1,17),(64,_binary '\0','2020-03-27 11:56:30','eine',_binary '','studentin2',1,17),(65,_binary '\0','2020-03-27 11:56:35','Suche',_binary '','studentin2',1,17),(66,_binary '\0','2020-03-27 11:56:49','durchführen',_binary '','studentin2',1,17),(67,_binary '\0','2020-03-27 11:56:55','möchte',_binary '','studentin2',1,17),(68,_binary '\0','2020-03-27 11:57:57','Also',_binary '','studentin1',1,17),(69,_binary '\0','2020-03-27 11:58:06','da kannst',_binary '','studentin1',1,17),(70,_binary '\0','2020-03-27 11:58:13','du am',_binary '','studentin1',1,17),(71,_binary '\0','2020-03-27 11:58:22','besten',_binary '','studentin1',1,17),(72,_binary '\0','2020-03-27 11:58:28','dir die',_binary '','studentin1',1,17),(73,_binary '\0','2020-03-27 11:58:35','Folie',_binary '','studentin1',1,17),(74,_binary '\0','2020-03-27 12:29:14','Es beginnt am Montag um 10:30 in 5C',_binary '','orga2',4,18),(75,_binary '\0','2020-03-27 12:30:41','sfdgsgsdfs',_binary '\0','studentin1',2,19);
/*!40000 ALTER TABLE `post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `username` varchar(255) NOT NULL,
  `role` varchar(255) DEFAULT NULL,
  `forum_id` bigint(20) NOT NULL,
  PRIMARY KEY (`username`,`forum_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES ('orga1','ADMIN',1),('orga1','ADMIN',2),('orga2','ADMIN',3),('orga2','ADMIN',4),('studentin1','MODERATOR',1),('studentin2','MODERATOR',2);
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `thread`
--

DROP TABLE IF EXISTS `thread`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `thread` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `anonymous` bit(1) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `moderated` bit(1) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `visible` bit(1) DEFAULT NULL,
  `author_username` varchar(255) DEFAULT NULL,
  `forum_id` bigint(20) DEFAULT NULL,
  `topic_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKd0inefna9nbvowbjgbnjrxb1w` (`author_username`),
  KEY `FK91yd4uq5lexcwhhi1orfs7nn` (`forum_id`),
  KEY `FKkpxdvhiu73mdmtkrieddk3tpa` (`topic_id`)
) ENGINE=MyISAM AUTO_INCREMENT=20 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `thread`
--

LOCK TABLES `thread` WRITE;
/*!40000 ALTER TABLE `thread` DISABLE KEYS */;
INSERT INTO `thread` VALUES (1,_binary '\0','Die Klausur findet am 05.05.2020 statt\nDie Räume werden noch bekannt gegeben.',_binary '','Klausurtermin',_binary '','orga1',1,1),(2,_binary '\0','Die Räume werden wie üblich nach Nachnamen verteilt :\nA-M : Hörsaal 5D\nN-Z : Hörsaal 5C',_binary '','Raumverteilung',_binary '','studentin1',1,1),(3,_binary '\0','Aufgrund von Corona wird die Klausur auf den 08.08.2020 verschoben',_binary '','Verschiebung Klausurtermin',_binary '','orga1',1,1),(4,_binary '\0','War die erste Woche noch im Urlaub.',_binary '\0','Wo finde ich das Material ?',_binary '','studentin2',1,2),(5,_binary '\0','Kann mir da jemand helfen ?',_binary '\0','Brauche Hilfe bei Quicksort',_binary '','studentin3',1,2),(6,_binary '\0','Komme da einfach nicht auf den Baum ?',_binary '\0','Blatt 7 Nr. 4',_binary '','studentin2',1,2),(7,_binary '','Zum Glück ist das hier anonym !',_binary '\0','Was sind acht Hobbits? Ein Hobbyte!',_binary '','studentin1',2,3),(8,_binary '','Did you hear about the monkeys who shared an Amazon account? They were Prime mates.',_binary '\0','Amazon',_binary '','studentin3',2,3),(9,_binary '','We’ll we’ll we’ll…if it isn’t autocorrect.',_binary '\0','Für alle die Englisch können',_binary '','orga1',2,3),(10,_binary '\0','nano ist beste !',_binary '\0','NANO !!',_binary '','studentin3',2,4),(11,_binary '\0','Nick hat mich überzeugt, IntelliJ kann einfach alles !',_binary '\0','IntelliJ ftw !',_binary '','studentin1',2,4),(12,_binary '\0','Wenn der Tonfall sich hier nicht ändert, wird moderiert ! ',_binary '\0','Umgangsformen !',_binary '','orga1',2,4),(14,_binary '\0','Mir ist langweilig',_binary '\0','Hallo wie gehts',_binary '','studentin2',3,5),(17,_binary '\0','Also',_binary '\0','Hab hier mal ne längere Frage',_binary '','studentin2',1,7),(18,_binary '\0','Es beginnt am Montag um 10:30 in 5C',_binary '\0','Seminarbeginn',_binary '','orga2',4,8),(19,_binary '\0','sfdgsgsdfs',_binary '','fdafdsaf',_binary '\0','studentin1',2,9);
/*!40000 ALTER TABLE `thread` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `topic`
--

DROP TABLE IF EXISTS `topic`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `topic` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `anonymous` bit(1) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `moderated` bit(1) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `forum_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjbo776uy3da7iln995ky5u009` (`forum_id`)
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `topic`
--

LOCK TABLES `topic` WRITE;
/*!40000 ALTER TABLE `topic` DISABLE KEYS */;
INSERT INTO `topic` VALUES (1,_binary '\0','Hier finden Sie alle organisatorischen Informationen zur Klausur.',_binary '','Informationen zur Klausur',1),(2,_binary '\0','Hier könnt ihr alle Fragen bzgl. des Inhaltes der Vorlesung stellen.',_binary '\0','Inhaltliche Fragen',1),(3,_binary '','Hier könnt ihr anonym Witze posten.',_binary '\0','Schlechte Witze aus dem Internet',2),(4,_binary '\0','Mit welchem Editor/IDE sollen wir die Übungen vorführen ?',_binary '\0','Editorwahl',2),(5,_binary '\0','hier könnt ihr alle Fragen zu den Abgaben stellen',_binary '\0','Fragen zu den Blättern',3),(7,_binary '\0','hier wird das Paging demonstriert.',_binary '\0','Fragen zum Paging',1),(8,_binary '\0','Hier steht wichtiger Orga Stuff',_binary '\0','Ankündigungen',4),(9,_binary '\0','Hier steht alles wichtige drin',_binary '','Ankündigungen',2);
/*!40000 ALTER TABLE `topic` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `username` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('studentin','stud0@gmail.com','Bernhard Zimmermann'),('studentin1','stud1@gmail.com','Lisa Schmidt'),('studentin2','stud2@gmail.com','Anna Müller'),('studentin3','stud3@gmail.com','Martin Esel'),('orga','orga0@gmail.com','Peter Organisatorus'),('orga1','orga1@gmail.com','Meike Brummer'),('orga2','orga2@gmail.com','Marianne Baum'),('actuator','actuator@gmail.com','Heinrich Heine'),('Unbekannt','','Default');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_forum`
--

DROP TABLE IF EXISTS `user_forum`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_forum` (
  `username` varchar(255) NOT NULL,
  `id` bigint(20) NOT NULL,
  PRIMARY KEY (`username`,`id`),
  KEY `FKm3wbm30upe0ogtn3b9jjes43w` (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_forum`
--

LOCK TABLES `user_forum` WRITE;
/*!40000 ALTER TABLE `user_forum` DISABLE KEYS */;
INSERT INTO `user_forum` VALUES ('actuator',1),('actuator',2),('actuator',3),('actuator',4),('actuator',5),('orga1',1),('orga1',2),('orga2',3),('orga2',4),('studentin1',1),('studentin1',2),('studentin2',1),('studentin2',2),('studentin3',1),('studentin3',2),('studentin3',3),('studentin3',4);
/*!40000 ALTER TABLE `user_forum` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-04-02 15:34:44
