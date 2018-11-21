parser grammar DecafParser;

@header {
package decaf;
}

options
{
  language=Java;
  tokenVocab=DecafLexer;
}
program: CLASS PROGRAM ABRECHAVE field_decl* method_decl* FECHACHAVE;

field_decl : ( type ID | type ID LCOLCHETE integer + RCOLCHETE( V ( type ID LCOLCHETE integer RCOLCHETE | type ID ))*) PV;

method_decl : (type | VOID) ID AP ( type ID (V type ID)*)* FP block;

block : ABRECHAVE var_decl* statement* FECHACHAVE;

var_decl : type ID (V ID)* PV;


statement : (location assign_op expr PV 
			| method_call PV
			| IF expr block (ELSE block)*
			| FOR ID IGUAL expr V expr block
			| RETURN (expr)* PV
			| BREAK PV
			| CONTINUE PV
			| block);

assign_op : IGUAL | PLUSIGUAL | MINIGUAL;

method_call : method_name AP (expr (V expr)*)? FP 
			|CALLOUT (AP strings (V callout_arg (V callout_arg)*)?)* (FP)?;

location : ID | ID LCOLCHETE expr RCOLCHETE;

expr : location (expr)*
	| method_call 
	| literal 
	| expr bin_op expr 
	| HIFEN expr 
	| EXC expr 
	| AP expr FP;

callout_arg : expr | strings;

call_arg : (expr | STRING);

bin_op : (ARITH_OP | REL_OP | EQ_OP | COND_OP);
literal : (integer | char_literal | bool_literal);

type:  INT | BOOLEAN;
integer : (NUMBER | HEXA);
strings: STRING*;
bool_literal : BOOLEANLITERAL;
char_literal : CHAR;
method_name : ID;