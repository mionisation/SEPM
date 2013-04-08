INSERT INTO Pferde (id, name, foto, preis, therapieart, rasse, kinderfreundlich, deleted) VALUES (1, 'Wendy','/',15.5,'Hippotherapie','Lippizaner',TRUE, TRUE);
INSERT INTO Pferde (id, name, foto, preis, therapieart, rasse, kinderfreundlich, deleted) VALUES (2, 'Liese','/',12,'HPR','Haflinger',TRUE, FALSE);
INSERT INTO Pferde (id, name, foto, preis, therapieart, kinderfreundlich, deleted) VALUES (3, 'Anne','/',8,'HPV',FALSE, FALSE);


INSERT INTO Rechnungen (datum, name, gesamtpreis, gesamtstunden, zahlungsart, telefon) VALUES ('2012-12-20 12:37:35.35', 'Max Musterli', 220.5, 18, 'Ueberweisung', 12345);
INSERT INTO Rechnungen (datum, name, gesamtpreis, gesamtstunden, zahlungsart) VALUES ('2012-12-21 13:12:24.75', 'Sepp Sowieso', 40, 5, 'Barzahlung');

INSERT INTO Buchung (datum, id, preis, stunden) VALUES ('2012-12-20 12:37:35.35', 1, 10.2, 12);
INSERT INTO Buchung (datum, id, preis, stunden) VALUES ('2012-12-20 12:37:35.35', 2, 8.2, 5);
INSERT INTO Buchung (datum, id, preis, stunden) VALUES ('2012-12-20 12:37:35.35', 3, 7.2, 6);
INSERT INTO Buchung (datum, id, preis, stunden) VALUES ('2012-12-21 13:12:24.75', 3, 9, 8);