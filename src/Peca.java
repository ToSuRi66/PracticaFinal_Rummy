public class Peca implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private int valor;
	private String grup;

	public Peca(int valor, String grup) {

		this.valor = valor;
		this.grup = grup;

	}

	public String getGrup() {
		return grup;
	}

	public int getValor() {

		return valor;

	}
	public void setValor(int valor) {

		this.valor = valor;

	}

	@Override
	public String toString() {
		return valor + " de " + grup;
	}
}
