//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 2 "Gramatica.y"
package compilador;
import java.util.Vector;
import java.util.Enumeration;
//#line 21 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short ENTERO=257;
public final static short SI=258;
public final static short ENTONCES=259;
public final static short SINO=260;
public final static short IMPRIMIR=261;
public final static short ITERAR=262;
public final static short HASTA=263;
public final static short ID=264;
public final static short CONSTANTE=265;
public final static short DOBLE=266;
public final static short STRING=267;
public final static short COMPARADOR=268;
public final static short ASIGNACION=269;
public final static short FIN=270;
public final static short RANGO=271;
public final static short DE=272;
public final static short VECTOR=273;
public final static short CTEENTERO=274;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    0,    0,    1,    1,    3,    3,    3,    3,
    3,    5,    5,    6,    6,    6,    6,    6,    6,    6,
    4,    4,    2,    2,    7,    7,    7,    7,    7,    8,
    8,    8,    8,    8,    8,    8,    8,    9,    9,   13,
   14,   14,   14,   15,   10,   11,   11,   11,   11,   12,
   12,   12,   16,   16,   16,   17,   17,   17,   17,   17,
   17,   18,   18,   18,   18,
};
final static short yylen[] = {                            2,
    3,    4,    3,    3,    2,    1,    3,    2,    3,    3,
    2,    3,    1,    9,    9,    9,    9,    9,    9,    9,
    1,    1,    2,    1,    1,    1,    1,    1,    2,    4,
    4,    4,    3,    7,    7,    7,    6,    3,    1,    4,
    3,    3,    1,    5,    4,    5,    5,    5,    5,    3,
    3,    1,    3,    3,    1,    2,    1,    1,    2,    1,
    1,    4,    4,    4,    4,
};
final static short yydefred[] = {                         0,
    0,   22,    0,   21,    0,    0,    6,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   24,   25,   26,   27,
   28,    0,    0,    0,    0,    0,    0,    5,    0,   13,
    0,   11,    0,   29,    0,    0,    0,    0,    0,    0,
   43,    0,    0,    0,    0,    0,    4,   23,    9,    0,
    0,    0,    0,    3,    0,    0,    0,    1,   10,    7,
    0,    0,   57,   60,    0,    0,    0,   55,   61,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   12,   38,    0,    0,    0,    0,    0,    2,    0,    0,
   56,   59,   32,    0,    0,    0,    0,    0,    0,    0,
   40,    0,    0,    0,   41,   45,    0,   31,   30,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   53,   54,
    0,   49,    0,   48,   47,   46,    0,    0,    0,    0,
    0,    0,   64,   65,   63,   62,    0,   44,    0,    0,
    0,    0,    0,    0,    0,   35,   36,   34,    0,    0,
    0,    0,    0,    0,   15,   17,   16,   19,   20,   18,
   14,
};
final static short yydgoto[] = {                          5,
    6,   15,    7,    8,   16,    9,   17,   18,   19,   20,
   21,   78,   22,   42,   38,   67,   68,   69,
};
final static short yysindex[] = {                      -118,
  -52,    0,  -78,    0,    0,   47,    0, -127,  -44,  -29,
  -22,  -15,   -9,  -86, -128,   38,    0,    0,    0,    0,
    0, -222, -194, -164,  -40,  -79,  -59,    0,  104,    0,
   46,    0,  -39,    0,  -39,  -91,  -39,  -88, -136,  -43,
    0,  -72,  -39,  -39,  -39,  -86,    0,    0,    0,  -80,
   -9,  -64, -147,    0,  -18,   25,  -38,    0,    0,    0,
  117,  125,    0,    0, -152,   33,  114,    0,    0,    1,
  181,  -35,   -9,  182,  186,  -69,  -22,    2,  -42,   15,
    0,    0,  -46,  -33,  -30,  -64, -147,    0,  -39,   27,
    0,    0,    0,  -39,  -39,  -39,  -39,  -21,  175,  -39,
    0,  177,  -55,  -29,    0,    0,  -16,    0,    0,  -14,
  157,  166,  -77,   30,   -7,  -36,  114,  114,    0,    0,
  -39,    0,  155,    0,    0,    0,  -39,  -39,  -12,  -11,
   -6, -177,    0,    0,    0,    0,   40,    0,   58,   59,
    6,   12,   13,   34, -165,    0,    0,    0, -122, -122,
 -122, -122, -122, -102,    0,    0,    0,    0,    0,    0,
    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   68,    0,    0,    0,    0,    0,    0,
    0,  -96,    0,    0,    0,    0,    0,    0,    0,    0,
   60,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -19,    0,    0,    0,  -89,   -4,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   22,   22,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0, -111,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    7,   18,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -76,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,
};
final static short yygindex[] = {                         0,
    0,   28,  264,  144,  265,    0,    5,    0,    0,    0,
    0,   -2,    0,   20,  195,   63,   44,    0,
};
final static int YYTABLESIZE=326;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         36,
   94,   36,   95,  126,   45,   65,   94,   94,   95,   95,
   36,   56,   24,   42,   32,  132,  109,   37,   34,   48,
   34,   58,   58,   58,   39,   58,   65,   58,   39,   34,
   66,   48,   70,   27,   72,   33,   52,   51,   52,   58,
   52,   79,   80,   94,   94,   95,   95,   50,   37,   50,
   35,   50,   35,   80,   52,  105,  136,   94,   51,   95,
   51,   35,   51,   60,   60,   50,   60,   76,   60,   65,
   82,   65,   94,   58,   95,   94,   51,   95,  144,   52,
   48,   50,   94,   89,   95,  134,  114,  116,   52,   50,
  153,   93,  101,   98,  107,  145,   49,  123,  146,   50,
   94,   94,   95,   95,   60,   58,  154,  110,   84,   53,
   51,   13,   91,   40,   60,   41,  147,  148,  137,   74,
   52,   92,  133,   85,  139,  140,   13,   10,   29,   11,
   75,   50,   12,   13,    2,   46,   30,    1,    2,  119,
  120,   47,   51,    4,   42,    3,   42,    4,   42,   42,
   42,   42,   42,  160,    2,   96,  117,  118,   42,   39,
   97,   39,   59,    4,   39,   39,   33,   39,   33,   43,
   73,   33,   33,   39,   33,   71,   55,   23,  131,   37,
   33,   37,   44,   81,   37,   37,  104,   37,   11,   44,
   77,   12,   13,   37,   46,  138,   57,   94,   11,   95,
  125,   12,   13,   10,   46,   11,   83,   89,   12,   13,
   58,   14,   10,  108,   11,   90,   61,   12,   13,  135,
   46,   99,  102,   30,   62,   63,  103,  111,   33,   54,
   33,   88,  100,  122,   64,  124,   58,   61,   58,   33,
  112,   58,   58,  113,   58,   62,   63,  121,   58,  129,
   58,   52,  127,   52,  128,   86,   52,   52,  130,   52,
  141,  142,   50,   52,   50,   52,  143,   50,   50,   28,
   50,  106,   31,   51,   50,   51,   50,  149,   51,   51,
   61,   51,  115,  150,  151,   51,    0,   51,   62,   63,
   62,   63,  155,  156,  157,  158,  159,  161,   87,    0,
   64,    0,   25,    2,   11,  152,    0,   12,   13,    0,
   26,    0,    4,    0,    0,    8,    8,    8,    0,    0,
    8,    8,    0,    8,    0,    8,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
   43,   40,   45,   59,   91,   45,   43,   43,   45,   45,
   40,   91,   91,  125,   59,   93,   59,   40,   59,   15,
   59,   41,   42,   43,   40,   45,   45,   47,  125,   59,
   33,   27,   35,    6,   37,  125,   41,  260,   43,   59,
   45,   44,   45,   43,   43,   45,   45,   41,  125,   43,
   91,   45,   91,   56,   59,  125,   93,   43,   41,   45,
   43,   91,   45,   42,   43,   59,   45,   40,   47,   45,
   51,   45,   43,   93,   45,   43,   59,   45,  256,  274,
   76,   44,   43,   91,   45,   93,   89,   90,   93,   44,
  256,   59,   73,   93,   93,  273,   59,  100,   59,   93,
   43,   43,   45,   45,   59,  125,  272,   93,  256,  274,
   93,   44,  265,  123,   93,  125,   59,   59,  121,  256,
  125,  274,   93,  271,  127,  128,   59,  256,  256,  258,
  267,  125,  261,  262,  257,  264,  264,  256,  257,   96,
   97,  270,  125,  266,  256,  264,  258,  266,  260,  261,
  262,  263,  264,  256,  257,   42,   94,   95,  270,  256,
   47,  258,   59,  266,  261,  262,  256,  264,  258,  256,
  259,  261,  262,  270,  264,  267,  256,  256,  256,  256,
  270,  258,  269,  264,  261,  262,  256,  264,  258,  269,
  263,  261,  262,  270,  264,   41,  256,   43,  258,   45,
  256,  261,  262,  256,  264,  258,  271,   91,  261,  262,
  270,  264,  256,  256,  258,   91,  256,  261,  262,  256,
  264,   41,   41,  264,  264,  265,   41,  274,  269,  270,
  269,  270,  268,   59,  274,   59,  256,  256,  258,  269,
  274,  261,  262,  274,  264,  264,  265,  269,  268,   93,
  270,  256,  269,  258,  269,  274,  261,  262,   93,  264,
  273,  273,  256,  268,  258,  270,  273,  261,  262,    6,
  264,   77,    8,  256,  268,  258,  270,  272,  261,  262,
  256,  264,  256,  272,  272,  268,   -1,  270,  264,  265,
  264,  265,  149,  150,  151,  152,  153,  154,  274,   -1,
  274,   -1,  256,  257,  258,  272,   -1,  261,  262,   -1,
  264,   -1,  266,   -1,   -1,  256,  257,  258,   -1,   -1,
  261,  262,   -1,  264,   -1,  266,
};
}
final static short YYFINAL=5;
final static short YYMAXTOKEN=274;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
"'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,null,"';'",
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
"'['",null,"']'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,"ENTERO","SI","ENTONCES","SINO",
"IMPRIMIR","ITERAR","HASTA","ID","CONSTANTE","DOBLE","STRING","COMPARADOR",
"ASIGNACION","FIN","RANGO","DE","VECTOR","CTEENTERO",
};
final static String yyrule[] = {
"$accept : programa",
"programa : declaraciones sentencias_ejecutables FIN",
"programa : declaraciones sentencias_ejecutables error FIN",
"programa : declaraciones error FIN",
"programa : error sentencias_ejecutables FIN",
"declaraciones : declaraciones declaracion",
"declaraciones : declaracion",
"declaracion : tipo lista_variables ';'",
"declaracion : tipo lista_variables",
"declaracion : error lista_variables ';'",
"declaracion : tipo error ';'",
"declaracion : vector_declaracion ';'",
"lista_variables : lista_variables ',' ID",
"lista_variables : ID",
"vector_declaracion : ID '[' CTEENTERO RANGO CTEENTERO ']' VECTOR DE tipo",
"vector_declaracion : ID error CTEENTERO RANGO CTEENTERO ']' VECTOR DE tipo",
"vector_declaracion : ID '[' CTEENTERO RANGO CTEENTERO error VECTOR DE tipo",
"vector_declaracion : ID '[' CTEENTERO error CTEENTERO ']' VECTOR DE tipo",
"vector_declaracion : ID '[' CTEENTERO RANGO CTEENTERO ']' VECTOR DE error",
"vector_declaracion : ID '[' CTEENTERO RANGO CTEENTERO ']' error DE tipo",
"vector_declaracion : ID '[' CTEENTERO RANGO CTEENTERO ']' VECTOR error tipo",
"tipo : DOBLE",
"tipo : ENTERO",
"sentencias_ejecutables : sentencias_ejecutables sentencia",
"sentencias_ejecutables : sentencia",
"sentencia : asignacion",
"sentencia : seleccion",
"sentencia : iteracion",
"sentencia : print",
"sentencia : error ';'",
"asignacion : ID ASIGNACION expresion ';'",
"asignacion : ID ASIGNACION expresion error",
"asignacion : error ASIGNACION expresion ';'",
"asignacion : error ASIGNACION expresion",
"asignacion : ID '[' expresion ']' ASIGNACION expresion ';'",
"asignacion : error '[' expresion ']' ASIGNACION expresion ';'",
"asignacion : ID error expresion ']' ASIGNACION expresion ';'",
"asignacion : ID '[' expresion ']' ASIGNACION expresion",
"seleccion : seleccion_simple SINO bloque",
"seleccion : seleccion_simple",
"seleccion_simple : SI condicion ENTONCES bloque",
"bloque : '{' sentencias_ejecutables '}'",
"bloque : '{' sentencias_ejecutables error",
"bloque : '}'",
"condicion : '(' expresion COMPARADOR expresion ')'",
"iteracion : ITERAR bloque HASTA condicion",
"print : IMPRIMIR '(' STRING ')' ';'",
"print : IMPRIMIR '(' STRING ')' error",
"print : IMPRIMIR '(' error ')' ';'",
"print : error '(' STRING ')' ';'",
"expresion : expresion '+' termino",
"expresion : expresion '-' termino",
"expresion : termino",
"termino : termino '*' factor",
"termino : termino '/' factor",
"termino : factor",
"factor : '-' CONSTANTE",
"factor : CONSTANTE",
"factor : ID",
"factor : '-' CTEENTERO",
"factor : CTEENTERO",
"factor : expresion_vector",
"expresion_vector : ID '[' expresion ']'",
"expresion_vector : ID '[' expresion error",
"expresion_vector : error '[' expresion ']'",
"expresion_vector : ID '[' error ']'",
};

