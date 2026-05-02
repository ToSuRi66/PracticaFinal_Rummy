import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.System.in;

public class Joc implements java.io.Serializable{
	private static final long serialVersionUID = 1L;

	private int tornActual;
	private boolean jaHaRobatAquestTorn = false;

	private ReglesJoc regles;

	private List<Peca> pilaDeRobo;
	private List<Peca> pilaDeDescarts;
	private List<Jugador> jugadors;
	private List<String> historial;
	private List<List<Peca>> taula;

	public Joc(ReglesJoc reglesEscollides) {
		this.regles = reglesEscollides;
		this.pilaDeRobo = new ArrayList<Peca>();
		this.pilaDeDescarts = new ArrayList<Peca>();
		this.jugadors = new ArrayList<>();
		this.tornActual = 0;
		this.historial = new ArrayList<>();
		this.taula = new ArrayList<>();
	}

	public boolean getJaHaRobatAquestTorn() {
		return this.jaHaRobatAquestTorn;
	}

	public void prepararPartida() {
		regles.inicialitzarPila(this.pilaDeRobo);
		this.barrejarPeces();
		this.repartirPeces(regles.pecesARepartir());
		this.iniciarPilaDescart();
	}

	public void barrejarPeces() {
		Collections.shuffle(this.pilaDeRobo);
	}

	public void repartirPeces(int quantitatPerJugador) {
		for (Jugador j : this.jugadors) {
			for (int i = 0; i < quantitatPerJugador; i++) {
				if (!pilaDeRobo.isEmpty()) {
					Peca p = this.pilaDeRobo.remove(0);
					j.afegirPeca(p);
				}
			}
		}
	}

	private void iniciarPilaDescart() {
		Peca inicial = this.robarPeca();

		if (inicial != null) {
			this.descartarPeca(inicial);
			this.registrarMoviment("S'ha iniciat la pila de descart amb la peça: " + inicial.toString());
		}
	}

	public void afegirJugadors(String nom) {
		Jugador nouJugador = new Jugador(nom);
		this.jugadors.add(nouJugador);
	}

	public Peca robarPeca() {
		if (this.pilaDeRobo.isEmpty()) {
			return null;
		}
		return this.pilaDeRobo.remove(0);
	}

	public void passarTorn() {
		this.tornActual = (this.tornActual + 1) % this.jugadors.size();
	}

	public void descartarPeca(Peca p) {
		this.pilaDeDescarts.add(p);
	}

	public void registrarMoviment(String accio) {
		this.historial.add(accio);
	}

	public Jugador getJugadorActual() {
		if (this.jugadors != null && !this.jugadors.isEmpty()) {
			return this.jugadors.get(this.tornActual);
		}
		return null;
	}

	public boolean ferAccioDescartar(int index) {
		Jugador actual = getJugadorActual();

		Peca p = actual.treurePeca(index);

		if (p != null) {
			this.descartarPeca(p);

			String registre = "El Jugador " + actual.getNom() + " ha descartat la peça " + p.toString();
			this.registrarMoviment(registre);

			System.out.println(registre);
			return true;

		} else {
			System.out.println("Error: Índex de peça no vàlid");
			return false;
		}
	}

	public void ferAccioRobar() {

		Jugador actual = getJugadorActual();
		Peca p = this.robarPeca();

		if (p != null) {
			actual.afegirPeca(p);
			String registre = "El Jugador " + actual.getNom() + " ha robat una peça";
			this.registrarMoviment(registre);
			System.out.println(registre);
		} else {
			System.out.println("No queden peces per robar!");
		}
	}

	public void ferAccioRobarDescart(){
		Jugador actual = getJugadorActual();

		if (!this.pilaDeDescarts.isEmpty()) {
			Peca p = this.pilaDeDescarts.remove(this.pilaDeDescarts.size() - 1);
			actual.afegirPeca(p);

			String registre = "El Jugador " + actual.getNom() + " ha robat del descart: " + p.toString();
		} else {
			System.out.println("La pila de descart està buida! Has de robar de la pila de robo obligatoriament");
			ferAccioRobar();
		}
	}

