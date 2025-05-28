import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

public class MazeService {

    public static Set<Point> requestMaze(int width, int height){
        Set<Point> whitePoints = new HashSet<>();
        try{
            Map<String, Object> idParams = new HashMap<>();
            idParams.put("width", width);
            idParams.put("height", height);
            HttpResponse<String> response = Unirest.get("https://app.seker.live/fm1/get-points")
                    .queryString(idParams)
                    .asString();
            JSONArray jsonResponse = new JSONArray(response.getBody());
//            System.out.println(response.getBody());

            int x, y;
            boolean isWhite;

            for (int i = 0; i < jsonResponse.length(); i++) {
                JSONObject obj = jsonResponse.getJSONObject(i);

                x = obj.getInt("x");
                y = obj.getInt("y");
                isWhite = obj.getBoolean("white");

                if (isWhite){
                    whitePoints.add(new Point(x, y));
                }

//                System.out.println("x:" + x + ", y:" + y + ", white:" + isWhite);
            }


        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }

        return whitePoints;
    }


    public static BufferedImage createMazeImage(Set<Point> whitePoints, int width, int height) {
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < bufferedImage.getHeight(); i++) {
            for (int j = 0; j < bufferedImage.getWidth(); j++) {
                if (whitePoints.contains(new Point(j, i))) {
                    bufferedImage.setRGB(j, i , new Color(255,255,255).getRGB());
                } else {
                    bufferedImage.setRGB(j, i, new Color(0,0,0).getRGB());
                }
            }
        }
        return bufferedImage;
    }



    public static List<Point> findPathDFS(Set<Point> whitePoints, Point start, Point goal) {
        List<Point> correctPath = new ArrayList<>();
        List<Point> visited = new ArrayList<>();
        List<Point> junctions = new ArrayList<>();

        correctPath.add(start);
        visited.add(start);

        while (!correctPath.isEmpty()) {
            Point current = correctPath.get(correctPath.size() - 1);

            if (current.equals(goal)) {
                return correctPath;
            }

            List<Point> neighbors = getUnvisitedNeighbors(current, whitePoints, visited);

            if (neighbors.size() > 1) {
                junctions.add(current);
            }

            if (!neighbors.isEmpty()) {
                Point next = neighbors.get(0);
                correctPath.add(next);
                visited.add(next);
            } else {
                Point back = correctPath.remove(correctPath.size() - 1);
                junctions.remove(back);
            }
        }

        return Collections.emptyList();
    }

    private static List<Point> getUnvisitedNeighbors(Point current, Set<Point> whitePoints, List<Point> visited) {
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        List<Point> neighbors = new ArrayList<>();

        for (int[] dir : directions) {
            int nx = current.x + dir[0];
            int ny = current.y + dir[1];
            Point neighbor = new Point(nx, ny);
            if (whitePoints.contains(neighbor) && !visited.contains(neighbor)) {
                neighbors.add(neighbor);
            }
        }

        return neighbors;
    }

    public static void drawPathOnImage(BufferedImage img, List<Point> path, Color color) {
        for (Point p : path) {
            img.setRGB(p.x, p.y, color.getRGB());
        }
    }



    }
