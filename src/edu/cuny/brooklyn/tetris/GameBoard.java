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
import edu.cuny.brooklyn.tetris.cell.ColoredCellGrid;
import edu.cuny.brooklyn.tetris.cell.ColoredCell;

public class GameBoard implements Runnable, ActionListener, KeyListener 
{
    private static final int ANIMATION_RATE = 100;
    private static final int X_CELLS = 20;
    private static final int Y_CELLS = 30;

    private final JFrame frame_;
    private final Timer timer_;
    private int xPosition = X_CELLS/2;
    private int yPosition = 0;
    private int velocity = +1;
    private Shape currentShape_;
    private final ColoredCellGrid cellGrid_;

    public GameBoard()
    {
        frame_ = new JFrame("Tetris");
        frame_.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame_.setFocusable(true);
        frame_.addKeyListener(this);

        timer_ = new Timer(ANIMATION_RATE,this);

        currentShape_ = Shape.randomShape();
        cellGrid_ = new ColoredCellGrid(X_CELLS, Y_CELLS);
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
        yPosition += velocity;

        List<ColoredCell> cells = new ArrayList<ColoredCell>();
        for(Point p: currentShape_.getPoints())
        {
            int x = p.x + xPosition;
            int y = p.y + yPosition;

            cells.add(new ColoredCell(x,y,currentShape_.getColor()));
        }
        cellGrid_.addTemporaryCells(cells);
        cellGrid_.repaint();

        if(yPosition >= Y_CELLS - currentShape_.getHeight()*2)
        {
            cellGrid_.addPermenantCells(cells);
            yPosition = 0;
            currentShape_ = Shape.randomShape();
        }
    } 

    public void keyPressed(KeyEvent e)
    {	
        if(e.getKeyCode() == KeyEvent.VK_LEFT &&
                xPosition > 0) 
            xPosition -= 1;
        else if(e.getKeyCode() == KeyEvent.VK_RIGHT && 
                xPosition < (X_CELLS - currentShape_.getWidth()*2)) 
            xPosition += 1;
        else if(e.getKeyCode() == KeyEvent.VK_UP)
            currentShape_.rotate();
        else if(e.getKeyCode() == KeyEvent.VK_DOWN)
        {
        }

    }
    public void keyReleased(KeyEvent e){}
    public void keyTyped(KeyEvent e){}
}