//#line 211 "Gramatica.y"

void yyerror(String s) {
	if(s.contains("under"))
		System.out.println("par:"+s);
}

AnalizadorLexico analizador;
Mensajes manejador;
TablaSimbolos tabla;

public void setLexico(AnalizadorLexico al) {
	analizador = al;
	tabla = al.getTablaDeSimbolos();
}

public void setMensajes(Mensajes ms) {
	manejador = ms;
}

int yylex()
{
	int val = analizador.yylex();
	yylval = new ParserVal(analizador.getToken());
	yylval.ival = analizador.getNroLinea();
	
	return val;
}
//#line 391 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 2:
//#line 32 "Gramatica.y"
{manejador.error(analizador.getNroLinea(),analizador.getMensaje(4),"SINTACTICO");}
break;
case 3:
//#line 33 "Gramatica.y"
{manejador.error(analizador.getNroLinea(),analizador.getMensaje(5),"SINTACTICO");}
break;
case 4:
//#line 34 "Gramatica.y"
{manejador.error(analizador.getNroLinea(),analizador.getMensaje(6),"SINTACTICO");}
break;
case 7:
//#line 42 "Gramatica.y"
{ 	manejador.estructuraSintactica(analizador.getNroLinea(), analizador.getMensaje(30));
											Enumeration e = ((Vector<Token>)val_peek(1).obj).elements();
											while (e.hasMoreElements()){
												Token token = (Token)e.nextElement();
												if (token.getETS().getTipo() == null){
													token.getETS().setTipo(token.getTipo());
												}
											}
										}
