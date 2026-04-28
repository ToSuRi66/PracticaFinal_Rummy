public class Main {
	public static void main(String[] args) {

		Joc laMevaPartida = new Joc();

		laMevaPartida.afegirJugadors("Nom1");
		laMevaPartida.afegirJugadors("Nom2");

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
