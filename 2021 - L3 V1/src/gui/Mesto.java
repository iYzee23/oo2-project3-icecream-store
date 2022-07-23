package gui;

import java.awt.Canvas;
import java.awt.Graphics;

@SuppressWarnings("serial")
public class Mesto extends Canvas implements Runnable {

	private Aparat aparat;
	private Sladoled slad;
	private Ukus trUk;
	Thread nit;
	private boolean radi;
	
	public Mesto(Aparat ap) {
		aparat = ap;
	}
	
	public Sladoled getSladoled() {
		return slad;
	}
	
	public void setUkus(Ukus uk) {
		trUk = uk;
	}
	
	@Override
	public void paint(Graphics g) {
		if (nit != null) prekini();
		while (nit != null) {
			synchronized (this) {
				try {
					wait();
				} catch (InterruptedException e) {}
			}
		}
		slad = new Sladoled(200);
		nit = new Thread(this);
		nit.start();
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				synchronized (this) {
					while (!radi) wait();
				}
				Graphics g = getGraphics();
				g.translate(0, getHeight() - 20);
				if (trUk != null) slad.dodaj(trUk, 20);
				Sladoled.Elem tek = slad.prvi;
				int s = 0;
				while (tek != null) {
					int h = (int)(getHeight() * 1.0 / 200 * 20);
					s += 20;
					g.setColor(tek.ukus.getBoja());
					g.fillRect(0, 0, getWidth(), h);
					g.translate(0, -19);
					if (s >= tek.kolicina) {
						tek = tek.next;
						s = 0;
					}
				}
				aparat.slad.setText(slad.toString());
				aparat.slad.revalidate();
				Thread.sleep(500);
				if (slad.popCasa == 200) {
					zaustavi();
					aparat.prodaj.setEnabled(true);
				}
			}
		} catch (InterruptedException e) {}
		synchronized (this) {
			radi = false;
			nit = null;
			notifyAll();
		}
	}
	
	public synchronized void kreni() {
		radi = true;
		notifyAll();
	}
	
	public void zaustavi() {
		radi = false;
	}
	
	public void prekini() {
		if (nit != null) nit.interrupt();
	}
	
	public boolean getRadi() {
		return radi;
	}

}
