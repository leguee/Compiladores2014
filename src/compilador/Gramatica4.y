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
programa : declaraciones sentencias_ejecutables FIN	{	ArbolSintactico a1 = ((ArbolSintactico)$1.obj);
														ArbolSintactico a2 = ((ArbolSintactico)$2.obj);
														arbol = new ArbolSintactico ("program",a1,a2);
														arbol.imprimir (0);
														
													}
		 | declaraciones sentencias_ejecutables error FIN {manejador.error(analizador.getNroLinea(),analizador.getMensaje(4),"SINTACTICO");}
		 | declaraciones error FIN {manejador.error(analizador.getNroLinea(),analizador.getMensaje(5),"SINTACTICO");}
		 | error sentencias_ejecutables FIN {manejador.error(analizador.getNroLinea(),analizador.getMensaje(6),"SINTACTICO");}
;

/* DECLARACIONES */
declaraciones : declaraciones declaracion	{	
												ArbolSintactico a1 = ((ArbolSintactico)$1.obj);
																ArbolSintactico a2 = ((ArbolSintactico)$2.obj);
																$$.obj = new ArbolSintactico ("daclaraciones",a1,a2);
											}
			  | declaracion	/*{ $$.obj = ((ArbolSintactico)$1.obj) ; }*/
;

declaracion : tipo lista_variables ';' 	{ 	manejador.estructuraSintactica(analizador.getNroLinea(), analizador.getMensaje(30));
											Enumeration e = ((Vector<Token>)vt).elements();
											String lexema = ((ArbolSintactico)$1.obj).getValor();
											while (e.hasMoreElements()){
												Token token = (Token)e.nextElement();
												token.getETS().setTipo(lexema);
											}
											vt = new Vector<Token>();
											ArbolSintactico a1 = ((ArbolSintactico)$1.obj);
											ArbolSintactico a2 = ((ArbolSintactico)$2.obj);
											$$.obj = new ArbolSintactico ("declaracion",a1,a2);
											
										}
										
			| tipo lista_variables  error	{	manejador.error(analizador.getNroLinea(), analizador.getMensaje(7),"SINTACTICO");}
			| error lista_variables ';' {	manejador.error(analizador.getNroLinea(), analizador.getMensaje(8),"SINTACTICO");}
			| tipo error ';' 			{	manejador.error(analizador.getNroLinea(), analizador.getMensaje(9),"SINTACTICO");}
			| vector_declaracion ';'	{	manejador.estructuraSintactica(analizador.getNroLinea(), analizador.getMensaje(37));
											/*{ $$.obj = ((ArbolSintactico)$1.obj) ; }*/
										}
			
;




lista_variables : lista_variables ',' ID		{	Token token = (Token)$3.obj;
													token.setTipo("entero");
													vt.add(token);
													ArbolSintactico a3 = new Hoja (tabla.getTabla().get(token.getLexema()),token.getLexema());
													ArbolSintactico a1 = ((ArbolSintactico)$1.obj);
													$$.obj = new ArbolSintactico ("lista var",a1,a3);
												}								
				| lista_variables ID 			{	manejador.error(analizador.getNroLinea(), analizador.getMensaje(57),"SINTACTICO");}
			    | ID 							{	Vector<Token> tokens = new Vector<Token>();
													Token token = (Token)$1.obj;
													token.setTipo("entero");
													tokens.add(token);
													vt = tokens ;		
													$$.obj = new Hoja (tabla.getTabla().get(token.getLexema()),token.getLexema());
												}
				
;

