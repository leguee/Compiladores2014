package compilador;

public class Coord2 {
	
	private Coord2 izq, der ;
	private String valor ;
	private char c ;
	private int posxAnt ;
	private int posx;
	private int y;
	
	public Coord2(Coord2 izq, Coord2 der, String valor, char c, int y) {
		super();
		this.izq = izq ;
		this.der = der ;
		this.valor = valor;
		this.c = c ;
		this.y = y ;
	}

	public Coord2 getIzq() {
		return izq;
	}

	public void setIzq(Coord2 izq) {
		this.izq = izq;
	}

	public Coord2 getDer() {
		return der;
	}

	public void setDer(Coord2 der) {
		this.der = der;
	}

	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public char getC() {
		return c;
	}
	public void setC(char c) {
		this.c = c;
	}
	public int getPosxAnt() {
		return posxAnt;
	}
	public void setPosxAnt(int posxAnt) {
		this.posxAnt = posxAnt;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getPosx() {
		return posx;
	}
	public void setPosx(int posx) {
		this.posx = posx;
	}
	
	
}
