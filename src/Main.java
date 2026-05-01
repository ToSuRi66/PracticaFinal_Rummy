import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {

		boolean partidaAcabada = false;
		Scanner teclat = new Scanner(System.in);
		ReglesJoc regles = new ReglesEstandard();
		Joc laMevaPartida = new Joc(regles);

		System.out.println("JUG 1");
		laMevaPartida.afegirJugadors(teclat.nextLine());
		System.out.println("JUG 2");
		laMevaPartida.afegirJugadors(teclat.nextLine());

		System.out.println("Preparant la baralla...");
		laMevaPartida.prepararPartida();

		while (!partidaAcabada) {
			laMevaPartida.mostrarEstatPartida();
			Jugador actual = laMevaPartida.getJugadorActual();

			// FASE DE ROBAR (obligatoria a principi de torn)
			System.out.println(actual.toString() + " , d'on vols robar? [P] Pila o [D] Descart: ");
			String opcio = teclat.nextLine().toUpperCase();

			if (opcio.equals("D")) {
				laMevaPartida.ferAccioRobarDescart();
				System.out.println("\n>>>" + actual.getNom() + " , roba una peça dels descarts...");
			} else {
				laMevaPartida.ferAccioRobar();
				System.out.println("\n>>>" + actual.getNom() + " , roba una peça de la pila...");
			}

			laMevaPartida.mostrarEstatPartida();

			//FASE DE COMBINACIONS (opcional)
			String opcioBaixar;
			do {
				System.out.println(actual.getNom() + ", vols baixar una combinació? [S/N]: ");
				opcioBaixar = teclat.nextLine().toUpperCase();

				if (opcioBaixar.equals("S")) {
					System.out.println("Introdueix els índexs de les cartes (separats per espais):");
					String[] entrada = teclat.nextLine().split(" ");
					List<Integer> indexATreure = new ArrayList<>();

					for (String s : entrada) {
						indexATreure.add(Integer.parseInt(s));
					}

					laMevaPartida.ferAccioBaixarCombinacio(indexATreure);
				}
			} while (opcioBaixar.equals("S") && !regles.haGuanyat(actual));

			//FASE DE DESCARTAR
			System.out.println(actual.getNom() + " , tria l'index de la peça que vols descartar: ");
			int index = teclat.nextInt();
			teclat.nextLine();

			laMevaPartida.ferAccioDescartar(index);

			if (regles.haGuanyat(actual)) {
				System.out.println("\n " + actual.getNom().toUpperCase() + " S'HA QUED0AT SENSE PECES I GUANYA!");
				partidaAcabada = true;
			} else {
				laMevaPartida.passarTorn();
				System.out.println("\n------------- CANVI DE TORN -------------");
			}
		}
		/*laMevaPartida.mostrarEstatPartida();

		System.out.println("Simulam que el primer jugador descarta la primera carta...");
		laMevaPartida.ferAccioDescartar(0);

		laMevaPartida.mostrarEstatPartida();

		System.out.println("Simulam que el primer jugador roba una carta...");
		laMevaPartida.ferAccioRobar();

		laMevaPartida.mostrarEstatPartida();*/
	}
}
