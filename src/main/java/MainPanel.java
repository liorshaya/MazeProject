import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MainPanel extends JPanel {
    private BufferedImage img;

    public MainPanel(int x, int y, int width, int height, BufferedImage img){
        this.img = img;

        this.setBounds(x, y, width, height);
        this.setLayout(null);

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(img, getWidth()/2 - 50, getHeight()/2 - 50, this);
    }
}
