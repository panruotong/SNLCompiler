package complier.lex;


import java.io.*    ;
import complier.common.Enum  ;
import complier.common.Enum.lexType;
import complier.common.Node  ;
import complier.common.Error ;
import java.util.*  ;


public class scanner {

    public static int line , row , cur ;
    public static String Buffer ;
    public static Error error = new Error() ;

    public scanner() {
        init() ;
    }

    public void init() {
        line = 1 ;
        row = 1 ;
        cur = 0 ;
        Buffer = "" ;
        error.Flag = false ;
    }

    /************************************/
    /*传入文件地址(string)，读入源文件，返回string */
    /*       功能是将源文件读入Buffer中                             */
    /************************************/
    public static String readTxt( String filePath ) throws Exception {
        FileReader file = new FileReader( filePath ) ;
        BufferedReader reader = new BufferedReader( file ) ;
        String temp = "" ;
        while( reader.ready() ) {
            temp += reader.readLine() ;
            temp += '\n' ;
        }
        return temp ;
    }


    /************************************/
    /*  取得下一个非空字符，并将该字符的下标赋值给cur  */
    /*          如果到了文件结束返回‘\0’       */
    /************************************/
    public static char getNextChar() {
        int   i  ;
        char  ch = '\0' ;
        if( cur == Buffer.length() - 1 ) {
            ch = '\0' ;
            return ch ;
        }
        for( i = cur ; i < Buffer.length() ; i ++ ) {
            if( Buffer.charAt( i ) == '\n' ) {
                line ++ ;
                row = 1 ;
            }
            else if( Buffer.charAt( i ) == ' ' ) row ++ ;
            else if( Buffer.charAt( i ) == '\t' ) row += 4 ;
            else break ;
        }
        if( i != Buffer.length() ) {
            ch = Buffer.charAt( i ) ;
        }
        else ch = '\0' ;
        cur = i ;
        return ch ;
    }


    /***********************************/
    /*	        	识别数字                                          */
    /***********************************/
    public static String isNumber( char ch ){
        String res = "" ;
        int  temp = cur ;
        while( Buffer.charAt( temp ) >= '0' && Buffer.charAt( temp ) <= '9' ){
            res += Buffer.charAt( temp ) ;
            temp ++ ;
            row  ++ ;
        }
        if( ( Buffer.charAt( temp ) >= 'a' && Buffer.charAt( temp ) <= 'z' ) || ( Buffer.charAt( temp ) >= 'A' && Buffer.charAt( temp ) <= 'Z' ) )
            res = null ;
        cur = temp ;
        return res ;
    }


    /***********************************/
    /*			识别标示符或者保留字                                    */
    /***********************************/
    public static String isName( char ch ){
        String res = "" ;
        int  temp = cur ;
        while( temp < Buffer.length() && ( ( Buffer.charAt( temp ) >= '0' && Buffer.charAt( temp ) <= '9' ) || ( Buffer.charAt( temp ) >= 'a' && Buffer.charAt( temp ) <= 'z' )
                || ( Buffer.charAt( temp ) >= 'A' && Buffer.charAt( temp ) <= 'Z' ) ) ) {
            res += Buffer.charAt( temp ) ;
            temp ++ ;
            row  ++ ;
        }
        cur = temp ;
        return res ;
    }


    /***********************************/
    /*			识别具体是哪个保留字		   */
    /***********************************/
    public static lexType recognizeName( String name ){
        switch( name ) {
            case "program"   : return Enum.lexType.PROGRAM   ;
            case "type"      : return Enum.lexType.TYPE      ;
            case "var"       : return Enum.lexType.VAR       ;
            case "procedure" : return Enum.lexType.PROCEDURE ;
            case "begin"     : return Enum.lexType.BEGIN     ;
            case "end"		 : return Enum.lexType.END       ;
            case "array"	 : return Enum.lexType.ARRAY	 ;
            case "of"		 : return Enum.lexType.OF 		 ;
            case "record"    : return Enum.lexType.RECORD    ;
            case "if"		 : return Enum.lexType.IF        ;
            case "then"		 : return Enum.lexType.THEN		 ;
            case "else"		 : return Enum.lexType.ELSE 	 ;
            case "fi"		 : return Enum.lexType.FI		 ;
            case "while"	 : return Enum.lexType.WHILE	 ;
            case "do"		 : return Enum.lexType.DO		 ;
            case "endwh"	 : return Enum.lexType.ENDWH	 ;
            case "read"		 : return Enum.lexType.READ		 ;
            case "write"	 : return Enum.lexType.WRITE	 ;
            case "return"	 : return Enum.lexType.RETURN	 ;
            case "integer"	 : return Enum.lexType.INTEGER	 ;
            case "char"		 : return Enum.lexType.CHAR		 ;
            default          : return Enum.lexType.ID		 ;
        }
    }


