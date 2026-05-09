import java.util.List;

public class ReglesRummyArgenti implements ReglesJoc{
	private static final long serialVersionUID = 1L;

	@Override public int getPUNTS_MINIM_OBERTURA() { return 30; }

	@Override public boolean getPERMET_JOQUERS() { return true; }

	@Override public int getNUM_BARALLES() { return 2; }

	@Override public int getNUM_JUGADORS_MAXIM() { return 6; }

	@Override public int getNUM_JUGADORS_MINIM() { return 2; }

	@Override public int getNumMinimPerCombinacions() { return 3; }

	@Override public boolean getPermetAfegirACombinacions() { return true; }

	@Override public boolean getPermetManipularTaula() { return false; }

	@Override public boolean getPermetBaixarCombinacions() { return true; }

	@Override public boolean getObligatoriDescartarAFinalDeTorn() { return true; }

	@Override public int getPuntsMaximsPerTancarMa() { return 0; }

	@Override
	public boolean getPermetRobarTotElDescart() {
		return false;
	}

	@Override
	public int getValorPeca(Peca p) {
		return 0;
	}

	@Override
	public int pecesARepartir(int numJugadors) { return 13; }

	@Override
	public void inicialitzarPila(List<Peca> pila) {
	}

	@Override
	public boolean esCombinacioValida(List<Peca> peces) {

		return false;
	}

	@Override
	public boolean haGuanyat(Jugador j) { return j.getMa().isEmpty(); }

}
