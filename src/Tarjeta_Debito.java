public class Tarjeta_Debito implements Metodo_de_Pago{
	private long numero;
	private int codigo;
	private String vencimiento;

	public Tarjeta_Debito(long numero, int codigo, String vencimiento){
		this.numero = numero;
		this.codigo = codigo;
		this.vencimiento = vencimiento;
	}

	public void pagar(){
		System.out.println(this);
	}

	public String toString(){
		return "Pago con Tajeta de Debito numero: " + numero; 
	}
}
