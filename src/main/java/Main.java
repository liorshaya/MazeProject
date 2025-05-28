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
import java.util.List;

public class Main {
    public static final int WINDOW_WIDTH = 500;
    public static final int WINDOW_HEIGHT = 500;
    public static final int IMG_WIDTH = 100;
    public static final int IMG_HEIGHT = 100;


    public static void main(String[] args) {
        int[] num = {1};
        JFrame window = new JFrame("The Maze");
        window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setResizable(false);
        window.setLayout(null);


        Set<Point> whitePoints = MazeService.requestMaze(IMG_WIDTH, IMG_HEIGHT);
        BufferedImage img = MazeService.createMazeImage(whitePoints, IMG_WIDTH, IMG_HEIGHT);

        List<Point> path = MazeService.findPathDFS(whitePoints, new Point(0, 0), new Point(IMG_WIDTH - 1, IMG_HEIGHT - 1));

        JLabel label = new JLabel();
        label.setBounds(WINDOW_WIDTH/2 - 110, 80, 220, 50);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setText("מצא או לא מצא תפתרון?");

        JButton button = new JButton();
        button.setBounds(WINDOW_WIDTH/2- 70, 350, 140, 70);
        button.setFont(new Font("Arial", Font.BOLD, 15));
        button.setText("Check solution");
        button.addActionListener((e) -> {
            Color color = (num[0] % 2 == 0) ? Color.WHITE : Color.RED;
            MazeService.drawPathOnImage(img, path, color);
            num[0]++;
            if (path.isEmpty()){
                label.setText("לא מצא תפתרון");
            } else {
                label.setText("מצא מצא תפתרון");
            }
            window.repaint();
        });

        window.add(label);
        window.add(button);

        MainPanel mainPanel = new MainPanel(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, img);
        window.add(mainPanel);


        window.repaint();
        window.revalidate();
        window.setVisible(true);


    }
}
