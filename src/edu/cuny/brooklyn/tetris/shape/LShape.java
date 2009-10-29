package edu.cuny.brooklyn.tetris.shape;

import java.awt.Graphics;

public class LShape implements Shape
{
    private final int cellSize_;
    public LShape(int cellSize) 
    {
        cellSize_ = cellSize;
    }

    public int getWidth()
    {
        return 3*cellSize_;
    }

    public int getHeight()
    {
        return 2*cellSize_;
    }

    public void draw(Graphics g, int x, int y)
    {
        g.fillRect(x,y,cellSize_,cellSize_);
        g.fillRect(x,y+cellSize_,cellSize_,cellSize_);
        g.fillRect(x+cellSize_,y+cellSize_,cellSize_,cellSize_);
        g.fillRect(x+cellSize_*2,y+cellSize_,cellSize_,cellSize_);
    }
}
