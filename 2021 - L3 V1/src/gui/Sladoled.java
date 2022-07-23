package gui;

public class Sladoled {
	
	static class Elem {
		Ukus ukus;
		int kolicina;
		Elem next;
		Elem(Ukus uk, int kol) {
			ukus = uk;
			kolicina = kol;
			next = null;
		}
	}
	
	Elem prvi, posl;
	private int mlCasa;
	int popCasa = 0;

	public Sladoled(int ml) {
		mlCasa = ml;
	}
	
	public Sladoled dodaj(Ukus u, int ml) {
		if (popCasa == mlCasa) return this;
		else if (popCasa + ml > mlCasa) ml = mlCasa - popCasa;
		popCasa += ml;
		Elem tek = prvi;
		while (tek != null) {
			if (tek.ukus.equals(u)) {
				tek.kolicina += ml;
				return this;
			}
			tek = tek.next;
		}
		Elem novi = new Elem(u, ml);
		if (prvi == null) prvi = novi;
		else posl.next = novi;
		posl = novi;
		return this;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Elem tek = prvi;
		while (tek != null) {
			sb.append(tek.kolicina + "ml" + tek.ukus);
			tek = tek.next;
		}
		return sb.toString();
	}

}
