package complier.semantic;

import java.util.ArrayList;

import complier.parser.Parser;

import complier.common.treeNode ;
import complier.common.Enum ; 
import complier.common.Error ;

public class semantic {
	private treeNode root ;
	public SymTable symTable = new SymTable() ;
	public Scope myScope = new Scope() ;
	public ArrayList< semError > ErrorList = new ArrayList< semError >() ; 
	public int ErrorNum ;
	public final int OFFSET = 0 ;
	public Parser parser = new Parser() ;
	public static String output ;
	
	public static typeIR IntTy , CharTy , BoolTy ;
	
	private void initTy() {
		IntTy = new typeIR() ;
		CharTy = new typeIR() ;
		BoolTy = new typeIR() ;
		
		IntTy.Kind = Enum.TypeKind.intTy ;
		IntTy.size.value = 2 ;
		
		CharTy.Kind = Enum.TypeKind.charTy ;
		CharTy.size.value = 1 ;
		
		BoolTy.Kind = Enum.TypeKind.boolTy ;
		BoolTy.size.value = 1 ;
	}

	private void error( String str , int line , int row ) {
		if( Error.getFlag() == false ) {
			Error.setError( line , row , 2 ) ;
		}
		ErrorNum ++ ;
		
		semError Error = new semError() ;
		Error.line = line  ;
		Error.row = row ;
		Error.type = str ;
		
		ErrorList.add( Error ) ;
	}
	
	public boolean OutErrorCmd() {
		if( ErrorNum == 0 ) return false ;
		for( int i = 0 ; i < ErrorList.size() ; i ++ ){
			output += "行： " + ErrorList.get(i).line + "		" + ErrorList.get(i).type + "\n" ;
		}
		return true ;
	}
	
	private treeNode Search( treeNode Root , treeNode ptr , int ntype ) {
		/*********************/
		/*ntype=0时，仅遍历同一层**/
		/*ntype=1时，仅遍历最左分支*/
		/*ntype=2时，遍历所有子树**/
		/*ntype=3时，遍历所有子树**/
		/*********************/
		
		if( Root == null ) return null ; 
		
		int  i ;
		if( Root.getflag() == ptr.getflag() ) {
			if( Root.getflag() == 0 && Root.getTerminal().equals( ptr.getTerminal() )  ) return Root ;
			if( Root.getflag() == 1 && Root.getNonTerminal().equals( ptr.getNonTerminal() )  ) return Root ;
		}
		if( ntype == 0 ) {
			for( i = 0 ; i < Root.getchildNum() ; i ++ ) {
				treeNode temp = Root.getChild( i ) ;
				if( temp.getflag() == 0 && temp.getTerminal().equals(ptr.getTerminal())) return temp ;
				if( temp.getflag() == 1 && temp.getNonTerminal().equals(ptr.getNonTerminal())) return temp ;
			}
		}
		else if( ntype == 1 ) {
			return Search( Root.getChild(0) , ptr , ntype ) ;
		}
		else if( ntype == 2 ) {
			for( i = 0 ; i < Root.getchildNum() ; i ++ ) {
				treeNode temp = Search( Root.getChild(i) , ptr , ntype );
				if( temp != null ) return temp ;
			}
		}
		else if( ntype == 3 ) {
			for( i = Root.getchildNum() - 1 ; i >= 0 ; i -- ) {
				treeNode temp = Search( Root.getChild(i) , ptr , ntype );
				if( temp != null ) return temp ;
			}
		}
		return null ;
	}
	
	public void OutToCmd() {
		output += symTable.OutToCmd() ;
	}
	
	public SymTable OutSymTable() {
		return symTable ;
	}
	
	public String SemScanner( String filePath ) throws Exception {
		output = "" ;
		root = parser.getTree( filePath ) ;
		output += parser.getOutput( filePath ) ;
		if( root == null ) return output ;
		initTy() ;
		ErrorNum = 0 ;
		program( root ) ;
		OutErrorCmd() ;
		if( ErrorList.size() == 0 ) OutToCmd() ;
		return output ;
	}
	
	private void program( treeNode Root ) {
		treeNode p ;
		treeNode tar = new treeNode() ;
		tar.setflag( 1 ) ; 	tar.setNonTerminal( Enum.nonTerminals.DeclarePart ) ;
		
		p = Search( Root , tar , 0 ) ;
		if( p != null ) {
			SymTableNode ptr = new SymTableNode() ;
			ptr.EOFL = true ;
			myScope.newLayer( null ) ;
			integer offset = new integer( OFFSET ) ;
			declarePart( p , 0 , offset ) ;
		}
		
		tar.setNonTerminal( Enum.nonTerminals.ProgramBody ) ;
		p = Search( Root , tar , 0 ) ;
		if( p != null ) {
			programBody( p ) ;
		}
	}
	
	private void declarePart( treeNode ptr , int layer , integer offset ) {
		treeNode p ; 
		treeNode tar = new treeNode() ;
		tar.setflag( 1 ) ;  tar.setNonTerminal( Enum.nonTerminals.TypeDecpart ) ;
		
		p = Search( ptr , tar , 1 ) ;
		if( p != null ) typeDecpart( p , layer ) ;
		
		tar.setNonTerminal( Enum.nonTerminals.VarDecpart ) ;
		
		p = Search( ptr , tar , 0 ) ;
		if( p != null ) varDecpart( p , layer , offset ) ;
		
		ptr.symtPtr = myScope.scope.peek().front ;
		
		tar.setNonTerminal( Enum.nonTerminals.ProcDecpart ) ;
		p = Search( ptr , tar , 0 ) ;
		if( p != null ) {
			tar.setNonTerminal( Enum.nonTerminals.ProcDecPart ) ;
			treeNode temp ;
			temp = Search( p , tar , 2 ) ;
			if( temp != null ) procDecpart( p , layer + 1 ) ;
		}
	}
	
