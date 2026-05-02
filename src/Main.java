import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {

		boolean partidaAcabada = false;

		Scanner teclat = new Scanner(System.in);
		ReglesJoc regles = new ReglesEstandard();

		System.out.println("Vols carregar la partida anterior? [S/N]");
		String resposta = teclat.nextLine().toUpperCase();

		Joc laMevaPartida;

		if (resposta.equals("S")) {
			laMevaPartida = Joc.carregarPartida("partida_rummy.ser");

			if (laMevaPartida == null) {
				System.out.println("No s'ha trobat cap partida. Començant-ne una de nova...");
				laMevaPartida = new Joc(new ReglesEstandard());
				laMevaPartida.afegirJugadors("Jugador1");
				laMevaPartida.afegirJugadors("Jugador2");
				laMevaPartida.prepararPartida();
			}
		} else {
			laMevaPartida = new Joc(new ReglesEstandard());
			System.out.println("JUG 1");
			laMevaPartida.afegirJugadors(teclat.nextLine());
			System.out.println("JUG 2");
			laMevaPartida.afegirJugadors(teclat.nextLine());

			System.out.println("Preparant la baralla...");
			laMevaPartida.prepararPartida();
		}
		while (!partidaAcabada) {
			Jugador actual = laMevaPartida.getJugadorActual();

			System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
			System.out.println("\n---- TORN DE: " + actual.getNom().toUpperCase() + " ----");
			System.out.println("Que passi el jugador indicat...");
			teclat.nextLine();

			laMevaPartida.mostrarEstatPartida();

			// FASE DE ROBAR (obligatoria a principi de torn)
			if (!laMevaPartida.getJaHaRobatAquestTorn()) {
				System.out.println(actual.getNom() + " , d'on vols robar? [P] Pila o [D] Descart: ");
				String opcio = teclat.nextLine().toUpperCase();

				if (opcio.equals("D")) {
					laMevaPartida.ferAccioRobarDescart();
					System.out.println("\n--- " + actual.getNom() + " , roba una peça dels descarts...");
				} else {
					laMevaPartida.ferAccioRobar();

					System.out.println("\n--- " + actual.getNom() + " , roba una peça de la pila...");
				}
			} else {
				System.out.println("\n (Ja havies robat carta abans de carregar la partida) ");
			}

			//FASE DE COMBINACIONS (opcional)
			String accio;
			do {
				laMevaPartida.mostrarEstatPartida();
				System.out.println(actual.getNom() + ", què vols fer?");
				System.out.println("[B] Baixar una nova combinació");
				System.out.println("[A] Afegir carta a una combinacio de la taula");
				System.out.println("[P] Passar a la fase de descart");
				System.out.println("[G] Guardar la prtida i sortir");
				System.out.println("Tria una opcio: ");
				accio = teclat.nextLine().toUpperCase();

				if (accio.equals("G")) {
					laMevaPartida.serialitzarPartida("partida_rummy.ser");
					System.out.println("Partida guardada! Fins després");
					System.exit(0);
				}

				if (accio.equals("B")) {
					System.out.println("Introdueix els índexs de les cartes (separats per espais):");
					String[] entrada = teclat.nextLine().split(" ");
					List<Integer> indexATreure = new ArrayList<>();

					for (String s : entrada) {
						indexATreure.add(Integer.parseInt(s));
					}

					laMevaPartida.ferAccioBaixarCombinacio(indexATreure);
				} else if (accio.equals("A")) {
					System.out.println("Índex de la teva carta a la mà: ");
					int indexMà = teclat.nextInt();
					teclat.nextLine();
					System.out.println("Numero de la combinació a la taula: ");
					int indexTaula = teclat.nextInt();
					teclat.nextLine();

					laMevaPartida.ferAccioAfegirCartaACombinacio(indexMà, indexTaula);
				}
			} while (!accio.equals("P") && !regles.haGuanyat(actual));

			//FASE DE DESCARTAR (Obligatoria)
			boolean descartFet = false;
			while (!descartFet) {
				if (!regles.haGuanyat(actual)) {
					laMevaPartida.mostrarEstatPartida();
					System.out.println(actual.getNom() + " , tria l'index de la peça que vols descartar: ");
					int indexDesc = teclat.nextInt();
					teclat.nextLine();

					if (indexDesc >= 0) {
						descartFet = laMevaPartida.ferAccioDescartar(indexDesc);
					}
				}
			}

			if (regles.haGuanyat(actual)) {
				System.out.println("\n " + actual.getNom().toUpperCase() + " S'HA QUEDAT SENSE PECES I GUANYA!");
				partidaAcabada = true;
			} else {
				laMevaPartida.passarTorn();
				System.out.println("\n------------- CANVI DE TORN -------------");
			}
		}
	}
}
