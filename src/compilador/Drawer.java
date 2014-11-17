package compilador;

import java.awt.EventQueue;
import java.util.*;
import javax.swing.JScrollPane;

public class Drawer {
	
	public Drawer (){
		
	}
		
	private static int getAncho(ArbolSintactico a, Vector<Integer> aux) {
		
		int izq = 0 ;
		int der = 0 ;
		
		for ( Integer i : aux ){
			
			if (i < 0)
				if (i < izq)
					izq = i ;
			
			if (i > 0)
				if (i > der)
					der = i ;
		}
		return ( izq*(-1) + der + 1 );
	}
	
	

	private static void recorrerArbolAncho(ArbolSintactico a, Vector<Integer> aux, int contador) {
		
		aux.add(contador);
		
		
		if (a!=null)
			if(!a.esHoja()){
				if(a.getHijoIzq()!=null){
					contador--;
					recorrerArbolAncho(a.getHijoIzq(),aux,contador);
					contador++;
				}
				if(a.getHijoDer()!=null){
					contador++;
					recorrerArbolAncho(a.getHijoDer(),aux,contador);
					contador--;
	
				}
			}
	}
	
	private static int getAlto(ArbolSintactico a, Vector<Integer> aux) {
			
			int alto = 0 ;
			
			for ( Integer i : aux ){
				if (i > alto)
					alto = i ;
			}
			
			return alto+1;
		}
	
	private static void recorrerArbolAlto (ArbolSintactico a, Vector<Integer> aux, int contador) {
			
			aux.add(contador);
			System.out.println(contador);
			if (a!=null)
			if(!a.esHoja()){
				contador++;
				if(a.getHijoIzq()!=null){
					recorrerArbolAlto(a.getHijoIzq(),aux,contador);
				}
				if(a.getHijoDer()!=null){
					recorrerArbolAlto(a.getHijoDer(),aux,contador);
				}
			}
		}
	
	private static void recorrerArbolCoord(ArbolSintactico a, Vector<Coord> coords, int x, int y, int xAnt, int yAnt,char c) {
		
		coords.add(new Coord(x,y,xAnt,yAnt,a.getValor(),c));
		
		if(!a.esHoja()){
			
			int xMenos = x-1;
			int xMas = x+1;
			int yMas = y+1;
			
			
			if(a.getHijoIzq()!=null){
				if ( y == 0 )
					recorrerArbolCoord(a.getHijoIzq(),coords,xMenos,yMas,0,0,'i');
				else
					recorrerArbolCoord(a.getHijoIzq(),coords,xMenos,yMas,x,y,'i');
			}
			if(a.getHijoDer()!=null){
				if ( y == 0 )
					recorrerArbolCoord(a.getHijoDer(),coords,xMas,yMas,0,0,'d');
				else
					recorrerArbolCoord(a.getHijoDer(),coords,xMas,yMas,x,y,'d');
			}
			
		}
		
	}
	
	private static Coord2 recorrerArbolCoord2(ArbolSintactico a, char c,int y) {
		
		Coord2 izq = null, der = null ;
		int	yaux = y+1;
		
		if ( a != null){
			if ( !a.esHoja()){
				if (a.getHijoIzq()!= null)
					izq = recorrerArbolCoord2(a.getHijoIzq(),'i',yaux);
				if (a.getHijoDer()!= null)
					der = recorrerArbolCoord2(a.getHijoDer(),'d',yaux);
			}
		}
		
		return new Coord2 (izq,der,a.getValor(),c,y);
				
	}

	@SuppressWarnings("unused")
	private static void imprimirCoordenadas (Vector<Coord> coords){
		for ( Coord c : coords ){
			System.out.println(c.getValor()+" "+c.getY()+" "+c.getX());
		}
	}

	public static Grafico dibujarGrafo(ArbolSintactico a){
		
		Vector<Integer> aux = new Vector<Integer>();
		recorrerArbolAncho (a,aux,0);
		int ancho = getAncho(a,aux);
		System.out.println("Ancho: "+ancho);
		
		recorrerArbolAlto (a,aux,0);
		int alto = getAlto(a,aux);
		System.out.println("Alto: "+alto);
		
		Vector<Coord> coords = new Vector<Coord>();
		recorrerArbolCoord (a,coords,0,0,0,0,'n');
		Coord2 arbolCoord = recorrerArbolCoord2 (a,'n',0);
		
        Grafico frame = new Grafico(alto,(int) Math.pow(2, alto-1),coords,arbolCoord);
        frame.setVisible(true);
        //JScrollPane pane =new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        //pane.setVisible(true);
        
        return frame ;
	}


}
