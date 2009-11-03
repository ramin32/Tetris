package edu.cuny.brooklyn.tetris;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import javax.swing.Timer;
import javax.swing.SwingUtilities;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.awt.Point;

import edu.cuny.brooklyn.tetris.shape.Shape;
import edu.cuny.brooklyn.tetris.grid.ColoredGrid;
import javax.swing.JOptionPane;

public class GameBoard implements Runnable, ActionListener, KeyListener 
{
    private static final int ANIMATION_RATE = 300;
    private static final int X_CELLS = 15;
    private static final int Y_CELLS = 20;

    private final JFrame frame_;
    private final Timer timer_;
    private int xPosition_;
    private int yPosition_;
    private int velocity_ = 1;

    private Shape currentShape_;
    private Shape previousShape_;

    private ColoredGrid cellGrid_;


    public GameBoard()
    {
        frame_ = new JFrame("Tetris");
        frame_.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame_.setFocusable(true);
        frame_.addKeyListener(this);
        frame_.setJMenuBar(new TetrisMenu(this));

        cellGrid_ = new ColoredGrid(X_CELLS, Y_CELLS);
        timer_ = new Timer(ANIMATION_RATE,this);
        resetGame();

    }

    final public void resetGame()
    {
        cellGrid_.clearAll();
        refreshState();
    }
    final private void refreshState() {
        currentShape_ = Shape.randomShape();
        yPosition_ = 0;
        xPosition_ = X_CELLS/2 - currentShape_.getWidth()/2;
    }

    public void run()
    {
        frame_.add(cellGrid_);
        frame_.pack();
        frame_.setVisible(true);

        timer_.start();
    }


    public void actionPerformed(ActionEvent e)
    {
        yPosition_ += velocity_;
        Shape movedShape = currentShape_.move(xPosition_, yPosition_);

        if(yPosition_ >= (Y_CELLS - currentShape_.getHeight()))
        {
            cellGrid_.addPermenantCells(movedShape.getPoints(), 
                                        movedShape.getColor());
            refreshState();
        }

        else
        {
            cellGrid_.addTemporaryCells(movedShape.getPoints(), 
                                        movedShape.getColor());
        }

        previousShape_ = movedShape;
        cellGrid_.repaint();
    } 


    public void keyPressed(KeyEvent e)
    {	
        if(e.getKeyCode() == KeyEvent.VK_LEFT &&
                xPosition_ > 0) {
            xPosition_ -= 1;
            velocity_ = 0;
        }
        else if(e.getKeyCode() == KeyEvent.VK_RIGHT && 
                xPosition_ < X_CELLS - currentShape_.getWidth()) {
            xPosition_ += 1;
            velocity_ = 0;
        }
        else if(e.getKeyCode() == KeyEvent.VK_UP)
            currentShape_.rotate();
        else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
            actionPerformed(null);
        }
    }
    public void keyReleased(KeyEvent e)
    {
        if(e.getKeyCode() == KeyEvent.VK_LEFT || 
                e.getKeyCode() == KeyEvent.VK_RIGHT)
            velocity_ = 1;
    }
    public void keyTyped(KeyEvent e){}
}
