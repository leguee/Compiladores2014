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
												
												
												if ( tabla.contieneLexema(token.getLexema())&&(!tabla.getEntradaTS(token.getLexema()).isDeclarada())){
													token.getETS().setTipo(lexema);
													token.getETS().setDeclarada();
													token.getETS().setId((short)264);
												}
												else
												{
												manejador.error(analizador.getNroLinea(),analizador.getMensaje(61),"SEMANTICO");
												ArbolSintactico.setError();
												}
											}
											vt = new Vector<Token>();
											ArbolSintactico a1 = ((ArbolSintactico)$1.obj);
											ArbolSintactico a2 = ((ArbolSintactico)$2.obj);
											ArbolSintactico a3 = new ArbolSintactico ("declaracion",a1,a2);
											a3.setTipo(lexema);
											$$.obj = a3 ;
											
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
																				
																				if ( tabla.contieneLexema(ident.getLexema())&&(!tabla.getEntradaTS(ident.getLexema()).isDeclarada())){
																					ident.getETS().setTipo(tipo.getValor());
																					ident.getETS().setId((short)264);
																					ident.getETS().setRangoMenor (rangoMenor);
																					ident.getETS().setRangoMayor (rangoMayor);
																					ident.getETS().setDeclarada();
																				}
																				else{
																					ArbolSintactico.setError();
																					manejador.error(analizador.getNroLinea(),analizador.getMensaje(61),"SEMANTICO");
																					
																				}
																				if (rangoMenor.compareTo(rangoMayor)== 1 ) {
																					arbol.setError();
																					manejador.error(analizador.getNroLinea(),analizador.getMensaje(69),"SEMANTICO");
																				}
																				if (rangoMenor.compareTo(((Integer)0).toString())== -1 ){
																					arbol.setError();
																					manejador.error(analizador.getNroLinea(),analizador.getMensaje(71),"SEMANTICO");
																				}
																				ArbolSintactico vector = new ArbolSintactico (iden, rango, tipo);
																				vector.setTipo (tipo.getValor());
																				$$.obj = vector ;
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
			           | sentencia 
					   
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
										String lexID = token1.getLexema();
										
										EntradaTS ET = tabla.getEntradaTS(lexID);
										if ( !tabla.contieneLexema(lexID) || !ET.isDeclarada() ){
											manejador.error(analizador.getNroLinea(),analizador.getMensaje (60 ) , "SEMANTICO");
											ArbolSintactico.setError();
											}
										if (ET.getRangoMenor()!=null){
											arbol.setError();
											manejador.error(analizador.getNroLinea(),analizador.getMensaje (70) , "SEMANTICO");
										}
										ArbolSintactico a1 = new Hoja(tabla.getTabla().get(token1.getLexema()),token1.getLexema());
										if ( tabla.getEntradaTS(a1.getValor()).isDeclarada()){
											a1.setTipo(tabla.getEntradaTS(a1.getValor()).getTipo());
										}
										ArbolSintactico a3 = ((ArbolSintactico)$3.obj);
										
										if ( !a1.getTipo().equals(a3.getTipo())){
											manejador.error(analizador.getNroLinea(),analizador.getMensaje (68 ) , "SEMANTICO");
											arbol.setError();
										}
										$$.obj = new ArbolSintactico ("asig", a1 , a3 );
										}
		  | ID ASIGNACION expresion error {manejador.error(analizador.getNroLinea(),analizador.getMensaje(7),"SINTACTICO");}
		  | ID ASIGNACION error ';' {manejador.error(analizador.getNroLinea(),analizador.getMensaje(55),"SINTACTICO");}
		  | error ASIGNACION expresion ';' {manejador.error(analizador.getNroLinea(),analizador.getMensaje(11),"SINTACTICO");}
		  | error ASIGNACION expresion  {manejador.error(analizador.getNroLinea(),analizador.getMensaje(12),"SINTACTICO");}
		  | ID error expresion ';'{manejador.error(analizador.getNroLinea(), analizador.getMensaje(54),"SINTACTICO");}
		  | ID '[' expresion ']' ASIGNACION expresion ';'	{	manejador.estructuraSintactica(analizador.getNroLinea(), analizador.getMensaje(43));
																String lexID = ((Token)$1.obj).getLexema();
																EntradaTS ET = tabla.getEntradaTS (lexID);
																if ( !tabla.contieneLexema(lexID) || !ET.isDeclarada() ){
																	manejador.error(analizador.getNroLinea(),analizador.getMensaje (60 ) , "SEMANTICO");
																	ArbolSintactico.setError();
																	
																	}
																if (tabla.contieneLexema(lexID) && ET.getRangoMenor() == null)
																		manejador.error(analizador.getNroLinea(),analizador.getMensaje (62 ) , "SEMANTICO");
																
																ArbolSintactico a1 = new Hoja (tabla.getTabla().get(lexID),lexID);
																if ( tabla.getEntradaTS(a1.getValor()).isDeclarada()){
																	a1.setTipo(tabla.getEntradaTS(a1.getValor()).getTipo());
																}
																ArbolSintactico expresionVector = ((ArbolSintactico)$3.obj);
																if (!expresionVector.getTipo().equals("entero")){
																	manejador.error(analizador.getNroLinea(), analizador.getMensaje(67), "LEXICO");
																	arbol.setError();	
																}
																
																ArbolSintactico base = new Hoja (tabla.getEntradaTS(lexID),"&"+lexID);
																ArbolSintactico rangoMenor = new Hoja (null,tabla.getEntradaTS(lexID).getRangoMenor());
																String factor ;
																if (tabla.getEntradaTS(lexID).getTipo().equals("entero"))
																	factor = "2";
																else
																	factor = "6";
																ArbolSintactico tipo = new Hoja (null,factor);
																
																ArbolSintactico resta = new ArbolSintactico("-",expresionVector,rangoMenor);
																ArbolSintactico mult = new ArbolSintactico ("*",tipo,resta);
																ArbolSintactico direccion = new ArbolSintactico ("+",base,mult);
																
																ArbolSintactico vec = new ArbolSintactico ("vector",a1,direccion);
																ArbolSintactico a6 = ((ArbolSintactico)$6.obj);
																if ( !a1.getTipo().equals(a6.getTipo())){
																	manejador.error(analizador.getNroLinea(),analizador.getMensaje (68 ) , "SEMANTICO");
																	arbol.setError();
																}
																
																$$.obj = new ArbolSintactico ("asig a vector", vec , a6 );
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
											bloque_si.setHijoDer(bloque_sino);
											//ArbolSintactico cuerpoSi = new ArbolSintactico ("cuerpo si" ,null , bloque_si);
											//ArbolSintactico cuerpoSino = new ArbolSintactico ("cuerpo sino" ,null , bloque_sino);
											//ArbolSintactico cuerpo = new ArbolSintactico("bloque" , cuerpoSi, cuerpoSino );
											//sel_simple.setHijoDer(cuerpo) ;
											$$.obj = sel_simple ;
										  }
