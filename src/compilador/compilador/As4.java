package compilador;

import java.util.Hashtable;

public class As4 extends AccionesSemantica{ // Empaqueta el token controlando la longitud sin consumir el símbolo leído

	
		public static final Short SI = 258; 
	    public static final Short ENTONCES = 259;
	    public static final Short SINO = 260;
	    public static final Short IMPRIMIR = 261;
	    public static final Short DOBLE = 266;
	    public static final Short VECTOR = 273;
	    public static final Short DE = 272;
	    public static final Short ITERAR = 262;
	    public static final Short HASTA = 263;
	    public static final Short ENTERO = 257;
	    public static final Short ID = 264;
	    
	    
	    private TablaSimbolos ts;
	    private Mensajes ms;
	    private AnalizadorLexico al;
	    private Hashtable<String, Short> palabrasReservadas;

	    private void verificarLongitudString(Token token) { // si pasa el limite lo trunca y warning 
		if (token.getLongitud() > 12) {
	            ms.warning("Línea " + al.getNroLinea() + ": El identificador '" + token.getLexema() + "' ha sido truncado"); 
	            token.truncarId();
		}
	    }
	
	public As4(TablaSimbolos t, Mensajes m, AnalizadorLexico a){
        ts = t;
        ms = m;
        al = a;
        palabrasReservadas = new Hashtable<String, Short>();
        palabrasReservadas.put("si", SI); 
        palabrasReservadas.put("entonces", ENTONCES);
        palabrasReservadas.put("sino", SINO);
        palabrasReservadas.put("imprimir", IMPRIMIR);
        palabrasReservadas.put("doble", DOBLE);
        palabrasReservadas.put("vector", VECTOR);
        palabrasReservadas.put("de", DE);
        palabrasReservadas.put("iterar", ITERAR);
        palabrasReservadas.put("hasta", HASTA);
        palabrasReservadas.put("entero", ENTERO);
      
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
