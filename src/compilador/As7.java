package compilador;

public class As7 extends AccionesSemantica { // Lee un s�mbolo sin adicionarlo a ning�n token.

	public As7(){
    }

    public Token ejecutar(Token token, char c) {
        token.noSeAgregoCaracterLeido(); // TODO verificar que esto anda, sino cambiar por el otro ...true
        return token;
    }
}
