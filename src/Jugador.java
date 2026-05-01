import java.util.ArrayList;
import java.util.List;

public class Jugador implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

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

	public void afegirPeca(Peca p) {

		this.ma.add(p);

	}

	public Peca treurePeca(int index) {

		if( index >= 0 && index < this.ma.size()) {

			return this.ma.remove(index);

		}

		return null;

	}

	public List<Peca> getMa() {
		return this.ma;
	}

}
