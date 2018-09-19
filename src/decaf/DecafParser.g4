parser grammar DecafParser;

@header {
package decaf;
}

options
{
  language=Java;
  tokenVocab=DecafLexer;
}

program: CLASS PROGRAM LCURLY field_decl*;


statement: 
block: LCURLY  
field_decl: '{' TYPE ID | TYPE ID '[' INT ']'}+,';'
method_decl: LCURLY TYPE | VOID RCURLY ID ABREPARENTESE [ {TYPE ID}+,  ] FECHAPARENTESE hblocki