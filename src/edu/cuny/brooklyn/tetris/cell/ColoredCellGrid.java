package edu.cuny.brooklyn.tetris.cell;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import java.util.List;
import java.util.ArrayList;

public class ColoredCellGrid extends JPanel 
{
    private List<ColoredCell> permenantCells_;
    private List<ColoredCell> temporaryCells_;
    private int CELL_WIDTH = 10;

    private final int xCells_;
    private final int yCells_;
    
    public ColoredCellGrid(int xCells, int yCells) 
    {
        permenantCells_ = new ArrayList<ColoredCell>();
        temporaryCells_ = new ArrayList<ColoredCell>();
        xCells_ = xCells;
        yCells_ = yCells;

        setPreferredSize(new Dimension(xCells_ * CELL_WIDTH, 
                                       yCells_ * CELL_WIDTH));
    }

    public void addPermenantCells(List<ColoredCell> cells)
    {
        permenantCells_.addAll(cells);
    }
    public boolean contains(ColoredCell cell) 
    {
        return permenantCells_.contains(cell);
    }

    public void addTemporaryCells(List<ColoredCell> cells)
    {
        temporaryCells_.addAll(cells);
    }

    public int getCellWidth() 
    {
        return getWidth()/xCells_;
    }

    public int getCellHeight() 
    {
        return getHeight()/yCells_;
    }

    public void paintComponent(Graphics g) 
    {
        g.clearRect(0,0,getWidth(),getHeight());
        drawCells(g, permenantCells_);
        drawCells(g, temporaryCells_);
        temporaryCells_.clear();
    }

    private void drawCells(Graphics g, List<ColoredCell> cellList) 
    {
        for(ColoredCell cell: cellList)
        {
            g.setColor(cell.getColor());
            drawCell(g, cell.getX(), cell.getY());
        }
    }

    public void drawCell(Graphics g, int x, int y)
    {
        g.fillRect(x * getCellWidth() + 2,
                   y * getCellHeight() + 2,
                   getCellWidth() - 4,
                   getCellHeight() - 4);

        g.setColor(Color.BLACK);
        g.drawRect(x * getCellWidth() + 1,
                   y * getCellHeight() + 1,
                   getCellWidth() - 2,
                   getCellHeight() - 2);
    }

}