	private void programBody( treeNode ptr ) {
		treeNode p ; 
		treeNode tar = new treeNode() ;
		tar.setflag( 1 ) ;  tar.setNonTerminal( Enum.nonTerminals.StmList ) ;
		
		p = Search( ptr , tar , 0 ) ;
		
		if( p != null ) stmList( p ) ;
	}
	
	/***************/
	/*声明部分的各分块部分*/
	/***************/
	private void typeDecpart( treeNode ptr , int layer ) {
		treeNode p ; 
		treeNode tar = new treeNode() ;
		
		tar.setflag( 1 ) ; tar.setNonTerminal( Enum.nonTerminals.TypeDecList ) ;
		
		if( ptr.getchildNum() > 0 && ptr.getChild(0).getchildNum() == 0 ) return ;
		
		p = Search( ptr , tar , 2 ) ;
		
		if( p != null ) typeDecList( p , layer ) ;
	}
	
	private void varDecpart( treeNode ptr , int layer , integer offset ) {
		treeNode p ; 
		treeNode tar = new treeNode() ;
		
		tar.setflag( 1 ) ;  tar.setNonTerminal( Enum.nonTerminals.VarDecList ) ;
		
		if( ptr.getchildNum() != 0 && ptr.getChild(0).getchildNum() == 0 ) return ;
		
		p = Search( ptr , tar , 2 ) ;
		
		if( p != null ) varDecList( p , layer , offset ) ;
		
	}
	
	private void procDecpart( treeNode ptr , int layer ) {
		treeNode p ;
		treeNode tar = new treeNode() ;
		
		tar.setflag( 1 ) ;  tar.setNonTerminal( Enum.nonTerminals.ProcDec ) ;
		
		p = Search( ptr , tar , 1 ) ;
		
		if( p != null ) {
			procDec( p , layer , false ) ;
			tar.setNonTerminal( Enum.nonTerminals.ProcDecMore ) ;
			p = Search( p , tar , 0 ) ;
			if( p != null ) {
				tar.setNonTerminal( Enum.nonTerminals.ProcDec ) ;
				p = Search( p , tar , 1 ) ;
				if( p != null ) procDec( p , layer , true ) ;
			}
		}
	}
	/**************/
	/*****类型声明****/
	/**************/
	private void typeDecList( treeNode ptr , int layer ) {
		treeNode q ; 
		treeNode tar = new treeNode() ;
		
		if( ptr.getchildNum() != 0 ) {
			SymTableNode p = new SymTableNode() ; 
			p.name = ptr.getChild(0).getChild(0).getData() ;
			p.attrIR.kind = Enum.IdKind.typekind ;
			p.attrIR.level = layer ;
			p.next = new SymTableNode() ; 
			
			tar.setflag( 1 ) ;  tar.setNonTerminal( Enum.nonTerminals.TypeDef ) ;
			q = Search( ptr , tar , 0 ) ;
			if( q != null ) {
				typeDef( q , p.attrIR.idtype , layer ) ;
			}
			if( p.attrIR.idtype != null ) {	
				symTable.insertNode( p ) ;
				if( myScope.scope.peek().front == null ) {
					ptr.symtPtr = p ;
					myScope.scope.peek().front = p ; 
				}
			}
			else {
				String str ;
				str = "语义错误：		类型标识符" + p.name + "未定义" ;
				str = "语义错误：  类型标识符"+ p.name + "未定义";
				error( str , ptr.getLine() , ptr.getRow() ) ;
				p = null ; 
			}
			
		}
		
		tar.setNonTerminal( Enum.nonTerminals.TypeDecMore ) ;
		q = Search( ptr , tar , 0 ) ;
		if( q != null ) {
			tar.setNonTerminal( Enum.nonTerminals.TypeDecList ) ;
			q = Search( q , tar , 1 ) ;
			if( q != null ) typeDecList( q , layer ) ;
		}
	}
	
	private void typeDef( treeNode ptr , typeIR tIR , int layer ) {
		if( ptr.getchildNum() == 0 ) {
			tIR = null ; 
			return ;
		}
		if( ptr.getChild(0).getflag() == 1 ) {
			if( ptr.getChild(0).getNonTerminal().equals( Enum.nonTerminals.BaseType ) ) {
				if( ptr.getChild(0).getChild(0).getData().equals( "integer" ) ) tIR.copy( IntTy ) ;
				else if( ptr.getChild(0).getChild(0).getData().equals( "char" ) ) tIR.copy( CharTy ) ;
			}
			else if( ptr.getChild(0).getNonTerminal().equals( Enum.nonTerminals.StructureType ) ) 
				structureType( ptr.getChild(0) , tIR , layer ) ;
		}
		else if( ptr.getChild(0).getTerminal().equals( Enum.lexType.ID ) ) {
			SymTableNode p = myScope.FindID( ptr.getChild(0).getData() , true ) ;
			
			if( p == null ) {
				String str ; 
				str = "语义错误：		类型标识符" + ptr.getChild(0).getData() + "未定义" ;
				error( str , ptr.getChild(0).getLine() , ptr.getChild(0).getRow() ) ;
				tIR = null ; 
			}
			else {
				if( p.attrIR.kind != Enum.IdKind.typekind ) {
					String str ;
					str = "语义错误：		" + ptr.getChild(0).getData() + "非类型标识符" ;
					error( str , ptr.getChild(0).getLine() , ptr.getChild(0).getRow() ) ;
					tIR = null ;
				}
				else tIR.copy( p.attrIR.idtype ) ;
			}
		}
	} 
	
	private void structureType( treeNode ptr , typeIR tIR , int layer ) {
		treeNode p ; 
		treeNode tar = new treeNode() ;
		
		tar.setflag(1) ;  tar.setNonTerminal( Enum.nonTerminals.ArrayType ) ;
		p = Search( ptr , tar , 0 ) ;
		if( p != null ) {
			arrayType( p , tIR , layer ) ;
			return ;
		}
		
		tar.setNonTerminal( Enum.nonTerminals.RecType ) ;
		p = Search( ptr , tar , 0 ) ;
		if( p != null ) {
			recType( p , tIR , layer ) ;
			return ;
		}
		tIR = null ; 
	}
	
