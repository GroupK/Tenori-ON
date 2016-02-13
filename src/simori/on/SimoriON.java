/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simori.on;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.*;
/**
 *
 * @author Sunny
 */
public class SimoriON extends JFrame {
    
    final TextField      disp     =  new TextField(32);
    MatrixButton[][] matrixButton = new MatrixButton[16][16];
    OperationButton[] lButtons = new OperationButton[4];
    OperationButton[] rButtons = new OperationButton[4];
    OperationButton okButton;
    OperationButton onButton;
    private int GUIWidth;
    private int GUIHeight;
    
    Performance performance = new Performance();
        
    /*
    This is the cofiguration class for the matrix buttons
    */
    private class MatrixButton extends JButton {
        private final int width = 38;
        private final int height = 38;
        private Boolean pressed = false;
        private final int x;
        private final int y;
        
        MatrixButton(int x, int y){
            this.setBorder(null);
            this.setContentAreaFilled(false);
            this.x = x;
            this.y = y;
            
            this.addActionListener(new ActionListener() {
                
                @Override
                public void actionPerformed(ActionEvent e) {
                    
                    performance.changeState(x, y);
                    if (pressed == false) {
                        pressed = true;
                    } else {
                        pressed = false;
                    }
                }
            });
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            // if pressed is true, change colour
            if (pressed == true) {
               g.setColor(Color.orange);
            } else {
               g.setColor(Color.lightGray);
            }
            
            g.fillOval(0, 0, this.getSize().width-1, this.getSize().height-1);

            super.paintComponent(g);
        }
        
        // paint a round border as opposed to a rectangular one
        @Override
        protected void paintBorder(Graphics g) {
           g.setColor(Color.black);
           g.drawOval(0, 0, this.getSize().width-1, this.getSize().height-1);
        }
        
        @Override
        public int getWidth(){
            return this.width;
        }
        
        @Override        
        public int getHeight(){
            return this.height;
        }
        
    }
    
    /*
    This is the cofiguration clss for the operation buttons
    */
    private class OperationButton extends JButton{
        private final int width = 70;
        private final int height = 70;
        
        OperationButton(String s){
            this.setBorder(null);
            this.setText(s);
            this.setContentAreaFilled(false);
        }
        
        //paint in oval shape
        @Override
        protected void paintComponent(Graphics g) {
            // if the button is pressed and ready to be released
            if (this.getModel().isArmed()) {
               g.setColor(Color.orange);
            } else {
               g.setColor(Color.white);
            }

            g.fillOval(0, 0, this.getSize().width-1, this.getSize().height-1);

            super.paintComponent(g);
        }
        
        // paint a round border
        @Override
        protected void paintBorder(Graphics g) {
           g.setColor(Color.black);
           g.drawOval(0, 0, this.getSize().width-1, this.getSize().height-1);
        }
        
        @Override
        public int getWidth(){
            return this.width;
        }
        
        @Override
        public int getHeight(){
            return this.height;
        }
        
    }
    
    /*
    This is the constructor for setting the layout
    */
    public SimoriON(){
        GUIWidth = 800;
        GUIHeight = 820;
        
        this.setSize(GUIWidth, GUIHeight);        
        this.setContentPane(new JLabel(new ImageIcon("bg.jpg")));
      
        //on button
        onButton = new OperationButton("ON");
        onButton.setBounds(((GUIWidth - onButton.getWidth())/2), 3 , onButton.getWidth(), onButton.getHeight());
        add(onButton);
        
        //left buttons
        //X position of rButtons is set to GUIWidth + 5
        for(int i=0;i<4;i++){
            String s = "L" + Integer.toString(i+1);
            lButtons[i] = new OperationButton(s);
            lButtons[i].setBounds(3, i*(38+75)+115, lButtons[i].getWidth(), lButtons[i].getHeight());
            add(lButtons[i]);
        }
        
        //right buttons
        //X position of rButtons are set to GUIWidth - 5 - (buttonwidth)
        for(int i=0;i<4;i++){
            String s = "R" + Integer.toString(i+1);
            rButtons[i] = new OperationButton(s);
            rButtons[i].setBounds((GUIWidth - rButtons[i].getWidth() - 9), i*(38+75)+115, rButtons[i].getWidth(), rButtons[i].getHeight());
            add(rButtons[i]);
        }
        
        //matrix buttons
        int x = 78;
        int y = 76;
        for(int i=0;i<16;i++){
            for(int j=0;j<16;j++){
                matrixButton[i][j] = new MatrixButton(j, i);
            }
        }
        for(int i=0;i<16;i++){
            for(int j=0;j<16;j++){
                matrixButton[i][j].setBounds(40*i+x, 40*j+y, matrixButton[i][j].getWidth(),  matrixButton[i][j].getHeight());
                add(matrixButton[i][j]);
            }
        }
        
        //ok button
        okButton = new OperationButton("OK");
        okButton.setBounds(570, (GUIHeight - (okButton.getHeight()) - 32), okButton.getWidth(), okButton.getHeight());
        add(okButton);
        
        
        //text view
        disp.setBounds(156, 718, 320, 70);
        add(disp);
        
    }
    
    static void displayGUI() {
        JFrame frame = new SimoriON();
                       
        frame.setTitle("Simori-On");
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        displayGUI();
        Performance test = new Performance();
    }
    
}
