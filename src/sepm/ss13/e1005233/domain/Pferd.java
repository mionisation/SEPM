package sepm.ss13.e1005233.domain;

/**
 * Data Transfer Object zum Kapseln von Pferdinformationen
 */
public class Pferd {
	private int id;
	private String name, foto, therapieart, rasse;
	private double preis;
	private boolean kinderfreundlich, deleted;
	
	public Pferd(int id) {
		this.id = id;
		this.name = null;
		this.foto = null;
		this.therapieart = null;
		this.rasse = null;
		this.preis = 0;
		this.kinderfreundlich = false;
		this.deleted = false;
	}
	
	public Pferd(int id, String name, String foto, double preis, String therapieart, String rasse,
			 boolean kinderfreundlich, boolean deleted) {
		this.id = id;
		this.name = name;
		this.foto = foto;
		this.therapieart = therapieart;
		this.rasse = rasse;
		this.preis = preis;
		this.kinderfreundlich = kinderfreundlich;
		this.deleted = deleted;
	}
	
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
	
	public String toString() {
		return "" + id;
	}
	
	public boolean equals(Object o) {
		Pferd p;
		if(o instanceof Pferd)
			p = (Pferd)o;
		else
			return false;
		return	id == p.getId() && name.equals(p.getName()) && foto.equals(p.getFoto()) &&
				therapieart.equals(p.getTherapieart()) && rasse.equals(p.getRasse()) &&
				preis == p.getPreis() && kinderfreundlich == p.isKinderfreundlich() &&
				deleted == p.isDeleted();
	}
	
}
