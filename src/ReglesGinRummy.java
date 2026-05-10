import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReglesGinRummy implements ReglesJoc {

	private final int NUM_JUGADORS_MAXIM = 2;
	private final int NUM_JUGADORS_MINIM = 2;
	private final int PUNTS_MINIM_OBERTURA = 0;
	private final boolean PERMET_JOQUERS = false;
	private final int NUM_BARALLES = 1;
	private final String NOM_VARIANT = "gin";

	private static final long serialVersionUID = 1L;

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
		if (p.getGrup().equalsIgnoreCase("COMODI")) return 25;

		int v = p.getValor();
		if (v >= 11) {
			return 10;
		}
		;
		return v;
	}
}