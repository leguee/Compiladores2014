package compilador;

public class Coord {
	
	private int x ;
	private int y ;
	private int xAnt ;
	private int yAnt ;
	private String valor ;
	private char c ;
	private int posxAnt ;
	
	public Coord(int x, int y, int xAnt, int yAnt, String valor,char c) {
		super();
		this.x = x;
		this.y = y;
		this.xAnt = xAnt;
		this.yAnt = yAnt;
		this.valor = valor;
		this.c = c ;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getxAnt() {
		return xAnt;
	}
	public void setxAnt(int xAnt) {
		this.xAnt = xAnt;
	}
	public int getyAnt() {
		return yAnt;
	}
	public void setyAnt(int yAnt) {
		this.yAnt = yAnt;
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
	
	
}
