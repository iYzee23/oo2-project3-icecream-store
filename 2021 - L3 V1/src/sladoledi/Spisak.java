package sladoledi;

import java.awt.List;

public class Spisak extends Thread {
	
	private Narudzbina[] nar;
	private int pop;
	private Generator gen;
	private boolean radi;
	List lista;

	public Spisak(int kap, Generator g, List lis) {
		nar = new Narudzbina[kap];
		gen = g;
		lista = lis;
		start();
	}
	
	@Override
	public void run() {
		try {
			while (!isInterrupted()) {
				synchronized (this) {
					while (pop == nar.length || !radi) wait();
				}
				Thread.sleep(4000);
				Narudzbina n = gen.generisi();
				if (n != null) dodajNarudzbinu(n);
			}
		} catch (InterruptedException e) {
			// bez efekta
		}
	}
	
	public synchronized void dodajNarudzbinu(Narudzbina n) {
		if (n == null) return;
		for (int i = 0; i < nar.length; i++) {
			if (nar[i] == null) {
				nar[i] = n;
				++pop;
				notifyAll();
				break;
			}
		}
		azurirajListu();
	}
	
	private void azurirajListu() {
		if (lista == null) return;
		lista.removeAll();
		for (Narudzbina n: nar)
			if (n != null)
				lista.add(n.dohvSladoled().toString());
		lista.revalidate();
	}

	public synchronized void obrisiNarudzbinu(Narudzbina n) {
		for (int i = 0; i < nar.length; i++) {
			if (nar[i] == n) {
				nar[i] = null;
				--pop;
				notifyAll();
				break;
			}
		}
		azurirajListu();
	}
	
	public synchronized Narudzbina poklapa(Sladoled s) {
		for (int i = 0; i < nar.length; i++)
			if (nar[i] != null && nar[i].dohvSladoled().jednak(s))
				return nar[i];
		return null;
	}

	public synchronized void kreni() {
		radi = true;
		notifyAll();
	}
	
	public synchronized void zaustavi() {
		radi = false;
		notifyAll();
	}
	
	public void prekini() {
		interrupt();
	}
	
}
