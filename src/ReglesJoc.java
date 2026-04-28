import java.util.List;

public interface ReglesJoc {
	int pecesAlRepartir();

	void inicialitzarPila(List<Peca> pila);

	boolean haGuanyat(Jugador j);
}
