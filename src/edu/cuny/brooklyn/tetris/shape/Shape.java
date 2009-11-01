package edu.cuny.brooklyn.tetris.shape;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Color;
import java.util.List;
import java.util.Random;

import edu.cuny.brooklyn.tetris.GameBoard;

public class Shape 
{
    public static final int SHAPE_GRID_SIZE = 4;
    private static final Random random_ = new Random(System.currentTimeMillis());

    private Point[] points_;
    private Color color_;

    public Shape(Point[] points, Color color) 
    {
        points_ = points;
        color_ = color;
        int x = 5;
    }

    public static Shape randomShape() 
    {
        return new Shape(ShapeVector.VECTORS[random_.nextInt(ShapeVector.VECTORS.length)],
                         randomColor());
    }

    private static Color randomColor()
    {
        return new Color(random_.nextInt(256),
                random_.nextInt(256),
                random_.nextInt(256));
    }
    
    public int getWidth()
    {
        int width = 0;
        for(Point p: points_)
            width = Math.max(width,p.x);
        return width;
    }

    public int getHeight()
    {
        int height = 0;
        for(Point p: points_)
            height = Math.max(height,p.y);
        return  height;
    }

    public Point[] getPoints()
    {
        return points_;
    }

    public Color getColor()
    {
        return color_;
    }

    public void rotate()
    {
        int minX = SHAPE_GRID_SIZE;
        int minY = SHAPE_GRID_SIZE;
        for(Point p: points_) {
            p.move(p.y, SHAPE_GRID_SIZE - 1 - p.x);
            minX = Math.min(minX,p.x);
            minY = Math.min(minY,p.y);
        }

        // Normalize
        for(Point p: points_) {
                p.x = p.x-minX;
                p.y = p.y-minY;
        }
    }


}
