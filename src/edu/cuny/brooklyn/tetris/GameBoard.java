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

import edu.cuny.brooklyn.tetris.shape.*;

public class GameBoard extends JPanel implements Runnable, ActionListener {
    private static final int ANIMATION_RATE = 5;
    private static final int CELL_SIZE = 10;
    private static final int X_CELLS = 50;
    private static final int Y_CELLS = 100;


    private final JFrame frame_;
    private final Timer timer_;
    private int yPosition = 0;
    private int velocity = +1;
    private final Shape[] shapes_;

    public GameBoard() {
        frame_ = new JFrame("Tetris");
        frame_.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setPreferredSize(new Dimension(CELL_SIZE*X_CELLS, CELL_SIZE*Y_CELLS));
        timer_ = new Timer(ANIMATION_RATE,this);

        shapes_ = new Shape[]{ new TShape(CELL_SIZE),
                               new LShape(CELL_SIZE),
                               new SquareShape(CELL_SIZE),
                               new LineShape(CELL_SIZE),
                               new ZigZagShape(CELL_SIZE) };

    }
    public void run() {
        frame_.add(this);
        frame_.pack();
        frame_.setVisible(true);
        
        timer_.start();

    }

    public void actionPerformed(ActionEvent e) {
        if (yPosition < 0 || yPosition > getHeight()) {
            velocity *= -1; 
        }
        yPosition += velocity;
        repaint();
    } 
    public void paintComponent(Graphics g) {
        g.clearRect(0,0,getWidth(),getHeight());
        g.setColor(Color.RED);
        for(int i = 0; i < shapes_.length; i++)
            shapes_[i].draw(g,i*getWidth()/shapes_.length,yPosition);
    }
}
