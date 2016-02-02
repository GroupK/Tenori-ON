
package TenoriOn;
import java.awt.Color;
import java.awt.Image;
import java.awt.TextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.imageio.ImageIO;


public class GUI extends JFrame{
    final TextField      disp     =  new TextField(32);
    MatrixButton[][] matrixButton = new MatrixButton[16][16];
    OperationButton[] lButtons = new OperationButton[4];
    OperationButton[] rButtons = new OperationButton[4];
    OperationButton okButton;
    OperationButton onButton;
    
    public ImageIcon assignImage(String fileName){
        ImageIcon icon = null;
        try{
            Image image = ImageIO.read(new File(fileName));
            icon = new ImageIcon(image);
        }catch(Exception e){}
        return icon;
    }
    
    /*
    This is the cofiguration class for the matrix buttons
    */
    private class MatrixButton extends JButton{
        MatrixButton(){
            setBackground(Color.MAGENTA);
            setBorder(null);
        }
    }
    
    /*
    This is the cofiguration clss for the operation buttons
    */
    private class OperationButton extends JButton{
        OperationButton(String s){
            setBackground(Color.PINK);
            setText(s);
            setBorder(null);
            
        }
    }
    
    /*
    This is the constructor for setting the layout
    */
    public GUI(){
        setTitle("Tenori-On");
        setSize(800, 900);
        setLayout(null);
        Image bg;
        try{
             setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("bg.jpg")))));
        }catch(Exception e){}
        
        
        //on button
        onButton = new OperationButton("ON");
        onButton.setBounds(362, 0, 75, 75);
        add(onButton);
        
        //left buttons
        for(int i=0;i<4;i++){
            String s = "L" + Integer.toString(i);
            lButtons[i] = new OperationButton(s);
            lButtons[i].setBounds(0, i*(38+75)+100, 75, 75);
            add(lButtons[i]);
        }
        
        //right buttons
        for(int i=0;i<4;i++){
            String s = "R" + Integer.toString(i);
            rButtons[i] = new OperationButton(s);
            rButtons[i].setBounds(725, i*(38+75)+100, 75, 75);
            add(rButtons[i]);
        }
        
        //matrix buttons
        int x =85;
        int y =85;
        for(int i=0;i<16;i++){
            for(int j=0;j<16;j++){
                matrixButton[i][j] = new MatrixButton();
            }
        }
        for(int i=0;i<16;i++){
            for(int j=0;j<16;j++){
                matrixButton[i][j].setBounds(40*i+x, 40*j+y, 30, 30);
                add(matrixButton[i][j]);
            }
        }
        
        //ok button
        okButton = new OperationButton("OK");
        okButton.setBounds(600, 725, 75, 75);
        add(okButton);
        
        //text view
        disp.setBounds(200, 725, 300, 50);
        add(disp);
        
    }
    
    public static void main(String[] argv){
        JFrame frame = new GUI();
        frame.setForeground(Color.black);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
