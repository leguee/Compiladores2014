package compilador;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Vector;

public class GeneradorAssembler {

	 public static final Short CONSTANTE = 265;
	 public static final Short CTEENTERO=274;
	 public static final Short STRING = 267; // cadena de caracteres
	public static final Short ID = 264;
	 
	 
	private ArbolSintactico arb;
	private TablaSimbolos ts;
	
	//private String codigoAsm = "";
	private Vector<String> codigoAssembler;
	//private Vector<String> declaracionesarb;

	// private static HashMap<String,String> mapStringsASM ;
	public GeneradorAssembler(ArbolSintactico arb, TablaSimbolos ts) {
		this.arb = arb;
		this.ts = ts;
		this.codigoAssembler = new Vector<String>();
		// this.declaracionesarb = new Vector<String>();
		// this.setMapStringsASM(new HashMap<String,String>());
	}

//////////////// Inicializacion de encabezado assembler	
	  private Vector<String> inicializacion() {
	        Vector<String> inicializacion = new Vector<String>();
	        inicializacion.add(new String(".386"));
	        inicializacion.add(new String(".model flat, stdcall"));
	        inicializacion.add(new String("option casemap :none"));
	        inicializacion.add(new String("include \\masm32\\include\\windows.inc"));
	        inicializacion.add(new String("include \\masm32\\include\\kernel32.inc"));
	        inicializacion.add(new String("include \\masm32\\include\\user32.inc"));
	        inicializacion.add(new String("includelib \\masm32\\lib\\kernel32.lib"));
	        inicializacion.add(new String("includelib \\masm32\\lib\\user32.lib"));

	        return inicializacion;
	    }
	    
////////////////Declaracion de variables
	  private Vector<String> declaracionDeVariables(){
		  Vector<String> variables = new Vector<String>();
		  variables.add(new String(".DATA"));
		  variables.add(new String("TITULO DB \"Mensaje\" , 0"));
		  variables.add(new String("OVERFLOW_EN_PRODUCTO_MENSAJE DB \"Overflow en producto\" , 0"));
		  variables.add(new String("VECTOR_FUERA_DE_RANGO_MENSAJE DB \"Vector fuera de rango\" , 0"));
		  variables.add(new String(""));
		  variables.add(new String("_@cero DQ 0.0"));
		  variables.add(new String("_@max_doble DQ 1.7976931348623156E308"));
		  variables.add(new String("_@max_entero DW 32767"));

		  Enumeration<EntradaTS> e = ts.getTabla().elements();

		  int cadena = 1;
		  while(e.hasMoreElements()){
			  EntradaTS entrada = (EntradaTS)e.nextElement();

			  if (entrada.getId().equals(CONSTANTE)){
				  variables.add(new String(entrada.getLexAss()+ " DQ " + entrada.getLexema())); //
			  }
			  else
				  if (entrada.getId().equals(CTEENTERO)){
					  variables.add(new String(entrada.getLexAss()+ " DW " + entrada.getLexema())); //
				  }
				  else	
					  if(entrada.getId().equals(ID) && entrada.getRangoMayor() == null){ // si es un ID y no es un vector 
						  if (entrada.getTipo().equals("doble"))
						  { 
							  variables.add(new String(entrada.getLexAss()+ " DQ ?"));
						  }
						  else if (entrada.getTipo().equals("entero"))
							  variables.add(new String(entrada.getLexAss() + " DW ?"));
					  }// TODO ver como inicializa los vectores y los manda a las declaracions
					  else if (entrada.getId().equals(STRING)){
						  entrada.setCadena(cadena);
						  cadena++;
						  variables.add(new String(entrada.getCadena() + " DB " + "\"" + entrada.getLexema() + "\"" + " ,0"));
					  }
		  }
		  return variables;
	  }

	  public Vector<String> getCodigoAssembler() {

		  // ----------------------- CÓDIGO .code
		  // ------------------------------------------------------
		  Vector<String> codigoAs = new Vector<String>();
		  codigoAs.add(new String(".CODE"));
		  codigoAs.add(new String("START:"));
		  System.out.println("Generar Assembler"); //

		  Sentencia sentencias = new Sentencia(); // es la clase que voy a usar para la recursion, y va a tener el codigo generado 
		  arb.generarAssembler(ts, sentencias);// despues de recorrer el arbol y ademas tiene una pila de labels
		  codigoAs = sentencias.getCodigo();

		  //--------------------------------------------------------

		  this.codigoAssembler = this.inicializacion();
		  this.codigoAssembler.addAll(this.declaracionDeVariables());
		  this.codigoAssembler.addAll(codigoAs);

		  codigoAssembler.add(new String("SALIR:"));
		  codigoAssembler.add(new String("invoke ExitProcess, 0"));

		  codigoAssembler.add(new String("OVERFLOW_EN_PRODUCTO:"));
		  codigoAssembler.add(new String("invoke MessageBox, NULL, addr OVERFLOW_EN_PRODUCTO_MENSAJE, addr TITULO , MB_OK " ));
		  codigoAssembler.add(new String("JMP SALIR"));

		  codigoAssembler.add(new String("VECTOR_FUERA_DE_RANGO:"));
		  codigoAssembler.add(new String("invoke MessageBox, NULL, addr VECTOR_FUERA_DE_RANGO_MENSAJE, addr TITULO , MB_OK " ));
		  codigoAssembler.add(new String("JMP SALIR"));

		  codigoAssembler.add(new String("END START"));

		  return this.codigoAssembler;

	  }

	


}
