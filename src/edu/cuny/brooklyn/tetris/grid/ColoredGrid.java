package edu.cuny.brooklyn.tetris.grid;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Point;
import java.util.List;
import java.util.ArrayList;
import java.lang.IllegalArgumentException;

public class ColoredGrid extends JPanel 
{
    private Color[][] permenantCells_;
    private Color[][] temporaryCells_;
    private final int[] horizontalLines_;
    private int CELL_WIDTH = 15;

    private final int xCells_;
    private final int yCells_;
    
    public ColoredGrid(int xCells, int yCells) 
    {
        permenantCells_ = getNewGrid();
        temporaryCells_ = getNewGrid();
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

    public void addPermenantCells(Point[] points, Color color)
    {
        for(Point p: points)
            permenantCells_[p.x][p.y] = color;
    }

    public void addTemporaryCells(Point[] points, Color color)
    {
        for(Point p: points)
            temporaryCells_[p.x][p.y] = color;
    }

    public void clearAll()
    {
        permenantCells_ = getNewGrid();
        temporaryCells_ = getNewGrid();
    }

    public int getCellWidth() 
    {
        return getWidth()/xCells_;
    }

    public int getCellHeight() 
    {
        return getHeight()/yCells_;
    }

    public void paintComponent(Graphics g) 
    {
        g.clearRect(0,0,getWidth(),getHeight());
        for(int x = 0; x < xCells_; x++)
        {
            for(int y = 0; y < yCells_; y++)
            {
                if(permenantCells_[x][y] != null)
                { 
                    g.setColor(permenantCells_[x][y]);
                    drawCell(g,x,y);
                }
                else if(temporaryCells_[x][y] != null)
                { 
                    g.setColor(temporaryCells_[x][y]);
                    drawCell(g,x,y);
                }
            }
        }
        temporaryCells_ = getNewGrid();
    }


    public void drawCell(Graphics g, int x, int y)
    {
        g.fillRect(x * getCellWidth() + 2,
                   y * getCellHeight() + 2,
                   getCellWidth() - 4,
                   getCellHeight() - 4);

        g.setColor(Color.BLACK);
        g.drawRect(x * getCellWidth() + 1,
                   y * getCellHeight() + 1,
                   getCellWidth() - 2,
                   getCellHeight() - 2);
    }

}

