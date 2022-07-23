package sladoledi;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public class Aparat extends Panel {

	private static final long serialVersionUID = 1L;
	Mesto mesto = new Mesto(this);
	Generator gen = new Generator(this);
	Spisak spisak = new Spisak(4, gen, null);
	private Panel ukusi = new Panel();
	private Button prodaj = new Button("Prodaj");
	private Button obrisi = new Button("Obrisi");
	private Button o100 = new Button("100ml");
	private Button o200 = new Button("200ml");
	private Button o400 = new Button("400ml");
	private Label prikaz = new Label();
	private Map<Ukus, Button> sviUkusi = new HashMap<Ukus, Button>();

	public Aparat() {
		setLayout(new BorderLayout());
		popuniAparat();
	}
	
	private void popuniAparat() {
		Panel centar = new Panel(new BorderLayout());
		Panel desni = new Panel(new GridLayout(6, 1));
		//desni.setPreferredSize(new Dimension(200, 200));
		prodaj.setEnabled(false);
		obrisi.setEnabled(false);
		desni.add(prodaj);
		desni.add(obrisi);
		desni.add(o100);
		desni.add(o200);
		desni.add(o400);
		desni.add(mesto);
		centar.add(desni, BorderLayout.EAST);
		centar.add(ukusi, BorderLayout.CENTER);
		add(centar, BorderLayout.CENTER);
		ukusi.setBackground(Color.lightGray);
		
		prodaj.addActionListener(ae -> {
			if (mesto != null) {
				Sladoled s = mesto.dohvSladoled();
				System.out.println(s);
				mesto.prekini();
				//mesto.repaint();
				Narudzbina n = spisak.poklapa(s); 
				if (n != null) spisak.obrisiNarudzbinu(n);
			}
			prodaj.setEnabled(false);
			prodaj.revalidate();
		});
		
		obrisi.addActionListener(ae -> {
			if (mesto != null) mesto.prekini();
			obrisi.setEnabled(false);
			obrisi.revalidate();
		});
		
		o100.addActionListener(ae -> {
			mesto.dohvSladoled().postaviVelicinuCase(100);
		});
		
		o100.addMouseListener(new MouseAdapter() {
			Font f = o100.getFont();
			
			@Override
			public void mouseEntered(MouseEvent e) {
				o100.setFont(new Font("Arial", Font.BOLD, 20));
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				o100.setFont(f);
			}
			
		});
		
		o200.addActionListener(ae -> {
			mesto.dohvSladoled().postaviVelicinuCase(200);
		});
		
		o200.addMouseListener(new MouseAdapter() {
			Font f = o200.getFont();
			
			@Override
			public void mouseEntered(MouseEvent e) {
				o200.setFont(new Font("Arial", Font.BOLD, 20));
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				o200.setFont(f);
			}
			
		});
		
		o400.addActionListener(ae -> {
			mesto.dohvSladoled().postaviVelicinuCase(400);
		});
		
		o400.addMouseListener(new MouseAdapter() {
			Font f = o400.getFont();
			
			@Override
			public void mouseEntered(MouseEvent e) {
				o400.setFont(new Font("Arial", Font.BOLD, 20));
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				o400.setFont(f);
			}
			
		});
		
		Panel jug = new Panel();
		jug.setBackground(Color.gray);
		Label slad = new Label("Sladoled");
		slad.setFont(new Font("Arial", Font.BOLD, 20));
		jug.add(slad);
		prikaz.setAlignment(Label.CENTER);
		jug.add(prikaz);
		add(jug, BorderLayout.SOUTH);
	}

	public void postaviLabelu(String txt) {
		prikaz.setText(txt);
		prikaz.revalidate();
	}
	
	public synchronized void dodajUkusDugme(Ukus uk) {
		Button dugm = new Button(uk.dohvNaziv());
		dugm.setBackground(uk.dohvBoju());
		
		/*
		dugm.addActionListener(ae -> {
			mesto.postaviTrenutniUkus(uk);
			if (!mesto.zapocet) mesto.pokreni();
			else mesto.kreni();
		});
		*/
		
		dugm.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				mesto.postaviTrenutniUkus(uk);
				if (!mesto.zapocet) mesto.pokreni();
				else mesto.kreni();
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				mesto.postaviTrenutniUkus(null);
				mesto.zaustavi();
			}
		});
		
		sviUkusi.put(uk, dugm);
		ukusi.removeAll();
		if (sviUkusi.size() == 1) 
			ukusi.setLayout(new GridLayout(1, 1));
		else if (sviUkusi.size() == 2) 
			ukusi.setLayout(new GridLayout(1, 2));
		else
			ukusi.setLayout(new GridLayout(0, 2));
		for (Button b: sviUkusi.values())
			ukusi.add(b);
		ukusi.revalidate();
	}
	
	public void omoguciProdaju() {
		prodaj.setEnabled(true);
		prodaj.revalidate();
	}
	
	public int dohvBrojUkusa() {
		return sviUkusi.size();
	}
	
	public Ukus dohvUkus(int index) {
		if (index < 0 || index > sviUkusi.size()) return null;
		for (Ukus uk: sviUkusi.keySet()) {
			if (index == 0) return uk;
			index--;
		}
		return null;
	}

}
