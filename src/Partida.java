import java.util.Scanner;

public class Partida {
	private Joc joc;
	private Scanner teclat;
	private boolean partidaAcabada;
	private String nomFitxer;

	public Partida(Joc joc,String nomFitxerActual) {
		this.joc = joc;
		this.teclat = new Scanner(System.in);
		this.partidaAcabada = false;
		this.nomFitxer = nomFitxerActual;
	}

	public void lanzar() {

		System.out.println("---- PARAMETRES DE LA PARTIDA ----");
		int maxRondes = 0;
		while (maxRondes <= 0){
			try{
				System.out.println("Nombre màxim de rondes a jugar: ");
				maxRondes = Integer.parseInt(teclat.nextLine());
			} catch (Exception e) {
				System.out.println("Introdueix un nombre vàlid");
			}
		}

		int limitPunts = 0;
		while (limitPunts <= 0){
			try {
				System.out.println("Límit de punts per acabar la partida (100,150,...): ");
				limitPunts = Integer.parseInt(teclat.nextLine());
			}  catch (Exception e) {
				System.out.println("Introdueix un nombre vàlid");
			}
		}

		int ronda = 1;
		boolean limitsPuntsArribat = false;
		while (ronda <= maxRondes && !limitsPuntsArribat) {
			this.partidaAcabada = false;

			if (ronda > 1){
				joc.reiniciarPerANovaRonda();
				joc.prepararPartida();
			}

			while (!partidaAcabada) {
				Jugador actual = joc.getJugadorActual();

				netejarConsola();
				System.out.println("\n-------- RONDA " + ronda + " DE " + maxRondes + " (Límit: " + limitPunts + " punts) --------");
				System.out.println("\n-------- TORN DE: " + actual.getNom().toUpperCase() + " --------");
				System.out.println("Pulsa ENTER per començar...");
				teclat.nextLine();

				joc.mostrarEstatPartida();

				faseRobar(actual);
				faseAccions(actual);

				if (!partidaAcabada) {
					faseDescart(actual);
					verificarVictoria(actual);
					if (!partidaAcabada) {
						joc.passarTorn();
					}
				}
			}

			System.out.println("\n--- PUNTUACIONS DESPRÉS DE LA RONDA " + ronda + " ---");

			for (Jugador j : joc.getJugadors()) {
				int puntsRonda = joc.calcularPuntsMa(j);
				j.sumarPunts(puntsRonda);
				System.out.println("- " + j.getNom() + ": +" + puntsRonda + " punts (Total acumulat: " + j.getPuntsAcumulats() + "/" + limitPunts + ")");

				if (j.getPuntsAcumulats() >= limitPunts) {
					limitsPuntsArribat = true;
				}
			}

			if (!limitsPuntsArribat && ronda < maxRondes) {
				System.out.println("\nPulsa ENTER per passar de ronda...");
				teclat.nextLine();
				ronda++;
			} else {
				break;
			}

			netejarConsola();
			System.out.println("\n-------- FI DE LA PARTIDA --------");
			if (limitsPuntsArribat) {
				System.out.println("(S'ha tancat perquè un jugador ha superat el límit de " + limitPunts + " punts)");
			} else {
				System.out.println("(S'han completat les " + maxRondes + " rondes màximes)");
			}

			System.out.println("\nResultats finals definitius (Guanya qui té MENYS punts):");

			Jugador guanyadorAbsolut = joc.getJugadors().get(0);
			for (Jugador j : joc.getJugadors()) {
				System.out.println(j.getNom() + ": " + j.getPuntsAcumulats() + " punts totals.");
				if (j.getPuntsAcumulats() < guanyadorAbsolut.getPuntsAcumulats()) {
					guanyadorAbsolut = j;
				}
			}
			System.out.println("\n ENHORABONA " + guanyadorAbsolut.getNom().toUpperCase() + ", ETS EL GUANYADOR ABSOLUT!");

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