	private void arrayType( treeNode ptr , typeIR tIR , int layer ) {
		tIR.Kind = Enum.TypeKind.arrayTy ;
		
		treeNode p = ptr.getChild(2) ;
		if( p.getNonTerminal().equals( Enum.nonTerminals.Low) ) 
			tIR.More.ArrayAttr.low = Integer.parseInt( p.getChild(0).getData() ) ;
		
		p = ptr.getChild(4) ;
		if( p.getNonTerminal().equals( Enum.nonTerminals.Top) ) 
			tIR.More.ArrayAttr.top = Integer.parseInt( p.getChild(0).getData() ) ;
		
		tIR.More.ArrayAttr.indexTy = IntTy ; 
		
		p = ptr.getChild(7) ;
		if( p.getChild(0).getTerminal().equals( Enum.lexType.INTEGER ) ) 
			tIR.More.ArrayAttr.elemTy = IntTy ;
		else tIR.More.ArrayAttr.elemTy = CharTy ;
		
		if( tIR.More.ArrayAttr.top <= tIR.More.ArrayAttr.low ) {
			String str = "语义错误：		数组上界小于了下界" ;
			error( str , ptr.getChild(2).getLine() , ptr.getChild(2).getRow() ) ;
			return ;
		}
		
		tIR.size.value = tIR.More.ArrayAttr.top - tIR.More.ArrayAttr.low + 1 ;
		tIR.size.value *= tIR.More.ArrayAttr.elemTy.size.value ;
	}
	
	private void recType( treeNode ptr , typeIR tIR , int layer ) {
		tIR.Kind = Enum.TypeKind.fieldTy ;
		tIR.size.value = 0 ;
		
		treeNode p ;
		treeNode tar = new treeNode() ;
		tar.setflag( 1 ) ;  tar.setNonTerminal( Enum.nonTerminals.FieldDecList ) ;
		
		p = Search( ptr , tar , 0 ) ;
		if( p != null ) fieldDecList( ptr , tIR.More.body , tIR.size , layer ) ;
	}
	
	private void fieldDecList( treeNode ptr , fieldChain body , integer size , int layer ) {
		treeNode p ; 
		treeNode tar = new treeNode() ;
		tar.setflag( 1 ) ; tar.setNonTerminal( Enum.nonTerminals.BaseType ) ;
		
		p = Search( ptr , tar , 1 ) ;
		if( p != null ) {
			if( p.getChild(0).getTerminal().equals( Enum.lexType.INTEGER ) ) 
				idList( p.getFather().getChild(1) , p.getFather().getChild(1) , body , layer , size , IntTy ) ;
			else if( p.getChild(0).getTerminal().equals( Enum.lexType.CHAR ) )
				idList( p.getFather().getChild(1) , p.getFather().getChild(1) , body , layer , size , CharTy ) ;
			return ;
		}
		
		tar.setNonTerminal( Enum.nonTerminals.ArrayType ) ;
		p = Search( ptr , tar , 1 ) ;
		if( p != null ) {
			typeIR tIR = new typeIR() ;
			arrayType( p , tIR , layer ) ;
			idList( p.getFather().getChild(1) , p.getFather().getChild(1) , body , layer , size , tIR ) ;
		}
	}
	
	private void idList( treeNode ptr , treeNode parent , fieldChain body , int layer , integer size , typeIR tIR ) {
		body = new fieldChain() ;
		treeNode p ; 
		body.idname = ptr.getChild(0).getData() ;
		body.offset.value = size.value ;
		body.unitType = tIR ;
		body.next = new fieldChain() ;
		size.value += tIR.size.value ;
		
		treeNode tar = new treeNode() ;
		tar.setflag( 1 ) ;  tar.setNonTerminal( Enum.nonTerminals.IdMore ) ;
		p = Search( ptr , tar , 0 ) ;
		if( p != null ) {
			idList( ptr.getChild(1).getChild(1) , parent , body.next , layer , size , tIR ) ;
			return ;
		}
		
		tar.setNonTerminal( Enum.nonTerminals.FieldDecList ) ;
		p = Search( parent.getFather().getChild(4) , tar , 0 ) ;
		if( p != null ) {
			fieldDecList( p , body.next , size , layer ) ;
		}
	}
	
	/************/
	/****变量声明***/
	/************/
	
	private void varDecList( treeNode ptr , int layer , integer offset ) {
		treeNode p ; 
		treeNode tar = new treeNode() ;
		tar.setflag( 1 ) ; tar.setNonTerminal( Enum.nonTerminals.TypeDef ) ;
		
		p = Search( ptr , tar , 0 ) ;
		if( p != null ) {
			typeIR tIR = new typeIR() ; 
			typeDef( p , tIR , layer ) ;
			if( tIR != null ) {
				varIdList( p.getFather().getChild(1) , layer , offset , tIR ) ;
			}
		}
		
		tar.setNonTerminal( Enum.nonTerminals.VarDecMore ) ;
		p = Search( ptr , tar , 0 ) ;
		if( p != null ) {
			tar.setNonTerminal( Enum.nonTerminals.VarDecList ) ;
			p = Search( p , tar , 1 ) ;
			if( p != null ) {
				varDecList( p , layer , offset ) ;
			}
		}
	}
	
