create table Address(addressId int not null, firstLine varchar(100) ,secondline varchar(100), City varchar(50),
zipCode varchar(15) ,primary key (addressId));


Create table Customer( customerId int not null, 	
	firstName varchar(50),	
	LastName varchar(50),	
	AddressId int,	
	primary key (customerId),	
	FOREIGN KEY (AddressId) REFERENCES Address(AddressId));