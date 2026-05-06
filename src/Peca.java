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
		String pal;
		String simbol;

		if (this.grup.equalsIgnoreCase("COMODI")) return "\uD83C\uDCCF";

		switch (this.grup.toUpperCase()) {
			case "DIAMANTS":
				pal = "\u2666\uFE0F";
				break;
			case "TREBOLS":
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

		switch (this.valor) {
			case 1:
				simbol = "A";
				break;
			case 11:
				simbol = "J";
				break;
			case 12:
				simbol = "Q";
				break;
			case 13:
				simbol = "K";
				break;
			default:
				simbol = this.valor + "";
				break;
		}
		return simbol + pal;
	}

	public int getValorPuntuacio() {

		if (this.grup.equalsIgnoreCase("COMODI")) return 25;

		if (this.valor == 1 || this.valor >= 11) {
			return 10;
		} else {
			return this.valor;
		}
	}
}
