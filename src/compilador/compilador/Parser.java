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






//#line 2 "Gramatica4.y"
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
    3,    5,    5,    5,    6,    6,    6,    6,    6,    6,
    6,    4,    4,    2,    2,    7,    7,    7,    7,    7,
    7,    8,    8,    8,    8,    8,    8,    8,    8,    8,
    8,    9,    9,   13,   13,   13,   13,   13,   13,   13,
   14,   14,   14,   15,   15,   15,   15,   15,   10,   10,
   10,   10,   11,   11,   11,   11,   11,   11,   12,   12,
   12,   16,   16,   16,   17,   17,   17,   17,   17,   17,
   18,   18,   18,   18,
};
final static short yylen[] = {                            2,
    3,    4,    3,    3,    2,    1,    3,    3,    3,    3,
    2,    3,    2,    1,    9,    9,    9,    9,    9,    9,
    9,    1,    1,    2,    1,    1,    1,    1,    1,    2,
    1,    4,    4,    4,    4,    3,    4,    7,    7,    7,
    6,    1,    3,    6,    6,    6,    6,    6,    6,    5,
    3,    3,    1,    3,    3,    3,    3,    1,    6,    6,
    6,    5,    5,    5,    5,    5,    5,    4,    3,    3,
    1,    3,    3,    1,    2,    1,    1,    2,    1,    1,
    4,    4,    4,    4,
};
final static short yydefred[] = {                         0,
    0,   23,    0,   22,    0,    0,    6,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   31,   25,   26,   27,
   28,   29,    0,    0,    0,    0,    0,    0,    5,    0,
    0,   14,    0,   11,    0,   30,    0,    0,    0,    0,
    0,    0,    0,   53,    0,    0,    0,    0,    0,    4,
   24,   13,    9,    0,    0,    0,    0,    3,    0,    1,
   10,    8,    7,    0,    0,   76,   79,    0,    0,    0,
   74,   80,    0,    0,    0,   58,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   12,   43,    0,    0,    0,    2,
    0,    0,   75,   78,   35,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   68,    0,    0,   51,    0,    0,    0,   37,    0,
   34,   33,   32,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   72,   73,    0,    0,   66,    0,    0,
    0,    0,    0,    0,   50,    0,    0,   67,   65,   64,
   63,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   83,   84,   82,   81,    0,   47,   49,   48,   45,
   44,   60,   61,   59,    0,    0,   46,    0,    0,    0,
    0,    0,   39,   40,   38,    0,    0,    0,    0,    0,
    0,   16,   18,   17,   20,   21,   19,   15,
};
final static short yydgoto[] = {                          5,
    6,   15,    7,    8,   16,   17,   18,   19,   20,   21,
   22,   77,   23,   45,   78,   70,   71,   72,
};
final static short yysindex[] = {                       -77,
  -44,    0,  -82,    0,    0,  153,    0, -165,  -21,  -13,
  -29,  -28,   72,  -33,  112,  -39,    0,    0,    0,    0,
    0,    0, -135, -137, -123,  -40,  -33,  143,    0,  -21,
   99,    0,  -43,    0,   79,    0,   79,   63,  -30,   68,
 -106,  -97,  185,    0, -125,   82,   84,   95,  -30,    0,
    0,    0,    0,  -92,   72,  -96, -242,    0,  -38,    0,
    0,    0,    0,   87,  100,    0,    0, -101,   58,    1,
    0,    0,   -1,  -83,  145,    0,   -9,  151,  163,  -35,
  -37,  164,  169,   46,  -93,  171,  -15,  -96,   26,   27,
  -10, -242,   17,  175,    0,    0,  -12,   -5,    8,    0,
   79,   98,    0,    0,    0,   79,   79,   79,   79,  -50,
   79,  194,   79,  101,   29,   36,   43,   72, -115,  204,
  216,    0,  -56,  -13,    0,  -30,  -30,  -30,    0,   20,
    0,    0,    0,   38,   50,  233,  241,  -80,   21,  183,
   16,    1,    1,    0,    0,   79,  242,    0,  242,   87,
  242,   72,   72,   72,    0,   72,   72,    0,    0,    0,
    0,  298,  300,  304,   79,   79,   72,   88,   91,   94,
 -166,    0,    0,    0,    0,   77,    0,    0,    0,    0,
    0,    0,    0,    0,   83,   90,    0,   78,   80,  105,
  111, -140,    0,    0,    0, -100, -100, -100, -100, -100,
  -58,    0,    0,    0,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  -14,    0,    0,    0,    0,    0,    0,
    0,    0,  -68,    0,    0,    0,    0,    0,    0,  172,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -19,    0,    0,    0,  -55,   -4,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   37,    0,    0,
    0,   30,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0, -108,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    9,   22,    0,    0,    0,   35,    0,   42,   52,
   59,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  123,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  136,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    4,  349,  262,  363,  139,    3,    0,    0,    0,
    0,  343,    0,  270,   55,    5,  -11,    0,
};
final static int YYTABLESIZE=509;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         38,
   54,   38,  161,  119,   54,  117,   49,  101,   25,   28,
   40,   42,  171,   98,   68,   63,   52,   51,   36,   53,
   36,   77,   77,   77,  128,   77,   38,   77,   99,   14,
   51,  125,  106,  106,  107,  107,   71,   34,   71,   77,
   71,  106,  108,  107,   14,   36,   85,  109,  133,   69,
   37,   69,   37,   69,   71,  101,   42,   48,  106,  106,
  107,  107,   70,  106,   70,  107,   70,   69,  106,   36,
  107,   79,   79,   77,   79,   56,   79,   37,   79,   79,
   70,   79,   55,   79,  129,  131,  123,   51,   71,  191,
   31,  110,   57,   79,   81,   79,  144,  145,   32,   54,
  106,   69,  107,   94,  122,   77,  192,   68,  175,  134,
  142,  143,   68,  172,   70,  200,  105,  101,  130,  106,
   71,  107,   79,   68,   55,  106,   68,  107,   68,   79,
   86,  201,  106,   69,  107,  193,   56,   87,    9,   68,
  156,  194,   68,  157,   30,   68,   70,   52,  195,   52,
   57,   52,   52,   52,   52,   52,    2,   61,   83,   56,
   82,   52,  124,  103,   11,    4,   55,   12,   13,   84,
   27,   95,  104,   24,   97,  170,   57,  101,    1,    2,
  162,  163,  164,   54,  111,  112,    3,   42,    4,   42,
  102,  115,   42,   42,   43,   42,   44,  207,    2,  160,
   36,   42,   36,  116,  120,   36,   36,    4,   36,  121,
  126,   10,   62,   11,   36,  135,   12,   13,  146,   14,
   52,  118,   46,   32,   52,   74,   39,   41,   35,   58,
   35,  100,  111,   65,   66,   47,   77,   76,   77,   77,
  127,   77,   77,   67,   77,  132,  113,   62,   77,   14,
   77,   71,  148,   71,   71,   35,   71,   71,  114,   71,
   41,  136,  158,   71,   69,   71,   69,   69,  137,   69,
   69,  174,   69,  101,  159,  173,   69,   70,   69,   70,
   70,  138,   70,   70,  106,   70,  107,  152,  165,   70,
   56,   70,   56,   56,  153,   56,   56,   55,   56,   55,
   55,  154,   55,   55,   56,   55,  166,   57,  167,   57,
   57,   55,   57,   57,   54,   57,   54,   54,   74,   54,
   54,   57,   54,   80,   96,  168,   65,   66,   54,   75,
   76,   65,   66,  169,   64,   76,   67,   64,  182,   90,
  183,   67,   65,   66,  184,   65,   66,   65,   66,  196,
   64,  197,   67,  140,   29,   88,  150,   67,   65,   66,
  188,   65,   66,  189,   65,   66,  190,   10,   92,   11,
   33,   67,   12,   13,   67,   27,  198,   69,   62,   73,
   62,   50,  199,   62,   62,    0,   62,  155,   89,   91,
   93,   41,   62,   41,    0,    0,   41,   41,   59,   41,
   11,    0,    0,   12,   13,   41,   27,    0,   26,    2,
   11,    0,   60,   12,   13,    0,   27,    0,    4,    0,
    0,  177,  178,  179,    0,  180,  181,   31,    0,   31,
    0,    0,   31,   31,    0,   31,  187,    0,    0,    0,
   10,   31,   11,  139,  141,   12,   13,    0,   27,    0,
    0,    0,    0,  147,    0,  149,  151,  202,  203,  204,
  205,  206,  208,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  176,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  185,  186,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
   44,   40,   59,   41,   44,   41,   40,   91,   91,    6,
   40,   40,   93,  256,   45,   59,  125,   15,   59,   59,
   59,   41,   42,   43,   40,   45,   40,   47,  271,   44,
   28,  125,   43,   43,   45,   45,   41,   59,   43,   59,
   45,   43,   42,   45,   59,   59,   43,   47,   59,   41,
   91,   43,   91,   45,   59,   91,  125,   91,   43,   43,
   45,   45,   41,   43,   43,   45,   45,   59,   43,  125,
   45,   42,   43,   93,   45,   41,   47,   91,   42,   43,
   59,   45,   41,   47,   59,   59,   41,   85,   93,  256,
  256,   93,   41,   39,   40,   59,  108,  109,  264,   41,
   43,   93,   45,   49,   59,  125,  273,   45,   93,   93,
  106,  107,   45,   93,   93,  256,   59,   91,   93,   43,
  125,   45,   93,   45,  260,   43,   45,   45,   45,   93,
  256,  272,   43,  125,   45,   59,  274,  263,    0,   45,
  256,   59,   45,  259,    6,   45,  125,  256,   59,  258,
  274,  260,  261,  262,  263,  264,  257,   59,  256,  125,
  267,  270,  256,  265,  258,  266,  125,  261,  262,  267,
  264,  264,  274,  256,  271,  256,  125,   91,  256,  257,
  126,  127,  128,  125,  268,   41,  264,  256,  266,  258,
   91,   41,  261,  262,  123,  264,  125,  256,  257,  256,
  256,  270,  258,   41,   41,  261,  262,  266,  264,   41,
   40,  256,  256,  258,  270,   41,  261,  262,  269,  264,
  264,  259,  256,  264,  264,  256,  256,  256,  269,  270,
  269,  270,  268,  264,  265,  269,  256,  268,  258,  259,
  256,  261,  262,  274,  264,  256,  256,  125,  268,  264,
  270,  256,   59,  258,  259,  269,  261,  262,  268,  264,
  125,  274,   59,  268,  256,  270,  258,  259,  274,  261,
  262,  256,  264,   91,   59,   93,  268,  256,  270,  258,
  259,  274,  261,  262,   43,  264,   45,  259,  269,  268,
  256,  270,  258,  259,  259,  261,  262,  256,  264,  258,
  259,  259,  261,  262,  270,  264,  269,  256,  259,  258,
  259,  270,  261,  262,  256,  264,  258,  259,  256,  261,
  262,  270,  264,  256,   55,   93,  264,  265,  270,  267,
  268,  264,  265,   93,  256,  268,  274,  256,   41,  256,
   41,  274,  264,  265,   41,  264,  265,  264,  265,  272,
  256,  272,  274,  256,    6,  274,  256,  274,  264,  265,
  273,  264,  265,  273,  264,  265,  273,  256,  274,  258,
    8,  274,  261,  262,  274,  264,  272,   35,  256,   37,
  258,  270,  272,  261,  262,   -1,  264,  118,   46,   47,
   48,  256,  270,  258,   -1,   -1,  261,  262,  256,  264,
  258,   -1,   -1,  261,  262,  270,  264,   -1,  256,  257,
  258,   -1,  270,  261,  262,   -1,  264,   -1,  266,   -1,
   -1,  152,  153,  154,   -1,  156,  157,  256,   -1,  258,
   -1,   -1,  261,  262,   -1,  264,  167,   -1,   -1,   -1,
  256,  270,  258,  101,  102,  261,  262,   -1,  264,   -1,
   -1,   -1,   -1,  111,   -1,  113,  114,  196,  197,  198,
  199,  200,  201,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  146,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  165,  166,
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
"declaracion : tipo lista_variables error",
"declaracion : error lista_variables ';'",
"declaracion : tipo error ';'",
"declaracion : vector_declaracion ';'",
"lista_variables : lista_variables ',' ID",
"lista_variables : lista_variables ID",
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
"sentencia : vector_declaracion",
"asignacion : ID ASIGNACION expresion ';'",
"asignacion : ID ASIGNACION expresion error",
"asignacion : ID ASIGNACION error ';'",
"asignacion : error ASIGNACION expresion ';'",
"asignacion : error ASIGNACION expresion",
"asignacion : ID error expresion ';'",
"asignacion : ID '[' expresion ']' ASIGNACION expresion ';'",
"asignacion : error '[' expresion ']' ASIGNACION expresion ';'",
"asignacion : ID error expresion ']' ASIGNACION expresion ';'",
"asignacion : ID '[' expresion ']' ASIGNACION expresion",
"seleccion : seleccion_simple",
"seleccion : seleccion_simple SINO bloque",
"seleccion_simple : SI '(' condicion ')' ENTONCES bloque",
"seleccion_simple : SI '(' condicion ')' error bloque",
"seleccion_simple : ID '(' condicion ')' ENTONCES bloque",
"seleccion_simple : error '(' condicion ')' ENTONCES bloque",
"seleccion_simple : SI '(' error ')' ENTONCES bloque",
"seleccion_simple : SI error condicion ')' ENTONCES bloque",
"seleccion_simple : SI '(' condicion ENTONCES bloque",
"bloque : '{' sentencias_ejecutables '}'",
"bloque : '{' sentencias_ejecutables error",
"bloque : '}'",
"condicion : expresion COMPARADOR expresion",
"condicion : expresion error expresion",
"condicion : error COMPARADOR expresion",
"condicion : expresion COMPARADOR error",
"condicion : COMPARADOR",
"iteracion : ITERAR bloque HASTA '(' condicion ')'",
"iteracion : ITERAR bloque error '(' condicion ')'",
"iteracion : ITERAR bloque HASTA error condicion ')'",
"iteracion : ITERAR bloque HASTA '(' condicion",
"print : IMPRIMIR '(' STRING ')' ';'",
"print : IMPRIMIR '(' STRING ')' error",
"print : IMPRIMIR '(' error ')' ';'",
"print : error '(' STRING ')' ';'",
"print : IMPRIMIR error STRING ')' ';'",
"print : IMPRIMIR '(' STRING ';'",
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

