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
public final static short DE=272;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    0,    0,    1,    1,    3,    3,    3,    3,
    3,    6,    4,    4,    5,    5,    2,    2,    7,    7,
    7,    7,    7,    8,    8,    8,    8,    9,    9,   13,
   14,   14,   14,   15,   10,   11,   11,   11,   11,   12,
   12,   12,   16,   16,   16,   17,   17,   17,   17,   18,
};
final static short yylen[] = {                            2,
    3,    4,    3,    3,    2,    1,    3,    2,    3,    3,
    1,    8,    1,    1,    3,    1,    2,    1,    1,    1,
    1,    1,    2,    4,    4,    4,    3,    3,    1,    4,
    3,    3,    1,    5,    4,    5,    5,    5,    5,    3,
    3,    1,    3,    3,    1,    2,    1,    1,    1,    4,
};
final static short yydefred[] = {                         0,
    0,   14,    0,   13,    0,    0,    6,    0,   11,    0,
    0,    0,    0,    0,    0,    0,   18,   19,   20,   21,
   22,    0,    0,    0,    0,    0,    5,    0,   16,    0,
    0,   23,    0,    0,    0,    0,    0,   33,    0,    0,
    0,    4,   17,    9,    0,    0,    0,    3,    0,    1,
   10,    7,    0,   47,    0,    0,    0,   45,   49,    0,
    0,    0,    0,    0,    0,    0,    0,   15,   28,    0,
    2,    0,   46,   26,    0,    0,    0,    0,    0,    0,
   30,    0,    0,    0,   31,   35,   25,   24,    0,    0,
    0,    0,   43,   44,   39,    0,   38,   37,   36,    0,
   50,   34,    0,   12,
};
final static short yydgoto[] = {                          5,
    6,   15,    7,    8,   16,    9,   17,   18,   19,   20,
   21,   56,   22,   39,   35,   57,   58,   59,
};
final static short yysindex[] = {                      -175,
 -135,    0,  -76,    0,    0, -153,    0, -192,    0,  -34,
    2,    9,  -28, -204, -195,   -6,    0,    0,    0,    0,
    0, -184, -170,  -40,  -86, -178,    0,   20,    0,   -5,
  -37,    0, -173,  -37, -158, -220,  -77,    0, -161,  -37,
 -204,    0,    0,    0, -154,  -28, -159,    0,  -38,    0,
    0,    0,   27,    0, -140,  -27,   46,    0,    0,   91,
  -42,  -28,   92,   93,  -84,    2,  -16,    0,    0, -122,
    0,  -37,    0,    0,  -37,  -37,  -37,  -37,   77,  -37,
    0,   78,  -55,  -34,    0,    0,    0,    0,   48,  -33,
   46,   46,    0,    0,    0,  -32,    0,    0,    0, -125,
    0,    0, -189,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   12,    0,    0,    0,    0,    0,    0,
    0, -108,    0,    0,    0,    0,    0,    0,    0, -142,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  -19,    0,    0,  -95,   -8,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0, -118,    0,    0,    0,    0,    0,    0,
    3,   14,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    8,  143,   52,  149,    0,    5,    0,    0,    0,
    0,   18,    0,  -12,   85,   24,   53,    0,
};
final static int YYTABLESIZE=284;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         33,
   75,   33,   76,   99,   23,   33,   32,   55,  102,   75,
   75,   76,   76,   26,   23,   75,   29,   76,   32,   43,
   32,   48,   48,   48,   32,   48,   75,   48,   76,   27,
   43,   74,   42,   69,   42,   63,   42,   45,   45,   48,
   85,   34,   88,   40,   65,   40,   64,   40,   36,   81,
   42,   61,   44,   52,   41,   16,   41,   67,   41,  101,
   10,   40,   11,   28,   40,   12,   13,    2,   41,   43,
   16,   29,   41,   48,   42,   46,    4,   49,   51,   11,
    1,    2,   12,   13,   42,   41,   47,   77,    3,   90,
    4,   50,   78,   60,   37,   40,   38,   96,   91,   92,
   62,   66,   24,    2,   11,   48,   41,   12,   13,   68,
   25,   70,    4,    8,    8,    8,   42,   72,    8,    8,
   10,    8,   11,    8,   73,   12,   13,   40,   14,   93,
   94,   79,   82,   83,   89,   95,   97,   32,   41,   32,
  100,   32,   32,   32,   32,   32,  103,   29,   27,   29,
   86,   32,   29,   29,  104,   29,   30,    0,    0,    0,
   27,   29,   27,    0,    0,   27,   27,    0,   27,    0,
    0,   84,    0,   11,   27,    0,   12,   13,   10,   41,
   11,    0,   40,   12,   13,    0,   41,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   98,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   29,    0,   80,   53,   54,   31,   48,
   31,   71,    0,    0,   31,    0,   48,    0,   48,   87,
    0,   48,   48,    0,   48,    0,    0,   42,   48,   42,
   48,    0,   42,   42,    0,   42,    0,    0,   40,   42,
   40,   42,    0,   40,   40,    0,   40,    0,    0,   41,
   40,   41,   40,    0,   41,   41,    0,   41,    0,    0,
    0,   41,    0,   41,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
   43,   40,   45,   59,   91,   40,  125,   45,   41,   43,
   43,   45,   45,    6,   91,   43,  125,   45,   59,   15,
   59,   41,   42,   43,   59,   45,   43,   47,   45,  125,
   26,   59,   41,   46,   43,  256,   45,   44,   44,   59,
  125,   40,   59,   41,   37,   43,  267,   45,   40,   62,
   59,   34,   59,   59,   41,   44,   43,   40,   45,   93,
  256,   59,  258,  256,  269,  261,  262,  257,  264,   65,
   59,  264,   59,   93,  270,  260,  266,  256,   59,  258,
  256,  257,  261,  262,   93,  264,  257,   42,  264,   72,
  266,  270,   47,  267,  123,   93,  125,   80,   75,   76,
  259,  263,  256,  257,  258,  125,   93,  261,  262,  264,
  264,  271,  266,  256,  257,  258,  125,   91,  261,  262,
  256,  264,  258,  266,  265,  261,  262,  125,  264,   77,
   78,   41,   41,   41,  257,   59,   59,  256,  125,  258,
   93,  260,  261,  262,  263,  264,  272,  256,    6,  258,
   66,  270,  261,  262,  103,  264,    8,   -1,   -1,   -1,
  256,  270,  258,   -1,   -1,  261,  262,   -1,  264,   -1,
   -1,  256,   -1,  258,  270,   -1,  261,  262,  256,  264,
  258,   -1,  269,  261,  262,   -1,  264,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  256,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  264,   -1,  268,  264,  265,  269,  270,
  269,  270,   -1,   -1,  269,   -1,  256,   -1,  258,  256,
   -1,  261,  262,   -1,  264,   -1,   -1,  256,  268,  258,
  270,   -1,  261,  262,   -1,  264,   -1,   -1,  256,  268,
  258,  270,   -1,  261,  262,   -1,  264,   -1,   -1,  256,
  268,  258,  270,   -1,  261,  262,   -1,  264,   -1,   -1,
   -1,  268,   -1,  270,
};
}
final static short YYFINAL=5;
final static short YYMAXTOKEN=272;
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
"ASIGNACION","FIN","\"..\"","\"DE\"",
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
"vector_declaracion : ID '[' ENTERO \"..\" ENTERO ']' \"DE\" tipo",
"tipo : DOBLE",
"tipo : ENTERO",
"lista_variables : lista_variables ',' ID",
"lista_variables : ID",
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
"factor : asig_vector",
"asig_vector : ID '[' expresion ']'",
};

