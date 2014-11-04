package compilador;


public class ArbolSintactico {
	
	private String valor ;
	
	private ArbolSintactico hijoDer ;
	private ArbolSintactico hijoIzq ;
	
	private Hoja hijoDerHoja ;
	private Hoja hijoIzqHoja ;
	
	private ArbolSintactico padre ;
	
	public ArbolSintactico (){
		padre = new ArbolSintactico ();
	}
	
	public ArbolSintactico (String valor , ArbolSintactico i , ArbolSintactico d){
		this.valor = valor ;
		hijoIzq = i ;
		hijoDer = d ;
		hijoDerHoja = null ;
		hijoIzqHoja = null ;
	}
	
	public void setHijoDer (ArbolSintactico a){
		hijoDer = a ;
	}
	
	public void setHijoIzq (ArbolSintactico a){
		hijoIzq = a ;
	}
	
	public ArbolSintactico getHijoDer (){
		return hijoDer ;
	}
	
	public ArbolSintactico getHijoIzq () {
		return hijoIzq ;
	}
	
	public void setValor (String v){
		valor = v ;
	}
	
	public String getValor (){
		return valor ;
	} 
	
	public void setHijoDerHoja (Hoja d){
		hijoDerHoja = d ;
	}
	
	public Hoja getHijoDerHoja (){
		return hijoDerHoja ;
	}
	
	public void setHijoIzqHoja (Hoja i){
		hijoIzqHoja = i ;
	}
	
	public Hoja getHijoIzqHoja (){
		return hijoIzqHoja ;
	}
	
	public boolean esHoja (){
		return false ;
	}
	
	public boolean esNodo (){
		return true ;
	}
	
	public void imprimir (int nivel){
		int a = 0 ;
		if (this != null){
			
			while (a<nivel){
				System.out.print("           ");
				a++;
			}
			System.out.println (valor);
			nivel++;
			
			if (this.esNodo()){
				if(this.hijoIzq!=null)
					this.hijoIzq.imprimir(nivel);
				
				
				if(this.hijoDer!=null)
					this.hijoDer.imprimir(nivel);
			}
			else{
				if (this.hijoDerHoja!= null)
					this.hijoDerHoja.imprimir(nivel);
				if (this.hijoIzqHoja!=null)
					this.hijoIzqHoja.imprimir(nivel);
		}
	}
	}

	public ArbolSintactico getPadre() {
		return padre;
	}

	public void setPadre(ArbolSintactico padre) {
		this.padre = padre;
	}
	
	
}