//#line 338 "Gramatica4.y"

void yyerror(String s) {
	if(s.contains("under"))
		System.out.println("par:"+s);
}

AnalizadorLexico analizador;
Mensajes manejador;
TablaSimbolos tabla;
Vector<Token> vt = new Vector<Token>() ;
ArbolSintactico arbol ;
 
public void setLexico(AnalizadorLexico al) {
	analizador = al;
	tabla = al.getTablaDeSimbolos();
}

public void setMensajes(Mensajes ms) {
	manejador = ms;
}

public void setArbol ( ArbolSintactico a ){
	arbol = a ;
}

int yylex()
{
	int val = analizador.yylex();
	yylval = new ParserVal(analizador.getToken());
	yylval.ival = analizador.getNroLinea();
	
	return val;
}
//#line 468 "Parser.java"
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
case 1:
//#line 31 "Gramatica4.y"
{	ArbolSintactico a1 = ((ArbolSintactico)val_peek(2).obj);
														ArbolSintactico a2 = ((ArbolSintactico)val_peek(1).obj);
														arbol = new ArbolSintactico ("program",a1,a2);
														arbol.imprimir (0);
														
													}
break;
case 2:
//#line 37 "Gramatica4.y"
{manejador.error(analizador.getNroLinea(),analizador.getMensaje(4),"SINTACTICO");}
break;
case 3:
//#line 38 "Gramatica4.y"
{manejador.error(analizador.getNroLinea(),analizador.getMensaje(5),"SINTACTICO");}
break;
case 4:
//#line 39 "Gramatica4.y"
{manejador.error(analizador.getNroLinea(),analizador.getMensaje(6),"SINTACTICO");}
break;
case 5:
//#line 43 "Gramatica4.y"
{	
												ArbolSintactico a1 = ((ArbolSintactico)val_peek(1).obj);
																ArbolSintactico a2 = ((ArbolSintactico)val_peek(0).obj);
																yyval.obj = new ArbolSintactico ("daclaraciones",a1,a2);
											}
