package edu.cuny.brooklyn.tetris.shape;

import java.awt.Graphics;

public interface Shape 
{
    enum Orientation {Top, Bottom, Left, Right}

    int getWidth();
    int getHeight();

    void draw(Graphics g, int x, int y);
}
