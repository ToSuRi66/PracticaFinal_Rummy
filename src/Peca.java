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

	public int getValor() {
		return valor.getValorNumeric();
	}

	public ValorPeca getValorEnum() {
		return valor;
	}

	public void setValor(ValorPeca valor) {
		this.valor = valor;
	}

	@Override
	public String toString() {
		if (pal == PalPeca.COMODI) return pal.getSimbol();
		return valor.getSimbol() + pal.getSimbol();
	}
}