;


seleccion_simple : SI '(' condicion ')' ENTONCES bloque	{	ArbolSintactico cn = ((ArbolSintactico)$3.obj);
															ArbolSintactico bl = ((ArbolSintactico)$6.obj);
															ArbolSintactico bloque = new ArbolSintactico ("bloque", bl , null);
															$$.obj = new ArbolSintactico ("si",cn,bloque);
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
												ArbolSintactico comp = new ArbolSintactico (lexema,a1,a2);
												if ( a1.getTipo().equals(a2.getTipo()))
													comp.setTipo(a1.getTipo());
												else{
													arbol.setError();
													manejador.error(analizador.getNroLinea(),analizador.getMensaje(72),"SEMANTICO");
												}
												
												$$.obj = comp ;
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
										ArbolSintactico res = new ArbolSintactico ("+",a1,a2);
								  if ( a1.getTipo().equals(a2.getTipo()))
								  	res.setTipo(a1.getTipo());
								  else{
								  	manejador.error(analizador.getNroLinea(),analizador.getMensaje(66),"SEMANTICO");
								  	arbol.setError();
								  	}
										$$.obj = res ;
									}
		  | expresion '-' termino	{ ArbolSintactico a1 = ((ArbolSintactico)$1.obj);
									ArbolSintactico a2 = ((ArbolSintactico)$3.obj);
									ArbolSintactico res = new ArbolSintactico ("-",a1,a2);
								  	if ( a1.getTipo().equals(a2.getTipo()))
								  		res.setTipo(a1.getTipo());
								  	else{
								  	manejador.error(analizador.getNroLinea(),analizador.getMensaje(65),"SEMANTICO");
								  	arbol.setError();
								  	}
										$$.obj = res ;
									}
		  | termino {$$.obj = ((ArbolSintactico)$1.obj);} 
;

