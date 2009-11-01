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
    private static final int ANIMATION_RATE = 150;
    private static final int X_CELLS = 20;
    private static final int Y_CELLS = 30;

    private final JFrame frame_;
    private final Timer timer_;
    private int xPosition;
    private int yPosition;
    private final int velocity = 1;
    private Shape currentShape_;
    private ColoredCellGrid cellGrid_;

    private List<ColoredCell> previousCellList_;

    public GameBoard()
    {
        frame_ = new JFrame("Tetris");
        frame_.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame_.setFocusable(true);
        frame_.addKeyListener(this);
        frame_.setJMenuBar(new TetrisMenu(this));

        cellGrid_ = new ColoredCellGrid(X_CELLS, Y_CELLS);
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
        yPosition = 0;
        xPosition = X_CELLS/2 - currentShape_.getWidth()/2;
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

            ColoredCell cell = new ColoredCell(x,y,currentShape_.getColor());
            if(cellGrid_.contains(cell) && previousCellList_ != null) {
                cellGrid_.addPermenantCells(previousCellList_);
                refreshState();
                cellGrid_.repaint();
                return;
            }

            cells.add(cell);
        }
        previousCellList_ = cells;
        cellGrid_.addTemporaryCells(cells);
        cellGrid_.repaint();

        if(yPosition >= (Y_CELLS - currentShape_.getHeight()))
        {
            cellGrid_.addPermenantCells(cells);
            refreshState();
        }
    } 


    public void keyPressed(KeyEvent e)
    {	
        if(e.getKeyCode() == KeyEvent.VK_LEFT &&
                xPosition > 0) 
            xPosition -= 1;
        else if(e.getKeyCode() == KeyEvent.VK_RIGHT && 
                xPosition < X_CELLS - currentShape_.getWidth()) 
            xPosition += 1;
        else if(e.getKeyCode() == KeyEvent.VK_UP)
            currentShape_.rotate();
        else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
            actionPerformed(null);
        }
    }
    public void keyReleased(KeyEvent e){}
    public void keyTyped(KeyEvent e){}
}
