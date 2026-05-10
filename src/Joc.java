import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
		this.repartirPeces(regles.pecesARepartir(this.jugadors.size()));
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
		jaHaRobatAquestTorn = false;
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

		if (pilaDeRobo.isEmpty()) {
			remesclarDescarts();
		}

		if (p != null) {
			actual.afegirPeca(p);
			jaHaRobatAquestTorn = true;
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
			jaHaRobatAquestTorn = true;

			String registre = "El Jugador " + actual.getNom() + " ha robat del descart: " + p.toString();
		} else {
			System.out.println("La pila de descart està buida! Has de robar de la pila de robo obligatoriament");
			ferAccioRobar();
		}
	}

	private void remesclarDescarts() {
		if (this.pilaDeDescarts.size() <= 1) {
			System.out.println("No hi ha prou peces al descart per remesclar");
			return;
		}

		Peca ultimaPeca = this.pilaDeDescarts.remove(this.pilaDeDescarts.size() - 1);
		pilaDeRobo.addAll(this.pilaDeDescarts);
		pilaDeDescarts.clear();
		Collections.shuffle(this.pilaDeRobo);
		pilaDeDescarts.add(ultimaPeca);

		registrarMoviment("S'ha acabat la pila. S'han remesclat els descarts");
		System.out.println("S'ha acabat la pila. S'han remesclat els descarts");
	}

	public void ferAccioBaixarCombinacio(List<List<Integer>> llistaIndexsCombinacions) {
		Jugador actual = getJugadorActual();
		int puntsTotalsTorn = 0;
		List<List<Peca>> combinacionsNoves = new ArrayList<>();

		for (List<Integer> indexs : llistaIndexsCombinacions) {
			List<Peca> pecesTriades = new ArrayList<>();
			for (int i : indexs) {
				if (i >= 0 && i < actual.getMa().size()) {
					pecesTriades.add(actual.getMa().get(i));
				}
			}
			if (regles.esCombinacioValida(pecesTriades)) {
				pecesTriades.sort(Comparator.comparingInt(Peca::getValor));

				combinacionsNoves.add(pecesTriades);

				for (Peca p : pecesTriades) {
					puntsTotalsTorn += p.getValorPuntuacio();
				}
			} else {
				System.out.println("Una o mes combinacions no son vàlides, cancelant moviment...");
				return;
			}
		}

		//Comprovació punts per obrir
		if (!actual.getHaFetPrimeraTirada() && puntsTotalsTorn < 30) {
			System.out.println("No pots obrir! Per obrir has de baixar un minim de 30 punts => " + puntsTotalsTorn);
			return;
		}

		for (List<Peca> combinacio : combinacionsNoves) {

			ordenarCombinacio(combinacio);

			for (Peca p : combinacio) {
				actual.getMa().remove(p);
			}
			this.taula.add(combinacio);
		}

		actual.setHaFetPrimeraTiradaTrue();
		System.out.println("S'han baixat " + combinacionsNoves.size() + " combinacions.");
		this.registrarMoviment(actual.getNom() + " ha baixat " + combinacionsNoves.toString());
	}

	public void ferAccioAfegirCartaACombinacio(int indexMa, int indexTaula){
		Jugador actual = getJugadorActual();

		if (indexMa < 0 || indexMa >= actual.getMa().size() || indexTaula < 0 || indexTaula >= this.taula.size()) {
			System.out.println("Indexs No Vàlids");
			return;
		}

		if (!actual.getHaFetPrimeraTirada()) {
			System.out.println("Fins que no obris no podras afegir peces a combinacions existents");
			return;
		}

		Peca p = actual.getMa().get(indexMa);
		List<Peca> combinacioOriginal = this.taula.get(indexTaula);

		List<Peca> prova = new ArrayList<>(combinacioOriginal);
		prova.add(p);

		if (regles.esCombinacioValida(prova)) {

			ordenarCombinacio(prova);

			actual.getMa().remove(indexMa);

			this.taula.set(indexTaula, prova);

			System.out.println("Carta lligada correctament!");
			this.registrarMoviment(actual.getNom() + " ha lligat una carta a una combinacio " + indexTaula);
		} else {
			System.out.println("Carta o Combinacio erronea");
		}
	}

	public void ordenarCombinacio(List<Peca> peces) {
		if (peces == null || peces.isEmpty()) return;

		boolean esGrup = true;
		int valorReferencia = -1;
		for (Peca p : peces) {
			if (!p.getGrup().equalsIgnoreCase("COMODI")) {
				if ( valorReferencia == -1 ) valorReferencia = p.getValor();
				else if (p.getValor() != valorReferencia) {
					esGrup = false;
					break;
				}
			}
		}

		if (esGrup) {
			peces.sort((p1,p2) -> {
				if (p1.getGrup().equalsIgnoreCase("COMODI")) return 1;
				if (p2.getGrup().equalsIgnoreCase("COMODI")) return -1;
				return 0;
			});
		} else {
			List<Peca> reals =  new ArrayList<>();
			List<Peca> joquers = new ArrayList<>();
			for (Peca p : peces) {
				if (p.getGrup().equalsIgnoreCase("COMODI")) joquers.add(p);
				else reals.add(p);
			}

			reals.sort(Comparator.comparingInt(Peca::getValor));

			List<Peca> resultat = new ArrayList<>();
			if (!reals.isEmpty()) {
				int valorEsperat = reals.get(0).getValor();
				int iReals = 0;

				while (iReals < reals.size()) {
					if (reals.get(iReals).getValor() == valorEsperat) {
						resultat.add(reals.get(iReals));
						iReals++;
					} else if (!joquers.isEmpty()) {
						resultat.add(joquers.remove(0));
					}
					valorEsperat++;
				}
				resultat.addAll(joquers);
			}
			peces.clear();
			peces.addAll(resultat);
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

	public int calcularPuntsMa(Jugador j) {
		int suma = 0;
		for (Peca p : j.getMa()) {
			suma += p.getValorPuntuacio();
		}
		return suma;
	}

	public List<Jugador> getJugadors() {
		return this.jugadors;
	}

	public void serialitzarPartida(String nomFitxer) {

		String ruta = "partides/" + (nomFitxer.startsWith("Partides_Guardades"));

		try ( ObjectOutputStream out = new ObjectOutputStream( new FileOutputStream( nomFitxer ) ) ) {
			out.writeObject( this );
			System.out.println(" Partida guardada correctament a : " + ruta);
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
