package compilador;

public class As3 extends AccionesSemantica { // Empaqueta el token controlando el rango y sin consumir el último caracter.

	public static final Short CONSTANTE = 265; 
	private TablaSimbolos ts;
	private AnalizadorLexico al;
	private Mensajes ms;
	

	public As3(TablaSimbolos ts, AnalizadorLexico al, Mensajes ms) {
		this.ts = ts;
		this.al = al;
		this.ms = ms;
	}


	@Override
	public Token ejecutar(Token token, char caracter) {
		
		token.setId(CONSTANTE);
		double d = Double.valueOf(token.getLexema().replace('b', 'E').replace('B', 'E'));
		token.setLexema(String.valueOf(d));
		if(d> Short.MIN_VALUE && d< Short.MAX_VALUE ) { 
			if(ts.contieneLexema(token.getLexema())) {
				ts.getEntradaTS(token.getLexema()).incrementarCont();
				token.setEntradaTS(ts.getEntradaTS(token.getLexema()));
			}
			else {
				token.setId(CONSTANTE);
				ts.addETS(token.getLexema(), token.getETS());
			}
			
		}
		else {
			ms.error(al.getNroLinea(), al.getMensaje(1), "LEXICO"); 
		}
		token.noSeAgregoCaracterLeido();
		return token;
	}

}
