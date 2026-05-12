import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {

		boolean partidaAcabada = false;
		Scanner teclat = new Scanner(System.in);

		int numJugadors = 0;

		Joc laMevaPartida = null;
		String nomFitxerActual = "";

		//Posar elecció de variant
		ReglesJoc regles = new ReglesEstandard();

		//SELECCIO DE PARTIDA GUARDADA
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

		//INICI PARTIDA NOVA
		if (laMevaPartida == null) {

			System.out.println("\n ----- TRIA LA VARIANT DE JOC -----");
			System.out.println("1. Estàndard (40 punts obertura)");
			System.out.println("2. Gin Rummy (2 jugadors, no es baixa a taula)");
			System.out.println("Mes variants proximament...");
			//System.out.println("3. Rummy Argentí (13 cartes, 30 punts)");
			//System.out.println("4. Rummy Kub (Manipulació de taula, 14 cartes)");
			System.out.print("Selecciona una opció: ");

			int opcioVariant = Integer.parseInt(teclat.nextLine());
			switch (opcioVariant) {
				case 2:
					regles = new ReglesGinRummy();
					break;
				/*case 3:
					regles = new ReglesRummyArgenti();
					break;*/
				/*case 4:
					regles = new ReglesRummyKub();
					break;*/
				default:
					regles = new ReglesEstandard();
					break;
			}

			System.out.println("\n ----- COMENÇANT UNA NOVA PARTIDA ----- ");

			//NOM PER GUARDAR PARTIDA
			System.out.println("Quin nom li vols posar a aquesta partida?");
			String nomNou = teclat.nextLine().trim();
			nomFitxerActual = nomNou.endsWith(".ser") ? nomNou : regles.getNOM_VARIANT() + nomNou + ".ser";

			//CONFIGURAR JUGADORS
			while (numJugadors < regles.getNUM_JUGADORS_MINIM() || numJugadors > regles.getNUM_JUGADORS_MAXIM()) {
				System.out.println("Indicau nombre de Jugadors: [" + regles.getNUM_JUGADORS_MINIM() + "-" + regles.getNUM_JUGADORS_MAXIM() + "]");
				try {
					numJugadors = Integer.parseInt(teclat.nextLine());
				} catch (Exception e) {
					System.out.println("Nombre no vàlid");
				}
			}

			laMevaPartida = new Joc(regles);

			//AFEGIR JUGADORS
			for (int i = 0; i < numJugadors; i++) {
				System.out.println("Nom del jugador " + (i + 1) + " : ");
				laMevaPartida.afegirJugadors(teclat.nextLine());
			}

			System.out.println("Preparant la baralla i repartint...");
			laMevaPartida.prepararPartida();
		}

		//BUCLE PARTIDA
		while (!partidaAcabada) {
			Jugador actual = laMevaPartida.getJugadorActual();

			//INICI TORN
			netejarConsola();
			System.out.println("\n---- TORN DE: " + actual.getNom().toUpperCase() + " ----");
			System.out.println("Que passi el jugador indicat...");
			teclat.nextLine();

			laMevaPartida.mostrarEstatPartida();

			// FASE DE ROBAR (obligatoria a principi de torn en totes les variants);
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

			//FASE D'ACCIONS (opcional, es modifica segons variants)
			String accio;
			do {
				laMevaPartida.mostrarEstatPartida();
				System.out.println(actual.getNom() + ", què vols fer?");
				if (regles.getPermetBaixarCombinacions()) System.out.println("[B] Baixar una nova combinació");
				if (regles.getPermetAfegirACombinacions()) System.out.println("[A] Afegir carta a una combinació de la taula");
				if (regles.getPermetreTancarAmbPunts()) System.out.println("[K] Fer Knock (Tancar la mà)");
				System.out.println("[P] Passar a la fase de descart");
				System.out.println("[O] Ordenar la mà");
				System.out.println("[G] Guardar la partida i sortir");
				System.out.println("Tria una opció: ");
				accio = teclat.nextLine().toUpperCase();

				//GUARDAR PARTIDA
				if (accio.equals("G")) {
					laMevaPartida.serialitzarPartida(nomFitxerActual);
					System.out.println("Partida guardada amb el nom " + nomFitxerActual + "! Fins després");
					System.exit(0);

				//BAIXAR COMBINACIO
				} else if( accio.equals("O") ) {
					laMevaPartida.ferAccioOrdenarMa();
				}else if (accio.equals("B") && regles.getPermetBaixarCombinacions()) {
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

				//AFEGIR PECA A COMBINACIO
				} else if (accio.equals("A") && regles.getPermetAfegirACombinacions()) {
					System.out.println("Índex de la teva carta a la mà: ");
					int indexMa = teclat.nextInt();
					teclat.nextLine();
					System.out.println("Numero de la combinació a la taula: ");
					int indexTaula = teclat.nextInt();
					teclat.nextLine();

					laMevaPartida.ferAccioAfegirCartaACombinacio(indexMa, indexTaula);
				} else if (accio.equals("K") && regles.getPermetreTancarAmbPunts()) {
					int puntsDeadwood = regles.calcularPuntsDeadwood(actual.getMa());

					if (regles.potTancatMa(actual, puntsDeadwood)) {
						System.out.println("Knock! has guanyat amb " + puntsDeadwood + " punts.");
						partidaAcabada = true;
						accio = "P";
					} else {
						System.out.println("No pots tancar! Tens " + puntsDeadwood + " punts.");
					}
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

			//SECCIO PER PARTIDA GUANYADA
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
		File directori =new File("Partides_Guardades");

		if (!directori.exists()) {
			directori.mkdir();
		}

		File[] llistaFitxers = directori.listFiles((dir, nom) -> nom.endsWith(".ser"));

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
