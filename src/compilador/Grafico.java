package compilador;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.ScrollPane;
import java.awt.Toolkit;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;


public class Grafico extends JFrame {

	private static final long serialVersionUID = 1521094877914326241L;
	private JPanel contentPane ;
    private Vector<Coord> coords ;
    private Coord2 arbolCoord ;
    private int ancho ;
    private int alto ;
    
    /**
     * Create the frame.
     */
    public Grafico( int alto, int ancho , Vector<Coord> coords , Coord2 arbolCoord) {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);   
        contentPane = new JPanel ();
        //contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setBackground(new java.awt.Color(153,153,153));
        this.add(contentPane);
        //this.pack();
        contentPane.setLayout(null);
        this.ancho = ancho*100+100;
        this.setBounds(0,0,this.ancho,alto*100+100);
        this.alto = alto ;
        this.coords = coords ;
        this.arbolCoord = arbolCoord ;
    }
        
    private void llenarArbol (Coord2 arbolCoord, int xAnt){
    	    	
		arbolCoord.setPosxAnt(xAnt);

		if ( arbolCoord != null){
						
			if (arbolCoord.getC() != 'n'){
				if (arbolCoord.getC() == 'i'){
					int aux = xAnt-50*(alto-arbolCoord.getY());
					arbolCoord.setPosx(aux);

					if (arbolCoord.getIzq()!= null){
						llenarArbol(arbolCoord.getIzq(),aux);
					}
					if (arbolCoord.getDer() != null){
						llenarArbol(arbolCoord.getDer(),aux);
					}
				}
				else{
					int aux = xAnt+50*(alto-arbolCoord.getY());
					arbolCoord.setPosx(aux);
					
					if (arbolCoord.getIzq()!= null){
						llenarArbol(arbolCoord.getIzq(),aux);
					}
					if (arbolCoord.getDer() != null){
						llenarArbol(arbolCoord.getDer(),aux);
					}
				}
				
				
			}else{
				arbolCoord.setPosx(xAnt);
				if (arbolCoord.getIzq() != null){
					llenarArbol(arbolCoord.getIzq(),xAnt-150); // Cambiar el 150 por variable relativa a el alto
				}												// por ahi (50*alto-2) 
				if (arbolCoord.getDer() != null){				// altura 4, +50 es suficiente
					llenarArbol(arbolCoord.getDer(),xAnt+150);  // altura 5, se toca tmb, hay que estirar el 2do arco, no solo el primero
				}
			}
		}    	
    }
    
    private void imprimirArbol (Coord2 arbolCoord , Graphics g , int cont){
    	
    	if (arbolCoord != null){
    		    	
	        g.setColor (Color.blue);
	        int yAct = arbolCoord.getY()*100+100;
	        int yAnt = (arbolCoord.getY()-1)*100+100;
	        if (cont > 0)
	        	g.drawLine (arbolCoord.getPosxAnt(), yAnt, arbolCoord.getPosx(), yAct);
	        else{
	        	cont++ ;
	        }
    	
			if (arbolCoord.getIzq()!= null)
				imprimirArbol(arbolCoord.getIzq(), g, cont);
			if (arbolCoord.getDer()!= null)
				imprimirArbol(arbolCoord.getDer(), g, cont);
			
	        g.setColor (Color.blue);
			g.fillOval (arbolCoord.getPosx()-25, yAct-25, 50, 50);
	        g.setColor (Color.white);
	        g.drawString(arbolCoord.getValor(), arbolCoord.getPosx()-(arbolCoord.getValor().length()*9/2), yAct+g.getFontMetrics( g.getFont() ).getHeight()/2);
    
    	}
    }
    
    
    public void paint (Graphics g){
        super.paint(g);
        Font font = new Font( "Lucida Console", Font.BOLD, 14 );
        g.setFont( font );
        
        llenarArbol(arbolCoord,ancho/2);
        imprimirArbol(arbolCoord,g,0);
            
    }
    
}