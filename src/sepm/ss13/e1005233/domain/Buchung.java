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

	public Buchung(Pferd pferd, Rechnung rechnung, int stunden, double preis) {
		this.pferd = pferd;
		this.rechnung = rechnung;
		this.stunden = stunden;
		this.preis = preis;
	}
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
	
	@Override
	public boolean equals(Object obj) {
		Buchung b;
		if(obj instanceof Buchung)
			b = (Buchung) obj;
		else
			return false;
		return (this.pferd.getId() == b.getPferd().getId()) &&
				(this.preis == b.getPreis()) && (this.stunden == b.getStunden()) &&
				(this.rechnung.getDate().equals(b.getRechnung().getDate()));
	}
}
