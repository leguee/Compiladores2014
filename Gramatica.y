%{
package compilador;
import java.util.Vector;
import java.util.Enumeration;
%}

%token ENTERO
%token SI
%token ENTONCES
%token SINO
%token IMPRIMIR
%token ITERAR
%token HASTA
%token ID
%token CONSTANTE
%token DOBLE
%token STRING
%token COMPARADOR
%token ASIGNACION
%token FIN
%token RANGO
%token DE
%token VECTOR
%token CTEENTERO


/* GRAMATICA */
%% 

/* PROGRAMA */
programa : declaraciones sentencias_ejecutables FIN
		 | declaraciones sentencias_ejecutables error FIN {manejador.error(analizador.getNroLinea(),analizador.getMensaje(4),"SINTACTICO");}
		 | declaraciones error FIN {manejador.error(analizador.getNroLinea(),analizador.getMensaje(5),"SINTACTICO");}
		 | error sentencias_ejecutables FIN {manejador.error(analizador.getNroLinea(),analizador.getMensaje(6),"SINTACTICO");}
;

/* DECLARACIONES */
declaraciones : declaraciones declaracion 
			  | declaracion
;

declaracion : tipo lista_variables ';' 	{ 	manejador.estructuraSintactica(analizador.getNroLinea(), analizador.getMensaje(30));
											Enumeration e = ((Vector<Token>)$2.obj).elements();
											while (e.hasMoreElements()){
												Token token = (Token)e.nextElement();
												if (token.getETS().getTipo() == null){
													token.getETS().setTipo(token.getTipo());
												}
											}
										}
										
			| tipo lista_variables 		{	manejador.error(analizador.getNroLinea(), analizador.getMensaje(7),"SINTACTICO");}
			| error lista_variables ';' {	manejador.error(analizador.getNroLinea(), analizador.getMensaje(8),"SINTACTICO");}
			| tipo error ';' 			{	manejador.error(analizador.getNroLinea(), analizador.getMensaje(9),"SINTACTICO");}
			| vector_declaracion ';'	{manejador.estructuraSintactica(analizador.getNroLinea(), analizador.getMensaje(37));}
			
;


lista_variables : lista_variables ',' ID		{	Vector<Token> tokens = (Vector<Token>)$1.obj;
													Token token = (Token)$3.obj;
													token.setTipo("entero");
													tokens.add(token);
													$$.obj = tokens;
												}								
			    | ID 							{	Vector<Token> tokens = new Vector<Token>();
													Token token = (Token)$1.obj;
													token.setTipo("entero");
													tokens.add(token);
													$$.obj = tokens;
												}
;

vector_declaracion : ID '[' CTEENTERO RANGO CTEENTERO ']' VECTOR DE tipo  
				   | ID error CTEENTERO RANGO CTEENTERO ']' VECTOR DE tipo  {	manejador.error(analizador.getNroLinea(), analizador.getMensaje(38),"SINTACTICO");}
				   | ID '[' CTEENTERO RANGO CTEENTERO error VECTOR DE tipo { manejador.error(analizador.getNroLinea(), analizador.getMensaje(39),"SINTACTICO");}
				   | ID '[' CTEENTERO error CTEENTERO ']' VECTOR DE tipo {manejador.error(analizador.getNroLinea(), analizador.getMensaje(40),"SINTACTICO");}
				   | ID '[' CTEENTERO RANGO CTEENTERO ']' VECTOR DE error  {manejador.error(analizador.getNroLinea(), analizador.getMensaje(41),"SINTACTICO");}
				   | ID '[' CTEENTERO RANGO CTEENTERO ']' error DE tipo  { manejador.error(analizador.getNroLinea(), analizador.getMensaje(42),"SINTACTICO");}
				   | ID '[' CTEENTERO RANGO CTEENTERO ']' VECTOR error tipo  {  manejador.error(analizador.getNroLinea(), analizador.getMensaje(43),"SINTACTICO");}
				   
				   
tipo : DOBLE 
	 | ENTERO 
;


/* SENTENCIAS EJECUTABLES */

sentencias_ejecutables : sentencias_ejecutables sentencia  
			           | sentencia 
;

sentencia : asignacion
		  | seleccion
		  | iteracion
		  | print
		  | error ';' {manejador.error(analizador.getNroLinea(),analizador.getMensaje(19),"SINTACTICO");}
;

asignacion: ID ASIGNACION expresion ';' {manejador.estructuraSintactica(analizador.getNroLinea(), analizador.getMensaje(31));}
		  | ID ASIGNACION expresion error {manejador.error(analizador.getNroLinea(),analizador.getMensaje(7),"SINTACTICO");}
		  | error ASIGNACION expresion ';' {manejador.error(analizador.getNroLinea(),analizador.getMensaje(11),"SINTACTICO");}
		  | error ASIGNACION expresion {manejador.error(analizador.getNroLinea(),analizador.getMensaje(12),"SINTACTICO");}
		  | ID '[' expresion ']' ASIGNACION expresion ';' {manejador.estructuraSintactica(analizador.getNroLinea(), analizador.getMensaje(43));}
		  | error '[' expresion ']' ASIGNACION expresion ';' {manejador.error(analizador.getNroLinea(), analizador.getMensaje(11),"SINTACTICO");}
		  | ID error expresion ']' ASIGNACION expresion ';' {manejador.error(analizador.getNroLinea(), analizador.getMensaje(38),"SINTACTICO");}
		/* | ID '[' expresion error ASIGNACION expresion ';' {manejador.error(analizador.getNroLinea(), analizador.getMensaje(39));}*/
		  | ID '[' expresion ']' ASIGNACION expresion {manejador.error(analizador.getNroLinea(), analizador.getMensaje(7),"SINTACTICO");}