	private void varIdList( treeNode ptr , int layer , integer offset , typeIR tIR ) {
		SymTableNode p = new SymTableNode() ;
		p.name = ptr.getChild(0).getData() ;
		p.next = new SymTableNode() ;
		p.attrIR.kind = Enum.IdKind.varkind ;
		p.attrIR.idtype.copy( tIR ) ;
		p.attrIR.More.VarAttr.offset.value = offset.value ;
		p.attrIR.level = layer ;
		p.attrIR.More.VarAttr.access = Enum.AccessKind.dir ;
		if( myScope.FindID( p.name , false ) == null ) {
			System.out.println( p.name ) ;
			if( p.attrIR.idtype != null ) {
				offset.value += p.attrIR.idtype.size.value ;
				symTable.insertNode( p ) ;
				if( myScope.scope.peek().front == null ) {
					myScope.scope.peek().front = p ; 
					ptr.symtPtr = p ; 
				}
			}
			else return ;
		}
		else {
			error( "语义错误：		变量" + p.name + "重复定义" , ptr.getLine() , ptr.getRow() ) ;
			return ;
		}
		
		treeNode q ; 
		treeNode tar = new treeNode() ;
		tar.setflag( 1 ) ; tar.setNonTerminal( Enum.nonTerminals.VarIdMore ) ;
		q = Search( ptr , tar , 0 ) ;
		if( q != null ) {
			tar.setNonTerminal( Enum.nonTerminals.VarIdList ) ;
			q = Search( q , tar , 0 ) ;
			if( q != null ) {
				varIdList( q , layer , offset , tIR ) ;
			}
		}
	}
	
	/************/
	/**过程声明部分**/
	/************/
	
	private void procDec( treeNode ptr , int layer , boolean mid  ) {
		treeNode p ; 
		SymTableNode sym = new SymTableNode() ;
		ptr.getChild(1).getChild(0).symtPtr = sym ;
		
		sym.name = ptr.getChild(1).getChild(0).getData() ;
		sym.next = new SymTableNode() ; 
		sym.attrIR.idtype = null ;
		sym.attrIR.kind = Enum.IdKind.prockind ;
		sym.attrIR.level = layer;
		sym.attrIR.More.ProcAttr.size.value = 0 ;
		sym.EOFL = true ;
		
		if( myScope.scope.peek().front == null ) {
			myScope.scope.peek().front = sym ;
			symTable.insertNode( sym ) ;
		}
		if( mid == false ) {
			symTable.insertNode( sym ) ;
		}
		else if( mid == true ) {
			SymTableNode s0 = myScope.GetRear() ;
			s0.EOFL = false ; 
			symTable.insertMid( s0 , sym ) ;
		}
		
		myScope.newLayer( null ) ;
		p = ptr.getChild(3) ;
		paramList( p , layer , sym.attrIR.More.ProcAttr.size , sym.attrIR.More.ProcAttr.param ) ;
		p = ptr.getChild(6).getChild(0) ;
		declarePart( p , layer , sym.attrIR.More.ProcAttr.size ) ;
		p = ptr.getChild(7).getChild(0) ; 
		programBody( p ) ;
		
		myScope.DropLayer() ;
	} 
	
	private void paramList( treeNode ptr , int layer , integer size , ParamTable param ) {
		treeNode p ; 
		treeNode tar = new treeNode() ;
		tar.setflag( 1 ) ; tar.setNonTerminal( Enum.nonTerminals.ParamDecList ) ;
		p = Search( ptr , tar , 1 ) ;
		if( p != null ) {
			paramDecList( p , layer , size , param ) ;
		}
	}
	
	private void paramDecList( treeNode ptr , int layer , integer size , ParamTable param ) {
		treeNode p ;
		treeNode tar = new treeNode() ; 
		tar.setflag( 1 ) ;
		
		if( ptr.getchildNum() != 0 && ptr.getChild(0).getNonTerminal().equals( Enum.nonTerminals.Param ) ) {
			typeIR tIR = new typeIR() ;
			
			tar.setNonTerminal( Enum.nonTerminals.TypeDef ) ;
			p = Search( ptr , tar , 1 ) ;
			if( p != null ) {
				typeDef( p , tIR , layer ) ;
				if( tIR != null ) {
					p = p.getFather().getChild(1) ;
					formList( p , layer , size , param , tIR , false ) ;
				}
			}
			else {
				tar.setflag( 0 ) ; tar.setTerminal( Enum.lexType.VAR ) ;
				p = Search( ptr , tar , 1 ) ; 
				if( p != null ) {
					if( p.getFather().getChild(1).getNonTerminal().equals( Enum.nonTerminals.TypeDef ) ) {
						typeDef( p.getFather().getChild(1) , tIR , layer ) ;
						tar.setflag( 1 ) ; tar.setNonTerminal( Enum.nonTerminals.FormList ) ;
						p = Search( p.getFather().getChild(1) , tar , 1 ) ;
						if( tIR != null && p != null ) formList( p , layer , size , param , tIR , true ) ;
					}
				}
			}
		}
		
		tar.setNonTerminal( Enum.nonTerminals.ParamMore ) ;
		p = Search( ptr , tar , 0 ) ;
		if( p != null ) {
			tar.setNonTerminal( Enum.nonTerminals.ParamDecList ) ;
			p = Search( p , tar , 0 ) ;
			if( p != null ) {
				if( param != null ) {
					ParamTable pm = param ;
					while( pm.next != null ) pm = pm.next ; 
					paramDecList( p , layer , size , pm.next ) ;
				}
				else paramDecList( p , layer , size , param ) ; 
			}
		}
	}
	
