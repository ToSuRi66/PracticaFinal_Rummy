import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Joc {

	private int tornActual;

	private ReglesJoc regles;

	private List<Peca> pilaDeRobo;
	private List<Peca> pilaDeDescarts;
	private List<Jugador> jugadors;
	private List<String> historial;

	public Joc(ReglesJoc reglesEscollides) {
		this.regles = reglesEscollides;
		this.pilaDeRobo = new ArrayList<Peca>();
		this.pilaDeDescarts = new ArrayList<Peca>();
		this.jugadors = new ArrayList<>();
		this.tornActual = 0;
		this.historial = new ArrayList<>();
	}

	public void prepararPartida() {
		regles.inicialitzarPila(this.pilaDeRobo);
		this.barrejarPeces();
		this.repartirPeces(regles.pecesARepartir());
	}

	/*public void crearBarallaEstandard() {
		String[] pals = {"Cors","Diamants","Piques","Trèvols"};

		for ( String pal : pals ) {

			for (int i = 0; i < 13; i++) {

				this.pilaDeRobo.add( new Peca( i , pal ) );

			}

		}
	}*/

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

	public void ferAccioDescartar(int index) {
		Jugador actual = getJugadorActual();

		Peca p = actual.treurePeca(index);

		if (p != null) {
			this.descartarPeca(p);

			String registre = "El Jugador " + actual.getNom() + " ha descartat la peça " + p.toString();
			this.registrarMoviment(registre);

			System.out.println(registre);
		} else {
			System.out.println("Error: Índex de peça no vàlid");
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
	}
}
