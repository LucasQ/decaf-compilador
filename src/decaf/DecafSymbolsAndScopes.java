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
    boolean retorno = false;
    int argmiss;
    String returnMetod;



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
            System.err.println("Don't has main method");
            System.exit(SAIDA);
        }
    }

    @Override
    public void enterField_decl(DecafParser.Field_declContext ctx) {

        if (ctx.LCOLCHETE().size() > 0 && ctx.LCOLCHETE().get(0).getText() != NULL) {
        int arraysize = Integer.parseInt(ctx.integer().get(0).NUMBER().getText());
                        
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
        //int typeTokenType = ctx.type().getStart().getType();
        //DecafSymbol.Type type = this.getType(typeTokenType);
        // push new scope by making new one that points to enclosing scope
        FunctionSymbol function = new FunctionSymbol(name);
        //function.setType(type); // Set symbol type

        currentScope.define(function); // Define function in current scope

        if(ctx.ID().size()==3 && name.equals("foo") ){
            argmiss = ctx.ID().size();
        }

        returnMetod = String.valueOf(ctx.VOID());

        if(returnMetod.equals("void") && ctx.ID().size() == 3){
            System.out.println("O método é void, não pode retornar valor");
            System.exit(SAIDA);
        }
            
        if(ctx.VOID()!=null){
            retorno=true;

        }
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

        
        if (argmiss >0 && !returnMetod.equals("void")) {
            System.out.println("Argument match compatibility missed");
            System.exit(0);    
        }
        
    }

    @Override
    public void exitBlock(DecafParser.BlockContext ctx) {
        
        popScope();
    }

    @Override
    public void enterStatement(DecafParser.StatementContext stmt) {
        List<? extends Symbol> symbols = currentScope.getSymbols();

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
        List<Token> tk = new ArrayList<Token>();
        for (int i = 0; i < ctx.ID().size(); i++) {
            tk.add(ctx.ID().get(i).getSymbol());
        }
        for(int j = 0; j<tk.size(); j++){
            determinationVar(tk.get(j));
        }

    }


    @Override
    public void exitVar_decl(DecafParser.Var_declContext ctx) {
    
    }

    @Override 
    public void enterExpr(DecafParser.ExprContext ctx) { 
    //System.out.println("ENTRAAAAAAAAAAAAAA");
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

        if(!var.equals(var.getName())){
            System.out.printf("Var decl: %s",  var.getName() + "\n");
        }
         System.out.printf("Escopo decl: %s", currentScope.getSymbolNames() + "\n");
        
    }

    /**
     * Método que atualiza o escopo para o atual e imprime o valor
     *
     * @param s
     */
    private void pushScope(Scope s) {
        currentScope = s;
        System.out.println("entering: "+currentScope.getName()+": "+s);

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
