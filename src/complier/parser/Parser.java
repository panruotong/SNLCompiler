package complier.parser;

import java.util.*;

import  complier.lex.scanner  ;
import  complier.common.* ;
import  complier.common.Enum  ;
import  complier.common.Error ;

public class Parser {

    static SNLproduct Product = new SNLproduct() ;
    static SNLpredict Predict = new SNLpredict() ;
    static scanner Scanner = new scanner() ;
    static ArrayList < Node > TokenList = new ArrayList< Node > () ;
    static int cur ;
    static String output ;
    static treeNode root = new treeNode() ;
    public static Error error = new Error() ;

    public Parser() {
        cur = 0 ;
    }


    /*****************/
    /*递归地匹配一个非终极符*/
    /*****************/
    public static treeNode match( Enum.nonTerminals NonTerminal , treeNode father ) {

        int  i , j , choose = -1 ;
        treeNode root = new treeNode() ;
        Enum.nonTerminals temp ;
        Enum.lexType curLex = TokenList.get(cur).getType() ;

        root.setflag( 1 ) ;
        root.setNonTerminal( NonTerminal ) ;
        root.setFather( father ) ;

        root.setLine( TokenList.get(cur).getLine() ) ;
        root.setRow( TokenList.get(cur).getRow() ) ;

        for( i = 1 ; i <= 104 ; i ++ ) {
            int flag = 0 ;
            temp = Product.product[i].getHead() ;
            for( j = 0 ; j < Predict.predict[i].getPredictNum() ; j ++ ) {
                if( curLex == Predict.predict[i].getPredict( j ) ) {
                    flag = 1 ;
                    break ;
                }
            }
            if(  flag == 1 && temp == NonTerminal ) {
                choose = i ;
                break ;
            }
        }

        if( choose == -1 ) {
            error.setError( TokenList.get(cur).getLine() , TokenList.get(cur).getRow() , 2 ) ;
            return null ;
        }
        else {
            for( i = 0 ; i < Product.product[choose].getproductNum() ; i ++ ) {
                if( Product.product[choose].getflag( i ) == 0 ) {
                    if(!terminalMatch(Product.product[choose].getProductTerminal(i),TokenList.get(cur).getType())){
                        error.setError( TokenList.get(cur).getLine() , TokenList.get(cur).getRow() , 2 ) ;
                        return null ;
                    }
                    treeNode leaf = new treeNode() ;
                    leaf.setFather( root ) ;
                    leaf.setflag( 0 ) ;
                    leaf.setTerminal( Product.product[choose].getProductTerminal( i ) ) ;
                    leaf.setData( TokenList.get( cur ).getData() ) ;
                    leaf.setLine( TokenList.get(cur).getLine() ) ;
                    leaf.setRow( TokenList.get(cur).getRow() ) ;
                    root.setChild( leaf ) ;
                    cur ++ ;
                }
                else {
                    treeNode child ;
                    Enum.nonTerminals NonTerminals = Product.product[choose].getProductNonterminal(i) ;
                    child = match( NonTerminals , root ) ;
                    root.setChild( child ) ;
                }
            }
        }

        return root ;
    }

    public static boolean terminalMatch (Enum.lexType terminal, Enum.lexType curLex){
        if(terminal == curLex)
            return true;
        else
            return false;
    }
    /*********************/
    /*得到一个语法分析树，返回树根*/
    /*********************/
    public treeNode getTree( String filePath ) throws Exception {

        TokenList = Scanner.getTokenList( filePath ) ;
        if( Scanner.error.getFlag() == true ) {
            if( Scanner.error.getErrorType() == 1 )
                output = Scanner.lexString( filePath ) ;
            return null ;
        }
        else {
            treeNode root = new treeNode() ;
            cur = 0 ;
            root = match( Enum.nonTerminals.Program , root ) ;
            if( TokenList.get(cur).getType() != Enum.lexType.ENDFILE ){
                error.setError( TokenList.get(cur).getLine() , TokenList.get(cur).getRow() , 2 ) ;
            }
            if( error.getFlag() == true ) {
                int line ;
                line = error.getLine();
                output = "行:" + line + "			" +  "语法错误" ;
                return null ;
            }
            return root ;
        }
    }

    public String getOutput( String filePath ) throws Exception {
        output = "" ;
        error.Flag = false ;
        root = getTree( filePath ) ;
        return output ;
    }

    public void drawTree() {
        if( root != null ) drawTree.drawtree(root) ;
    }
}