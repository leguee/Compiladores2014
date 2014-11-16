package compilador;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArbolSintactico h5 = new ArbolSintactico ("5",null, null);
		ArbolSintactico h6 = new ArbolSintactico ("6",null,null);
		ArbolSintactico mas = new ArbolSintactico ("+",h5,h6);
		ArbolSintactico varA = new ArbolSintactico("a",null,null);
		ArbolSintactico igual = new ArbolSintactico ("=",varA,mas);
		ArbolSintactico bloque = new ArbolSintactico("bloque", null, null);
		ArbolSintactico sIf = new ArbolSintactico("if",igual,bloque);
		
		sIf.imprimir(0);
		
		Drawer.dibujarGrafo(sIf);
		
	}

}
