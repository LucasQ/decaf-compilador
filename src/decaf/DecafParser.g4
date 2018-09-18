parser grammar DecafParser;

@header {
package decaf;
}

options
{
  language=Java;
  tokenVocab=DecafLexer;
}

program: CLASS PROGRAM LCURLY VOID ID ABREPARENTESES FECHAPARENTESES LCURLY RCURLY EOF;
