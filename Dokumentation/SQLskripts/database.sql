CREATE TABLE Pferde (
	id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
	name VARCHAR(30) NOT NULL,
	foto VARCHAR(100) NOT NULL,
	preis NUMERIC(10,2) NOT NULL,
	therapieart VARCHAR(30) CHECK(therapieart IN('HPR','HPV','Hippotherapie')),
	rasse VARCHAR(30),
	kinderfreundlich BOOLEAN NOT NULL,
	deleted BOOLEAN NOT NULL
);

CREATE TABLE Rechnungen (
	datum TIMESTAMP PRIMARY KEY,
	name VARCHAR(30) NOT NULL,
	gesamtpreis NUMERIC(20,2) NOT NULL,
	gesamtstunden INTEGER NOT NULL,
	zahlungsart VARCHAR(30) CHECK(zahlungsart IN('Barzahlung','Kreditkarte','Ueberweisung')),
	telefon BIGINT CHECK(telefon >= 1000 AND telefon <= 9223372036854775806) NOT NULL
);

CREATE TABLE Buchung (
	datum TIMESTAMP FOREIGN KEY REFERENCES Rechnungen(datum),
	id INTEGER FOREIGN KEY REFERENCES Pferde(id),
	preis NUMERIC(10,2) NOT NULL,
	stunden INTEGER NOT NULL
);