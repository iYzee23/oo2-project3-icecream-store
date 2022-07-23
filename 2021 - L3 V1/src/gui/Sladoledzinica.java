package gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@SuppressWarnings("serial")
public class Sladoledzinica extends Frame {

	private Aparat aparat = new Aparat();
	private TextField naziv = new TextField(10);
	private TextField boja = new TextField(10);
	private Button dodaj = new Button("Dodaj ukus");
	
	public Sladoledzinica() {
		setBounds(700, 400, 500, 500);
		setTitle("Sladoledzinica");
		setResizable(false);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (aparat.mesto.nit != null)
					aparat.mesto.prekini();
				dispose();
			}
		});
		
		Panel s = new Panel();
		s.setBackground(Color.cyan);
		s.add(new Label("Naziv: "));
		s.add(naziv);
		s.add(new Label("Boja: "));
		s.add(boja);
		s.add(dodaj);
		add(s, BorderLayout.SOUTH);
		
		dodaj.addActionListener((ae) -> {
			String naz = naziv.getText();
			String bj = boja.getText();
			if (!naz.equals("") && !bj.equals("")) {
				Button bt = new Button(naz);
				int r = Integer.parseInt(bj.substring(0, 2), 16);
				int g = Integer.parseInt(bj.substring(2, 4), 16);
				int b = Integer.parseInt(bj.substring(4), 16);
				bt.setBackground(new Color(r, g, b));
				aparat.ukusi.add(bt);
				aparat.azuriraj();
			}
		});
		
		add(aparat, BorderLayout.CENTER);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new Sladoledzinica();
	}

}
