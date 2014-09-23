package compilador;

public class As9 extends AccionesSemantica {// Empaqueta el token consumiendo el simbolo, pero sin adicionarlo. en el caso de las comillas simples

	 public static final Short STRING = 270;
	    private Mensajes ms;
	    private AnalizadorLexico al;
	    private TablaSimbolos tabla;

	    public As9(Mensajes m, AnalizadorLexico a, TablaSimbolos t){
	        ms = m;
	        al = a;
	        tabla = t;
	    }

	    public Token ejecutar(Token token, char c) {
	        token.setId(STRING);
	        ms.token(al.getNroLinea(), token.getLexema());
	        if (!tabla.contieneLexema(token.getLexema())){
	            tabla.addETS(token.getLexema(), token.getETS());
	            token.getETS().setTipo("Cadena de caracteres");
	        }
	        else
	            token.setEntradaTS(tabla.getEntradaTS(token.getLexema()));
	        token.seAgregoCaracterLeido();
	        return token;
	    }


}