break;
case 8:
//#line 52 "Gramatica.y"
{	manejador.error(analizador.getNroLinea(), analizador.getMensaje(7),"SINTACTICO");}
break;
case 9:
//#line 53 "Gramatica.y"
{	manejador.error(analizador.getNroLinea(), analizador.getMensaje(8),"SINTACTICO");}
break;
case 10:
//#line 54 "Gramatica.y"
{	manejador.error(analizador.getNroLinea(), analizador.getMensaje(9),"SINTACTICO");}
break;
case 11:
//#line 55 "Gramatica.y"
{manejador.estructuraSintactica(analizador.getNroLinea(), analizador.getMensaje(37));}
break;
case 12:
//#line 60 "Gramatica.y"
{	Vector<Token> tokens = (Vector<Token>)val_peek(2).obj;
													Token token = (Token)val_peek(0).obj;
													token.setTipo("entero");
													tokens.add(token);
													yyval.obj = tokens;
												}
break;
case 13:
//#line 66 "Gramatica.y"
{	Vector<Token> tokens = new Vector<Token>();
													Token token = (Token)val_peek(0).obj;
													token.setTipo("entero");
													tokens.add(token);
													yyval.obj = tokens;
												}
break;
case 15:
//#line 75 "Gramatica.y"
{	manejador.error(analizador.getNroLinea(), analizador.getMensaje(38),"SINTACTICO");}
break;
case 16:
//#line 76 "Gramatica.y"
{ manejador.error(analizador.getNroLinea(), analizador.getMensaje(39),"SINTACTICO");}
break;
case 17:
//#line 77 "Gramatica.y"
{manejador.error(analizador.getNroLinea(), analizador.getMensaje(40),"SINTACTICO");}
break;
case 18:
//#line 78 "Gramatica.y"
{manejador.error(analizador.getNroLinea(), analizador.getMensaje(41),"SINTACTICO");}
break;
case 19:
//#line 79 "Gramatica.y"
{ manejador.error(analizador.getNroLinea(), analizador.getMensaje(42),"SINTACTICO");}
break;
case 20:
//#line 80 "Gramatica.y"
{  manejador.error(analizador.getNroLinea(), analizador.getMensaje(43),"SINTACTICO");}
break;
case 29:
//#line 98 "Gramatica.y"
{manejador.error(analizador.getNroLinea(),analizador.getMensaje(19),"SINTACTICO");}
break;
case 30:
//#line 101 "Gramatica.y"
{manejador.estructuraSintactica(analizador.getNroLinea(), analizador.getMensaje(31));}
break;
case 31:
//#line 102 "Gramatica.y"
{manejador.error(analizador.getNroLinea(),analizador.getMensaje(7),"SINTACTICO");}
break;
case 32:
//#line 103 "Gramatica.y"
{manejador.error(analizador.getNroLinea(),analizador.getMensaje(11),"SINTACTICO");}
break;
case 33:
//#line 104 "Gramatica.y"
{manejador.error(analizador.getNroLinea(),analizador.getMensaje(12),"SINTACTICO");}
break;
case 34:
//#line 105 "Gramatica.y"
{manejador.estructuraSintactica(analizador.getNroLinea(), analizador.getMensaje(43));}
break;
case 35:
//#line 106 "Gramatica.y"
{manejador.error(analizador.getNroLinea(), analizador.getMensaje(11),"SINTACTICO");}
break;
case 36:
//#line 107 "Gramatica.y"
{manejador.error(analizador.getNroLinea(), analizador.getMensaje(38),"SINTACTICO");}
break;
case 37:
//#line 109 "Gramatica.y"
{manejador.error(analizador.getNroLinea(), analizador.getMensaje(7),"SINTACTICO");}
break;
case 38:
//#line 112 "Gramatica.y"
{manejador.estructuraSintactica(analizador.getNroLinea(), analizador.getMensaje(32));}
break;
case 39:
//#line 113 "Gramatica.y"
{manejador.estructuraSintactica(analizador.getNroLinea(), analizador.getMensaje(32));}
break;
case 41:
//#line 120 "Gramatica.y"
{manejador.estructuraSintactica(analizador.getNroLinea(), analizador.getMensaje(35));}
break;
case 42:
//#line 121 "Gramatica.y"
{manejador.error(analizador.getNroLinea(),analizador.getMensaje(13),"SINTACTICO");}
break;
case 43:
//#line 122 "Gramatica.y"
{manejador.error(analizador.getNroLinea(),analizador.getMensaje(14),"SINTACTICO");}
break;
case 45:
//#line 129 "Gramatica.y"
{manejador.estructuraSintactica(analizador.getNroLinea(), analizador.getMensaje(33));}
break;
case 46:
//#line 132 "Gramatica.y"
{manejador.estructuraSintactica(analizador.getNroLinea(), analizador.getMensaje(34));}
break;
case 47:
//#line 133 "Gramatica.y"
{manejador.error(analizador.getNroLinea(),analizador.getMensaje(7),"SINTACTICO");}
break;
case 48:
//#line 134 "Gramatica.y"
{manejador.error(analizador.getNroLinea(),analizador.getMensaje(17),"SINTACTICO");}
break;
case 49:
//#line 135 "Gramatica.y"
{manejador.error(analizador.getNroLinea(),analizador.getMensaje(18),"SINTACTICO");}
break;
case 56:
//#line 148 "Gramatica.y"
{	String lexema = ((Token)val_peek(0).obj).getLexema();
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
							yyval.obj = tabla.getTabla().get(nuevoLexema);
						}
