package complier.semantic;

public class SymTableNode {
	public String name ;							// 符号表项的名称
	public AttributeIR attrIR = new AttributeIR() ; // 符号表项的信息
	public boolean EOFL ;							// 是否是本层符号表结束
	public SymTableNode next  ;						// 指向下一个符号表项
}
