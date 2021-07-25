package complier.common;
import complier.semantic.SymTableNode;; 
      
public  class treeNode {  
          
	int childNum ;
    treeNode[] child = new treeNode[10] ;  
    treeNode father ;
         
    public SymTableNode symtPtr ;
    int line , row ;
    
    int flag ;     // 0 终极符
    
    Enum.nonTerminals NonTerminal ;
    Enum.lexType Terminal ;
    String data ;
 
    int x , y , width , length ;  

    public treeNode() { 
    	childNum = 0 ;  
    }  
          
    public void setChild( treeNode node ) {  
        child[childNum] = new treeNode() ;  
        child[childNum] = node ;  
        childNum ++ ;  
    }  
          
    public void setFather( treeNode node ) {  
        father = new treeNode() ;  
        father = node ;  
    }  
          
    public void setflag( int FLAG ) {  
        flag = FLAG ;  
    }  
          
    public void setData( String DATA ) {  
        data = DATA ;   
    }  
          
    public void setNonTerminal( Enum.nonTerminals nonTerminal ) {  
        NonTerminal = nonTerminal ;  
    }  
          
    public void setTerminal( Enum.lexType terminal ) {  
        Terminal = terminal ;  
    }  
          
    public int getchildNum() {  
        return childNum ;  
    }  
          
    public treeNode getChild( int num ) {  
        return child[num] ;  
    }  
          
    public treeNode getFather() {  
        return father ;  
    }  
          
    public int getflag() {  
        return flag ;   
    }  
          
    public Enum.nonTerminals getNonTerminal() {  
        return NonTerminal ;   
    }  
          
    public Enum.lexType getTerminal() {  
        return Terminal ;   
    }  
          
    public String getData() {  
        return data ;   
    }  
          
    public void setX( int X ) {  
        x = X ;  
    }  
    public int  getX() {  
        return x ;  
    }  
          
    public void setY( int Y ) {  
        y = Y ;  
    }  
    public int  getY() {  
        return y ;   
    }  
          
    public void setWidth( int Width ) {  
        width = Width ;  
    }  
    public int  getWidth() {  
        return width ;  
    }  
          
    public void setLength( int Length ) {  
        length = Length ;  
    }  
    public int  getLength() {  
        return length ;  
    }  
    
    public void setLine( int Line ) {
    	line = Line ;
    }
    public void setRow( int Row ) {
    	row = Row ;
    }
    public int getLine() {
    	return line ;
    }
    public int getRow() {
    	return row ;
    }
}  