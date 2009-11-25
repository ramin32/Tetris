package edu.cuny.brooklyn.tetris.grid;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.util.List;
import java.util.ArrayList;
import java.lang.IllegalArgumentException;

import edu.cuny.brooklyn.tetris.shape.Shape;

/**
 * Data structure for handling the grid of the game.
 * Has a grid of size MxN where each cell contains a color.
 * On each repaint all the cells with non-null colors get painted.
 *
 * If a temporary Shape object is set that is painted as well
 * within the grid.
 *
 * @author Ramin Rakhamimov
 * @author Jonathan Weinblatt
 * @see Shape
 */

public class ColoredGrid extends JPanel 
{
    public static final Point LEFT_CELL   = new Point(-1, 0);
    public static final Point RIGHT_CELL  = new Point(+1, 0);
    public static final Point TOP_CELL    = new Point(0, -1);
    public static final Point BOTTOM_CELL = new Point(0, +1);

    private Color[][] grid_;
    private Shape temporaryShape_;
    private int[] horizontalLines_;
    private int CELL_WIDTH = 15;

    private final int xCells_;
    private final int yCells_;
    private String message_;

    /**
     * Constructs a Colored grid of xCells X yCells size.
     * @param xCells
     * @param yCells
     */
    public ColoredGrid(int xCells, int yCells) 
    {
        grid_ = getNewGrid();
        horizontalLines_ = new int[yCells];

        xCells_ = xCells;
        yCells_ = yCells;

        setPreferredSize(new Dimension(xCells_ * CELL_WIDTH, 
                    yCells_ * CELL_WIDTH));
    }

    private final Color[][] getNewGrid()
    {
        return new Color[xCells_][yCells_];
    }

    /**
     * Sets a color at location of Point p.
     * @param p point within the grid
     * @param c color to set
     */
    public void add(Point p, Color c)
    { 
        horizontalLines_[p.y]++;
        if(horizontalLines_[p.y] < xCells_)
            grid_[p.x][p.y] = c;
        else
        {
            horizontalLines_[p.y] = 0;
            for(int y = p.y; y > 0; y--)
            {
                for(int x = 0; x < xCells_; x++)
                {
                    if(grid_[x][y-1] != null)
                    {
                        horizontalLines_[y-1]--;
                        horizontalLines_[y]++;
                    }
                    grid_[x][y] = grid_[x][y-1];
                }
            }
            //Clear first row
            horizontalLines_[0] = 0;
            for(int x = 0; x < xCells_; x++)
                grid_[x][0] = null;
        }

    }

    /**
     * Sets message to be displayed within the grid.
     * Gets flushed on each repaint.
     * @param str message to display
     */
    public void setMessage(String str)
    {
        message_ = str;
    }

    /**
     * Adds all the cells of a shape to the grid.
     * @param shape shape to add
     */
    public void addShape(Shape shape)
    {
        for(Point p: shape.getPoints()){
            add(p,shape.getColor());
        }
    }


    /**
     * Sets the temporary shape.
     * Gets flushed on each repaint.
     * @param shape shape to set
     */
    public void setTemporaryShape(Shape shape)
    {
        temporaryShape_ = shape;
    }

    /**
     * Resets the grid.
     */
    public void clearAll()
    {
        grid_ = getNewGrid();
        temporaryShape_ = null;
        horizontalLines_ = new int[yCells_];
    }

    /**
     * Checks if a shape collides with any point in the grid.
     * Shape is moved to given point from (0,0).
     * @param shape given shape
     * @param point offset point
     */
    public boolean collidesWith(Shape shape, Point point)
    {
        try
        {
            for(Point p: shape.getPoints())
                if(grid_[p.x + point.x][p.y + point.y] != null)
                    return true;
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            return true;
        }
        return false;
    }

    /**
     * Returns the cell width.
     */
    public int getCellWidth() 
    {
        return getWidth()/xCells_;
    }

    /**
     * Returns the cell height.
     */
    public int getCellHeight() 
    {
        return getHeight()/yCells_;
    }

    /**
     * Repaints all the cells in the grid including the temporaryShape.
     * Flushes temporaryShape and message on each repaint.
     * @param g graphics object
     */
    public void paintComponent(Graphics g) 
    {
        g.clearRect(0,0,getWidth(),getHeight());
        for(int x = 0; x < xCells_; x++)
        {
            for(int y = 0; y < yCells_; y++)
            {
                if(grid_[x][y] != null)
                { 
                    g.setColor(grid_[x][y]);
                    drawCell(g,x,y);
                }
            }
        }

        // Flush temporaryShape
        if(temporaryShape_ != null)
        {
            g.setColor(temporaryShape_.getColor());
            for(Point p: temporaryShape_.getPoints())
                drawCell(g, p.x, p.y);
            temporaryShape_ = null;
        }

        // Flush message
        if(message_ != null)
        {
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString(message_, getWidth()/4, getHeight()/2);
            message_ = null;
        }
    }


    /**
     * Draws a cell at position (x,y)
     * @param g
     * @param x
     * @param y
     */
    public void drawCell(Graphics g, int x, int y)
    {
        g.fillRect(x * getCellWidth() + 2,
                y * getCellHeight() + 2,
                getCellWidth() - 4,
                getCellHeight() - 4);

        Color previousColor = g.getColor();
        g.setColor(Color.BLACK);
        g.drawRect(x * getCellWidth() + 1,
                y * getCellHeight() + 1,
                getCellWidth() - 2,
                getCellHeight() - 2);
        g.setColor(previousColor);
    }

}

