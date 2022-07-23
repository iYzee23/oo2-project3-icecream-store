package sladoledi;

public class Generator {
	
	private Aparat aparat;
	
	public Generator(Aparat ap) {
		aparat = ap;
	}

	public Narudzbina generisi() {
		if (aparat.dohvBrojUkusa() == 0) return null;
		double rnd = Math.random();
		int vel = (rnd < 0.33 ? 100 : rnd < 0.66 ? 200 : 400);
		Sladoled s = new Sladoled(vel);
		int br = (int)(1 + Math.random() * 3);
		vel -= br * 20;
		vel /= 20;
		for (int i = 0; i < br; i++) {
			Ukus uk = aparat.dohvUkus((int)(Math.random() * aparat.dohvBrojUkusa()));
			int kol = (i != br-1 ? (int)(Math.random() * (vel + 1)) : vel);
			vel -= kol;
			s.dodajUkus(uk, kol * 20 + 20);
		}
		return new Narudzbina(s);
	}

}
