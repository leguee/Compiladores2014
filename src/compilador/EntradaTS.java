package compilador;

public class EntradaTS {
	 public static final Short CONSTANTE = 265;
	 public static final short CTEENTERO=274;
	 public static final Short ID = 264;
	 public static int IDDOBLE = 1;
	 public static int IDENTERO = 1;
	 public static int IDAUX = 1;
	 public static final short AUX=275;
	
	private Short id;
	private String lexema;
	private String tipo;
	private int contRef;
	private int idDoble;
	private int idEntero;
	private String cadena;
	private int idAux;
	private String nombVector;


	private String rangoMenor = null ;
	private String rangoMayor = null ;

	private boolean declarada = false ;



	public int getContRef() {
		return contRef;
	}

	public void setContRef(int contRef) {
		this.contRef = contRef;
	}

	public String getTipo() {
		return tipo;
	}

	public void setId(Short id) {
		this.id = id;
	}

	public EntradaTS(Short id, String lexema) {
		this.id = id; 
		this.setLexema(lexema);
		tipo = null;
		setNombVector(null);
		contRef = 1;
		 cadena = null;
	    if (this.id.equals(CONSTANTE))
        {
            idDoble = IDDOBLE;
            IDDOBLE++;
        }
        else
            this.idDoble = 0;
	    
	    if (this.id.equals(CTEENTERO))
        {
            idEntero = IDENTERO;
            IDENTERO++;
        }
        else
            this.idEntero = 0;
	    if (this.id.equals(AUX)) {
	    	idAux = IDAUX;
	    	IDAUX++;
	    }
        
	}

	public Short getId() {
		return this.id;
	}

	public void incrementarCont() {
		contRef++;

	}
	public void decContRef(){
		this.contRef--;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;

	}

	public String getLexema() {
		return lexema;
	}

	public void setLexema(String lexema) {
		this.lexema = lexema;
	}
	public void invertir(){
		this.lexema= '-'+this.lexema;
	}

	public String imprimir(){
		return lexema;
	}

	public String getRangoMenor() {
		return rangoMenor;
	}

	public void setRangoMenor(String rangoMenor) {
		this.rangoMenor = rangoMenor;
	}

	public String getRangoMayor() {
		return rangoMayor;
	}

	public void setRangoMayor(String rangoMayor) {
		this.rangoMayor = rangoMayor;
	}

	public String getLexAss() {
		 if (id.equals(ID))
	        {   
	            return new String("_" + this.getLexema());
	        }
	     else
	         if (id.equals(CONSTANTE)) {
	        	return new String("_doble" + this.idDoble);
	         }
		 else if(id.equals(AUX)) { 
        	 return new String("_@aux"+this.idAux);
         } else
        	 return new String("_entero"+ this.idEntero);
		 		
	}
	
    public void setCadena(int c){ // Me trata las cadenas en las declaraciones, y aumenta el c 
        cadena = new String("cadena"+c);
    }

    public String getCadena(){
        return cadena;
    }

	
	
	public void setDeclarada (){
		declarada = true ;
	}
	
	public boolean isDeclarada (){
		return declarada ;
	}
	public int getIdAux() {
    	return this.idAux;
    }

	public String getNombVector() {
		return nombVector;
	}

	public void setNombVector(String nombVector) {
		this.nombVector = nombVector;
	}
	
}
