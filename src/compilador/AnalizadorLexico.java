package compilador;

public class AnalizadorLexico {
	public static final Short FIN = 270;

    private int nroLinea;                       //numero de linea 
    private String codFuente;                //codigo fuente tomado del panel de texto ´grafica
    private TablaSimbolos tablaSimb;              //tabla de simbolos que contiene los tokens
    private Mensajes msj;      //lleva los msj a la interfaz
    private Token token;                        //token actual
    private int estado;                         //estado actual
    private int posicion;                       //posicion en el codigo fuente
    private AccionesSemantica as1, as2, as3, as4, as5, as6, as7, as8, as9, as10, as11, as12, as13; //acciones semanticas
    private boolean eof;                        //booleana que indica si se llegó al final del archivo
    private boolean fin;

                                  //  L  D  b  _  $  &  +  -  /  *  >  <  =  :  ^  (  )  [  ]  {  }  ,  .  ;  " OTRO TyB /n  EOF        
    private int[][] matrizEstados = {{ 1, 2, 1,-1,-1,-1,-1,-1,-1,-1, 4, 4,-1, 3, 3,-1,-1,11,-1,-1,-1,-1, 6,-1, 5, 0, 0 , 0 , 0},    //estado 0 
    								 { 1, 1, 1, 1, 1, 1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1 ,-1 , -1},    //estado 1
    								 {-1, 2, 9,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, 7,-1,-1,-1,-1 ,-1 , -1},    //estado 2
                                     {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1 ,-1 , -1},    //estado 3
                                     {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1 ,-1 , -1},    //estado 4
                                     { 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5,-1, 5, 5 ,-1 , -1},    //estado 5
                                     {-1, 7,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1 ,-1 , -1},    //estado 6
                                     {-1, 7, 8,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1 ,-1 , -1},    //estado 7
                                     {-1,10,-1,-1,-1,-1, 9, 9,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1 ,-1 , -1},    //estado 8
                                     {-1,10,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1 ,-1 , -1},    //estado 9
                                     {-1,10,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1 ,-1 , -1},    //estado 10
                                     {-1,-1,-1,-1,-1,-1,-1,12,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1 ,-1 , -1},    //estado 11
                                     {12,12,12,12,12,12,12,13,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12 ,12 , -1},    //estado 12
                                     {12,12,12,12,12,12,12,13,12,12,12,12,12,12,12,12,12,12, 0,12,12,12,12,12,12,12,12 ,12 , -1}};   //estado 13

    private AccionesSemantica[][] matrizAS;


    public AnalizadorLexico(String cf, Mensajes m){
        tablaSimb = new TablaSimbolos();
        msj = m;
        this.codFuente = cf;
        this.nroLinea = 1;
        eof = false;
        fin = false;
        posicion = 0;
        inicializarAS();
    }
    
    public int yylex(){ // devuelve el ID del token pedido por el parser
        this.estado = 0;
        this.token = new Token();
        while (estado != -1 && !eof){
            char caracter = codFuente.charAt(posicion);                      	
            int simbolo = getColumna(caracter);                                
            token = (matrizAS[estado][simbolo]).ejecutar(token, caracter);     
            if (!token.consumioCaracter())                                      
                posicion--;                                                    
            if (caracter == '\n' && token.consumioCaracter())                  
                nroLinea++;                                                    
            posicion++;                                                        
            estado = matrizEstados[estado][simbolo];                          
        }
        
        if (eof && !fin){                                                       //Cuando llegamos al final del archivo
            msj.tablaDeSimbolos();
            fin = true;
            return FIN;                                                         //Primero devolvemos el "FIN"
        }

        if (eof && fin)                                                         //Cuando llegamos al final del archivo
            return 0;                                                           //Luego de devolver el "FIN" devolvemos el "0"
        
        return token.getId().intValue();
    }
    
    //retrocede la posición para releer un caracter
    public void releerCaracter(){
        posicion--;
    }
    
    //devuelve el número de línea actual
    public int getNroLinea(){
        return this.nroLinea;
    }

    //devuelve el token actual
    public Token getToken(){
        return token;
    }
    
    //devuelve la tabla de simbolos
    public TablaSimbolos getTablaDeSimbolos(){
        return tablaSimb;
    }

