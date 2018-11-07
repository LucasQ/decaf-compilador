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

ABRECHAVE: '{';
FECHACHAVE: '}';
LCOLCHETE: '[';
RCOLCHETE: ']';
AP: '(';
FP: ')';
 
IF : 'if';
ELSE : 'else';
CALLOUT : 'callout';
BOOLEANLITERAL : ('false'|'true');
BOOLEAN : 'boolean';
CLASS : 'class';
PROGRAM: 'Program';
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
NUMBER: (NUMERAL)+;
REL_OP: ('<'|'>'|'>='|'<=');
EQ_OP: ('!='|'==');
COND_OP: ('&&'| '||');
HIFEN: '-';
ARITH_OP:  ('%'|'/'|'*'|'-'|'+');


V : ',';
PV : ';';
EXC: '!';
IGUAL: '=';
PLUSIGUAL: '+=';


ONLY0X: '0x';
HEXA : '0x'(NUMERAL|LETRA)+;
MINIGUAL: '-=';


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