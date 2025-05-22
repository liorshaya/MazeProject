import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

                System.out.println("x:" + x + ", y:" + y + ", white:" + isWhite);
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
                if (whitePoints.contains(new Point(i, j))) {
                    bufferedImage.setRGB(j, i , new Color(255,255,255).getRGB());
                } else {
                    bufferedImage.setRGB(j, i, new Color(0,0,0).getRGB());
                }
            }
        }
        return bufferedImage;
    }


}
