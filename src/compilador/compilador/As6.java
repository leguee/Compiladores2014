package compilador;

import java.util.Hashtable;

public class As6 extends AccionesSemantica { //  Empaqueta el token sin adicionar el símbolo y sin consumirlo


    public static final Short COMPARADOR = 268;
    
    private Mensajes ms;
    private AnalizadorLexico al;
    private Hashtable <String, Short> simbolos;

    public As6(Mensajes m, AnalizadorLexico a){
        ms = m;
        al = a;
        simbolos = new Hashtable<String, Short>(); 
        simbolos.put("[", new Short((short)'['));
        simbolos.put("<", COMPARADOR);
        simbolos.put(">", COMPARADOR);
    }

    public Token ejecutar(Token token, char c) {
        ms.token(al.getNroLinea(), token.getLexema());
        token.noSeAgregoCaracterLeido();
        token.setId((Short)simbolos.get(token.getLexema()));
        return token;
    }
}