	private void formList( treeNode ptr , int layer , integer size , ParamTable param , typeIR tIR , boolean ntype ) {
		treeNode p  ; 
		treeNode tar = new treeNode() ;
		
		SymTableNode sym = new SymTableNode() ;
		sym.name = ptr.getChild(0).getData() ;
		sym.next = new SymTableNode() ;
		sym.attrIR.idtype.copy( tIR ) ;
		sym.attrIR.kind = Enum.IdKind.varkind ;
		if( ntype == true ) sym.attrIR.More.VarAttr.access = Enum.AccessKind.indir ;
		else sym.attrIR.More.VarAttr.access = Enum.AccessKind.dir ; 
		sym.attrIR.level = layer ;
		sym.attrIR.More.VarAttr.offset.value = size.value ; 
		
		if( myScope.FindID( sym.name , false ) != null ) {
			String str = "语义错误：		参数标识符" + sym.name + "重复定义!" ;
			error( str , ptr.getLine() , ptr.getRow() ) ;
			sym = null ;
			tar.setflag( 1 ) ; tar.setNonTerminal( Enum.nonTerminals.FormList ) ;
			p = Search( ptr.getChild(1) , tar , 2 ) ;
			if( p != null ) formList( p , layer , size , param , tIR , ntype ) ;
		}
		else {
			size.value += tIR.size.value ;
			symTable.insertNode( sym ) ;
			param.type.copy( tIR ) ; 
			param.symPtr = sym ;
			if( myScope.scope.peek().front == null ) myScope.scope.peek().front = sym ;
			
			tar.setflag( 1 ) ; tar.setNonTerminal( Enum.nonTerminals.FormList ) ;
			p = Search( ptr.getChild(1) , tar , 2 ) ;
			if( p != null ) {
				param.next = new ParamTable() ;
				formList( p , layer , size , param.next , tIR , ntype ) ;
			}
		}
	}
	
	/***********/
	/**程序体部分**/
	/***********/
	
	private void stmList( treeNode ptr ) {
		treeNode p ; 
		treeNode tar = new treeNode() ;
		if( ptr == null ) return ;
		boolean flag = false ; 	
		tar.setflag( 1 ) ; tar.setNonTerminal( Enum.nonTerminals.ConditionalStm ) ;
		
		p = Search( ptr , tar , 1 ) ;
		if( p != null ) {
			flag = true ;
			conditionalStm( p ) ;
		}
		
		if( flag == false ) {
			tar.setNonTerminal( Enum.nonTerminals.LoopStm ) ;
			p = Search( ptr , tar , 1 ) ;
			if( p != null ) {
				flag = true ;
				loopStm( p ) ;
			}
		}
		
		if( flag == false ) {
			tar.setNonTerminal( Enum.nonTerminals.InputStm ) ;
			p = Search( ptr , tar , 1 ) ;
			if( p != null ) {
				flag = true ;
				inputStm( p ) ;
			}
		}
		
		if( flag == false ) {
			tar.setNonTerminal( Enum.nonTerminals.OutputStm ) ;
			p = Search( ptr , tar , 1 ) ;
			if( p != null ) {
				flag = true ;
				outputStm( p ) ;
			}
		}
		
		if( flag == false ) {
			tar.setNonTerminal( Enum.nonTerminals.ReturnStm ) ;
			p = Search( ptr , tar , 1 ) ;
			if( p != null ) {
				flag = true ;
				returnStm( p ) ;
			}
		}
		
		if( flag == false ) {
			tar.setflag( 0 ) ;
			tar.setTerminal( Enum.lexType.ID ) ;
			p = Search( ptr , tar , 1 ) ;
			if( p != null ) {
				flag = true ;
				otherStm( p ) ;
			}
		}
		
		tar.setflag( 1 ) ; tar.setNonTerminal( Enum.nonTerminals.StmMore ) ;
		p = Search( ptr , tar , 0 ) ;
		
		if( p != null ) {
			tar.setflag( 1 ) ; tar.setNonTerminal( Enum.nonTerminals.StmList ) ;
			p = Search( p , tar , 0 ) ;
			if( p != null ) stmList( p ) ;
		}		
	}
	
	private void conditionalStm( treeNode ptr ) {
		treeNode p ;
		treeNode tar = new treeNode() ;
		
		tar.setflag( 1 ) ; tar.setNonTerminal( Enum.nonTerminals.RelExp ) ;
		p = Search( ptr , tar , 0 ) ;
		if( p != null ) {
			relExp( p ) ;
		}
		
		tar.setNonTerminal( Enum.nonTerminals.StmList ) ; 
		p = Search( ptr , tar , 0 ) ;
		if( p != null ) {
			stmList( p ) ;
			for( int i = 0 ; i < ptr.getchildNum() ; i ++ ){
				if( ptr.getChild(i).getflag() == 1 ) {
					if( ptr.getChild(i) != p && ptr.getChild(i).getNonTerminal().equals( Enum.nonTerminals.StmList ) ) {
						p = ptr.getChild(i) ; 
						break ;
					}
				}
			}
			stmList( p ) ;
		}
	}
	
	private void loopStm( treeNode ptr ) {
		treeNode p ; 
		treeNode tar = new treeNode() ;
		tar.setflag( 1 ) ; tar.setNonTerminal( Enum.nonTerminals.RelExp ) ;
		
		p = Search( ptr , tar , 0 ) ;
		if( p != null ) {
			relExp( p ) ;
		}
		
		tar.setNonTerminal( Enum.nonTerminals.StmList ) ;
		p = Search( ptr , tar , 0 ) ;
		if( p != null ) {
			stmList( p ) ;
		}
	}
	
	private void inputStm( treeNode ptr ) {
		treeNode p ; 
		treeNode tar = new treeNode() ;
		tar.setflag( 1 ) ; tar.setNonTerminal( Enum.nonTerminals.Invar ) ;
		
		p = Search( ptr , tar , 0 ) ;
		if( p != null ) {
			invar( p ) ;
		}
	}
	
	private void outputStm( treeNode ptr ) {
		treeNode p ; 
		treeNode tar = new treeNode() ;
		
		typeIR tIR = new typeIR() ;
		integer  ntype = new integer( 0 ) ; 
		
		tar.setflag( 1 ) ; tar.setNonTerminal( Enum.nonTerminals.Exp ) ;
		
		p = Search( ptr , tar , 0 ) ;
		if( p != null ) {
			Exp( p , tIR , ntype ) ;
		}
	}
	
	private void returnStm( treeNode ptr ) {
	}
	