break;
case 7:
//#line 51 "Gramatica4.y"
{ 	manejador.estructuraSintactica(analizador.getNroLinea(), analizador.getMensaje(30));
											Enumeration e = ((Vector<Token>)vt).elements();
											String lexema = ((ArbolSintactico)val_peek(2).obj).getValor();
											while (e.hasMoreElements()){
												Token token = (Token)e.nextElement();
												token.getETS().setTipo(lexema);
											}
											vt = new Vector<Token>();
											ArbolSintactico a1 = ((ArbolSintactico)val_peek(2).obj);
											ArbolSintactico a2 = ((ArbolSintactico)val_peek(1).obj);
											yyval.obj = new ArbolSintactico ("declaracion",a1,a2);
											
										}
break;
case 8:
//#line 65 "Gramatica4.y"
{	manejador.error(analizador.getNroLinea(), analizador.getMensaje(7),"SINTACTICO");}
break;
case 9:
//#line 66 "Gramatica4.y"
{	manejador.error(analizador.getNroLinea(), analizador.getMensaje(8),"SINTACTICO");}
break;
case 10:
//#line 67 "Gramatica4.y"
{	manejador.error(analizador.getNroLinea(), analizador.getMensaje(9),"SINTACTICO");}
break;
case 11:
//#line 68 "Gramatica4.y"
{	manejador.estructuraSintactica(analizador.getNroLinea(), analizador.getMensaje(37));
											/*{ $$.obj = ((ArbolSintactico)$1.obj) ; }*/
										}
