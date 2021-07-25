/*
 * 标识符和类型声明的内部表示
 * */
package complier.semantic;

import complier.common.Enum ; 

public class typeIR {
	public integer size = new integer( 0 );
	public Enum.TypeKind Kind ;
	public more More = new more() ;			//	表示数组和域
	public class more {							
		public arrayAttr ArrayAttr = new arrayAttr() ;
		public class arrayAttr {
			public typeIR indexTy ;
			public typeIR elemTy ;
			public int  low , top ;
		}
		public fieldChain body ;
	}
	public void copy( typeIR b ){
		this.size.value = b.size.value ;
		this.Kind = b.Kind ;
		this.More.ArrayAttr.low = b.More.ArrayAttr.low ;
		this.More.ArrayAttr.top = b.More.ArrayAttr.top ;
		this.More.ArrayAttr.elemTy = b.More.ArrayAttr.elemTy ;
		this.More.ArrayAttr.indexTy = b.More.ArrayAttr.indexTy ;
		this.More.body = b.More.body ;
	}
	public boolean equals( typeIR b ) {
		if( this.size.value != b.size.value  ) return false ;
		if( this.Kind != b.Kind ) return false ;
		if( this.More.ArrayAttr.low != b.More.ArrayAttr.low ) return false ;
		if( this.More.ArrayAttr.top != b.More.ArrayAttr.top ) return false ; 
		if( this.More.ArrayAttr.elemTy != b.More.ArrayAttr.elemTy ) return false ;
		if( this.More.ArrayAttr.indexTy != b.More.ArrayAttr.indexTy ) return false ;
		if( this.More.body != b.More.body ) return false ; 
		return true ;
	}
}