	private void otherStm( treeNode ptr ) {
		treeNode p , q , t ; 
		treeNode tar = new treeNode() ; tar.setflag( 1 );
		
		SymTableNode sym = new SymTableNode() ; 
		sym = myScope.FindID( ptr.getData() , true ) ;
		
		if( sym == null ) {
			String str ;
			tar.setNonTerminal( Enum.nonTerminals.AssignmentRest ) ;
			p = Search( ptr.getFather().getChild(1) , tar , 1 ) ;
			if( p != null ) str = "语义错误：		变量标识符" + ptr.getData() + "未定义" ;
			else str = "语义错误：		过程标识符" + ptr.getData() + "未定义" ;
			error( str , ptr.getLine() , ptr.getRow() ) ;
			return ;
		}
		else {
			ptr.symtPtr = sym ; 
			tar.setNonTerminal( Enum.nonTerminals.AssignmentRest ) ;
			q = Search( ptr.getFather().getChild(1) , tar , 1 ) ;
			if( q != null ) {
				if( sym.attrIR.idtype.Kind.equals( Enum.TypeKind.arrayTy ) ) {
					tar.setflag(0) ; tar.setTerminal( Enum.lexType.LMIDPAREN ) ;
					t = Search( q.getChild(0) , tar , 0 ) ;
					if( t == null ) {
						String str ; 
						str = "语法错误：		把数组" + ptr.getData() + "当成标识符来使用了" ;
						error( str , ptr.getLine() , ptr.getRow() ) ;
					}
				}
				assignmentRest( q , sym ) ;
				return ; 
			}
			tar.setNonTerminal( Enum.nonTerminals.CallStmRest ) ;
			p = Search( ptr.getFather().getChild(1) , tar , 1 ) ;
			
			if( p != null ) {
				callStmRest( p , sym ) ;
				return ; 
			}
		}
	}
	
	private void relExp( treeNode ptr ) {
		treeNode p ; 
		treeNode tar = new treeNode() ;
		
		typeIR tIR = new typeIR() ;
		integer ntype = new integer( 0 ) ; 
		
		tar.setflag( 1 ) ; tar.setNonTerminal( Enum.nonTerminals.Exp ) ;
		p = Search( ptr , tar , 1 ) ;
		if( p != null ) {
			Exp( p , tIR , ntype ) ;
			if( tIR.equals( CharTy ) ) tIR.copy( IntTy ) ;
			p = Search( ptr.getChild(1) , tar , 2 ) ;
			ntype.value = 1 ;
			Exp( p , tIR , ntype ) ;
		}
	}
	
	private void invar( treeNode ptr ) {
		if( ptr.getchildNum() != 0 && ptr.getChild(0).getTerminal().equals(Enum.lexType.ID) ) {
			SymTableNode sym = new SymTableNode() ;
			sym = myScope.FindID( ptr.getChild(0).getData() , true ) ;
			if( sym == null ) {
				String str ;
				str = "语义错误：		变量标识符" + ptr.getChild(0).getData() + "未定义" ;
				error( str , ptr.getChild(0).getLine() , ptr.getChild(0).getRow() ) ;
			}
			else if( sym.attrIR.kind.equals( Enum.IdKind.varkind ) == false ) {
				error( "语义错误：		标识符" + ptr.getChild(0).getData() + "应为变量类型" , ptr.getChild(0).getLine() , ptr.getChild(0).getRow() ) ;
			}
			else ptr.getChild(0).symtPtr = sym ;
		}
	}
	
	private void Exp( treeNode ptr , typeIR tIR , integer ntype ) {
		treeNode p ;
		treeNode tar = new treeNode() ;
		tar.setflag( 1 ) ; tar.setNonTerminal( Enum.nonTerminals.Term ) ;
		
		p = Search( ptr , tar , 1 ) ;
		if( p != null ) {
			term( p , tIR , ntype ) ;
		}
		
		tar.setNonTerminal( Enum.nonTerminals.OtherTerm ) ;
		p = Search( ptr , tar , 0 ) ;
		if( p != null ) {
			otherTerm( p , tIR , ntype ) ;
		}
	}
	
	private void variMore( treeNode ptr , SymTableNode sym , typeIR tIR ) {
		treeNode p ; 
		treeNode tar = new treeNode() ;
		integer ntype = new integer( 2 ) ; 
		
		tar.setflag( 1 ) ; tar.setNonTerminal( Enum.nonTerminals.Exp ) ;
		p = Search( ptr , tar , 0 ) ;
		if( p != null ) {
			tIR.copy( sym.attrIR.idtype.More.ArrayAttr.indexTy ) ;
			Exp( p , tIR , ntype ) ;
		}
		else {
			tar.setNonTerminal( Enum.nonTerminals.FieldVar ) ;
			p = Search( ptr , tar , 0 ) ;
			if( p != null ) {
				if( p.getchildNum() != 0 && p.getChild(0).getTerminal().equals( Enum.lexType.ID ) ) {
					fieldChain body = new fieldChain() ;
					body = sym.attrIR.idtype.More.body ;
					while( body != null ) {
						if( body.idname.equals( p.getChild(0).getData()) ) break ; 
						body = body.next ;
					}
					if( body != null ) {
						tIR.copy( IntTy ) ;
						tar.setNonTerminal( Enum.nonTerminals.FieldVarMore ) ;
						p = Search( p , tar , 0 ) ;
						if( p != null ) {
							tar.setNonTerminal( Enum.nonTerminals.Exp ) ;
							p = Search( p , tar , 0 ) ;
							ntype.value = 2 ;
							Exp( p , tIR , ntype ) ;
						}
					}
					else {
						error( "语义错误：		" + p.getChild(0).getData()+"并非过程标识符，而是"+sym.name+"类型" , p.getChild(0).getLine() , p.getChild(0).getRow() ) ;
					}
				}
			}
		}
	}
	