termino : termino '*' factor	{ ArbolSintactico a1 = ((ArbolSintactico)$1.obj);
								  ArbolSintactico a2 = ((ArbolSintactico)$3.obj);
								  ArbolSintactico res = new ArbolSintactico ("*",a1,a2);
								  if ( a1.getTipo().equals(a2.getTipo()))
								  	res.setTipo(a1.getTipo());
								  else{
								  	manejador.error(analizador.getNroLinea(),analizador.getMensaje(63),"SEMANTICO");
								  	arbol.setError();
								  	}
								  $$.obj = res ;
								}
		| termino '/' factor	{ ArbolSintactico a1 = ((ArbolSintactico)$1.obj);
								  ArbolSintactico a2 = ((ArbolSintactico)$3.obj);
								  ArbolSintactico res = new ArbolSintactico ("/",a1,a2);
								  if ( a1.getTipo().equals(a2.getTipo()))
								  	res.setTipo(a1.getTipo());
								  else{
								  	manejador.error(analizador.getNroLinea(),analizador.getMensaje(64),"SEMANTICO");
								  	arbol.setError();
								  	}
								  $$.obj = res;
								}
		| factor {
					ArbolSintactico a1 = ((ArbolSintactico)$1.obj);
					String t = a1.getValor () ;
					EntradaTS ETs = tabla.getEntradaTS(t);
					try {
						if ((!tabla.contieneLexema(t) || 
								!ETs.isDeclarada())&& ETs.getId()==264){
							manejador.error(analizador.getNroLinea(),analizador.getMensaje (60 ) , "SEMANTICO");
							ArbolSintactico.setError();
							}
					} catch (NullPointerException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					yyval.obj = a1;
				  }
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
							ArbolSintactico a1 = new Hoja(tabla.getTabla().get(lexema),lexema);
							a1.setTipo ("doble"); 
							$$.obj = a1 ;
							
						}
	   | CONSTANTE { String lexema = ((Token)$1.obj).getLexema();
	   					ArbolSintactico a1 = new Hoja(tabla.getTabla().get(lexema),lexema);
							a1.setTipo ("doble"); 
							$$.obj = a1 ;
						
					}
	   
	   | ID		{	String lexema = ((Token)$1.obj).getLexema();
	   				ArbolSintactico a1 = new Hoja (tabla.getTabla().get(lexema),lexema);
	   				if ( tabla.getEntradaTS(lexema).isDeclarada())
	   					a1.setTipo(tabla.getEntradaTS(lexema).getTipo());
					$$.obj = a1 ;
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
							
							ArbolSintactico a1 = new Hoja(tabla.getTabla().get(lexema),lexema);
							a1.setTipo ("entero"); 
							$$.obj = a1 ;
							
						}
				   else {
					manejador.error(analizador.getNroLinea(), analizador.getMensaje(20), "LEXICO"); 
					}
				}
	   | CTEENTERO {	
						String lexema = ((Token)$1.obj).getLexema();

							if(Long.parseLong(lexema)<= Short.MAX_VALUE ) { //TODO
							ArbolSintactico a1 = new Hoja(tabla.getTabla().get(lexema),lexema);
							a1.setTipo ("entero"); 
							$$.obj = a1 ;
							}
							else {
								manejador.error(analizador.getNroLinea(), analizador.getMensaje(20), "LEXICO");
							}
					}					
	   | expresion_vector {/*$$.obj = ((ArbolSintactico)$1.obj);*/}
;


expresion_vector : ID '[' expresion ']' {	String lexema = ((Token)$1.obj).getLexema();
											ArbolSintactico id = new Hoja (tabla.getTabla().get(lexema),lexema);
											EntradaTS ET = tabla.getEntradaTS (lexema);
											id.setTipo (ET.getTipo());
											if ( !tabla.contieneLexema(lexema) || !ET.isDeclarada() ){
												manejador.error(analizador.getNroLinea(),analizador.getMensaje (60 ) , "SEMANTICO");
												ArbolSintactico.setError();
											}
											
											ArbolSintactico exp = ((ArbolSintactico)$3.obj);
											if (!exp.getTipo().equals("entero")){
												manejador.error(analizador.getNroLinea(), analizador.getMensaje(67), "LEXICO");
												arbol.setError();	
											}
											ArbolSintactico base = new Hoja (tabla.getEntradaTS(lexema),"&"+lexema);
											String factor ;
											if (id.getTipo().equals("entero")){
												factor = "2" ;
												}
											else{
												factor = "6";
												}
												
											ArbolSintactico tipo = new Hoja (null,factor);
											ArbolSintactico rango = new Hoja (null,ET.getRangoMenor());
											ArbolSintactico resta = new ArbolSintactico ("-",exp,rango);
											ArbolSintactico mult = new ArbolSintactico ("*",tipo,resta);
											ArbolSintactico direccion = new ArbolSintactico ("+",base,mult);
											ArbolSintactico asigVector = new ArbolSintactico ("asig vector" , id , direccion);
											asigVector.setTipo (id.getTipo());
											$$.obj = asigVector ;
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