package sladoledi;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;

public class Mesto extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	private Thread nit;
	private Aparat aparat;
	private Sladoled sladoled;
	private boolean radi;
	boolean zapocet;
	private Ukus trenutni;

	public Mesto(Aparat ap) {
		this.setPreferredSize(new Dimension(200, 500));
		// setBackground(Color.green);
		aparat = ap;
	}
	
	public Sladoled dohvSladoled() {
		return sladoled;
	}
	
	@Override
	public void paint(Graphics g) {
		if (sladoled == null) return;
		int w = getWidth();
		int h = (int) (20.0 / sladoled.dohvVelicinuCase() * getHeight());
		g.translate(0, getHeight() - h);
		for (Sladoled.Elem uk: sladoled.ukusi) {
			int kol = uk.kolicina;
			g.setColor(uk.ukus.dohvBoju());
			while (kol > 0) {
				g.fillRect(0, 0, w, h);
				g.translate(0, -h);
				kol -= 20;
			}
		}
	}
	
	public boolean tocenjeUToku() {
		return radi;
	}
	
	public void postaviTrenutniUkus(Ukus uk) {
		trenutni = uk;
	}

	@Override
	public void run() {
		try {
			while (!nit.isInterrupted()) {
				synchronized (this) {
					while (!radi) wait();
				}
				Thread.sleep(500);
				//if (trenutni != null) System.out.println("uso3");
				//if (!sladoled.pun()) System.out.println("uso4");
				if (trenutni != null && !sladoled.pun()) {
					//System.out.println("Uso");
					sladoled.dodajUkus(trenutni, 20);
				}
				if (sladoled != null && sladoled.pun())
					aparat.omoguciProdaju();
				aparat.postaviLabelu(sladoled.toString());
				synchronized (this) {
					repaint();
				}
				//trenutni = null;
			}
		} catch (InterruptedException e) {
			// bez efekta
		}
		synchronized (this) {
			nit = null;
			notifyAll();
		}
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
		radi = false;
		if (nit != null) nit.interrupt();
		sladoled = null;
		trenutni = null;
		zapocet = false;
		repaint();
	}
	
	public synchronized void pokreni() {
		if (nit != null) prekini();
		while (nit != null) {
			try {
				wait();
			} catch (InterruptedException e) {
				// bez efekta
			}
		}
		radi = true;
		sladoled = new Sladoled(200);
		zapocet = true;
		nit = new Thread(this);
		nit.start();
	}

}
