/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Daniel
 * Created: 2016-05-13
 */





CREATE TABLE AUTH(
    AUTH_ID int NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1),
    LOGIN varchar(30) NOT NULL,
    PASS varchar(30) NOT NULL
);

CREATE TABLE ADRES(
    ADRES_ID int NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1),
    ULICA varchar(30),
    MIASTO varchar(30),
    KOD varchar(9)
);

CREATE TABLE PERSON (
    PERSON_ID int NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY(START WITH 1,INCREMENT BY 1),
    IMIE varchar(20),
    NAZWISKO varchar(30),
    TYP varchar(20) CONSTRAINT check_typ CHECK(TYP in ('klient','admin')),
    EMAIL varchar(50) UNIQUE,
    ADRES int NOT NULL CONSTRAINT adres_fk references ADRES(ADRES_ID) ON DELETE CASCADE,  
    AUTH int NOT NULL CONSTRAINT auth_fk references AUTH(AUTH_ID) ON DELETE CASCADE,
    UNIQUE(ADRES),
    UNIQUE(AUTH)
);

CREATE TABLE PRODUKT(
    PRODUKT_ID int NOT NULL PRIMARY KEY,
    NAZWA varchar(30) NOT NULL,
    CENA double NOT NULL
);

CREATE TABLE ZAMOWIENIE(
    NUMER int NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1),
    KLIENT int NOT NULL CONSTRAINT klient_fk references PERSON(PERSON_ID) ON DELETE CASCADE
);

CREATE TABLE P_ZAMOWIONY(
    ID int NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY(START WITH 1,INCREMENT BY 1),
    ID_PRODUKTU int NOT NULL,
    NAZWA varchar(30) NOT NULL,
    CENA double NOT NULL,
    ILOSC int NOT NULL,
    ID_ZAMOWIENIA int NOT NULL CONSTRAINT zamowienie_fk references ZAMOWIENIE(NUMER) ON DELETE CASCADE 
);


insert into AUTH(LOGIN,PASS) values('admin','admin1234');
insert into ADRES(MIASTO,ULICA,KOD) values('WARSZAWA','Sezamkowa 7/7','21-666');
insert into PERSON(IMIE,NAZWISKO,TYP,ADRES,AUTH,EMAIL) values('Jan','Kowalski','admin',1,1,'testmail1234@interia.pl');