package edu.cuny.brooklyn.tetris;

import javax.swing.SwingUtilities;


public class Tetris {
    public static void main(String... args) {
        SwingUtilities.invokeLater(new GameBoard());
    }
}

