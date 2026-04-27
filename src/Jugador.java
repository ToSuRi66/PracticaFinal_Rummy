import java.util.ArrayList;

public class Jugador {
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

}
