package complier.semantic;

import complier.common.Enum; 

public class AttributeIR {
	public typeIR idtype = new typeIR();	// 标识符和类型的内部表示
	public Enum.IdKind kind ;				// 符号表 是 类型，标识符，变量，还是过程
	public int level ;						// 层数
	public more More = new more() ;			
	public class more {
		public varAttr VarAttr = new varAttr() ;		// 标识符的信息
		public procAttr ProcAttr = new procAttr() ;		// 过程定义的信息
	}
	public class procAttr {					
		public ParamTable param = new ParamTable() ;
		public int code ;
		public integer size = new integer(0) ;
	}
	public class varAttr {
		public Enum.AccessKind access ;
		public integer offset = new integer(0) ;
		public varAttr(){
		}
	}
}
