import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Joc {

	private int tornActual;

	private List<Peca> pilaDeRobo;
	private List<Peca> pilaDeDescarts;
	private List<Jugador> jugadors ;

	public Joc() {

		this.pilaDeRobo = new ArrayList<Peca>();
		this.pilaDeDescarts = new ArrayList<Peca>();
		this.jugadors = new ArrayList<>();
		this.tornActual = 0;

	}

	public void crearBarallaEstandard() {
		String[] pals = {"Cors","Diamants","Piques","Trèvols"};

		for ( String pal : pals ) {

			for (int i = 0; i < 13; i++) {

				this.pilaDeRobo.add( new Peca( i , pal ) );

			}

		}
	}

	public void barrejarPeces() {

		Collections.shuffle( this.pilaDeRobo );

	}

	public void repartirPeces(int quantitatPerJugador) {

		for (Jugador j : this.jugadors) {

			for (int i = 0; i < quantitatPerJugador; i++) {

				if (!pilaDeRobo.isEmpty()) {

					Peca p = this.pilaDeRobo.remove(0);
					j.afegirPeca(p);

				}

			}

		}

	}

	public void afegirJugadors(String nom) {

		Jugador nouJugador = new Jugador(nom);
		this.jugadors.add(nouJugador);

	}

	public Peca robarPeca() {
		if (this.pilaDeRobo.isEmpty()) {
			return null;
		}

		return this.pilaDeRobo.remove(0);
	}
}