//#line 161 "Gramatica.y"

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

public void setManejadorDeMensajes(Mensajes ms) {
	manejador = ms;
}

int yylex()
{
	int val = analizador.yylex();
	yylval = new ParserVal(analizador.getToken());
	yylval.ival = analizador.getNroLinea();
	
	return val;
}
//#line 343 "Parser.java"
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
//#line 27 "Gramatica.y"
{manejador.error(analizador.getNroLinea(),analizador.getMensaje(4),"SINTACTICO");}
break;
case 3:
//#line 28 "Gramatica.y"
{manejador.error(analizador.getNroLinea(),analizador.getMensaje(5),"SINTACTICO");}
break;
case 4:
//#line 29 "Gramatica.y"
{manejador.error(analizador.getNroLinea(),analizador.getMensaje(6),"SINTACTICO");}
break;
case 7:
//#line 37 "Gramatica.y"
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
//#line 46 "Gramatica.y"
{	manejador.error(analizador.getNroLinea(), analizador.getMensaje(7),"SINTACTICO");}
break;
case 9:
//#line 47 "Gramatica.y"
{	manejador.error(analizador.getNroLinea(), analizador.getMensaje(8),"SINTACTICO");}
break;
case 10:
//#line 48 "Gramatica.y"
{	manejador.error(analizador.getNroLinea(), analizador.getMensaje(9),"SINTACTICO");}
break;
case 15:
//#line 58 "Gramatica.y"
{	Vector<Token> tokens = (Vector<Token>)val_peek(2).obj;
													Token token = (Token)val_peek(0).obj;
													token.setTipo("doble");
													tokens.add(token);
													yyval.obj = tokens;
												}
