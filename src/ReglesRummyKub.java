import java.util.List;

public class ReglesRummyKub implements ReglesJoc{
	private static final long serialVersionUID = 1L;

	private final String NOM_VARIANT = "kub";

	@Override
	public String getNOM_VARIANT() {
		return NOM_VARIANT;
	}

	@Override
	public int getPUNTS_MINIM_OBERTURA() { return 30; }

	@Override
	public boolean getPERMET_JOQUERS() { return true; }

	@Override
	public int getNUM_BARALLES() { return 2; }

	@Override
	public int getNUM_JUGADORS_MAXIM() { return 4; }

	@Override
	public int getNUM_JUGADORS_MINIM() { return 2; }

	@Override
	public int getNumMinimPerCombinacions() { return 3; }

	@Override
	public boolean getPermetAfegirACombinacions() { return true; }

	@Override
	public boolean getPermetManipularTaula() { return true; }

	@Override
	public boolean getPermetBaixarCombinacions() { return true; }

	@Override
	public boolean getObligatoriDescartarAFinalDeTorn() { return false; }

	@Override
	public int getPuntsMaximsPerTancarMa() { return 0; }

	@Override
	public boolean getPermetRobarTotElDescart() {
		return false;
	}

	@Override
	public boolean getPermetRobarDeDescart() {
		return false;
	}

	@Override
	public int getValorPeca(Peca p) {
		return 0;
	}

	@Override
	public int pecesARepartir(int numJugadors) { return 14; }

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
