package edu.cuny.brooklyn.tetris;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

/**
 * Menu for the game.
 *
 * @author Ramin Rakhamimov
 * @author Jonathan Weinblatt
 */

public class TetrisMenu extends JMenuBar implements ActionListener
{
    private static final String FILE_STRING = "File";
    private static final String RESET_STRING = "Reset";
    private static final String EXIT_STRING = "Exit";
    private static final String ABOUT_STRING = "About";
    private static final String ABOUT_MESSAGE = 
        "Thanks for playing Tetris!\n" +
        "Written by Ramin Rakhamimov.\n" + 
        "http://raminrakhamimov.tk";

    private final GameBoard board_;

    /**
     * Constructs a menu based on a gameboard.
     */
    public TetrisMenu(GameBoard board)
    {
        board_ = board;
        add(getFileMenu(board));
        add(getAboutMenu());
    }

    private JMenu getFileMenu(GameBoard board) 
    {
        JMenu fileMenu = new JMenu(FILE_STRING);
        JMenuItem reset = new JMenuItem(RESET_STRING);
        JMenuItem exit = new JMenuItem(EXIT_STRING);

        fileMenu.add(reset);
        fileMenu.add(exit);
        
        reset.addActionListener(this);
        exit.addActionListener(this);
        return fileMenu;
    }

    private JMenu getAboutMenu() 
    {
        JMenu aboutMenu = new JMenu(ABOUT_STRING);
        JMenuItem about = new JMenuItem(ABOUT_STRING);
        
        aboutMenu.add(about);

        about.addActionListener(this);

        
        return aboutMenu;
    }


    /**
     * Handles events
     */
    public void actionPerformed(ActionEvent e)
    {
        board_.setPaused(true);
        if(e.getActionCommand() == EXIT_STRING)
            System.exit(1);
        else if(e.getActionCommand() == RESET_STRING)
            board_.resetGame();

        else if(e.getActionCommand() == ABOUT_STRING)
            JOptionPane.showMessageDialog(null, ABOUT_MESSAGE);
        board_.setPaused(false);
    }

}
