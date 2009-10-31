package edu.cuny.brooklyn.tetris.shape;

import java.awt.Point;

public interface ShapeVector {

    Point[] LINE = { 
        new Point(0,0),
        new Point(1,0),
        new Point(2,0),
        new Point(3,0) 
    };

    Point[] T = { 
        new Point(1,0),
        new Point(0,1),
        new Point(1,1),
        new Point(2,1) 
    };

    Point[] SQUARE = { 
        new Point(0,0),
        new Point(0,1),
        new Point(1,0),
        new Point(1,1) 
    };

    Point[] L = { 
        new Point(0,0),
        new Point(0,1),
        new Point(1,1),
        new Point(2,1) 
    };

    Point[] ZIG_ZAG = { 
        new Point(0,0),
        new Point(1,0),
        new Point(1,1),
        new Point(2,1) 
    };

    Point[][] VECTORS = {T, L, LINE, ZIG_ZAG, SQUARE};
}
