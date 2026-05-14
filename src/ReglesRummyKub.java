import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReglesRummyKub implements ReglesJoc{
	private final int NUM_JUGADORS_MAXIM = 4;
	private final int NUM_JUGADORS_MINIM = 2;
	private final int PUNTS_MINIM_OBERTURA = 40;
	private final boolean PERMET_JOQUERS = false;
	private final int NUM_BARALLES = 2;
	private final String NOM_VARIANT = "classic";

	private static final long serialVersionUID = 1L;

	@Override
	public int pecesARepartir(int numJugadors) {
		if ( numJugadors == 2 ){
			return 10;
		} else {
			return 7;
		}
	}

	@Override
	public boolean haGuanyat(Jugador j) {
		return j.getMa().isEmpty();
	}

	@Override
	public boolean esCombinacioValida(List<Peca> peces) {

		if ( peces == null|| peces.size() < getNumMinimPerCombinacions() ) return false;

		return esGrup(peces) || esEscala(peces);
	}

	@Override
	public int getNUM_JUGADORS_MAXIM() {
		return NUM_JUGADORS_MAXIM;
	}

	@Override
	public int getNUM_JUGADORS_MINIM() {
		return NUM_JUGADORS_MINIM;
	}

	@Override
	public int getPUNTS_MINIM_OBERTURA() {
		return PUNTS_MINIM_OBERTURA;
	}

	@Override
	public boolean getPERMET_JOQUERS() {
		return PERMET_JOQUERS;
	}

	@Override
	public int getNUM_BARALLES() {
		return NUM_BARALLES;
	}

	@Override
	public boolean getPermetAfegirACombinacions() {
		return false;
	}

	@Override
	public boolean getPermetBaixarCombinacions() {
		return false;
	}

	@Override
	public boolean getPermetManipularTaula() {
		return false;
	}

	@Override
	public int getNumMinimPerCombinacions() {
		return 3;
	}

	@Override
	public boolean getObligatoriDescartarAFinalDeTorn() {
		return false;
	}

	@Override
	public int getPuntsMaximsPerTancarMa() {
		return 0;
	}

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
		if (p.getPal() ==  PalPeca.COMODI) return 25;

		int v = p.getValor();
		if (v == 1 || v >=11) {
			return 10;
		};
		return v;
	}

	@Override
	public String getNOM_VARIANT() {
		return NOM_VARIANT;
	}
}
