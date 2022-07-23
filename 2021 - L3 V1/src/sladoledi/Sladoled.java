package sladoledi;

import java.util.ArrayList;

public class Sladoled {
	
	static class Elem {
		Ukus ukus;
		int kolicina;
		Elem(Ukus uk, int kol) { 
			ukus = uk;
			kolicina = kol;
		}
	}
	
	ArrayList<Elem> ukusi = new ArrayList<Elem>();
	private int maxVelicina, trVelicina;

	public Sladoled(int vel) {
		maxVelicina = vel;
	}
	
	public synchronized Sladoled dodajUkus(Ukus uk, int kol) {
		//if (uk == null) System.out.println("Null");
		if (kol + trVelicina > maxVelicina)
			kol = maxVelicina - trVelicina;
		if (kol > 0) {
			boolean ind = false;
			for (Elem el: ukusi) {
				if (el.ukus == uk) {
					el.kolicina += kol;
					ind = true;
					break;
				}
			}
			if (!ind) {
				Elem novi = new Elem(uk, kol);
				ukusi.add(novi);
			}
			trVelicina += kol;
		}
		return this;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Elem el: ukusi) sb.append(el.kolicina + "ml" + el.ukus);
		return sb.toString();
	}
	
	synchronized boolean pun() {
		return trVelicina == maxVelicina;
	}
	
	public synchronized int dohvCenu() {
		int counter = 0;
		for (Elem el: ukusi)
			counter += el.ukus.dohvCenu();
		return counter;
	}
	
	public synchronized boolean jednak(Sladoled s) {
		for (Elem el1: ukusi)
			for (Elem el2: s.ukusi)
				if (!el1.ukus.jednak(el2.ukus) || (el1.kolicina != el2.kolicina))
					return false;
		return true;
	}
	
	public synchronized void postaviVelicinuCase(int max) {
		if (max > trVelicina) maxVelicina = max;
	}
	
	public int dohvVelicinuCase() {
		return maxVelicina;
	}

}
