import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {

		boolean partidaAcabada = false;
		Scanner teclat = new Scanner(System.in);
		ReglesJoc regles = new ReglesEstandard();
		int numJugadors = 0;

		Joc laMevaPartida = null;
		String nomFitxerActual = "partida_rummy.ser";

		System.out.println("Vols obrir l'historial de partides? [S/N]");
		String respostaHistorial = teclat.nextLine().toUpperCase();

		if (respostaHistorial.equals("S")) {
			String fitxerTriat = gestionarPartidesGuardades(teclat);

			if (fitxerTriat != null) {
				nomFitxerActual = fitxerTriat;
				laMevaPartida = Joc.carregarPartida(nomFitxerActual);
				System.out.println("Partida " + nomFitxerActual + " carregada!");
			}
		}

		if (laMevaPartida == null) {
			System.out.println("\n ----- COMENÇANT UNA NOVA PARTIDA ----- ");

			System.out.println("Quin nom li vols posar a aquesta partida?");
			String nomNou = teclat.nextLine().trim();
			nomFitxerActual = nomNou.endsWith(".ser") ? nomNou : nomNou + ".ser";


			while (numJugadors < regles.getNUM_JUGADORS_MINIM() || numJugadors > regles.getNUM_JUGADORS_MAXIM()) {
				System.out.println("Indicau nombre de Jugadors: [" + regles.getNUM_JUGADORS_MINIM() + "-" + regles.getNUM_JUGADORS_MAXIM() + "]");
				try {
					numJugadors = Integer.parseInt(teclat.nextLine());
				} catch (Exception e) {
					System.out.println("Nombre no vàlid");
				}
			}

			laMevaPartida = new Joc(regles);

			for (int i = 0; i < numJugadors; i++) {
				System.out.println("Nom del jugador " + (i + 1) + " : ");
				laMevaPartida.afegirJugadors(teclat.nextLine());
			}

			System.out.println("Preparant la baralla i repartint...");
			laMevaPartida.prepararPartida();
		}
		while (!partidaAcabada) {
			Jugador actual = laMevaPartida.getJugadorActual();

			netejarConsola();
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
					laMevaPartida.serialitzarPartida(nomFitxerActual);
					System.out.println("Partida guardada amb el nom " + nomFitxerActual + "! Fins després");
					System.exit(0);
				}

				if (accio.equals("B")) {
					List<List<Integer>> totesBaixades = new ArrayList<>();
					boolean mesCombinacions = true;

					while (mesCombinacions) {
						System.out.println("Introdueix els índexs de la combinació (separats per espais) o pulsa ENTER (Empty) per acabar:");
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
							}catch (NumberFormatException e) {
								System.out.println("Entrada no valida, nombre separats per espais");
							}
						}
					}

					if (!totesBaixades.isEmpty()) {
						laMevaPartida.ferAccioBaixarCombinacio(totesBaixades);
					}
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

				System.out.println("Resultats:");
				for (Jugador j : laMevaPartida.getJugadors()) {
					int puntsRonda = laMevaPartida.calcularPuntsMa(j);

					System.out.println("- " + j.getNom() + ": " + puntsRonda + "punts en aquesta mà.");
					j.sumarPunts(puntsRonda);
					System.out.println("RECORDA: Guanya qui te menys punts totals");
				}

				partidaAcabada = true;
			} else {
				laMevaPartida.passarTorn();
				System.out.println("\n------------- CANVI DE TORN -------------");
			}
		}
	}

	public static String gestionarPartidesGuardades (Scanner teclat) {
		java.io.File directori =new java.io.File(".");
		java.io.File[] llistaFitxers = directori.listFiles((dir,nom) -> nom.endsWith(".ser"));

		if (llistaFitxers == null || llistaFitxers.length == 0) {
			System.out.println("No hi ha partides guardades");
			return null;
		}

		System.out.println("\n--------- PARTIDES DISPONIBLES ---------");
		for (int i = 0; i < llistaFitxers.length; i++) {
			System.out.println((i + 1) + ". " + llistaFitxers[i].getName());
		}
		System.out.println("0 - Cancel·lar / Crear partida nova");
		System.out.println("-1 - Esborrar una partida vella");
		System.out.println(" ------------------------------------ ");

		int opcio = -2;
		while (opcio < -1 || opcio > llistaFitxers.length) {
			System.out.println("Que vols fer?");
			try {
				opcio = Integer.parseInt(teclat.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("Error. Introdueix un nombre vàlid");
			}
		}

		if (opcio == 0) {
			return null;
		} else if (opcio == -1) {
			System.out.println("Quina partida vols esborrar? [ 1 - " + llistaFitxers.length + " ] ");
			try {
				int esborrarPartida = Integer.parseInt(teclat.nextLine());
				if (esborrarPartida > 0 && esborrarPartida <= llistaFitxers.length) {
					java.io.File fitxerAEsborrar = llistaFitxers[esborrarPartida - 1];
					if (fitxerAEsborrar.delete()) {
						System.out.println("Partida " + fitxerAEsborrar.getName() + " eliminada!");
					} else {
						System.out.println(" Error en esborrar la partida");
					}
				}
			} catch (Exception e) {
				System.out.println("Operació cancel·lada");
			}

			return gestionarPartidesGuardades(teclat);
		}

		return llistaFitxers[opcio - 1].getName();
	}

	public static void netejarConsola() {
		System.out.println("\033[H\033[2J");
		System.out.flush();

		for (int i = 0; i < 50; i++) {
			System.out.println();
		}
	}
}
