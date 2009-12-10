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
import edu.cuny.brooklyn.tetris.Velocity;
import edu.cuny.brooklyn.tetris.grid.ColoredGrid;
import javax.swing.JOptionPane;

/**
 * Actual gameboard that contains a ColoredGrid and different
 * listeners to handle events. This class runs the actual game
 * executing user events and updates the ColoredGrid. It also 
 * sets messages for the user upon game over.
 *
 * @author Ramin Rakhamimov
 * @see Shape
 * @see ColoredGrid
 */

public class GameBoard implements Runnable, ActionListener, KeyListener 
{
    private static final int ANIMATION_RATE = 300;
    private static final int X_CELLS = 15;
    private static final int Y_CELLS = 20;

    private final JFrame frame_;
    private final Timer timer_;
    private int xPosition_;
    private int yPosition_;
    private final Velocity velocity_;

    private Shape currentShape_;
    private Shape previousShape_;

    private ColoredGrid cellGrid_;

    /**
     * Constructs a GameBoard
     */
    public GameBoard()
    {
        frame_ = new JFrame("Tetris");
        frame_.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame_.setFocusable(true);
        frame_.setJMenuBar(new TetrisMenu(this));

        cellGrid_ = new ColoredGrid(X_CELLS, Y_CELLS);
        timer_ = new Timer(ANIMATION_RATE,this);
        frame_.addKeyListener(this);

        velocity_ = new Velocity(1);
        resetGame();

    }

    /**
     * Sets the game's paused state.
     */
    public void setPaused(boolean b)
    {
        if(b)
            timer_.stop();
        else
            timer_.restart();
    }

    /**
     * Resets all states of the game.
     */
    final public void resetGame()
    {
        cellGrid_.clearAll();
        refreshState();
        timer_.stop();
        velocity_.setTemporaryVelocity(null);
    }

    final private void refreshState() {
        currentShape_ = Shape.randomShape();
        yPosition_ = 0;
        xPosition_ = X_CELLS/2 - currentShape_.getWidth()/2;
    }

    /**
     * Runs the game.
     */
    public void run()
    {
        frame_.add(cellGrid_);
        frame_.pack();
        frame_.setVisible(true);
        timer_.start();
    }

    /**
     * Updates game state on each timer iteration.
     */
    public void actionPerformed(ActionEvent e)
    {
        yPosition_ += velocity_.getVelocity();
        Shape movedShape = currentShape_.move(xPosition_, yPosition_);

        if(cellGrid_.collidesWith(movedShape, ColoredGrid.BOTTOM_CELL)) 
        {
            if(yPosition_ < currentShape_.getHeight())
            {
                cellGrid_.setMessage("Nice Try!");
                cellGrid_.repaint();
                resetGame();
                return;
            }
            else
            {
                cellGrid_.addShape(movedShape);
                refreshState();
            }
        }
        else
        {
            cellGrid_.setTemporaryShape(movedShape);
        }

        previousShape_ = movedShape;
        cellGrid_.repaint();
        velocity_.setTemporaryVelocity(null);
    } 


    /**
     * Handles user key presses, shifts and rotations.
     * @param e KeyEvent type
     */
    public void keyPressed(KeyEvent e)
    {	
        timer_.restart();

        if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            Shape movedShape = currentShape_.move(xPosition_, yPosition_);
            if(!cellGrid_.collidesWith(movedShape, ColoredGrid.LEFT_CELL)){
                xPosition_--;
                velocity_.setTemporaryVelocity(0);
                actionPerformed(null);
            }
        }
        else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            Shape movedShape = currentShape_.move(xPosition_, yPosition_);
            if(!cellGrid_.collidesWith(movedShape, ColoredGrid.RIGHT_CELL)){
                xPosition_++;
                velocity_.setTemporaryVelocity(0);
                actionPerformed(null);
            }
        }
        else if(e.getKeyCode() == KeyEvent.VK_UP){
            currentShape_.rotate();
            velocity_.setTemporaryVelocity(0);
            actionPerformed(null);
        }
        else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
            actionPerformed(null);
        }
    }

    public void keyReleased(KeyEvent e){}
    public void keyTyped(KeyEvent e){}
}
