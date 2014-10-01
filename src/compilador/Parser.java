package compilador;
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






//#line 2 "gramatica.y"

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
    8,    8,    8,    9,    9,   13,   14,   14,   14,   15,
   10,   11,   11,   11,   11,   12,   12,   12,   16,   16,
   16,   17,   17,   17,   17,   17,   18,
};
final static short yylen[] = {                            2,
    3,    4,    3,    3,    2,    1,    3,    2,    3,    3,
    1,    3,    1,    9,    9,    9,    9,    9,    9,    9,
    1,    1,    2,    1,    1,    1,    1,    1,    2,    4,
    4,    4,    3,    3,    1,    4,    3,    3,    1,    5,
    4,    5,    5,    5,    5,    3,    3,    1,    3,    3,
    1,    2,    1,    1,    1,    1,    4,
};
final static short yydefred[] = {                         0,
    0,   22,    0,   21,    0,    0,    6,    0,   11,    0,
    0,    0,    0,    0,    0,    0,   24,   25,   26,   27,
   28,    0,    0,    0,    0,    0,    0,    5,    0,   13,
    0,    0,   29,    0,    0,    0,    0,    0,   39,    0,
    0,    0,    4,   23,    9,    0,    0,    0,    0,    3,
    0,    1,   10,    7,    0,   53,   55,    0,    0,    0,
   51,   56,    0,    0,    0,    0,    0,    0,    0,    0,
   12,   34,    0,    0,    0,    2,    0,   52,   32,    0,
    0,    0,    0,    0,    0,   36,    0,    0,    0,   37,
   41,   31,   30,    0,    0,    0,    0,    0,    0,   49,
   50,   45,    0,   44,   43,   42,    0,    0,    0,    0,
   57,   40,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   15,   17,   16,   19,   20,   18,   14,
};
final static short yydgoto[] = {                          5,
    6,   15,    7,    8,   16,    9,   17,   18,   19,   20,
   21,   59,   22,   40,   36,   60,   61,   62,
};
final static short yysindex[] = {                      -176,
  -72,    0,  -84,    0,    0, -148,    0, -188,    0,  -29,
  -24,   32,  -54, -185, -169,   -6,    0,    0,    0,    0,
    0, -206, -183, -180,  -40,  -86, -159,    0,   39,    0,
    5,  -39,    0, -167,  -39, -155, -215,  -65,    0, -151,
  -39, -185,    0,    0,    0, -149,  -54, -145, -211,    0,
  -38,    0,    0,    0,   50,    0,    0, -122,   18,   28,
    0,    0,  108,  -35,  -54,  109,  111,  -98,  -24,  -42,
    0,    0, -119, -113, -112,    0,  -39,    0,    0,  -39,
  -39,  -39,  -39,  114,  -39,    0,  118,  -55,  -29,    0,
    0,    0,    0,   75,   85,  -81,  -14,   28,   28,    0,
    0,    0,  126,    0,    0,    0,  -92,  -88,  -79, -217,
    0,    0,  -85,  -77,  -74,  -70, -238, -199, -199, -199,
 -199, -199, -134,    0,    0,    0,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    6,    0,    0,    0,    0,    0,    0,
    0, -105,    0,    0,    0,    0,    0,    0,    0,    0,
 -137,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -19,    0,    0,    0,  -82,   -8,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0, -116,    0,
    0,    0,    0,    0,    0,    0,    0,    3,   14,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    9,  194,   15,  195,    0,   -2,    0,    0,    0,
    0,    1,    0,  -33,  135,    2,   48,    0,
};
final static int YYTABLESIZE=284;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         34,
   80,   34,   81,  106,   24,   58,   24,   80,   38,   81,
   34,  110,   44,   72,   27,   35,   93,  122,   33,   35,
   33,   54,   54,   54,   44,   54,   90,   54,   80,   33,
   81,   86,   48,  123,   48,   64,   48,   46,  116,   54,
   66,   70,   33,   46,   74,   46,   68,   46,   46,   13,
   48,   67,   45,   47,   47,  117,   47,    2,   47,   75,
   80,   46,   81,   54,   13,   44,    4,   29,   38,   82,
   39,   37,   47,   54,   83,   30,   79,   97,  111,    1,
    2,   98,   99,   41,   48,  103,   10,    3,   11,    4,
   48,   12,   13,   49,   42,   46,   51,   53,   11,   63,
   43,   12,   13,   65,   42,   54,   47,   25,    2,   11,
   52,   69,   12,   13,   71,   26,   48,    4,    8,    8,
    8,  129,    2,    8,    8,   73,    8,   46,    8,  100,
  101,    4,  124,  125,  126,  127,  128,  130,   47,   38,
   77,   38,   78,   38,   38,   38,   38,   38,   84,   87,
   35,   88,   35,   38,   94,   35,   35,   89,   35,   11,
   95,   96,   12,   13,   35,   42,  112,  107,   80,   23,
   81,   23,  102,   33,  109,   33,  104,  108,   33,   33,
  113,   33,   41,   10,  114,   11,  118,   33,   12,   13,
   10,   14,   11,  115,  119,   12,   13,  120,   42,   28,
  105,  121,   31,   91,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   92,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   30,   55,   56,    0,    0,   32,   50,
   32,   76,   85,    0,   57,    0,   54,    0,   54,   32,
    0,   54,   54,    0,   54,    0,    0,   48,   54,   48,
   54,    0,   48,   48,    0,   48,    0,    0,   46,   48,
   46,   48,    0,   46,   46,    0,   46,    0,    0,   47,
   46,   47,   46,    0,   47,   47,    0,   47,    0,    0,
    0,   47,    0,   47,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
   43,   40,   45,   59,   91,   45,   91,   43,  125,   45,
   40,   93,   15,   47,    6,   40,   59,  256,   59,  125,
   59,   41,   42,   43,   27,   45,  125,   47,   43,   59,
   45,   65,   41,  272,   43,   35,   45,   44,  256,   59,
  256,   41,  125,   41,  256,   43,   38,   45,   44,   44,
   59,  267,   59,  260,   41,  273,   43,  257,   45,  271,
   43,   59,   45,   59,   59,   68,  266,  256,  123,   42,
  125,   40,   59,   93,   47,  264,   59,   77,   93,  256,
  257,   80,   81,  269,   93,   85,  256,  264,  258,  266,
  274,  261,  262,  274,  264,   93,  256,   59,  258,  267,
  270,  261,  262,  259,  264,  125,   93,  256,  257,  258,
  270,  263,  261,  262,  264,  264,  125,  266,  256,  257,
  258,  256,  257,  261,  262,  271,  264,  125,  266,   82,
   83,  266,  118,  119,  120,  121,  122,  123,  125,  256,
   91,  258,  265,  260,  261,  262,  263,  264,   41,   41,
  256,   41,  258,  270,  274,  261,  262,  256,  264,  258,
  274,  274,  261,  262,  270,  264,   41,   93,   43,  256,
   45,  256,   59,  256,  256,  258,   59,   93,  261,  262,
  273,  264,  269,  256,  273,  258,  272,  270,  261,  262,
  256,  264,  258,  273,  272,  261,  262,  272,  264,    6,
  256,  272,    8,   69,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  256,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  264,  264,  265,   -1,   -1,  269,  270,
  269,  270,  268,   -1,  274,   -1,  256,   -1,  258,  269,
   -1,  261,  262,   -1,  264,   -1,   -1,  256,  268,  258,
  270,   -1,  261,  262,   -1,  264,   -1,   -1,  256,  268,
  258,  270,   -1,  261,  262,   -1,  264,   -1,   -1,  256,
  268,  258,  270,   -1,  261,  262,   -1,  264,   -1,   -1,
   -1,  268,   -1,  270,
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
"declaracion : vector_declaracion",
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
"factor : CTEENTERO",
"factor : asig_vector",
"asig_vector : ID '[' expresion ']'",
};