	private void term( treeNode ptr , typeIR tIR , integer ntype) {
		treeNode p , q ;
		treeNode tar = new treeNode() ;
		tar.setflag( 1 ) ; tar.setNonTerminal( Enum.nonTerminals.Factor ) ;
		
		p = Search( ptr , tar , 1 ) ;
		if( p != null ) {
			tar.setNonTerminal( Enum.nonTerminals.Exp ) ;
			q = Search( p , tar , 0 ) ;
			if( q !=null ) Exp( q , tIR , ntype) ;
			else {
				tar.setNonTerminal( Enum.nonTerminals.Variable ) ;
				q = Search( p , tar , 1 ) ;
				if( q != null ){
					variable( q , tIR , ntype ) ;
				}
				else if( tIR != null && p.getchildNum() != 0 && ( q = p.getChild(0) ).getTerminal().equals( Enum.lexType.INTC)) {
					if( !tIR.equals( IntTy ) && ntype.value != 2 ){
						error( "语义错误：		类型应该为" + tIR.Kind + ",而不应该是整型" , q.getLine() , q.getRow() ) ;
					}
				}
			}
			tar.setNonTerminal( Enum.nonTerminals.OtherFactor ) ;
			q = Search( ptr , tar , 0 ) ;
			if( q != null ) {
				tar.setNonTerminal( Enum.nonTerminals.Term ) ;
				q = Search( q , tar , 0 ) ;
				if( q != null ) term( q , tIR , ntype ) ;
			}
		}
	}
	
	private void otherTerm( treeNode ptr , typeIR tIR , integer ntype ) {
		treeNode p ; 
		treeNode tar = new treeNode() ;
		tar.setflag( 1 ) ; tar.setNonTerminal( Enum.nonTerminals.Exp ) ;
		
		p = Search( ptr , tar , 2 ) ;
		if( p != null ) {
			Exp( p , tIR , ntype ) ;
		}
	}
	
	@SuppressWarnings("null")
	private void variable( treeNode ptr , typeIR tIR , integer ntype ) {
		treeNode p , q , t ;
		treeNode tar = new treeNode() ;
		integer type = new integer( 0 ) ; 
		
		tar.setflag( 0 ) ;  tar.setTerminal( Enum.lexType.ID ) ;
		p = Search( ptr , tar , 2 ) ;
		if( p != null ) {
			SymTableNode sym = myScope.FindID( p.getData() , true ) ;
			if( sym != null ) {
				p.symtPtr = sym ;
				if( !sym.attrIR.kind.equals( Enum.IdKind.varkind ) ) {
					error( "语义错误：		标识符"+ptr.getChild(0).getData()+"应为变量类型" , ptr.getChild(0).getLine() , ptr.getChild(0).getRow() ) ;
				}
				else {
					if( sym.attrIR.idtype.Kind.equals( Enum.TypeKind.arrayTy ) ) {
						treeNode temp = new treeNode() ;
						treeNode tmp ;
						temp.setflag(0) ; temp.setTerminal( Enum.lexType.LMIDPAREN ) ;
						tmp = Search( ptr.getChild(1) , temp , 0 ) ;
						if( tmp == null ) {
							String str ; 
							str = "语法错误：		把数组" + ptr.getChild(0).getData() + "当成标识符来使用了" ;
							error( str , ptr.getChild(0).getLine() , ptr.getChild(0).getRow() ) ;
						}
					}
					if( ptr.getChild(1) != null && ptr.getChild(1).getchildNum() != 0 ) {
						
						tar.setflag( 1 ) ; tar.setNonTerminal( Enum.nonTerminals.Exp ) ;
						q = Search( ptr.getChild(1) , tar , 0 ) ;
						if( q != null ) {
							if( !sym.attrIR.idtype.Kind.equals( Enum.TypeKind.arrayTy ) ){
								String str ;
								str = "语义错误：		标识符" + ptr.getChild(0).getData() + "类型应该为" + " arrayTy" ;
								error( str , ptr.getChild(0).getLine() , ptr.getChild(0).getRow() ) ;
								return ;
							}
							if( ntype.value != 0 && !sym.attrIR.idtype.More.ArrayAttr.elemTy.equals( tIR ) ){
								String str ;
								str = "语义错误：		标识符" + ptr.getChild(0).getData() + "类型应该为" + tIR.Kind ;
								error( str , ptr.getChild(0).getLine() , ptr.getChild(0).getRow() ) ;
								return ; 
							}
							else {
								typeIR tIR0 = new typeIR() ;
								tIR0.copy( IntTy ) ;
								type.value = 2 ;
								Exp( q , tIR0 , type ) ;
								if( ntype.value == 0 ) {
									ntype.value = 1 ; 
									tIR.copy( sym.attrIR.idtype.More.ArrayAttr.elemTy ) ; 
								}
							}
						}
						tar.setNonTerminal( Enum.nonTerminals.FieldVar ) ;
						q = Search( ptr.getChild(1) , tar , 0 ) ;
						if( q != null ) {
							if( !sym.attrIR.idtype.Kind.equals( Enum.TypeKind.fieldTy ) ) {
								String str ; 
								str = "语义错误：		标识符" + ptr.getChild(0).getData() + "类型应该为" + Enum.TypeKind.fieldTy ;
								error( str , ptr.getChild(0).getLine() , ptr.getChild(0).getRow() ) ;
								return ;
							}
							tar.setflag(0) ; tar.setTerminal( Enum.lexType.ID ) ;
							q = Search( q , tar , 1 ) ;
							if( q != null ) {
								String idName = q.getData() ;
								fieldChain body = sym.attrIR.idtype.More.body ;
								while( body != null ) {
									if( body.idname.equals( idName ) ) break ; 
									body = body.next ; 
								}
								if( body == null ) {
									String str ; 
									str = "语义错误：		变量" + idName + "非纪录" + p.getData() + "成员变量" ;
									error( str , ptr.getChild(0).getLine() , ptr.getChild(0).getRow() ) ;
									return ;
								}
								else {
									tar.setflag(1) ; tar.setNonTerminal( Enum.nonTerminals.Exp ) ;
									t = Search( q.getFather().getChild(1) , tar , 2 ) ;
									if( t != null ) {
										if( !body.unitType.Kind.equals( Enum.TypeKind.arrayTy ) ) {
											String str ; 
											str = "语义错误：		纪录" + p.getData() + "成员变量标识符" + idName + "类型并非数组类型" ;
											error( str , q.getLine() , q.getRow() ) ;
											return ; 
										}
										if( ntype.value != 0  && !body.unitType.More.ArrayAttr.elemTy.equals( tIR ) ) {
											String str = "语义错误：		标识符" + idName + "类型应该为" +tIR.Kind ;
											error( str , q.getLine() , q.getRow() ) ;
											return ; 
										}
										else {
											typeIR tIR0 = new typeIR() ;
											tIR0.copy( IntTy ) ;
											type.value = 2 ;
											Exp( t , tIR0 , type ) ;
											if( ntype.value == 0 ) {
												ntype.value = 1 ; 
												tIR.copy( body.unitType.More.ArrayAttr.elemTy ) ; 
											}
										}
									}
									else {
										if( ntype.value != 0 && !body.unitType.equals( tIR ) ){
											String str = "语义错误：		标识符"+ idName + "类型应该为" + tIR.Kind ;
											error( str , q.getLine() , q.getRow() ) ;
											return ; 
										}
									}
								}
							}
							else {
								String str = "语义错误：		此处不应该出现纪录类型" ;
								error( str , q.getLine() , q.getRow() ) ;
								return ;
							}
						}
					}
					else {
						if( ntype.value == 0 ){
							tIR.copy( sym.attrIR.idtype ) ;
							ntype.value = 1 ;
						}
						else if( ntype.value == 1 ) {
							if( !sym.attrIR.idtype.equals( tIR ) ) {
								String str = "语义错误：		标识符" + ptr.getChild(0).getData() + "类型应该为" + tIR.Kind ;
								error( str , ptr.getChild(0).getLine() , ptr.getChild(0).getRow() ) ;
							}
						}
						else if( ntype.value == 2 && !sym.attrIR.idtype.equals( IntTy ) ) {
							String str = "语义错误：		标识符" + ptr.getChild(0).getData() + "类型应该为" + "intTy" ;
							error( str , ptr.getChild(0).getLine() , ptr.getChild(0).getRow() ) ;
						}
					}
				}
			}
			else {
				String str = "语义错误：		变量标识符" + ptr.getChild(0).getData() + "未定义" ;
				error( str , ptr.getChild(0).getLine() , ptr.getChild(0).getRow() ) ;
				return ;
			}
		}
	}
	
