/*
SQLyog Community v13.3.1 (64 bit)
MySQL - 9.4.0 : Database - projekat_programiranje_db
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`projekat_programiranje_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `projekat_programiranje_db`;

/*Table structure for table `gradovi` */

DROP TABLE IF EXISTS `gradovi`;

CREATE TABLE `gradovi` (
  `Naziv` varchar(255) NOT NULL,
  PRIMARY KEY (`Naziv`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `gradovi` */

insert  into `gradovi`(`Naziv`) values 
('Beograd'),
('Nis'),
('Novi Sad');

/*Table structure for table `iznajmljivanje` */

DROP TABLE IF EXISTS `iznajmljivanje`;

CREATE TABLE `iznajmljivanje` (
  `idIznajmljivanje` bigint NOT NULL AUTO_INCREMENT,
  `idKorisnik` bigint NOT NULL,
  `Naziv` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `idTrotinet` bigint NOT NULL,
  `datum` date NOT NULL,
  `pocetak` time(6) NOT NULL,
  `kraj` time(6) NOT NULL,
  PRIMARY KEY (`idIznajmljivanje`),
  KEY `idPoslovnogPartnera` (`idKorisnik`),
  KEY `iznajmljivanje_ibfk_5` (`idTrotinet`),
  KEY `idLokacija` (`Naziv`),
  CONSTRAINT `iznajmljivanje_ibfk_2` FOREIGN KEY (`idKorisnik`) REFERENCES `korisnik` (`idKorisnik`),
  CONSTRAINT `iznajmljivanje_ibfk_5` FOREIGN KEY (`idTrotinet`) REFERENCES `trotinet` (`idTrotinet`),
  CONSTRAINT `iznajmljivanje_ibfk_6` FOREIGN KEY (`Naziv`) REFERENCES `gradovi` (`Naziv`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `iznajmljivanje` */

insert  into `iznajmljivanje`(`idIznajmljivanje`,`idKorisnik`,`Naziv`,`idTrotinet`,`datum`,`pocetak`,`kraj`) values 
(7,4,'Nis',2,'2025-10-24','09:00:00.000000','10:00:00.000000'),
(10,4,'Novi Sad',3,'2025-10-22','13:00:00.000000','17:00:00.000000'),
(11,4,'Nis',2,'2025-10-24','08:00:00.000000','10:00:00.000000');

/*Table structure for table `korisnik` */

DROP TABLE IF EXISTS `korisnik`;

CREATE TABLE `korisnik` (
  `idKorisnik` bigint NOT NULL AUTO_INCREMENT,
  `ime` varchar(255) DEFAULT NULL,
  `prezime` varchar(255) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `pass` varchar(255) NOT NULL,
  PRIMARY KEY (`idKorisnik`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `korisnik` */

insert  into `korisnik`(`idKorisnik`,`ime`,`prezime`,`email`,`pass`) values 
(4,'Veljko','Cukanic','vc20220005@student.fon.bg.ac.rs','Veljko135$');

/*Table structure for table `stanje` */

DROP TABLE IF EXISTS `stanje`;

CREATE TABLE `stanje` (
  `idTrotinet` bigint NOT NULL,
  `Naziv` varchar(255) NOT NULL,
  `stanje` bigint NOT NULL,
  PRIMARY KEY (`idTrotinet`,`Naziv`),
  KEY `Naziv` (`Naziv`),
  CONSTRAINT `stanje_ibfk_1` FOREIGN KEY (`idTrotinet`) REFERENCES `trotinet` (`idTrotinet`),
  CONSTRAINT `stanje_ibfk_2` FOREIGN KEY (`Naziv`) REFERENCES `gradovi` (`Naziv`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `stanje` */

insert  into `stanje`(`idTrotinet`,`Naziv`,`stanje`) values 
(1,'Beograd',2),
(1,'Niš',2),
(1,'Novi Sad',2),
(2,'Beograd',2),
(2,'Niš',2),
(2,'Novi Sad',2),
(3,'Beograd',2),
(3,'Niš',2),
(3,'Novi Sad',2);

/*Table structure for table `trotinet` */

DROP TABLE IF EXISTS `trotinet`;

CREATE TABLE `trotinet` (
  `idTrotinet` bigint NOT NULL AUTO_INCREMENT,
  `naziv` varchar(255) NOT NULL,
  `cenaPoSatu` bigint NOT NULL,
  PRIMARY KEY (`idTrotinet`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `trotinet` */

insert  into `trotinet`(`idTrotinet`,`naziv`,`cenaPoSatu`) values 
(1,'TrotinetMini',800),
(2,'TrotinetX1',900),
(3,'TrotinetPro',1200);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
