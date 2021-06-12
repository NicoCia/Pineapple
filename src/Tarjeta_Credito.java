public class Tarjeta_Credito implements Metodo_de_Pago{
	private long numero;
	private int codigo, n_cuotas;
	private String vencimiento;

	public Tarjeta_Credito(long numero, int codigo, int n_cuotas, String vencimiento){
		this.numero = numero;
		this.codigo = codigo;
		this.n_cuotas = n_cuotas;
		this.vencimiento = vencimiento;
	}

	public void pagar(){
		System.out.println(this);
	}

	public String toString(){
		return "Pago con Tajeta de Credito numero: " + numero + "en cuotas: " + n_cuotas; 
	}
}