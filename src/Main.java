import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {

		Scanner teclat = new Scanner(System.in);
		Joc laMevaPartida = null;
		String nomFitxerActual = "";

		System.out.println("Vols obrir l'historial de partides? [S/N]");
		String respostaHistorial = teclat.nextLine().toUpperCase().trim();

		if (respostaHistorial.equals("S")) {
			String fitxerTriat = gestionarPartidesGuardades(teclat);
			if (fitxerTriat != null) {
				nomFitxerActual = fitxerTriat;
				laMevaPartida = Joc.carregarPartida(nomFitxerActual);
				if (laMevaPartida != null) {
					System.out.println("Partida " + nomFitxerActual + " carregada!");
				}
			}
		}


		if (laMevaPartida == null) {
			ReglesJoc regles = triarVariant(teclat);
			laMevaPartida = new Joc(regles);

			System.out.println("Quin nom li vols posar a aquesta partida?");
			String nomNou = teclat.nextLine().trim();
			nomFitxerActual = nomNou.isEmpty() ? "partida_nova.ser" : nomNou + ".ser";

			configurarPartidaNova(laMevaPartida, teclat);
		}


		Partida controlador = new Partida(laMevaPartida, nomFitxerActual);
		controlador.lanzar();
	}

	private static ReglesJoc triarVariant(Scanner teclat) {
		System.out.println("\n ----- TRIA LA VARIANT DE JOC -----");
		System.out.println("1. Estàndard (40 punts obertura)");
		System.out.println("2. Gin Rummy (2 jugadors, no es baixa a taula)");
		System.out.println("Mes variants proximament...");
		//System.out.println("3. Rummy Argentí (13 cartes, 30 punts)");
		//System.out.println("4. Rummy Kub (Manipulació de taula, 14 cartes)");
		System.out.print("Selecciona una opció: ");

		int opcio = 1;
		try {
			opcio = Integer.parseInt(teclat.nextLine().trim());
		} catch (Exception e) {
			System.out.println("Opció no vàlida, usant Estàndard.");
		}

		return (opcio == 2) ? new ReglesGinRummy() : new ReglesEstandard();
	}

	private static void configurarPartidaNova(Joc joc, Scanner teclat) {
		ReglesJoc r = joc.getRegles();
		System.out.println("Nombre de Jugadors: [" + r.getNUM_JUGADORS_MINIM() + "-" + r.getNUM_JUGADORS_MAXIM() + "]");
		int num = Integer.parseInt(teclat.nextLine());

		for (int i = 0; i < num; i++) {
			System.out.println("Nom del jugador " + (i + 1) + ": ");
			joc.afegirJugadors(teclat.nextLine());
		}
		joc.prepararPartida();
	}

	public static String gestionarPartidesGuardades(Scanner teclat) {
		File directori = new File("Partides_Guardades");
		if (!directori.exists()) directori.mkdir();

		File[] llistaFitxers = directori.listFiles((dir, nom) -> nom.endsWith(".ser"));
		if (llistaFitxers == null || llistaFitxers.length == 0) {
			System.out.println("No hi ha partides guardades.");
			return null;
		}

		System.out.println("\n------- PARTIDES DISPONIBLES -------");
		for (int i = 0; i < llistaFitxers.length; i++) {
			System.out.println((i + 1) + ". " + llistaFitxers[i].getName());
		}

		System.out.println("Tria una opció (0 per nova): ");
		int opcio = Integer.parseInt(teclat.nextLine());
		return (opcio > 0) ? llistaFitxers[opcio - 1].getPath() : null;
	}
}