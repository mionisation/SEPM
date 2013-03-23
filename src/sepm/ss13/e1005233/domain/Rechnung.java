package sepm.ss13.e1005233.domain;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

/**
 * Data Transfer Object zum Kapseln von Rechnungsinformationen
 */
public class Rechnung {
	private Timestamp date;
	private String name, zahlungsart;
	private double gesamtpreis;
	private int gesamtstunden, telefon;
	private List<Buchung> buchungen;
	
	public Rechnung(Timestamp date) {
		this.date = date;
	}
	
	
	
	//Getter und Setter zu den Informationen

	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getZahlungsart() {
		return zahlungsart;
	}
	public void setZahlungsart(String zahlungsart) {
		this.zahlungsart = zahlungsart;
	}
	public double getGesamtpreis() {
		return gesamtpreis;
	}
	public void setGesamtpreis(double gesamtpreis) {
		this.gesamtpreis = gesamtpreis;
	}
	public int getGesamtstunden() {
		return gesamtstunden;
	}
	public void setGesamtstunden(int gesamtstunden) {
		this.gesamtstunden = gesamtstunden;
	}
	public int getTelefon() {
		return telefon;
	}
	public void setTelefon(int telefon) {
		this.telefon = telefon;
	}
	
	
	public void addBuchung(Buchung b) {
		buchungen.add(b);
	}
	public List<Buchung> getBuchungen() {
		return buchungen;
	}
	
	public String toString() {
		return date.toString();
	}
}
