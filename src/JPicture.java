import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.Objects;
import javax.swing.*;
import javax.imageio.*;

public class JPicture extends JComponent {
    protected BufferedImage picture;

    public JPicture(String filePath, int x, int y, int width, int height) {
        super();
        if (filePath == null)
            picture = null;
        else
            try {
                picture = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(filePath)));
            } catch (IOException | IllegalArgumentException e) {
                picture = null;
            }
        setBounds(x, y, width, height);
    }

    public JPicture() {
        this(null, 0, 0, 0, 0);
    }

    public BufferedImage getPicture() {
        return picture;
    }

    public void setPicture(BufferedImage picture) {
        this.picture = picture;
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (picture != null)
            g.drawImage(picture, 0, 0, getWidth(), getHeight(), this);
    }
}
