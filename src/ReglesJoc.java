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
	int getNUM_BARALLES();
	int getNumMinimPerCombinacions();
	int getPuntsMaximsPerTancarMa();

	default boolean getPermetreTancarAmbPunts(){
		return false;
	}
	default boolean getPermetManipularTaula() { return false; }
	default boolean getPermetRobarTotElDescart() { return false; }
	default boolean getObligatoriDescartarAFinalDeTorn() {return true; }
	default boolean getPermetAfegirACombinacions() {return true; }
	default boolean getPermetBaixarCombinacions() { return true; }
	default boolean getPermetRobarDeDescart() { return true; }

	boolean getPERMET_JOQUERS();
	boolean haGuanyat(Jugador j);

	default public void inicialitzarPila(List<Peca> pila) {
		String[] pals = {"Diamants", "Piques", "Cors", "Trebols"};
		for (int b = 0; b < NUM_BARALLES; b++) {
			for (String pal : pals) {
				for (int i = 1; i <= 13; i++) {
					pila.add(new Peca(i, pal));
				}
			}
			if (PERMET_JOQUERS) {
				pila.add(new Peca(0, "COMODI"));
				pila.add(new Peca(0, "COMODI"));
			}
		}
	}

	default boolean esGrup(List<Peca> peces) {
		int valorReferencia = -1;
		for (Peca p : peces) {
			if (p.getGrup().equalsIgnoreCase("COMODI")) {
				continue;
			}
			if (valorReferencia == -1) {
				valorReferencia = p.getValor();
			} else if (p.getValor() != valorReferencia) {
				return false;
			}
		}
		return true;
	}

	default boolean esEscala(List<Peca> peces) {

		String palReferencia = null;
		List<Integer> valorsReals = new ArrayList<>();
		int numJoquers = 0;

		for (Peca p : peces) {
			if (p.getGrup().equalsIgnoreCase("COMODI")) {
				numJoquers++;
			} else {
				if (palReferencia == null) {
					palReferencia = p.getGrup();
				} else if (!p.getGrup().equals(palReferencia)) {
					return false;
				}
				valorsReals.add(p.getValor());
			}
		}

		Collections.sort(valorsReals);

		int foratsNecessaris = 0;
		for (int i = 0; i < valorsReals.size() - 1; i++) {
			int actual = valorsReals.get(i);
			int seguent = valorsReals.get( i + 1 );

			if (actual == seguent) {
				return false;
			}

			foratsNecessaris += (seguent - actual) - 1;

		}

		return numJoquers >= foratsNecessaris;

	}

	default boolean esCombinacioValida(List<Peca> peces) {

		if ( peces == null|| peces.size() < getNumMinimPerCombinacions() ) return false;

		return esGrup(peces) || esEscala(peces);
	}

	default boolean potTancatMa (Jugador j , int puntsActuals) {
		int limit = getPuntsMaximsPerTancarMa();

		if (limit == 0) return j.getMa().isEmpty();

		return puntsActuals <= limit;
	}
}
