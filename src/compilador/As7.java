package compilador;

public class As7 extends AccionesSemantica { // Lee un símbolo sin adicionarlo a ningún token.

	public As7(){
    }

    public Token ejecutar(Token token, char c) {
        token.seAgregoCaracterLeido(); // TODO verificar que esto anda, sino cambiar por el otro ...true->original
        return token;
    }
}
