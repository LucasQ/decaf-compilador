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

ID : [a-zA-Z_] [a-zA-Z_0-9]* ;

WS_ : (' ' | '\n' ) -> skip;

SL_COMMENT : '//' (~'\n')* '\n' -> skip;

CHAR : '\'' (ESC|LETRA|NUMERO|OUTROS) '\'';
STRING : '"' (ESC|LETRA|NUMERO|OUTROS)* '"';

fragment
ESC : '\\' ('n'|'"'|'t'|'\\'|'\'');

fragment
OUTROS: [(-/!#-&{-~:-@^-`];

fragment
LETRA: [a-zA-Z\u0080-\u00FF_ ];

fragment
NUMERO: [0-9];

