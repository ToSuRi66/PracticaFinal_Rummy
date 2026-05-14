public enum PalPeca {
	DIAMANTS("\u2666\uFE0F"),CORS("\u2665\uFE0F"),TREBOLS("\u2663\uFE0F"),PIQUES("\u2660\uFE0F"),COMODI("\uD83C\uDCCF");

	private final String simbol;
	PalPeca(String simbol) {
		this.simbol = simbol;
	}
	public String getSimbol() {return simbol;}
}