    private void inicializarAS(){
        
        matrizAS = new AccionesSemantica[14][29];
        as1 = new As1();
        as2 = new As2();
        as3 = new As3(tablaSimb, this, msj);
        as4 = new As4(tablaSimb, msj, this);
        as5 = new As5(msj,this);
        as6 = new As6(msj, this);
        as7 = new As7();
        as8 = new As8(msj,this);
        as9 = new As9(msj, this, tablaSimb);
        as10 = new As10();
        as11 = new As11(msj, this);
        as12 = new As12(msj, this, tablaSimb);
        as13 = new As13(tablaSimb, this, msj);
        
        //Estado 0
        for (int i = 0; i <= 28; i++)
            matrizAS[0][i] = as8;
        matrizAS[0][0] = as1;
        matrizAS[0][1] = as1;
        matrizAS[0][2] = as1;
        matrizAS[0][3] = as11;
        matrizAS[0][4] = as11;
        matrizAS[0][5] = as11;
        matrizAS[0][10] = as1;
        matrizAS[0][11] = as1;
        matrizAS[0][13] = as1;
        matrizAS[0][14] = as1;
        matrizAS[0][17] = as1;
        matrizAS[0][22] = as1;
        matrizAS[0][24] = as10;
        matrizAS[0][25] = as7;
        matrizAS[0][26] = as7;
        matrizAS[0][27] = as7;
        matrizAS[0][28] = as7; 
        //estado 1
        for (int i = 0; i <= 28; i++)
            matrizAS[1][i] = as4;
        matrizAS[1][0] = as2;
        matrizAS[1][1] = as2;
        matrizAS[1][2] = as2;
        matrizAS[1][3] = as2; 
        matrizAS[1][4] = as2; 
        matrizAS[1][5] = as2; 
        matrizAS[1][28] = as4; 
        //estado 2
        for (int i = 0; i <= 28; i++)
            matrizAS[2][i] = as13;
        matrizAS[2][1] = as2;
        matrizAS[2][2] = as2;
        matrizAS[2][22] = as2;
        matrizAS[2][28] = as13; 
        
        //estado 3
        for (int i = 0; i <= 28; i++)
            matrizAS[3][i] = as11; 
        matrizAS[3][12] = as5;
        
        //estado 4
        for (int i = 0; i <= 28; i++)
            matrizAS[4][i] = as6;
        matrizAS[4][12] = as5;
        
        //estado 5
        for (int i = 0; i <= 28; i++)
            matrizAS[5][i] = as2;
        matrizAS[5][24] = as9;
        matrizAS[5][27] = as11; 
        matrizAS[5][28] = as11; 
        
        //estado 6
        for (int i = 0; i <= 28; i++)
            matrizAS[6][i] = as11;
        matrizAS[6][1] = as2;
        matrizAS[6][22] = as5;
       
        
        //estado 7
        for (int i = 0; i <= 28; i++)
            matrizAS[7][i] = as3;
        matrizAS[7][1] = as2;
        matrizAS[7][2] = as2;  

        
        //estado 8
        for (int i = 0; i <= 28; i++)
            matrizAS[8][i] = as12;
        matrizAS[8][1] = as2;
        matrizAS[8][6] = as2; 
        matrizAS[8][7] = as2;  
        
     
        //estado 9
        for (int i = 0; i <= 28; i++)
            matrizAS[9][i] = as11;
        matrizAS[9][1] = as2;
        
        //estado 10
        for (int i = 0; i <= 28; i++)
            matrizAS[10][i] = as3;
        matrizAS[10][1] = as2;
   
        //estado 11
        for (int i = 0; i <= 28; i++)
            matrizAS[11][i] = as6;
        matrizAS[11][7] = as10;

        
        //estado 12
        for (int i = 0; i <= 28; i++)
            matrizAS[12][i] = as7;
        matrizAS[12][28] = as11; 
      
        
        //estado 13
        for (int i = 0; i <= 28; i++)
            matrizAS[13][i] = as7;
        matrizAS[13][28] = as11; 
    }
    
