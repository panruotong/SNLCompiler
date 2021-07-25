package complier.semantic;

import java.util.Stack;

public class Scope {
	public Stack <ScopeNode> scope = new Stack <ScopeNode>() ;
	
	public SymTableNode FindID( String idname , boolean ntype ) {
		/***********************/
		/*从符号表栈中查找已经声明的标示符*/
		/****ntype=1时遍历所有层****/
		/****ntype=0时遍历当前层****/
		/***********************/
		ScopeNode ptr = scope.peek() ;
		if( scope.empty() ) return null ;
		
		while( ptr != null ) {
			SymTableNode nptr = new SymTableNode() ;
			nptr = ptr.front ;
			while( nptr != null ) {
				if( nptr.name == null ) {}
				else if( nptr.name.equals(idname) ) {
					return nptr ;
				}
				if( nptr.EOFL == true ) break ; 
				nptr = nptr.next ;
			}
			if( ntype == true ) ptr = ptr.parent ;
			else break ;
		}
		return null;
	}
	
	public SymTableNode GetRear() {
		SymTableNode p = scope.peek().front ;
		while( p != null ) {
			if( p.EOFL == true || p.next == null ) return p ;
			p = p.next ; 
		}
		return null ;
	}

	public boolean newLayer( SymTableNode ptr ) {
		/***************/
		/******新建一层****/
		/***************/
		ScopeNode nptr = new ScopeNode() ;
		nptr.front = ptr ;
		if( scope.empty() ) nptr.parent = null ;
		else nptr.parent = scope.peek() ;
		scope.push( nptr ) ;
		return true ;
	}

	public boolean DropLayer(){
		/***************/
		/******删除一层****/
		/***************/
		if( scope.empty() ) return false ;
		scope.pop() ;
		return true ;
	}
	
}
