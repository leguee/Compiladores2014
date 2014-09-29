package compilador;

import java.util.Hashtable;

public class As8 extends AccionesSemantica { //Inicializa un token, adiciona el símbolo leído y empaqueta el token.

    public static final Short COMPARADOR = 271;
		private Mensajes ms;
	    private AnalizadorLexico al;
	    private Hashtable <String, Short> simbolos;

	    public As8(Mensajes m, AnalizadorLexico a){
	        ms = m;
	        al = a;
	        simbolos = new Hashtable<String, Short>(); 
	        simbolos.put("+", new Short((short)'+'));
	        simbolos.put("-", new Short((short)'-'));
	        simbolos.put("*", new Short((short)'*'));
	        simbolos.put("/", new Short((short)'/'));
	        simbolos.put("]", new Short((short)']'));
	        simbolos.put(",", new Short((short)','));
	        simbolos.put(";", new Short((short)';'));
	        simbolos.put(")", new Short((short)')'));
	        simbolos.put("(", new Short((short)'('));
	        simbolos.put("{", new Short((short)'{'));
	        simbolos.put("}", new Short((short)'}'));
	        simbolos.put("=", COMPARADOR);
	    }

	    public Token ejecutar(Token token, char c) {
	        token = new Token();
	        token.agregarCaracter(c);
	        ms.token(al.getNroLinea(), token.getLexema());
	        token.setId((Short)simbolos.get(token.getLexema())); // seteo la id, osea el nunmero ascii del operador 
	        return token;
	    }
}
