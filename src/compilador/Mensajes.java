package compilador;

public interface Mensajes {

	public void tablaDeSimbolos();

	public void error(int nroLinea, String mensaje, String string);

	public void warning(String string) ;
	
	public void token(int nroLinea, String lexema);

	public void estructuraSintactica(int linea, String estructura);
		
}
