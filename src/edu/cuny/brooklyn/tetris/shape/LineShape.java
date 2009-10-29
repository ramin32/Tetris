package edu.cuny.brooklyn.tetris.shape;

import java.awt.Graphics;

public class LineShape implements Shape
{
    private final int cellSize_;
    public LineShape(int cellSize) 
    {
        cellSize_ = cellSize;
    }

    public int getWidth()
    {
        return 4*cellSize_;
    }

    public int getHeight()
    {
        return 1*cellSize_;
    }

    public void draw(Graphics g, int x, int y)
    {
        for(int i = 0; i < 4; i++)
            g.fillRect(x+i*cellSize_,y,cellSize_,cellSize_);
    }
}
