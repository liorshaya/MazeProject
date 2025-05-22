import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.css.RGBColor;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;

public class Main {
    public static final int WINDOW_WIDTH = 500;
    public static final int WINDOW_HEIGHT = 500;
    public static final int IMG_WIDTH = 100;
    public static final int IMG_HEIGHT = 100;


    public static void main(String[] args) {
        JFrame window = new JFrame("The Maze");
        window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setResizable(false);
        window.setLayout(null);


        Set<Point> whitePoints = MazeService.requestMaze(IMG_WIDTH, IMG_HEIGHT);
        BufferedImage img = MazeService.createMazeImage(whitePoints, IMG_WIDTH, IMG_HEIGHT);

        MainPanel mainPanel = new MainPanel(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, img);
        window.add(mainPanel);


        window.repaint();
        window.revalidate();
        window.setVisible(true);


    }
}
