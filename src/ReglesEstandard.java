import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReglesEstandard implements ReglesJoc{

	private int numJugadorsMaxim = 4;
	private int numJugadorsMinim = 2;

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
		for (String pal : pals ) {
			for (int i = 1; i <= 13; i++) {
				pila.add( new Peca(i,pal));
			}
		}

		pila.add( new Peca(0, "COMODI"));
		pila.add( new Peca(0, "COMODI"));
	}

	@Override
	public boolean haGuanyat(Jugador j) {
		return j.getMa().isEmpty();
	}

	@Override
	public boolean esCombinacioValida(List<Peca> peces) {

		if ( peces == null|| peces.size() < 3 ) return false;

		return esGrup(peces) || esEscala(peces);
	}

	private boolean esGrup(List<Peca> peces) {
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

	private boolean esEscala(List<Peca> peces) {

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
	public int getNumJugadorsMaxim() {
		return numJugadorsMaxim;
	}

	@Override
	public int getNumJugadorsMinim() {
		return numJugadorsMinim;
	}
}
