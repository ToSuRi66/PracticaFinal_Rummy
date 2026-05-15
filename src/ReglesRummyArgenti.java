public class ReglesRummyArgenti implements ReglesJoc {
	private static final long serialVersionUID = 1L;

	private final int NUM_JUGADORS_MAXIM = 4;
	private final int NUM_JUGADORS_MINIM = 2;
	private final int PUNTS_MINIM_OBERTURA = 30;
	private final boolean PERMET_JOQUERS = true;
	private final int NUM_BARALLES = 2;
	private final String NOM_VARIANT = "argenti";

	@Override public int getNUM_JUGADORS_MAXIM() { return NUM_JUGADORS_MAXIM; }
	@Override public int getNUM_JUGADORS_MINIM() { return NUM_JUGADORS_MINIM; }
	@Override public int getPUNTS_MINIM_OBERTURA() { return PUNTS_MINIM_OBERTURA; }
	@Override public boolean getPERMET_JOQUERS() { return PERMET_JOQUERS; }
	@Override public int getNUM_BARALLES() { return NUM_BARALLES; }
	@Override public String getNOM_VARIANT() { return NOM_VARIANT; }
	@Override public int getNumMinimPerCombinacions() { return 3; }
	@Override public int getPuntsMaximsPerTancarMa() { return 0; }
	@Override public int pecesARepartir(int numJugadors) { return 9; }
	@Override public boolean haGuanyat(Jugador j) { return j.getMa().isEmpty(); }

	@Override public boolean getPermetAfegirACombinacions() { return false; }
	@Override public boolean getPermetBaixarCombinacions() { return false; }
	@Override public boolean getPermetRobarDeDescart() { return false; }
	@Override public boolean getObligatoriDescartarAFinalDeTorn() { return false; }

	@Override
	public int getValorPeca(Peca p) {
		if (p.getPal() == PalPeca.COMODI) return 50;
		int v = p.getValor();
		if (v == 1) return 15;
		if (v >= 2 && v <= 7) return 5;
		return 10;
	}
}