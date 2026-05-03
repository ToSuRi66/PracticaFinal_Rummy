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
		String[] pals = {"Diamants","Piques","Cors","Trébol"};
		for (String pal : pals ) {
			for (int i = 1; i <= 13; i++) {
				pila.add( new Peca(i,pal));
			}
		}
	}

	@Override
	public boolean haGuanyat(Jugador j) {
		return j.getMa().isEmpty();
	}

	@Override
	public boolean esCombinacioValida(List<Peca> peces) {

		if (peces == null|| peces.size() < 3) return false;

		return esGrup(peces) || esEscala(peces);
	}

	private boolean esGrup(List<Peca> peces) {
		int valorReferencia = peces.get(0).getValor();
		for (Peca p : peces) {
			if (p.getValor() != valorReferencia) {
				return false;
			}
		}
		return true;
	}

	private boolean esEscala(List<Peca> peces) {

		if (peces.isEmpty() || peces.size() < 3) {return false;}

		String palReferencia = peces.get(0).getGrup();
		for (Peca p : peces) {
			if (!p.getGrup().equals(palReferencia)) {
				return false;
			}
		}

		peces.sort((p1 , p2) -> Integer.compare(p1.getValor() , p2.getValor()));

		for (int i = 1; i < peces.size(); i++) {
			int valorActual = peces.get(i).getValor();
			int valorAnterior = peces.get(i-1).getValor();

			if (valorActual != valorAnterior + 1) {
				return false;
			}
		}
		return true;
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
