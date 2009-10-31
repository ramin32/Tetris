package edu.cuny.brooklyn.tetris.cell;


import java.awt.Color;
import java.awt.Point;

public class ColoredCell {
    private final int x_;
    private final int y_;
    private final Color color_;
    
    public ColoredCell(int x, int y, Color color) {
        x_ = x;
        y_ = y;
        color_ = color;
    }

    public ColoredCell(Point p, Color color) {
        this(p.x, p.y, color);
    }

    public int getX() {
        return x_;
    }

    public int getY() {
        return y_;
    }

    public Color getColor() {
        return color_;
    }

    @Override
    public boolean equals(Object o) {
        if(o == null || !(o instanceof ColoredCell))
            return false;
        ColoredCell other = (ColoredCell) o;
        return (x_ == other.x_) &&
               (y_ == other.y_) &&
               (color_ == other.color_);
    }
}
