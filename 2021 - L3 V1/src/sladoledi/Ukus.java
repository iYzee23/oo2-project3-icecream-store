package sladoledi;

import java.awt.Color;

public class Ukus {
	
	private String naziv;
	private Color boja;
	private int cena;

	public Ukus(String naz, Color col, int c) {
		naziv = naz;
		boja = col;
		cena = c;
	}
	
	public String dohvNaziv() {
		return naziv;
	}
	
	public Color dohvBoju() {
		return boja;
	}
	
	public int dohvCenu() {
		return cena;
	}
	
	public boolean jednak(Ukus u) {
		return naziv.equals(u.naziv);
	}
	
	@Override
	public String toString() {
		return "[" + naziv + "]";
	}

}
