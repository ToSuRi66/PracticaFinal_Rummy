import java.util.ArrayList;
import java.util.List;
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

			if (ronda > 1) {
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
			if (regles.getPermetManipularTaula()) System.out.println("[M] Manipular a taula");
			if (regles.getPermetreTancarAmbPunts()) System.out.println("[K] Fer Knock");
			System.out.println("[P] Passar al descart");
			System.out.println("[O] Ordenar mà");
			System.out.println("[G] Guardar i sortir");

			accio = teclat.nextLine().toUpperCase();

			if (accio.equals("P") && !joc.verificarTaulaValida()) {
				System.out.println("\n NO POTS PASSAR EL TORN! Has deixat combinacion no vàlides a la taula");
				accio = "";
			} else {
				processarAccio(accio, actual);
			}
		} while (!accio.equals("P") && !partidaAcabada);
	}

	private void processarAccio(String accio, Jugador actual) {
		ReglesJoc regles = joc.getRegles();

		switch (accio) {
			case "G": executarGuardar(); break;
			case "O": executarOrdenar(); break;
			case "B": executarBaixar(regles); break;
			case "A": executarAfegir(regles); break;
			case "M": executarManipular(regles); break;
			case "K": executarKnock(regles, actual); break;
		}
	}

	private void executarGuardar() {
		joc.serialitzarPartida(nomFitxer);
		System.out.println("Partida guardada correctament a " + nomFitxer + ". Fins després!");
		System.exit(0);
	}

	private void executarOrdenar() {
		joc.ferAccioOrdenarMa();
	}

	private void executarBaixar(ReglesJoc regles) {
		if (!regles.getPermetBaixarCombinacions()) return;

		List<List<Integer>> totesBaixades = new ArrayList<>();
		boolean mesCombinacions = true;

		while (mesCombinacions) {
			System.out.println("Introdueix els índexs de la combinació (separats per espais) o ENTER per acabar:");
			String entrada = teclat.nextLine().trim();

			if (entrada.isEmpty()) {
				mesCombinacions = false;
			} else {
				try {
					String[] parts = entrada.split("\\s+");
					List<Integer> unaCombinacio = new ArrayList<>();
					for (String s : parts) {
						unaCombinacio.add(Integer.parseInt(s));
					}
					totesBaixades.add(unaCombinacio);
				} catch (NumberFormatException e) {
					System.out.println("Entrada no vàlida. Introdueix nombes separats per espais.");
				}
			}
		}
		if (!totesBaixades.isEmpty()) {
			joc.ferAccioBaixarCombinacio(totesBaixades);
		}
	}

	private void executarAfegir(ReglesJoc regles) {
		if (!regles.getPermetAfegirACombinacions()) return;

		try {
			System.out.println("Index de la teva carta a la mà: ");
			int indexMa = Integer.parseInt(teclat.nextLine().trim());
			System.out.println("Número de la combinació a la taula: ");
			int indexTaula = Integer.parseInt(teclat.nextLine().trim());

			joc.ferAccioAfegirCartaACombinacio(indexMa, indexTaula);
		} catch (Exception e) {
			System.out.println("Error en introduir els índexs");
		}
	}

	private void executarManipular(ReglesJoc regles) {
		if (!regles.getPermetManipularTaula()) return;

		try {
			System.out.println("Numero de la combinacio a la taula: ");
			int t = Integer.parseInt(teclat.nextLine().trim());
			System.out.println("Index de la carta que vols agafar: ");
			int p = Integer.parseInt(teclat.nextLine().trim());
			joc.ferAccioAgafarPecaDeTaula(t, p);
		} catch (Exception e) {
			System.out.println("Entrada no vàlida");
		}
	}

	private void executarKnock(ReglesJoc regles, Jugador actual) {
		if (!regles.getPermetreTancarAmbPunts()) return;

		int puntsDeadwood = regles.calcularPuntsDeadwood(actual.getMa());
		if (regles.potTancatMa(actual, puntsDeadwood)) {
			System.out.println("Knock. Has tancat la ronda amb " + puntsDeadwood + " punts.");
			partidaAcabada = true;
		} else {
			System.out.println("No pots tancar! Encara tens " + puntsDeadwood + " punts.");
		}
	}

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
			System.out.println("\n " + actual.getNom().toUpperCase() + " ha guanyat la ronda");
			partidaAcabada = true;
		}
	}

	private void netejarConsola() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}
}
