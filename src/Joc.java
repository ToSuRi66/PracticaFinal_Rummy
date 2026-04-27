import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Joc {
	private List<Peca> pilaDeRobo;
	private List<Peca> pilaDeDescarts;

	public Joc() {
		pilaDeRobo = new ArrayList<Peca>();
		pilaDeDescarts = new ArrayList<Peca>();
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

	public void repartirPeces() {

	}
}
