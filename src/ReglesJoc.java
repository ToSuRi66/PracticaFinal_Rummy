import java.util.List;

public interface ReglesJoc extends java.io.Serializable {

	boolean esCombinacioValida(List<Peca> peces);

	int pecesARepartir(int numJugadors);

	void inicialitzarPila(List<Peca> pila);

	boolean haGuanyat(Jugador j);

	public int getNumJugadorsMaxim();

	public int getNumJugadorsMinim();

}
