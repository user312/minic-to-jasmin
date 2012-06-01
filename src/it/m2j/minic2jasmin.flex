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

Integer             ([1-9][0-9]*)|0
Float	            ([1-9]{1,1}[0-9]*"."[0-9]+)|(0"."[0-9]+)
Identifier          [a-zA-Z_]+[a-zA-Z0-9_]*

%{

	private Symbol sym(int type)
	{
		return sym(type, yytext());
	}
	
	

%}

%% //----------------------------------------------------------------------------------------

"/*"   { Location.locToken(yylength()); yybegin(comment); }

<comment>"/*"       { locNestToken(); yybegin(comment); }
<comment>"*/"       { locNestToken(); yybegin(YYINITIAL); }
<comment>"\n"       { locNestNewline(); }
<comment><<EOF>>    {
						System.out.println("Errore lessicale: manca il token di fine commento '*/'");
						
                        //error(&yylloc, "Errore lessicale", "Manca il token di fine commento '*/'");
                        //yyterminate();
                    }
<comment>.          { locNestToken(); }

"*/"             {
                        locToken();
                        System.out.println("Errore lessicale: Fine inattesa del commento '*/'");
                        
                        //error(&yylloc, "Errore lessicale", "Fine inattesa del commento %s", yytext);
                    }
                    
"//"						{ locToken(); yybegin(singleline_comment);}
<singleline_comment>.       { locNestToken(); /* eat char of the line */  }
<singleline_comment>"\n"    { locNestToken(); yybegin(YYINITIAL); }

"\""                {
                        locToken();
                        //string_buf_ptr = string_buf;
                        yybegin(quote);
                    }

<quote>"\""         {
                        yybegin(YYINITIAL);
                        locNestToken();
                        //yylval.StringVal =
                        //    Strndup(string_buf, string_buf_ptr - string_buf);
                        //return CONST_STRING;
                    }

<quote>"\\n"        { locNestToken(); }		//{ loc_nest_token(); eat_char('\n'); }
<quote>"\\t"        { locNestToken(); }		//{ loc_nest_token(); eat_char('\t'); }
<quote>"\\r"        { locNestToken(); }		//{ loc_nest_token(); eat_char('\r'); }
<quote>"\n"         { locNestNewline(); }	//{ loc_nest_newline(); eat_char('\n'); }
<quote>"\\\n"       { locNestNewline(); }	//{ loc_nest_newline(); /* don't handle, just skip '\n' */ }
<quote>"\\".        { locNestToken(); }		//{ loc_nest_token(); eat_char(yytext[1]); }
<quote><<EOF>>      {
						System.out.println("Errore lessicale: Fine inattesa della stringa");
                        //error(&yylloc, "Errore lessicale", "Fine inattesa della stringa");
                        //yyterminate();
                    }
<quote>[^\\\n"]+    { locNestToken(); }		//{ loc_nest_token(); eat_string(); }

"bool"              { loc_token(); return TYPE_BOOL; }
"int"               { loc_token(); return TYPE_INT; }
"float"             { loc_token(); return TYPE_FLOAT; }
"string"            { loc_token(); return TYPE_STRING; }
"void"              { loc_token(); return TYPE_VOID; }
"extern"            { loc_token(); return EXTERN; }

"if"                { loc_token(); return IF; }
"else"              { loc_token(); return ELSE; }
"while"             { loc_token(); return WHILE; }

"new"               { loc_token(); return NEW; }
"return"            { loc_token(); return RETURN; }
"="                 { loc_token(); return ASSIGN; }

"+"                 { loc_token(); return OP_PLUS; }
"-"                 { loc_token(); return OP_DIFF; }
"*"                 { loc_token(); return OP_TIME; }
"/"                 { loc_token(); return OP_DIV; }
"%"               { loc_token(); return OP_MOD; }

"=="                { loc_token(); return OP_EQ; }
"!="                { loc_token(); return OP_NEQ; }
"<"                 { loc_token(); return OP_LT; }
"<="                { loc_token(); return OP_LET; }
">"                 { loc_token(); return OP_GT; }
">="                { loc_token(); return OP_GET; }

"&&"               { loc_token(); return OP_AND; }
"||"                { loc_token(); return OP_OR; }
"!"               { loc_token(); return OP_NOT; }

","                 { loc_token(); return COMMA; }
";"                 { loc_token(); return SEMI; }

"("                 { loc_token(); return BRA_O; }
")"                 { loc_token(); return BRA_C; }
"["                 { loc_token(); return BRA_OS; }
"]"                 { loc_token(); return BRA_CS; }

"{"		    { loc_token(); return BRA_OG; }
"}"		    { loc_token(); return BRA_CG; }

"true"              { loc_token(); yylval.BoolVal = true;  return CONST_BOOL; }
"false"             { loc_token(); yylval.BoolVal = false; return CONST_BOOL; }

{Integer}           {
                        loc_token();
                        yylval.IntVal = atoi(yytext);
                        return CONST_INT;
                    }
{Float}             {
                        loc_token();
                        yylval.FloatVal = atof(yytext);
                        return CONST_FLOAT;
                    }

{Identifier}        {
                        loc_token();
                        yylval.StringVal = (char *)strndup(yytext, yyleng);
                        if (yylval.StringVal == NULL)
                            err_sys("%s - yylex", PROGRAM);
                        return ID;
                    }

"\n"                { loc_newline(); }

[[:blank:]]+        { loc_token(); }

<<EOF>>             { yyterminate(); }

.                   {
                        loc_token();
                        /* ERROR: unknown token */
                        error(&yylloc, "Errore lessicale", "Token sconosciuto: '%s'", yytext);
                    }

/* EOF scanner.lex */
