import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public interface ReglesJoc extends java.io.Serializable {

	String getNOM_VARIANT();

	int getPUNTS_MINIM_OBERTURA();

	boolean getPERMET_JOQUERS();

	boolean esCombinacioValida(List<Peca> peces);

	int pecesARepartir(int numJugadors);

	void inicialitzarPila(List<Peca> pila);

	boolean haGuanyat(Jugador j);

	int getNUM_JUGADORS_MAXIM();

	int getNUM_JUGADORS_MINIM();

	int getNUM_BARALLES();

	boolean getPermetAfegirACombinacions();

	boolean getPermetManipularTaula();

	boolean getPermetBaixarCombinacions();

	int getNumMinimPerCombinacions();

	boolean getObligatoriDescartarAFinalDeTorn();

	int getPuntsMaximsPerTancarMa();

	boolean getPermetRobarTotElDescart();

	boolean getPermetRobarDeDescart();

	int getValorPeca(Peca p);

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
}
