package compilador;

public class As1 extends AccionesSemantica { // Inicializa un token y agrega el símbolo leído.


	@Override
	public Token ejecutar(Token token, char caracter) {
		   token = new Token();
	       token.agregarCaracter(caracter);
	       return token;
	}

}
