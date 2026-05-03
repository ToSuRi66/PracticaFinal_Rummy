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

	public int getValorPuntuacio() {
		if (this.valor >= 1 && this.valor <= 7) {
			return 5;
		} else {
			return 10;
		}
	}

	@Override
	public String toString() {
		String pal;

		switch (this.grup.toUpperCase()) {
			case "DIAMANTS":
				pal = "\u2666\uFE0F";
				break;
			case "TRÈBOLS":
				pal = "\u2663\uFE0F";
				break;
			case "CORS":
				pal = "\u2665\uFE0F";
				break;
			case "PIQUES":
				pal = "\u2660\uFE0F";
				break;
			default:
					pal = "??";
					break;
		}
		return valor + pal;
	}
}
