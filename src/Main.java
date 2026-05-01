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

			System.out.println("\n>>>" + actual.getNom() + " , roba una peça de la pila...");
			laMevaPartida.ferAccioRobar();

			laMevaPartida.mostrarEstatPartida();

			System.out.println(actual.getNom() + " , tria l'index de la peça que vols descartar: ");
			int index = teclat.nextInt();
			teclat.nextLine();

			laMevaPartida.ferAccioDescartar(index);

			if (regles.haGuanyat(actual)) {
				System.out.println("\n " + actual.getNom().toUpperCase() + " S'HA QUED0AT SENSE PECES I GUANYA!");
				partidaAcabada = true;
			} else {
				laMevaPartida.passarTorn();
				System.out.println("\n=== CANVI DE TORN ===");
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