;

seleccion : seleccion_simple SINO bloque {manejador.estructuraSintactica(analizador.getNroLinea(), analizador.getMensaje(32));}
		  | seleccion_simple {manejador.estructuraSintactica(analizador.getNroLinea(), analizador.getMensaje(32));}
;


seleccion_simple : SI condicion ENTONCES bloque
;

bloque: '{' sentencias_ejecutables '}' {manejador.estructuraSintactica(analizador.getNroLinea(), analizador.getMensaje(35));}
	  | '{' sentencias_ejecutables error {manejador.error(analizador.getNroLinea(),analizador.getMensaje(13),"SINTACTICO");}
	  | '}' {manejador.error(analizador.getNroLinea(),analizador.getMensaje(14),"SINTACTICO");}
;


condicion: '(' expresion COMPARADOR expresion ')'
;

iteracion : ITERAR bloque HASTA condicion {manejador.estructuraSintactica(analizador.getNroLinea(), analizador.getMensaje(33));}
;

print: IMPRIMIR '(' STRING ')' ';' {manejador.estructuraSintactica(analizador.getNroLinea(), analizador.getMensaje(34));}
	 | IMPRIMIR '(' STRING ')' error {manejador.error(analizador.getNroLinea(),analizador.getMensaje(7),"SINTACTICO");}
	 | IMPRIMIR '(' error ')' ';' {manejador.error(analizador.getNroLinea(),analizador.getMensaje(17),"SINTACTICO");}
	 | error '(' STRING ')' ';' {manejador.error(analizador.getNroLinea(),analizador.getMensaje(18),"SINTACTICO");}
;

expresion : expresion '+' termino
		  | expresion '-' termino
		  | termino
;

termino : termino '*' factor
		| termino '/' factor
		| factor
;

factor : '-' CONSTANTE	{	String lexema = ((Token)$2.obj).getLexema();
							EntradaTS entrada = (EntradaTS)tabla.getTabla().get(lexema);
							String nuevoLexema = "-"+lexema;
							if (entrada.getContRef() == 1){
								if (tabla.getTabla().contains(nuevoLexema))
									((EntradaTS)tabla.getTabla().get(nuevoLexema)).incrementarCont();
								else {	EntradaTS nuevaEntrada = new EntradaTS(CONSTANTE, nuevoLexema);
										tabla.addETS(nuevaEntrada.getLexema(), nuevaEntrada);
										nuevaEntrada.setTipo("doble");
								}
								tabla.getTabla().remove(lexema);
							}
							else {
								entrada.decContRef();
								if (tabla.getTabla().containsKey(nuevoLexema))
									((EntradaTS)tabla.getTabla().get(nuevoLexema)).incrementarCont();
								else{
									EntradaTS nuevaEntrada = new EntradaTS(CONSTANTE, nuevoLexema);
									tabla.addETS(nuevaEntrada.getLexema(), nuevaEntrada);
									nuevaEntrada.setTipo("doble");
								}
							}
							$$.obj = tabla.getTabla().get(nuevoLexema);
						}
	   | CONSTANTE
	   | ID
	   | '-' CTEENTERO		{	String lexema = ((Token)$2.obj).getLexema();
							EntradaTS entrada = (EntradaTS)tabla.getTabla().get(lexema);
							String nuevoLexema = "-"+lexema;
							if (entrada.getContRef() == 1){
								if (tabla.getTabla().contains(nuevoLexema))
									((EntradaTS)tabla.getTabla().get(nuevoLexema)).incrementarCont();
								else {	EntradaTS nuevaEntrada = new EntradaTS(CONSTANTE, nuevoLexema);
										tabla.addETS(nuevaEntrada.getLexema(), nuevaEntrada);
										nuevaEntrada.setTipo("entero");
								}
								tabla.getTabla().remove(lexema);
							}
							else {
								entrada.decContRef();
								if (tabla.getTabla().containsKey(nuevoLexema))
									((EntradaTS)tabla.getTabla().get(nuevoLexema)).incrementarCont();
								else{
									EntradaTS nuevaEntrada = new EntradaTS(CONSTANTE, nuevoLexema);
									tabla.addETS(nuevaEntrada.getLexema(), nuevaEntrada);
									nuevaEntrada.setTipo("entero");
								}
							}
							$$.obj = tabla.getTabla().get(nuevoLexema);
						}
	   | CTEENTERO
	   | expresion_vector
;

expresion_vector : ID '[' expresion ']' 
			     /*| ID error expresion ']' {manejador.error(analizador.getNroLinea(),analizador.getMensaje(38),"SINTACTICO");}*/
				 | ID '[' expresion error {manejador.error(analizador.getNroLinea(),analizador.getMensaje(39),"SINTACTICO");}
		    	 | error '[' expresion ']' {manejador.error(analizador.getNroLinea(),analizador.getMensaje(11),"SINTACTICO");}
				 | ID '[' error ']' {manejador.error(analizador.getNroLinea(),analizador.getMensaje(44),"SINTACTICO");}
;


%%

void yyerror(String s) {
	if(s.contains("under"))
		System.out.println("par:"+s);
}

AnalizadorLexico analizador;
Mensajes manejador;
TablaSimbolos tabla;

public void setLexico(AnalizadorLexico al) {
	analizador = al;
	tabla = al.getTablaDeSimbolos();
}

public void setMensajes(Mensajes ms) {
	manejador = ms;
}

int yylex()
{
	int val = analizador.yylex();
	yylval = new ParserVal(analizador.getToken());
	yylval.ival = analizador.getNroLinea();
	
	return val;
}