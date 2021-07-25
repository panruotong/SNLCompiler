/****************/
/* SNL的predict集  */
/****************/
package complier.parser;

import complier.common.Enum;

public class SNLpredict {
	public Predict[] predict = new Predict[105] ;
	public SNLpredict() {
		for( int i = 0 ; i <= 104 ; i ++ )
			predict[i] = new Predict() ;
		predict[1].setPredict( Enum.lexType.PROGRAM ) ;
		
		predict[2].setPredict( Enum.lexType.PROGRAM ) ;
		
		predict[3].setPredict( Enum.lexType.ID ) ;
		
		predict[4].setPredict( Enum.lexType.TYPE ) ; 			predict[4].setPredict( Enum.lexType.VAR ) ;
		predict[4].setPredict( Enum.lexType.PROCEDURE ) ;		predict[4].setPredict( Enum.lexType.BEGIN ) ;
		
		predict[5].setPredict( Enum.lexType.VAR ) ;				predict[5].setPredict( Enum.lexType.PROCEDURE ) ;
		predict[5].setPredict( Enum.lexType.BEGIN ) ;
		
		predict[6].setPredict( Enum.lexType.TYPE ) ;
		
		predict[7].setPredict( Enum.lexType.TYPE ) ;
		
		predict[8].setPredict( Enum.lexType.ID ) ;
		
		predict[9].setPredict( Enum.lexType.VAR ) ;				predict[9].setPredict( Enum.lexType.PROCEDURE ) ;
		predict[9].setPredict( Enum.lexType.BEGIN ) ;
		
		predict[10].setPredict( Enum.lexType.ID ) ;
		
		predict[11].setPredict( Enum.lexType.ID ) ;
		
		predict[12].setPredict( Enum.lexType.INTEGER ) ;		predict[12].setPredict( Enum.lexType.CHAR ) ;
		
		predict[13].setPredict( Enum.lexType.ARRAY ) ;			predict[13].setPredict( Enum.lexType.RECORD ) ;
		
		predict[14].setPredict( Enum.lexType.ID ) ;
		
		predict[15].setPredict( Enum.lexType.INTEGER ) ;
		
		predict[16].setPredict( Enum.lexType.CHAR ) ;
		
		predict[17].setPredict( Enum.lexType.ARRAY ) ;
		
		predict[18].setPredict( Enum.lexType.RECORD ) ;
		
		predict[19].setPredict( Enum.lexType.ARRAY ) ;
		
		predict[20].setPredict( Enum.lexType.INTC ) ;
		
		predict[21].setPredict( Enum.lexType.INTC ) ;
		
		predict[22].setPredict( Enum.lexType.RECORD ) ;
		
		predict[23].setPredict( Enum.lexType.INTEGER ) ; 		predict[23].setPredict( Enum.lexType.CHAR ) ;
		
		predict[24].setPredict( Enum.lexType.ARRAY ) ;
		
		predict[25].setPredict( Enum.lexType.END ) ;
		
		predict[26].setPredict( Enum.lexType.INTEGER ) ;		predict[26].setPredict( Enum.lexType.CHAR ) ;
		predict[26].setPredict( Enum.lexType.ARRAY ) ;
		
		predict[27].setPredict( Enum.lexType.ID ) ;
		
		predict[28].setPredict( Enum.lexType.SEMI ) ;
		
		predict[29].setPredict( Enum.lexType.COMMA ) ;
		
		predict[30].setPredict( Enum.lexType.PROCEDURE ) ; 		predict[30].setPredict( Enum.lexType.BEGIN ) ;
		
		predict[31].setPredict( Enum.lexType.VAR ) ;
		
		predict[32].setPredict( Enum.lexType.VAR ) ;
		
		predict[33].setPredict( Enum.lexType.INTEGER ) ;		predict[33].setPredict( Enum.lexType.CHAR ) ;
		predict[33].setPredict( Enum.lexType.ARRAY ) ;			predict[33].setPredict( Enum.lexType.RECORD ) ;
		predict[33].setPredict( Enum.lexType.ID ) ;
		
		predict[34].setPredict( Enum.lexType.PROCEDURE ) ;		predict[34].setPredict( Enum.lexType.BEGIN ) ;
		
		predict[35].setPredict( Enum.lexType.INTEGER ) ;		predict[35].setPredict( Enum.lexType.CHAR ) ;
		predict[35].setPredict( Enum.lexType.ARRAY ) ;			predict[35].setPredict( Enum.lexType.RECORD ) ;
		predict[35].setPredict( Enum.lexType.ID ) ;
		
		predict[36].setPredict( Enum.lexType.ID ) ;
		
		predict[37].setPredict( Enum.lexType.SEMI ) ;
		
		predict[38].setPredict( Enum.lexType.COMMA ) ;
		
		predict[39].setPredict( Enum.lexType.BEGIN ) ;
		
		predict[40].setPredict( Enum.lexType.PROCEDURE ) ;
		
		predict[41].setPredict( Enum.lexType.PROCEDURE ) ;
		
		predict[42].setPredict( Enum.lexType.BEGIN ) ;
		
		predict[43].setPredict( Enum.lexType.PROCEDURE ) ;
		
		predict[44].setPredict( Enum.lexType.ID ) ;
		
		predict[45].setPredict( Enum.lexType.RPAREN ) ;
		
		predict[46].setPredict( Enum.lexType.INTEGER ) ;		predict[46].setPredict( Enum.lexType.CHAR ) ;
		predict[46].setPredict( Enum.lexType.ARRAY ) ;			predict[46].setPredict( Enum.lexType.RECORD ) ;
		predict[46].setPredict( Enum.lexType.ID	) ;				predict[46].setPredict( Enum.lexType.VAR ) ;
		
		predict[47].setPredict( Enum.lexType.INTEGER ) ;		predict[47].setPredict( Enum.lexType.CHAR ) ;
		predict[47].setPredict( Enum.lexType.ARRAY ) ;			predict[47].setPredict( Enum.lexType.RECORD ) ;
		predict[47].setPredict( Enum.lexType.ID	) ;				predict[47].setPredict( Enum.lexType.VAR ) ;
		
		predict[48].setPredict( Enum.lexType.RPAREN ) ;
		
		predict[49].setPredict( Enum.lexType.SEMI ) ;
		
		predict[50].setPredict( Enum.lexType.INTEGER ) ;		predict[50].setPredict( Enum.lexType.CHAR ) ;
		predict[50].setPredict( Enum.lexType.ARRAY ) ;			predict[50].setPredict( Enum.lexType.RECORD ) ;
		predict[50].setPredict( Enum.lexType.ID ) ;
		
		predict[51].setPredict( Enum.lexType.VAR ) ;
		
		predict[52].setPredict( Enum.lexType.ID ) ;
		
		predict[53].setPredict( Enum.lexType.SEMI ) ;			predict[53].setPredict( Enum.lexType.RPAREN ) ;
		
		predict[54].setPredict( Enum.lexType.COMMA ) ;
		
		predict[55].setPredict( Enum.lexType.TYPE ) ;			predict[55].setPredict( Enum.lexType.VAR ) ;
		predict[55].setPredict( Enum.lexType.PROCEDURE ) ;		predict[55].setPredict( Enum.lexType.BEGIN ) ;
		
		predict[56].setPredict( Enum.lexType.BEGIN ) ;
		
		predict[57].setPredict( Enum.lexType.BEGIN ) ;
		
		predict[58].setPredict( Enum.lexType.ID ) ;				predict[58].setPredict( Enum.lexType.IF ) ;
		predict[58].setPredict( Enum.lexType.WHILE ) ;			predict[58].setPredict( Enum.lexType.RETURN ) ;
		predict[58].setPredict( Enum.lexType.READ ) ;			predict[58].setPredict( Enum.lexType.WRITE ) ;
		predict[58].setPredict( Enum.lexType.END ) ;			predict[58].setPredict( Enum.lexType.SEMI ) ;
		
		predict[59].setPredict( Enum.lexType.ELSE ) ;			predict[59].setPredict( Enum.lexType.FI ) ;
		predict[59].setPredict( Enum.lexType.END ) ;			predict[59].setPredict( Enum.lexType.ENDWH ) ;
		
		predict[60].setPredict( Enum.lexType.SEMI ) ;	
		
		predict[61].setPredict( Enum.lexType.IF ) ;
		
		predict[62].setPredict( Enum.lexType.WHILE ) ;
		
		predict[63].setPredict( Enum.lexType.READ ) ;
		
		predict[64].setPredict( Enum.lexType.WRITE ) ;
		
		predict[65].setPredict( Enum.lexType.RETURN ) ;
		
		predict[66].setPredict( Enum.lexType.ID ) ;
		
		predict[67].setPredict( Enum.lexType.ASSIGN ) ;			predict[67].setPredict( Enum.lexType.DOT ) ;
		predict[67].setPredict( Enum.lexType.LMIDPAREN ) ;
		
		predict[68].setPredict( Enum.lexType.LPAREN ) ;
		
		predict[69].setPredict( Enum.lexType.LMIDPAREN ) ;		predict[69].setPredict( Enum.lexType.DOT ) ;
		predict[69].setPredict( Enum.lexType.ASSIGN ) ;
		
		predict[70].setPredict( Enum.lexType.IF ) ;
		
		predict[71].setPredict( Enum.lexType.WHILE ) ;
		
		predict[72].setPredict( Enum.lexType.READ ) ;
		
		predict[73].setPredict( Enum.lexType.ID ) ;
		
		predict[74].setPredict( Enum.lexType.WRITE ) ;
		
		predict[75].setPredict( Enum.lexType.RETURN ) ;
		
		predict[76].setPredict( Enum.lexType.LPAREN ) ;
		
		predict[77].setPredict( Enum.lexType.RPAREN ) ;
		
		predict[78].setPredict( Enum.lexType.LPAREN ) ;			predict[78].setPredict( Enum.lexType.INTC ) ;
		predict[78].setPredict( Enum.lexType.ID ) ;
		
		predict[79].setPredict( Enum.lexType.RPAREN ) ;
		
		predict[80].setPredict( Enum.lexType.COMMA ) ;
		
		predict[81].setPredict( Enum.lexType.LPAREN ) ;			predict[81].setPredict( Enum.lexType.INTC ) ;
		predict[81].setPredict( Enum.lexType.ID ) ;
		
		predict[82].setPredict( Enum.lexType.LT ) ;				predict[82].setPredict( Enum.lexType.EQ ) ;

		predict[83].setPredict( Enum.lexType.LPAREN ) ;			predict[83].setPredict( Enum.lexType.INTC ) ;
		predict[83].setPredict( Enum.lexType.ID ) ;
		
		predict[84].setPredict( Enum.lexType.LT ) ;				predict[84].setPredict( Enum.lexType.EQ ) ;
		predict[84].setPredict( Enum.lexType.RMIDPAREN ) ;		predict[84].setPredict( Enum.lexType.THEN ) ;
		predict[84].setPredict( Enum.lexType.ELSE ) ;			predict[84].setPredict( Enum.lexType.FI ) ;
		predict[84].setPredict( Enum.lexType.DO ) ;				predict[84].setPredict( Enum.lexType.ENDWH ) ;
		predict[84].setPredict( Enum.lexType.RPAREN ) ;			predict[84].setPredict( Enum.lexType.END ) ;
		predict[84].setPredict( Enum.lexType.SEMI ) ;			predict[84].setPredict( Enum.lexType.COMMA ) ;
		
		predict[85].setPredict( Enum.lexType.PLUS ) ;			predict[85].setPredict( Enum.lexType.MINUS ) ;
		
		predict[86].setPredict( Enum.lexType.LPAREN ) ;			predict[86].setPredict( Enum.lexType.INTC ) ;
		predict[86].setPredict( Enum.lexType.ID ) ;
		
		predict[87].setPredict( Enum.lexType.PLUS ) ;			predict[87].setPredict( Enum.lexType.MINUS ) ;
		predict[87].setPredict( Enum.lexType.LT ) ;				predict[87].setPredict( Enum.lexType.EQ ) ;
		predict[87].setPredict( Enum.lexType.RMIDPAREN ) ;		predict[87].setPredict( Enum.lexType.THEN ) ;
		predict[87].setPredict( Enum.lexType.ELSE ) ;			predict[87].setPredict( Enum.lexType.FI ) ;
		predict[87].setPredict( Enum.lexType.DO ) ;				predict[87].setPredict( Enum.lexType.ENDWH ) ;
		predict[87].setPredict( Enum.lexType.RPAREN ) ;			predict[87].setPredict( Enum.lexType.END ) ;
		predict[87].setPredict( Enum.lexType.SEMI ) ;			predict[87].setPredict( Enum.lexType.COMMA ) ;
		
		predict[88].setPredict( Enum.lexType.TIMES ) ;			predict[88].setPredict( Enum.lexType.OVER ) ;
		
		predict[89].setPredict( Enum.lexType.LPAREN ) ;		
		
		predict[90].setPredict( Enum.lexType.INTC ) ;
		
		predict[91].setPredict( Enum.lexType.ID ) ;
		
		predict[92].setPredict( Enum.lexType.ID ) ;
		
		predict[93].setPredict( Enum.lexType.ASSIGN ) ;			predict[93].setPredict( Enum.lexType.TIMES ) ;
		predict[93].setPredict( Enum.lexType.OVER ) ;			predict[93].setPredict( Enum.lexType.PLUS ) ;
		predict[93].setPredict( Enum.lexType.MINUS ) ;			predict[93].setPredict( Enum.lexType.LT ) ;
		predict[93].setPredict( Enum.lexType.EQ ) ;				predict[93].setPredict( Enum.lexType.THEN ) ;
		predict[93].setPredict( Enum.lexType.ELSE ) ;			predict[93].setPredict( Enum.lexType.FI ) ;
		predict[93].setPredict( Enum.lexType.DO ) ;				predict[93].setPredict( Enum.lexType.ENDWH ) ;
		predict[93].setPredict( Enum.lexType.RPAREN ) ;			predict[93].setPredict( Enum.lexType.END ) ;
		predict[93].setPredict( Enum.lexType.SEMI ) ;			predict[93].setPredict( Enum.lexType.COMMA ) ;
		predict[93].setPredict( Enum.lexType.RMIDPAREN ) ;
		
		predict[94].setPredict( Enum.lexType.LMIDPAREN ) ;
		
		predict[95].setPredict( Enum.lexType.DOT ) ;
		
		predict[96].setPredict( Enum.lexType.ID ) ;
		
		predict[97].setPredict( Enum.lexType.ASSIGN ) ;			predict[97].setPredict( Enum.lexType.TIMES ) ;
		predict[97].setPredict( Enum.lexType.OVER ) ;			predict[97].setPredict( Enum.lexType.PLUS ) ;
		predict[97].setPredict( Enum.lexType.MINUS ) ;			predict[97].setPredict( Enum.lexType.LT ) ;
		predict[97].setPredict( Enum.lexType.EQ ) ;				predict[97].setPredict( Enum.lexType.THEN ) ;
		predict[97].setPredict( Enum.lexType.ELSE ) ;			predict[97].setPredict( Enum.lexType.FI ) ;
		predict[97].setPredict( Enum.lexType.DO ) ;				predict[97].setPredict( Enum.lexType.ENDWH ) ;
		predict[97].setPredict( Enum.lexType.RPAREN ) ;			predict[97].setPredict( Enum.lexType.END ) ;
		predict[97].setPredict( Enum.lexType.SEMI ) ;			predict[97].setPredict( Enum.lexType.COMMA ) ;
		predict[97].setPredict( Enum.lexType.RMIDPAREN ) ;
		
		predict[98].setPredict( Enum.lexType.LMIDPAREN ) ;
		
		predict[99].setPredict( Enum.lexType.LT ) ;
		
		predict[100].setPredict( Enum.lexType.EQ ) ;
		
		predict[101].setPredict( Enum.lexType.PLUS ) ;
		
		predict[102].setPredict( Enum.lexType.MINUS ) ;
		
		predict[103].setPredict( Enum.lexType.TIMES ) ;
		
		predict[104].setPredict( Enum.lexType.OVER ) ;
	}
}
