import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class Grid extends JPanel implements ActionListener
{
	private int dim;
	private Cell[][] grid;
	private BufferedImage[][] picsGrid;
	
	public Grid(int dim)
	{
		this.dim = dim;
		grid = new Cell[dim][dim];
		picsGrid = new BufferedImage[dim][dim];
	}
	
	public void setDim(int dim) { this.dim = dim; }
	public Cell[][] getGrid() { return grid; }
	public void setGrid(Cell[][] grid) { this.grid = grid; }
	
    public void addActionListener(ActionListener listener) { listenerList.add(ActionListener.class, listener); }
    public void removeActionListener(ActionListener listener) { listenerList.remove(ActionListener.class, listener); }
	private void fireActionPerformed(Object e)
	{
        ActionListener[] listeners;
        
        listeners = listenerList.getListeners(ActionListener.class);
        if (listeners != null)
			if (listeners.length > 0)
				listeners[0].actionPerformed(new ActionEvent(e, ActionEvent.ACTION_PERFORMED, ""));
    }
	
	public void initPicture(BufferedImage currentImage)
	{
		BufferedImage out;
		Graphics2D gD;
		int x, y, relativeDim, WIN_DIM;
		
		WIN_DIM = View.WIN_DIM;
		out = new BufferedImage(WIN_DIM, WIN_DIM, BufferedImage.TYPE_INT_ARGB);
		gD = out.createGraphics();
		gD.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		gD.drawImage(currentImage, 0, 0, WIN_DIM, WIN_DIM, null);
		gD.dispose();
		relativeDim = WIN_DIM / dim;
		removeAll();
		grid = new Cell[dim][dim];
		picsGrid = new BufferedImage[dim][dim];
		for (y = 0; y < dim; y++)
			for (x = 0; x < dim; x++)
			{
				grid[x][y] = new Cell();
				grid[x][y].setCoord(new Point(x, y));
				grid[x][y].setBounds(x * relativeDim, y * relativeDim, relativeDim, relativeDim);
				if (x == dim - 1 && y == dim - 1)
					picsGrid[x][y] = new BufferedImage(relativeDim,  relativeDim, BufferedImage.TYPE_INT_ARGB);
				else
					picsGrid[x][y] = out.getSubimage(x * relativeDim, y * relativeDim, relativeDim, relativeDim);
				grid[x][y].setPicture(picsGrid[x][y]);
				add(grid[x][y]);
				grid[x][y].addActionListener(this);
			}
	}
	public void organizeGrid(int[][] grid)
	{
		int x, y, subx, suby;

		for (y = 0; y < dim; y++)
			for (x = 0; x < dim; x++)
			{
				subx = grid[x][y] % dim;
				suby = grid[x][y] / dim;
				this.grid[x][y].setPicture(picsGrid[subx][suby]);
			}
	}
	public void invertCells(int x1, int y1, int x2, int y2)
	{
		BufferedImage picture;
		
		picture = grid[x1][y1].getPicture();
		grid[x1][y1].setPicture(grid[x2][y2].getPicture());
		grid[x2][y2].setPicture(picture);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		fireActionPerformed(e.getSource());
	}
}