package compilador;

public class As2 extends AccionesSemantica{ //Agrega el símbolo leído al token recibido.

	@Override
	public Token ejecutar(Token token, char caracter) { 
		token.agregarCaracter(caracter);
		return token;
	}

}
