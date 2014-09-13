package compilador;

import java.util.Vector;

public class Token {

    public static final Short ID = 267;
    public static final Short CONSTANTE = 268;
    public static final Short STRING = 270;
    
    //Simbolos que forman el token
    private String lexema;
    private boolean ultimoCaracter;
    private int contadorDeReferencia;
    private ETS entrada;
    private Short id;
    private String tipo;
    private Vector<Token> parametros;
    
    public Token(){
        lexema = new String();
        ultimoCaracter = false;
        contadorDeReferencia = 1;
        entrada=null;
        id = 0;
        tipo = null;
        parametros = null;
    }

    // Agrega el caracter recibido al final del string
    public void agregarCaracter(char c){
        this.lexema = this.lexema + c;
        ultimoCaracter = true;
    }

    //Setea que el último caracter leído no fue agregado al token
    public void ultimoCaracterNoLeido(){
        ultimoCaracter = false;
    }

    //Setea que el último caracter leído fue agregado al token
    public void ultimoCaracterLeido(){
        ultimoCaracter = true;
    }
    
    public boolean consumioCaracter(){
        return ultimoCaracter;
    }
    
    //Devuelve la longitud del valor
    public int getLongitud(){
        return this.lexema.length();
    }
    
    //Devuelve el valor del token
    public String getLexema(){
        return this.lexema;
    }
    
    //Trunca a 15 caracteres el valor del token
    public void truncarId() {
	this.lexema = this.lexema.substring(0,15);
    }

    public void setId(Short id) {
        if ((id.equals(ID)) || (id.equals(STRING)) || (id.equals(CONSTANTE)))
            entrada = new ETS(id, this.lexema);  // Creo la entrada para la tabla de simbolos
        this.id = id;
    }

    public void setLexema(String nombre){
        this.lexema=nombre;
    }

    public Short getId(){
        if (entrada != null)
            return this.entrada.getId();
        return id;
    }

    public boolean equals(Token t){
        return this.lexema.equals(t.getLexema());
    }

    public void clear(){
        this.lexema= new String();
    }
    
    public void addMenos(){
        lexema = '-' + lexema;
    }

    public int getContRef(){
        return contadorDeReferencia;
    }

    public void incContRef(){
        contadorDeReferencia++;
    }

    public void decContRef(){
        contadorDeReferencia--;
    }
    
    public ETS getETS(){
        return this.entrada;
    }
    
    public void setETS(ETS ets){
        this.entrada = ets;
    }
    
    public String getTipo(){
        return tipo;
    }
    
    public void setTipo(String t){
        tipo = t;
    }
    
    public void addParametro(Token p){
        if (parametros == null)
            parametros = new Vector<Token>();
        parametros.addElement(p);
    }
    
    public void addParametros(Vector<Token> ps){
        parametros = ps;
    }
    
    public Vector<Token> getParametros(){
        return parametros;
    }


}
