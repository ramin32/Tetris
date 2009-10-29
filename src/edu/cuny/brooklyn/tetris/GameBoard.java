package edu.cuny.brooklyn.tetris;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import javax.swing.Timer;
import javax.swing.SwingUtilities;
import java.util.Random;

public class GameBoard extends JPanel implements Runnable, ActionListener, KeyListener {
    private static final int ANIMATION_RATE = 5;
    private static final int CELL_SIZE = 10;
    private static final int X_CELLS = 50;
    private static final int Y_CELLS = 100;
    private static final int BOARD_WIDTH = X_CELLS * CELL_SIZE;
    private static final int BOARD_HEIGHT = Y_CELLS * CELL_SIZE;
    private static final Random random_ = new Random(System.currentTimeMillis());

    private final JFrame frame_;
    private final Timer timer_;
    private int xPosition = BOARD_WIDTH/2;
    private int yPosition = 0;
    private int velocity = +1;
    private final Shape[] shapes_;
    private Shape currentShape_;
    private Color currentColor_;

    public GameBoard() {
        frame_ = new JFrame("Tetris");
        frame_.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame_.setFocusable(true);
        frame_.addKeyListener(this);

        setPreferredSize(new Dimension(CELL_SIZE*X_CELLS, CELL_SIZE*Y_CELLS));
        
        timer_ = new Timer(ANIMATION_RATE,this);

        shapes_ = new Shape[]{ Shape.createLineShape(CELL_SIZE),
                               Shape.createTShape(CELL_SIZE),
                               Shape.createLShape(CELL_SIZE),
                               Shape.createZigZagShape(CELL_SIZE),
                               Shape.createSquareShape(CELL_SIZE) };
        randomizeCurrentShape();
        randomizeCurrentColor();
    }
    
    private void randomizeCurrentShape()
    {
        currentShape_ = shapes_[random_.nextInt(shapes_.length)];
    }

    private void randomizeCurrentColor()
    {
        currentColor_ = new Color(random_.nextInt(256),
                                  random_.nextInt(256),
                                  random_.nextInt(256));
    }

    public void run() {
        frame_.add(this);
        frame_.pack();
        frame_.setVisible(true);
        
        timer_.start();

    }

    public void actionPerformed(ActionEvent e) {
        if (yPosition < 0 || yPosition > getHeight()-currentShape_.getHeight()) {
            velocity *= -1; 
        }
        yPosition += velocity;
        repaint();
    } 
    public void paintComponent(Graphics g) {
        g.clearRect(0,0,getWidth(),getHeight());
        g.setColor(currentColor_);
        currentShape_.draw(g,xPosition,yPosition);
    }

    public void keyPressed(KeyEvent e)
    {	
        if(e.getKeyCode() == KeyEvent.VK_LEFT)
            xPosition -= CELL_SIZE;
        else if(e.getKeyCode() == KeyEvent.VK_RIGHT)
            xPosition += CELL_SIZE;

    }
    public void keyReleased(KeyEvent e){}
    public void keyTyped(KeyEvent e){}
}
