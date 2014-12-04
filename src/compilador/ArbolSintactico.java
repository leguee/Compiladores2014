package compilador;

import java.util.Vector;


public class ArbolSintactico {

	private EntradaTS entrada ;
	
	
	public static final short AUX=275;
	private String tipo = "NADA" ;
	private String valor ;

	private ArbolSintactico hijoDer ;
	private ArbolSintactico hijoIzq ;

//	private Hoja hijoDerHoja ;
//	private Hoja hijoIzqHoja ;
	
	static ArbolSintactico puntAnterior = null ;
	ArbolSintactico pAnt = null ;
	static char ultimaVisita ;
	char uVis ;

	private static boolean error = false ;

	public ArbolSintactico (){

	}
	
	public static void resetError(){
		error = false;
	}
	public ArbolSintactico (EntradaTS e ,String valor){
		this.entrada = e ;
		this.valor = valor ;
		this.hijoDer = null ;
	//	this.hijoDerHoja = null ;
		this.hijoIzq = null ;
	//	this.hijoIzqHoja = null ;
	}

	public ArbolSintactico (String valor , ArbolSintactico i , ArbolSintactico d){
		this.valor = valor ;
		hijoIzq = i ;
		hijoDer = d ;
	//	hijoDerHoja = null ;
	//	hijoIzqHoja = null ;
	}

	public void setHijoDer (ArbolSintactico a){
		hijoDer = a ;
	}

	public void setHijoIzq (ArbolSintactico a){
		hijoIzq = a ;
	}

	public ArbolSintactico getHijoDer (){
		return hijoDer ;
	}

	public ArbolSintactico getHijoIzq () {
		return hijoIzq ;
	}

	public void setValor (String v){
		valor = v ;
	}

	public String getValor (){
		return valor ;
	} 

//	public void setHijoDerHoja (Hoja d){
//		hijoDerHoja = d ;
//	}
//
//	public Hoja getHijoDerHoja (){
//		return hijoDerHoja ;
//	}
//
//	public void setHijoIzqHoja (Hoja i){
//		hijoIzqHoja = i ;
//	}
//
//	public Hoja getHijoIzqHoja (){
//		return hijoIzqHoja ;
//	}

	public boolean esHoja (){
		return false ;
	}

	public boolean esNodo (){
		return true ;
	}

	public void imprimir (int nivel){
		int a = 0 ;
		if (this != null){

			while (a<nivel){
				CompiladorGUI.imprimirArbol("           ", false);
				a++;
			}
			CompiladorGUI.imprimirArbol(valor, true);
			nivel++;

			if (this.esNodo()){
				if(this.hijoIzq!=null)
					this.hijoIzq.imprimir(nivel);
				if(this.hijoDer!=null)
					this.hijoDer.imprimir(nivel);
			}
//			else{
//				if (this.hijoIzqHoja!=null)
//					this.hijoIzqHoja.imprimir(nivel);
//				if (this.hijoDerHoja!= null)
//					this.hijoDerHoja.imprimir(nivel);
//			}
		}
	}


	public static void setError (){
		error = true ;
	}

	public static boolean tieneError (){
		return error ;
	}

	public void setTipo (String t){
		tipo = t ;
	}

	public String getTipo (){
		return tipo ;
	}

