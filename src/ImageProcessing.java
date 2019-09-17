import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.*;

import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.CLAHE;
import org.opencv.imgproc.Imgproc;

import static java.lang.Math.PI;


/**
 * Simple application, that reads an image from file,
 * performs image processing using OpenCV,
 * displays the original and resulting image and
 * saves the processed image to a new file.
 *
 * @author Karsten Lehn
 * @version 1.9.2016
 */
public class ImageProcessing extends JFrame {

    private Random rng = new Random(12345);

    public BufferedImagePanel getImgPanel1() {
        return imgPanel1;
    }

    private BufferedImagePanel imgPanel1;
    private BufferedImagePanel imgPanel2;
    private static JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));

    private JButton to64, to8;
    private Window window;

    public static Point startPoint;
    public static Point endPoint;
    private Point colorPoint;
    private Point scalerad;
    public static int scaleDiameter;
    public static ArrayList<Point> trapPoints = new ArrayList<>();
    private Mat frame10 = new Mat();
    private Mat frameGreen = new Mat();
    private Mat frameRed = new Mat();
    private Mat framePink = new Mat();
    private Mat frameOrange = new Mat();
    private static Mat lines;
    /**
     * Create object and perform the processing by calling private member functions.
     */
    private Mat processedImgMat;

    public Mat getProcessedImgMat() {
        return processedImgMat;
    }

    public void setProcessedImgMat(Mat processedImgMat) {
        this.processedImgMat = processedImgMat;
    }

    public Mat getImgMat() {
        return imgMat;
    }

    public void setImgMat(Mat imgMat) {
        this.imgMat = imgMat;
    }

    private Mat imgMat;

    /**Autor Sebastian Schmidt
     * Konstruktor für geladene Labyrinthe
     * @param s Startpunkt
     * @param e Endpunkt
     * @param d Durchmesser
     * @param t Arraylist an Fallenpunkten
     * @param linesL Matrix an Wandpunkten
     */
    public ImageProcessing(Point s, Point e,int d, ArrayList<Point> t, Mat linesL){
        startPoint = s;
        endPoint = e;
        scaleDiameter = d;
        trapPoints = t;
        this.lines = linesL;
    }

    /**Autor Sebastian Schmidt
     * Konstruktor für den Entwicklermodus
     */
    public ImageProcessing() {

        //new Window();


        imgPanel1 = null;
        imgPanel2 = null;


        imgMat = loadImage(1);

    }

    /**Autor Sebastian Schmidt
     * Startet die Fenster welche den Entwicklermodus darstellen
     */
    public void startDeveloperMode(){
        createFrame();

        to64 = new JButton();
        to8 = new JButton();


        processedImgMat = processShowImage(imgMat, 0, 10);
        writeImage(processedImgMat, "ProcessedImage.png");

        JFrame frame = new JFrame("Window");
        window = new Window();
        frame.setContentPane(window.windowView);
        window.setProcess(this);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Autor Sebastian Schmidt
     * Konstruktor für ein neu erstelltes Labyrinth
     * @param s Parameter zur Unterscheidung von den verschiedenen Konstrukoren
     */
    public ImageProcessing(String s) {
        imgMat = loadImage(2);
        try {
            imgMat = scaleFull(imgMat);
        }catch (Exception e){
            System.err.println("Imageload not possible or stopped");
        }

    }

    /**
     * Autor Sebastian Schmidt
     * Führt eine vollständige Analyse des eingeladenen Bildes durch
     * @return Boolean ob die Analyse ohne Fehler ausgeführt werden konnte
     */
    public boolean analysePicture(){
        boolean allDone = true;
        if(!(imgMat == null)){
            try{
                colorPoints(imgMat);

            }catch (Exception e){
                System.err.println("Error in Color-Detection");
                allDone = false;
            }
            try{
                gaussFull(imgMat);
                Main.loadProgress++;
                System.out.println(Main.loadProgress);
                cannyFull(imgMat);
                Main.loadProgress++;
                System.out.println(Main.loadProgress);
                gaussFull(imgMat);
                Main.loadProgress++;
                System.out.println(Main.loadProgress);
            }catch (Exception e){
                System.err.println("Error in Gauss/Canny");
                allDone = false;
            }
            try {
                linesFull(imgMat);
                Main.loadProgress++;
                System.out.println(Main.loadProgress);
            }catch (Exception e){
                System.err.println("Error in Lines-Detection");
                allDone = false;
            }
        }else{
            allDone = false;
        }
        return allDone;
    }


    /**
     * Loads the image to be processed into memory
     *
     * @return loaded image matrix
     */
    private Mat loadImage(int x) {
        switch (x){
            case 1:
                fileChooser.setCurrentDirectory(new File("images\\Testbilder"));
                break;
            case 2:
                fileChooser.setCurrentDirectory(new File("Labyrinth Bilder\\"));
                break;
        }

        // Choose file path and file name via a file selector box
        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal != JFileChooser.APPROVE_OPTION) {
            return null;  // cancelled
        }
        File selectedFile = fileChooser.getSelectedFile();
        String filePathName = selectedFile.getPath();
        // End: Choose file path and file name via a file selector box


        // Load file into OpenCV Matrix
        Mat imageMat = Imgcodecs.imread(filePathName);
        // TODO: Smooth error handling
        if (imageMat.empty()) {
            System.err.println("Connot load image.");
        }

        System.out.println("Read image: " + imageMat);
        System.out.println("  Matrix columns: " + imageMat.cols());
        System.out.println("  Matrix rows: " + imageMat.rows());
        System.out.println("  Matrix channels: " + imageMat.channels());

        return imageMat;
    }

    /**
     * @param imgMat   image matrix to be written to a file
     * @param filename name of the file to be created
     */
    public void writeImage(Mat imgMat, String filename) {
        String filePathName = "images/" + filename;
        Imgcodecs.imwrite(filePathName, imgMat,
                new MatOfInt(Imgcodecs.CV_IMWRITE_PNG_STRATEGY_HUFFMAN_ONLY,
                        Imgcodecs.CV_IMWRITE_PNG_STRATEGY_FIXED));
    }


    /**
     * Create the JFrame to be displayed, displaying two images.
     */

    private void createFrame() {

        setTitle("Loaded and processed image");

        JPanel contentPane = (JPanel) getContentPane();
        contentPane.setLayout(new FlowLayout());

        imgPanel1 = new BufferedImagePanel();
        contentPane.add(imgPanel1);
        imgPanel2 = new BufferedImagePanel();
        contentPane.add(imgPanel2);

        // place the frame at the center of the screen and show
        pack();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - getWidth() / 2, dim.height / 2 - getHeight() / 2);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Autor: Bela Korb
     * <br><br>
     * angelehnt an: https://docs.opencv.org/3.4/d8/d01/tutorial_discrete_fourier_transform.html
     *<br>
     * @param imageMat Das zu transformierende Bild
     * @return Fouriertransformiertes Bild
     */
    public Mat fouriertransformation(Mat imageMat) {

        Mat I = new Mat();
        Imgproc.cvtColor(imageMat, I, Imgproc.COLOR_BGR2GRAY);

        Mat padded = new Mat();                     //expand input image to optimal size
        int m = Core.getOptimalDFTSize(I.rows());
        int n = Core.getOptimalDFTSize(I.cols()); // on the border add zero values
        Core.copyMakeBorder(I, padded, 0, m - I.rows(), 0, n - I.cols(), Core.BORDER_CONSTANT, Scalar.all(0));

        List<Mat> planes = new ArrayList<Mat>();
        padded.convertTo(padded, CvType.CV_32F);
        planes.add(padded);
        planes.add(Mat.zeros(padded.size(), CvType.CV_32F));
        Mat complexI = new Mat();
        Core.merge(planes, complexI);         // Add to the expanded another plane with zeros

        Core.dft(complexI, complexI);         // this way the result may fit in the source matrix

        // compute the magnitude and switch to logarithmic scale
        // => log(1 + sqrt(Re(DFT(I))^2 + Im(DFT(I))^2))
        Core.split(complexI, planes);                               // planes.get(0) = Re(DFT(I)
        // planes.get(1) = Im(DFT(I))
        Core.magnitude(planes.get(0), planes.get(1), planes.get(0));// planes.get(0) = magnitude
        Mat magI = planes.get(0);

        Mat matOfOnes = Mat.ones(magI.size(), magI.type());
        Core.add(matOfOnes, magI, magI);         // switch to logarithmic scale
        Core.log(magI, magI);

        // crop the spectrum, if it has an odd number of rows or columns
        magI = magI.submat(new Rect(0, 0, magI.cols() & -2, magI.rows() & -2));

        // rearrange the quadrants of Fourier image  so that the origin is at the image center
        int cx = magI.cols() / 2;
        int cy = magI.rows() / 2;

        Mat q0 = new Mat(magI, new Rect(0, 0, cx, cy));   // Top-Left - Create a ROI per quadrant
        Mat q1 = new Mat(magI, new Rect(cx, 0, cx, cy));  // Top-Right
        Mat q2 = new Mat(magI, new Rect(0, cy, cx, cy));  // Bottom-Left
        Mat q3 = new Mat(magI, new Rect(cx, cy, cx, cy)); // Bottom-Right

        Mat tmp = new Mat();               // swap quadrants (Top-Left with Bottom-Right)
        q0.copyTo(tmp);
        q3.copyTo(q0);
        tmp.copyTo(q3);

        q1.copyTo(tmp);                    // swap quadrant (Top-Right with Bottom-Left)
        q2.copyTo(q1);
        tmp.copyTo(q2);

        magI.convertTo(magI, CvType.CV_8UC1);
        Core.normalize(magI, magI, 0, 255, Core.NORM_MINMAX, CvType.CV_8UC1);

        Imgproc.cvtColor(magI, magI, Imgproc.COLOR_GRAY2BGR);

        imgPanel2.setImage(Mat2BufferedImage(magI));
        pack();

        return magI;
    }

    /**
     * Autor: Bela Korb
     *<br><br>
     * Eine Methode, welche eine Matrix nach der Butterworthtiefpass-Formel erstellt und diese Matrix mit der weitergegebenen Matrix verrechnet.
     * (Nur zum überprüfen, ob der Butterworth richtig erstellt wird; keine Verwendung im endgültigen Progamm)
     * @param imageMat Bild, worauf der Filter angewandt werden soll
     * @return berechnetes Bild
     */
    public Mat lowPassFilter(Mat imageMat) {
        Mat temp = new Mat();


        Imgproc.cvtColor(imageMat, temp, Imgproc.COLOR_BGR2GRAY);
        Mat butterworth = new Mat(imageMat.size(), CvType.CV_8UC1, Scalar.all(0));

        double grayvalue;
        for (int i = 0; i < imageMat.rows(); i++) {
            for (int j = 0; j < imageMat.cols(); j++) {
                grayvalue = (1 / (1 + Math.pow((Math.sqrt(Math.pow(i - (imageMat.rows() / 2), 2) + Math.pow(j - (imageMat.cols() / 2), 2)) / 50), 2)));
                butterworth.put(i, j, grayvalue);
                double[] pixel = temp.get(i, j);

                temp.put(i, j, (int) (pixel[0] * grayvalue));
            }
        }
        Imgproc.cvtColor(temp, temp, Imgproc.COLOR_GRAY2BGR);
        imgPanel2.setImage(Mat2BufferedImage(temp));
        pack();

        return temp;
    }

    /**
     * Autor: Bela Korb
     * <br><br>
     * angelehnt an: https://docs.opencv.org/3.4/d8/d01/tutorial_discrete_fourier_transform.html
     *<br>
     * Methode führt Fouriertransformation durch, multipliziert das Amplitudenspektrum mit dem
     * Butterworth-Tiefpassfilter und führt eine inverse Fouriertransformation durch.
     * Danach ist das Bild weichgezeichnet.
     *
     * (keine Verwendung im endgültigen Progamm; Das durch die Methode enstandende Bild war für unsere Zwecke nicht so gut brachbar)
     *
     * @param imageMat Bild, was weichgezeichnet werden soll
     * @return weichgezeichnetes Bild
     */
    public Mat invertFourier(Mat imageMat) {
        Mat I = new Mat();

        //Skalierung des Bildes auf die Optimale Groesse
        int newHeight= Core.getOptimalDFTSize(imageMat.rows());
        int newWidth= Core.getOptimalDFTSize(imageMat.cols());

        Mat newMat = new Mat(newHeight, newWidth, CvType.CV_8UC3, Scalar.all(0));

        Imgproc.resize(imageMat, I, new Size(newWidth, newHeight),newWidth,newHeight,3);

        Imgproc.cvtColor(I, I, Imgproc.COLOR_BGR2GRAY);

        Mat padded = new Mat();                     //expand input image to optimal size
        int m = Core.getOptimalDFTSize(I.rows());
        int n = Core.getOptimalDFTSize(I.cols()); // on the border add zero values
        Core.copyMakeBorder(I, padded, 0, m - I.rows(), 0, n - I.cols(), Core.BORDER_CONSTANT, Scalar.all(0));

        List<Mat> planes = new ArrayList<Mat>();
        padded.convertTo(padded, CvType.CV_32F);
        planes.add(padded);
        planes.add(Mat.zeros(padded.size(), CvType.CV_32F));
        Mat complexI = new Mat();
        Core.merge(planes, complexI);         // Add to the expanded another plane with zeros

        Core.dft(complexI, complexI);         // this way the result may fit in the source matrix
        Mat butterworth = new Mat(I.size(), CvType.CV_64FC1, Scalar.all(0));
        //berechnung der Cutoff-Distanz
        int cutoff=((m+n)/2)/10;
        double grayvalue;
        //Berechnung der Butterworth Tiefpassfiltermatrix
        for (int i = 0; i < I.rows(); i++) {
            for (int j = 0; j < I.cols(); j++) {
                grayvalue = (1 / (1 + Math.pow((Math.sqrt(Math.pow(i - (I.rows() / 2), 2) + Math.pow(j - (I.cols() / 2), 2)) / cutoff),3)));
                butterworth.put(i, j, grayvalue);
            }
        }
        //Vertauschen der Quadranten
        int cx = butterworth.cols() / 2;
        int cy = butterworth.rows() / 2;

        Mat q0 = new Mat(butterworth, new Rect(0, 0, cx, cy));   // Top-Left - Create a ROI per quadrant
        Mat q1 = new Mat(butterworth, new Rect(cx, 0, cx, cy));  // Top-Right
        Mat q2 = new Mat(butterworth, new Rect(0, cy, cx, cy));  // Bottom-Left
        Mat q3 = new Mat(butterworth, new Rect(cx, cy, cx, cy)); // Bottom-Right

        Mat tmp = new Mat();               // swap quadrants (Top-Left with Bottom-Right)
        q0.copyTo(tmp);
        q3.copyTo(q0);
        tmp.copyTo(q3);

        q1.copyTo(tmp);                    // swap quadrant (Top-Right with Bottom-Left)
        q2.copyTo(q1);
        tmp.copyTo(q2);


        double c;

        double[] complex;
        double r;
        int real = 0;
        int imaginary = 1;
        for (int i = 0; i < I.rows(); i++) {
            for (int j = 0; j < I.cols(); j++) {
                /*
                Dieser Teil wurde vorher für die Verrechnung verwendet, führt aber zu einem schlechteren Ergebnis
                Es sollte die Komplexe Zahl in die Eulersche Form umgerechnet werden, das Amplitudenspektrum mit dem Butterworth verrechnet werden
                und dann zurückgerechnet werden

                */
/*
                complex = complexI.get(i, j);
                if (complex[real] >= 0 && complex[imaginary] >= 0) {
                    r =Math.sqrt(complex[real] * complex[real] + complex[imaginary] * complex[imaginary]);
                    c =  r* butterworth.get(i, j)[0];

                    complex[real] = c * Math.cos(Math.acos(complex[real] / r));
                    complex[imaginary] = c * Math.sin(Math.asin(complex[imaginary] / r));
                    complexI.put(i, j, complex);
                } else if (complex[real] <= 0 && complex[imaginary] >= 0) {
                    r =Math.sqrt(complex[real] * complex[real] + complex[imaginary] * complex[imaginary]);
                    c =  r* butterworth.get(i, j)[0];

                    complex[real] = c * Math.cos(PI-Math.acos(complex[real] / r));
                    complex[imaginary] = c * Math.sin(Math.asin(complex[imaginary] / r));
                    complexI.put(i, j, complex);
                } else if (complex[real] <= 0 && complex[imaginary] <= 0) {
                    r =Math.sqrt(complex[real] * complex[real] + complex[imaginary] * complex[imaginary]);
                    c =  r* butterworth.get(i, j)[0];

                    complex[real] = c * Math.cos(2*PI-Math.acos(complex[real] / r));
                    complex[imaginary] = c * Math.sin(PI-Math.asin(complex[imaginary] / r));
                    complexI.put(i, j, complex);
                } else if (complex[real] >= 0 && complex[imaginary] <= 0) {
                    r =Math.sqrt(complex[real] * complex[real] + complex[imaginary] * complex[imaginary]);
                    c =  r* butterworth.get(i, j)[0];

                    complex[real] = c * Math.cos(2*PI-Math.acos(complex[real] / r));
                    complex[imaginary] = c * Math.sin(2*PI+Math.asin(complex[imaginary] / r));
                    complexI.put(i, j, complex);
                }


                */

                complex = complexI.get(i, j);
                complex[0] = complex[0] * butterworth.get(i, j)[0];
                complex[1] = complex[1] * butterworth.get(i, j)[0];
                complexI.put(i, j, complex);
            }
        }


        Core.dft(complexI, complexI, Core.DFT_INVERSE);         // this way the result may fit in the source matrix


        Mat restoredImage = new Mat();
        Core.split(complexI, planes);
        Core.normalize(planes.get(0), restoredImage, 0, 255, Core.NORM_MINMAX);
        Imgproc.cvtColor(restoredImage, restoredImage, Imgproc.COLOR_GRAY2BGR);
        restoredImage.convertTo(restoredImage, CvType.CV_8UC3);

        imgPanel2.setImage(Mat2BufferedImage(restoredImage));
        pack();

        return restoredImage;
    }

    /**
     * Autor: Bela Korb
     *<br><br>
     * Methode zum Skalieren des Bildes auf eine einheitliche Größe
     * Die laengere Seite wird auf 800 Pixel gesetzt.
     * Soll in den weiteren Berechnungen zu einheitlicheren Ergebnissen führen
     * @param imageMat Bild, welches skaliert werden soll
     * @return skaliertes Bild
     */
    public Mat scaleFull(Mat imageMat) {

        double height = imageMat.rows();
        double width = imageMat.cols();
        int newHeight;
        int newWidth;
        int res = 800;
        //if (height > res || width > res) {
        if (width < height) {
            newHeight = res;
            newWidth = (int) (width / height * res);
            Mat newMat = new Mat(newHeight, newWidth, CvType.CV_8UC3, Scalar.all(0));

            Imgproc.resize(imgMat, newMat, new Size(newWidth, newHeight));
            return newMat;
        } else {
            newHeight = (int) (height / width * res);
            newWidth = res;
            Mat newMat = new Mat(newHeight, newWidth, CvType.CV_8UC3, Scalar.all(0));


            Imgproc.resize(imageMat, newMat, new Size(newWidth, newHeight));
            return newMat;
        }

    }

    public Mat scale(Mat imageMat) {

        double height = imageMat.rows();
        double width = imageMat.cols();
        int newHeight;
        int newWidth;
        int res = 800;
        //if (height > res || width > res) {
        if (width < height) {
            newHeight = res;
            newWidth = (int) (width / height * res);
            Mat newMat = new Mat(newHeight, newWidth, CvType.CV_8UC3, Scalar.all(0));

            Imgproc.resize(imageMat, newMat, new Size(newWidth, newHeight));

            imgPanel2.setImage(Mat2BufferedImage(newMat));
            pack();
            return newMat;
        } else {
            newHeight = (int) (height / width * res);
            newWidth = res;
            Mat newMat = new Mat(newHeight, newWidth, CvType.CV_8UC3, Scalar.all(0));


            Imgproc.resize(imageMat, newMat, new Size(newWidth, newHeight));
            imgPanel2.setImage(Mat2BufferedImage(newMat));
            pack();
            return newMat;
        }

    }




    /**Autor: Furkan Calisir
     *
     * Eigentliche Erkennung der Farben in Verbindung mit der CLAHE-Histogrammlinearisierung und
     * vereinung der verschiedenen erhaltenen Skalare.
     *
     * @param imageMat
     *  -> vom Nutzer eingeladenes Originalbild
     * @param low
     *  -> untere Schranke vom ersten Farbbereich
     * @param up
     *  -> obere Schranke vom ersten Farbbereich
     * @param low2
     *  -> untere Schranke vom zweiten Farbbereich
     * @param up2
     *  -> obere Schranke vom zweiten Farbbereich
     * @param low3
     *  -> untere Schranke vom dritten Farbbereich
     * @param up3
     *  -> obere Schranke vom dritten Farbbereich
     * @param trap
     *  -> boolean zur Unterscheidung für die Farbe Pink, die ein Array erhalten will, statt einem Punkt
     * @param c
     *  -> Unterscheidung aller Fälle zur Speicherung der ausgegebenen Matrizen in Instanzmatrizen
     * @return
     *  -> return des erhaltenen Mittelpunktes an dem sich die jewelige Farbe befindet
     */
    public Point colordetection(Mat imageMat, Scalar low, Scalar up, Scalar low2, Scalar up2, Scalar low3, Scalar up3, boolean trap, int c) {

        Mat frame2 = new Mat();
        imageMat.copyTo(frame2);
        Mat frame3 = new Mat();
        imageMat.copyTo(frame3);
        Mat proImage = new Mat();
        Mat blackWhite = new Mat();

        //Verwendung von Clahe auf das vom Nutzer eingeladene Originalbild. Dies geschieht nur auf auf Light Werte von Lab,
        //wofür die Werte getrennt voneinander gespeichert werden und anschließend zurückgemerged
        Imgproc.cvtColor(frame3, frame2, Imgproc.COLOR_BGR2Lab);
        CLAHE clahe = Imgproc.createCLAHE();
        clahe.setClipLimit(2.0);
        List<Mat> framelab = new ArrayList<Mat>(3);
        Core.split(frame2, framelab);
        Mat frameL = framelab.get(0);
        clahe.apply(frameL, frameL);
        framelab.set(0, frameL);
        Core.merge(framelab, frame2);

        Imgproc.cvtColor(frame2, frame3, Imgproc.COLOR_Lab2BGR);
        Imgproc.cvtColor(frame3, frame2, Imgproc.COLOR_BGR2HSV);

        //Anwendung von der Farberkennung auf alle eingeladenen Skalare mit Unterscheidung der Fälle,
        // um  welche Farbe es sich handelt.
        //Dafür wird das erste Bild überall weiß gesetzt,
        // wo auch in den anderen Fällen ein weißer Pixel erkannt wird
        Core.inRange(frame2, low, up, proImage);
        if (low2 != null) {
            boolean forred = false;
            int forredcount = 0;
            for (int z=1; z<2; z++) {
                Mat proImage2 = new Mat();
                if (!forred){
                    Core.inRange(frame2, low2, up2, proImage2);
                }else {
                    Core.inRange(frame2, low3, up3, proImage2);
                }
                for (int x = 0; x < proImage2.rows(); x++)
                    for (int y = 0; y < proImage2.cols(); y++) {
                        if (proImage2.get(x, y)[0] == 255.0) {
                            proImage.put(x, y, 255);
                        }
                    }
                    if (low3 != null && forredcount ==0){
                        forred = true;
                        z --;
                        forredcount++;
                    }
            }
        }



    /* // Wurde wegen ungenauer Lösung durch Circle Detector ersetzt
        FastFeatureDetector blobDetector = FastFeatureDetector.create(FastFeatureDetector.THRESHOLD);
        MatOfKeyPoint keypoints = new MatOfKeyPoint();
        blobDetector.detect(proImage, keypoints);
    */
        Imgproc.cvtColor(proImage, blackWhite, Imgproc.COLOR_GRAY2BGR);
        for (int i = 0; i<=1; i++) {
            blackWhite = polish(blackWhite);
        }
        Imgproc.cvtColor(blackWhite, proImage, Imgproc.COLOR_BGR2GRAY);
        if (!trap)
            this.colorPoint = blobPoint(proImage, c);
        else
            blobPointTrap(proImage);

        //Speicherung in Instanzmatrizen, für spätere Verwendung in blackCircles
        switch (c) {
            case 1:
                blackWhite.copyTo(frameGreen);
                break;
            case 2:
                blackWhite.copyTo(frameRed);
                break;
            case 3:
                blackWhite.copyTo(framePink);
                break;
            case 4:
                blackWhite.copyTo(frameOrange);
        }

        Imgproc.cvtColor(proImage, proImage, Imgproc.COLOR_GRAY2BGR);

        return colorPoint;
    }

    /**Autor: Furkan Calisir
     *
     * Erstellung der Skalare für die Farbe grün und übergeben an die Colordetection und speichern des Startpunktes
     *
     * @param imageMat
     *  -> Vom Nutzer eingeladenes Originalbild
     */
    public void colordetectiongreen(Mat imageMat) {
        Mat frame = new Mat();
        imageMat.copyTo(frame);
        Scalar low = new Scalar(40, 50, 30);
        Scalar up = new Scalar(85, 255, 255);

        this.startPoint = colordetection(frame, low, up, null, null, null, null, false, 1);
        System.out.println(startPoint);
    }
    /**Autor: Furkan Calisir
     *
     * Erstellung der Skalare für die Farbe rot und übergeben an die Colordetection und speichern des Endpunktes
     *
     * @param imageMat
     *  -> Vom Nutzer eingeladenes Originalbild
     */
    public void colordetectionred(Mat imageMat) {
        Mat frame = new Mat();
        imageMat.copyTo(frame);
        Scalar low1red = new Scalar(0, 100, 100);
        Scalar up1red = new Scalar(5, 255, 255);
        Scalar low2red = new Scalar(172, 164, 60);
        Scalar up2red = new Scalar(180, 255, 200);
        Scalar low3red = new Scalar(6, 201, 150);
        Scalar up3red = new Scalar (7, 255, 255);

        endPoint = colordetection(frame, low1red, up1red, low2red, up2red, low3red, up3red, false, 2);
        System.out.println(endPoint);
    }
    /**Autor: Furkan Calisir
     *
     * Erstellung der Skalare für die Farbe pink und übergeben an die Colordetection und speichern der Fallen
     *
     * @param imageMat
     *  -> Vom Nutzer eingeladenes Originalbild
     */
    public void colordetectionpink(Mat imageMat) {
        Mat frame = new Mat();
        imageMat.copyTo(frame);
        Scalar low = new Scalar(135, 70, 70);
        Scalar up = new Scalar(167, 255, 255);
        Scalar low1 = new Scalar(168, 50, 190);
        Scalar up1 = new Scalar(176, 163, 255);

        trapPoints = new ArrayList<>();
        colordetection(frame, low, up, low1, up1, null, null, true, 3);
        System.out.println(trapPoints);
    }
    /**Autor: Furkan Calisir
     *
     * Erstellung der Skalare für die Farbe orange und übergeben an die Colordetection und speichern des Radius
     *
     * @param imageMat
     *  -> Vom Nutzer eingeladenes Originalbild
     */
    public void colordetectionorange(Mat imageMat) {
        Mat frame = new Mat();
        imageMat.copyTo(frame);
        Scalar low = new Scalar(8, 70, 70);
        Scalar up = new Scalar(25, 255, 255);
        Scalar low1 = new Scalar(6, 130, 140);
        Scalar up1 = new Scalar(7, 200, 255);

        this.scalerad = colordetection(frame, low, up, low1, up1, null, null, false, 4);
        System.out.println(scalerad);
    }

    /**Autor: Furkan Calisir
     *
     * Erkennt beim eingegeben Schwarzweißbild Kreise, falls welche vorhanden sind und gibt deren Mittelpunkt zurück,
     * die als Mitte der Farbkreise angesehen werden
     *
     * @param imageMat
     *  -> eingeladenes Schwarzweißbild mit weißen Flecken an den Stellen, an denen Farben erkannt wurden
     * @param rad
     *  -> Unterscheidung, ob es sich um die Farbe Orange handelt, die einen Radius statt einen Punkt braucht
     * @return
     *  -> Zentrum vom erkannten Kreis, dient als Punkt auf die entsprechende Objekte gesetzt werden
     */
    public Point blobPoint(Mat imageMat, int rad) {
        Mat temp = new Mat();
        imageMat.copyTo(temp);
        Mat image = new Mat();
        imageMat.copyTo(image);
        Mat circles = new Mat();
        image = gauss(image);
        Imgproc.HoughCircles(image, circles, Imgproc.HOUGH_GRADIENT, 1.0, (double) temp.rows() / 16, 10, 10, 3, 200);
        double[] c = circles.get(0, 0);
        if(rad == 4 && c[2] < 3){
            c[2] = 5.0;
        }
        if(rad != 4 && c[2] <3) {
            throw new NullPointerException();
        }
            Point center = new Point(Math.round(c[0]), Math.round(c[1]));


        if (rad == 4) {

            scaleDiameter = ((int) Math.round(c[2])) * 2;
            System.out.println(scaleDiameter);
        }
        return center;
    }

    /**Autor: Furkan Calisir
     *
     * Ähnlich zu der Methode blobPoint, behandelt jedoch mehrere Kreise. Nimmt dafür das eingegebene Schwarzweißbild,
     * auf dem Kreise erkannt werden können. Anschließend wird kontrolliert, ob die erkannten Mittelpunkte weit genug
     * auseinander liegen, damit eine Falle an der Stelle platziert werden kann und speichert die Punkte in einem Array
     *
     * @param imageMat
     *  -> Schwarzweißbild mit weißen Flecken an denen die Farbe pink erkannt wurde
     * @return
     *  -> ein Punkt, der nicht bearbeitet wurde
     */

     public Point blobPointTrap(Mat imageMat) {
        Mat temp = new Mat();
        imageMat.copyTo(temp);
        Mat grayImage = new Mat();
        imageMat.copyTo(grayImage);
        Mat circles = new Mat();
        grayImage = gauss(grayImage);
        Imgproc.HoughCircles(grayImage, circles, Imgproc.HOUGH_GRADIENT, 1.0, (double) temp.rows() / 30, 10, 10, 5, 200);

        ArrayList<Point> centers = new ArrayList<>();
        ArrayList<Integer> radius = new ArrayList<>();
        //geht durch alle vorhandenen Kreise und vergleicht den Abstand der Mittelpunkte
        for (int x = 0; x < circles.cols(); x++) {
            boolean o = true;
            double[] c = circles.get(0, x);
            Point center = new Point(Math.round(c[0]), Math.round(c[1]));
            radius.add((int) Math.round(c[2]));
            if (centers.size() > 0) {
                for (Integer i : radius) {
                    for (Point u : centers) {
                        //Abstandsberechnung der beiden verglichenen Punkte
                        double dx = center.x - u.x;
                        double dy = center.y - u.y;
                        //Keine Übernahme des Punktes, falls er in dem Radius einen anderen Kreises liegt
                        double distance = Math.sqrt(dx * dx + dy * dy);
                        if (distance < i) {
                            o = false;
                            break;
                        }
                    }
                }
            }
            //Speicherung der akzeptierten Punkte in einem Instanzarray
            if (o == true) {
                centers.add(center);
                trapPoints.add(center);
            }
        }
        return colorPoint;
    }

    /**Autor: Furkan Calisir
     * colorPoints dient dazu, die Methoden für die verschiedenen Farberkenungen aufzurufen.
     *
     * @param imgMat
     *  -> Originalbild, das vom Nutzer selber eingeladen wird
     */
    public void colorPoints(Mat imgMat) {
        Mat frame = new Mat();
        imgMat.copyTo(frame);
        colordetectiongreen(frame);
        Main.loadProgress++;
        System.out.println(Main.loadProgress);
        colordetectionred(frame);
        Main.loadProgress++;
        System.out.println(Main.loadProgress);
        colordetectionorange(frame);
        Main.loadProgress++;
        System.out.println(Main.loadProgress);
        colordetectionpink(frame);
        Main.loadProgress++;
        System.out.println(Main.loadProgress);

    }

    /**Autor: Furkan Calisir
     *
     * Überschreibung aller Pixel an denen eine Farbe erkannt wurde durch die Farbe schwarz auf das Grayscale Bild,
     * das nach Canny entsteht. Zusätzlich färbt sie alle Nachbarn mit einer Reichweiter von sieben Pixeln schwarz,
     * um den Farbverlauf der nicht erkannt wird, ebenfalls zu erwischen.
     *
     * @param imageMat
     *  -> vom Nutzer eingeladenes Bild, auf das der Canny-Operator angewandt wurde
     */
    public void circlesBlack(Mat imageMat) {
        Mat frameBW = new Mat();
        Mat frame = new Mat();
        imageMat.copyTo(frame);
        frame.copyTo(frameBW);
        double[] colorBlack = {0, 0, 0};
        for (int x = 0; x < frameBW.rows(); x++)
            for (int y = 0; y < frameBW.cols(); y++) {
                //Überprüfung, ob der jeweilige Pixel eine Farbe enthält
                if ((frameGreen.get(x, y)[0] == 255.0) || (frameRed.get(x, y)[0] == 255.0) || (framePink.get(x, y)[0] == 255.0) || (frameOrange.get(x, y)[0] == 255.0)) {
                    frameBW.put(x, y, colorBlack);
                    if (x > 7 & y > 7) {
                        //Färben von allen Nachbarpixeln mit sieben Pixel Reichweite in schwarz
                        for (int g = 1; g < 7; g++) {
                            frameBW.put((x - g), (y - g), colorBlack);
                            frameBW.put((x - g), y, colorBlack);
                            frameBW.put(x, (y - g), colorBlack);
                            frameBW.put((x + g), (y + g), colorBlack);
                            frameBW.put((x + g), y, colorBlack);
                            frameBW.put(x, (y + g), colorBlack);
                        }
                    }
                }
            }
        frameBW.copyTo(imgMat);

    }

    /**Autor: Furkan Calisir
     *
     * Methode für zum Aufrufen der Colordetection durch einen Button in der Entwicklerumgebung
     *
     * @param imageMat
     *  -> eingeladenes Bild
     * @return
     *  -> Schwarzweißbild mit weißen Flecken an den Stellen an denen eine Farbe erkannt wurde
     */
    public Mat testColor(Mat imageMat) {
        imageMat.copyTo(frame10);
        colorPoints(frame10);
        frameRed.copyTo(frame10);
        //Mergen aller Farben, die erkannt wurden, in ein Bild
        double[] colorWhite = {255, 255, 255};
        for (int x = 0; x < frame10.rows(); x++)
            for (int y = 0; y < frame10.cols(); y++) {
                //Überprüfung, ob der jeweilige Pixel eine Farbe enthält
                if ((frameGreen.get(x, y)[0] == 255.0) || (frameRed.get(x, y)[0] == 255.0) || (framePink.get(x, y)[0] == 255.0) || (frameOrange.get(x, y)[0] == 255.0)) {
                    frame10.put(x, y, colorWhite);
                }
            }
        imgPanel2.setImage(Mat2BufferedImage(frame10));
        pack();
        return frame10;
    }

    /**
     * Autor: Bela Korb
     *<br><br>
     * angelehnt an: https://docs.opencv.org/3.4/d4/d70/tutorial_hough_circle.html
     *
     * Methode erkennt Kreise und speichert Mittelpunkt und Radius der Kreise in eine Matrix
     * Kreise werden auf dem Eingabebild gezeichnet
     * @param imageMat Eingabebild
     * @return Ausgabebild
     */
    public Mat circles(Mat imageMat) {
        Mat temp = new Mat();
        imageMat.copyTo(temp);
        Mat grayImage = new Mat();
        Mat circles = new Mat();
        Imgproc.cvtColor(imageMat, grayImage, Imgproc.COLOR_BGR2GRAY);
        grayImage = gauss(grayImage);
        //erkennen der Kreise und setzen der Mittelpunkte und Radien in die circles Mat
        Imgproc.HoughCircles(grayImage, circles, Imgproc.HOUGH_GRADIENT, 1.0, (double) temp.rows() / 16, 100, 30, 10, 200);

        for (int x = 0; x < circles.cols(); x++) {
            double[] c = circles.get(0, x);
            Point center = new Point(Math.round(c[0]), Math.round(c[1]));

            Imgproc.circle(temp, center, 1, new Scalar(0, 100, 100), 3, 9, 0);

            int radius = (int) Math.round(c[2]);
            Imgproc.circle(temp, center, radius, new Scalar(255, 0, 255), 3, 9, 0);
        }

        imgPanel2.setImage(Mat2BufferedImage(temp));
        pack();

        return temp;
    }

    /**
     * Autor: Bela Korb
     *<br><br>
     * Methode invertiert das Eingabebild
     *
     * @param imageMat Eingabebild
     * @return invertiertes Ausgabebild
     */
    public Mat invert(Mat imageMat) {
        Mat temp = new Mat(imageMat.rows(), imageMat.cols(), CvType.CV_8UC3);

        for (int i = 0; i < imageMat.rows(); i++) {
            for (int j = 0; j < imageMat.cols(); j++) {
                double[] data = imageMat.get(i, j);
                for (int x = 0; x < 3; x++) {
                    data[x] = 255 - data[x];
                }
                temp.put(i, j, data);
            }
        }
        imgPanel2.setImage(Mat2BufferedImage(temp));
        pack();

        return temp;
    }
    private int linesThreshold = 40;

    /**
     * Autor: Bela Korb
     *<br><br>
     * angelehnt an: https://docs.opencv.org/3.4/d9/db0/tutorial_hough_lines.html
     *<br>
     * Die Methode wendet erst den Canny-Kantendetektor und dann HoughLines an.
     * Die durch HoughLinesP enstandene LinienMatrix wird dann an die Methoden: merge() und norm() weitergegeben
     * wo die Linien weiter bearbeitet werden.
     * Die Linien werden weiterhin vor und nach den Methodenaufrufen auf das weitergegebene Bild gezeichnet
     *
     * @param imageMat Bild auf dem Linien erkannt werden sollen
     * @return imageMat mit den erkannten und bearbeiteten Linien ergänzt
     */
    public Mat lines(Mat imageMat) {
        Mat temp = new Mat();
        Mat edges = new Mat();
        imageMat.copyTo(temp);
        //anwendung des Canny-Kantendetektors
        Imgproc.Canny(temp, edges, minThreshhold, minThreshhold * 3, 3, false);
        Mat dst = new Mat(imageMat.size(), CvType.CV_8UC3, Scalar.all(0));
        imageMat.copyTo(dst, edges);
        Mat lines = new Mat(imageMat.size(), CvType.CV_8UC3, Scalar.all(0));
        //erkennen und speichern der Linien in lines
        Imgproc.HoughLinesP(edges, lines, 1, PI / 180, linesThreshold, 35, 20); // runs the actual detection

        // Draw the lines
        for (int x = 0; x < lines.rows(); x++) {
            double[] l = lines.get(x, 0);
            Imgproc.line(dst, new Point(l[0], l[1]), new Point(l[2], l[3]), new Scalar(0, 0, 255), 2, Imgproc.LINE_AA, 0);
        }
        //Aufruf der Methoden zur Verbesserung der Erkannten Linien
        lines = merge(lines);
        lines = norm(lines);

        this.lines = lines;
        for (int x = 0; x < lines.rows(); x++) {
            double[] l = lines.get(x, 0);
            Imgproc.line(dst, new Point(l[0], l[1]), new Point(l[2], l[3]), new Scalar(0, 255, 0), 1, Imgproc.LINE_AA, 0);
        }


        imgPanel2.setImage(Mat2BufferedImage(dst));
        pack();

        return dst;
    }

    /**Autor: Bela Korb
     * <br><br>
     * Hat die gleichen Funktionen wie lines,
     * nur lines ist für den Entwikclermodus
     * während linesFull für das endgültige Programm genutzt wird
     * @param imageMat
     */


    public void linesFull(Mat imageMat) {
        Mat temp = new Mat();
        Mat edges = new Mat();
        imageMat.copyTo(temp);

        Imgproc.Canny(temp, edges, minThreshhold, minThreshhold * 3, 3, false);
        // edges=circlesBlacktest(edges);

        Size ksize = new Size(3, 3);
        //Imgproc.GaussianBlur(edges, edges, ksize, 0);

        Mat dst = new Mat(imageMat.size(), CvType.CV_8UC3, Scalar.all(0));
        imageMat.copyTo(dst, edges);
        Mat lines = new Mat(imageMat.size(), CvType.CV_8UC3, Scalar.all(0));

        Imgproc.HoughLinesP(edges, lines, 1, PI / 180, linesThreshold, 35, 20); // runs the actual detection;
        // Draw the lines
        for (int x = 0; x < lines.rows(); x++) {
            double[] l = lines.get(x, 0);
            Imgproc.line(dst, new Point(l[0], l[1]), new Point(l[2], l[3]), new Scalar(0, 0, 255), 2, Imgproc.LINE_AA, 0);
        }
        Main.loadProgress++;
        System.out.println(Main.loadProgress);

        lines = merge(lines);
        lines = norm(lines);

        this.lines = lines;
        for (int x = 0; x < lines.rows(); x++) {
            double[] l = lines.get(x, 0);
            Imgproc.line(dst, new Point(l[0], l[1]), new Point(l[2], l[3]), new Scalar(0, 255, 0), 1, Imgproc.LINE_AA, 0);
        }
        dst.copyTo(imgMat);
        writeImage(imgMat,"ProcessedImage.png");
    }

    /**
     * Autor: Bela Korb
     *
     * Methode zum zusammenführen gleicher oder ähnlicher Linien, da durch HoughLinesP sehr viele kurze, für unsere Zwecke
     * unnötige Linien erkannt hat
     *
     * @param lines Linien-matrix, welche bearbeitet werden soll
     * @return bearbeitete Linien-Matrix
     */
    private Mat merge(Mat lines) {
        double[] steigung = new double[lines.rows()];
        for (int i = 0; i < lines.rows(); i++) {
            double[] l = lines.get(i, 0);
            //berechnung der Steigung
            steigung[i] = ((l[3] - l[1]) / (l[2] - l[0]));
            //Wenn Steigung für Java Inf oder -Inf ist(bei Linien, welche z.B. gerade nach oben verlaufen), wird diese für spätere berechnungen auf einen
            //sehr großen oder kleinen Wert gesetzt
            if (steigung[i] < -1000000) steigung[i] = -1000000;
            if (steigung[i] > 1000000) steigung[i] = 1000000;
        }


        int f;
        double[] length = new double[6];

        Mat temp = new Mat(lines.rows() - 1, 1, CvType.CV_32SC4, Scalar.all(0));

        //Doppelte For-Schleife, um jede Linie miteinander zu vergleichen
        for (f = 0; f < lines.rows(); f++) {
            for (int j = f + 1; j < lines.rows(); j++) {
                // Abfrage, ob der Winkel zwischen den beiden Linien zwischen 15 Grad liegt
                if ((Math.toDegrees(Math.acos((1 + steigung[f] * steigung[j]) / (Math.sqrt(1 + Math.pow(steigung[f], 2)) * Math.sqrt(1 + Math.pow(steigung[j], 2))))) < 15) || Math.toDegrees(Math.acos((1 + steigung[f] * steigung[j]) / (Math.sqrt(1 + Math.pow(steigung[f], 2)) * Math.sqrt(1 + Math.pow(steigung[j], 2))))) > 165) {

                    //Abfrage, ob die beiden Linien auf einer Geraden liegen

                    if (sameVektor(lines, f, j, steigung[f], steigung[j])) {

                        double[] data1 = lines.get(f, 0);
                        double[] data2 = lines.get(j, 0);
                        double[] datanew = new double[4];
                        int counter2 = 0;
                        //Die verschiedenen Abstände zwischen den verschiedenen Punkten der beiden Geraden werden ausgerechnet
                        for (int ii = 0; ii < 2; ii++) {
                            for (int jj = 0; jj < 2; jj++) {
                                length[counter2++] = Math.sqrt((Math.pow(((data1[ii * 2] - data2[jj * 2])), 2) + Math.pow((data1[1 + ii * 2] - data2[1 + jj * 2]), 2)));

                            }
                        }
                        length[counter2++] = Math.sqrt((Math.pow(((data1[0] - data1[2])), 2) + Math.pow((data1[1] - data1[3]), 2)));
                        length[counter2++] = Math.sqrt((Math.pow(((data2[0] - data2[2])), 2) + Math.pow((data2[1] - data2[3]), 2)));
                        int longestLine = 0;
                        double longLine = length[0];
                        double shortLine = length[0];

                        //Der kürzeste Abstand wird herausgesucht
                        for (int kk = 1; kk < length.length - 2; kk++) {
                            if (length[kk] < shortLine) {

                                shortLine = length[kk];
                            }
                        }
                        //Es wird geguckt ob die eine zwischen der anderen Linie liegt oder die beiden Linien höchstens den Abstand 20 von einander haben
                        if (((length[0] < length[4]) && (length[3] < length[4]) || ((length[0] < length[5]) && (length[3] < length[5]))) || ((length[1] < length[4]) && (length[2] < length[4]) || ((length[1] < length[5]) && (length[2] < length[5]))) || shortLine < 20) {
                            //der größte Abstand wird ermittelt
                            for (int kk = 1; kk < length.length; kk++) {
                                if (length[kk] > longLine) {
                                    longestLine = kk;
                                    longLine = length[kk];
                                }


                            }
                            //die neue (zusammengeführte) Linie ensteht zwischen den beiden Punkten mit dem größten Abstand
                            switch ((int) longestLine) {
                                case 0:
                                    datanew[0] = data1[0];
                                    datanew[1] = data1[1];
                                    datanew[2] = data2[0];
                                    datanew[3] = data2[1];
                                    break;
                                case 1:
                                    datanew[0] = data1[0];
                                    datanew[1] = data1[1];
                                    datanew[2] = data2[2];
                                    datanew[3] = data2[3];
                                    break;
                                case 2:
                                    datanew[0] = data1[2];
                                    datanew[1] = data1[3];
                                    datanew[2] = data2[0];
                                    datanew[3] = data2[1];
                                    break;
                                case 3:
                                    datanew[0] = data1[2];
                                    datanew[1] = data1[3];
                                    datanew[2] = data2[2];
                                    datanew[3] = data2[3];
                                    break;
                                case 4:
                                    datanew[0] = data1[0];
                                    datanew[1] = data1[1];
                                    datanew[2] = data1[2];
                                    datanew[3] = data1[3];
                                    break;
                                case 5:
                                    datanew[0] = data2[0];
                                    datanew[1] = data2[1];
                                    datanew[2] = data2[2];
                                    datanew[3] = data2[3];
                                    break;
                            }
                            int qq = 0;
                            //Die neue Linie wird in die Lines-Matrix gesetzt, welche jetzt um eins kürzer ist
                            for (int q = 0; q < lines.rows() - 1; q++) {
                                if (q != f && q != j) temp.put(q, 0, lines.get(qq, 0));
                                else if (q == f) temp.put(f, 0, datanew);
                                else if (q == j) {
                                    qq++;
                                    temp.put(q, 0, lines.get(qq, 0));
                                }
                                qq++;
                            }
                            temp.copyTo(lines);
                            //Die Methode ruft sich selbst nochmal auf, bis keine Linien mehr zusammen geführt werden können
                            return merge(lines);

                        }
                    }
                }
            }
        }
        return lines;
    }

    /**
     * Autor: Bela Korb
     *<br><br>
     * Methode berechnet, ob zwei Linien auf einer Geraden liegen
     *
     *
     * @param lines Linien-Matrix
     * @param f Index der ersten zu überprüfenden Linie
     * @param j Index der zweiten zu überprüfenden Linie
     * @param fm Steigung der ersten zu überprüfenden Linie
     * @param jm Steigung der zweiten zu überprüfenden Linie
     * @return Wahr oder falsch, abhängig davon, ob die Linien auf einer Geraden liegen
     */
    private boolean sameVektor(Mat lines, int f, int j, double fm, double jm) {
        double[] data1 = lines.get(f, 0);
        double[] data2 = lines.get(j, 0);
        //durchschnittliche Steigung
        //double m = (fm + jm) / 2;
        double m=fm;
        int minimalScalarI;
        if (m <= 1 && m >= -1) {
            /*
            Berechnung an welcher bei welchem I das Skalar zwischen zwei Geraden 0 ist um später den geringsten Abstand von Punkt zu Gerade zu bestimmen
            Hierzu wird die Steigungsgrade und die Gerade von einem Punkt der Zweiten Linie zu einem Punkt auf der Geraden der ersten Linie
            Das I bestimmt dann den Index für den Punkt, wo der geringste Abstand vorliegt
             */
            minimalScalarI = (int) Math.round((data1[0] - data2[0] + m * (data1[1] - data2[1])) / (-1 - m * m));

            //Wenn der Abstand zwischen den beiden ausgerechneten Punkten klein genug ist, wird true zurückgegeben
            if ((Math.sqrt(Math.pow(data1[0] + minimalScalarI - data2[0], 2) + Math.pow(data1[1] + m * minimalScalarI - data2[1], 2))) < 10) {
                return true;
            }
            /*
            Bei zu hoher oder niedriger Steigung wird das Koordiantensystem gespiegelt(x-Achse und y-Achse vertauscht
            ,damit es zu keinen Fehlern bei der Berechnung kommt
             */
        } else if (m > 1 || m < -1) {
            m = 1 / m;
            /*
            Berechnung an welcher bei welchem I das Skalar zwischen zwei Geraden 0 ist um später den geringsten Abstand von Punkt zu Gerade zu bestimmen
            Hierzu wird die Steigungsgrade und die Gerade von einem Punkt der Zweiten Linie zu einem Punkt auf der Geraden der ersten Linie
            Das I bestimmt dann den Index für den Punkt, wo der geringste Abstand vorliegt
             */
            minimalScalarI = (int) Math.round((data1[1] - data2[1] + m * (data1[0] - data2[0])) / (-1 - m * m));
            //Wenn der Abstand zwischen den beiden ausgerechneten Punkten klein genug ist, wird true zurückgegeben
            if ((Math.sqrt(Math.pow(data1[1] + minimalScalarI - data2[1], 2) + Math.pow(data1[0] + m * minimalScalarI - data2[0], 2))) < 10) {

                return true;
            }
        }
        return false;
    }


    /**
     * Autor: Bela Korb
     * <br><br>
     * Die Methode führt Linienpunkte zusammen, wenn sie nur einen gewissen Abstand haben
     * Wird benutzt um die nachher erstellten Wände an den Eckpunkten richtig zusammen zu führen
     *
     * @param lines Linien-Matrix
     * @return bearbeitete Linien-Matrix
     */
    private Mat norm(Mat lines) {
        //for-Schleifen, um jeden Linien Anfags- und Endpunkt mit einander zu vergleichen
        for (int l = 0; l < 2; l++) {
            for (int i = 0; i < lines.rows(); i++) {
                for (int j = i + 1; j < lines.rows(); j++) {
                    for (int k = 0; k < 2; k++) {
                        /*
                        Abstandsberechnung zwischen den verschiedenen Linienpunkten
                        Wenn Abstand kleiner als 15 ist, werden die Punkte zusammen geführt
                         */
                        if (Math.sqrt(Math.pow(lines.get(j, 0)[k * 2] - lines.get(i, 0)[l * 2], 2) + Math.pow(lines.get(j, 0)[k * 2 + 1] - lines.get(i, 0)[l * 2 + 1], 2)) < 15) {

                           //bestimmung, welche Punkte der jeweiligen Linien zusmmen kommen
                            double[] newDouble = new double[4];
                            if (l == 0 && k == 0) {


                                newDouble[0] = lines.get(i, 0)[0];
                                newDouble[1] = lines.get(i, 0)[1];
                                newDouble[2] = lines.get(j, 0)[2];
                                newDouble[3] = lines.get(j, 0)[3];


                            } else if (l == 1 && k == 0) {
                                newDouble[0] = lines.get(i, 0)[2];
                                newDouble[1] = lines.get(i, 0)[3];
                                newDouble[2] = lines.get(j, 0)[2];
                                newDouble[3] = lines.get(j, 0)[3];


                            } else if (l == 0 && k == 1) {
                                newDouble[0] = lines.get(i, 0)[0];
                                newDouble[1] = lines.get(i, 0)[1];
                                newDouble[2] = lines.get(j, 0)[0];
                                newDouble[3] = lines.get(j, 0)[1];

                            } else if (l == 1 && k == 1) {
                                newDouble[0] = lines.get(i, 0)[2];
                                newDouble[1] = lines.get(i, 0)[3];
                                newDouble[2] = lines.get(j, 0)[0];
                                newDouble[3] = lines.get(j, 0)[1];

                            }
                            lines.put(j, 0, newDouble);
                        }
                    }
                }
            }
        }

        return lines;
    }

    private int minThreshhold = 20;

    /**
     * Autor: Bela Korb
     * <br><br>
     * angelehnt an: https://docs.opencv.org/3.4/da/d5c/tutorial_canny_detector.html
     * <br>
     * Canny-Kanten-Detektor
     * @param imageMat zu bearbeitendes Bild
     * @return mit Canny bearbeitetes Bild
     */
    public Mat canny(Mat imageMat) {
        Mat temp = new Mat();
        Mat edges = new Mat();
        imageMat.copyTo(temp);
        Imgproc.Canny(temp, edges, minThreshhold, minThreshhold * 3, 3, false);
        Mat dst = new Mat(imageMat.size(), CvType.CV_8UC3, Scalar.all(0));
        imageMat.copyTo(dst, edges);

        imgPanel2.setImage(Mat2BufferedImage(dst));
        pack();
        return dst;
    }


    /**Autor: Bela Korb
     * <br><br>
     * Hat die gleichen Funktionen wie canny,
     * nur canny ist für den Entwicklermodus
     * während cannyFull für das endgültige Programm genutzt wird
     * @param imageMat
     */

    public void cannyFull(Mat imageMat) {
        Mat temp = new Mat();
        Mat edges = new Mat();
        imageMat.copyTo(temp);
        Imgproc.Canny(temp, edges, minThreshhold, minThreshhold * 3, 3, false);
        Mat dst = new Mat(imageMat.size(), CvType.CV_8UC3, Scalar.all(0));
        imageMat.copyTo(dst, edges);
        dst.copyTo(imgMat);
        //try {
            circlesBlack(imgMat);
            Main.loadProgress++;
            System.out.println(Main.loadProgress);
        //} catch (NullPointerException e) {
        //    e.printStackTrace();
        //}
    }
    /**
     * Autor: Michelle Wetscheck
     * <br><br>
     * angelehnt an: https://docs.opencv.org/3.4/df/d0d/tutorial_find_contours.html
     *<br>
     * Hierarchien der erkannten Linien im Bild finden
     * <br>
     * War eine Versuch um die Außenkonturen von dem Rest erkennen zu können
     * um offene Labyrinthe schließen zu können
     * @param imageMat
     * @return contourHierarchy;
     */


    public Mat contoursHierarchy(Mat imageMat) {

        Mat temp = new Mat();
        Mat lines = new Mat();
        Imgproc.cvtColor(imageMat, temp, Imgproc.COLOR_BGR2GRAY);
        Imgproc.Canny(temp, lines, 20, 20 * 3);
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(lines, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
        Mat contourHierarchy = Mat.zeros(lines.size(), CvType.CV_8UC3);
        for (int i = 0; i < contours.size(); i++) {
            Scalar color = new Scalar(rng.nextInt(256), rng.nextInt(256), rng.nextInt(256));
            Imgproc.drawContours(contourHierarchy, contours, i, color, 2, Core.LINE_8, hierarchy, 0, new Point());
        }
        imgPanel2.setImage(Mat2BufferedImage(contourHierarchy));
        pack();

        return contourHierarchy;
    }

    public Mat gradient(Mat imageMat) {
        Mat temp = new Mat();
        int ddepth = CvType.CV_16S;


        Mat grad_x = new Mat(), grad_y = new Mat();
        Mat abs_grad_x = new Mat(), abs_grad_y = new Mat();
        Mat kernel_x = new Mat(3, 3, CvType.makeType(1, 1), new Scalar(0));
        Mat kernel_y = new Mat(3, 3, CvType.makeType(1, 1), new Scalar(0));
        kernel_x.put(1, 0, -1);
        kernel_x.put(1, 2, 1);
        kernel_y.put(0, 1, -1);
        kernel_y.put(2, 1, 1);


        Imgproc.filter2D(imageMat, grad_x, ddepth, kernel_x);
        Imgproc.filter2D(imageMat, grad_y, ddepth, kernel_y);


        Core.convertScaleAbs(grad_x, abs_grad_x);
        Core.convertScaleAbs(grad_y, abs_grad_y);
        Core.addWeighted(abs_grad_x, 0.5, abs_grad_y, 0.5, 0, temp);
        imgPanel2.setImage(Mat2BufferedImage(temp));
        pack();

        return temp;
    }

    /**
     * Autor: Bela Korb
     * <br>
     * https://docs.opencv.org/3.2.0/d2/d2c/tutorial_sobel_derivatives.html
     * <br>
     * Sobel Kanten-Detektor
     * @param imageMat zu bearbeitendes Bild
     * @return bearbeitetes Bild
     */
    public Mat sobel(Mat imageMat) {
        Mat temp = new Mat(imageMat.rows(), imageMat.cols(), CvType.CV_8UC3);
        Mat grayImage = new Mat();
        //Mat temp = new Mat();
        Mat grad_x = new Mat(), grad_y = new Mat();
        Mat abs_grad_x = new Mat(), abs_grad_y = new Mat();

        int ddepth = CvType.CV_16S;


        Imgproc.cvtColor(imageMat, grayImage, Imgproc.COLOR_BGR2GRAY);
        Imgproc.Sobel(grayImage, grad_x, ddepth, 1, 0, 3, 1, 0, Core.BORDER_DEFAULT);
        Imgproc.Sobel(grayImage, grad_y, ddepth, 0, 1, 3, 1, 0, Core.BORDER_DEFAULT);

        Core.convertScaleAbs(grad_x, abs_grad_x);
        Core.convertScaleAbs(grad_y, abs_grad_y);
        Core.addWeighted(abs_grad_x, 0.5, abs_grad_y, 0.5, 0, temp);



        imgPanel2.setImage(Mat2BufferedImage(temp));
        pack();

        return temp;


    }

    /**
     * Autor: Bela Korb
     * <br>
     * Mittelwert-Filter
     * @param imageMat zu bearbeitendes Bild
     * @return bearbeitetes Bild
     */
    public Mat mittelWert(Mat imageMat) {
        Mat temp = new Mat();
        Size ksize = new Size(3, 3);
        Imgproc.blur(imageMat, temp, ksize);
        imgPanel2.setImage(Mat2BufferedImage(temp));
        pack();
        return temp;
    }

    /**
     * Autor: Bela Korb
     * <br>
     * Gauss-Filter
     * @param imageMat zu bearbeitendes Bild
     * @return bearbeitetes Bild
     */
    public Mat gauss(Mat imageMat) {
        Mat temp = new Mat();
        Size ksize = new Size(5, 5);
        Imgproc.GaussianBlur(imageMat, temp, ksize, 0);
        try{
            imgPanel2.setImage(Mat2BufferedImage(temp));
        }catch(Exception e){

        }

        pack();
        return temp;
    }

    /**
     * Autor: Bela Korb
     * <br><br>
     * Hat die gleichen Funktionen wie gauss,
     * nur lines ist für den Entwikclermodus
     * während gausFull für das endgültige Programm genutzt wird
     * @param imgMat
     */


    public Mat gaussFull(Mat imgMat) {
        Mat temp = new Mat();
        Size ksize = new Size(5, 5);
        Imgproc.GaussianBlur(imgMat, temp, ksize, 0);
        temp.copyTo(this.imgMat);
        return this.imgMat;
    }

    /**
     * Autor: Bela Korb
     * <br>
     * Median-Filter
     * @param imageMat zu bearbeitendes Bild
     * @return bearbeitetes Bild
     */
    public Mat median(Mat imageMat) {
        Mat temp = new Mat();

        Imgproc.medianBlur(imageMat, temp, 3);
        imgPanel2.setImage(Mat2BufferedImage(temp));
        pack();
        return temp;
    }


    /**
     * Autor: Bela Korb
     * <br>
     * Die Methode ändert die Helligkeit des Bildes.
     * Je nach Auswahl wird die Helligkeit nur in den bestimmten Farbkanälen verändert
     *
     * @param imageMat zu bearbeitendes Bild
     * @param x Wert der Helligkeitsnderung
     * @return bearbeitetes Bild
     */
    public Mat aendereHelligkeit(Mat imageMat, double x) {

        Mat temp = new Mat(imageMat.rows(), imageMat.cols(), CvType.CV_8UC3);
        for (int i = 0; i < imageMat.rows(); i++) {
            for (int j = 0; j < imageMat.cols(); j++) {
                double[] data = imageMat.get(i, j);
                if (window.getRotH().isSelected()) {
                    data[2] = data[2] + x;
                }
                if (window.getGruenH().isSelected()) {
                    data[1] = data[1] + x;
                }
                if (window.getBlauH().isSelected()) {
                    data[0] = data[0] + x;
                }
                temp.put(i, j, data);
            }
        }
        imgPanel2.setImage(Mat2BufferedImage(temp));
        pack();

        return temp;
    }

    /**
     * Autor: Bela Korb
     *
     * Methode zum ändern des Kontrasts
     * Je nach Auswahl wird der Kontrast nur in den bestimmten Farbkanälen geändert
     * @param imageMat zu bearbeitendes Bild
     * @param x Wert der Kontraständerung
     * @return bearbeitetes Bild
     */
    public Mat aendereKontrast(Mat imageMat, double x) {

        Mat temp = new Mat(imageMat.rows(), imageMat.cols(), CvType.CV_8UC3);
        for (int i = 0; i < imageMat.rows(); i++) {
            for (int j = 0; j < imageMat.cols(); j++) {
                double[] data = imageMat.get(i, j);
                if (window.getRotK().isSelected()) {
                    if (x / 100 < 1) {
                        data[2] = (255 - data[2]) * (100 / x);
                        data[2] = 255 - data[2];
                    } else {
                        data[2] = data[2] * (x / 100);
                    }
                }
                if (window.getGruenK().isSelected()) {
                    if (x / 100 < 1) {
                        data[1] = (255 - data[1]) * (100 / x);
                        data[1] = 255 - data[1];
                    } else {
                        data[1] = data[1] * (x / 100);
                    }
                }
                if (window.getBlauK().isSelected()) {
                    if (x / 100 < 1) {
                        data[0] = (255 - data[0]) * ((100 / x));
                        data[0] = 255 - data[0];
                    } else {
                        data[0] = data[0] * (x / 100);
                    }
                }

                temp.put(i, j, data);
            }
        }
        imgPanel2.setImage(Mat2BufferedImage(temp));

        pack();

        return temp;
    }


    public Mat processShowImage(Mat imageMat, double x, double y) {
        // Show loaded image
        imgPanel1.setImage(Mat2BufferedImage(imageMat));


        Mat grayImage = new Mat(imageMat.rows(), imageMat.cols(), CvType.CV_8UC3);
        //Mat grayImage = new Mat();
        //Imgproc.cvtColor(imageMat, grayImage, Imgproc.COLOR_BGR2GRAY);
        //Mat M = new Mat (imageMat.rows(),imageMat.cols(), CvType.CV_8UC3,new Scalar(2,2,2));
        //grayImage =M.mul(imageMat);
        y = (double) y * 0.1;
        //System.out.println(y);
        Mat M = new Mat(imageMat.rows(), imageMat.cols(), CvType.CV_8UC3, new Scalar(0.6, 0.6, 0.6));

        grayImage = M.mul(imageMat);
        //System.out.println(imageMat.dump());


        //System.out.println(grayImage.dump());
        // Show processed image
        imgPanel2.setImage(Mat2BufferedImage(grayImage));
        pack();

        return grayImage;
    }

    public Mat processTo64(Mat imageMat) {
        imgPanel1.setImage(Mat2BufferedImage(imageMat));


        Mat grayImage = new Mat(imageMat.rows(), imageMat.cols(), CvType.CV_8UC3);

        for (int i = 0; i < imageMat.rows(); i++) {
            for (int j = 0; j < imageMat.cols(); j++) {
                double[] data = imageMat.get(i, j);
                if (data[0] > 191) {
                    data[0] = 255;
                } else if (data[0] > 127) {
                    data[0] = 191;
                } else if (data[0] > 63) {
                    data[0] = 127;
                } else if (data[0] <= 63) {
                    data[0] = 0;
                }

                if (data[1] > 191) {
                    data[1] = 255;
                } else if (data[1] > 127) {
                    data[1] = 191;
                } else if (data[1] > 63) {
                    data[1] = 127;
                } else if (data[1] <= 63) {
                    data[1] = 0;
                }

                if (data[2] > 191) {
                    data[2] = 255;
                } else if (data[2] > 127) {
                    data[2] = 191;
                } else if (data[2] > 63) {
                    data[2] = 127;
                } else if (data[2] <= 63) {
                    data[2] = 0;
                }
                grayImage.put(i, j, data);
            }
        }
        imgPanel2.setImage(Mat2BufferedImage(grayImage));
        pack();

        return grayImage;
    }

    public Mat processTo8(Mat imageMat) {
        imgPanel1.setImage(Mat2BufferedImage(imageMat));


        Mat grayImage = new Mat(imageMat.rows(), imageMat.cols(), CvType.CV_8UC3);

        for (int i = 0; i < imageMat.rows(); i++) {
            for (int j = 0; j < imageMat.cols(); j++) {
                double[] data = imageMat.get(i, j);
                if (data[0] > 127) {
                    data[0] = 255;
                } else if (data[0] <= 127) {
                    data[0] = 0;
                }

                if (data[1] > 127) {
                    data[1] = 255;
                } else if (data[1] <= 127) {
                    data[1] = 0;
                }

                if (data[2] > 127) {
                    data[2] = 255;
                } else if (data[2] <= 127) {
                    data[2] = 0;
                }
                grayImage.put(i, j, data);
            }
        }
        imgPanel2.setImage(Mat2BufferedImage(grayImage));
        pack();

        return grayImage;
    }

    /**Autor: Sebastian Schmidt
     *
     *
     * Methode zum Füllen von Lücken von weißen Feldern und zusätzliche Abrundung zur besseren Kreiserkennung
     *
     * @param imageMat
     *  -> durch Colordetection erhaltenes Schwarzweißbild mit weißen Flecken an den Stellen, an denen Farben erkannt wurden
     * @return
     *  -> Rückgabe vom überarbeiteten Bild
     */
    public Mat polish(Mat imageMat) {

        Mat tempImage = new Mat(imageMat.rows(), imageMat.cols(), CvType.CV_8UC3);
        for (int i = 0; i < imageMat.rows(); i++) {
            for (int j = 0; j < imageMat.cols(); j++) {
                double[] data2;
                if (i > 0) {
                    data2 = imageMat.get((i - 1), j);
                } else {
                    data2 = imageMat.get(i, j);
                }

                double[] data3;
                if (i < imageMat.rows() - 1) {
                    data3 = imageMat.get((i + 1), j);
                } else {
                    data3 = imageMat.get(i, j);
                }

                double[] data4;
                if (j > 0) {
                    data4 = imageMat.get(i, (j - 1));
                } else {
                    data4 = imageMat.get(i, j);
                }

                double[] data5;
                if (j < imageMat.cols() - 1) {
                    data5 = imageMat.get(i, (j + 1));
                } else {
                    data5 = imageMat.get(i, j);
                }

                double[] data = imageMat.get(i, j);

                int a = (int) data2[0];
                int s = (int) data3[0];
                int d = (int) data4[0];
                int f = (int) data5[0];

                //Setzt einen Schwarzen Pixel weiß, wenn zwei Nachbarpixel ebenfalls weiß sind
                if (a + s + d + f >=510 && data[0] == 0) {
                    data[0] = 255;
                    data[1] = 255;
                    data[2] = 255;
                }
                tempImage.put(i, j, data);
            }
        }
        pack();

        return tempImage;
    }

    /**Autor Sebastian Schmidt
     * Füllt Lücken in schwarzen Objekten durch Überprüfung von der Farbe der Pixel in 4er-Nachbarschaft
     * @param imageMat Zu bearbeitendes Bild
     * @return neues Bild
     */
    public Mat countCoins(Mat imageMat) {

        Mat tempImage = new Mat(imageMat.rows(), imageMat.cols(), CvType.CV_8UC3);

        for (int i = 0; i < imageMat.rows(); i++) {
            for (int j = 0; j < imageMat.cols(); j++) {
                double[] data = imageMat.get(i, j);
                double rG = data[2] - data[1];
                double gB = data[1] - data[0];
                if ((rG < 20 && rG > 10) && (gB < 32 && gB > 20)) {
                    data[0] = 0;
                    data[1] = 0;
                    data[2] = 0;
                } else {
                    data[0] = 255;
                    data[1] = 255;
                    data[2] = 255;
                }
                tempImage.put(i, j, data);
            }
        }
        for (int i = 0; i < 2; i++) {
            tempImage =polish(tempImage);
        }

        imgPanel2.setImage(Mat2BufferedImage(tempImage));
        pack();

        return tempImage;
    }

    /**
     * Reads the file with the handle file into a Java BufferedImage data structure.
     *
     * @param file file handle
     * @return read image as BufferedImage
     */
    public BufferedImage loadBufferedImage(File file) {
        try {
            BufferedImage image = ImageIO.read(file);
            if (image == null || (image.getWidth(null) < 0)) {
                System.err.println("Image file not found!");
            }
            return image;
        } catch (IOException exc) {
            System.err.println("IO Exception occured!");
            return null;
        }
    }


    /**
     * Converts an OpenCV matrix into a BufferedImage.
     * <p>
     * Inspired by
     * http://answers.opencv.org/question/10344/opencv-java-load-image-to-gui/
     * Fastest code
     * <p>
     * OpenCV Matrix to be converted, Must be 1 channel (grayscale) or 3 channel (BGR) matrix,
     * one or three byte(s) per pixel.
     *
     * @return converted image as BufferedImage
     */
    public BufferedImage Mat2BufferedImage(Mat imgMat) {
        int bufferedImageType = 0;
        switch (imgMat.channels()) {
            case 1:
                bufferedImageType = BufferedImage.TYPE_BYTE_GRAY;
                break;
            case 3:
                bufferedImageType = BufferedImage.TYPE_3BYTE_BGR;
                break;
            default:
                throw new IllegalArgumentException("Unknown matrix type. Only one byte per pixel (one channel) or three bytes pre pixel (3 channels) are allowed.");
        }
        BufferedImage bufferedImage = new BufferedImage(imgMat.cols(), imgMat.rows(), bufferedImageType);
        final byte[] bufferedImageBuffer = ((DataBufferByte) bufferedImage.getRaster().getDataBuffer()).getData();
        imgMat.get(0, 0, bufferedImageBuffer);
        return bufferedImage;
    }

    public int getMinThreshhold() {
        return minThreshhold;
    }

    public void setMinThreshhold(int minThreshhold) {
        this.minThreshhold = minThreshhold;
    }

    public int getLinesThreshold() {
        return linesThreshold;
    }

    public void setLinesThreshold(int linesThreshold) {
        this.linesThreshold = linesThreshold;
    }



    public static Mat getLines() {
        return lines;
    }
}
