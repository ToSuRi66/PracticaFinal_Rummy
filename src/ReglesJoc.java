import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public interface ReglesJoc extends java.io.Serializable {

	int NUM_BARALLES = 2;
	boolean PERMET_JOQUERS = true;

	String getNOM_VARIANT();
	int getValorPeca(Peca p);
	int getPUNTS_MINIM_OBERTURA();
	int pecesARepartir(int numJugadors);
	int getNUM_JUGADORS_MAXIM();
	int getNUM_JUGADORS_MINIM();
	int getNumMinimPerCombinacions();
	int getPuntsMaximsPerTancarMa();

	default boolean getPermetreTancarAmbPunts(){ return false; }
	default boolean getPermetManipularTaula() { return false; }
	default boolean getPermetRobarTotElDescart() { return false; }
	default boolean getObligatoriDescartarAFinalDeTorn() { return true; }
	default boolean getPermetAfegirACombinacions() { return true; }
	default boolean getPermetBaixarCombinacions() { return true; }
	default boolean getPermetRobarDeDescart() { return true; }
	default boolean getPermetDescartar() {return true; }

	boolean haGuanyat(Jugador j);

	default int getNUM_BARALLES() { return NUM_BARALLES; }
	default boolean getPERMET_JOQUERS() { return PERMET_JOQUERS; }

	default public void inicialitzarPila(List<Peca> pila) {
		for (int b = 0; b < getNUM_BARALLES(); b++) {
			for (PalPeca pal : PalPeca.values()) {
				if (pal == PalPeca.COMODI) continue;
				for (ValorPeca valor : ValorPeca.values()) {
					if (valor == ValorPeca.COMODI) continue;
					pila.add(new Peca(valor, pal));
				}
			}
			if (getPERMET_JOQUERS()) {
				pila.add(new Peca(ValorPeca.COMODI, PalPeca.COMODI));
				pila.add(new Peca(ValorPeca.COMODI, PalPeca.COMODI));
			}
		}
	}

	default boolean esGrup(List<Peca> peces) {
		int valorReferencia = -1;
		for (Peca p : peces) {
			if (p.getPal() == PalPeca.COMODI) continue;

			if (valorReferencia == -1) {
				valorReferencia = p.getValor();
			} else if (p.getValor() != valorReferencia) {
				return false;
			}
		}
		return true;
	}

	default boolean esEscala(List<Peca> peces) {
		PalPeca palReferencia = null;
		List<Integer> valorsReals = new ArrayList<>();
		int numJoquers = 0;

		for (Peca p : peces) {
			if (p.getPal() == PalPeca.COMODI) {
				numJoquers++;
			} else {
				if (palReferencia == null) {
					palReferencia = p.getPal();
				} else if (p.getPal() != palReferencia) {
					return false;
				}
				valorsReals.add(p.getValor());
			}
		}

		Collections.sort(valorsReals);

		int foratsNecessaris = 0;
		for (int i = 0; i < valorsReals.size() - 1; i++) {
			int actual = valorsReals.get(i);
			int seguent = valorsReals.get(i + 1);

			if (actual == seguent) return false;
			foratsNecessaris += (seguent - actual) - 1;
		}
		return numJoquers >= foratsNecessaris;
	}

	default boolean esCombinacioValida(List<Peca> peces) {
		if (peces == null || peces.size() < getNumMinimPerCombinacions()) return false;
		return esGrup(peces) || esEscala(peces);
	}

	default boolean potTancatMa(Jugador j, int puntsActuals) {
		int limit = getPuntsMaximsPerTancarMa();
		if (limit == 0) return j.getMa().isEmpty();
		return puntsActuals <= limit;
	}

	default int calcularPuntsDeadwood(List<Peca> ma) {
		int suma = 0;
		for (Peca p : ma) {
			suma += getValorPeca(p);
		}
		return suma;
	}
}
