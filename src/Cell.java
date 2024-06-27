import java.awt.*;
import java.awt.event.*;

public class Cell extends JPicture implements MouseListener {
    private final static Color COLOR_CELL = new Color(40, 40, 40);
    private final static Color COLOR_CELL_MOUSEOVER = Color.red;
    private final static Color COLOR_CELL_CLICKED = Color.green;
    private final static int CELL_STROKE = 1;

    private boolean mouseOver;
    private boolean mousePressed;
    private Point coord;

    public Cell() {
        super();

        mouseOver = false;
        mousePressed = false;
        addMouseListener(this);
        coord = new Point();
        setName("");
    }

    public Point getCoord() {
        return coord;
    }

    public void setCoord(Point coord) {
        this.coord = coord;
    }

    public void addActionListener(ActionListener listener) {
        listenerList.add(ActionListener.class, listener);
    }

    private void fireActionPerformed() {
        ActionListener[] listeners;

        listeners = listenerList.getListeners(ActionListener.class);
        if (listeners != null)
            if (listeners.length > 0)
                listeners[0].actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, ""));
    }

    @Override
    public void repaint() {
        super.repaint();

        Graphics2D gD;

        gD = picture.createGraphics();
        gD.setStroke(new BasicStroke(3));
        if (mousePressed && mouseOver)
            gD.setColor(COLOR_CELL_CLICKED);
        else if (mouseOver)
            gD.setColor(COLOR_CELL_MOUSEOVER);
        else
            gD.setColor(COLOR_CELL);
        gD.drawRect(0, 0, getWidth() - CELL_STROKE, getHeight() - CELL_STROKE);
        gD.dispose();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        mouseOver = true;
        repaint();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        mouseOver = false;
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mousePressed = true;
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mousePressed = false;
        if (mouseOver)
            fireActionPerformed();
        repaint();
    }
}