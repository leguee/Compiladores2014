package compilador;

public abstract class Mensajes {

	public abstract void tablaDeSimbolos();

	public abstract void error(int nroLinea, String mensaje, String string);

	public abstract void token(int nroLinea, String lexema) ;
	
	public abstract void warning(String string) ;
		
	 public abstract void estructuraSintactica(int linea, String estructura);
}
