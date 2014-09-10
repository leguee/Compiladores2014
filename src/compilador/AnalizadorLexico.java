package compilador;

public class AnalizadorLexico {
	public static final Short FIN = 273;

    private int nroLinea;                       //numero de linea 
    private String codFuente;                //codigo fuente tomado del panelText
    private TablaSimbolos tablaSimb;              //tabla de simbolos que contiene los tokens
    private Mensajes manejador;      //lleva los msj a la interfaz
    private Token token;                        //token actual
    private int estado;                         //estado actual
    private int posicion;                       //posicion en el codigo fuente
    private AccionesSemantica as1, as2, as3, as4, as5, as6, as7, as8, as9, as10, as11, as12; //acciones semanticas
    private boolean eof;                        //booleana que indica si se llegó al final del archivo
    private boolean fin;

                                    // L  D \n  *  '  +  -  /  (  )  ,  ;  =  !  <  >  .  d SyT EOF OTRO
    private int[][] matrizEstados = {{ 1, 2, 0,-1, 6,-1,-1,-1, 3,-1,-1,-1, 7, 8, 7, 7, 9, 1, 0, 0, 0},    //estado 0
                                     { 1, 1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, 1,-1,-1,-1},    //estado 1
                                     {-1, 2,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,10,11,-1,-1,-1},    //estado 2
                                     {-1,-1,-1, 4,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},    //estado 3
                                     { 4, 4, 4, 5, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 0, 4},    //estado 4
                                     { 4, 4, 4, 4, 4, 4, 4, 4, 4, 0, 4, 4, 4, 4, 4, 4, 4, 4, 4, 0, 4},    //estado 5
                                     { 6, 6, 0, 6,-1, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 0, 6},    //estado 6
                                     {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},    //estado 7
                                     { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,-1, 0, 0, 0, 0, 0, 0, 0, 0},    //estado 8
                                     { 0,10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},    //estado 9
                                     {-1,10,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,11,-1,-1,-1},    //estado 10
                                     {-1,13,-1,-1,-1,12,12,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},    //estado 11
                                     { 0,13, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},    //estado 12
                                     { 0,13, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},    //estado 13
                                     {-1,13,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1}};   //estado 14

    private AccionSemantica[][] matrizAS;


    public AnalizadorLexico(String cf, Mensajes m){
        tablaSimb = new TablaSimbolos();
        manejador = m;
        this.codigoFuente = cf;
        this.nroLinea = 1;
        eof = false;
        fin = false;
        posicion = 0;
        inicializarAS();
    }
    
