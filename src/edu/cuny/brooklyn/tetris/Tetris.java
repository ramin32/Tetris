package edu.cuny.brooklyn.tetris;

import javax.swing.SwingUtilities;

/**
 * Main class that runs the Game through GameBoard
 *
 * @author Ramin Rakhamimov
 * @see GameBoard
 */

public class Tetris {
    public static void main(String... args) {
        SwingUtilities.invokeLater(new GameBoard());
    }
}