vector_declaracion : ID '[' CTEENTERO RANGO CTEENTERO ']' VECTOR DE tipo	{	
																				Token ident = (Token)$1.obj ;
																				String iden = ident.getLexema();
																				String rangoMenor = ((Token)$3.obj).getLexema();
																				String rangoMayor = ((Token)$5.obj).getLexema();
																				ArbolSintactico rMenor = new Hoja (tabla.getTabla().get(rangoMenor),rangoMenor);
																				ArbolSintactico rMayor = new Hoja (tabla.getTabla().get(rangoMayor),rangoMayor);
																				ArbolSintactico rango = new ArbolSintactico (("rango") ,rMenor,rMayor);
																				ArbolSintactico tipo = ((ArbolSintactico)$9.obj);
																				ident.getETS().setTipo(tipo.getValor());
																				ident.getETS().setRangoMenor (rangoMenor.getValor();
																				ident.getETS().setRangoMayor (rangoMayor.getValor();
																				$$.obj = new ArbolSintactico (iden, rango, tipo);
																			}
				   | ID error CTEENTERO RANGO CTEENTERO ']' VECTOR DE tipo  {	manejador.error(analizador.getNroLinea(), analizador.getMensaje(38),"SINTACTICO");}
				   | ID '[' CTEENTERO RANGO CTEENTERO error VECTOR DE tipo { manejador.error(analizador.getNroLinea(), analizador.getMensaje(39),"SINTACTICO");}
				   | ID '[' CTEENTERO error CTEENTERO ']' VECTOR DE tipo {manejador.error(analizador.getNroLinea(), analizador.getMensaje(40),"SINTACTICO");}
				   | ID '[' CTEENTERO RANGO CTEENTERO ']' VECTOR DE error  {manejador.error(analizador.getNroLinea(), analizador.getMensaje(41),"SINTACTICO");}
				   | ID '[' CTEENTERO RANGO CTEENTERO ']' error DE tipo  { manejador.error(analizador.getNroLinea(), analizador.getMensaje(42),"SINTACTICO");}
				   | ID '[' CTEENTERO RANGO CTEENTERO ']' VECTOR error tipo  {  manejador.error(analizador.getNroLinea(), analizador.getMensaje(43),"SINTACTICO");}
				   
				   
tipo : DOBLE	{	String lexema = ((Token)$1.obj).getLexema();
					$$.obj = new Hoja (tabla.getTabla().get(lexema),lexema);
				}
	 | ENTERO 	{	String lexema = ((Token)$1.obj).getLexema();
					$$.obj = new Hoja (tabla.getTabla().get(lexema),lexema);
				}
;


/* SENTENCIAS EJECUTABLES */

sentencias_ejecutables : sentencias_ejecutables sentencia  {	ArbolSintactico a1 = ((ArbolSintactico)$1.obj);
																ArbolSintactico a2 = ((ArbolSintactico)$2.obj);
																$$.obj = new ArbolSintactico ("sentencias",a1,a2);
															}
			           | sentencia /*{ $$.obj = ((ArbolSintactico)$1.obj);}*/
					   
;

sentencia : asignacion	
		  | seleccion	
		  | iteracion	
		  | print	
		  | error ';' {manejador.error(analizador.getNroLinea(),analizador.getMensaje(19),"SINTACTICO");}
		  | vector_declaracion {manejador.error(analizador.getNroLinea(),analizador.getMensaje(19),"SINTACTICO");}  
;

asignacion: ID ASIGNACION expresion ';' {manejador.estructuraSintactica(analizador.getNroLinea(), analizador.getMensaje(31));
										Token token1 = ((Token)$1.obj);
										ArbolSintactico a1 = new Hoja(tabla.getTabla().get(token1.getLexema()),token1.getLexema());
										ArbolSintactico a3 = ((ArbolSintactico)$3.obj);
										$$.obj = new ArbolSintactico ("asig", a1 , a3 );
										}
		  | ID ASIGNACION expresion error {manejador.error(analizador.getNroLinea(),analizador.getMensaje(7),"SINTACTICO");}
		  | ID ASIGNACION error ';' {manejador.error(analizador.getNroLinea(),analizador.getMensaje(55),"SINTACTICO");}
		  | error ASIGNACION expresion ';' {manejador.error(analizador.getNroLinea(),analizador.getMensaje(11),"SINTACTICO");}
		  | error ASIGNACION expresion  {manejador.error(analizador.getNroLinea(),analizador.getMensaje(12),"SINTACTICO");}
		  | ID error expresion ';'{manejador.error(analizador.getNroLinea(), analizador.getMensaje(54),"SINTACTICO");}
		  | ID '[' expresion ']' ASIGNACION expresion ';'	{	manejador.estructuraSintactica(analizador.getNroLinea(), analizador.getMensaje(43));
																String lexID = ((Token)$1.obj).getLexema();
																ArbolSintactico a1 = new Hoja (tabla.getTabla().get(lexID),lexID);
																ArbolSintactico a3 = ((ArbolSintactico)$3.obj);
																ArbolSintactico vec = new ArbolSintactico ("vector",a1,a3);
																ArbolSintactico a6 = ((ArbolSintactico)$6.obj);
																$$.obj = new ArbolSintactico ("asig vector", vec , a6 );
															}
		  | error '[' expresion ']' ASIGNACION expresion ';' {manejador.error(analizador.getNroLinea(), analizador.getMensaje(11),"SINTACTICO");}
		  | ID error expresion ']' ASIGNACION expresion ';' {manejador.error(analizador.getNroLinea(), analizador.getMensaje(38),"SINTACTICO");}
		  | ID '[' expresion ']' ASIGNACION expresion {manejador.error(analizador.getNroLinea(), analizador.getMensaje(7),"SINTACTICO");}
;

seleccion : seleccion_simple	{	manejador.estructuraSintactica(analizador.getNroLinea(), analizador.getMensaje(32));
								}
		  | seleccion_simple SINO bloque  { manejador.estructuraSintactica(analizador.getNroLinea(), analizador.getMensaje(48));
											ArbolSintactico sel_simple = ((ArbolSintactico)$1.obj);
											ArbolSintactico bloque_si = sel_simple.getHijoDer();
											 
											ArbolSintactico bloque_sino =  ((ArbolSintactico)$3.obj);
											ArbolSintactico cuerpo = new ArbolSintactico("cuerpo" , bloque_si, bloque_sino );
											cuerpo.getHijoDer().setValor ("sino");
											cuerpo.getHijoIzq().setValor ("si");
											sel_simple.setHijoDer(cuerpo) ;
											$$.obj = sel_simple ;
										  }
;


seleccion_simple : SI '(' condicion ')' ENTONCES bloque	{	ArbolSintactico cn = ((ArbolSintactico)$3.obj);
															ArbolSintactico bl = ((ArbolSintactico)$6.obj);
															$$.obj = new ArbolSintactico ("si",cn,bl);
														}
				 | SI '(' condicion ')' error bloque {manejador.error(analizador.getNroLinea(), analizador.getMensaje(46),"SINTACTICO");}
				 | ID '(' condicion ')' ENTONCES bloque {manejador.error(analizador.getNroLinea(), analizador.getMensaje(47),"SINTACTICO");}
				 | error '(' condicion ')' ENTONCES bloque {manejador.error(analizador.getNroLinea(), analizador.getMensaje(47),"SINTACTICO");}
				 | SI '(' error ')' ENTONCES bloque {manejador.error(analizador.getNroLinea(),analizador.getMensaje(53),"SINTACTICO");}
				 | SI error condicion ')' ENTONCES bloque {manejador.error(analizador.getNroLinea(),analizador.getMensaje(51),"SINTACTICO");}
				 | SI '(' condicion ENTONCES bloque  {manejador.error(analizador.getNroLinea(),analizador.getMensaje(52),"SINTACTICO");}
;

bloque: '{' sentencias_ejecutables '}'	{	manejador.estructuraSintactica(analizador.getNroLinea(), analizador.getMensaje(35));
											$$.obj = ((ArbolSintactico)$2.obj);
										}
	  | '{' sentencias_ejecutables error {manejador.error(analizador.getNroLinea(),analizador.getMensaje(13),"SINTACTICO");}
	  | '}' {manejador.error(analizador.getNroLinea(),analizador.getMensaje(14),"SINTACTICO");}
;


condicion: expresion COMPARADOR expresion	{ 	String lexema = ((Token)$2.obj).getLexema();
												ArbolSintactico a1 = ((ArbolSintactico)$1.obj);
												ArbolSintactico a2 = ((ArbolSintactico)$3.obj);
												$$.obj = new ArbolSintactico (lexema,a1,a2);
											}
		 | expresion error expresion  {manejador.error(analizador.getNroLinea(),analizador.getMensaje(53),"SINTACTICO");}
		 | error COMPARADOR expresion {manejador.error(analizador.getNroLinea(),analizador.getMensaje(53),"SINTACTICO");}
		 | expresion COMPARADOR error {manejador.error(analizador.getNroLinea(),analizador.getMensaje(53),"SINTACTICO");}
		 | COMPARADOR {manejador.error(analizador.getNroLinea(),analizador.getMensaje(53),"SINTACTICO");}
		 
;

iteracion : ITERAR bloque HASTA '(' condicion ')'	{	manejador.estructuraSintactica(analizador.getNroLinea(), analizador.getMensaje(33));
														ArbolSintactico bl = ((ArbolSintactico)$2.obj);
														ArbolSintactico cn = ((ArbolSintactico)$5.obj);
														$$.obj = new ArbolSintactico ("iterar",bl,cn);
													}
		/*  | error bloque HASTA '(' condicion ')' {manejador.error(analizador.getNroLinea(),analizador.getMensaje(49),"SINTACTICO");}*/
		  | ITERAR bloque error '(' condicion ')' {manejador.error(analizador.getNroLinea(),analizador.getMensaje(50),"SINTACTICO");}
		  | ITERAR bloque HASTA error condicion ')' {manejador.error(analizador.getNroLinea(),analizador.getMensaje(51),"SINTACTICO");}
		  | ITERAR bloque HASTA '(' condicion  {manejador.error(analizador.getNroLinea(),analizador.getMensaje(52),"SINTACTICO");}
;

print: IMPRIMIR '(' STRING ')' ';' {manejador.estructuraSintactica(analizador.getNroLinea(), analizador.getMensaje(34));
									String lexema = ((Token)$3.obj).getLexema();
									ArbolSintactico string = new Hoja (tabla.getTabla().get(lexema), lexema);
									$$.obj = new ArbolSintactico ("print" , string , null );
}
	 | IMPRIMIR '(' STRING ')' error {manejador.error(analizador.getNroLinea(),analizador.getMensaje(7),"SINTACTICO");}
	 | IMPRIMIR '(' error ')' ';' {manejador.error(analizador.getNroLinea(),analizador.getMensaje(17),"SINTACTICO");}
	 | error '(' STRING ')' ';' {manejador.error(analizador.getNroLinea(),analizador.getMensaje(18),"SINTACTICO");}
	 | IMPRIMIR error STRING ')' ';' {manejador.error(analizador.getNroLinea(), analizador.getMensaje(58),"SINTACTICO");}
	 | IMPRIMIR '(' STRING ';' {manejador.error(analizador.getNroLinea(), analizador.getMensaje(59),"SINTACTICO");}
;

expresion : expresion '+' termino	{ ArbolSintactico a1 = ((ArbolSintactico)$1.obj);
										ArbolSintactico a2 = ((ArbolSintactico)$3.obj);
										$$.obj = new ArbolSintactico ("+",a1,a2);
									}
		  | expresion '-' termino	{ ArbolSintactico a1 = ((ArbolSintactico)$1.obj);
										ArbolSintactico a2 = ((ArbolSintactico)$3.obj);
										$$.obj = new ArbolSintactico ("-",a1,a2);
									}
		  | termino {$$.obj = ((ArbolSintactico)$1.obj);} 
;

termino : termino '*' factor	{ ArbolSintactico a1 = ((ArbolSintactico)$1.obj);
								  ArbolSintactico a2 = ((ArbolSintactico)$3.obj);
								  $$.obj = new ArbolSintactico ("*",a1,a2);
								}
		| termino '/' factor	{ ArbolSintactico a1 = ((ArbolSintactico)$1.obj);
								  ArbolSintactico a2 = ((ArbolSintactico)$3.obj);
								  $$.obj = new ArbolSintactico ("/",a1,a2);
								}
		| factor {$$.obj = ((ArbolSintactico)$1.obj);}
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
							$$.obj = new Hoja(tabla.getTabla().get(nuevoLexema),nuevoLexema);
						}
	   | CONSTANTE { String lexema = ((Token)$1.obj).getLexema();
						$$.obj = new Hoja (tabla.getTabla().get(lexema),lexema);
					}
	   
	   | ID		{	String lexema = ((Token)$1.obj).getLexema();
					$$.obj = new Hoja (tabla.getTabla().get(lexema),lexema);
				}
	   | '-' CTEENTERO		{	String lexema = ((Token)$2.obj).getLexema();
							
						if(Long.parseLong(lexema)<= Short.MAX_VALUE+1 ) { //TODO
							EntradaTS entrada = (EntradaTS)tabla.getTabla().get(lexema);
							String nuevoLexema = "-"+lexema;
							if (entrada.getContRef() == 1){
								if (tabla.getTabla().contains(nuevoLexema))
									((EntradaTS)tabla.getTabla().get(nuevoLexema)).incrementarCont();
								else {	EntradaTS nuevaEntrada = new EntradaTS(CTEENTERO, nuevoLexema);
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
									EntradaTS nuevaEntrada = new EntradaTS(CTEENTERO, nuevoLexema);
									tabla.addETS(nuevaEntrada.getLexema(), nuevaEntrada);
									nuevaEntrada.setTipo("entero");
								}
							}
							$$.obj = new Hoja(tabla.getTabla().get(nuevoLexema),nuevoLexema);
						}
				   else {
					manejador.error(analizador.getNroLinea(), analizador.getMensaje(20), "LEXICO"); 
					}
				}
	   | CTEENTERO {	
						String lexema = ((Token)$1.obj).getLexema();

							if(Long.parseLong(lexema)<= Short.MAX_VALUE ) { //TODO
				
							$$.obj = new Hoja(tabla.getTabla().get(lexema),lexema);
							}
							else {
							manejador.error(analizador.getNroLinea(), analizador.getMensaje(20), "LEXICO"); 
							}
					}					
	   | expresion_vector {$$.obj = ((ArbolSintactico)$1.obj);}
;

expresion_vector : ID '[' expresion ']' {	ArbolSintactico id = ((ArbolSintactico)$1.obj);
											ArbolSintactico exp = ((ArbolSintactico)$3.obj);
											$$.obj = new ArbolSintactico ("asig vector" , id , exp);
										}
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
Vector<Token> vt = new Vector<Token>() ;
ArbolSintactico arbol = new ArbolSintactico (); ;
 
public void setLexico(AnalizadorLexico al) {
	analizador = al;
	tabla = al.getTablaDeSimbolos();
}

public void setMensajes(Mensajes ms) {
	manejador = ms;
}

public void setArbol ( ArbolSintactico a ){
	arbol = a ;
}

public ArbolSintactico getArbol (){
	return arbol ;
}

int yylex()
{
	int val = analizador.yylex();
	yylval = new ParserVal(analizador.getToken());
	yylval.ival = analizador.getNroLinea();
	
	return val;
}