	public void ferAccioBaixarCombinacio(List<Integer> indexs){
		Jugador actual = getJugadorActual();
		List<Peca> pecesTriades = new ArrayList<>();

		for (int i : indexs) {
			pecesTriades.add( actual.getMa().get( i ) );
		}

		if (regles.esCombinacioValida(pecesTriades)) {
			for (Peca p : pecesTriades) {
				actual.getMa().remove( p );
			}
			this.taula.add(pecesTriades);
			System.out.println("Combinacio baixada correctament!");
			this.registrarMoviment(actual.getNom() + " ha baixat la combinacio " + pecesTriades.toString());
		} else {
			System.out.println("Aquesta combinacio no és vàlida segons les normes del joc!");
		}
	}

	public void ferAccioAfegirCartaACombinacio(int indexMa, int indexTaula){
		Jugador actual = getJugadorActual();

		if (indexMa < 0 || indexMa >= actual.getMa().size() || indexTaula < 0 || indexTaula >= this.taula.size()) {
			System.out.println("Indexs No Vàlids");
			return;
		}

		Peca p = actual.getMa().get(indexMa);
		List<Peca> combinacioOriginal = this.taula.get(indexTaula);

		List<Peca> prova = new ArrayList<>(combinacioOriginal);
		prova.add(p);

		if (regles.esCombinacioValida(prova)) {
			actual.getMa().remove(indexMa);

			this.taula.set(indexTaula, prova);

			System.out.println("Carta lligada correctament!");
			this.registrarMoviment(actual.getNom() + " ha lligat una carta a una combinacio " + indexTaula);
		} else {
			System.out.println("Carta o Combinacio erronea");
		}
	}

	public void mostrarEstatPartida() {
		Jugador actual = getJugadorActual();

		System.out.println("\n--------------------------------------------------");
		System.out.println(" JUGADOR ACTUAL: " + actual.getNom().toUpperCase());
		System.out.println("\n--------------------------------------------------");

		System.out.println("La teva mà: ");
		List<Peca> maActual = actual.getMa();
		for (int i = 0; i < maActual.size(); i++) {
			System.out.println(" [" + i + "] " + maActual.get(i).toString());
		}

		System.out.println("\n--------------------------------------------------");
		System.out.println("Pila de descart: ");

		if (this.pilaDeDescarts.isEmpty()) {
			System.out.println("[Buida]");
		} else {
			Peca ultima = this.pilaDeDescarts.get(this.pilaDeDescarts.size() - 1);
			System.out.println("-> " + ultima.toString());
		}
		System.out.println("\n--------------------------------------------------");
		System.out.println("\n------------- TAULER DE COMBINACIONS -------------");
		if (this.taula.isEmpty()) {
			System.out.println(" [No hi ha res a la taula encara] ");
		} else {
			for (int i = 0; i < this.taula.size(); i++) {
				System.out.println("Combinació " + i + ": " + this.taula.get(i).toString());
			}
		}
		System.out.println("\n--------------------------------------------------");

	}

	public void serialitzarPartida(String nomFitxer) {
		try ( ObjectOutputStream out = new ObjectOutputStream( new FileOutputStream( nomFitxer ) ) ) {
			out.writeObject( this );
			System.out.println(" Partida guardada correctament a : " + nomFitxer);
		} catch ( IOException e ) {
			System.out.println(" Error en serialitzar la partida: " + e.getMessage());
		}
	}

	public static Joc carregarPartida(String nomFitxer) {
		try (ObjectInputStream in = new ObjectInputStream( new FileInputStream( nomFitxer ) ) ) {
			return ( Joc ) in.readObject();
		} catch ( IOException | ClassNotFoundException e ) {
			System.out.println(" No s'ha pogut carregar la partida (potser el fitxer no existeix).");
			return null;
		}
	}
}
