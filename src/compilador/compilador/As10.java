package compilador;

public class As10 extends AccionesSemantica { // Inicializar el token sin adicionar el simbolo y si consumirlo.

	 public As10(){
	    }

	    public Token ejecutar(Token token, char c) {
	        token = new Token();
	        token.seAgregoCaracterLeido(); 
	        return token;
	    }
}
