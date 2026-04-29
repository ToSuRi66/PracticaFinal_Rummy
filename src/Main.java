import java.util.Scanner;

public class Main {
	public static void main(String[] args) {

		Scanner teclat = new Scanner(System.in);

		ReglesJoc regles = new ReglesEstandard();

		Joc laMevaPartida = new Joc(regles);

		System.out.println("JUG 1");
		laMevaPartida.afegirJugadors(teclat.nextLine());
		System.out.println("JUG 2");
		laMevaPartida.afegirJugadors(teclat.nextLine());

		System.out.println("Preparant la baralla...");
		laMevaPartida.prepararPartida();

		laMevaPartida.mostrarEstatPartida();

		System.out.println("Simulam que el primer jugador descarta la primera carta...");
		laMevaPartida.ferAccioDescartar(0);

		laMevaPartida.mostrarEstatPartida();

		System.out.println("Simulam que el primer jugador roba una carta...");
		laMevaPartida.ferAccioRobar();

		laMevaPartida.mostrarEstatPartida();
	}
}
