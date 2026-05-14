import java.util.Scanner;

public class Partida {
	private Joc joc;
	private Scanner teclat;
	private boolean partidaAcabada;

	public Partida(Joc joc) {
		this.joc = joc;
		this.teclat = new Scanner(System.in);
		this.partidaAcabada = false;
	}

	public void lanzar() {
		while (!partidaAcabada) {
			Jugador actual = joc.getJugadorActual();

			netejarConsola();
			System.out.println("\n---- TORN DE: " + actual.getNom().toUpperCase() + " ----");
			System.out.println("Prem ENTER per començar...");
			teclat.nextLine();

			joc.mostrarEstatPartida();

			faseRobar(actual);
			faseAccions(actual);

			if (!partidaAcabada) {
				faseDescart(actual);
				verificarVictoria(actual);
			}

			if (!partidaAcabada) {
				joc.passarTorn();
			}
		}
	}

	private void faseRobar(Jugador actual) {
		if (!joc.getJaHaRobatAquestTorn()) {
			System.out.println(actual.getNom() + ", d'on vols robar? [P] Pila o [D] Descart");
			String opcio = teclat.nextLine().toUpperCase();

			if (opcio.equals("D")) {
				joc.ferAccioRobarDescart();
			} else {
				joc.ferAccioRobar();
			}
		}
	}

	private void faseAccions(Jugador actual) {
		String accio;
		ReglesJoc regles = joc.getRegles();

		do {
			joc.mostrarEstatPartida();
			System.out.println(actual.getNom() + ", què vols fer?");
			if (regles.getPermetBaixarCombinacions()) System.out.println("[B] Baixar combinació");
			if (regles.getPermetAfegirACombinacions()) System.out.println("[A] Afegir a taula");
			if (regles.getPermetreTancarAmbPunts()) System.out.println("[K] Fer Knock");
			System.out.println("[P] Passar al descart");
			System.out.println("[O] Ordenar mà");
			System.out.println("[G] Guardar i sortir");

			accio = teclat.nextLine().toUpperCase();

			processarAccio(accio, actual);
		} while (!accio.equals("P") && !partidaAcabada);
	}

	private void processarAccio(String accio, Jugador actual) {}

	private void faseDescart(Jugador actual) {
		boolean descartFet = false;

		while (!descartFet) {
			System.out.println(actual.getNom() + ", tria l'índex per descartar: ");
			try {
				int index = Integer.parseInt(teclat.nextLine());
				descartFet = joc.ferAccioDescartar(index);
			} catch (Exception e) {
				System.out.println("Entrada no vàlida");
			}
		}
	}

	private void verificarVictoria(Jugador actual) {
		if (joc.getRegles().haGuanyat(actual)) {
			System.out.println("\n " + actual.getNom().toUpperCase() + " ha guanyat la partida");
			partidaAcabada = true;
		}
	}

	private void netejarConsola() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}
}
