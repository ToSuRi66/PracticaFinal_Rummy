import java.util.ArrayList;
import java.util.List;

public class Jugador implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private int puntsAcumulats = 0;
	private boolean haFetPrimeraTirada = false;
	private String nom;
	private ArrayList<Peca> ma;

	public Jugador(String nom) {

		this.nom = nom;
		this.ma = new ArrayList<>();

	}

	public String getNom() {

		return nom;

	}

	public void setNom(String nom) {

		this.nom = nom;

	}

	public List<Peca> getMa() {
		return this.ma;
	}

	public void setHaFetPrimeraTiradaTrue() {
		this.haFetPrimeraTirada = true;
	}

	public boolean getHaFetPrimeraTirada() {
		return this.haFetPrimeraTirada;
	}

	public void afegirPeca(Peca p) {

		this.ma.add(p);

	}

	public Peca treurePeca(int index) {

		if( index >= 0 && index < this.ma.size()) {

			return this.ma.remove(index);

		}

		return null;

	}

	public void sumarPunts(int punts) {
		this.puntsAcumulats += punts;
	}

	public int getPuntsAcumulats() {
		return this.puntsAcumulats;
	}
}
