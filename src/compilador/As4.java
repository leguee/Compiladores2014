package compilador;

import java.util.Hashtable;

public class As4 extends AccionesSemantica{ // Empaqueta el token controlando la longitud sin consumir el símbolo leído

	
	 public static final Short IF = 257; // TODO CAMBIAR ESTO PASARLO A ESPAÑOL
	    public static final Short THEN = 258;
	    public static final Short ELSE = 259;
	    public static final Short BEGIN = 260;
	    public static final Short END = 261;
	    public static final Short PRINT = 262;
	    public static final Short LOOP = 263;
	    public static final Short UNTIL = 264;
	    public static final Short FUNCTION = 265;
	    public static final Short RETURN = 266;
	    public static final Short ID = 267;
	    public static final Short DOUBLE = 269;
	    
	    private TablaSimbolos ts;
	    private Mensajes ms;
	    private AnalizadorLexico al;
	    private Hashtable<String, Short> palabrasReservadas;

	    private void verificarLongitudString(Token token) {
		if (token.getLongitud() > 12) {
	            ms.warning("Línea " + al.getNroLinea() + ": El identificador '" + token.getLexema() + "' ha sido truncado"); // TODO manejar este mensaje
	            token.truncarId();
		}
	    }
	
	public As4(TablaSimbolos t, Mensajes m, AnalizadorLexico a){
        ts = t;
        ms = m;
        al = a;
        palabrasReservadas = new Hashtable<String, Short>();
        palabrasReservadas.put("if", IF); // TODO cambiar tambien
        palabrasReservadas.put("then", THEN);
        palabrasReservadas.put("else", ELSE);
        palabrasReservadas.put("begin", BEGIN);
        palabrasReservadas.put("end", END);
        palabrasReservadas.put("function", FUNCTION);
        palabrasReservadas.put("return", RETURN);
	palabrasReservadas.put("print", PRINT);
        palabrasReservadas.put("loop", LOOP);
        palabrasReservadas.put("until", UNTIL);
        palabrasReservadas.put("double", DOUBLE);
    }


	@Override
	public Token ejecutar(Token token, char caracter) {
		if (ts.contieneLexema(token.getLexema())){                       // Si el token es un lexema que ya esta agregado a la tabla de simbolos
            token.noSeAgregoCaracterLeido();                                  //Seteo que el último caracter leído no fue agregado al token
            token.setEntradaTS(ts.getEntradaTS(token.getLexema()));
            ms.token(al.getNroLinea(), token.getLexema());
            return token;
        }
        else {
            if (!ts.esPalabraReservada(token.getLexema())){              // Si el token no es una palabra reservada
                verificarLongitudString(token);                             // Verifico la longitud del token
                token.setId(ID);                                            // Seteo el tipo como identificador
                ts.addETS(token.getLexema(), token.getETS());
            }
            else {
                token.setId((Short)palabrasReservadas.get(token.getLexema())); // devuelvo el token con la palabra reservada, sin guardarla en la tabla de simbolos
            }
            ms.token(al.getNroLinea(), token.getLexema());
            token.noSeAgregoCaracterLeido();                                  //Seteo que el último caracter leído no fue agregado al token
            return token;
        }
	}

}
