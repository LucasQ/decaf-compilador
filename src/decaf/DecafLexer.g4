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

FABREPARENTESES: '(';
FECHAPARENTESES: ')';
ABRECHAVE: '{';
FECHACHAVE: '}';
COLCHETE: ('['|']');

BOOLEAN : 'boolean';
CALLOUT : 'callout';
CLASS : 'class';
ELSE : 'else';
BOOLEANLITERAL : ('false'|'true');
IF : 'if';
INT : 'int';
RETURN : 'return';
VOID : 'void';
FOR : 'for';
BREAK : 'break';
CONTINUE : 'continue';


WS_: (' '|'\n'|'\t' ) -> skip;

SL_COMMENT: '//' (~'\n')* '\n' -> skip;

ID: [a-zA-Z_] [a-zA-Z_0-9]* ;
CHAR: '\'' (ESC|LETRA|NUMERAL|OUTROS) '\'';
STRING: '"' (ESC|LETRA|NUMERAL|OUTROS)* '"';
OPERADORES: (CONDICIONAL|OP);
NUMBER: (NUMERAL)+;
ALL : (OPERADORES|','|';'|'|'|CONDICIONAL);
ONLY0X: '0x';
HEXA : '0x'(NUMERAL|LETRA)+;

fragment
ESC: '\\' ('n'|'"'|'t'|'\\'|'\'');

fragment
OUTROS: [(-/!|#-&|{-~|:-@|^-`];

fragment
LETRA: [a-zA-Z\u0080-\u00FF_ ];

fragment
NUMERAL: [0-9];

fragment
CONDICIONAL: ('<'|'>'|'='|'>='|'<='|'&&'|'!='|'==');

fragment
OP: ('^'|'-'|'*'|'+'|'-');
