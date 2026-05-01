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
}
