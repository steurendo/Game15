import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.*;
import javax.swing.filechooser.*;

public class View extends JFrame {
    public final static int WIN_DIM = 480;

    private Controller controller;
    private Model model;
    private JMenuBar menuBar;
    private JMenuItem menuLoadImage;
    private JMenuItem menuAdd;
    private JMenuItem menuRemove;
    private JButton buttonShuffle;
    private BufferedImage currentImage;
    private final Grid panelGrid;
    private boolean isDefaultPicture;
    private boolean isStarted;

    public View() {
        model = new Model();
        controller = new Controller(this, model);

        setTitle("15 Puzzle");
        setSize(WIN_DIM + 6, WIN_DIM + 14 + 40 + 40);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);

        menuBar = new JMenuBar();
        menuBar.setBounds(0, 0, WIN_DIM, 25);
        menuLoadImage = new JMenuItem("Load Image");
        menuLoadImage.setName("menuLoadImage");
        menuLoadImage.addActionListener(controller);
        menuAdd = new JMenuItem("+");
        menuAdd.setName("menuAdd");
        menuAdd.addActionListener(controller);
        menuRemove = new JMenuItem("-");
        menuRemove.setName("menuRemove");
        menuRemove.addActionListener(controller);
        buttonShuffle = new JButton("Shuffle");
        buttonShuffle.setName("buttonShuffle");
        buttonShuffle.setBounds(WIN_DIM / 2 - 50, WIN_DIM + 30, 100, 30);
        buttonShuffle.addActionListener(controller);
        panelGrid = new Grid(model.getDim());
        panelGrid.setLayout(null);
        panelGrid.setBounds(0, 25, WIN_DIM, WIN_DIM);
        panelGrid.addActionListener(controller);
        panelGrid.setName("panelGrid");
        isDefaultPicture = true;
        initPicture();

        add(menuBar);
        menuBar.add(menuLoadImage);
        menuBar.add(menuAdd);
        menuBar.add(menuRemove);
        menuBar.add(new JMenuItem());
        add(buttonShuffle);
        add(panelGrid);
    }

    public void start() {
        setVisible(true);
    }

    public void update(int x, int y, int move) {
        int x2, y2;

        x2 = x;
        y2 = y;
        if (move == 0)
            y2--;
        if (move == 1)
            x2++;
        if (move == 2)
            y2++;
        if (move == 3)
            x2--;
        panelGrid.invertCells(x, y, x2, y2);
        repaint();
        if (model.isGridCompleted() && isStarted) {
            JOptionPane.showMessageDialog(null, "Congratulations! You won the game!");
            isStarted = false;
        }
    }

    public void loadImage() {
        JFileChooser fc;

        fc = new JFileChooser("");
        fc.setFileFilter(new FileNameExtensionFilter("Images", "jpg", "jpeg", "png", "gif", "bmp"));
        fc.showOpenDialog(this);
        if (fc.getSelectedFile() != null) {
            try {
                currentImage = ImageIO.read(fc.getSelectedFile());
                isDefaultPicture = false;
                model.init();
                redrawGrid(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        model.init();
    }

    public void redrawGrid(boolean gameStarted) {
        isStarted = gameStarted;
        initPicture();
    }

    public void initPicture() {
        panelGrid.setDim(model.getDim());
        if (isDefaultPicture) {
            Graphics gD;
            int x, y, relativeDim, subx, suby;
            String num;

            currentImage = new BufferedImage(WIN_DIM, WIN_DIM, BufferedImage.TYPE_INT_ARGB);
            gD = currentImage.getGraphics();
            gD.setColor(Color.WHITE);
            gD.fillRect(0, 0, WIN_DIM, WIN_DIM);
            relativeDim = WIN_DIM / model.getDim();
            for (y = 0; y < model.getDim(); y++)
                for (x = 0; x < model.getDim(); x++) {
                    FontMetrics fm;

                    num = "" + (y * model.getDim() + x + 1);
                    gD.setFont(new Font("Arial", Font.BOLD, 25 + 35 - (int) (35 * ((float) (model.getDim() - Model.DIM_MIN) / (Model.DIM_MAX - Model.DIM_MIN)))));
                    fm = gD.getFontMetrics();
                    subx = (relativeDim - fm.stringWidth(num)) / 2;
                    suby = (relativeDim - fm.getHeight()) / 2 + fm.getAscent();
                    gD.setColor(Color.black);
                    gD.drawString("" + (y * model.getDim() + x + 1), x * relativeDim + subx, y * relativeDim + suby);
                }
        }
        panelGrid.initPicture(currentImage);
        panelGrid.organizeGrid(model.getGrid());
        repaint();
    }
}