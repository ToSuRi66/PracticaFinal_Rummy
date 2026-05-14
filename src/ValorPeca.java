public enum ValorPeca {
	COMODI(0, "\uD83C\uDCCF"), AS(1, "A"), DOS(2, "2"), TRES(3, "3"), QUATRE(4, "4"),
	CINC(5, "5"), SIS(6, "6"), SET(7, "7"), VUIT(8, "8"), NOU(9, "9"),
	DEU(10, "10"), JOTA(11, "J"), REINA(12, "Q"), REI(13, "K");

	private final int valorNumeric;
	private final String simbol;

	ValorPeca (int valorNumeric, String simbol) {
		this.valorNumeric = valorNumeric;
		this.simbol = simbol;
	}

	public int getValorNumeric() { return this.valorNumeric; }
	public String getSimbol() { return this.simbol; }
}