break;
case 12:
//#line 77 "Gramatica4.y"
{	Token token = (Token)val_peek(0).obj;
													token.setTipo("entero");
													vt.add(token);
													ArbolSintactico a3 = new Hoja (tabla.getTabla().get(token.getLexema()),token.getLexema());
													ArbolSintactico a1 = ((ArbolSintactico)val_peek(2).obj);
													yyval.obj = new ArbolSintactico ("lista var",a1,a3);
												}
break;
case 13:
//#line 84 "Gramatica4.y"
{	manejador.error(analizador.getNroLinea(), analizador.getMensaje(57),"SINTACTICO");}
break;
case 14:
//#line 85 "Gramatica4.y"
{	Vector<Token> tokens = new Vector<Token>();
													Token token = (Token)val_peek(0).obj;
													token.setTipo("entero");
													tokens.add(token);
													vt = tokens ;		
													yyval.obj = new Hoja (tabla.getTabla().get(token.getLexema()),token.getLexema());
												}
break;
case 15:
//#line 95 "Gramatica4.y"
{	String lexema1 = ((Token)val_peek(8).obj).getLexema();
																				String lexema3 = ((Token)val_peek(6).obj).getLexema();
																				String lexema5 = ((Token)val_peek(4).obj).getLexema();
																				ArbolSintactico r1 = new Hoja (tabla.getTabla().get(lexema3),lexema3);
																				ArbolSintactico r2 = new Hoja (tabla.getTabla().get(lexema5),lexema5);
																				ArbolSintactico rango = new ArbolSintactico (("rango") ,r1,r2);
																				ArbolSintactico t = ((ArbolSintactico)val_peek(0).obj);
																				yyval.obj = new ArbolSintactico (lexema1, rango, t);
																			}
