import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Controller implements ActionListener
{
	private Model model;
	private View view;
	
	public Controller(View view, Model model)
	{
		this.view = view;
		this.model = model;
	}

	//ActionListener
	@Override
	public void actionPerformed(ActionEvent e)
	{
		JComponent src;
		src = (JComponent)e.getSource();
		if (src.getName().equals("buttonShuffle"))
		{
			model.shuffle();
			view.redrawGrid(true);
		}
		else if (src.getName().equals("menuLoadImage"))
			view.loadImage();
		else if (src.getName().equals("menuAdd"))
		{
			model.addDim();
			view.redrawGrid(false);
		}
		else if (src.getName().equals("menuRemove"))
		{
			model.removeDim();
			view.redrawGrid(false);
		}
		else if (src instanceof Cell)
		{
			Point coord;
			int move;
			Cell cell;
			
			cell = (Cell)e.getSource();
			coord = cell.getCoord();
			move = model.move(coord);
			if (move != -1)
				view.update(coord.x, coord.y, move);
		}
	}
}