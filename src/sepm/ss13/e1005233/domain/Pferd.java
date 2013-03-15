package sepm.ss13.e1005233.domain;

/**
 * Data Transfer Object zum Kapseln von Pferdinformationen
 */
public class Pferd {
	private int id;
	private String name, foto, therapieart, rasse;
	private double preis;
	private boolean kinderfreundlich, deleted;
	
	//Getter und Setter der Informationen
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}
	public String getTherapieart() {
		return therapieart;
	}
	public void setTherapieart(String therapieart) {
		this.therapieart = therapieart;
	}
	public String getRasse() {
		return rasse;
	}
	public void setRasse(String rasse) {
		this.rasse = rasse;
	}
	public double getPreis() {
		return preis;
	}
	public void setPreis(double preis) {
		this.preis = preis;
	}
	public boolean isKinderfreundlich() {
		return kinderfreundlich;
	}
	public void setKinderfreundlich(boolean kinderfreundlich) {
		this.kinderfreundlich = kinderfreundlich;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	
	
	
}