break;
case 16:
//#line 104 "Gramatica4.y"
{	manejador.error(analizador.getNroLinea(), analizador.getMensaje(38),"SINTACTICO");}
break;
case 17:
//#line 105 "Gramatica4.y"
{ manejador.error(analizador.getNroLinea(), analizador.getMensaje(39),"SINTACTICO");}
break;
case 18:
//#line 106 "Gramatica4.y"
{manejador.error(analizador.getNroLinea(), analizador.getMensaje(40),"SINTACTICO");}
break;
case 19:
//#line 107 "Gramatica4.y"
{manejador.error(analizador.getNroLinea(), analizador.getMensaje(41),"SINTACTICO");}
break;
case 20:
//#line 108 "Gramatica4.y"
{ manejador.error(analizador.getNroLinea(), analizador.getMensaje(42),"SINTACTICO");}
break;
case 21:
//#line 109 "Gramatica4.y"
{  manejador.error(analizador.getNroLinea(), analizador.getMensaje(43),"SINTACTICO");}
break;
case 22:
//#line 112 "Gramatica4.y"
{	String lexema = ((Token)val_peek(0).obj).getLexema();
					yyval.obj = new Hoja (tabla.getTabla().get(lexema),lexema);
				}
break;
case 23:
//#line 115 "Gramatica4.y"
{	String lexema = ((Token)val_peek(0).obj).getLexema();
					yyval.obj = new Hoja (tabla.getTabla().get(lexema),lexema);
				}
break;
case 24:
//#line 123 "Gramatica4.y"
{	ArbolSintactico a1 = ((ArbolSintactico)val_peek(1).obj);
																ArbolSintactico a2 = ((ArbolSintactico)val_peek(0).obj);
																yyval.obj = new ArbolSintactico ("sentencias",a1,a2);
															}
