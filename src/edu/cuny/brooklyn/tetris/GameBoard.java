package edu.cuny.brooklyn.tetris;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Timer;
import javax.swing.SwingUtilities;

public class GameBoard extends JPanel implements Runnable, ActionListener {
    private final JFrame frame_;
    private final Timer timer_;
    private int x = 0;
    private int velocity = +1;

    public GameBoard() {
        frame_ = new JFrame("Tetris");
        frame_.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setPreferredSize(new Dimension(500,500));
        timer_ = new Timer(5,this);

    }
    public void run() {
        frame_.add(this);
        frame_.pack();
        frame_.setVisible(true);
        
        timer_.start();

    }

    public void actionPerformed(ActionEvent e) {
        if (x < 0 || x > 490) {
            velocity *= -1; 
        }
        x += velocity;
        repaint();
    } 
    public void paintComponent(Graphics g) {
        g.clearRect(0,0,getWidth(),getHeight());
        g.setColor(Color.RED);
        g.fillRect(getWidth()/2,x,10,10);
    }
}
