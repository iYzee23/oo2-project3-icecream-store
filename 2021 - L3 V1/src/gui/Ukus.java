package gui;

import java.awt.Color;

public class Ukus {
	
	private String naziv;
	private Color boja;

	public Ukus(String naz, Color col) {
		boja = col;
		naziv = naz;
	}
	
	public String getNaziv() {
		return naziv;
	}
	
	public Color getBoja() {
		return boja;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Ukus)) return false;
		Ukus u = (Ukus)obj;
		return naziv == u.naziv;
	}
	
	@Override
	public String toString() {
		return "[" + naziv + "]";
	}

}
