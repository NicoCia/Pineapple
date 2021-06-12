public class Efectivo implements Metodo_de_Pago {
	private int monto;

	public Efectivo(int monto){
		this.monto = monto;
	}

	public void pagar(){
		System.out.println(this);
	}

	public String toString(){
		return "Pago con efectivo $" + monto; 
	}
}
