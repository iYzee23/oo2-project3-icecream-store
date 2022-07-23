package gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Aparat extends Panel {

	Mesto mesto = new Mesto(this);
	Label slad = new Label("", Label.CENTER);
	Button prodaj = new Button("Prodaj");
	ArrayList<Button> ukusi = new ArrayList<>();
	Panel center;
	
	public Aparat() {
		setLayout(new BorderLayout());
		
		Panel p = new Panel(new GridLayout(2, 1));
		prodaj.setPreferredSize(new Dimension(200, 300));
		prodaj.setEnabled(false);
		p.add(prodaj);
		p.add(mesto);
		add(p, BorderLayout.EAST);
		
		Panel s = new Panel();
		s.setBackground(Color.lightGray);
		s.add(new Label("Sladoled", Label.LEFT));
		s.add(slad);
		add(s, BorderLayout.SOUTH);
		
		center = new Panel();
		center.setBackground(Color.gray);
		add(center, BorderLayout.CENTER);
		
		prodaj.addActionListener((ae) -> {
			System.out.println(slad.getText());
			slad.setText(null);
			slad.revalidate();
			Graphics g = mesto.getGraphics();
			g.clearRect(0, 0, mesto.getWidth(),	mesto.getHeight());
			mesto.prekini();
			prodaj.setEnabled(false);
		});
	}
	
	void azuriraj() {
		int n = ukusi.size();
		if (n == 0) return;
		remove(center);
		if (n == 1) center = new Panel(new GridLayout(1, 1));
		else if (n == 2) center = new Panel(new GridLayout(2, 1));
		else center = new Panel(new GridLayout(0, 2));
		for (Button b: ukusi) {
			center.add(b);
			b.addMouseListener(new MouseAdapter() {

				@Override
				public void mousePressed(MouseEvent e) {
					if (mesto.nit == null) mesto.repaint();
					else if (mesto.getSladoled().popCasa == 200) return;
					String naz = b.getActionCommand();
					Color col = b.getBackground();
					mesto.setUkus(new Ukus(naz, col));
					mesto.kreni();
				}

				@Override
				public void mouseReleased(MouseEvent e) {
					mesto.zaustavi();
					mesto.setUkus(null);
				}
				
			});
		}
		center.setBackground(Color.gray);
		add(center, BorderLayout.CENTER);
		revalidate();
	}

}
