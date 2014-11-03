package compilador;

public class As7 extends AccionesSemantica { // Lee un símbolo sin adicionarlo a ningún token.

	public As7(){
    }

    public Token ejecutar(Token token, char c) {
        token.seAgregoCaracterLeido(); 
        return token;
    }
}
