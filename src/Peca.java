public class Peca implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private ValorPeca valor;
	private PalPeca pal;

	public Peca(ValorPeca valor, PalPeca pal) {

		this.valor = valor;
		this.pal = pal;

	}

	public PalPeca getPal() {
		return pal;
	}
	public ValorPeca getValorEnum() {
		return valor;
	}

	public int getValor() { return valor.getValorNumeric(); }

	@Override
	public String toString() {
		if (pal == PalPeca.COMODI) return pal.getSimbol();
		return valor.getSimbol() + pal.getSimbol();
	}

	public int getValorPuntuacio() {

		if (this.pal == PalPeca.COMODI) { return 25; }

		int v = this.valor.getValorNumeric();

		if (v == 1 || v >= 11) {
			return 10;
		} else {
			return v;
		}
	}
}
