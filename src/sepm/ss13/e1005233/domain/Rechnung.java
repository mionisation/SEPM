package sepm.ss13.e1005233.domain;

import java.sql.Timestamp;
import java.util.List;

/**
 * Data Transfer Object zum Kapseln von Rechnungsinformationen
 */
public class Rechnung {
	private Timestamp date;
	private String name, zahlungsart;
	private double gesamtpreis;
	private int gesamtstunden;
	private long telefon;
	private List<Buchung> buchungen;
	
	public Rechnung(Timestamp date) {
		this.date = date;
		this.name = null;
		this.zahlungsart = null;
		this.gesamtpreis = 0;
		this.gesamtstunden = 0;
		this.telefon = 0;
		this.buchungen = null;
	}	
	
	public Rechnung(Timestamp date, String name, String zahlungsart,
			double gesamtpreis, int gesamtstunden, long telefon,
			List<Buchung> buchungen) {
		this.date = date;
		this.name = name;
		this.zahlungsart = zahlungsart;
		this.gesamtpreis = gesamtpreis;
		this.gesamtstunden = gesamtstunden;
		this.telefon = telefon;
		this.buchungen = buchungen;
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
	public long getTelefon() {
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
	
	@Override
	public String toString() {
		return date.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		Rechnung r;
		if(obj instanceof Rechnung)
			r = (Rechnung) obj;
		else
			return false;
		return (this.buchungen.equals(r.getBuchungen()))&&(this.date.equals(r.getDate())&&
				this.gesamtpreis == r.getGesamtpreis())&& (this.gesamtstunden == r.getGesamtstunden()&&
				this.name.equals(r.getName()))&& this.telefon == r.getTelefon() && this.zahlungsart.equals(r.getZahlungsart());
	}
}
