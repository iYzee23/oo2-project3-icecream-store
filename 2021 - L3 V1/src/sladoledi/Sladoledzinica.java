package sladoledi;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Label;
import java.awt.List;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Sladoledzinica extends Frame {

	private static final long serialVersionUID = 1L;
	private Aparat aparat = new Aparat();
	private TextField naziv = new TextField(10);
	private TextField boja = new TextField(10);
	private Button dodaj = new Button("Dodaj ukus");
	private List narudz = new List();
	private Label novac = new Label("Novac: 0");
	private Button sladza = new Button("Otvori");

	public Sladoledzinica() {
		setBounds(500, 500, 500, 800);
		setTitle("Sladoledzinica");
		setResizable(false);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (aparat != null && aparat.mesto != null)
					aparat.mesto.prekini();
				dispose();
			}
		});
		
		popuniProzor();
		//pack();
		setVisible(true);
	}

	private void popuniProzor() {
		Panel sever = new Panel();
		aparat.spisak.lista = narudz;
		sever.add(narudz);
		sever.add(novac);
		sever.add(sladza);
		add(sever, BorderLayout.NORTH);
		
		sladza.addActionListener(ae -> {
			if (sladza.getLabel().equals("Otvori")) {
				aparat.spisak.kreni();
				sladza.setLabel("Zatvori");
				sladza.revalidate();
			}
			else {
				aparat.spisak.zaustavi();
				sladza.setLabel("Otvori");
				sladza.revalidate();
			}
		});
		
		Panel jug = new Panel();
		jug.setBackground(Color.cyan);
		Label naz = new Label("Naziv");
		naz.setFont(new Font("Arial", Font.BOLD, 20));
		Label boj = new Label("Boja");
		boj.setFont(new Font("Arial", Font.BOLD, 20));
		jug.add(naz);
		jug.add(naziv);
		jug.add(boj);
		jug.add(boja);
		jug.add(dodaj);
		add(jug, BorderLayout.SOUTH);
		add(aparat, BorderLayout.CENTER);
		
		dodaj.addActionListener(ae -> {
			if (!naziv.getText().equals("") && !boja.getText().equals("")) {
				int r = Integer.parseInt(boja.getText().substring(0, 2), 16);
				int g = Integer.parseInt(boja.getText().substring(2, 4), 16);
				int b = Integer.parseInt(boja.getText().substring(4), 16);
				Ukus uk = new Ukus(naziv.getText(), new Color(r, g, b), 50);
				aparat.dodajUkusDugme(uk);
			}
		});
	}
	
	public static void main(String[] args) {
		new Sladoledzinica();
	}

}
