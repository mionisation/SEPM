package sepm.ss13.e1005233.domain;

public class SuchPferd {
	
	private String name, therapieart, rasse;
	private double minpreis, maxpreis;
	private boolean kinderfreundlich;
	
	public SuchPferd(String name, String therapieart, String rasse,
					double minpreis, double maxpreis, boolean kinderfreundlich) {
		this.name = name;
		this.therapieart = therapieart;
		this.rasse = rasse;
		this.minpreis = minpreis;
		this.maxpreis = maxpreis;
		this.kinderfreundlich = kinderfreundlich;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public double getMinpreis() {
		return minpreis;
	}

	public void setMinpreis(double minpreis) {
		this.minpreis = minpreis;
	}

	public double getMaxpreis() {
		return maxpreis;
	}

	public void setMaxpreis(double maxpreis) {
		this.maxpreis = maxpreis;
	}

	public boolean isKinderfreundlich() {
		return kinderfreundlich;
	}

	public void setKinderfreundlich(boolean kinderfreundlich) {
		this.kinderfreundlich = kinderfreundlich;
	}
}
