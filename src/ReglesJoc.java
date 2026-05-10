import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public interface ReglesJoc extends java.io.Serializable {

	String getNOM_VARIANT();

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

	boolean getPermetRobarDeDescart();

	int getValorPeca(Peca p);

	boolean esGrup(List<Peca> peces);

	boolean esEscala(List<Peca> peces);
}
