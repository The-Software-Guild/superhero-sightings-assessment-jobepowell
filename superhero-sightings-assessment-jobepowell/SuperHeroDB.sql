DROP DATABASE IF EXISTS superherodb;
DROP DATABASE IF EXISTS superherodbtest;
CREATE DATABASE superherodb;
CREATE DATABASE superherodbtest;

#Making DB for program's use
USE superherodb;
  
CREATE TABLE SuperPower(
  idSuperPower INT AUTO_INCREMENT NOT NULL,
  SuperPowerName VARCHAR(45) NOT NULL,
  PRIMARY KEY (idSuperPower));
  
  CREATE TABLE SuperOrganization(
  idSuperOrganization INT AUTO_INCREMENT NOT NULL,
  SuperOrganizationName VARCHAR(50) NOT NULL,
  SuperOrganizationDescription VARCHAR(255) NOT NULL,
  SuperOrganizationAddress VARCHAR(45) NOT NULL,
  SuperOrganizationContact VARCHAR(50) NOT NULL,
  PRIMARY KEY (idSuperOrganization));
  
  CREATE TABLE Location(
  idLocation INT AUTO_INCREMENT NOT NULL,
  LocationName VARCHAR(45) NOT NULL,
  LocationDescription VARCHAR(255) NOT NULL,
  LocationAddress VARCHAR(45) NOT NULL,
  LocationLatitude VARCHAR(20) NOT NULL,
  LocationLongitude VARCHAR(20) NOT NULL,
  PRIMARY KEY (idLocation));
  
  CREATE TABLE Sighting(
  idSighting INT AUTO_INCREMENT NOT NULL,
  SightingDate DATE NOT NULL,
  Location_idLocation INT NOT NULL,
  PRIMARY KEY (idSighting),
  CONSTRAINT fk_Sighting_Location1
    FOREIGN KEY (Location_idLocation)
    REFERENCES Location (idLocation));

CREATE TABLE Super (
  idSuper INT AUTO_INCREMENT NOT NULL,
  SuperName VARCHAR(45) NOT NULL,
  SuperDescription VARCHAR(255) NOT NULL,
  idSuperPower INT NOT NULL,
  PRIMARY KEY (idSuper),
  CONSTRAINT fk_Super_SuperPower
    FOREIGN KEY (idSuperPower)
    REFERENCES SuperPower (idSuperPower));
    
CREATE TABLE Super_SuperOrganization(
  Super_idSuper INT NOT NULL,
  SuperOrganization_idSuperOrganization INT NOT NULL,
  PRIMARY KEY (Super_idSuper,SuperOrganization_idSuperOrganization),
  CONSTRAINT fk_Super_SuperOrganization_Super
    FOREIGN KEY (Super_idSuper)
    REFERENCES Super(idSuper),
  CONSTRAINT fk_Super_SuperOrganization_SuperOrganization
    FOREIGN KEY (SuperOrganization_idSuperOrganization)
    REFERENCES SuperOrganization(idSuperOrganization));
    
CREATE TABLE Super_Sighting(
  Super_idSuper INT NOT NULL,
  Sighting_idSighting INT NOT NULL,
  PRIMARY KEY (Super_idSuper, Sighting_idSighting),
  CONSTRAINT fk_Super_Sighting_Super
    FOREIGN KEY (Super_idSuper)
    REFERENCES Super (idSuper),
  CONSTRAINT fk_Super_Sighting_Sighting
    FOREIGN KEY (Sighting_idSighting)
    REFERENCES Sighting (idSighting));

#Making DB for testing
USE superherodbtest;
  
CREATE TABLE SuperPower(
  idSuperPower INT AUTO_INCREMENT NOT NULL,
  SuperPowerName VARCHAR(45) NOT NULL,
  PRIMARY KEY (idSuperPower));
  
  CREATE TABLE SuperOrganization(
  idSuperOrganization INT AUTO_INCREMENT NOT NULL,
  SuperOrganizationName VARCHAR(50) NOT NULL,
  SuperOrganizationDescription VARCHAR(255) NOT NULL,
  SuperOrganizationAddress VARCHAR(45) NOT NULL,
  SuperOrganizationContact VARCHAR(50) NOT NULL,
  PRIMARY KEY (idSuperOrganization));
  
  CREATE TABLE Location(
  idLocation INT AUTO_INCREMENT NOT NULL,
  LocationName VARCHAR(45) NOT NULL,
  LocationDescription VARCHAR(255) NOT NULL,
  LocationAddress VARCHAR(45) NOT NULL,
  LocationLatitude VARCHAR(20) NOT NULL,
  LocationLongitude VARCHAR(20) NOT NULL,
  PRIMARY KEY (idLocation));
  
  CREATE TABLE Sighting(
  idSighting INT AUTO_INCREMENT NOT NULL,
  SightingDate DATE NOT NULL,
  Location_idLocation INT NOT NULL,
  PRIMARY KEY (idSighting),
  CONSTRAINT fk_Sighting_Location1
    FOREIGN KEY (Location_idLocation)
    REFERENCES Location (idLocation));

CREATE TABLE Super (
  idSuper INT AUTO_INCREMENT NOT NULL,
  SuperName VARCHAR(45) NOT NULL,
  SuperDescription VARCHAR(255) NOT NULL,
  idSuperPower INT NOT NULL,
  PRIMARY KEY (idSuper),
  CONSTRAINT fk_Super_SuperPower
    FOREIGN KEY (idSuperPower)
    REFERENCES SuperPower (idSuperPower));
    
CREATE TABLE Super_SuperOrganization(
  Super_idSuper INT NOT NULL,
  SuperOrganization_idSuperOrganization INT NOT NULL,
  PRIMARY KEY (Super_idSuper,SuperOrganization_idSuperOrganization),
  CONSTRAINT fk_Super_SuperOrganization_Super
    FOREIGN KEY (Super_idSuper)
    REFERENCES Super(idSuper),
  CONSTRAINT fk_Super_SuperOrganization_SuperOrganization
    FOREIGN KEY (SuperOrganization_idSuperOrganization)
    REFERENCES SuperOrganization(idSuperOrganization));
    
CREATE TABLE Super_Sighting(
  Super_idSuper INT NOT NULL,
  Sighting_idSighting INT NOT NULL,
  PRIMARY KEY (Super_idSuper, Sighting_idSighting),
  CONSTRAINT fk_Super_Sighting_Super
    FOREIGN KEY (Super_idSuper)
    REFERENCES Super (idSuper),
  CONSTRAINT fk_Super_Sighting_Sighting
    FOREIGN KEY (Sighting_idSighting)
    REFERENCES Sighting (idSighting));