-- Phase 2 SCHEMA
-- CS6400 Spring 2017
-- Team 081

CREATE SCHEMA `ASACS`;

CREATE TABLE ASACS.Site (
        Id INT NOT NULL AUTO_INCREMENT,
        ShortName VARCHAR(50) NOT NULL,
        StreetAddress VARCHAR(250) NOT NULL,
        City VARCHAR(100) NOT NULL,
        State VARCHAR(2) NOT NULL,
        Zip VARCHAR(10) NOT NULL,
        PhoneNumber VARCHAR(12) NOT NULL,
        PRIMARY KEY (Id)
);

CREATE TABLE ASACS.Account (
        UserName VARCHAR(25) NOT NULL,
        Password VARCHAR(50) NOT NULL,
        PRIMARY KEY (UserName)
);


CREATE TABLE ASACS.User(
        UserName VARCHAR(25) NOT NULL,
        Email VARCHAR(250) NOT NULL,
        FirstName VARCHAR(50) NOT NULL,
        LastName VARCHAR(50) NOT NULL,
        Role VARCHAR(25) NOT NULL,
        SiteId INT NOT NULL,
        PRIMARY KEY (Email),
        FOREIGN KEY (UserName) REFERENCES ASACS.Account (UserName),
        FOREIGN KEY (SiteId) REFERENCES ASACS.Site (Id)
);

-- Create Tables for ENUMs

CREATE TABLE ASACS.ItemStorageType(
		StorageType VARCHAR(50) NOT NULL,
		StorageTypeInt INT NOT NULL,
		PRIMARY KEY (StorageType)
);

CREATE TABLE ASACS.FoodItemCategory(
		Category VARCHAR(50) NOT NULL,
		CategoryInt INT NOT NULL,
		PRIMARY KEY (Category)
);	

CREATE TABLE ASACS.SupplyItemCategory(
		Category VARCHAR(50) NOT NULL,
		CategoryInt INT NOT NULL,
		PRIMARY KEY (Category)
);	

CREATE TABLE ASACS.RequestStatus(
		StatusDesc VARCHAR (50) NOT NULL,
		StatusInt INT NOT NULL,
		PRIMARY KEY (StatusDesc)
);

CREATE TABLE ASACS.ClientServiceCategory(
		Category VARCHAR(50) NOT NULL,
		CategoryInt INT NOT NULL,
		PRIMARY KEY (Category)
);

CREATE TABLE ASACS.FoodBank(
        SiteId INT NOT NULL,
        PRIMARY KEY (SiteId),
        FOREIGN KEY (SiteId) REFERENCES ASACS.Site (Id) ON DELETE CASCADE
);

CREATE TABLE ASACS.Item(
        ItemId INT NOT NULL AUTO_INCREMENT,
        SiteId INT NOT NULL,
        Category VARCHAR(50) NOT NULL,
        ExpireDate DATE NOT NULL,
        NumberUnits INT NOT NULL,
        Name VARCHAR(250) NOT NULL,
        StorageType VARCHAR(50) NOT NULL,
        PRIMARY KEY (ItemId), 
        UNIQUE (SiteId, Category), 
        FOREIGN KEY (SiteId) REFERENCES ASACS.FoodBank (SiteId),
	FOREIGN KEY (StorageType) REFERENCES ASACS.ItemStorageType (StorageType)
);

CREATE TABLE ASACS.FoodItem(
        ItemId INT NOT NULL,
        FoodCategory VARCHAR(50) NOT NULL,
        PRIMARY KEY (ItemId),
        FOREIGN KEY (ItemId) REFERENCES ASACS.Item (ItemId) ON DELETE CASCADE,
	FOREIGN KEY (FoodCategory) REFERENCES ASACS.FoodItemCategory (Category)
);

CREATE TABLE ASACS.SupplyItem(
        ItemId INT NOT NULL,
        SupplyCategory VARCHAR(50) NOT NULL,
        PRIMARY KEY (ItemId),
        FOREIGN KEY (ItemId) REFERENCES ASACS.Item (ItemId) ON DELETE CASCADE,
	FOREIGN KEY (SupplyCategory) REFERENCES ASACS.SupplyItemCategory (Category)
);

CREATE TABLE ASACS.RequestedItem(
	ItemId INT NOT NULL,
        RequesteeSiteId INT NOT NULL,
        ReqDateTime TIMESTAMP NOT NULL,
        UserId VARCHAR(250) NOT NULL,
        Status INT NOT NULL,
        NumRequested INT NOT NULL DEFAULT 1,
        NumFilled INT NOT NULL DEFAULT 0,
        PRIMARY KEY (RequesteeSiteId, ReqDateTime),
        FOREIGN KEY (ItemId)REFERENCES ASACS.Item(ItemId),
        FOREIGN KEY (UserId) REFERENCES ASACS.User(Email),
        FOREIGN KEY (RequesteeSiteId) REFERENCES ASACS.Site(Id)
);

CREATE TABLE ASACS.ClientService(
	SiteId INT NOT NULL,
        ServiceId INT NOT NULL AUTO_INCREMENT,
        ServiceCategory VARCHAR(50) NOT NULL,
        Description VARCHAR(500),
        HoursOperation VARCHAR(500) NOT NULL,
        ConditionUse VARCHAR(500) NOT NULL,
        PRIMARY KEY (ServiceId),
        FOREIGN KEY (SiteId) REFERENCES ASACS.Site (Id),
	FOREIGN KEY (ServiceCategory) REFERENCES ASACS.ClientServiceCategory (Category)
);


