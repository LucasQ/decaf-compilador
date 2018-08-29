lexer grammar DecafLexer;

@header {
package decaf;
}

options
{
  language=Java;
}

tokens
{
  TK_class
}

LCURLY : '{';
RCURLY : '}';

ID  :
  [LETRA][LETRA | NUMERO]*;

WS_ : [' ' | '\n' ] -> skip;

SL_COMMENT : '//' (~'\n')* '\n' -> skip;

CHAR : '\'' [ESC|LETRA|NUMERO|OUTROS] '\'';
STRING : '"' [ESC|LETRA|NUMERO]+ '"';

fragment
ESC :  '\\'['n'|'t'|'"'|'\\'|'.'|'?'];
OUTROS: ['!'|'#'|'$'|'@'|'&'];
LETRA: [a-z]|[A-Z];
NUMERO: [0-9];

