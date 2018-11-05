package decaf;

import java.lang.System;
import org.antlr.symtab.FunctionSymbol;
import org.antlr.symtab.GlobalScope;
import org.antlr.symtab.LocalScope;
import org.antlr.symtab.Scope;
import org.antlr.symtab.VariableSymbol;
import org.antlr.symtab.Symbol;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import java.util.*;

import com.sun.tools.javac.Main;

/**
 * This class defines basic symbols and scopes for Decaf language
 */
public class DecafSymbolsAndScopes extends DecafParserBaseListener {
    ParseTreeProperty<Scope> scopes = new ParseTreeProperty<Scope>();
    GlobalScope globals;
    Scope currentScope; // define symbols in this scope
    static final int SAIDA = 0;
    static final String NULL = "";
    
    @Override
    public void enterProgram(DecafParser.ProgramContext ctx) {
        globals = new GlobalScope(null);
        pushScope(globals);
    }

    @Override
    public void exitProgram(DecafParser.ProgramContext ctx) {
        System.out.println(globals);

        boolean main = globals.getSymbols().contains("main");

        if (!main) {
            System.err.println("Não tem main");
            System.exit(SAIDA);
        }
    }

    @Override
    public void enterField_decl(DecafParser.Field_declContext ctx) {

        if (ctx.size() > 0 && ctx.get(0).getText() != NULL) {
                        
                if (arraysize <= SAIDA) {
                    this.error(ctx.integer().get(0).NUMBER().getSymbol(), " tamanho de array não válido");
                    System.exit(SAIDA);
                }
            }
            determinationVar(ctx.ID().get(SAIDA).getSymbol()); 
    }

    @Override
    public void enterMethod_decl(DecafParser.Method_declContext ctx) {
        String name = ctx.ID().get(SAIDA).getText();
        //int typeTokenType = ctx.type().start.getType();
        //DecafSymbol.Type type = this.getType(typeTokenType);

        // push new scope by making new one that points to enclosing scope
        FunctionSymbol function = new FunctionSymbol(name);
        //function.setType(type); // Set symbol type

        currentScope.define(function); // Define function in current scope
        saveScope(ctx, function);
        pushScope(function);
    }

    // @Override
    // public void exitMethod_decl(DecafParser.Method_declContext ctx) {
    //     popScope();
    // }

    @Override
    public void enterBlock(DecafParser.BlockContext ctx) {
        // LocalScope l = new LocalScope(currentScope);
        // saveScope(ctx, currentScope);
        // pushScope(l);
    }

    @Override
    public void exitBlock(DecafParser.BlockContext ctx) {
        popScope();
    }

    @Override
    public void enterStatement(DecafParser.StatementContext stmt) {
        
        
        if (!symbols.contains(new VariableSymbol(stmt.location().ID().getSymbol().getText()))) {
            this.error(stmt.location().ID().getSymbol(), "wrong declaration identifier");
            System.exit(SAIDA);
        }else{
            System.exit(1);
        }
    }
    
    @Override
    public void exitStatement(DecafParser.StatementContext ctx) {
    }


    @Override
    public void enterVar_decl(DecafParser.Var_declContext ctx) {
        Token name = ctx.ID().get(SAIDA);
        this.determinationVar(name);
    }


    @Override
    public void exitVar_decl(DecafParser.Var_declContext ctx) {
    
    }


    //     @Override
    //     public void enterDecl(DecafParser.Field_declContext ctx) {
    //         defineVar(ctx.type(), ctx.ID().getSymbol());
    //     }

    //     @Override
    // public void exitDecl(DecafParser.Field_declContext ctx) {
    //     String name = ctx.ID().getSymbol().getText();
    //     Symbol var = currentScope.resolve(name);
    //     if ( var==null ) {
    //         this.error(ctx.ID().getSymbol(), "no such variable: "+name);
    //     }
    //     if ( var instanceof FunctionSymbol ) {
    //         this.error(ctx.ID().getSymbol(), name+" is not a variable");
    //     }
    // }

    void determinationVar(Token nameToken) {
       //int typeTokenType = typeCtx.getStart().getType();
        VariableSymbol var = new VariableSymbol(nameToken.getText());

        // DecafSymbol.Type type = this.getType(typeTokenType);
        // var.setType(type);

        currentScope.define(var); // Define symbol in current scope

        
        
    }

    /**
     * Método que atualiza o escopo para o atual e imprime o valor
     *
     * @param s
     */
    private void pushScope(Scope s) {
        currentScope = s;
        System.out.println("entering: "+currentScope.getName()+":"+s);
    }

    /**
     * Método que cria um novo escopo no contexto fornecido
     *
     * @param ctx
     * @param s
     */
    void saveScope(ParserRuleContext ctx, Scope s) {
        scopes.put(ctx, s);
    }

    /**
     * Muda para o contexto superior e atualia o escopo
     */
    private void popScope() {
        System.out.println("leaving: "+currentScope.getName()+":"+currentScope);
        currentScope = currentScope.getEnclosingScope();
    }

    public static void error(Token t, String msg) {
        System.err.printf("line %d:%d %s\n", t.getLine(), t.getCharPositionInLine(),
                msg);
    }

    /**
     * Valida tipos encontrados na linguagem para tipos reais
     *
     * @param tokenType
     * @return
     */
    //// public static DecafSymbol.Type getType(int tokenType) {
    //     switch ( tokenType ) {
    //         case DecafParser.VOID :  return DecafSymbol.Type.tVOID;
    //         case DecafParser.NUMBER :   return DecafSymbol.Type.tINT;
    //     }
    //     return DecafSymbol.Type.tINVALID;
    // } java -jar dist/Compiler.jar -target inter ../semantics/legal-01.dcf
   


}
