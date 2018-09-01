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

ABREPARENTESES: '(';
FECHAPARENTESES: ')';
ABRECHAVE: '{';
FECHACHAVE: '}';
COLCHETE: ('['|']');

CONTINUE: 'continue';
BREAK: 'break';
BOOLEAN: 'boolean';
BOOLEANLITERAL: ('false'|'true');
CALLOUT: 'callout';
CLASS: 'class';
ELSE: 'else';
FALSE: 'false';
IF: 'if';
INT: 'int';
RETURN: 'return';
VOID: 'void';
FOR: 'for';


ID: [a-zA-Z_] [a-zA-Z_0-9]* ;

WS_: (' ' | '\n' ) -> skip;

SL_COMMENT: '//' (~'\n')* '\n' -> skip;

CHAR: '\'' (ESC|LETRA|NUMERAL|OUTROS) '\'';
STRING: '"' (ESC|LETRA|NUMERAL|OUTROS)* '"';
OPERADORES: ('+'|'-'|'*'|'<'|'<='|'!='|'&&');
NUMBER: (NUMERAL)+;

fragment
ESC: '\\' ('n'|'"'|'t'|'\\'|'\'');

fragment
OUTROS: [(-/!|#-&|{-~|:-@|^-`];

fragment
LETRA: [a-zA-Z\u0080-\u00FF_ ];

fragment
NUMERAL: [0-9];



