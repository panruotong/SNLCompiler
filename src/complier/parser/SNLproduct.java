/***************/
/*   SNL的产生式     */
/***************/

package complier.parser;

import complier.common.Enum;

public class SNLproduct {
	public Production[] product = new Production[105] ;
	public SNLproduct() {
		for( int i = 0 ; i <= 104 ; i ++ )
			product[i] = new Production() ;
		product[1].setHead( Enum.nonTerminals.Program ) ;    			product[1].setProduction( Enum.nonTerminals.ProgramHead ) ;
		product[1].setProduction( Enum.nonTerminals.DeclarePart ) ;  	product[1].setProduction( Enum.nonTerminals.ProgramBody ) ;
		
		product[2].setHead( Enum.nonTerminals.ProgramHead ) ; 			product[2].setProduction( Enum.lexType.PROGRAM ) ;
		product[2].setProduction( Enum.nonTerminals.ProgramName ) ;
		
		product[3].setHead( Enum.nonTerminals.ProgramName ) ;			product[3].setProduction( Enum.lexType.ID ) ;
		
		product[4].setHead( Enum.nonTerminals.DeclarePart ) ;			product[4].setProduction( Enum.nonTerminals.TypeDecpart ) ;
		product[4].setProduction( Enum.nonTerminals.VarDecpart ) ;      product[4].setProduction( Enum.nonTerminals.ProcDecpart ) ;
		
		product[5].setHead( Enum.nonTerminals.TypeDecpart ) ;
		
		product[6].setHead( Enum.nonTerminals.TypeDecpart ) ;			product[6].setProduction( Enum.nonTerminals.TypeDec ) ;
		
		product[7].setHead( Enum.nonTerminals.TypeDec ) ;				product[7].setProduction( Enum.lexType.TYPE ) ;
		product[7].setProduction( Enum.nonTerminals.TypeDecList ) ;
		
		product[8].setHead( Enum.nonTerminals.TypeDecList ) ;			product[8].setProduction( Enum.nonTerminals.TypeId ) ;
		product[8].setProduction( Enum.lexType.EQ ) ;					product[8].setProduction( Enum.nonTerminals.TypeDef ) ;
		product[8].setProduction( Enum.lexType.SEMI ) ;					product[8].setProduction( Enum.nonTerminals.TypeDecMore ) ;
		
		product[9].setHead( Enum.nonTerminals.TypeDecMore ) ;			
		
		product[10].setHead( Enum.nonTerminals.TypeDecMore ) ; 			product[10].setProduction( Enum.nonTerminals.TypeDecList ) ;
		
		product[11].setHead( Enum.nonTerminals.TypeId ) ; 				product[11].setProduction( Enum.lexType.ID ) ;
		
		product[12].setHead( Enum.nonTerminals.TypeDef ) ;				product[12].setProduction( Enum.nonTerminals.BaseType ) ;
		
		product[13].setHead( Enum.nonTerminals.TypeDef ) ;				product[13].setProduction( Enum.nonTerminals.StructureType ) ;
		
		product[14].setHead( Enum.nonTerminals.TypeDef ) ;				product[14].setProduction( Enum.lexType.ID ) ;
		
		product[15].setHead( Enum.nonTerminals.BaseType ) ;				product[15].setProduction( Enum.lexType.INTEGER ) ;
		
		product[16].setHead( Enum.nonTerminals.BaseType ) ;				product[16].setProduction( Enum.lexType.CHAR ) ;
		
		product[17].setHead( Enum.nonTerminals.StructureType ) ;		product[17].setProduction( Enum.nonTerminals.ArrayType ) ;
		
		product[18].setHead( Enum.nonTerminals.StructureType ) ;		product[18].setProduction( Enum.nonTerminals.RecType ) ;
		
		product[19].setHead( Enum.nonTerminals.ArrayType ) ;			product[19].setProduction( Enum.lexType.ARRAY ) ;
		product[19].setProduction( Enum.lexType.LMIDPAREN ) ;			product[19].setProduction( Enum.nonTerminals.Low ) ;
		product[19].setProduction( Enum.lexType.UNDERANGE ) ;			product[19].setProduction( Enum.nonTerminals.Top ) ;
		product[19].setProduction( Enum.lexType.RMIDPAREN ) ;			product[19].setProduction( Enum.lexType.OF ) ;					
		product[19].setProduction( Enum.nonTerminals.BaseType ) ;
		
		product[20].setHead( Enum.nonTerminals.Low ) ;					product[20].setProduction( Enum.lexType.INTC ) ;
		
		product[21].setHead( Enum.nonTerminals.Top ) ;					product[21].setProduction( Enum.lexType.INTC ) ;
		
		product[22].setHead( Enum.nonTerminals.RecType ) ;				product[22].setProduction( Enum.lexType.RECORD ) ;
		product[22].setProduction( Enum.nonTerminals.FieldDecList ) ;	product[22].setProduction( Enum.lexType.END ) ;
		
		product[23].setHead( Enum.nonTerminals.FieldDecList ) ;			product[23].setProduction( Enum.nonTerminals.BaseType ) ;
		product[23].setProduction( Enum.nonTerminals.IdList ) ;			product[23].setProduction( Enum.lexType.SEMI ) ;
		product[23].setProduction( Enum.nonTerminals.FieldDecMore ) ;
		
		product[24].setHead( Enum.nonTerminals.FieldDecList ) ;			product[24].setProduction( Enum.nonTerminals.ArrayType ) ;
		product[24].setProduction( Enum.nonTerminals.IdList ) ;			product[24].setProduction( Enum.lexType.SEMI ) ;
		product[24].setProduction( Enum.nonTerminals.FieldDecMore ) ;
		
		product[25].setHead( Enum.nonTerminals.FieldDecMore ) ;
		
		product[26].setHead( Enum.nonTerminals.FieldDecMore ) ;			product[26].setProduction( Enum.nonTerminals.FieldDecList ) ;
		
		product[27].setHead( Enum.nonTerminals.IdList ) ;				product[27].setProduction( Enum.lexType.ID ) ;
		product[27].setProduction( Enum.nonTerminals.IdMore ) ;
		
		product[28].setHead( Enum.nonTerminals.IdMore ) ;
		
		product[29].setHead( Enum.nonTerminals.IdMore ) ;				product[29].setProduction( Enum.lexType.COMMA ) ;
		product[29].setProduction( Enum.nonTerminals.IdList ) ;
		
		product[30].setHead( Enum.nonTerminals.VarDecpart ) ;
		
		product[31].setHead( Enum.nonTerminals.VarDecpart ) ;			product[31].setProduction( Enum.nonTerminals.VarDec ) ;
		
		product[32].setHead( Enum.nonTerminals.VarDec ) ;				product[32].setProduction( Enum.lexType.VAR ) ;
		product[32].setProduction( Enum.nonTerminals.VarDecList ) ;
		
		product[33].setHead( Enum.nonTerminals.VarDecList ) ;			product[33].setProduction( Enum.nonTerminals.TypeDef ) ;
		product[33].setProduction( Enum.nonTerminals.VarIdList ) ;		product[33].setProduction( Enum.lexType.SEMI ) ;
		product[33].setProduction( Enum.nonTerminals.VarDecMore ) ;
		
		product[34].setHead( Enum.nonTerminals.VarDecMore ) ;
		
		product[35].setHead( Enum.nonTerminals.VarDecMore ) ;			product[35].setProduction( Enum.nonTerminals.VarDecList ) ;
		
		product[36].setHead( Enum.nonTerminals.VarIdList ) ;			product[36].setProduction( Enum.lexType.ID ) ;
		product[36].setProduction( Enum.nonTerminals.VarIdMore ) ;
		
		product[37].setHead( Enum.nonTerminals.VarIdMore ) ;
		
		product[38].setHead( Enum.nonTerminals.VarIdMore ) ;			product[38].setProduction( Enum.lexType.COMMA ) ;
		product[38].setProduction( Enum.nonTerminals.VarIdList ) ;
		
		product[39].setHead( Enum.nonTerminals.ProcDecpart ) ;			
		
		product[40].setHead( Enum.nonTerminals.ProcDecpart ) ;  		product[40].setProduction( Enum.nonTerminals.ProcDec ) ;
		
		product[41].setHead( Enum.nonTerminals.ProcDec ) ;				product[41].setProduction( Enum.lexType.PROCEDURE ) ;
		product[41].setProduction( Enum.nonTerminals.ProcName ) ;		product[41].setProduction( Enum.lexType.LPAREN ) ;
		product[41].setProduction( Enum.nonTerminals.ParamList ) ;		product[41].setProduction( Enum.lexType.RPAREN ) ;
		product[41].setProduction( Enum.lexType.SEMI ) ;				product[41].setProduction( Enum.nonTerminals.ProcDecPart ) ;
		product[41].setProduction( Enum.nonTerminals.ProcBody ) ;		product[41].setProduction( Enum.nonTerminals.ProcDecMore ) ;
		
		product[42].setHead( Enum.nonTerminals.ProcDecMore ) ;
		
		product[43].setHead( Enum.nonTerminals.ProcDecMore ) ;			product[43].setProduction( Enum.nonTerminals.ProcDec ) ;
		
		product[44].setHead( Enum.nonTerminals.ProcName ) ;				product[44].setProduction( Enum.lexType.ID ) ;
		
		product[45].setHead( Enum.nonTerminals.ParamList ) ;			
		
		product[46].setHead( Enum.nonTerminals.ParamList ) ;			product[46].setProduction( Enum.nonTerminals.ParamDecList ) ;
		
		product[47].setHead( Enum.nonTerminals.ParamDecList ) ;			product[47].setProduction( Enum.nonTerminals.Param ) ;
		product[47].setProduction( Enum.nonTerminals.ParamMore ) ;
		
		product[48].setHead( Enum.nonTerminals.ParamMore ) ;
		
		product[49].setHead( Enum.nonTerminals.ParamMore ) ;			product[49].setProduction( Enum.lexType.SEMI ) ;
		product[49].setProduction( Enum.nonTerminals.ParamDecList ) ;
		
		product[50].setHead( Enum.nonTerminals.Param ) ;				product[50].setProduction( Enum.nonTerminals.TypeDef ) ;
		product[50].setProduction( Enum.nonTerminals.FormList ) ;
		
		product[51].setHead( Enum.nonTerminals.Param ) ;				product[51].setProduction( Enum.lexType.VAR ) ;
		product[51].setProduction( Enum.nonTerminals.TypeDef ) ;		product[51].setProduction( Enum.nonTerminals.FormList ) ;
		
		product[52].setHead( Enum.nonTerminals.FormList ) ;				product[52].setProduction( Enum.lexType.ID ) ;
		product[52].setProduction( Enum.nonTerminals.FidMore ) ;
		
		product[53].setHead( Enum.nonTerminals.FidMore ) ;
		
		product[54].setHead( Enum.nonTerminals.FidMore ) ;				product[54].setProduction( Enum.lexType.COMMA ) ;
		product[54].setProduction( Enum.nonTerminals.FormList ) ;
		
		product[55].setHead( Enum.nonTerminals.ProcDecPart ) ;			product[55].setProduction( Enum.nonTerminals.DeclarePart ) ;
		
		product[56].setHead( Enum.nonTerminals.ProcBody ) ;				product[56].setProduction( Enum.nonTerminals.ProgramBody ) ;
		
		product[57].setHead( Enum.nonTerminals.ProgramBody ) ;			product[57].setProduction( Enum.lexType.BEGIN ) ;
		product[57].setProduction( Enum.nonTerminals.StmList ) ;		product[57].setProduction( Enum.lexType.END ) ;
		
		product[58].setHead( Enum.nonTerminals.StmList ) ;				product[58].setProduction( Enum.nonTerminals.Stm ) ;
		product[58].setProduction( Enum.nonTerminals.StmMore ) ;
		
		product[59].setHead( Enum.nonTerminals.StmMore ) ;
		
		product[60].setHead( Enum.nonTerminals.StmMore ) ;				product[60].setProduction( Enum.lexType.SEMI ) ;
		product[60].setProduction( Enum.nonTerminals.StmList ) ;
		
		product[61].setHead( Enum.nonTerminals.Stm ) ;					product[61].setProduction( Enum.nonTerminals.ConditionalStm ) ;
		
		product[62].setHead( Enum.nonTerminals.Stm ) ;					product[62].setProduction( Enum.nonTerminals.LoopStm ) ;
		
		product[63].setHead( Enum.nonTerminals.Stm ) ;					product[63].setProduction( Enum.nonTerminals.InputStm ) ;
		
		product[64].setHead( Enum.nonTerminals.Stm ) ;					product[64].setProduction( Enum.nonTerminals.OutputStm ) ;
		
		product[65].setHead( Enum.nonTerminals.Stm ) ;					product[65].setProduction( Enum.nonTerminals.ReturnStm ) ;
		
		product[66].setHead( Enum.nonTerminals.Stm ) ;					product[66].setProduction( Enum.lexType.ID ) ;
		product[66].setProduction( Enum.nonTerminals.AssCall ) ;
		
		product[67].setHead( Enum.nonTerminals.AssCall );				product[67].setProduction( Enum.nonTerminals.AssignmentRest ) ;
		
		product[68].setHead( Enum.nonTerminals.AssCall ) ; 				product[68].setProduction( Enum.nonTerminals.CallStmRest ) ;
		
		product[69].setHead( Enum.nonTerminals.AssignmentRest ) ;		product[69].setProduction( Enum.nonTerminals.VariMore ) ; 
		product[69].setProduction( Enum.lexType.ASSIGN ) ;				product[69].setProduction( Enum.nonTerminals.Exp ) ;
		
		product[70].setHead( Enum.nonTerminals.ConditionalStm ) ;		product[70].setProduction( Enum.lexType.IF ) ;
		product[70].setProduction( Enum.nonTerminals.RelExp ) ; 		product[70].setProduction( Enum.lexType.THEN ) ; 
		product[70].setProduction( Enum.nonTerminals.StmList ) ;		product[70].setProduction( Enum.lexType.ELSE ) ;
		product[70].setProduction( Enum.nonTerminals.StmList ) ;		product[70].setProduction( Enum.lexType.FI ) ;
		
		product[71].setHead( Enum.nonTerminals.LoopStm ) ;				product[71].setProduction( Enum.lexType.WHILE ) ;
		product[71].setProduction( Enum.nonTerminals.RelExp ) ;			product[71].setProduction( Enum.lexType.DO ) ;
		product[71].setProduction( Enum.nonTerminals.StmList ) ;		product[71].setProduction( Enum.lexType.ENDWH ) ;
		
		product[72].setHead( Enum.nonTerminals.InputStm ) ;				product[72].setProduction( Enum.lexType.READ ) ;
		product[72].setProduction( Enum.lexType.LPAREN ) ;				product[72].setProduction( Enum.nonTerminals.Invar ) ;
		product[72].setProduction( Enum.lexType.RPAREN ) ;
		
		product[73].setHead( Enum.nonTerminals.Invar ) ;				product[73].setProduction( Enum.lexType.ID ) ;
		
		product[74].setHead( Enum.nonTerminals.OutputStm ) ;			product[74].setProduction( Enum.lexType.WRITE ) ;
		product[74].setProduction( Enum.lexType.LPAREN ) ;				product[74].setProduction( Enum.nonTerminals.Exp ) ;
		product[74].setProduction( Enum.lexType.RPAREN ) ;
		
		product[75].setHead( Enum.nonTerminals.ReturnStm ) ;			product[75].setProduction( Enum.lexType.RETURN ) ;
		
		product[76].setHead( Enum.nonTerminals.CallStmRest ) ;			product[76].setProduction( Enum.lexType.LPAREN );
		product[76].setProduction( Enum.nonTerminals.ActParamList ) ;	product[76].setProduction( Enum.lexType.RPAREN ) ;
		
		product[77].setHead( Enum.nonTerminals.ActParamList ) ;	
		
		product[78].setHead( Enum.nonTerminals.ActParamList ) ;			product[78].setProduction( Enum.nonTerminals.Exp ) ;
		product[78].setProduction( Enum.nonTerminals.ActParamMore ) ;
		
		product[79].setHead( Enum.nonTerminals.ActParamMore ) ;	
		
		product[80].setHead( Enum.nonTerminals.ActParamMore ) ; 		product[80].setProduction( Enum.lexType.COMMA ) ;
		product[80].setProduction( Enum.nonTerminals.ActParamList ) ;
		
		product[81].setHead( Enum.nonTerminals.RelExp ) ;				product[81].setProduction( Enum.nonTerminals.Exp ) ;
		product[81].setProduction( Enum.nonTerminals.OtherRelE ) ;
		
		product[82].setHead( Enum.nonTerminals.OtherRelE ) ;			product[82].setProduction( Enum.nonTerminals.CmpOp ) ;
		product[82].setProduction( Enum.nonTerminals.Exp ) ;
		
		product[83].setHead( Enum.nonTerminals.Exp ) ;					product[83].setProduction( Enum.nonTerminals.Term ) ;
		product[83].setProduction( Enum.nonTerminals.OtherTerm ) ;
		
		product[84].setHead( Enum.nonTerminals.OtherTerm ) ;			
		
		product[85].setHead( Enum.nonTerminals.OtherTerm ) ;			product[85].setProduction( Enum.nonTerminals.AddOp ) ;
		product[85].setProduction( Enum.nonTerminals.Exp ) ;
		
		product[86].setHead( Enum.nonTerminals.Term ) ;					product[86].setProduction( Enum.nonTerminals.Factor ) ;
		product[86].setProduction( Enum.nonTerminals.OtherFactor ) ;
		
		product[87].setHead( Enum.nonTerminals.OtherFactor ) ;			
		
		product[88].setHead( Enum.nonTerminals.OtherFactor ) ;			product[88].setProduction( Enum.nonTerminals.MultOp ) ;
		product[88].setProduction( Enum.nonTerminals.Term ) ;
		
		product[89].setHead( Enum.nonTerminals.Factor ) ;				product[89].setProduction( Enum.lexType.LPAREN ) ;
		product[89].setProduction( Enum.nonTerminals.Exp ) ;			product[89].setProduction( Enum.lexType.RPAREN ) ;
		
		product[90].setHead( Enum.nonTerminals.Factor ) ;				product[90].setProduction( Enum.lexType.INTC ) ;
		
		product[91].setHead( Enum.nonTerminals.Factor ) ;				product[91].setProduction( Enum.nonTerminals.Variable ) ;
		
		product[92].setHead( Enum.nonTerminals.Variable ) ;				product[92].setProduction( Enum.lexType.ID ) ;
		product[92].setProduction( Enum.nonTerminals.VariMore ) ;
		
		product[93].setHead( Enum.nonTerminals.VariMore ) ;	
		
		product[94].setHead( Enum.nonTerminals.VariMore ) ;				product[94].setProduction( Enum.lexType.LMIDPAREN ) ;
		product[94].setProduction( Enum.nonTerminals.Exp ) ;			product[94].setProduction( Enum.lexType.RMIDPAREN ) ;
		
		product[95].setHead( Enum.nonTerminals.VariMore ) ;				product[95].setProduction( Enum.lexType.DOT ) ;
		product[95].setProduction( Enum.nonTerminals.FieldVar ) ;
		
		product[96].setHead( Enum.nonTerminals.FieldVar ) ;				product[96].setProduction( Enum.lexType.ID ) ;
		product[96].setProduction( Enum.nonTerminals.FieldVarMore ) ;
		
		product[97].setHead( Enum.nonTerminals.FieldVarMore ) ;
		
		product[98].setHead( Enum.nonTerminals.FieldVarMore ) ;			product[98].setProduction( Enum.lexType.LMIDPAREN ) ;
		product[98].setProduction( Enum.nonTerminals.Exp ) ;			product[98].setProduction( Enum.lexType.RMIDPAREN ) ;
		
		product[99].setHead( Enum.nonTerminals.CmpOp ) ;				product[99].setProduction( Enum.lexType.LT ) ;
		
		product[100].setHead( Enum.nonTerminals.CmpOp ) ;				product[100].setProduction( Enum.lexType.EQ ) ;
		
		product[101].setHead( Enum.nonTerminals.AddOp ) ;				product[101].setProduction( Enum.lexType.PLUS ) ;
		
		product[102].setHead( Enum.nonTerminals.AddOp ) ;				product[102].setProduction( Enum.lexType.MINUS ) ;
		
		product[103].setHead( Enum.nonTerminals.MultOp ) ;				product[103].setProduction( Enum.lexType.TIMES ) ;
		
		product[104].setHead( Enum.nonTerminals.MultOp ) ;				product[104].setProduction( Enum.lexType.OVER ) ;
	}
}
