package complier.parser;

import complier.common.treeNode;
import javax.swing.* ;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics ;

@SuppressWarnings("serial")
public class drawTree extends JFrame {

    static int  X ;
    static int  Space = 30 ;
    static int  Width , High ;
    static NewPanel Panel ;


    public  drawTree() {
        Panel = new NewPanel() ;
        Panel.setPreferredSize( new Dimension( Width , High ) ) ;
        JScrollPane pane = new JScrollPane( Panel );
        add( pane ) ;
    }

    public  static int getTreeInf( treeNode root , int Y ) {
        int  temp , Length , width = 0 ;
        String str = "" ;
        //如果是终极符或者没有子结点
        if( root.getflag() == 0 || ( root.getchildNum() == 0 ) ) {
            if( root.getflag() == 0 ) str += root.getData() ;
            else str += root.getNonTerminal() ;
            Length = str.length() * 8 ;
            width = Length + Space ;
            root.setLength( Length ) ;
            root.setWidth( width ) ;
            root.setX( X ) ;
            root.setY( Y ) ;
            X += width ;
        }
        else {
            str += root.getNonTerminal() ;
            Length = str.length() * 8 ;
            root.setLength( Length ) ;
            root.setY( Y ) ;
            temp = X ;
            for( int i = 0 ; i < root.getchildNum() ; i ++ ) {
                width += getTreeInf( root.getChild( i ) , Y + 150 ) ;
            }
            root.setX( temp + width / 2 - Length / 2 ) ;
            if( width < Length ) {
                width = Length / 2 + width / 2 ;
                X += Length - width + Space ;
            }
            root.setWidth( width ) ;

        }
        return width ;
    }


    /***********************/
    /*传入语法树的根节点，然后绘制出来*/
    /***********************/
    @SuppressWarnings("static-access")
    public  static void drawtree( treeNode root ) {
        X = 20 ;
        High = getTreeInf( root , 20 ) ;
        Width = X ;
        Panel.Root = root ;

        drawTree frame = new drawTree() ;
        frame.setTitle("语法分析树") ;
//		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE ) ;
        frame.setSize( 800 , 600 ) ;
        frame.setVisible( true ) ;
    }
}

@SuppressWarnings("serial")
class NewPanel extends JLabel {

    public static treeNode Root ;

    public void draw( treeNode root , Graphics g ) {
        g.drawRect( root.getX() - 3 , root.getY() - 15 , root.getLength() + 1 , 20 ) ;
        //是终极符
        if( root.getflag() == 0 ) {
            g.drawString( root.getData() , root.getX() , root.getY() ) ;
        }
        else {
            g.drawString( "" + root.getNonTerminal() , root.getX() , root.getY() ) ;
            if( root.getchildNum() != 0 ) {
                for( int i = 0 ; i < root.getchildNum() ; i ++ ) {
                    g.drawLine( root.getX() + root.getLength() / 2 , root.getY() + 5  , root.getChild(i).getX() + root.getChild(i).getLength() / 2 , root.getChild(i).getY() - 15 ) ;
                    draw( root.getChild( i ) , g ) ;
                }
            }
        }
    }

    public void paint( Graphics g ) {
        super.paint( g ) ;
        g.setColor( Color.white ) ;
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor( Color.black ) ;
        draw( Root , g ) ;
    }
}