//#line 183 "gramatica.y"

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
//#line 361 "Parser.java"
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
//#line 32 "gramatica.y"
{manejador.error(analizador.getNroLinea(),analizador.getMensaje(4),"SINTACTICO");}
break;
case 3:
//#line 33 "gramatica.y"
{manejador.error(analizador.getNroLinea(),analizador.getMensaje(5),"SINTACTICO");}
break;
case 4:
//#line 34 "gramatica.y"
{manejador.error(analizador.getNroLinea(),analizador.getMensaje(6),"SINTACTICO");}
break;
case 7:
//#line 42 "gramatica.y"
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
//#line 52 "gramatica.y"
{	manejador.error(analizador.getNroLinea(), analizador.getMensaje(7),"SINTACTICO");}
break;
case 9:
//#line 53 "gramatica.y"
{	manejador.error(analizador.getNroLinea(), analizador.getMensaje(8),"SINTACTICO");}
break;
case 10:
//#line 54 "gramatica.y"
{	manejador.error(analizador.getNroLinea(), analizador.getMensaje(9),"SINTACTICO");}
break;
case 12:
//#line 59 "gramatica.y"
{	Vector<Token> tokens = (Vector<Token>)val_peek(2).obj;
													Token token = (Token)val_peek(0).obj;
													token.setTipo("entero");
													tokens.add(token);
													yyval.obj = tokens;
												}