break;
case 30:
//#line 135 "Gramatica4.y"
{manejador.error(analizador.getNroLinea(),analizador.getMensaje(19),"SINTACTICO");}
break;
case 31:
//#line 136 "Gramatica4.y"
{manejador.error(analizador.getNroLinea(),analizador.getMensaje(19),"SINTACTICO");}
break;
case 32:
//#line 139 "Gramatica4.y"
{manejador.estructuraSintactica(analizador.getNroLinea(), analizador.getMensaje(31));
										Token token1 = ((Token)val_peek(3).obj);
										ArbolSintactico a1 = new Hoja(tabla.getTabla().get(token1.getLexema()),token1.getLexema());
										ArbolSintactico a3 = ((ArbolSintactico)val_peek(1).obj);
										yyval.obj = new ArbolSintactico ("asig", a1 , a3 );
										}
break;
case 33:
//#line 145 "Gramatica4.y"
{manejador.error(analizador.getNroLinea(),analizador.getMensaje(7),"SINTACTICO");}
break;
case 34:
//#line 146 "Gramatica4.y"
{manejador.error(analizador.getNroLinea(),analizador.getMensaje(55),"SINTACTICO");}
break;
case 35:
//#line 147 "Gramatica4.y"
{manejador.error(analizador.getNroLinea(),analizador.getMensaje(11),"SINTACTICO");}
break;
case 36:
//#line 148 "Gramatica4.y"
{manejador.error(analizador.getNroLinea(),analizador.getMensaje(12),"SINTACTICO");}
break;
case 37:
//#line 149 "Gramatica4.y"
{manejador.error(analizador.getNroLinea(), analizador.getMensaje(54),"SINTACTICO");}
break;
case 38:
//#line 150 "Gramatica4.y"
{	manejador.estructuraSintactica(analizador.getNroLinea(), analizador.getMensaje(43));
																String lexID = ((Token)val_peek(6).obj).getLexema();
																ArbolSintactico a1 = new Hoja (tabla.getTabla().get(lexID),lexID);
																ArbolSintactico a3 = ((ArbolSintactico)val_peek(4).obj);
																ArbolSintactico vec = new ArbolSintactico ("vector",a1,a3);
																ArbolSintactico a6 = ((ArbolSintactico)val_peek(1).obj);
																yyval.obj = new ArbolSintactico ("asig vector", vec , a6 );
															}
break;
case 39:
//#line 158 "Gramatica4.y"
{manejador.error(analizador.getNroLinea(), analizador.getMensaje(11),"SINTACTICO");}
break;
case 40:
//#line 159 "Gramatica4.y"
{manejador.error(analizador.getNroLinea(), analizador.getMensaje(38),"SINTACTICO");}
break;
case 41:
//#line 160 "Gramatica4.y"
{manejador.error(analizador.getNroLinea(), analizador.getMensaje(7),"SINTACTICO");}
break;
case 42:
//#line 163 "Gramatica4.y"
{	manejador.estructuraSintactica(analizador.getNroLinea(), analizador.getMensaje(32));
									ArbolSintactico si = ((ArbolSintactico)val_peek(0).obj);
									yyval.obj = new ArbolSintactico ("si", si, null );
								}
break;
case 43:
//#line 167 "Gramatica4.y"
{manejador.estructuraSintactica(analizador.getNroLinea(), analizador.getMensaje(48));
											ArbolSintactico a1 = ((ArbolSintactico)val_peek(2).obj);
											ArbolSintactico a3 =  ((ArbolSintactico)val_peek(0).obj);
											yyval.obj = new ArbolSintactico("sino" , a1, a3 );
											}
break;
case 44:
//#line 175 "Gramatica4.y"
{	ArbolSintactico cn = ((ArbolSintactico)val_peek(3).obj);
															ArbolSintactico bl = ((ArbolSintactico)val_peek(0).obj);
															yyval.obj = new ArbolSintactico ("si",cn,bl);
														}
break;
case 45:
//#line 179 "Gramatica4.y"
{manejador.error(analizador.getNroLinea(), analizador.getMensaje(46),"SINTACTICO");}
break;
case 46:
//#line 180 "Gramatica4.y"
{manejador.error(analizador.getNroLinea(), analizador.getMensaje(47),"SINTACTICO");}
break;
case 47:
//#line 181 "Gramatica4.y"
{manejador.error(analizador.getNroLinea(), analizador.getMensaje(47),"SINTACTICO");}
break;
case 48:
//#line 182 "Gramatica4.y"
{manejador.error(analizador.getNroLinea(),analizador.getMensaje(53),"SINTACTICO");}
break;
case 49:
//#line 183 "Gramatica4.y"
{manejador.error(analizador.getNroLinea(),analizador.getMensaje(51),"SINTACTICO");}
break;
case 50:
//#line 184 "Gramatica4.y"
{manejador.error(analizador.getNroLinea(),analizador.getMensaje(52),"SINTACTICO");}
break;
case 51:
//#line 187 "Gramatica4.y"
{	manejador.estructuraSintactica(analizador.getNroLinea(), analizador.getMensaje(35));
											yyval.obj = ((ArbolSintactico)val_peek(1).obj);
										}
