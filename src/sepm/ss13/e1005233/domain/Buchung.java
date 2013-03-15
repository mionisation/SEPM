package sepm.ss13.e1005233.domain;


/**
 * Data Transfer Object zum Kapseln von Buchungsinformationen
 */
public class Buchung {
	private Pferd pferd;
	private Rechnung rechnung;
	private int stunden;
	private double preis;
	
	//Getter und Setter zu den Informationen

	public Pferd getPferd() {
		return pferd;
	}
	public void setPferd(Pferd pferd) {
		this.pferd = pferd;
	}
	public Rechnung getRechnung() {
		return rechnung;
	}
	public void setRechnung(Rechnung rechnung) {
		this.rechnung = rechnung;
	}
	public int getStunden() {
		return stunden;
	}
	public void setStunden(int stunden) {
		this.stunden = stunden;
	}
	public double getPreis() {
		return preis;
	}
	public void setPreis(double preis) {
		this.preis = preis;
	}
}