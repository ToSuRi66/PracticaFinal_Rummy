import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner teclat = new Scanner(System.in);

		Joc laMevaPartida = new Joc();

		System.out.println("JUG 1");
		laMevaPartida.afegirJugadors(teclat.nextLine());
		System.out.println("JUG 2");
		laMevaPartida.afegirJugadors(teclat.nextLine());

		System.out.println("Preparant la baralla...");
		laMevaPartida.crearBarallaEstandard();

		System.out.println("Barrejant la baralla...");
		laMevaPartida.barrejarPeces();

		System.out.println("Repartint cartes...");
		laMevaPartida.repartirPeces(7);

		laMevaPartida.mostrarEstatPartida();

		System.out.println("Simulam que el primer jugador descarta la primera carta...");
		laMevaPartida.ferAccioDescartar(0);

		laMevaPartida.mostrarEstatPartida();
	}
}