	private void assignmentRest( treeNode ptr , SymTableNode sym ) {
		treeNode p ; 
		treeNode tar = new treeNode() ;
		typeIR tIR = new typeIR() ;
		tIR.copy( sym.attrIR.idtype ) ; 
		integer ntype = new integer( 1 ) ; 
		
		tar.setflag( 1 ) ; tar.setNonTerminal( Enum.nonTerminals.VariMore ) ;
		p = Search( ptr , tar , 0 ) ;
		if( p != null ) variMore( p , sym , tIR ) ;
		if( tIR.equals( CharTy ) ) tIR.copy( IntTy ) ;
		tar.setNonTerminal( Enum.nonTerminals.Exp ) ;
		p = Search( ptr , tar , 0 ) ;
		if( p != null ) {
			Exp( p , tIR , ntype ) ;
		}
	}
	
	@SuppressWarnings("null")
	private void callStmRest( treeNode ptr , SymTableNode sym ) {
		treeNode p ; 
		treeNode tar = new treeNode() ;
		tar.setflag( 1 ) ; tar.setNonTerminal( Enum.nonTerminals.ActParamList ) ;
		p = Search( ptr , tar , 0 ) ;
		if( p != null ) {
			if( sym.attrIR.kind.equals( Enum.IdKind.prockind ) ) actParamList( p , sym.attrIR.More.ProcAttr.param ) ;
		}
		else {
			error( "语义错误：		" + sym.name + "并非过程标识符，而是" + sym.attrIR.kind + "类型" , p.getChild(0).getLine() , p.getChild(0).getRow() ) ;
		}
	}
	
	private void actParamList( treeNode ptr , ParamTable param ) {
		treeNode p ;
		treeNode tar = new treeNode() ;
		integer ntype = new integer( 1 ) ; 
		@SuppressWarnings("unused")
		typeIR tIR = new typeIR() ; 
		
		tar.setflag( 1 ) ;  tar.setNonTerminal( Enum.nonTerminals.Exp ) ;
		p = Search( ptr , tar , 1 ) ;
		if( p != null ) {
			if( param == null ) {
				error( "语义错误：		过程调用实参数目过多" , ptr.getLine() , ptr.getRow() ) ;
				return  ;
			}
			if( param.type.equals( CharTy ) ) {
				ntype.value = 0 ;
				Exp( p , tIR = IntTy , ntype ) ;
			}
			else Exp( p , tIR = param.type , ntype ) ;
			
			tar.setNonTerminal( Enum.nonTerminals.ActParamMore ) ;
			p = Search( ptr , tar , 0 ) ;
			
			if( p != null ) {
				tar.setNonTerminal( Enum.nonTerminals.ActParamList ) ;
				p = Search( p , tar , 0 ) ;
				if( p != null ) actParamList( p , param.next ) ;
				else if( param.next != null ) {
					error( "语义错误：		过程调用实参数目不完整" , ptr.getLine() , ptr.getRow() ) ;
					return ;
				}
			}
			
		}
		else if( param != null ) {
			error( "语义错误：		过程调用实参数目不完整" , ptr.getLine() , ptr.getRow() ) ;
			return ;
		}
	}
}