break;
case 52:
//#line 190 "Gramatica4.y"
{manejador.error(analizador.getNroLinea(),analizador.getMensaje(13),"SINTACTICO");}
break;
case 53:
//#line 191 "Gramatica4.y"
{manejador.error(analizador.getNroLinea(),analizador.getMensaje(14),"SINTACTICO");}
break;
case 54:
//#line 195 "Gramatica4.y"
{ 	String lexema = ((Token)val_peek(1).obj).getLexema();
												ArbolSintactico a1 = ((ArbolSintactico)val_peek(2).obj);
												ArbolSintactico a2 = ((ArbolSintactico)val_peek(0).obj);
												yyval.obj = new ArbolSintactico (lexema,a1,a2);
											}
break;
case 55:
//#line 200 "Gramatica4.y"
{manejador.error(analizador.getNroLinea(),analizador.getMensaje(53),"SINTACTICO");}
break;
case 56:
//#line 201 "Gramatica4.y"
{manejador.error(analizador.getNroLinea(),analizador.getMensaje(53),"SINTACTICO");}
break;
case 57:
//#line 202 "Gramatica4.y"
{manejador.error(analizador.getNroLinea(),analizador.getMensaje(53),"SINTACTICO");}
break;
case 58:
//#line 203 "Gramatica4.y"
{manejador.error(analizador.getNroLinea(),analizador.getMensaje(53),"SINTACTICO");}
break;
case 59:
//#line 207 "Gramatica4.y"
{	manejador.estructuraSintactica(analizador.getNroLinea(), analizador.getMensaje(33));
														ArbolSintactico bl = ((ArbolSintactico)val_peek(4).obj);
														ArbolSintactico cn = ((ArbolSintactico)val_peek(1).obj);
														yyval.obj = new ArbolSintactico ("iterar",bl,cn);
													}
break;
case 60:
//#line 213 "Gramatica4.y"
{manejador.error(analizador.getNroLinea(),analizador.getMensaje(50),"SINTACTICO");}
break;
case 61:
//#line 214 "Gramatica4.y"
{manejador.error(analizador.getNroLinea(),analizador.getMensaje(51),"SINTACTICO");}
break;
case 62:
//#line 215 "Gramatica4.y"
{manejador.error(analizador.getNroLinea(),analizador.getMensaje(52),"SINTACTICO");}
break;
case 63:
//#line 218 "Gramatica4.y"
{manejador.estructuraSintactica(analizador.getNroLinea(), analizador.getMensaje(34));
									String lexema = ((Token)val_peek(2).obj).getLexema();
									ArbolSintactico string = new Hoja (tabla.getTabla().get(lexema), lexema);
									yyval.obj = new ArbolSintactico ("print" , string , null );
}
break;
case 64:
//#line 223 "Gramatica4.y"
{manejador.error(analizador.getNroLinea(),analizador.getMensaje(7),"SINTACTICO");}
break;
case 65:
//#line 224 "Gramatica4.y"
{manejador.error(analizador.getNroLinea(),analizador.getMensaje(17),"SINTACTICO");}
break;
case 66:
//#line 225 "Gramatica4.y"
{manejador.error(analizador.getNroLinea(),analizador.getMensaje(18),"SINTACTICO");}
break;
case 67:
//#line 226 "Gramatica4.y"
{manejador.error(analizador.getNroLinea(), analizador.getMensaje(58),"SINTACTICO");}
break;
case 68:
//#line 227 "Gramatica4.y"
{manejador.error(analizador.getNroLinea(), analizador.getMensaje(59),"SINTACTICO");}
break;
case 69:
//#line 230 "Gramatica4.y"
{ ArbolSintactico a1 = ((ArbolSintactico)val_peek(2).obj);
										ArbolSintactico a2 = ((ArbolSintactico)val_peek(0).obj);
										yyval.obj = new ArbolSintactico ("+",a1,a2);
									}
