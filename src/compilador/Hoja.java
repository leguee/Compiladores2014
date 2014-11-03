package compilador;



public class Hoja extends ArbolSintactico {
	
	EntradaTS entrada ;
	
	
	public Hoja (EntradaTS e , String valor){
		super(valor,null,null);
		entrada = e ;
		
	}
	
	public Hoja () {}
	
	public EntradaTS getEntrada ( ){
		return entrada ;
	}
	
	public void setEntrada (EntradaTS e){
		entrada = e ;
	}
	
	public boolean esHoja (){
		return true ;
		
	}
	public boolean esNodo (){
		return false ;
	}
	
	public void imprimir (){
		System.out.print(this.entrada.getLexema());
	}

}
