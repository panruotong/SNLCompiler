/***********************/
/*    产生式的数据结构                   */
/***********************/

package complier.parser;  

import complier.common.Enum;
      
    public class Production {  
        Enum.nonTerminals Head ;  
        int productNum ;   
        class product {  
            public int flag ;                 // 0表示终极符，1表示非终极符  
            public Enum.nonTerminals nonterminals ;  
            public Enum.lexType      terminals    ;  
        }  
          
        product[] Product = new product[10] ;  
          
        public Production(){  
            productNum = 0 ;   
        }  
        public void setHead( Enum.nonTerminals head ) {  
            Head = head ;  
        }  
        public void setProduction( Enum.nonTerminals nonterminal ){  
            Product[productNum] = new product() ;  
            Product[productNum].flag = 1 ;  
            Product[productNum].nonterminals = nonterminal ;   
            productNum ++ ;   
        }  
        public void setProduction( Enum.lexType  terminal ){  
            Product[productNum] = new product() ;   
            Product[productNum].flag = 0 ;  
            Product[productNum].terminals = terminal ;  
            productNum ++ ;  
        }  
        public Enum.nonTerminals getHead(){  
            return Head ;  
        }  
        public int getproductNum() {  
            return productNum ;  
        }  
        public int getflag( int number ) {  
            if( Product[number].flag == 1 ) return 1 ;  
            else return 0 ;  
        }  
        public Enum.nonTerminals getProductNonterminal( int number ) {  
            return Product[number].nonterminals ;  
        }  
        public Enum.lexType getProductTerminal( int number ) {  
            return Product[number].terminals ;  
        }  
    } ;  