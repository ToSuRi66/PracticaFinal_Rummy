import java.util.List;

public class ReglesEstandard implements ReglesJoc{

	@Override
	public int pecesARepartir() {
		return 7;
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

		if (peces.size() < 3) return false;

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
		return false;
	}
}
