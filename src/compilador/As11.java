package compilador;

public class As11 extends AccionesSemantica { // Vacía el token e informa error.

		private Mensajes ms;
	    private AnalizadorLexico al;

	    public As11(Mensajes m, AnalizadorLexico a) {
	        ms = m;
	        al = a;
	    }

	    public Token ejecutar(Token token, char c) {
	        token = new Token();
	        int aux = entrada(c); // me dice el tipo de mensaje
	        if (aux == 20){
	            token.seAgregoCaracterLeido();
	            ms.error(al.getNroLinea(), al.getMensaje(2), "LEXICO");
	        }
	        else
	            ms.error(al.getNroLinea(), al.getMensaje(3), "LEXICO");
	        return token;
	    }

	    
	    private int entrada(int caracter) { //TODO verificar esto
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
	                    return 19;  //FIN DE ARCHIVO
	            }
	            return 20;
	    }

}
