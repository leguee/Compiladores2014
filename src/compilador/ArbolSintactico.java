package compilador;

import java.util.Vector;


public class ArbolSintactico {

	public static final short AUX=275;
	private String tipo = "NADA" ;
	private String valor ;

	private ArbolSintactico hijoDer ;
	private ArbolSintactico hijoIzq ;

	private Hoja hijoDerHoja ;
	private Hoja hijoIzqHoja ;
	ArbolSintactico puntAnterior = null ;
	private static char ultimaVisita ;

	private static boolean error = false ;

	public ArbolSintactico (){

	}

	public ArbolSintactico (String valor , ArbolSintactico i , ArbolSintactico d){
		this.valor = valor ;
		hijoIzq = i ;
		hijoDer = d ;
		hijoDerHoja = null ;
		hijoIzqHoja = null ;
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

	public void setHijoDerHoja (Hoja d){
		hijoDerHoja = d ;
	}

	public Hoja getHijoDerHoja (){
		return hijoDerHoja ;
	}

	public void setHijoIzqHoja (Hoja i){
		hijoIzqHoja = i ;
	}

	public Hoja getHijoIzqHoja (){
		return hijoIzqHoja ;
	}

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
				CompiladorGUI.imprimirArbol("   ", false);
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
			else{
				if (this.hijoIzqHoja!=null)
					this.hijoIzqHoja.imprimir(nivel);
				if (this.hijoDerHoja!= null)
					this.hijoDerHoja.imprimir(nivel);
			}
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

	public void generarAssembler(TablaSimbolos ts, Sentencia sentencias) {



		//Se recorre el arbol in orden
		if (this.hijoIzq != null) {
			puntAnterior = this ;
			ultimaVisita = 'i';
			hijoIzq.generarAssembler(ts, sentencias);
		}


		//nodo del medio //////////////////////////////////////////////////////////////////////////////

		//Si, seleccion
		if (valor.equals("si")){
			//Se generó ya la comparación !!!!!!!!!!!!!!!!!! TODO ver donde se generó en la recursion
			//Se debe poner el salto y apilarlo
			String comparador = this.hijoIzq .getValor(); // me va a devolver si es:  >=     <=		>		<		=		^=
			String etiqueta = sentencias.apilarEtiqueta(); // no me importa el tipo porque con las instrucciones FSTSW FWAIT y SAHF se oculta y se procesa como si fuera entero
			String salto = getSalto(comparador); // si es JE, JB, JNE etc
			sentencias.add(salto + etiqueta);
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
			sentencias.agregarEtiqueta(etiquetaPrimera);
		}

		//IZQUIERDA #################################################################################
		if (valor.equals("iterar")){ // TODO verificar que estos string sean correcto lo que se se setea en valor
			String label = sentencias.apilarEtiqueta();
			sentencias.agregarEtiqueta(label);
		}

		//Se recorre el arbol
		if (this.hijoDer!= null) {
			puntAnterior = this ;
			ultimaVisita = 'd' ;
			this.hijoDer.generarAssembler(ts,sentencias);
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
				sentencias.agregarEtiqueta(etiqueta);
			}
			return;
		}


		//Iteración salida poner etiqueta
		if (valor.equals("iterar")){
			//Se agrega el salto
			String comparador = this.hijoDer.getValor();
			String etiqueta = sentencias.desapilarEtiqueta();
			String salto = getSalto(comparador); // si es JE, JB, JNE etc
			//Se agrega la sentencia
			sentencias.add(salto + etiqueta);
			return;
		}


		//Asignación
		if (valor.equals("asig") ){// ||TODO valor.equals("asig vector")
			System.out.println("el tipo es ");
			System.out.println( );
			System.out.println("...............es hoja.......");

			String dest = hijoIzqHoja.getEntrada().getLexAss(); // aca se rompe
			String orig = hijoDerHoja.getEntrada().getLexAss(); 
			if (this.tipo.equals("entero")) {
				sentencias.add("MOV "+dest+" , "+ orig);
			}else if (this.tipo.equals("doble")) {
				sentencias.add("FLD "+ orig);
				sentencias.add("FSTP "+dest);
			}
			return;
		}


		//Comparación 
		/*if (valor.equals("=") || v alor.equals("<") || valor.equals("<=") || valor.equals(">") || valor.equals(">=") || valor.equals("^=")){



			return;
		}*/


		if (valor.equals("indice")){ // tratar el indice
			return;
		}


		//Operacion Aritmeticas---------------------------------------------------------------------------------



		if (valor.equals("+")){ /////////////////////////////////////////////////////////////SUMA/////////////////////////////////////////
			if (this.tipo.equals("entero")) {
				EntradaTS ent= new EntradaTS(AUX, "");
				ent.setLexema("aux"+ ent.getIdAux());
				ent.setTipo("entero");
				ts.addETS("aux"+ ent.getIdAux(), ent);				
				sentencias.add("ADD " + hijoIzqHoja.getEntrada().getLexAss() +","+ hijoDerHoja.getEntrada().getLexAss() );
				sentencias.add("MOV "+ent.getLexAss()+", "+hijoIzqHoja.getEntrada().getLexAss());
				if (ultimaVisita == 'd') {
					puntAnterior.setHijoDerHoja(new Hoja (ent,"@aux"+ent.getIdAux()));
					puntAnterior.getHijoDerHoja().setTipo(puntAnterior.getHijoDer().getTipo());
					puntAnterior.setHijoDer(null);
				}
				else
				{
					puntAnterior.setHijoIzq(new Hoja (ent,"@aux"+ ent.getIdAux()));
					puntAnterior.getHijoIzqHoja().setTipo(puntAnterior.getHijoIzq().getTipo());
					puntAnterior.setHijoIzq(null);
				}

			}else if (this.tipo.equals("doble")) {
				EntradaTS ent= new EntradaTS(AUX, "");
				ent.setLexema("aux"+ ent.getIdAux());
				ent.setTipo("doble");
				ts.addETS("aux"+ent.getIdAux(), ent);
				sentencias.add("FLD " + hijoDerHoja.getEntrada().getLexAss());
				sentencias.add("FLD " +hijoIzqHoja.getEntrada().getLexAss());
				sentencias.add("FADD ");
				sentencias.add("FSTP "+ ent.getLexAss() );
				sentencias.add("FLD "+  ent.getLexAss() );
				sentencias.add("FSTP "+hijoIzqHoja.getEntrada().getLexAss());

				if (ultimaVisita == 'd') {
					puntAnterior.setHijoDerHoja(new Hoja (ent,"@aux"+ent.getIdAux()));
					puntAnterior.getHijoDerHoja().setTipo(puntAnterior.getHijoDer().getTipo());
					puntAnterior.setHijoDer(null);
				}
				else
				{
					puntAnterior.setHijoIzq(new Hoja (ent,"@aux"+ ent.getIdAux()));
					puntAnterior.getHijoIzqHoja().setTipo(puntAnterior.getHijoIzq().getTipo());
					puntAnterior.setHijoIzq(null);
				}			
			}

		}else if (valor.equals("-")){/////////////////////////////////////////////////////////////RESTA/////////////////////////////////////////
			if (this.tipo.equals("entero")) {
				EntradaTS ent= new EntradaTS(AUX, "");
				ent.setLexema("aux"+ ent.getIdAux());
				ent.setTipo("entero");
				ts.addETS("aux"+ ent.getIdAux(), ent);				
				sentencias.add("SUB " + hijoIzqHoja.getEntrada().getLexAss() +","+ hijoDerHoja.getEntrada().getLexAss() );
				sentencias.add("MOV "+ent.getLexAss()+", "+hijoIzqHoja.getEntrada().getLexAss());
				if (ultimaVisita == 'd') {
					puntAnterior.setHijoDerHoja(new Hoja (ent,"@aux"+ent.getIdAux()));
					puntAnterior.getHijoDerHoja().setTipo(puntAnterior.getHijoDer().getTipo());
					puntAnterior.setHijoDer(null);
				}
				else
				{
					puntAnterior.setHijoIzq(new Hoja (ent,"@aux"+ ent.getIdAux()));
					puntAnterior.getHijoIzqHoja().setTipo(puntAnterior.getHijoIzq().getTipo());
					puntAnterior.setHijoIzq(null);
				}

			}else if (this.tipo.equals("doble")) {
				EntradaTS ent= new EntradaTS(AUX, "");
				ent.setLexema("aux"+ ent.getIdAux());
				ent.setTipo("doble");
				ts.addETS("aux"+ent.getIdAux(), ent);
				sentencias.add("FLD " + hijoDerHoja.getEntrada().getLexAss());
				sentencias.add("FLD " +hijoIzqHoja.getEntrada().getLexAss());
				sentencias.add("FSUBR ");
				sentencias.add("FSTP "+ ent.getLexAss() );
				sentencias.add("FLD "+  ent.getLexAss() );
				sentencias.add("FSTP "+hijoIzqHoja.getEntrada().getLexAss());

				if (ultimaVisita == 'd') {
					puntAnterior.setHijoDerHoja(new Hoja (ent,"@aux"+ent.getIdAux()));
					puntAnterior.getHijoDerHoja().setTipo(puntAnterior.getHijoDer().getTipo());
					puntAnterior.setHijoDer(null);
				}
				else
				{
					puntAnterior.setHijoIzq(new Hoja (ent,"@aux"+ ent.getIdAux()));
					puntAnterior.getHijoIzqHoja().setTipo(puntAnterior.getHijoIzq().getTipo());
					puntAnterior.setHijoIzq(null);
				}			
			}
		}else if (valor.equals("*")){/////////////////////////////////////////////////////////////MULTIPLIC/////////////////////////////////////////
			if (this.tipo.equals("entero")) {
				EntradaTS ent= new EntradaTS(AUX, "");
				ent.setLexema("aux"+ ent.getIdAux());
				ent.setTipo("entero");
				ts.addETS("aux"+ ent.getIdAux(), ent);				
				sentencias.add("MUL " + hijoIzqHoja.getEntrada().getLexAss() +","+ hijoDerHoja.getEntrada().getLexAss() );
				sentencias.add("CMP "+ hijoIzqHoja.getEntrada().getLexAss() + ", _@max_entero"); 
				sentencias.add("JB OVERFLOW_EN_PRODUCTO ");
				sentencias.add("MOV "+ent.getLexAss()+", "+hijoIzqHoja.getEntrada().getLexAss());
				if (ultimaVisita == 'd') {
					puntAnterior.setHijoDerHoja(new Hoja (ent,"@aux"+ent.getIdAux()));
					puntAnterior.getHijoDerHoja().setTipo(puntAnterior.getHijoDer().getTipo());
					puntAnterior.setHijoDer(null);
				}
				else
				{
					puntAnterior.setHijoIzq(new Hoja (ent,"@aux"+ ent.getIdAux()));
					puntAnterior.getHijoIzqHoja().setTipo(puntAnterior.getHijoIzq().getTipo());
					puntAnterior.setHijoIzq(null);
				}

			}else if (this.tipo.equals("doble")) {
				EntradaTS ent= new EntradaTS(AUX, "");
				ent.setLexema("aux"+ ent.getIdAux());
				ent.setTipo("doble");
				ts.addETS("aux"+ent.getIdAux(), ent);
				sentencias.add("FLD " + hijoDerHoja.getEntrada().getLexAss());
				sentencias.add("FLD " +hijoIzqHoja.getEntrada().getLexAss());
				sentencias.add("FMUL ");
				sentencias.add("FLD _@max_doble");
				sentencias.add("FCOMPP ");
				sentencias.add("FWAIT ");
				sentencias.add("SAHF ");
				sentencias.add("JB OVERFLOW_EN_PRODUCTO ");
				sentencias.add("FSTP "+ ent.getLexAss() );
				sentencias.add("FLD "+  ent.getLexAss() );
				sentencias.add("FSTP "+hijoIzqHoja.getEntrada().getLexAss());

				if (ultimaVisita == 'd') {
					puntAnterior.setHijoDerHoja(new Hoja (ent,"@aux"+ent.getIdAux()));
					puntAnterior.getHijoDerHoja().setTipo(puntAnterior.getHijoDer().getTipo());
					puntAnterior.setHijoDer(null);
				}
				else
				{
					puntAnterior.setHijoIzq(new Hoja (ent,"@aux"+ ent.getIdAux()));
					puntAnterior.getHijoIzqHoja().setTipo(puntAnterior.getHijoIzq().getTipo());
					puntAnterior.setHijoIzq(null);
				}			
			}
		}else if (valor.equals("/")){  /////////////////////////////////////////////////////////////DIVISION/////////////////////////////////////////
			if (this.tipo.equals("entero")) {
				EntradaTS ent= new EntradaTS(AUX, "");
				ent.setLexema("aux"+ ent.getIdAux());
				ent.setTipo("entero");
				ts.addETS("aux"+ ent.getIdAux(), ent);				
				sentencias.add("DIV " + hijoIzqHoja.getEntrada().getLexAss() +","+ hijoDerHoja.getEntrada().getLexAss() );
				sentencias.add("MOV "+ent.getLexAss()+", "+hijoIzqHoja.getEntrada().getLexAss());
				if (ultimaVisita == 'd') {
					puntAnterior.setHijoDerHoja(new Hoja (ent,"@aux"+ent.getIdAux()));
					puntAnterior.getHijoDerHoja().setTipo(puntAnterior.getHijoDer().getTipo());
					puntAnterior.setHijoDer(null);
				}
				else
				{
					puntAnterior.setHijoIzq(new Hoja (ent,"@aux"+ ent.getIdAux()));
					puntAnterior.getHijoIzqHoja().setTipo(puntAnterior.getHijoIzq().getTipo());
					puntAnterior.setHijoIzq(null);
				}

			}else if (this.tipo.equals("doble")) {
				EntradaTS ent= new EntradaTS(AUX, "");
				ent.setLexema("aux"+ ent.getIdAux());
				ent.setTipo("doble");
				ts.addETS("aux"+ent.getIdAux(), ent);
				sentencias.add("FLD " + hijoDerHoja.getEntrada().getLexAss());
				sentencias.add("FLD " +hijoIzqHoja.getEntrada().getLexAss());
				sentencias.add("FDIVR ");
				sentencias.add("FSTP "+ ent.getLexAss() );
				sentencias.add("FLD "+  ent.getLexAss() );
				sentencias.add("FSTP "+hijoIzqHoja.getEntrada().getLexAss());

				if (ultimaVisita == 'd') {
					puntAnterior.setHijoDerHoja(new Hoja (ent,"@aux"+ent.getIdAux()));
					puntAnterior.getHijoDerHoja().setTipo(puntAnterior.getHijoDer().getTipo());
					puntAnterior.setHijoDer(null);
				}
				else
				{
					puntAnterior.setHijoIzq(new Hoja (ent,"@aux"+ ent.getIdAux()));
					puntAnterior.getHijoIzqHoja().setTipo(puntAnterior.getHijoIzq().getTipo());
					puntAnterior.setHijoIzq(null);
				}			
			}
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



}
