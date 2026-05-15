import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ReglesGinRummy implements ReglesJoc {
	private static final long serialVersionUID = 1L;

	private final int NUM_JUGADORS_MAXIM = 2;
	private final int NUM_JUGADORS_MINIM = 2;
	private final int PUNTS_MINIM_OBERTURA = 0;
	private final boolean PERMET_JOQUERS = false;
	private final int NUM_BARALLES = 1;
	private final String NOM_VARIANT = "gin";

	@Override public int getNUM_JUGADORS_MAXIM() { return NUM_JUGADORS_MAXIM; }
	@Override public int getNUM_JUGADORS_MINIM() { return NUM_JUGADORS_MINIM; }
	@Override public int getPUNTS_MINIM_OBERTURA() { return PUNTS_MINIM_OBERTURA; }
	@Override public boolean getPERMET_JOQUERS() { return PERMET_JOQUERS; }
	@Override public int getNUM_BARALLES() { return NUM_BARALLES; }
	@Override public String getNOM_VARIANT() { return NOM_VARIANT; }

	@Override public boolean getPermetAfegirACombinacions() { return false; }
	@Override public boolean getPermetBaixarCombinacions() { return false; }
	@Override public boolean getPermetreTancarAmbPunts() { return true; }

	@Override public int getNumMinimPerCombinacions() { return 3; }
	@Override public int getPuntsMaximsPerTancarMa() { return 10; }

	@Override public int pecesARepartir(int numJugadors) { return 10; }
	@Override public boolean haGuanyat(Jugador j) { return j.getMa().isEmpty(); }

	@Override
	public int getValorPeca(Peca p) {
		if (p.getPal() == PalPeca.COMODI) return 25;
		int v = p.getValor();
		return (v >= 11) ? 10 : v;
	}

	@Override
	public int calcularPuntsDeadwood(List<Peca> ma) {
		List<Peca> copiaMa = new ArrayList<>(ma);

		// Ordenació adaptada per fer servir el getPal() de l'Enum
		copiaMa.sort((p1, p2) -> {
			int c = p1.getPal().compareTo(p2.getPal());
			return (c != 0) ? c : Integer.compare(p1.getValor(), p2.getValor());
		});

		for (int i = 0; i < copiaMa.size();) {
			List<Peca> escalaPotencial = new ArrayList<>();
			escalaPotencial.add(copiaMa.get(i));

			for (int j = i + 1; j < copiaMa.size(); j++) {
				Peca seguent = copiaMa.get(j);
				Peca ultimaAfegida = escalaPotencial.get(escalaPotencial.size() - 1);

				if (seguent.getPal() == ultimaAfegida.getPal() && seguent.getValor() == ultimaAfegida.getValor() + 1) {
					escalaPotencial.add(seguent);
				} else {
					break;
				}
			}

			if (escalaPotencial.size() >= 3) {
				for (Peca p : escalaPotencial) copiaMa.remove(p);
				i = 0;
			} else {
				i++;
			}
		}

		copiaMa.sort(Comparator.comparingInt(Peca::getValor));

		for (int i = 0; i < copiaMa.size();) {
			int valorRef = copiaMa.get(i).getValor();
			int comptador = 0;

			for (int j = i; j < copiaMa.size() && copiaMa.get(j).getValor() == valorRef; j++) {
				comptador++;
			}

			if (comptador >= 3) {
				for (int j = 0; j < comptador; j++) copiaMa.remove(i);
				i = 0;
			} else {
				i++;
			}
		}

		int suma = 0;
		for (Peca p : copiaMa) suma += getValorPeca(p);
		return suma;
	}
}