break;
case 59:
//#line 174 "Gramatica.y"
{	String lexema = ((Token)val_peek(0).obj).getLexema();
							EntradaTS entrada = (EntradaTS)tabla.getTabla().get(lexema);
							String nuevoLexema = "-"+lexema;
							if (entrada.getContRef() == 1){
								if (tabla.getTabla().contains(nuevoLexema))
									((EntradaTS)tabla.getTabla().get(nuevoLexema)).incrementarCont();
								else {	EntradaTS nuevaEntrada = new EntradaTS(CONSTANTE, nuevoLexema);
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
									EntradaTS nuevaEntrada = new EntradaTS(CONSTANTE, nuevoLexema);
									tabla.addETS(nuevaEntrada.getLexema(), nuevaEntrada);
									nuevaEntrada.setTipo("entero");
								}
							}
							yyval.obj = tabla.getTabla().get(nuevoLexema);
						}
break;
case 63:
//#line 204 "Gramatica.y"
{manejador.error(analizador.getNroLinea(),analizador.getMensaje(39),"SINTACTICO");}
break;
case 64:
//#line 205 "Gramatica.y"
{manejador.error(analizador.getNroLinea(),analizador.getMensaje(11),"SINTACTICO");}
break;
case 65:
//#line 206 "Gramatica.y"
{manejador.error(analizador.getNroLinea(),analizador.getMensaje(44),"SINTACTICO");}
break;
//#line 764 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
