    package complier.common;  
      
    import complier.common.Enum.*;  
      
    public class Node {  
        String Data ;  
        int    Line , Row ;  
        lexType    type ;
        public Node(){  
        }  
        public String getData() {  
            return Data;  
        }  
        public void setData(String data) {  
            Data = data;  
        }  
        public int getLine() {  
            return Line;  
        }  
        public void setLine(int line) {  
            Line = line;  
        }  
        public int getRow() {  
            return Row;  
        }  
        public void setRow(int row) {  
            Row = row;  
        }  
        public lexType getType() {  
            return type;  
        }
        public void setType(lexType integer) {
            this.type = integer;  
        }
    }