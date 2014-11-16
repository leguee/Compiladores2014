package compilador;

import java.util.Vector;

public class Sentencia { 
	private Integer label = 0;
	private Vector<String> pilaEtiquetas = new Vector<String>();
	private Vector<String> codigo = new Vector<String>();
	
	public Vector<String> getCodigo(){
		return codigo;
	}
	private String getEtiqueta(){
		String salida;
		salida = "label" + label;
		label++;
		return salida;
	}
	
	public String apilarEtiqueta(){
		String salida;
		salida = getEtiqueta();
		pilaEtiquetas.add(salida);
		return salida;
	}
	
	public void agregarEtiqueta(String etiqueta){
		codigo.add(etiqueta) ;
	}
	
	public String desapilarEtiqueta(){
		String salida;
		salida = pilaEtiquetas.lastElement(); 
		pilaEtiquetas.remove(pilaEtiquetas.size() - 1);
		return salida;
	}
	public void add(String string) {
		codigo.add(string);
		
	}
}