break;
case 16:
//#line 64 "Gramatica.y"
{ 	Vector<Token> tokens = new Vector<Token>();
													Token token = (Token)val_peek(0).obj;
													token.setTipo("doble");
													tokens.add(token);
													yyval.obj = tokens;
												}
break;
case 23:
//#line 82 "Gramatica.y"
{manejador.error(analizador.getNroLinea(),analizador.getMensaje(19),"SINTACTICO");}
break;
case 24:
//#line 85 "Gramatica.y"
{manejador.estructuraSintactica(analizador.getNroLinea(), analizador.getMensaje(31));}
break;
case 25:
//#line 86 "Gramatica.y"
{manejador.error(analizador.getNroLinea(),analizador.getMensaje(7),"SINTACTICO");}
break;
case 26:
//#line 87 "Gramatica.y"
{manejador.error(analizador.getNroLinea(),analizador.getMensaje(11),"SINTACTICO");}
break;
case 27:
//#line 88 "Gramatica.y"
{manejador.error(analizador.getNroLinea(),analizador.getMensaje(12),"SINTACTICO");}
break;
case 28:
//#line 91 "Gramatica.y"
{manejador.estructuraSintactica(analizador.getNroLinea(), analizador.getMensaje(32));}
break;
case 29:
//#line 92 "Gramatica.y"
{manejador.estructuraSintactica(analizador.getNroLinea(), analizador.getMensaje(32));}
break;
case 31:
//#line 99 "Gramatica.y"
{manejador.estructuraSintactica(analizador.getNroLinea(), analizador.getMensaje(35));}
break;
case 32:
//#line 100 "Gramatica.y"
{manejador.error(analizador.getNroLinea(),analizador.getMensaje(13),"SINTACTICO");}
break;
case 33:
//#line 101 "Gramatica.y"
{manejador.error(analizador.getNroLinea(),analizador.getMensaje(14),"SINTACTICO");}
break;
case 35:
//#line 108 "Gramatica.y"
{manejador.estructuraSintactica(analizador.getNroLinea(), analizador.getMensaje(33));}
break;
case 36:
//#line 111 "Gramatica.y"
{manejador.estructuraSintactica(analizador.getNroLinea(), analizador.getMensaje(34));}
break;
case 37:
//#line 112 "Gramatica.y"
{manejador.error(analizador.getNroLinea(),analizador.getMensaje(7),"SINTACTICO");}
break;
case 38:
//#line 113 "Gramatica.y"
{manejador.error(analizador.getNroLinea(),analizador.getMensaje(17),"SINTACTICO");}
break;
case 39:
//#line 114 "Gramatica.y"
{manejador.error(analizador.getNroLinea(),analizador.getMensaje(18),"SINTACTICO");}
break;
case 46:
//#line 127 "Gramatica.y"
{ 	String lexema = ((Token)val_peek(0).obj).getLexema();
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
//#line 633 "Parser.java"
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