    /***********************************/
    /*        识别该字符具体是哪个符号                                  */
    /***********************************/
    public static lexType recognizeSymbol( char symbol ) {
        switch( symbol ) {
            case '+'		 : return Enum.lexType.PLUS		 ;
            case '-'		 : return Enum.lexType.MINUS	 ;
            case '*'		 : return Enum.lexType.TIMES	 ;
            case '/'		 : return Enum.lexType.OVER		 ;
            case '('		 : return Enum.lexType.LPAREN	 ;
            case ')'		 : return Enum.lexType.RPAREN	 ;
            case '.'		 : return Enum.lexType.DOT		 ;
            case '['		 : return Enum.lexType.LMIDPAREN ;
            case ']'		 : return Enum.lexType.RMIDPAREN ;
            case ';'		 : return Enum.lexType.SEMI		 ;
            case ':'		 : return Enum.lexType.COLON	 ;
            case ','		 : return Enum.lexType.COMMA	 ;
            case '<'		 : return Enum.lexType.LT		 ;
            case '='		 : return Enum.lexType.EQ		 ;
            case '\''		 : return Enum.lexType.CHARC	 ;
            case '\0' 		 : return Enum.lexType.ENDFILE	 ;
        }
        return null ;
    }


    /************************************/
    /*         得到下一个 token             */
    /************************************/
    public static Node getNextToken() {
        Node now = new Node() ;
        char c ;
        c = getNextChar() ;
        if( c == '\0' ) return null ;
        now.setLine( line ) ;
        now.setRow(  row  ) ;
        if( c >= '0' && c <= '9' ){
            String temp = isNumber( c ) ;
            if( temp != null ) {
                now.setData( temp ) ;
                now.setType( Enum.lexType.INTC ) ;
            }
            else {
                now = null ;
                Error.setError( line , row , 1 ) ;
            }
        }
        else if( ( c >= 'a' && c<= 'z' ) || ( c >= 'A' && c <= 'Z' ) ){
            String temp = isName( c ) ;
            if( temp != null ) now.setData( temp ) ;
            else now = null ;
            now.setType( recognizeName( temp ) ) ;
        }
        else if( c == '{' ) {
            int  i ;
            int  num = 1 ;
            for( i = cur + 1 ; i < Buffer.length() ; i ++ ) {
                if( Buffer.charAt( i ) == '{' ) num ++ ;
                else if( Buffer.charAt( i ) == '}' ) num -- ;
                if( Buffer.charAt( i ) == '\n' ) {
                    line ++ ;
                    row = 1 ;
                }
                else if( Buffer.charAt( i ) == '\t' ) row += 4 ;
                else row ++ ;
                if( num == 0 ) break ;
            }
            if( num != 0 ) {
                cur = i ;
                error.setError( line , row , 1 ) ;
            }
            else cur = i + 1 ;
            now.setData( null ) ;
        }
        else{
            if( c == ':' && Buffer.charAt( cur + 1 ) == '=' ) {
                now.setData( ":=" ) ;
                now.setType( Enum.lexType.ASSIGN ) ;
                cur += 2 ; row += 2 ;
            }
            else if( c == '.' && Buffer.charAt( cur + 1 ) == '.' ) {
                now.setData( ".." ) ;
                now.setType( Enum.lexType.UNDERANGE ) ;
                cur += 2 ; row += 2 ;
            }
            else {
                String temp = new String() ;
                temp += c ;
                now.setType( recognizeSymbol( c ) ) ;
                now.setData( temp ) ;
                if( now.getType() == null ) {
                    Error.setError( line , row , 1) ;
                }
                cur ++ ; row ++ ;
            }
        }
        return now ;
    }


    /************************************/
    /*        用String传入源文件地址                            */
    /*        返回一个链表，就是token链                       */
    /************************************/
    public ArrayList< Node > getTokenList( String filePath ) throws Exception {
        init() ;
        Buffer = filePath  ;
        ArrayList< Node > TokenList = new ArrayList < Node > () ;
        while( true ) {
            Node temp = new Node () ;
            temp = getNextToken() ;
            if( temp == null ){
                Node tmp = new Node() ;
                tmp.setType( lexType.ENDFILE ) ;
                tmp.setLine( line + 1 ) ;
                tmp.setRow( 0 ) ;
                TokenList.add( tmp ) ;
                break ;
            }
            if( temp.getData() == null ) continue ;
            if( Error.getFlag() ) break ;
            TokenList.add( temp ) ;
        }
        return TokenList ;
    }
    public String lexString( String filePath ) throws Exception {
        String output = "" ;
        init() ;
        ArrayList< Node > TokenList;
        TokenList = getTokenList( filePath ) ;
        if( error.getFlag() == true ) {
            int line ;
            line = error.getLine();
            output = "行:" + line +  "			" +  "词法错误" ;
        }
        else {
            for( int i = 0 ; i < TokenList.size() ; i ++ ) {
                output += TokenList.get(i).getLine()+ "	" +TokenList.get(i).getData() + "	" + TokenList.get(i).getType() + "\n" ;
            }
        }
        return output ;
    }
}