break;
case 13:
//#line 65 "gramatica.y"
{	Vector<Token> tokens = new Vector<Token>();
													Token token = (Token)val_peek(0).obj;
													token.setTipo("entero");
													tokens.add(token);
													yyval.obj = tokens;
												}
break;
case 14:
//#line 73 "gramatica.y"
{manejador.estructuraSintactica(analizador.getNroLinea(), analizador.getMensaje(37));
																		
																		
											

																	 }
break;
case 15:
//#line 79 "gramatica.y"
{	manejador.error(analizador.getNroLinea(), analizador.getMensaje(38),"SINTACTICO");}
break;
case 16:
//#line 80 "gramatica.y"
{ manejador.error(analizador.getNroLinea(), analizador.getMensaje(39),"SINTACTICO");}
break;
case 17:
//#line 81 "gramatica.y"
{manejador.error(analizador.getNroLinea(), analizador.getMensaje(40),"SINTACTICO");}
break;
case 18:
//#line 82 "gramatica.y"
{manejador.error(analizador.getNroLinea(), analizador.getMensaje(41),"SINTACTICO");}
break;
case 19:
//#line 83 "gramatica.y"
{ manejador.error(analizador.getNroLinea(), analizador.getMensaje(42),"SINTACTICO");}
break;
case 20:
//#line 84 "gramatica.y"
{  manejador.error(analizador.getNroLinea(), analizador.getMensaje(43),"SINTACTICO");}
break;
case 24:
//#line 95 "gramatica.y"
{manejador.estructuraSintactica(analizador.getNroLinea(), analizador.getMensaje(36));}
break;
case 29:
//#line 102 "gramatica.y"
{manejador.error(analizador.getNroLinea(),analizador.getMensaje(19),"SINTACTICO");}
break;
case 30:
//#line 105 "gramatica.y"
{manejador.estructuraSintactica(analizador.getNroLinea(), analizador.getMensaje(31));}
break;
case 31:
//#line 106 "gramatica.y"
{manejador.error(analizador.getNroLinea(),analizador.getMensaje(7),"SINTACTICO");}
break;
case 32:
//#line 107 "gramatica.y"
{manejador.error(analizador.getNroLinea(),analizador.getMensaje(11),"SINTACTICO");}
break;
case 33:
//#line 108 "gramatica.y"
{manejador.error(analizador.getNroLinea(),analizador.getMensaje(12),"SINTACTICO");}
break;
case 34:
//#line 111 "gramatica.y"
{manejador.estructuraSintactica(analizador.getNroLinea(), analizador.getMensaje(32));}
break;
case 35:
//#line 112 "gramatica.y"
{manejador.estructuraSintactica(analizador.getNroLinea(), analizador.getMensaje(32));}
break;
case 37:
//#line 119 "gramatica.y"
{manejador.estructuraSintactica(analizador.getNroLinea(), analizador.getMensaje(35));}
break;
case 38:
//#line 120 "gramatica.y"
{manejador.error(analizador.getNroLinea(),analizador.getMensaje(13),"SINTACTICO");}
break;
case 39:
//#line 121 "gramatica.y"
{manejador.error(analizador.getNroLinea(),analizador.getMensaje(14),"SINTACTICO");}
break;
case 41:
//#line 128 "gramatica.y"
{manejador.estructuraSintactica(analizador.getNroLinea(), analizador.getMensaje(33));}
break;
case 42:
//#line 131 "gramatica.y"
{manejador.estructuraSintactica(analizador.getNroLinea(), analizador.getMensaje(34));}
break;
case 43:
//#line 132 "gramatica.y"
{manejador.error(analizador.getNroLinea(),analizador.getMensaje(7),"SINTACTICO");}
break;
case 44:
//#line 133 "gramatica.y"
{manejador.error(analizador.getNroLinea(),analizador.getMensaje(17),"SINTACTICO");}
break;
case 45:
//#line 134 "gramatica.y"
{manejador.error(analizador.getNroLinea(),analizador.getMensaje(18),"SINTACTICO");}
break;
case 52:
//#line 147 "gramatica.y"
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
//#line 688 "Parser.java"
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