    private int getColumna(int caracter) { // obtiene la columna en la que está cada caracter en la matriz de estados 
            //comprueba que llegue una letra
            if ((caracter >= 65 && caracter <= 90) || (caracter >= 97 && caracter <= 122))
                if ((caracter != 'B') && (caracter != 'b')) //B o b se trata en otra columna
                    return 0;  // Retorna columna de letras
            if (caracter >= 48 && caracter <= 57)
                    return 1;  // Retorna columna de digitos
            if ((caracter == 'B') || (caracter =='b'))
                    return 2;
            if (caracter == '_')
                    return 3;
            if (caracter == '$')
                    return 4;
            if (caracter == '&')
                    return 5;
            if (caracter == '+')
                    return 6;
            if (caracter == '-')
                    return 7;
            if (caracter == '/')
                    return 8;
            if (caracter == '*')
                    return 9;
            if (caracter == '>')
                    return 10;
            if (caracter == '<')
                    return 11;
            if (caracter == '=')
                    return 12;
            if (caracter == ':')
                    return 13;
            if (caracter == '^')
                    return 14;
            if (caracter == '(')
                    return 15;
            if (caracter == ')')
                    return 16;
            if (caracter == '[')  
                    return 17;  
            if (caracter ==']')
            		return 18;
            if (caracter =='{')
        			return 19;
            if (caracter =='}')
        			return 20;
            if (caracter ==',')
        			return 21;
            if (caracter =='.')
        			return 22;
            if (caracter ==';')
        			return 23;
            if (caracter =='"')
        			return 24;
            if ((caracter == 32) || (caracter == 9))
                    return 26;  // ESPACIO BLANCO O TABULACION
            if (caracter == '\n')
            		return 27;
            if (caracter == 255){
                    eof = true;
                    return 28;  //FIN DE ARCHIVO
            }
            return 25;
    }

    
    public String getMensaje(int nro){ 
        switch(nro){
        //ERRORES LEXICOS
        case 1: return "Constante double fuera del rango permitido";
        case 2: return "Carácter no identificado";
        case 3: return "Construcción de token erróneo";
        case 20: return "Constante entero fuera de rango permitido";

        //ERRORES SINTACTICOS
        case 4: return "No se encontró el fin de archivo";
        case 5: return "Falta el bloque de sentencias ejecutables";    
        case 6: return "Falta el bloque de sentencias declarativas";
        case 7: return "Se esperaba un ';'";
        case 8: return "Falta el tipo de la declaración";
        case 9: return "Sentencia declarativa incorrecta";
        case 11: return "Falta el identificador de la asignación";
        case 12: return "Falta el identificador de la asignación y se esperaba un ';'";
        case 13: return "Bloque de sentencias sin finalizar falta '}'";    
        case 14: return "Bloque de sentencias sin inicializar falta '{'";
        case 15: return "Falta abrir paréntesis '('";
        case 16: return "Falta cerrar paréntesis ')'";
        case 17: return "Parámetro del imprimir incorrecto";
        case 18: return "Falta palabra reservada 'imprimir'";  
        case 19: return "Sentencia incorrecta";

        // ESTRUCTURAS SINTACTICAS
        case 30: return "Sentencia declarativa";
        case 31: return "Sentencia de asignación";
        case 32: return "Sentencia de selección simple";
        case 33: return "Sentencia de iteración";
        case 34: return "Sentencia de impresión de caracteres";
        case 35: return "Bloque de sentencias";
        case 37: return "Sentencia declaracion de vector";
        case 38: return "Falta abrir corchetes '['";
        case 39: return "Falta cerrar corchetes ']'";
        case 40: return "Falta declarar los dos puntos ..";
        case 41: return "Falta agregar el tipo";
        case 42: return "Falta agregar el tipo 'vector'";
        case 43: return "Asignacion de vector" ;
        case 44: return "Falta agregar la expresion en la asignacion del vector" ;
        case 45: return "Error en el token de asgnacion se espera un ':='" ;
        case 46: return "No se reconoce la palabra 'entonces'" ;
        case 47: return "No se reconoce la palabra reservada 'si'";
        case 48: return "Sentencia de seleccion completa" ;
        case 49: return "No se reconoce la palabra 'iterar'" ;
        case 50: return "No se reconoce la palabra 'hasta'" ;
        case 51: return "No se reconoce '(' en la condicion" ;
        case 52: return "No se reconoce ')' en la condicion" ;
        case 53: return "error en la condicion";
        case 54: return "no se reconoce ':=' en la asignacion";
        case 55: return "error en la expresion de la asignacion ";
        
        case 57: return "falta la ',' entre los identificadores" ;
        case 58: return "falta '(' en la sentencia imprimir";
        case 59: return "falta ')' en la sentencia imprimir";
        case 60: return "identificador no declarado" ; 
        case 61: return "el identificador ya fue declarado";
        case 62: return "el identificador no es un vector" ;
        case 63: return "el tipo no es el mismo en la operacion *";
        case 64: return "el tipo no es el mismo en la operacion /";
        case 65: return "el tipo no es el mismo en la operacion -";
        case 66: return "el tipo no es el mismo en la operacion +";
        case 67: return "el tipo del indice del vector no es entero" ;
        case 68: return "los tipos en la asignacion son diferentes";
        case 69: return "el rango menor del vector es mayor que el mayor";
        case 70: return "el vector tiene que tener el indice entre [ ]" ;
        case 71: return "el rango del vector es negativo";
        case 72: return "los tipos en la comparacion son diferentes";
        }
        return null;
    }

}