    public int yylex(){
        this.estado = 0;
        this.token = new Token();
        while (estado != -1 && !eof){
            char caracter = codigoFuente.charAt(posicion);                      //Leo el caracter actual
            int simbolo = getColumna(caracter);                                 //Obtengo la columna en las matrices del caracter leído
            token = (matrizAS[estado][simbolo]).ejecutar(token, caracter);              //Ejecuto la acción semántica correspondiente
            if (!token.consumioCaracter())                                      //Si el caracter no fue consumido
                posicion--;                                                     //Retrocedo la posición para volverlo a leer
            if (caracter == '\n' && token.consumioCaracter())                   //Si era el carcater de salto de línea
                nroLinea++;                                                     //Aumento en uno el número de líneas
            posicion++;                                                         //Aumento la posición en el código fuente
            estado = matrizEstados[estado][simbolo];                            //Obtengo el nuevo estado actual
        }
        
        if (eof && !fin){                                                       //Cuando llegamos al final del archivo
            manejador.tablaDeSimbolos();
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
    public TablaDeSimbolos getTablaDeSimbolos(){
        return tabla;
    }

    private void inicializarAS(){
        
        matrizAS = new AccionSemantica[14][21];
        as1 = new AS1();
        as2 = new AS2();
        as3 = new AS3(tabla,manejador,this);
        as4 = new AS4(tabla,manejador,this);
        as5 = new AS5(manejador,this);
        as6 = new AS6();
        as7 = new AS7(manejador,this);
        as8 = new AS8(manejador,this);
        as9 = new AS9();
        as10 = new AS10(manejador,this);
        as11 = new AS11(manejador,this,tabla);
        as12 = new AS12(manejador,this,tabla);
        
        //Estado 0
        for (int i = 0; i <= 20; i++)
            matrizAS[0][i] = as5;
        matrizAS[0][0] = as1;
        matrizAS[0][1] = as1;
        matrizAS[0][2] = as6;
        matrizAS[0][4] = as9;
        matrizAS[0][8] = as1;
        matrizAS[0][12] = as1;
        matrizAS[0][13] = as1;
        matrizAS[0][14] = as1;
        matrizAS[0][15] = as1;
        matrizAS[0][16] = as1;
        matrizAS[0][17] = as1;
        matrizAS[0][18] = as6;
        matrizAS[0][19] = as6;
        matrizAS[0][20] = as10;
        
        //estado 1
        for (int i = 0; i <= 20; i++)
            matrizAS[1][i] = as3;
        matrizAS[1][0] = as2;
        matrizAS[1][1] = as2;
        matrizAS[1][17] = as2;
        
        //estado 2
        for (int i = 0; i <= 20; i++)
            matrizAS[2][i] = as4;
        matrizAS[2][1] = as2;
        matrizAS[2][16] = as2;
        matrizAS[2][17] = as2;
        
        //estado 3
        for (int i = 0; i <= 20; i++)
            matrizAS[3][i] = as7;
        matrizAS[3][3] = as9;
        
        //estado 4
        for (int i = 0; i <= 20; i++)
            matrizAS[4][i] = as6;
        matrizAS[4][19] = as10;
        
        //estado 5
        for (int i = 0; i <= 20; i++)
            matrizAS[5][i] = as6;
        matrizAS[5][19] = as10;
        
        //estado 6
        for (int i = 0; i <= 20; i++)
            matrizAS[6][i] = as2;
        matrizAS[6][4] = as11;
        matrizAS[6][2] = as10;
        matrizAS[6][19] = as10;
        
        //estado 7
        for (int i = 0; i <= 20; i++)
            matrizAS[7][i] = as7;
        matrizAS[7][12] = as8;
        
        //estado 8
        for (int i = 0; i <= 20; i++)
            matrizAS[8][i] = as10;
        matrizAS[8][12] = as8;
        
        //estado 9
        for (int i = 0; i <= 20; i++)
            matrizAS[9][i] = as10;
        matrizAS[9][1] = as2;
        
        //estado 10
        for (int i = 0; i <= 20; i++)
            matrizAS[10][i] = as4;
        matrizAS[10][1] = as2;
        matrizAS[10][17] = as2;
        
        //estado 11
        for (int i = 0; i <= 20; i++)
            matrizAS[11][i] = as12;
        matrizAS[11][1] = as2;
        matrizAS[11][5] = as2;
        matrizAS[11][6] = as2;
        
        //estado 12
        for (int i = 0; i <= 20; i++)
            matrizAS[12][i] = as10;
        matrizAS[12][1] = as2;
        
        //estado 13
        for (int i = 0; i <= 20; i++)
            matrizAS[13][i] = as4;
        matrizAS[13][1] = as2;
    }
    
    private int getColumna(int caracter) {
            //comprueba que llegue una letra
            if ((caracter >= 65 && caracter <= 90) || (caracter >= 97 && caracter <= 122))
                if ((caracter != 'D') && (caracter != 'd')) //D o d se trata en otra columna
                    return 0;  // Retorna columna de letras
            if (caracter >= 48 && caracter <= 57)
                    return 1;  // Retorna columna de digitos
            if (caracter == '\n')//salto de linea
                    return 2;
            if (caracter == '*')
                    return 3;
            if (caracter == '\'')
                    return 4;
            if (caracter == '+')
                    return 5;
            if (caracter == '-')
                    return 6;
            if (caracter == '/')
                    return 7;
            if (caracter == '(')
                    return 8;
            if (caracter == ')')
                    return 9;
            if (caracter == ',')
                    return 10;
            if (caracter == ';')
                    return 11;
            if (caracter == '=')
                    return 12;
            if (caracter == '!')
                    return 13;
            if (caracter == '<')
                    return 14;
            if (caracter == '>')
                    return 15;
            if (caracter == '.')
                    return 16;
            if ((caracter == 'D') || (caracter =='d'))
                    return 17;  // L o l para delimitar un longint
            if ((caracter == 32) || (caracter == 9))
                    return 18;  // ESPACIO BLANCO O TABULACION
            if (caracter == 255){
                    eof = true;
                    return 19;  //FIN DE ARCHIVO
            }
            return 20;
    }

    
    public String getMensaje(int nro){
        switch(nro){
            //ERRORES LEXICOS
            case 1: return "Constante double fuera del rango permitido";
            case 2: return "Carácter no identificado";
            case 3: return "Construcción de token erróneo";

            //ERRORES SINTACTICOS
            case 4: return "No se encontró el fin de archivo";
            case 5: return "Falta el bloque de sentencias ejecutables";    
            case 6: return "Falta el bloque de sentencias declarativas";
            case 7: return "Se esperaba un ';'";
            case 8: return "Falta el tipo de la declaración";
            case 9: return "Sentencia declarativa incorrecta";
            case 10: return "Sentencia expresión de retorno";
            case 11: return "Falta el identificador de la asignación";
            case 12: return "Falta el identificador de la asignación y se esperaba un ';'";
            case 13: return "Bloque de sentencias sin finalizar";    
            case 14: return "Bloque de sentencias sin inicializar";
            case 15: return "Falta abrir paréntesis '('";
            case 16: return "Falta cerrar paréntesis ')'";
            case 17: return "Parámetro del print incorrecto";
            case 18: return "Falta palabra reservada 'print'";  
            case 19: return "Sentencia incorrecta";
                
            /*    
            case 7: return "Error sintáctico";
            case 8: return "Falta abrir paréntesis '('";
            case 9: return "Falta cerrar paréntesis ')'";
            case 10: return "Bloque de sentencias sin inicializar";    
            case 11: return "Bloque de sentencias sin finalizar";
            case 12: return "Error en el bloque de sentencias";
            case 13: return "Variable redeclarada";
            case 14: return "Variable no declarada";
            case 15: return "Utilización de variable incorrecta";*/

            // ESTRUCTURAS SINTACTICAS
            case 30: return "Sentencia declarativa";
            case 31: return "Sentencia de asignación";
            case 32: return "Sentencia de selección";
            case 33: return "Sentencia de iteración";
            case 34: return "Sentencia de impresión de caracteres";
            case 35: return "Bloque de sentencias";
            case 36: return "Sentencia de return";

        }
        return null;
    }

}