break;
case 70:
//#line 234 "Gramatica4.y"
{ ArbolSintactico a1 = ((ArbolSintactico)val_peek(2).obj);
										ArbolSintactico a2 = ((ArbolSintactico)val_peek(0).obj);
										yyval.obj = new ArbolSintactico ("-",a1,a2);
									}
break;
case 71:
//#line 238 "Gramatica4.y"
{yyval.obj = ((ArbolSintactico)val_peek(0).obj);}
break;
case 72:
//#line 241 "Gramatica4.y"
{ ArbolSintactico a1 = ((ArbolSintactico)val_peek(2).obj);
								  ArbolSintactico a2 = ((ArbolSintactico)val_peek(0).obj);
								  yyval.obj = new ArbolSintactico ("*",a1,a2);
								}
break;
case 73:
//#line 245 "Gramatica4.y"
{ ArbolSintactico a1 = ((ArbolSintactico)val_peek(2).obj);
								  ArbolSintactico a2 = ((ArbolSintactico)val_peek(0).obj);
								  yyval.obj = new ArbolSintactico ("/",a1,a2);
								}
break;
case 74:
//#line 249 "Gramatica4.y"
{yyval.obj = ((ArbolSintactico)val_peek(0).obj);}
break;
case 75:
//#line 252 "Gramatica4.y"
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
							yyval.obj = new Hoja(tabla.getTabla().get(nuevoLexema),nuevoLexema);
						}
break;
case 76:
//#line 276 "Gramatica4.y"
{ String lexema = ((Token)val_peek(0).obj).getLexema();
						yyval.obj = new Hoja (tabla.getTabla().get(lexema),lexema);
					}
break;
case 77:
//#line 280 "Gramatica4.y"
{	String lexema = ((Token)val_peek(0).obj).getLexema();
					yyval.obj = new Hoja (tabla.getTabla().get(lexema),lexema);
				}
break;
case 78:
//#line 283 "Gramatica4.y"
{	String lexema = ((Token)val_peek(0).obj).getLexema();
							
						if(Long.parseLong(lexema)<= Short.MAX_VALUE+1 ) { /*TODO*/
							EntradaTS entrada = (EntradaTS)tabla.getTabla().get(lexema);
							String nuevoLexema = "-"+lexema;
							if (entrada.getContRef() == 1){
								if (tabla.getTabla().contains(nuevoLexema))
									((EntradaTS)tabla.getTabla().get(nuevoLexema)).incrementarCont();
								else {	EntradaTS nuevaEntrada = new EntradaTS(CTEENTERO, nuevoLexema);
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
									EntradaTS nuevaEntrada = new EntradaTS(CTEENTERO, nuevoLexema);
									tabla.addETS(nuevaEntrada.getLexema(), nuevaEntrada);
									nuevaEntrada.setTipo("entero");
								}
							}
							yyval.obj = new Hoja(tabla.getTabla().get(nuevoLexema),nuevoLexema);
						}
				   else {
					manejador.error(analizador.getNroLinea(), analizador.getMensaje(20), "LEXICO"); 
					}
				}
break;
case 79:
//#line 313 "Gramatica4.y"
{	
						String lexema = ((Token)val_peek(0).obj).getLexema();

							if(Long.parseLong(lexema)<= Short.MAX_VALUE ) { /*TODO*/
				
							yyval.obj = new Hoja(tabla.getTabla().get(lexema),lexema);
							}
							else {
							manejador.error(analizador.getNroLinea(), analizador.getMensaje(20), "LEXICO"); 
							}
					}
break;
case 80:
//#line 324 "Gramatica4.y"
{yyval.obj = ((ArbolSintactico)val_peek(0).obj);}
break;
case 81:
//#line 327 "Gramatica4.y"
{	ArbolSintactico id = ((ArbolSintactico)val_peek(3).obj);
											ArbolSintactico exp = ((ArbolSintactico)val_peek(1).obj);
											yyval.obj = new ArbolSintactico ("asig vector" , id , exp);
										}
break;
case 82:
//#line 331 "Gramatica4.y"
{manejador.error(analizador.getNroLinea(),analizador.getMensaje(39),"SINTACTICO");}
break;
case 83:
//#line 332 "Gramatica4.y"
{manejador.error(analizador.getNroLinea(),analizador.getMensaje(11),"SINTACTICO");}
break;
case 84:
//#line 333 "Gramatica4.y"
{manejador.error(analizador.getNroLinea(),analizador.getMensaje(44),"SINTACTICO");}
break;
//#line 1096 "Parser.java"
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
