/* scanner.flex */

package it.m2j;

import java.io.*;
import java_cup.runtime.*;
import java.io.IOException;

/* TODO
import it.m2j.m2jSym;
import static it.m2j.m2jSym.*;
*/

%% //----------------------------------------------------------------------------------------

%class m2jLex
%public
%unicode

%line
%column

%standalone

//%cupsym it.m2j.m2jSym
//%cup

%xstate comment
%xstate singleline_comment
%xstate quote

Integer = ([1-9][0-9])*|0
Float = ([1-9]{1,1}[0-9]*"."[0-9]+)|(0"."[0-9]+)
Identifier = [a-zA-Z_]+[a-zA-Z0-9_]*

%{
	private Symbol sym(int type)
	{
		return sym(type, yytext());
	}

	private Symbol sym(int type, Object value)
	{
	/*	System.out.println("Symbol found: "+ type + ", symbol name: " + symClass.getTT(type) +
							", text: \"" + yytext() + "\""); */
		return new Symbol(type, yyline, yycolumn, value); 
	}

	private void error() throws IOException
	{
		throw new IOException("illegal text at line = "+yyline+", column = "+yycolumn+", text = '"+yytext()+"'");
	}

%}

%% //----------------------------------------------------------------------------------------

"/*"   { yybegin(comment); }

<comment>"/*"       { yybegin(comment); }
<comment>"*/"       { yybegin(YYINITIAL); }
<comment>"\n"       { ; }
<comment><<EOF>>    { System.out.println("Errore lessicale: manca il token di fine commento '*/'"); }
<comment>.          { ; }

"*/"             	{ System.out.println("Errore lessicale: Fine inattesa del commento '*/'"); }
                    
"//"				{ yybegin(singleline_comment);}
<singleline_comment>.       { ; }
<singleline_comment>"\n"    { yybegin(YYINITIAL); }

"\""                { yybegin(quote); }

<quote>"\""         { yybegin(YYINITIAL); }


<quote>"\\".        { ; } // gestire sequenza di escape all'interno di una stringa quotata
<quote><<EOF>>      { System.out.println("Errore lessicale: Fine inattesa della stringa"); }
<quote>[^\\\n\"]+    { ; }

"bool"              { return sym(TYPE_BOOL); }
"int"               { return sym(TYPE_INT); }
"float"             { return sym(YPE_FLOAT); }
"string"            { return sym(TYPE_STRING); }
"void"              { return sym(TYPE_VOID); }
"extern"            { return sym(EXTERN); }

"if"                { return sym(IF); }
"else"              { return sym(ELSE); }
"while"             { return sym(WHILE); }

"new"               { return sym(NEW); }
"return"            { return sym(RETURN); }
"="                 { return sym(ASSIGN); }

"+"                 { return sym(OP_PLUS); }
"-"                 { return sym(OP_DIFF); }
"*"                 { return sym(OP_TIME); }
"/"                 { return sym(OP_DIV); }
"%"                 { return sym(OP_MOD); }

"=="                { return sym(OP_EQ); }
"!="                { return sym(OP_NEQ); }
"<"                 { return sym(OP_LT); }
"<="                { return sym(OP_LET); }
">"                 { return sym(OP_GT); }
">="                { return sym(OP_GET); }

"&&"               	{ return sym(OP_AND); }
"||"                { return sym(OP_OR); }
"!"               	{ return sym(OP_NOT); }

","                 { return sym(COMMA); }
";"                 { return sym(SEMI); }

"("                 { return sym(BRA_O); }
")"                 { return sym(BRA_C); }
"["                 { return sym(BRA_OS); }
"]"                 { return sym(BRA_CS); }

"{"		    		{ return sym(BRA_OG); }
"}"		    		{ return sym(BRA_CG); }

"true"              { return CONST_BOOL; }
"false"             { return CONST_BOOL; }

{Integer}           { return CONST_INT; }
{Float}             { return CONST_FLOAT; }
{Identifier}        { return ID; }

"\n"                { ; }

.                   { error(); } // ERROR: unknown token
