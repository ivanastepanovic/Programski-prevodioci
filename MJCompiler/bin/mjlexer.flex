
package rs.ac.bg.etf.pp1;

import java_cup.runtime.Symbol;

%%

%{

	// ukljucivanje informacije o poziciji tokena
	private Symbol new_symbol(int type) {
		return new Symbol(type, yyline+1, yycolumn);
	}
	
	// ukljucivanje informacije o poziciji tokena
	private Symbol new_symbol(int type, Object value) {
		return new Symbol(type, yyline+1, yycolumn, value);
	}

%}

%cup
%line
%column

%xstate COMMENT

%eofval{
	return new_symbol(sym.EOF);
%eofval}

%%

" " 	{ }
"\b" 	{ }
"\t" 	{ }
"\r\n" 	{ }
"\f" 	{ }

"program"   { return new_symbol(sym.PROG, yytext());}
"count"     {return new_symbol(sym.CNT, yytext());}
"if"   { return new_symbol(sym.IF, yytext());}
"else"   { return new_symbol(sym.ELSE, yytext());}
"print" 	{ return new_symbol(sym.PRINT, yytext()); }
"return" 	{ return new_symbol(sym.RETURN, yytext()); }
"void" 		{ return new_symbol(sym.VOID, yytext()); }
"+" 		{ return new_symbol(sym.PLUS, yytext()); }
"=" 		{ return new_symbol(sym.EQUAL, yytext()); }
";" 		{ return new_symbol(sym.SEMI, yytext()); }
"," 		{ return new_symbol(sym.COMMA, yytext()); }
"(" 		{ return new_symbol(sym.LPAREN, yytext()); }
")" 		{ return new_symbol(sym.RPAREN, yytext()); }
"{" 		{ return new_symbol(sym.LBRACE, yytext()); }
"}"			{ return new_symbol(sym.RBRACE, yytext()); }

//"class"     { return new_symbol(sym.CLASS,     yytext());}
//"interface" { return new_symbol(sym.INTERFACE, yytext());}
"enum"      { return new_symbol(sym.ENUM,      yytext());}
"new "      { return new_symbol(sym.NEW,       yytext());}
"for"       { return new_symbol(sym.FOR,       yytext());}
//"extends"   { return new_symbol(sym.EXTENDS,   yytext());}
"continue"  { return new_symbol(sym.CONTINUE,  yytext());}
"break"     { return new_symbol(sym.BREAK,     yytext());}
"read"      { return new_symbol(sym.READ,      yytext());}

"-"      { return new_symbol(sym.MINUS,     yytext());}
"*"      { return new_symbol(sym.MUL,       yytext());}
"/"      { return new_symbol(sym.DIV,       yytext());}
"%"      { return new_symbol(sym.MOD,       yytext());}
"!="     { return new_symbol(sym.NOTEQUAL,  yytext());}
">"      { return new_symbol(sym.GREATER,   yytext());}
">="     { return new_symbol(sym.GREAT_EQU, yytext());}
"<"      { return new_symbol(sym.LESS,      yytext());}
"<="     { return new_symbol(sym.LESS_EQU,  yytext());}
"&&"     { return new_symbol(sym.AND,       yytext());}
"||"     { return new_symbol(sym.OR,        yytext());}
"++"     { return new_symbol(sym.INC,       yytext());}
"--"     { return new_symbol(sym.DEC,       yytext());}
"["      { return new_symbol(sym.LSQBRACKET,  yytext());}
"]"      { return new_symbol(sym.RSQBRACKET,  yytext());}
"=="     { return new_symbol(sym.EQUCMP,  yytext());}


"const"     { return new_symbol(sym.CONST, yytext());}
"\."        { return new_symbol(sym.DOT,   yytext());}


"//" {yybegin(COMMENT);}
<COMMENT> . {yybegin(COMMENT);}
<COMMENT> "\r\n" { yybegin(YYINITIAL); }

[0-9]+          { return new_symbol(sym.NUMBER, new Integer (yytext())); }
"true"|"false"  { return new_symbol(sym.BOOL, new Boolean (yytext().equals("true") ? true : false)); }
//regex za char
'.'           { return new_symbol(sym.CHAR, new Character (yytext().charAt(1))); }

([a-z]|[A-Z])[a-z|A-Z|0-9|_]* 	{return new_symbol (sym.IDENT, yytext()); }

. { System.err.println("Leksicka greska ("+yytext()+") u liniji "+(yyline+1)+ " u koloni " + (yycolumn + 1)); }
