import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReglesEstandard implements ReglesJoc{

	private final int NUM_JUGADORS_MAXIM = 4;
	private final int NUM_JUGADORS_MINIM = 2;
	private final int PUNTS_MINIM_OBERTURA = 40;
	private final boolean PERMET_JOQUERS = true;
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
	public void inicialitzarPila(List<Peca> pila) {
		String[] pals = {"Diamants","Piques","Cors","Trebols"};
		for (int b = 0; b < NUM_BARALLES; b++ ) {
			for (String pal : pals) {
				for (int i = 1; i <= 13; i++) {
					pila.add(new Peca(i, pal));
				}
			}
			pila.add( new Peca(0, "COMODI"));
			pila.add( new Peca(0, "COMODI"));
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
	public boolean esGrup(List<Peca> peces) {
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

	@Override
	public boolean esEscala(List<Peca> peces) {

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
		return true;
	}

	@Override
	public boolean getPermetBaixarCombinacions() {
		return true;
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
		return true;
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
		if (p.getGrup().equalsIgnoreCase("COMODI")) return 25;

		int v = p.getValor();
		if (v == 1 && v >=11) {
			return 10;
		};
		return v;
	}

	@Override
	public String getNOM_VARIANT() {
		return NOM_VARIANT;
	}
}
