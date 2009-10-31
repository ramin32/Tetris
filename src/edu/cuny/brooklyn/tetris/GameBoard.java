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
import java.awt.Point;

import edu.cuny.brooklyn.tetris.shape.Shape;

public class GameBoard extends JPanel implements Runnable, ActionListener, KeyListener {
    private static final int ANIMATION_RATE = 10;
    private static final int X_CELLS = 20;
    private static final int Y_CELLS = 30;
    private static final int BOARD_WIDTH = 150;
    private static final int BOARD_HEIGHT = 200;
    private static final Random random_ = new Random(System.currentTimeMillis());

    private final JFrame frame_;
    private final Timer timer_;
    private int xPosition = X_CELLS/2;
    private int yPosition = 0;
    private int velocity = +1;
    private final Shape[] shapes_;
    private Shape currentShape_;
    private Color currentColor_;
    private final Color[][] gameGrid_;

    public GameBoard() {
        frame_ = new JFrame("Tetris");
        frame_.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame_.setFocusable(true);
        frame_.addKeyListener(this);

        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        
        timer_ = new Timer(ANIMATION_RATE,this);

        shapes_ = new Shape[]{ Shape.createLineShape(this),
                               Shape.createTShape(this),
                               Shape.createLShape(this),
                               Shape.createZigZagShape(this),
                               Shape.createSquareShape(this) };
        randomizeCurrentShape();
        randomizeCurrentColor();
        gameGrid_ = new Color[X_CELLS][Y_CELLS];
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

    public int getCellWidth() {
        return getWidth()/X_CELLS;
    }

    public int getCellHeight() {
        return getHeight()/Y_CELLS;
    }

    public void actionPerformed(ActionEvent e) {
        if (yPosition > (getHeight()-currentShape_.getHeight())) {
            for(Point p: currentShape_.getPoints())
            {
               gameGrid_[xPosition * p.x][yPosition * p.y] = currentColor_; 
            }
            yPosition = 0;
            randomizeCurrentShape();
            randomizeCurrentColor();
        }
        else
            yPosition += velocity;
        repaint();
    } 
    public void paintComponent(Graphics g) {
        g.clearRect(0,0,getWidth(),getHeight());

        g.setColor(currentColor_);

        for(Point p: currentShape_.getPoints())
            drawCell(g, xPosition + p.x, yPosition + p.y);

        for(int x = 0; x < X_CELLS; x++)
            for(int y = 0; y < Y_CELLS; y++)
            {
                Color c = gameGrid_[x][y];
                if(c != null)
                {
                    g.setColor(c);
                    drawCell(g, x, y);
                }
            }
    }

    public void drawCell(Graphics g, int x, int y)
    {
            g.fillRect(x * getCellWidth() + 1,
                       y * getCellHeight() + 1,
                       getCellWidth() - 2,
                       getCellHeight() - 2);
    }
    public void keyPressed(KeyEvent e)
    {	
        if(e.getKeyCode() == KeyEvent.VK_LEFT &&
                xPosition > 0) 
            xPosition -= getCellWidth();
        else if(e.getKeyCode() == KeyEvent.VK_RIGHT && 
                xPosition <= (getWidth() - currentShape_.getWidth()*2)) 
            xPosition += getCellWidth();
        else if(e.getKeyCode() == KeyEvent.VK_UP)
            currentShape_.rotate();
        else if(e.getKeyCode() == KeyEvent.VK_DOWN)
        {
        }

    }
    public void keyReleased(KeyEvent e){}
    public void keyTyped(KeyEvent e){}
}
