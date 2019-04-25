import org.opencv.core.CvType;
import org.opencv.core.Mat;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.opencv.core.Point;
import org.opencv.core.Scalar;

public class DataManager {
    /**Autor Sebastian Schmidt
     * Speichert alle notwendigen Daten des Labyrinths
     * @param name Name des Labyrinths vom Nutzer gegeben
     * @throws IOException
     */
    public static void saveMaze(String name) throws IOException {
        String start = ImageProcessing.startPoint.x + "," + ImageProcessing.startPoint.y;
        String end = ImageProcessing.endPoint.x + "," + ImageProcessing.endPoint.y;
        String trappoints = "";
        for (int i = 0; i < ImageProcessing.trapPoints.size(); i++) {
            String temp = trappoints;
            trappoints = temp + ImageProcessing.trapPoints.get(i).x + "," + ImageProcessing.trapPoints.get(i).y + ";";
        }
        List<String> lines = Arrays.asList(start, end, "" + ImageProcessing.scaleDiameter, trappoints, ImageProcessing.getLines().dump());

        Path file = Paths.get("savegames\\" + name + ".txt");
        Files.write(file, lines, Charset.forName("UTF-8"));
    }

    /**Autor Sebastian Schmidt
     * Läd die Textdatei und gibt die Daten an ImageProcessing weiter
     * @param path Pfad der gescheicherten Datei
     * @return
     */
    public static boolean loadMaze(String path) {
        String newLine = "";
        Point start = new Point();
        Point end = new Point();
        ArrayList<Point> traps = new ArrayList<>();
        int scale=0;

        boolean b;
        int cutPosition = 0;
        try {
            FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);

            newLine = br.readLine();
            cutPosition = newLine.indexOf(",");
            System.out.println(newLine.substring(0, cutPosition));
            start.x = Double.parseDouble(newLine.substring(0, cutPosition));
            start.y = Double.parseDouble(newLine.substring(cutPosition + 1, newLine.length()));

            newLine = br.readLine();
            cutPosition = newLine.indexOf(",");
            end.x = Double.parseDouble(newLine.substring(0, cutPosition));
            end.y = Double.parseDouble(newLine.substring(cutPosition + 1, newLine.length()));

            newLine = br.readLine();
            scale = Integer.parseInt(newLine);

            cutPosition = 0;
            int oldCutposition = -1;
            newLine = br.readLine();
            while (cutPosition < newLine.length() - 1) {
                cutPosition = newLine.indexOf(",");
                Point p = new Point(0, 0);
                int startInt;
                if(newLine.substring(0, 1).equals("[")){
                    startInt = 2;
                }else{
                    startInt = 1;
                }
                p.x = Double.parseDouble(newLine.substring(oldCutposition + startInt, cutPosition));
                oldCutposition = cutPosition;
                cutPosition = newLine.indexOf(";");
                p.y = Double.parseDouble(newLine.substring(oldCutposition + 1, cutPosition));
                oldCutposition = cutPosition;
                newLine = newLine.substring(cutPosition + 1, newLine.length());
                cutPosition = 0;
                oldCutposition = -1;
                traps.add(p);
                System.out.println(traps);
            }
            cutPosition = 0;
            newLine = br.readLine();
            ArrayList<double[]> tempPoints = new ArrayList<>();
            //System.out.println(newLine);

            while (newLine != null) {
                newLine = newLine.substring(1, newLine.length());
                double[] p = new double[4];

                cutPosition = newLine.indexOf(",");
                p[0] = Double.parseDouble(newLine.substring(0, cutPosition));
                newLine = newLine.substring(cutPosition+2, newLine.length());

                cutPosition = newLine.indexOf(",");
                p[1] = Double.parseDouble(newLine.substring(0, cutPosition));
                newLine = newLine.substring(cutPosition+2, newLine.length());

                cutPosition = newLine.indexOf(",");
                p[2] = Double.parseDouble(newLine.substring(0, cutPosition));
                newLine = newLine.substring(cutPosition+2, newLine.length());

                cutPosition = newLine.indexOf(";");
                if(cutPosition == -1){
                    cutPosition = newLine.length()-1;
                }
                p[3] = Double.parseDouble(newLine.substring(0, cutPosition));

                newLine = br.readLine();
                tempPoints.add(p);
                System.out.println(tempPoints);
                cutPosition = 0;
            }
            Mat wallPoints = new Mat(tempPoints.size(), 1, CvType.CV_32SC4, Scalar.all(0));
            for (int i = 0; i<tempPoints.size(); i++){
                wallPoints.put(i, 0, tempPoints.get(i));
            }
            /*
            while (newLine != null) {
                newLine = br.readLine();
            }*/
            br.close();
            fr.close();
            b = true;
            new ImageProcessing(start, end, scale, traps, wallPoints);
        } catch (Exception e) {
            System.out.println("Error in loading Labyrinth Data");
            e.printStackTrace();
            b = false;
        }

        return b;
    }
}
