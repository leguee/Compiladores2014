package compilador;

public class As12 extends AccionesSemantica {
	// Empaqueta el token, controlando el rango, no adiciona el ultimo simbolo y
	// retrocediendo la posición de lectura de caracteres para que se vuelva a leer el caracter "B".
	// El carcater "B" no se incluye en el token actual.
	
    public static final Short CONSTANTE = 268;
    private Mensajes ms;
    private AnalizadorLexico al;
    private TablaSimbolos tabla;
    
    public As12(Mensajes m, AnalizadorLexico a, TablaSimbolos t){
        ms = m;
        al = a;
        tabla = t;
    }
    
    public Token ejecutar(Token t, char c) {
        t.setId(CONSTANTE);
        double d = Double.valueOf(t.getLexema().substring(0, t.getLexema().length()-1));
        t.setLexema(String.valueOf(d)); // TODO verificar bien este rango 
        if (d > Short.MIN_VALUE && d < Short.MAX_VALUE){
            if (tabla.contieneLexema(t.getLexema())){
                tabla.getEntradaTS(t.getLexema()).incrementarCont();
                t.setEntradaTS(tabla.getEntradaTS(t.getLexema()));
            }
            else{
                t.setId(CONSTANTE);
                tabla.addETS(t.getLexema(), t.getETS());
                tabla.getEntradaTS(t.getLexema()).setTipo("doble"); 
            }
            ms.token(al.getNroLinea(), t.getLexema());
        }
        else ms.error(al.getNroLinea(), al.getMensaje(1), "LEXICO");
        
        al.releerCaracter();
        t.noSeAgregoCaracterLeido();
        return t;
    }
	
	
}
