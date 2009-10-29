package edu.cuny.brooklyn.tetris;

import java.awt.Graphics;
import java.awt.Point;

public class Shape 
{
    public static final Point[] LINE_SHAPE_POINTS = { new Point(0,0),
                                                      new Point(1,0),
                                                      new Point(2,0),
                                                      new Point(3,0) };

    public static final Point[] T_SHAPE_POINTS = { new Point(1,0),
                                                   new Point(0,1),
                                                   new Point(1,1),
                                                   new Point(2,1) };

    public static final Point[] SQUARE_SHAPE_POINTS = { new Point(0,0),
                                                        new Point(0,1),
                                                        new Point(1,0),
                                                        new Point(1,1) };

    public static final Point[] L_SHAPE_POINTS = { new Point(0,0),
                                                   new Point(0,1),
                                                   new Point(1,1),
                                                   new Point(2,1) };

    public static final Point[] ZIG_ZAG_SHAPE_POINTS = { new Point(0,0),
                                                         new Point(1,0),
                                                         new Point(1,1),
                                                         new Point(2,1) };
    private final int cellSize_;
    private Point[] shapePoints_;

    public Shape(int cellSize, Point[] shapePoints) 
    {
        cellSize_ = cellSize;
        shapePoints_ = shapePoints;
    }

    public static Shape createLShape(int cellSize)
    {
        return new Shape(cellSize, L_SHAPE_POINTS);
    }

    public static Shape createTShape(int cellSize)
    {
        return new Shape(cellSize, T_SHAPE_POINTS);
    }

    public static Shape createSquareShape(int cellSize)
    {
        return new Shape(cellSize, SQUARE_SHAPE_POINTS);
    }

    public static Shape createZigZagShape(int cellSize)
    {
        return new Shape(cellSize, ZIG_ZAG_SHAPE_POINTS);
    }

    public static Shape createLineShape(int cellSize)
    {
        return new Shape(cellSize, LINE_SHAPE_POINTS);
    }

    public int getWidth()
    {
        int width = 0;
        for(Point p: shapePoints_)
            width = Math.max(width,p.x);
        return width;
    }

    public int getHeight()
    {
        int height = 0;
        for(Point p: shapePoints_)
            height = Math.max(height,p.y);
        return height;
    }

    public void draw(Graphics g, int x, int y)
    {
        for(Point p: shapePoints_)
            g.fillRect(x+p.x*cellSize_+1,y+p.y*cellSize_+1,cellSize_-2,cellSize_-2);
    }
}
