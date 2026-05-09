import java.util.List;

public interface ReglesJoc extends java.io.Serializable {

	int getPUNTS_MINIM_OBERTURA();

	boolean getPERMET_JOQUERS();

	boolean esCombinacioValida(List<Peca> peces);

	int pecesARepartir(int numJugadors);

	void inicialitzarPila(List<Peca> pila);

	boolean haGuanyat(Jugador j);

	int getNUM_JUGADORS_MAXIM();

	int getNUM_JUGADORS_MINIM();

	int getNUM_BARALLES();

	boolean getPermetAfegirACombinacions();

	boolean getPermetManipularTaula();

	boolean getPermetBaixarCombinacions();

	int getNumMinimPerCombinacions();

	boolean getObligatoriDescartarAFinalDeTorn();

	int getPuntsMaximsPerTancarMa();

	boolean getPermetRobarTotElDescart();

	int getValorPeca(Peca p);
}