CREATE TABLE ASACS.SoupKitchen(
	SiteId INT NOT NULL,
        ServiceId INT NOT NULL,
        AvailableSeats INT NOT NULL DEFAULT 0,
        PRIMARY KEY (ServiceId),
        FOREIGN KEY (ServiceId) REFERENCES ASACS.ClientService (ServiceId) ON DELETE CASCADE,
        FOREIGN KEY (SiteId) REFERENCES ASACS.Site (Id)		
);

CREATE TABLE ASACS.Shelter(
	SiteId INT NOT NULL,
        ServiceId INT NOT NULL,
        FamilyRoomCount INT NOT NULL DEFAULT 0,
        MaleBunkCount INT NOT NULL DEFAULT 0,
        FemaleBunkCount INT NOT NULL DEFAULT 0,
        MixedBunkCount INT NOT NULL DEFAULT 0,
        TotalBunkCount INT NOT NULL DEFAULT 0,
        PRIMARY KEY (ServiceId),
        FOREIGN KEY (ServiceId) REFERENCES ASACS.ClientService (ServiceId) ON DELETE CASCADE,
        FOREIGN KEY (SiteId) REFERENCES ASACS.Site (Id)
	
);

CREATE TABLE ASACS.FoodPantry(
	SiteId INT NOT NULL,
        ServiceId INT NOT NULL,
        PRIMARY KEY (ServiceId),
        FOREIGN KEY (ServiceId) REFERENCES ASACS.ClientService (ServiceId) ON DELETE CASCADE,
        FOREIGN KEY (SiteId) REFERENCES ASACS.Site (Id) ON DELETE CASCADE
				
);

CREATE TABLE ASACS.Client(
        Identifier INT NOT NULL AUTO_INCREMENT,
        Description VARCHAR(500) NOT NULL,
        Name VARCHAR(100) NOT NULL,
        Phone VARCHAR(12),
        PRIMARY KEY (Identifier)
);

CREATE TABLE ASACS.WaitList(
        ClientId INT NOT NULL,
        SiteId INT NOT NULL,
        Position INT NOT NULL,
        PRIMARY KEY (SiteId,ClientId),
        FOREIGN KEY (SiteId) REFERENCES ASACS.Site (Id),
        FOREIGN KEY (ClientId) REFERENCES ASACS.Client(Identifier)
);

CREATE TABLE ASACS.LogEntry(
        ClientId INT NOT NULL,
        SiteId INT NOT NULL,
        DateTime TIMESTAMP NOT NULL,
        Notes VARCHAR(500),
        DescOfService VARCHAR(500),
        PRIMARY KEY (SiteId,DateTime),
        FOREIGN KEY (SiteId) REFERENCES ASACS.Site (Id),
        FOREIGN KEY (ClientId) REFERENCES ASACS.Client(Identifier)
);

-- Populate ENUM Tables
INSERT INTO ASACS.ItemStorageType(StorageType, StorageTypeInt)
VALUES ('Food', 0);
INSERT INTO ASACS.ItemStorageType(StorageType, StorageTypeInt)
VALUES ('Supply', 1);

INSERT INTO ASACS.FoodItemCategory(Category, CategoryInt)
VALUES('Vegetables', 0);
INSERT INTO ASACS.FoodItemCategory(Category, CategoryInt)
VALUES('Nuts/Grains/Beans', 1);	
INSERT INTO ASACS.FoodItemCategory(Category, CategoryInt)
VALUES('Meat/Seafood', 2);
INSERT INTO ASACS.FoodItemCategory(Category, CategoryInt)
VALUES('Dairy/Eggs', 3);
INSERT INTO ASACS.FoodItemCategory(Category, CategoryInt)
VALUES('Sauce/Condiment/Seasoning', 4);
INSERT INTO ASACS.FoodItemCategory(Category, CategoryInt)
VALUES('Juice/Drink', 5);

INSERT INTO ASACS.SupplyItemCategory(Category, CategoryInt)
VALUES('Personal Hygiene', 0);
INSERT INTO ASACS.SupplyItemCategory(Category, CategoryInt)
VALUES('Clothing', 1);	
INSERT INTO ASACS.SupplyItemCategory(Category, CategoryInt)
VALUES('Shelter', 2);	
INSERT INTO ASACS.SupplyItemCategory(Category, CategoryInt)
VALUES('Other', 3);		

INSERT INTO ASACS.RequestStatus(StatusDesc, StatusInt)
VALUES ('Pending',0);
INSERT INTO ASACS.RequestStatus(StatusDesc, StatusInt)
VALUES ('Closed', 1);

INSERT INTO ASACS.ClientServiceCategory(Category, CategoryInt)
VALUES ('FoodPantry', 0);
INSERT INTO ASACS.ClientServiceCategory(Category, CategoryInt)
VALUES ('Shelter', 1);
INSERT INTO ASACS.ClientServiceCategory(Category, CategoryInt)
VALUES ('SoupKitchen', 2);