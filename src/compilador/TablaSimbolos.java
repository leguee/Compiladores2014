package compilador;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public class TablaSimbolos {

    private Vector <String> palReservadas;
    private Hashtable <String,EntradaTS> lexemas;


    public TablaSimbolos(){
        palReservadas = new Vector<String>();
        lexemas = new Hashtable <String,EntradaTS>();
        palReservadas.addElement("si");
        palReservadas.addElement("entonces");
        palReservadas.addElement("sino");
        palReservadas.addElement("imprimir");
        palReservadas.addElement("doble");
        palReservadas.addElement("vector");
        palReservadas.addElement("de");
        palReservadas.addElement("itera");
        palReservadas.addElement("hasta");
        palReservadas.addElement("entero");
    }
    

    public Hashtable <String,EntradaTS> getTabla(){
        return this.lexemas;
    }

    public boolean contieneLexema(String lexema) {
        return this.lexemas.containsKey(lexema);
    }

    public EntradaTS getEntradaTS(String lexema) {
        return this.lexemas.get(lexema);
    }

    public boolean esPalabraReservada(String valor) {
        return this.palReservadas.contains(valor);
    }

    public void addETS(String lexema, EntradaTS entrada) {
        this.lexemas.put(lexema, entrada);
    }
    
    public void mostrarTabla(){
        Enumeration<String> e = lexemas.keys();
        while (e.hasMoreElements()){
            System.out.println((String)e.nextElement());
        }
    }
}