	public void generarAssembler(TablaSimbolos ts, Sentencia sentencias , ArbolSintactico puntAnterior) {
		
		this.pAnt = puntAnterior ;
		this.uVis = ultimaVisita ;
		
		if (valor.equals("iterar")){ // 
			String label = sentencias.apilarEtiqueta();
			sentencias.agregarEtiqueta(label+":");
		}
		
		//Se recorre el arbol in orden
		if ((this.hijoIzq != null)&&(!this.hijoIzq.esHoja())) {
			puntAnterior = this;
			ultimaVisita = 'i';
			hijoIzq.generarAssembler(ts, sentencias,puntAnterior);
			
		}
		//nodo del medio //////////////////////////////////////////////////////////////////////////////

		//Si, seleccion
		if (valor.equals("si")){

			if ( hijoIzq != null){

				String comparador = this.hijoIzq.getValor(); // me va a devolver si es:  >=     <=		>		<		=		^=
				String varComp1 = hijoIzq.getHijoIzq().getEntrada().getLexAss(); 
				String varComp2 = hijoIzq.getHijoDer().getEntrada().getLexAss(); 
				if (hijoIzq.getTipo().equals("doble")){
					sentencias.add("FLD "+varComp1);
					sentencias.add("FLD "+varComp2);
					sentencias.add("FCOMPP");
					sentencias.add("FSTSW AX");
					sentencias.add("SAHF");
					String etiqueta = sentencias.apilarEtiqueta(); // no me importa el tipo porque con las instrucciones FSTSW FWAIT y SAHF se oculta y se procesa como si fuera entero
					String salto = getSalto(comparador); // si es JE, JB, JNE etc
					sentencias.add(salto + etiqueta);
				}
				else{

					sentencias.add("MOV AX, "+varComp1);
					sentencias.add("CMP AX , "+varComp2);
					String etiqueta = sentencias.apilarEtiqueta(); // no me importa el tipo porque con las instrucciones FSTSW FWAIT y SAHF se oculta y se procesa como si fuera entero
					String salto = getSalto(comparador); // si es JE, JB, JNE etc
					sentencias.add(salto + etiqueta);
				}
			}
		}

		// bloque o Cuerpo sino
		if (valor.equals("bloque")){
			String etiquetaPrimera = sentencias.desapilarEtiqueta();

			if (hijoDer != null){
				String etiqueta = sentencias.apilarEtiqueta();
				//Se agrega la sentencia
				sentencias.add("JMP "+ etiqueta);

			}
			//Primer Label
			sentencias.agregarEtiqueta(etiquetaPrimera+":");
		}

		//IZQUIERDA #################################################################################
		
		

		//Se recorre el arbol
		if ((this.hijoDer != null)&&(!this.hijoDer.esHoja())) {
			ultimaVisita = 'd' ;
			puntAnterior = this ;
			System.out.println ("entro a "+hijoDer.getValor());
			this.hijoDer.generarAssembler(ts,sentencias,puntAnterior);
			System.out.println ("salio de "+hijoDer.getValor());
			
		}


		//Nodos que ignoro
		if (valor.equals("program")){
			return;
		}
		if (valor.equals("sentencias")){
			return;
		}

		if (valor.equals("declaraciones")){
			return;
		}

		if (valor.equals("declaracion")){
			return;
		}


		//bloque 
		if (valor.equals("bloque")){
			if (hijoDer!= null){
				String etiqueta = sentencias.desapilarEtiqueta();
				sentencias.agregarEtiqueta(etiqueta+":");
			}
			return;
		}


		//Iteración salida poner etiqueta
		if (valor.equals("iterar")){

			String comparador = this.hijoDer.getValor(); // me va a devolver si es:  >=     <=		>		<		=		^=
			String varComp1 = hijoDer.getHijoIzq().getEntrada().getLexAss(); 
			String varComp2 = hijoDer.getHijoDer().getEntrada().getLexAss();
			if (hijoDer.getTipo().equals("doble")){
				sentencias.add("FLD "+varComp1);
				sentencias.add("FLD "+varComp2);
				sentencias.add("FCOMPP");
				sentencias.add("FSTSW AX");
				sentencias.add("SAHF");
				String etiqueta = sentencias.desapilarEtiqueta(); // no me importa el tipo porque con las instrucciones FSTSW FWAIT y SAHF se oculta y se procesa como si fuera entero
				String salto = getSalto(comparador); // si es JE, JB, JNE etc
				sentencias.add(salto + etiqueta);
			}
			else
			{
				sentencias.add("MOV AX, "+varComp1);
				sentencias.add("CMP AX , "+varComp2);
				String etiqueta = sentencias.desapilarEtiqueta(); // no me importa el tipo porque con las instrucciones FSTSW FWAIT y SAHF se oculta y se procesa como si fuera entero
				String salto = getSalto(comparador); // si es JE, JB, JNE etc
				sentencias.add(salto + etiqueta);
			}
			//Se agrega el salto
			/*String comparador = this.hijoDer.getValor();
			String etiqueta = sentencias.desapilarEtiqueta();
			String salto = getSalto(comparador); // si es JE, JB, JNE etc
			//Se agrega la sentencia
			sentencias.add(salto + etiqueta);*/
			return;
		}


		//Asignación
		if (valor.equals("asig") ){
//			mov ax, _@aux1
//			MOV _a , ax
			String dest = hijoIzq.getEntrada().getLexAss(); 
			String orig = hijoDer.getEntrada().getLexAss();
			if (this.tipo.equals("entero")) {
				sentencias.add("MOV AX ," + orig );
				sentencias.add("MOV "+dest+" , AX");
			}else if (this.tipo.equals("doble")) {
				sentencias.add("FLD "+ orig);
				sentencias.add("FSTP "+dest);
			}
			return;
		}
		
		
		if (valor.equals("asig a vector")){ 

			System.out.println("el tipo de .asig a vector,: del lado izq"+ ". entradaTS: "+ hijoIzq.getEntrada().getTipo());
			System.out.println("o de donde estoy parado : " + this.getTipo());
			if (hijoIzq.getEntrada().getTipo().equals("entero")){
				sentencias.add("MOV ["+hijoIzq.getEntrada().getNombVector()+" + "+ hijoIzq.getEntrada().getLexAss()+" ] , "+ hijoDer.getEntrada().getLexAss());
			}else if(hijoIzq.getEntrada().getTipo().equals("doble")){
				sentencias.add("FLD " + hijoDer.getEntrada().getLexAss() );
				sentencias.add("FSTP [ "+hijoIzq.getEntrada().getNombVector()+" + "+ hijoIzq.getEntrada().getLexAss()+" ] ");
			}
				return;
		}
	
			
		if (valor.equals("vector")){  //  siempre es entero porque devuelve un aux con el corrimiento 
			EntradaTS ent= new EntradaTS(AUX, "");
			ent.setLexema("aux"+ ent.getIdAux());
			ent.setTipo("entero");
			ent.setNombVector(hijoIzq.getEntrada().getLexAss());
			ent.setTipo(hijoIzq.getEntrada().getTipo());// seteo con el tipo del vector
			ts.addETS("aux"+ ent.getIdAux(), ent);
			sentencias.add("MOV "+ ent.getLexAss()+ " , "+ hijoDer.getEntrada().getLexAss());
			if (hijoIzq.getEntrada().getTipo().equals("entero")){ // VERIFICA LOS LIMITES
				sentencias.add("DIV " +hijoDer.getEntrada().getLexAss() + " , 2" );
			}else if (hijoIzq.getEntrada().getTipo().equals("doble")){
				sentencias.add("DIV " +hijoDer.getEntrada().getLexAss() + " , 6" );
			}
			sentencias.add("ADD "+hijoDer.getEntrada().getLexAss() +" , " +hijoIzq.getEntrada().getLexAss()+"LimInf" );
			sentencias.add("CMP "+hijoDer.getEntrada().getLexAss() + " , "+hijoIzq.getEntrada().getLexAss()+"LimSup");
			sentencias.add("JB VECTOR_FUERA_DE_RANGO");
			sentencias.add("CMP "+hijoIzq.getEntrada().getLexAss()+"LimInf" + " , "+hijoDer.getEntrada().getLexAss());
			sentencias.add("JB VECTOR_FUERA_DE_RANGO \n");
			if (uVis == 'd') {
				System.out.println (pAnt.getValor());
				String tipo = pAnt.getHijoDer().getTipo();
				ArbolSintactico nuevoNodo = new ArbolSintactico (ent,"@aux"+ent.getIdAux());
				pAnt.setHijoDer(nuevoNodo);
				pAnt.getHijoDer().setTipo(tipo);
				System.out.println (pAnt.getValor());
				pAnt.getHijoDer().setHijoDer(null);
				pAnt.getHijoDer().setHijoIzq(null);
			}
			else
			{
				System.out.println (pAnt.getValor());
				String tipo = pAnt.getHijoIzq().getTipo();
				ArbolSintactico nuevoNodo = new ArbolSintactico (ent,"@aux"+ent.getIdAux());
				pAnt.setHijoIzq(nuevoNodo);
				pAnt.getHijoIzq().setTipo(tipo);
				System.out.println (pAnt.getValor());
				pAnt.getHijoIzq().setHijoDer(null);
				pAnt.getHijoIzq().setHijoIzq(null);
			}
			
			
			return;
		}

		if (valor.equals("asig vector")){ // TODO siempre es entero porque es el indice 
			EntradaTS ent= new EntradaTS(AUX, "");
			ent.setLexema("aux"+ ent.getIdAux());
			ent.setTipo("entero");
			ts.addETS("aux"+ ent.getIdAux(), ent);	
			sentencias.add("MOV " +ent.getLexAss() +" , "+hijoDer.getEntrada().getLexAss()); // Creo una copia de Aux hijo derech
			//if( hijoIzq.getTipo().equals("entero")){
			if( hijoIzq.getEntrada().getTipo().equals("entero")){
				sentencias.add("DIV " +ent.getLexAss() + " , 2" );//  VERIFICO PRIMERO EL LIMITE DEL INDICE
				sentencias.add("ADD "+ent.getLexAss() +" , " +hijoIzq.getEntrada().getLexAss()+"LimInf" );
				sentencias.add("CMP "+ent.getLexAss() + " , "+hijoIzq.getEntrada().getLexAss()+"LimSup");
				sentencias.add("JB VECTOR_FUERA_DE_RANGO");
				sentencias.add("CMP "+hijoIzq.getEntrada().getLexAss()+"LimInf" + " , "+ent.getLexAss());
				sentencias.add("JB VECTOR_FUERA_DE_RANGO \n");
				sentencias.add("MOV "+ent.getLexAss()+", ["+hijoIzq.getEntrada().getLexAss() +" + "+ hijoDer.getEntrada().getLexAss()+" ]" );
				//System.out.println("MOV "+ent.getLexAss()+", ["+hijoIzq.getEntrada().getLexAss() +" + "+ hijoDer.getEntrada().getLexAss()+" ]" );
			}else if( hijoIzq.getEntrada().getTipo().equals("doble")){//if(hijoIzq.getTipo().equals("doble")){
				sentencias.add("DIV " +ent.getLexAss() + " , 6" );//  VERIFICO PRIMERO EL LIMITE DEL INDICE
				sentencias.add("ADD "+ent.getLexAss() +" , " +hijoIzq.getEntrada().getLexAss()+"LimInf" );
				sentencias.add("CMP "+ent.getLexAss() + " , "+hijoIzq.getEntrada().getLexAss()+"LimSup");
				sentencias.add("JB VECTOR_FUERA_DE_RANGO");
				sentencias.add("CMP "+hijoIzq.getEntrada().getLexAss()+"LimInf" + " , "+ent.getLexAss());
				sentencias.add("JB VECTOR_FUERA_DE_RANGO \n");
				sentencias.add("FLD "+" ["+hijoIzq.getEntrada().getLexAss() +" + "+ hijoDer.getEntrada().getLexAss()+" ]");
				sentencias.add("FSTP "+ent.getLexAss());
			}
			//  ESTO DA LO MISMO  System.out.println("el tipo de "+hijoIzq.getEntrada().getLexAss() + hijoIzq.getTipo() + "...o el tipo de la entradaTS: "+ hijoIzq.getEntrada().getTipo());
			if (uVis == 'd') {// TODO ver si esto es necesario
				System.out.println (pAnt.getValor());
				String tipo = pAnt.getHijoDer().getTipo();
				ArbolSintactico nuevoNodo = new ArbolSintactico (ent,"@aux"+ent.getIdAux());
				pAnt.setHijoDer(nuevoNodo);
				pAnt.getHijoDer().setTipo(tipo);
				System.out.println (pAnt.getValor());
				pAnt.getHijoDer().setHijoDer(null);
				pAnt.getHijoDer().setHijoIzq(null);
			}
			else
			{
				System.out.println (pAnt.getValor());
				String tipo = pAnt.getHijoIzq().getTipo();
				ArbolSintactico nuevoNodo = new ArbolSintactico (ent,"@aux"+ent.getIdAux());
				pAnt.setHijoIzq(nuevoNodo);
				pAnt.getHijoIzq().setTipo(tipo);
				System.out.println (pAnt.getValor());
				pAnt.getHijoIzq().setHijoDer(null);
				pAnt.getHijoIzq().setHijoIzq(null);
			}
			return;
		}
		
		
		//Comparación 
		/*if (valor.equals("=") || v alor.equals("<") || valor.equals("<=") || valor.equals(">") || valor.equals(">=") || valor.equals("^=")){



			return;
		}*/
		
		if (valor.equals("print")){
			System.out.println("el hijo izq del print tienen " + hijoIzq.getEntrada().getLexAss());
			 sentencias.add("invoke MessageBox, NULL, addr "+ hijoIzq.getEntrada().getLexAss()+", addr TITULO , MB_OK " );
		}



		


		//Operacion Aritmeticas---------------------------------------------------------------------------------



		if (valor.equals("+")){ /////////////////////////////////////////////////////////////SUMA/////////////////////////////////////////
			if (this.tipo.equals("entero") || hijoIzq.getValor().contains("&")) {
				EntradaTS ent= new EntradaTS(AUX, "");
				ent.setLexema("aux"+ ent.getIdAux());
				ent.setTipo("entero");
				
				ts.addETS("aux"+ ent.getIdAux(), ent);		

				if (hijoIzq.getValor().contains("&")){ // si es un &vector
					String izq = "0";
					sentencias.add("ADD " + hijoDer.getEntrada().getLexAss() +","+ izq );
					sentencias.add("MOV AX, "+ hijoDer.getEntrada().getLexAss());
					//System.out.println("asignacion del vector en la suma ...................................");
					//System.out.println ("ADD " + hijoDer.getEntrada().getLexAss() +","+ izq );
					sentencias.add("MOV "+ent.getLexAss()+", AX" );
					//System.out.println ("MOV "+ent.getLexAss()+", "+hijoDer.getEntrada().getLexAss());
					//System.out.println("asignacion del vector en la suma .....FIN FIN FIN..............................");
				} else {
					
//					mov ax, _entero4
//					ADD ax,_entero6
//					MOV _@aux1, ax
					sentencias.add("MOV AX ," + hijoIzq.getEntrada().getLexAss());
					sentencias.add("ADD AX ,"+ hijoDer.getEntrada().getLexAss() );
					sentencias.add("MOV "+ent.getLexAss()+", AX");

				}

				if (uVis == 'd') {
					System.out.println (pAnt.getValor());
					String tipo = pAnt.getHijoDer().getTipo();
					ArbolSintactico nuevoNodo = new ArbolSintactico (ent,"@aux"+ent.getIdAux());
					pAnt.setHijoDer(nuevoNodo);
					pAnt.getHijoDer().setTipo(tipo);
					System.out.println (pAnt.getValor());
					pAnt.getHijoDer().setHijoDer(null);
					pAnt.getHijoDer().setHijoIzq(null);
				}
				else
				{
					System.out.println (pAnt.getValor());
					String tipo = pAnt.getHijoIzq().getTipo();
					ArbolSintactico nuevoNodo = new ArbolSintactico (ent,"@aux"+ent.getIdAux());
					pAnt.setHijoIzq(nuevoNodo);
					pAnt.getHijoIzq().setTipo(tipo);
					System.out.println (pAnt.getValor());
					pAnt.getHijoIzq().setHijoDer(null);
					pAnt.getHijoIzq().setHijoIzq(null);
				}

			}else  {
				EntradaTS ent= new EntradaTS(AUX, "");
				ent.setLexema("aux"+ ent.getIdAux());
				ent.setTipo("doble");
				ts.addETS("aux"+ent.getIdAux(), ent);
				sentencias.add("FLD " + hijoDer.getEntrada().getLexAss());
				sentencias.add("FLD " +hijoIzq.getEntrada().getLexAss());
				sentencias.add("FADD ");
				sentencias.add("FSTP "+ ent.getLexAss() );

				if (uVis == 'd') {
					System.out.println (pAnt.getValor());
					String tipo = pAnt.getHijoDer().getTipo();
					ArbolSintactico nuevoNodo = new ArbolSintactico (ent,"@aux"+ent.getIdAux());
					pAnt.setHijoDer(nuevoNodo);
					pAnt.getHijoDer().setTipo(tipo);
					System.out.println (pAnt.getValor());
					pAnt.getHijoDer().setHijoDer(null);
					pAnt.getHijoDer().setHijoIzq(null);
				}
				else
				{
					System.out.println (pAnt.getValor());
					String tipo = pAnt.getHijoIzq().getTipo();
					ArbolSintactico nuevoNodo = new ArbolSintactico (ent,"@aux"+ent.getIdAux());
					pAnt.setHijoIzq(nuevoNodo);
					pAnt.getHijoIzq().setTipo(tipo);
					System.out.println (pAnt.getValor());
					pAnt.getHijoIzq().setHijoDer(null);
					pAnt.getHijoIzq().setHijoIzq(null);
				}			
			}

		}else if (valor.equals("-")){/////////////////////////////////////////////////////////////RESTA/////////////////////////////////////////
			if (this.tipo.equals("entero") || hijoDer.getEntrada() == null ) {
				EntradaTS ent= new EntradaTS(AUX, "");
				ent.setLexema("aux"+ ent.getIdAux());
				ent.setTipo("entero");
				ts.addETS("aux"+ ent.getIdAux(), ent);
				if (hijoDer.getEntrada() == null){ // es porque es el LimInf del vector en la resta de indices 
					sentencias.add("MOV AX ," + hijoIzq.getEntrada().getLexAss());
					sentencias.add("SUB AX, "+ hijoDer.getValor() );
					System.out.println("el hijo derecho es null tengo que acceder por el getValor porque no tiene EntradaTs ");
				}else {
					sentencias.add("MOV AX ," + hijoIzq.getEntrada().getLexAss());
					sentencias.add("SUB AX ,"+ hijoDer.getEntrada().getLexAss() );
				}	
				sentencias.add("MOV "+ent.getLexAss()+", AX");
				if (uVis == 'd') {
					System.out.println (pAnt.getValor());
					String tipo = pAnt.getHijoDer().getTipo();
					ArbolSintactico nuevoNodo = new ArbolSintactico (ent,"@aux"+ent.getIdAux());
					pAnt.setHijoDer(nuevoNodo);
					pAnt.getHijoDer().setTipo(tipo);
					System.out.println (pAnt.getValor());
					pAnt.getHijoDer().setHijoDer(null);
					pAnt.getHijoDer().setHijoIzq(null);
				}
				else
				{
					System.out.println (pAnt.getValor());
					String tipo = pAnt.getHijoIzq().getTipo();
					ArbolSintactico nuevoNodo = new ArbolSintactico (ent,"@aux"+ent.getIdAux());
					pAnt.setHijoIzq(nuevoNodo);
					pAnt.getHijoIzq().setTipo(tipo);
					System.out.println (pAnt.getValor());
					pAnt.getHijoIzq().setHijoDer(null);
					pAnt.getHijoIzq().setHijoIzq(null);
				}

			}else  {
				EntradaTS ent= new EntradaTS(AUX, "");
				ent.setLexema("aux"+ ent.getIdAux());
				ent.setTipo("doble");
				ts.addETS("aux"+ent.getIdAux(), ent);
				sentencias.add("FLD " + hijoDer.getEntrada().getLexAss());
				sentencias.add("FLD " +hijoIzq.getEntrada().getLexAss());
				sentencias.add("FSUBR ");
				sentencias.add("FSTP "+ ent.getLexAss() );


				if (uVis == 'd') {
					System.out.println (pAnt.getValor());
					String tipo = pAnt.getHijoDer().getTipo();
					ArbolSintactico nuevoNodo = new ArbolSintactico (ent,"@aux"+ent.getIdAux());
					pAnt.setHijoDer(nuevoNodo);
					pAnt.getHijoDer().setTipo(tipo);
					System.out.println (pAnt.getValor());
					pAnt.getHijoDer().setHijoDer(null);
					pAnt.getHijoDer().setHijoIzq(null);
				}
				else
				{
					System.out.println (pAnt.getValor());
					String tipo = pAnt.getHijoIzq().getTipo();
					ArbolSintactico nuevoNodo = new ArbolSintactico (ent,"@aux"+ent.getIdAux());
					pAnt.setHijoIzq(nuevoNodo);
					pAnt.getHijoIzq().setTipo(tipo);
					System.out.println (pAnt.getValor());
					pAnt.getHijoIzq().setHijoDer(null);
					pAnt.getHijoIzq().setHijoIzq(null);
				}			
			}
		}else if (valor.equals("*")){/////////////////////////////////////////////////////////////MULTIPLIC/////////////////////////////////////////
			//
//			if(hijoIzq.getEntrada() == null) {
//				System.out.println("esto pertenece al producto con el factor del vector");
//			}
//			else{
			if (this.tipo.equals("entero") || hijoIzq.getEntrada() == null) {
				EntradaTS ent= new EntradaTS(AUX, "");
				ent.setLexema("aux"+ ent.getIdAux());
				ent.setTipo("entero");
				ts.addETS("aux"+ ent.getIdAux(), ent);
				if(hijoIzq.getEntrada() == null) {
					System.out.println("esto pertenece al producto con el factor del vector");
					sentencias.add("MUL " + hijoDer.getEntrada().getLexAss()  +","+ hijoIzq.getValor() );

				}else{
					sentencias.add("MUL " + hijoDer.getEntrada().getLexAss() +","+ hijoIzq.getEntrada().getLexAss() );
					sentencias.add("CMP "+ hijoDer.getEntrada().getLexAss() + ", _@max_entero"); 
					sentencias.add("JB OVERFLOW_EN_PRODUCTO ");
				}
				sentencias.add("MOV "+ent.getLexAss()+", "+hijoDer.getEntrada().getLexAss());
				if (uVis == 'd') {
					System.out.println (pAnt.getValor());
					String tipo = pAnt.getHijoDer().getTipo();
					ArbolSintactico nuevoNodo = new ArbolSintactico (ent,"@aux"+ent.getIdAux());
					pAnt.setHijoDer(nuevoNodo);
					pAnt.getHijoDer().setTipo(tipo);
					System.out.println (pAnt.getValor());
					pAnt.getHijoDer().setHijoDer(null);
					pAnt.getHijoDer().setHijoIzq(null);
				}
				else
				{
					System.out.println (pAnt.getValor());
					String tipo = pAnt.getHijoIzq().getTipo();
					ArbolSintactico nuevoNodo = new ArbolSintactico (ent,"@aux"+ent.getIdAux());
					pAnt.setHijoIzq(nuevoNodo);
					pAnt.getHijoIzq().setTipo(tipo);
					System.out.println (pAnt.getValor());
					pAnt.getHijoIzq().setHijoDer(null);
					pAnt.getHijoIzq().setHijoIzq(null);
				}

			}else  {
				EntradaTS ent= new EntradaTS(AUX, "");
				ent.setLexema("aux"+ ent.getIdAux());
				ent.setTipo("doble");
				ts.addETS("aux"+ent.getIdAux(), ent);
				sentencias.add("FLD " + hijoDer.getEntrada().getLexAss());
				sentencias.add("FLD " +hijoIzq.getEntrada().getLexAss());
				sentencias.add("FMUL ");
				sentencias.add("FLD _@max_doble");
				sentencias.add("FCOMPP ");
				sentencias.add("FWAIT ");
				sentencias.add("SAHF ");
				sentencias.add("JB OVERFLOW_EN_PRODUCTO ");
				sentencias.add("FSTP "+ ent.getLexAss() );

				if (uVis == 'd') {
					System.out.println (pAnt.getValor());
					String tipo = pAnt.getHijoDer().getTipo();
					ArbolSintactico nuevoNodo = new ArbolSintactico (ent,"@aux"+ent.getIdAux());
					pAnt.setHijoDer(nuevoNodo);
					pAnt.getHijoDer().setTipo(tipo);
					System.out.println (pAnt.getValor());
					pAnt.getHijoDer().setHijoDer(null);
					pAnt.getHijoDer().setHijoIzq(null);
				}
				else
				{
					System.out.println (pAnt.getValor());
					String tipo = pAnt.getHijoIzq().getTipo();
					ArbolSintactico nuevoNodo = new ArbolSintactico (ent,"@aux"+ent.getIdAux());
					pAnt.setHijoIzq(nuevoNodo);
					pAnt.getHijoIzq().setTipo(tipo);
					System.out.println (pAnt.getValor());
					pAnt.getHijoIzq().setHijoDer(null);
					pAnt.getHijoIzq().setHijoIzq(null);
				}			
			}
		}else if (valor.equals("/")){  /////////////////////////////////////////////////////////////DIVISION/////////////////////////////////////////
			if (this.tipo.equals("entero")) {
				EntradaTS ent= new EntradaTS(AUX, "");
				ent.setLexema("aux"+ ent.getIdAux());
				ent.setTipo("entero");
				ts.addETS("aux"+ ent.getIdAux(), ent);				
				sentencias.add("DIV " + hijoIzq.getEntrada().getLexAss() +","+ hijoDer.getEntrada().getLexAss() );
				sentencias.add("MOV "+ent.getLexAss()+", "+hijoIzq.getEntrada().getLexAss());
				if (uVis == 'd') {
					System.out.println (pAnt.getValor());
					String tipo = pAnt.getHijoDer().getTipo();
					ArbolSintactico nuevoNodo = new ArbolSintactico (ent,"@aux"+ent.getIdAux());
					pAnt.setHijoDer(nuevoNodo);
					pAnt.getHijoDer().setTipo(tipo);
					System.out.println (pAnt.getValor());
					pAnt.getHijoDer().setHijoDer(null);
					pAnt.getHijoDer().setHijoIzq(null);
				}
				else
				{
					System.out.println (pAnt.getValor());
					String tipo = pAnt.getHijoIzq().getTipo();
					ArbolSintactico nuevoNodo = new ArbolSintactico (ent,"@aux"+ent.getIdAux());
					pAnt.setHijoIzq(nuevoNodo);
					pAnt.getHijoIzq().setTipo(tipo);
					System.out.println (pAnt.getValor());
					pAnt.getHijoIzq().setHijoDer(null);
					pAnt.getHijoIzq().setHijoIzq(null);
				}
			}else {
				EntradaTS ent= new EntradaTS(AUX, "");
				ent.setLexema("aux"+ ent.getIdAux());
				ent.setTipo("doble");
				ts.addETS("aux"+ent.getIdAux(), ent);
				sentencias.add("FLD " + hijoDer.getEntrada().getLexAss());
				sentencias.add("FLD " +hijoIzq.getEntrada().getLexAss());
				sentencias.add("FDIVR ");
				sentencias.add("FSTP "+ ent.getLexAss() );

				if (uVis == 'd') {
					System.out.println (pAnt.getValor());
					String tipo = pAnt.getHijoDer().getTipo();
					ArbolSintactico nuevoNodo = new ArbolSintactico (ent,"@aux"+ent.getIdAux());
					pAnt.setHijoDer(nuevoNodo);
					pAnt.getHijoDer().setTipo(tipo);
					System.out.println (pAnt.getValor());
					pAnt.getHijoDer().setHijoDer(null);
					pAnt.getHijoDer().setHijoIzq(null);
				}
				else
				{
					System.out.println (pAnt.getValor());
					String tipo = pAnt.getHijoIzq().getTipo();
					ArbolSintactico nuevoNodo = new ArbolSintactico (ent,"@aux"+ent.getIdAux());
					pAnt.setHijoIzq(nuevoNodo);
					pAnt.getHijoIzq().setTipo(tipo);
					System.out.println (pAnt.getValor());
					pAnt.getHijoIzq().setHijoDer(null);
					pAnt.getHijoIzq().setHijoIzq(null);
				}			}
		} else {
			return;
		}



		/* ---------------------------------------------------------------------------------- */

		return;
	}

	private String getSalto(String comparador) {
		if (comparador.equals(">")){
			//Saltas por Menor Igual
			return "JBE ";
		}
		if (comparador.equals("<")){
			//Saltas por Mayor Igual
			return "JAE ";
		}
		if (comparador.equals(">=")){
			//Saltas por Menor
			return "JB ";
		}
		if (comparador.equals("<=")){
			//Saltas por Mayor
			return "JA ";
		}
		if (comparador.equals("=")){
			//Saltas por Igual
			return "JNE ";	
		}
		if (comparador.equals("^=")){
			//Saltas por Distinto
			return "JE ";		
		}
		return null;
	}

	public EntradaTS getEntrada() {
		return entrada;
	}

	public void setEntrada(EntradaTS entrada) {
		this.entrada = entrada;
	}

	

}
