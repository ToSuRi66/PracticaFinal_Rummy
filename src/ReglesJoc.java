import java.util.List;

public interface ReglesJoc {
	boolean esCombinacioValida(List<Peca> peces);

	int pecesARepartir();

	void inicialitzarPila(List<Peca> pila);

	boolean haGuanyat(Jugador j);
}
