import java.util.List;

public interface ReglesJoc {
	int pecesARepartir();

	void inicialitzarPila(List<Peca> pila);

	boolean haGuanyat(Jugador j);
}
