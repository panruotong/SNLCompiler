    package complier.common;  
      
    public class Error {  
        public static int  Line ;  
        public static int  Row  ;
        public static int  ErrorType  ;
        public static boolean  Flag ;
        public static String Type ;
        public Error(){  
            Line = 0 ;   
            Row  = 0 ;  
            ErrorType = 0 ;   
            Flag = false ;  
        }  
        public static void printError() {  
            if( ErrorType == 1 ) System.out.print( "词法错误" ) ;
            else if( ErrorType == 2 ) System.out.print( "语法错误" ) ;
            else if( ErrorType == 3 ) System.out.print( "语义错误" ) ;
            System.out.println( "  行:" + Line) ;
        }  
        public static void setLine( int line ) {  
            Line = line ;   
        }  
        public static void setRow( int row ) {  
            Row = row ;   
        }  
        public static void setErrorType( int type ) {  
            ErrorType = type ;  
        }  
        public static void setFlag( boolean flag ) {  
            Flag = flag ;   
        }  
        public static int getLine() {  
            return Line ;  
        }  
        public static int getRow()  {  
            return Row  ;  
        }  
        public static int getErrorType() {  
            return ErrorType ;  
        }  
        public static boolean getFlag() {  
            return Flag ;   
        }  
        public static void setError( int line , int row , int type ) {  
            Line = line ;   
            Row  = row  ;  
            ErrorType = type ;  
            Flag = true    ;  
        }  
    }  