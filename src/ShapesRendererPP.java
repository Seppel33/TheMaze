/**
 * Copyright 2012-2013 JogAmp Community. All rights reserved.
 * <p>
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 * <p>
 * 1. Redistributions of source code must retain the above copyright notice, this list of
 * conditions and the following disclaimer.
 * <p>
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list
 * of conditions and the following disclaimer in the documentation and/or other materials
 * provided with the distribution.
 * <p>
 * THIS SOFTWARE IS PROVIDED BY JogAmp Community ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL JogAmp Community OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * <p>
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of JogAmp Community.
 * <p>
 * Performs the OpenGL graphics processing using the Programmable Pipeline and the
 * OpenGL Core profile
 * <p>
 * Starts an animation loop.
 * Zooming and rotation of the Camera is included (see InteractionHandler).
 * Use: left/right/up/down-keys and +/-Keys and mouse
 * Enables drawing of simple shapes: box, sphere, cone (frustum) and roof
 * Serves as a template (start code) for setting up an OpenGL/Jogl application
 * using a vertex and fragment shader.
 * <p>
 * Please make sure setting the file path and names of the shader correctly (see below).
 * <p>
 * Core code is based on a tutorial by Chua Hock-Chuan
 * http://www3.ntu.edu.sg/home/ehchua/programming/opengl/JOGL2.0.html
 * <p>
 * and on an example by Xerxes Rånby
 * http://jogamp.org/git/?p=jogl-demos.git;a=blob;f=src/demos/es2/RawGL2ES2demo.java;hb=HEAD
 *
 * @author Karsten Lehn
 * @version 22.10.2017
 * <p>
 * Performs the OpenGL graphics processing using the Programmable Pipeline and the
 * OpenGL Core profile
 * <p>
 * Starts an animation loop.
 * Zooming and rotation of the Camera is included (see InteractionHandler).
 * Use: left/right/up/down-keys and +/-Keys and mouse
 * Enables drawing of simple shapes: box, sphere, cone (frustum) and roof
 * Serves as a template (start code) for setting up an OpenGL/Jogl application
 * using a vertex and fragment shader.
 * <p>
 * Please make sure setting the file path and names of the shader correctly (see below).
 * <p>
 * Core code is based on a tutorial by Chua Hock-Chuan
 * http://www3.ntu.edu.sg/home/ehchua/programming/opengl/JOGL2.0.html
 * <p>
 * and on an example by Xerxes Rånby
 * http://jogamp.org/git/?p=jogl-demos.git;a=blob;f=src/demos/es2/RawGL2ES2demo.java;hb=HEAD
 * @author Karsten Lehn
 * @version 22.10.2017
 * <p>
 * Performs the OpenGL graphics processing using the Programmable Pipeline and the
 * OpenGL Core profile
 * <p>
 * Starts an animation loop.
 * Zooming and rotation of the Camera is included (see InteractionHandler).
 * Use: left/right/up/down-keys and +/-Keys and mouse
 * Enables drawing of simple shapes: box, sphere, cone (frustum) and roof
 * Serves as a template (start code) for setting up an OpenGL/Jogl application
 * using a vertex and fragment shader.
 * <p>
 * Please make sure setting the file path and names of the shader correctly (see below).
 * <p>
 * Core code is based on a tutorial by Chua Hock-Chuan
 * http://www3.ntu.edu.sg/home/ehchua/programming/opengl/JOGL2.0.html
 * <p>
 * and on an example by Xerxes Rånby
 * http://jogamp.org/git/?p=jogl-demos.git;a=blob;f=src/demos/es2/RawGL2ES2demo.java;hb=HEAD
 * @author Karsten Lehn
 * @version 22.10.2017
 * <p>
 * Performs the OpenGL graphics processing using the Programmable Pipeline and the
 * OpenGL Core profile
 * <p>
 * Starts an animation loop.
 * Zooming and rotation of the Camera is included (see InteractionHandler).
 * Use: left/right/up/down-keys and +/-Keys and mouse
 * Enables drawing of simple shapes: box, sphere, cone (frustum) and roof
 * Serves as a template (start code) for setting up an OpenGL/Jogl application
 * using a vertex and fragment shader.
 * <p>
 * Please make sure setting the file path and names of the shader correctly (see below).
 * <p>
 * Core code is based on a tutorial by Chua Hock-Chuan
 * http://www3.ntu.edu.sg/home/ehchua/programming/opengl/JOGL2.0.html
 * <p>
 * and on an example by Xerxes Rånby
 * http://jogamp.org/git/?p=jogl-demos.git;a=blob;f=src/demos/es2/RawGL2ES2demo.java;hb=HEAD
 * @author Karsten Lehn
 * @version 22.10.2017
 */

/**
 * Performs the OpenGL graphics processing using the Programmable Pipeline and the
 * OpenGL Core profile
 *
 * Starts an animation loop.
 * Zooming and rotation of the Camera is included (see InteractionHandler).
 * 	Use: left/right/up/down-keys and +/-Keys and mouse
 * Enables drawing of simple shapes: box, sphere, cone (frustum) and roof
 * Serves as a template (start code) for setting up an OpenGL/Jogl application
 * using a vertex and fragment shader.
 *
 * Please make sure setting the file path and names of the shader correctly (see below).
 *
 * Core code is based on a tutorial by Chua Hock-Chuan
 * http://www3.ntu.edu.sg/home/ehchua/programming/opengl/JOGL2.0.html
 *
 * and on an example by Xerxes Rånby
 * http://jogamp.org/git/?p=jogl-demos.git;a=blob;f=src/demos/es2/RawGL2ES2demo.java;hb=HEAD
 *
 * @author Karsten Lehn
 * @version 22.10.2017
 *
 */

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;

import com.jogamp.opengl.math.VectorUtil;
import com.jogamp.opengl.util.PMVMatrix;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;


public class ShapesRendererPP extends GLCanvas implements GLEventListener {

    private static final long serialVersionUID = 1L;
    private static float[][] lightPosition;

    // taking shader source code files from relative path
    private final String shaderPath = ".\\resources\\";
    // Shader for objecttype 1


    /*
    Bela Korb

    Die verschiedenen Fragment- und Vertexshader
    Shader beinhalten berechnungen für Diffuse, Specular, Ambient, Textur, Licht und Lichtabfall
     */
    private final String vertexShader1FileName = "foxShader.vert";
    private final String fragmentShader1FileName = "foxShader.frag";
    private final String vertexShader2FileName = "texturShader.vert";
    private final String fragmentShader2FileName = "texturShader.frag";
    private final String vertexShader3FileName = "torchShader.vert";
    private final String fragmentShader3FileName = "torchShader.frag";


    private LightSource light0;
    private Material material0;
    private Material material1;
    private Material material2;
    private Material material3;
    private GL3 gl;


    private ShaderProgram shaderProgram1;
    private ShaderProgram shaderProgram2;
    private ShaderProgram shaderProgram3;

    // Pointers (names) for data transfer and handling on GPU
    private int[] vaoName;  // Names of vertex array objects
    private int[] vboName;    // Names of vertex buffer objects
    private int[] iboName;    // Names of index buffer objects

    //Textur Buffer
    private int[] texName;
    private int[] textureIndex;

    // Create objects for the scene
    // The box and roof do not need objects because all methods of these classes are static
    //private Sphere sphere0;
    //private Cone cone0;

    // Object for handling keyboard and mouse interaction
    private InteractionHandler interactionHandler;
    // Projection model view matrix tool
    private PMVMatrix pmvMatrix;
    private int numberOfObjects = 0;
    private int imageScale = ImageProcessing.scaleDiameter;
    private Mat boxPointsGotten = ImageProcessing.getLines();
    private Mat wallPoints = new Mat(boxPointsGotten.rows() + 4, 1, CvType.CV_64FC4, Scalar.all(0));
    private Mat torchPoints = new Mat(wallPoints.rows(), 1, CvType.CV_64FC3, Scalar.all(0));
    private double[] floorRoofPoints = new double[4];
    //private Mat pillarPoints;
    private float[] cameraPos = {0, 0, 1f};
    private float[] cameraFront = {0, 0, -1f};
    private float[] cameraFront2 = {0, 0, -1f};
    private float cameraSpeed = 0.1f;
    private float oldYRotation = 0;
    private float lightflickering = 0;
    private float[] charakter;
    private float[] charakterbegin;
    private float[][] charakterbeginFox;
    private float[] charakterFox;

    private float[][] trapVerticesBegin;
    private float[][] trapVertices;

    private float[] charboxmiddle = new float[2];
    private ObjectCollision objectCollision;
    private boolean rotateCharacter;
    private boolean firstFrame = true;

    long runningTime = 0;
    private long oldTime = 0;
    private long currentTime;
    private int[] foxIndices;
    private ObjImport objImport;
    private float[][] torchWalls;
    private float[][] lightPoints;
    private float[][] torchWallsNormals;
    private int[] randomLightIndexes;
    private int randomLightIndexCounter = 0;
    private int torchCounter = 0;
    private int trapCounter = 0;
    private static int numberOfLights;
    private static int numberOfTorches;
    private int numberOfTraps;
    private float trapUpValue = 0;
    private float trapHold = 0;
    private boolean trapBoolean = false;
    public static String time;
    public int timerFox = 0;
    private float intensitys[] = new float[10];

    /**
     * Standard constructor for object creation.
     */
    public ShapesRendererPP() {
        // Create the canvas with default capabilities
        super();

        // Add this object as OpenGL event listener
        this.addGLEventListener(this);
        createAndRegisterInteractionHandler();
    }

    /**Bearbeitet von: Sebastian Schmidt, Michelle Wetscheck, Bela Korb
     * <br>
     * Create the canvas with the requested OpenGL capabilities
     * Startet den ObjImport und Berechnungen, welche vor dem Start aller anderen Methoden durchgeführt werden müssen.
     * @param capabilities The capabilities of the canvas, including the OpenGL profile
     */
    public ShapesRendererPP(GLCapabilities capabilities) {
        // Create the canvas with the requested OpenGL capabilities
        super(capabilities);
        objImport = new ObjImport();
        objectManager();
        //Berechnet die Punkte fuer Licht und Fackeln an den Waenden des Labyrinths
        calculateTorch();
        // Add this object as an OpenGL event listener
        this.addGLEventListener(this);
        createAndRegisterInteractionHandler();

    }

    /**
     * Autor: Michelle Wetscheck
     * <br><br>
     * Es wird die Mitte der erkannten Linien inkl. der Normalen berechnet
     * <br>
     * Dadurch erhalten wird die Postion für beiden Seiten der späteren Wand,
     * an der die Fackeln inkl. Licht platziert werden sollen
     */

    private void calculateTorch() {
        double[] linesCoordinates;
        double[] newCoordinates = new double[3];
        torchWalls = new float[(wallPoints.rows() - 4) * 2][4];
        lightPoints = new float[(wallPoints.rows() - 4) * 2][4];
        torchWallsNormals = new float[(wallPoints.rows() - 4) * 2][4];

        randomLightIndexes = new int[numberOfLights];

        for (int i = 0; i < wallPoints.rows() - 4; i++) {
            linesCoordinates = wallPoints.get(i, 0);


            newCoordinates[0] = linesCoordinates[0] * 0.5 + linesCoordinates[2] * 0.5;
            newCoordinates[2] = linesCoordinates[1] * 0.5 + linesCoordinates[3] * 0.5;
            newCoordinates[1] = 2;

            torchPoints.put(i, 0, newCoordinates);

            float[] leftVec1 = {(float) (linesCoordinates[2] - linesCoordinates[0]), 0, (float) (linesCoordinates[3] - linesCoordinates[1])};
            float[] leftVec2 = {0, 2, 0};
            float[] normalV = new float[3];
            VectorUtil.crossVec3(normalV, leftVec1, leftVec2);


            normalV = VectorUtil.normalizeVec3(normalV);

            //Fackelpunkte
            torchWalls[i * 2][0] = (float) newCoordinates[0] + normalV[0] / 8f;
            torchWalls[i * 2][1] = 2f;
            torchWalls[i * 2][2] = (float) newCoordinates[2] + normalV[2] / 8f;
            torchWalls[i * 2][3] = 1f;
            torchWalls[1 + i * 2][0] = (float) newCoordinates[0] - normalV[0] / 8f;
            torchWalls[1 + i * 2][1] = 2f;
            torchWalls[1 + i * 2][2] = (float) newCoordinates[2] - normalV[2] / 8f;
            torchWalls[1 + i * 2][3] = 1f;
            //Lichtpunkte
            lightPoints[i * 2][0] = (float) (newCoordinates[0] + normalV[0] / 1.6f);
            lightPoints[i * 2][1] = 2f;
            lightPoints[i * 2][2] = (float) (newCoordinates[2] + normalV[2] / 1.6f);
            lightPoints[i * 2][3] = 1f;
            lightPoints[1 + i * 2][0] = (float) (newCoordinates[0] - normalV[0] / 1.6f);
            lightPoints[1 + i * 2][1] = 2f;
            lightPoints[1 + i * 2][2] = (float) (newCoordinates[2] - normalV[2] / 1.6f);
            lightPoints[1 + i * 2][3] = 1f;
/*
            for(int k=0;k<numberOfTorches;k++){
                System.out.println(Arrays.toString(lightPoints[k]));
            }
*/

            torchWallsNormals[i * 2][0] = normalV[0];
            torchWallsNormals[i * 2][1] = 0;
            torchWallsNormals[i * 2][2] = normalV[2];
            torchWallsNormals[i * 2][3] = 1;
            torchWallsNormals[1 + i * 2][0] = -normalV[0];
            torchWallsNormals[1 + i * 2][1] = 0;
            torchWallsNormals[1 + i * 2][2] = -normalV[2];
            torchWallsNormals[1 + i * 2][3] = 1;


        }
        int randomNumberCheck;
        boolean randomNumberCheckBooelan;
        for (int i = 0; i < numberOfLights; i++) {
            randomNumberCheckBooelan = true;
            randomNumberCheck = (int) (Math.random() * (wallPoints.rows() - 4) * 2);
            for (int j = 0; j < numberOfLights; j++) {
                if (randomNumberCheck == randomLightIndexes[j]) {
                    randomNumberCheckBooelan = false;

                }
            }
            if (randomNumberCheckBooelan) {
                randomLightIndexes[i] = randomNumberCheck;
            } else {
                i--;
            }

        }

    }

    /**
     * Autor: Sebastian Schmidt
     * <br>
     *     Berechnung der Gesamtgröße des Labyrinths und die Gesamtanzahl aller an die Grafikkarte zu übermittelden Objekte
     */
    private void objectManager() {
        double[] maxMinPoints = boxPointsGotten.get(0, 0);

        for (int i = 0; i < boxPointsGotten.rows(); i++) {
            double[] tempPoints = boxPointsGotten.get(i, 0);
            //Start search of Floor Points
            if (maxMinPoints[0] < tempPoints[0]) {
                maxMinPoints[0] = tempPoints[0];
            } else if (maxMinPoints[2] > tempPoints[0]) {
                maxMinPoints[2] = tempPoints[0];
            }
            if (maxMinPoints[0] < tempPoints[2]) {
                maxMinPoints[0] = tempPoints[2];
            } else if (maxMinPoints[2] > tempPoints[2]) {
                maxMinPoints[2] = tempPoints[2];
            }
            if (maxMinPoints[1] < tempPoints[1]) {
                maxMinPoints[1] = tempPoints[1];
            } else if (maxMinPoints[3] > tempPoints[1]) {
                maxMinPoints[3] = tempPoints[1];
            }
            if (maxMinPoints[1] < tempPoints[3]) {
                maxMinPoints[1] = tempPoints[3];
            } else if (maxMinPoints[3] > tempPoints[3]) {
                maxMinPoints[3] = tempPoints[3];
            }
            //End of search

            //Scale all Points
            for (int j = 0; j < 4; j++) {
                tempPoints[j] = tempPoints[j] / imageScale;
            }

            wallPoints.put(i, 0, tempPoints);
        }
        //Scale Floor/Roof Points
        for (int h = 0; h < 4; h++) {
            floorRoofPoints[h] = maxMinPoints[h] / imageScale;
        }
        //Außenwände werden berechnet
        for (int k = boxPointsGotten.rows(); k < (boxPointsGotten.rows() + 4); k++) {
            double[] newLinePoints = new double[4];
            switch (k - boxPointsGotten.rows()) {
                case 0:
                    newLinePoints[0] = floorRoofPoints[0]-0.1f;
                    newLinePoints[1] = floorRoofPoints[1]-0.1f;
                    newLinePoints[2] = floorRoofPoints[2]+0.1f;
                    newLinePoints[3] = floorRoofPoints[1]-0.1f;
                    wallPoints.put(k, 0, newLinePoints);
                    break;
                case 1:
                    newLinePoints[0] = floorRoofPoints[2]+0.1f;
                    newLinePoints[1] = floorRoofPoints[1]-0.1f;
                    newLinePoints[2] = floorRoofPoints[2]+0.1f;
                    newLinePoints[3] = floorRoofPoints[3]+0.1f;
                    wallPoints.put(k, 0, newLinePoints);
                    break;
                case 2:
                    newLinePoints[0] = floorRoofPoints[2]+0.1f;
                    newLinePoints[1] = floorRoofPoints[3]+0.1f;
                    newLinePoints[2] = floorRoofPoints[0]-0.1f;
                    newLinePoints[3] = floorRoofPoints[3]+0.1f;
                    wallPoints.put(k, 0, newLinePoints);
                    break;
                case 3:
                    newLinePoints[0] = floorRoofPoints[0]-0.1f;
                    newLinePoints[1] = floorRoofPoints[3]+0.1f;
                    newLinePoints[2] = floorRoofPoints[0]-0.1f;
                    newLinePoints[3] = floorRoofPoints[1]-0.1f;
                    wallPoints.put(k, 0, newLinePoints);
                    break;
            }
        }

        int l = 0;
        Point[] points = new Point[ImageProcessing.trapPoints.size()];
        for (Point p : ImageProcessing.trapPoints) {
            points[l] = p;
            points[l].x = points[l].x / imageScale;
            points[l].y = points[l].y / imageScale;
            l++;
        }
        Point newEndPoint = new Point(ImageProcessing.endPoint.x / imageScale, ImageProcessing.endPoint.y / imageScale);
        objectCollision = new ObjectCollision(wallPoints, points, newEndPoint);

        //Calculate full Number of Objects
        numberOfObjects += wallPoints.rows();
        numberOfObjects += 0;
        //For Charakter Box and Floor+Roof
        numberOfObjects += 3;
        //fox
        numberOfObjects += 1;
        //chest
        numberOfObjects += 1;
        numberOfTraps = ImageProcessing.trapPoints.size();
        numberOfObjects += numberOfTraps;
        //torches
        numberOfLights = 10;
        numberOfTorches = (wallPoints.rows() - 4) * 2;
        numberOfObjects += numberOfTorches;
    }

    /**
     * Helper method for creating an interaction handler object and registering it
     * for key press and mouse interaction callbacks.
     */
    private void createAndRegisterInteractionHandler() {
        // The constructor call of the interaction handler generates meaningful default values
        // Nevertheless the start parameters can be set via setters
        // (see class definition of the interaction handler)
        interactionHandler = new InteractionHandler();
        this.addKeyListener(interactionHandler);
        this.addMouseListener(interactionHandler);
        this.addMouseMotionListener(interactionHandler);
        this.addMouseWheelListener(interactionHandler);
    }

    /**Verändert durch: Bela Korb, Sebastian Schmidt
     * Implementation of the OpenGL EventListener (GLEventListener) method
     * that is called when the OpenGL renderer is started for the first time.
     * @param drawable The OpenGL drawable
     */
    @Override
    public void init(GLAutoDrawable drawable) {
        gl = drawable.getGL().getGL3();

        System.err.println("Chosen GLCapabilities: " + drawable.getChosenGLCapabilities());
        System.err.println("INIT GL IS: " + gl.getClass().getName());
        System.err.println("GL_VENDOR: " + gl.glGetString(GL.GL_VENDOR));
        System.err.println("GL_RENDERER: " + gl.glGetString(GL.GL_RENDERER));
        System.err.println("GL_VERSION: " + gl.glGetString(GL.GL_VERSION));

        // Verify if VBO-Support is available
        if (!gl.isExtensionAvailable("GL_ARB_vertex_buffer_object"))
            System.out.println("Error: VBO support is missing");
        else
            System.out.println("VBO support is available");


        /*
        Bela Korb

        Erstellen von Licht und Material Eigenschaften
         */
        lightPosition = lightPoints;

        float[] lightAmbientColor = {1.8f, 1.8f, 1.8f, 1.0f};
        float[] lightDiffuseColor = {(float) 231 / 255, (float) 161 / 255, (float) 22 / 255, 1.0f};
        float[] lightSpecularColor = {1f, 0.55f, (float) 22 / 255, 1.0f};
        float lightIntesity = 1;
        light0 = new LightSource(lightPosition, lightAmbientColor, lightDiffuseColor, lightSpecularColor, lightIntesity);

        float[] matEmission0 = {0.0f, 0.0f, 0.0f, 1.0f};
        float[] matAmbient0 = {0.25f, 0.25f, 0.25f, 1.0f};
        float[] matDiffuse0 = {0.9f, 0.9f, 0.9f, 1.0f};

        float[] matSpecular0 = {0.7f, 0.7f, 0.7f, 1.0f};
        float matShininess0 = 80.0f;
        material0 = new Material(matEmission0, matAmbient0, matDiffuse0, matSpecular0, matShininess0);

        float[] matEmission1 = {0.0f, 0.0f, 0.0f, 1.0f};
        float[] matAmbient1 = {(float) 40 / 255, (float) 12 / 255, 0f, 1.0f};
        float[] matDiffuse1 = {(float) 170 / 255, (float) 85 / 255, (float) 31 / 255, 1.0f};

        float[] matSpecular1 = {0.1f, 0.1f, 0.1f, 1.0f};
        float matShininess1 = 40.0f;
        material1 = new Material(matEmission1, matAmbient1, matDiffuse1, matSpecular1, matShininess1);

        float[] matEmission2 = {0.0f, 0.0f, 0.0f, 1.0f};
        float[] matAmbient2 = {(float) 28 / 255, (float) 22 / 255, (float) 22 / 255, 1.0f};
        float[] matDiffuse2 = {(float) 220 / 255, (float) 220 / 255, (float) 220 / 255, 1.0f};

        float[] matSpecular2 = {0.1f, 0.1f, 0.1f, 1.0f};
        float matShininess2 = 40.0f;
        material2 = new Material(matEmission2, matAmbient2, matDiffuse2, matSpecular2, matShininess2);

        float[] matEmission3 = {0.0f, 0.0f, 0.0f, 1.0f};
        float[] matAmbient3 = {(float) 50 / 255, (float) 30 / 255, (float) 5 / 255, 1.0f};
        float[] matDiffuse3 = {(float) 200 / 255, (float) 139 / 255, (float) 23 / 255, 1.0f};

        float[] matSpecular3 = {1f, 1f, 1f, 1.0f};
        float matShininess3 = 80000.0f;
        material3 = new Material(matEmission3, matAmbient3, matDiffuse3, matSpecular3, matShininess3);


        // BEGIN: Preparing scene
        // BEGIN: Allocating vertex array objects and buffers for each object
        // create vertex array objects for noOfObjects objects (VAO)
        vaoName = new int[numberOfObjects];
        gl.glGenVertexArrays(numberOfObjects, vaoName, 0);
        if (vaoName[0] < 1)
            System.err.println("Error allocating vertex array object (VAO).");

        // create vertex buffer objects for noOfObjects objects (VBO)
        vboName = new int[numberOfObjects];
        gl.glGenBuffers(numberOfObjects, vboName, 0);
        if (vboName[0] < 1)
            System.err.println("Error allocating vertex buffer object (VBO).");

        // create index buffer objects for noOfObjects objects (IBO)
        iboName = new int[numberOfObjects];
        gl.glGenBuffers(numberOfObjects, iboName, 0);
        if (iboName[0] < 1)
            System.err.println("Error allocating index buffer object.");
        // END: Allocating vertex array objects and buffers for each object
        //
        texName = new int[3];
        gl.glGenTextures(3, texName, 0);

        textureIndex = new int[numberOfObjects];


        shaderProgram1 = new ShaderProgram(gl);
        shaderProgram1.loadShaderAndCreateProgram(shaderPath,
                vertexShader1FileName, fragmentShader1FileName);

        shaderProgram2 = new ShaderProgram(gl);
        shaderProgram2.loadShaderAndCreateProgram(shaderPath,
                vertexShader2FileName, fragmentShader2FileName);

        shaderProgram3 = new ShaderProgram(gl);
        shaderProgram3.loadShaderAndCreateProgram(shaderPath,
                vertexShader3FileName, fragmentShader3FileName);

        //Laden der Texturen in Buffer
        TextureLoader.textureBufferLoader();
        // Initialize objects to be drawn (see respective sub-methods)

        for (int i = 0; i < wallPoints.rows(); i++) {
            //System.out.println(i);
            initObjectWall(gl, i);
        }
        for (int i = wallPoints.rows(); i < wallPoints.rows() + 2; i++) {
            initObjectFloorRoof(gl, i);
        }
        initObjectCharacter(gl, wallPoints.rows() + 2);
        initObjectCharacterFox(gl, wallPoints.rows() + 3);
        initObjectChest(gl, numberOfObjects - numberOfTraps - numberOfTorches - 1);

        //Anfangs Vertices speichern, damit man die bewegende Falle zuruecksetzen kann
        trapVerticesBegin = new float[objImport.getTrapVert().length][numberOfTraps];
        trapVertices = new float[objImport.getTrapVert().length][numberOfTraps];
        for (int i = 0; i < numberOfTraps; i++) {
            initObjectTrap(gl, numberOfObjects - numberOfTorches - numberOfTraps + i);
        }

        for (int i = 0; i < numberOfTorches; i++) {
            initObjectTorch(gl, numberOfObjects - numberOfTorches + i);
        }

        TextureLoader.deleteBuffers();

        // END: Preparing scene
        // Initialize objects to be drawn (see respective sub-methods)


        // Switch on back face culling
        gl.glEnable(GL.GL_CULL_FACE);
        gl.glCullFace(GL.GL_BACK);
//        gl.glCullFace(GL.GL_FRONT);
        // Switch on depth test
        gl.glEnable(GL.GL_DEPTH_TEST);

        // defining polygon drawing mode
        gl.glPolygonMode(GL.GL_FRONT_AND_BACK, gl.GL_FILL);
        //gl.glPolygonMode(GL.GL_FRONT_AND_BACK, gl.GL_LINE);

        // Create projection-model-view matrix
        pmvMatrix = new PMVMatrix();
        //pmvMatrix.glTranslatef(0, 2f, 0);


        // Start parameter settings for the interaction handler might be called here
        // END: Preparing scene
    }


    /**
     * angelehnt an Startcode
     * veraendert durch: Bela Korb, Sebastian Schmidt
     *
     * Methode erstellt die Wandobjekte
     * Initializes the GPU for drawing Wall
     * @param gl OpenGL context
     * @param objectNumber
     */
    private void initObjectWall(GL3 gl, int objectNumber) {
        // BEGIN: Prepare cube for drawing (object 1)
        gl.glBindVertexArray(vaoName[objectNumber]);
        textureIndex[objectNumber] = 0;
        new TextureLoader(gl, textureIndex[objectNumber], textureIndex[objectNumber]);

        double[] coordinates = wallPoints.get(objectNumber, 0);
        float[] color1 = {0.1f, 0.1f, 0.8f};
        float[] cubeVertices;

        cubeVertices = Box.makeWall(((float) coordinates[0]), ((float) coordinates[1]), ((float) coordinates[2]), ((float) coordinates[3]), color1);

        //float[] cubeVertices = Box.makeBoxVertices(0.8f, 0.5f, 0.4f, color1);
        int[] cubeIndices = Box.makeBoxIndicesForTriangleStrip();


        // activate and initialize vertex buffer object (VBO)
        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vboName[objectNumber]);
        // floats use 4 bytes in Java
        gl.glBufferData(GL.GL_ARRAY_BUFFER, cubeVertices.length * 4,
                FloatBuffer.wrap(cubeVertices), GL.GL_STATIC_DRAW);

        // activate and initialize index buffer object (IBO)
        gl.glBindBuffer(GL.GL_ELEMENT_ARRAY_BUFFER, iboName[objectNumber]);
        // integers use 4 bytes in Java
        gl.glBufferData(GL.GL_ELEMENT_ARRAY_BUFFER, cubeIndices.length * 4,
                IntBuffer.wrap(cubeIndices), GL.GL_STATIC_DRAW);


        // Activate and order vertex buffer object data for the vertex shader
        // The vertex buffer contains: position (3), color (3), normals (3)
        // Defining input for vertex shader
        // Pointer for the vertex shader to the position information per vertex
        gl.glEnableVertexAttribArray(0);
        gl.glVertexAttribPointer(0, 3, GL.GL_FLOAT, false, 11 * 4, 0);
        // Pointer for the vertex shader to the color information per vertex
        gl.glEnableVertexAttribArray(1);
        gl.glVertexAttribPointer(1, 3, GL.GL_FLOAT, false, 11 * 4, 3 * 4);
        // Pointer for the vertex shader to the normal information per vertex
        gl.glEnableVertexAttribArray(2);
        gl.glVertexAttribPointer(2, 3, GL.GL_FLOAT, false, 11 * 4, 6 * 4);

        gl.glEnableVertexAttribArray(3);
        gl.glVertexAttribPointer(3, 2, GL.GL_FLOAT, false, 11 * 4, 9 * 4);
        // END: Prepare cube for drawing
    }

    /**
     * angelehnt an Startcode
     * veraendert durch: Bela Korb, Sebastian Schmidt
     *
     * Methode erstellt Boden und Decke
     * Initializes the GPU for drawing Floor and Roof
     * @param gl OpenGL context
     * @param objectNumber
     */
    private void initObjectFloorRoof(GL3 gl, int objectNumber) {
        // BEGIN: Prepare cube for drawing (object 1)


        gl.glBindVertexArray(vaoName[objectNumber]);


        double[] coordinates = floorRoofPoints;
        float[] cubeVertices;
        if (objectNumber == wallPoints.rows()) {
            textureIndex[objectNumber] = 2;
            new TextureLoader(gl, textureIndex[objectNumber], textureIndex[objectNumber]);
            float[] color1 = {0.1f, 0.4f, 0.1f};
            cubeVertices = Box.makeFloor(((float) coordinates[0]), ((float) coordinates[1]), ((float) coordinates[2]), ((float) coordinates[3]), color1);
        } else {
            textureIndex[objectNumber] = 1;
            new TextureLoader(gl, textureIndex[objectNumber], textureIndex[objectNumber]);
            float[] color2 = {0.2f, 0.4f, 0.5f};
            cubeVertices = Box.makeRoof(((float) coordinates[0]), ((float) coordinates[1]), ((float) coordinates[2]), ((float) coordinates[3]), color2);
        }

        //float[] cubeVertices = Box.makeBoxVertices(0.8f, 0.5f, 0.4f, color1);
        int[] cubeIndices = Box.makeBoxIndicesForTriangleStrip();

        // activate and initialize vertex buffer object (VBO)
        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vboName[objectNumber]);
        // floats use 4 bytes in Java
        gl.glBufferData(GL.GL_ARRAY_BUFFER, cubeVertices.length * 4,
                FloatBuffer.wrap(cubeVertices), GL.GL_STATIC_DRAW);

        // activate and initialize index buffer object (IBO)
        gl.glBindBuffer(GL.GL_ELEMENT_ARRAY_BUFFER, iboName[objectNumber]);
        // integers use 4 bytes in Java
        gl.glBufferData(GL.GL_ELEMENT_ARRAY_BUFFER, cubeIndices.length * 4,
                IntBuffer.wrap(cubeIndices), GL.GL_STATIC_DRAW);

        // Activate and order vertex buffer object data for the vertex shader
        // The vertex buffer contains: position (3), color (3), normals (3)
        // Defining input for vertex shader
        // Pointer for the vertex shader to the position information per vertex
        gl.glEnableVertexAttribArray(0);
        gl.glVertexAttribPointer(0, 3, GL.GL_FLOAT, false, 11 * 4, 0);
        // Pointer for the vertex shader to the color information per vertex
        gl.glEnableVertexAttribArray(1);
        gl.glVertexAttribPointer(1, 3, GL.GL_FLOAT, false, 11 * 4, 3 * 4);
        // Pointer for the vertex shader to the normal information per vertex
        gl.glEnableVertexAttribArray(2);
        gl.glVertexAttribPointer(2, 3, GL.GL_FLOAT, false, 11 * 4, 6 * 4);

        gl.glEnableVertexAttribArray(3);
        gl.glVertexAttribPointer(3, 2, GL.GL_FLOAT, false, 11 * 4, 9 * 4);
        // END: Prepare cube for drawing
    }

    /**
     * angelehnt an Startcode
     * veraendert durch: Bela Korb
     *
     * Methode zum erstellen der Charakterhitabox(wird im Endgueltigen Programm nicht mehr angezeigt)
     * Initializes the GPU for drawing Character
     * @param gl OpenGL context
     * @param objectNumber
     */
    private void initObjectCharacter(GL3 gl, int objectNumber) {
        // BEGIN: Prepare cube for drawing (object 1)


        gl.glBindVertexArray(vaoName[objectNumber]);


        // gl.glBindTexture(GL.GL_TEXTURE_2D, texName[objectNumber]);
        new TextureLoader(gl, 1, 1);
        textureIndex[objectNumber] = 0;


        float[] cubeVertices;
        float[] color3 = {0.5f, 0.1f, 0.1f};
        charakterbegin = Box.makeBoxVertices(0.2f, 0.3f, 0.6f, color3);
        charboxmiddle[0] = 0;
        charboxmiddle[1] = 0;
        //anfangspunkte werden gespeichert
        cubeVertices = charakterbegin;
        charakter = new float[charakterbegin.length];
        for (int i = 0; i < charakterbegin.length; i++) {
            charakter[i] = charakterbegin[i];
        }

        int[] cubeIndices = Box.makeBoxIndicesForTriangleStrip();

        // activate and initialize vertex buffer object (VBO)
        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vboName[objectNumber]);
        // floats use 4 bytes in Java
        gl.glBufferData(GL.GL_ARRAY_BUFFER, cubeVertices.length * 4,
                FloatBuffer.wrap(cubeVertices), GL.GL_STATIC_DRAW);

        // activate and initialize index buffer object (IBO)
        gl.glBindBuffer(GL.GL_ELEMENT_ARRAY_BUFFER, iboName[objectNumber]);
        // integers use 4 bytes in Java
        gl.glBufferData(GL.GL_ELEMENT_ARRAY_BUFFER, cubeIndices.length * 4,
                IntBuffer.wrap(cubeIndices), GL.GL_STATIC_DRAW);


        // Activate and order vertex buffer object data for the vertex shader
        // The vertex buffer contains: position (3), color (3), normals (3)
        // Defining input for vertex shader
        // Pointer for the vertex shader to the position information per vertex
        gl.glEnableVertexAttribArray(0);
        gl.glVertexAttribPointer(0, 3, GL.GL_FLOAT, false, 11 * 4, 0);
        // Pointer for the vertex shader to the color information per vertex
        gl.glEnableVertexAttribArray(1);
        gl.glVertexAttribPointer(1, 3, GL.GL_FLOAT, false, 11 * 4, 3 * 4);
        // Pointer for the vertex shader to the normal information per vertex
        gl.glEnableVertexAttribArray(2);
        gl.glVertexAttribPointer(2, 3, GL.GL_FLOAT, false, 11 * 4, 6 * 4);

        gl.glEnableVertexAttribArray(3);
        gl.glVertexAttribPointer(3, 2, GL.GL_FLOAT, false, 11 * 4, 9 * 4);


        // END: Prepare cube for drawing
    }

    /**
     * angelehnt an Startcode
     * veraendert durch: Bela Korb
     * Methode zum erstellen des Fuchs
     * Initializes the GPU for drawing Fox
     * @param gl OpenGL context
     * @param objectNumber
     */
    private void initObjectCharacterFox(GL3 gl, int objectNumber) {
        // BEGIN: Prepare cube for drawing (object 1)


        gl.glBindVertexArray(vaoName[objectNumber]);


        float[] cubeVertices;

        charakterbeginFox = objImport.getFoxVertices();
        charboxmiddle[0] = 0;
        charboxmiddle[1] = 0;
        cubeVertices = charakterbeginFox[0];
        //Fuchs vertices werden eingelesen und gespeichert

        charakterFox = new float[charakterbeginFox.length];
        for (int x = 0; x < charakterbeginFox.length; x++) {
            for (int i = 0; i < charakterbeginFox[x].length; i++) {
                //skaliertung der Vertices
                charakterbeginFox[x][i] = (float) (charakterbeginFox[x][i] * 0.06);
                charakterFox[x] = charakterbeginFox[x][i];
            }
        }
        //laden der Indices
        foxIndices = objImport.getFoxIndices();

        // activate and initialize vertex buffer object (VBO)
        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vboName[objectNumber]);
        // floats use 4 bytes in Java
        gl.glBufferData(GL.GL_ARRAY_BUFFER, cubeVertices.length * 4,
                FloatBuffer.wrap(cubeVertices), GL.GL_STATIC_DRAW);

        // activate and initialize index buffer object (IBO)
        gl.glBindBuffer(GL.GL_ELEMENT_ARRAY_BUFFER, iboName[objectNumber]);
        // integers use 4 bytes in Java
        gl.glBufferData(GL.GL_ELEMENT_ARRAY_BUFFER, foxIndices.length * 4,
                IntBuffer.wrap(foxIndices), GL.GL_STATIC_DRAW);


        // Activate and order vertex buffer object data for the vertex shader
        // The vertex buffer contains: position (3), normals (3)
        // Defining input for vertex shader
        // Pointer for the vertex shader to the position information per vertex
        gl.glEnableVertexAttribArray(0);
        gl.glVertexAttribPointer(0, 3, GL.GL_FLOAT, false, 6 * 4, 0);
        // Pointer for the vertex shader to the normal information per vertex
        gl.glEnableVertexAttribArray(1);
        gl.glVertexAttribPointer(1, 3, GL.GL_FLOAT, false, 6 * 4, 3 * 4);

        // END: Prepare cube for drawing
    }

    /**
     * angelehnt an Startcode
     * veraendert durch: Bela Korb
     * Methode zum erstellen und plazieren der Fackeln
     * Initializes the GPU for drawing Torches
     * @param gl OpenGL context
     * @param objectNumber
     */
    private void initObjectTorch(GL3 gl, int objectNumber) {
        // BEGIN: Prepare cube for drawing (object 1)


        gl.glBindVertexArray(vaoName[objectNumber]);


        float[] cubeVertices;
        int[] cubeIndices;
        //speichert Vertices
        cubeVertices = objImport.getTorchVert().clone();
        //skaliert Vertices
        for (int i = 0; i < cubeVertices.length; i++) {
            cubeVertices[i] = (float) (cubeVertices[i] * 0.17);
        }
        float angle;
        float angleTemp;
        float[] torchDirection = {1, 0, 0, 1};

        //berechnet Winkel, um den die Fackel gedreht werden muss um richtig an der Wand zu sitzen
        angle = (float) Math.toDegrees(Math.acos((1 * torchWallsNormals[torchCounter][0]) / (1 * Math.sqrt(torchWallsNormals[torchCounter][0] * torchWallsNormals[torchCounter][0] + torchWallsNormals[torchCounter][2] * torchWallsNormals[torchCounter][2]))));

        //erstellt Rotationsmatrix mit dem bestimmten Winkel
        float[] r = CustomMath.rotmat(angle);

        //es wird geguckt in welche Richtung gedreht werden muss
        for (int i = 0; i < 1; i++) {

            float[] verticestemp = new float[4];

            for (int l = 0; l < 4; l++) {

                verticestemp[l] = r[l * 4] * torchDirection[0] + r[l * 4 + 1] * torchDirection[1] + r[l * 4 + 2] * torchDirection[2] + r[l * 4 + 3] * torchDirection[3];

            }
            for (int k = 0; k < 3; k++) {
                torchDirection[k] = verticestemp[k];
            }
        }
        angleTemp = (float) Math.toDegrees(Math.acos((torchDirection[0] * torchWallsNormals[torchCounter][0] + torchDirection[2] * torchWallsNormals[torchCounter][2]) / (Math.sqrt(torchDirection[0] * torchDirection[0] + torchDirection[2] * torchDirection[2]) * Math.sqrt(torchWallsNormals[torchCounter][0] * torchWallsNormals[torchCounter][0] + torchWallsNormals[torchCounter][2] * torchWallsNormals[torchCounter][2]))));

        if (angleTemp > 2) {
            r = CustomMath.rotmat(-angle);
        }
        //erstellt Translationsmatrix, fuer Translation an die richtige Position
        float[] t = CustomMath.transmat(torchWalls[torchCounter][0], 2f, torchWalls[torchCounter][2]);
        //multipliziert Rotations- und Translationsmatrix fuer
        float[] j = CustomMath.matrixmult(t, r);


        cubeVertices = CustomMath.matMultVek(j, r, cubeVertices);

        //float[] cubeVertices = Box.makeBoxVertices(0.8f, 0.5f, 0.4f, color1);
        cubeIndices = objImport.getTorchInd();

        // activate and initialize vertex buffer object (VBO)
        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vboName[objectNumber]);
        // floats use 4 bytes in Java
        gl.glBufferData(GL.GL_ARRAY_BUFFER, cubeVertices.length * 4,
                FloatBuffer.wrap(cubeVertices), GL.GL_STATIC_DRAW);

        // activate and initialize index buffer object (IBO)
        gl.glBindBuffer(GL.GL_ELEMENT_ARRAY_BUFFER, iboName[objectNumber]);
        // integers use 4 bytes in Java
        gl.glBufferData(GL.GL_ELEMENT_ARRAY_BUFFER, cubeIndices.length * 4,
                IntBuffer.wrap(cubeIndices), GL.GL_STATIC_DRAW);


        // Activate and order vertex buffer object data for the vertex shader
        // The vertex buffer contains: position (3), normals (3)
        // Defining input for vertex shader
        // Pointer for the vertex shader to the position information per vertex
        gl.glEnableVertexAttribArray(0);
        gl.glVertexAttribPointer(0, 3, GL.GL_FLOAT, false, 6 * 4, 0);
        // Pointer for the vertex shader to the normal information per vertex
        gl.glEnableVertexAttribArray(1);
        gl.glVertexAttribPointer(1, 3, GL.GL_FLOAT, false, 6 * 4, 3 * 4);


        // END: Prepare cube for drawing

        torchCounter++;
    }

    /**
     * angelehnt an Startcode
     * veraendert durch: Bela Korb
     * Methode zum erstellen und plazieren der Fallen
     * Initializes the GPU for drawing Traps
     * @param gl OpenGL context
     * @param objectNumber
     */
    private void initObjectTrap(GL3 gl, int objectNumber) {
        // BEGIN: Prepare cube for drawing (object 1)


        gl.glBindVertexArray(vaoName[objectNumber]);

        float[] cubeVertices;
        int[] cubeIndices;
        //speichern der Fallenpunkte
        trapVerticesBegin[trapCounter] = objImport.getTrapVert().clone();
        //Skalieren der Vertices
        for (int i = 0; i < trapVerticesBegin.length; i++) {
            trapVerticesBegin[trapCounter][i] = (float) (trapVerticesBegin[trapCounter][i] * 0.2);
        }
        //erstellen rotations und translationsmatrix
        float[] j = CustomMath.transmat((float) ImageProcessing.trapPoints.get(trapCounter).x, 0f, (float) ImageProcessing.trapPoints.get(trapCounter).y);
        float[] r = CustomMath.rotmat(0);

        //Multiplikation matrix vektor
        //Punkte werden mit Matrix berechnet und Normalen mit Rotationsmatrix
        cubeVertices = CustomMath.matMultVek(j, r, trapVerticesBegin[trapCounter]);

        cubeIndices = objImport.getTrapInd();

        // activate and initialize vertex buffer object (VBO)
        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vboName[objectNumber]);
        // floats use 4 bytes in Java
        gl.glBufferData(GL.GL_ARRAY_BUFFER, cubeVertices.length * 4,
                FloatBuffer.wrap(cubeVertices), GL.GL_STATIC_DRAW);

        // activate and initialize index buffer object (IBO)
        gl.glBindBuffer(GL.GL_ELEMENT_ARRAY_BUFFER, iboName[objectNumber]);
        // integers use 4 bytes in Java
        gl.glBufferData(GL.GL_ELEMENT_ARRAY_BUFFER, cubeIndices.length * 4,
                IntBuffer.wrap(cubeIndices), GL.GL_STATIC_DRAW);


        // Activate and order vertex buffer object data for the vertex shader
        // The vertex buffer contains: position (3), normals (3)
        // Defining input for vertex shader
        // Pointer for the vertex shader to the position information per vertex
        gl.glEnableVertexAttribArray(0);
        gl.glVertexAttribPointer(0, 3, GL.GL_FLOAT, false, 6 * 4, 0);
        // Pointer for the vertex shader to the normal information per vertex
        gl.glEnableVertexAttribArray(1);
        gl.glVertexAttribPointer(1, 3, GL.GL_FLOAT, false, 6 * 4, 3 * 4);


        // END: Prepare cube for drawing

        trapCounter++;
    }

    /**
     * angelehnt an Startcode
     * veraendert durch: Bela Korb
     * Methode zum erstellen und plazieren der Chest
     * Initializes the GPU for drawing Chest
     * @param gl OpenGL context
     * @param objectNumber
     */
    private void initObjectChest(GL3 gl, int objectNumber) {
        // BEGIN: Prepare cube for drawing (object 1)


        gl.glBindVertexArray(vaoName[objectNumber]);


        float[] cubeVertices;
        int[] cubeIndices;

        cubeVertices = objImport.getChestVert().clone();

        //Berechnung der Matrizen
        float[] j = CustomMath.transmat((float) ImageProcessing.endPoint.x / imageScale, 0f, (float) ImageProcessing.endPoint.y / imageScale);
        float[] r = CustomMath.rotmat(0);

        //berechnung matrix * vektor
        cubeVertices = CustomMath.matMultVek(j, r, cubeVertices);

        cubeIndices = objImport.getChestInd();

        // activate and initialize vertex buffer object (VBO)
        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vboName[objectNumber]);
        // floats use 4 bytes in Java
        gl.glBufferData(GL.GL_ARRAY_BUFFER, cubeVertices.length * 4,
                FloatBuffer.wrap(cubeVertices), GL.GL_STATIC_DRAW);

        // activate and initialize index buffer object (IBO)
        gl.glBindBuffer(GL.GL_ELEMENT_ARRAY_BUFFER, iboName[objectNumber]);
        // integers use 4 bytes in Java
        gl.glBufferData(GL.GL_ELEMENT_ARRAY_BUFFER, cubeIndices.length * 4,
                IntBuffer.wrap(cubeIndices), GL.GL_STATIC_DRAW);


        // Activate and order vertex buffer object data for the vertex shader
        // The vertex buffer contains: position (3), normals (3)
        // Defining input for vertex shader
        // Pointer for the vertex shader to the position information per vertex
        gl.glEnableVertexAttribArray(0);
        gl.glVertexAttribPointer(0, 3, GL.GL_FLOAT, false, 6 * 4, 0);
        // Pointer for the vertex shader to the normal information per vertex
        gl.glEnableVertexAttribArray(1);
        gl.glVertexAttribPointer(1, 3, GL.GL_FLOAT, false, 6 * 4, 3 * 4);


        // END: Prepare cube for drawing

    }

    /**
     * Verandert durch: Bela Korb, Sebastian Schmidt
     *
     *
     * @param drawable
     */
    @Override
    public void display(GLAutoDrawable drawable) {

        GL3 gl = drawable.getGL().getGL3();
        gl.glClear(GL3.GL_COLOR_BUFFER_BIT | GL3.GL_DEPTH_BUFFER_BIT);

        // Background color of the canvas
        gl.glClearColor(0f, 0f, 0f, 1.0f);

        //von Bela Korb
        //Fackel flackerberechnung durch aneinanderreihung von verschiedenen Sinus und Cosinus Kurven
        for (int i = 0; i < numberOfLights; i++) {
            intensitys[i] = (2 * (float) (Math.pow(Math.sin(lightflickering + 20 * i), 2) * 0.4f + Math.pow(Math.cos(lightflickering * 3 + 20 * i), 2) * 0.5f + Math.pow(Math.sin(8 * lightflickering + 20 * i), 2) * 0.3f + Math.pow(Math.cos(5 * lightflickering + 20 * i), 2) * Math.pow(Math.sin(lightflickering * 0.7 + 20 * i), 2) + 1));
        }

        lightflickering += 0.005;
        //ende Bela Korb

        // Using the PMV-Tool for geometric transforms
        pmvMatrix.glMatrixMode(PMVMatrix.GL_MODELVIEW);
        pmvMatrix.glLoadIdentity();
        // Setting the camera position, based on user input

        //von Sebastian Schmidt
        //aenderung der Mausbewegung fuehrt zu Rotation
        rotateCharacter = false;
        float[] front = {0, 0, 0};
        float[] front2 = {0, 0, 0};
        front[0] = (float) Math.cos(Math.toRadians(interactionHandler.getAngleYaxis()));
        front[2] = (float) Math.sin(Math.toRadians(interactionHandler.getAngleYaxis()));
        front2[0] = (float) Math.cos(Math.PI / 2 + Math.toRadians(interactionHandler.getAngleYaxis()));
        front2[2] = (float) Math.sin(Math.PI / 2 + Math.toRadians(interactionHandler.getAngleYaxis()));
        cameraFront = VectorUtil.normalizeVec3(front);
        cameraFront2 = VectorUtil.normalizeVec3(front2);

        //Kamerabewegung in die entprechende Richtung
        float[] cameraPosOld = new float[3];
        cameraPosOld[0] = cameraPos[0];
        cameraPosOld[1] = cameraPos[1];
        cameraPosOld[2] = cameraPos[2];
        if (!interactionHandler.isEscKeyPressed()) {
            if (interactionHandler.iswKeyPressed() && interactionHandler.isaKeyPressed()) {
                cameraPos[0] += cameraSpeed * VectorUtil.normalizeVec3(CustomMath.matrixadd(front, front2))[0];
                cameraPos[2] += cameraSpeed * VectorUtil.normalizeVec3(CustomMath.matrixadd(front, front2))[2];
                rotateCharacter = true;
            } else if (interactionHandler.iswKeyPressed() && interactionHandler.isdKeyPressed()) {
                float[] t = new float[3];
                t[0] = -front[0];
                t[1] = -front[1];
                t[2] = -front[2];
                cameraPos[0] += cameraSpeed * VectorUtil.normalizeVec3(CustomMath.matrixadd(t, front2))[0];
                cameraPos[2] += cameraSpeed * VectorUtil.normalizeVec3(CustomMath.matrixadd(t, front2))[2];
                rotateCharacter = true;
            } else if (interactionHandler.issKeyPressed() && interactionHandler.isaKeyPressed()) {
                float[] t = new float[3];
                t[0] = -front2[0];
                t[1] = -front2[1];
                t[2] = -front2[2];
                cameraPos[0] += cameraSpeed * VectorUtil.normalizeVec3(CustomMath.matrixadd(front, t))[0];
                cameraPos[2] += cameraSpeed * VectorUtil.normalizeVec3(CustomMath.matrixadd(front, t))[2];
                rotateCharacter = true;
            } else if (interactionHandler.issKeyPressed() && interactionHandler.isdKeyPressed()) {
                cameraPos[0] -= cameraSpeed * VectorUtil.normalizeVec3(CustomMath.matrixadd(front, front2))[0];
                cameraPos[2] -= cameraSpeed * VectorUtil.normalizeVec3(CustomMath.matrixadd(front, front2))[2];
                rotateCharacter = true;
            } else if ((interactionHandler.issKeyPressed() && interactionHandler.iswKeyPressed()) || (interactionHandler.isaKeyPressed() && interactionHandler.isdKeyPressed())) {
                //do nothing
            } else if (interactionHandler.iswKeyPressed()) {
                cameraPos[0] += cameraSpeed * cameraFront2[0];
                cameraPos[2] += cameraSpeed * cameraFront2[2];
                rotateCharacter = true;
            } else if (interactionHandler.issKeyPressed()) {
                cameraPos[0] -= cameraSpeed * cameraFront2[0];
                cameraPos[2] -= cameraSpeed * cameraFront2[2];
                rotateCharacter = true;
            } else if (interactionHandler.isaKeyPressed()) {
                cameraPos[0] += cameraSpeed * cameraFront[0];
                cameraPos[2] += cameraSpeed * cameraFront[2];
                rotateCharacter = true;
            } else if (interactionHandler.isdKeyPressed()) {
                cameraPos[0] -= cameraSpeed * cameraFront[0];
                cameraPos[2] -= cameraSpeed * cameraFront[2];
                rotateCharacter = true;
            }
        }
        if (firstFrame) {
            this.requestFocus();
            Main.m.setVisible(false);
            firstFrame = false;
            interactionHandler.setAngleYaxis(45);
            cameraPos[0] = -(float) ImageProcessing.startPoint.x / imageScale;
            cameraPos[2] = -(float) ImageProcessing.startPoint.y / imageScale;
        }
        //ende Sebastian Schmidt

        //Bela Korb, Sebastian Schmidt
        //Berchung der Rotationsmatrix fuer den Charakter
        float[] r2;
        if (rotateCharacter) {
            r2 = CustomMath.rotmat(interactionHandler.getAngleYaxis() - 180);
            oldYRotation = interactionHandler.getAngleYaxis();
        } else {
            r2 = CustomMath.rotmat(oldYRotation - 180);
        }
        //Bela Korb
        //Multiplikation Rotations und Translationsmatrix
        float[] j2 = CustomMath.matrixmult(r2, CustomMath.transmat(cameraPos[0], cameraPos[2]));


        //Sebastian Schmidt
        //test for collision
        if (!interactionHandler.iskKeyPressed()) {
            cameraPos = objectCollision.testForWallCollision(cameraPos, cameraPosOld, j2);
            if (objectCollision.testForTrapCollison(j2)) {
                System.err.println("Trap Collision");
            }
            if (objectCollision.testForEndCollison(j2)) {
                System.err.println("End Collision");
                Main.g.endScreen();
            }
        }


        //Bela Korb, Sebastian Schmidt
        //Pruefen ob Kollision mit Falle vorliegt
        if (objectCollision.testForTrapCollison(j2)) {
            trapBoolean = true;
            rotateCharacter = false;
        }
        //von Bela Korb
        //Wenn Kollision mit einer Falle vorliegt, kann sich der Spieler nicht mehr bewegen und wird nach gewisser Zeit zurückgesetzt
        if (trapBoolean) {
            if (trapUpValue > 0.3) {
                cameraPos[0] = cameraPosOld[0];
                cameraPos[2] = cameraPosOld[2];
            }
            if (this.trapHold > 1) {

                trapUpValue = 0;
                trapHold = 0;

                firstFrame = true;
                trapBoolean = false;
            }

                //Bewegung der Falle nach oaben
                float[] trapUp = CustomMath.transmat((float) ImageProcessing.trapPoints.get(objectCollision.getCollidedTrapObjectNumber()).x, this.trapUpValue, (float) ImageProcessing.trapPoints.get(objectCollision.getCollidedTrapObjectNumber()).y);
                if (trapUpValue < 0.8) {
                    trapUpValue += 0.05;
                }
                //Weitergabe der Punkte an die Grafikkarte
                float[] trapRot = CustomMath.rotmat(0);
                trapVertices[objectCollision.getCollidedTrapObjectNumber()] = CustomMath.matMultVek(trapUp, trapRot, trapVerticesBegin[objectCollision.getCollidedTrapObjectNumber()]);


                gl.glBindVertexArray(vaoName[numberOfObjects - numberOfTorches - numberOfTraps + objectCollision.getCollidedTrapObjectNumber()]);
                gl.glBindBuffer(GL.GL_ELEMENT_ARRAY_BUFFER, iboName[numberOfObjects - numberOfTorches - numberOfTraps + objectCollision.getCollidedTrapObjectNumber()]);

                int[] cubeIndices = objImport.getTrapInd();

                // integers use 4 bytes in Java
                gl.glBufferData(GL.GL_ELEMENT_ARRAY_BUFFER, cubeIndices.length * 4,
                        IntBuffer.wrap(cubeIndices), GL.GL_DYNAMIC_DRAW);

                gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vboName[numberOfObjects - numberOfTorches - numberOfTraps + objectCollision.getCollidedTrapObjectNumber()]);
                gl.glBufferData(GL.GL_ARRAY_BUFFER, trapVertices[objectCollision.getCollidedTrapObjectNumber()].length * 4,
                        FloatBuffer.wrap(trapVertices[objectCollision.getCollidedTrapObjectNumber()]), GL.GL_DYNAMIC_DRAW);

                // Pointer for the vertex shader to the position information per vertex
                gl.glEnableVertexAttribArray(0);
                gl.glVertexAttribPointer(0, 3, GL.GL_FLOAT, false, 6 * 4, 0);
                // Pointer for the vertex shader to the normal information per vertex
                gl.glEnableVertexAttribArray(1);
                gl.glVertexAttribPointer(1, 3, GL.GL_FLOAT, false, 6 * 4, 3 * 4);

                trapHold += 0.008;
                //displayObject(gl, wallPoints.rows() + 3);
                // displayTrap(gl, numberOfObjects-numberOfTorches-numberOfTraps+i);


        }
        //ende Bela Korb


        //Sebastian Schmidt
        //Wenn sich der Spieler dreht, ohne sich zu bewegen, wird der Fuchs nicht mitgedreht
        float[] r;
        if (rotateCharacter) {
            r = CustomMath.rotmat(-interactionHandler.getAngleYaxis() - 180);
            oldYRotation = interactionHandler.getAngleYaxis();
        } else {
            r = CustomMath.rotmat(-oldYRotation - 180);
        }

        pmvMatrix.gluLookAt(0, 0.4f, 1.3f,
                0, 0.4f, 0,
                0, 1, 0);
        pmvMatrix.glRotatef(interactionHandler.getAngleXaxis(), 1f, 0f, 0f);
        pmvMatrix.glRotatef(interactionHandler.getAngleYaxis(), 0f, 1f, 0f);
        pmvMatrix.glTranslatef(cameraPos[0], 0, cameraPos[2]);
        // Transform for the complete scene


        //anzeigen der Objekte
        for (int i = 0; i < wallPoints.rows() + 2; i++) {

            pmvMatrix.glPushMatrix();
            displayObject(gl, i);
            pmvMatrix.glPopMatrix();

        }
        for (int i = numberOfObjects - numberOfTorches - numberOfTraps; i < numberOfObjects - numberOfTorches; i++) {
            pmvMatrix.glPushMatrix();
            displayTrap(gl, i);
            pmvMatrix.glPopMatrix();
        }
        for (int i = numberOfObjects - numberOfTorches; i < numberOfObjects; i++) {
            pmvMatrix.glPushMatrix();
            displayTorch(gl, i);
            pmvMatrix.glPopMatrix();
        }
        pmvMatrix.glPushMatrix();
        displayChest(gl, numberOfObjects - numberOfTorches - numberOfTraps - 1);
        pmvMatrix.glPopMatrix();
        //Bela Korb
        //Berechnung der Bewegungsmatrix der CharakterHitbox
        //Berechnen der neuen Vertices und Normalen
        float[] j;
        j = CustomMath.matrixmult(CustomMath.transmat(-cameraPos[0], -cameraPos[2]), r);
        for (int i = 0; i < 24; i++) {
            float[] vertices = new float[4];
            float[] verticestemp = new float[4];
            for (int k = 0; k < 3; k++) {
                vertices[k] = charakterbegin[i * 11 + k];
            }
            vertices[3] = 1f;
            for (int l = 0; l < 4; l++) {
                verticestemp[l] = j[l * 4] * vertices[0] + j[l * 4 + 1] * vertices[1] + j[l * 4 + 2] * vertices[2] + j[l * 4 + 3] * vertices[3];
            }
            for (int k = 0; k < 3; k++) {
                charakter[i * 11 + k] = verticestemp[k];
            }
            for (int k = 0; k < 3; k++) {
                vertices[k] = charakterbegin[i * 11 + k + 6];
            }
            vertices[3] = 1f;
            for (int l = 0; l < 4; l++) {
                verticestemp[l] = r[l * 4] * vertices[0] + r[l * 4 + 1] * vertices[1] + r[l * 4 + 2] * vertices[2] + r[l * 4 + 3] * vertices[3];
            }
            for (int k = 0; k < 3; k++) {
                charakter[i * 11 + k + 6] = verticestemp[k];
            }
        }
        //Anzeigen der Hitbox (nicht mehr aktuell, zu klein)
        /*
        gl.glBindVertexArray(vaoName[wallPoints.rows() + 2]);
        gl.glBindBuffer(GL.GL_ELEMENT_ARRAY_BUFFER, iboName[wallPoints.rows() + 2]);


        int[]    cubeIndices = Box.makeBoxIndicesForTriangleStrip();

        // integers use 4 bytes in Java
        gl.glBufferData(GL.GL_ELEMENT_ARRAY_BUFFER, cubeIndices.length * 4,
                IntBuffer.wrap(cubeIndices), GL.GL_DYNAMIC_DRAW);

        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vboName[wallPoints.rows() + 2]);
        gl.glBufferData(GL.GL_ARRAY_BUFFER, charakter.length * 4,
                FloatBuffer.wrap(charakter), GL.GL_DYNAMIC_DRAW);


        gl.glEnableVertexAttribArray(0);
        gl.glVertexAttribPointer(0, 3, GL.GL_FLOAT, false, 11 * 4, 0);
        // Pointer for the vertex shader to the color information per vertex
        gl.glEnableVertexAttribArray(1);
        gl.glVertexAttribPointer(1, 3, GL.GL_FLOAT, false, 11 * 4, 3 * 4);
        // Pointer for the vertex shader to the normal information per vertex
        gl.glEnableVertexAttribArray(2);
        gl.glVertexAttribPointer(2, 3, GL.GL_FLOAT, false, 11 * 4, 6 * 4);

        gl.glEnableVertexAttribArray(3);
        gl.glVertexAttribPointer(3, 2, GL.GL_FLOAT, false, 11 * 4, 9 * 4);

        displayObject(gl, wallPoints.rows() + 2);
        */

        //Furkan Calisir, Bela Korb
        //Andauernder Wechsel des Fuchsobjektes zur Animationssimulierung
        if (timerFox >= 0 && timerFox <= 4)
            charakterFox = CustomMath.matMultVek(j, r, charakterbeginFox[0]);
        else if (timerFox > 4 && timerFox <= 8)
            charakterFox = CustomMath.matMultVek(j, r, charakterbeginFox[1]);
        else if (timerFox > 8 && timerFox <= 12)
            charakterFox = CustomMath.matMultVek(j, r, charakterbeginFox[2]);
        else if (timerFox > 12 && timerFox <= 16)
            charakterFox = CustomMath.matMultVek(j, r, charakterbeginFox[3]);
        else if (timerFox > 16 && timerFox <= 20)
            charakterFox = CustomMath.matMultVek(j, r, charakterbeginFox[4]);
        else if (timerFox > 20 && timerFox <= 24)
            charakterFox = CustomMath.matMultVek(j, r, charakterbeginFox[5]);
        else if (timerFox > 24 && timerFox <= 28)
            charakterFox = CustomMath.matMultVek(j, r, charakterbeginFox[6]);
        else if (timerFox > 28 && timerFox <= 32)
            charakterFox = CustomMath.matMultVek(j, r, charakterbeginFox[7]);
        else if (timerFox > 32 && timerFox <= 36)
            charakterFox = CustomMath.matMultVek(j, r, charakterbeginFox[8]);
        else if (timerFox > 36 && timerFox <= 40)
            charakterFox = CustomMath.matMultVek(j, r, charakterbeginFox[9]);
        else if (timerFox > 40 && timerFox <= 44)
            charakterFox = CustomMath.matMultVek(j, r, charakterbeginFox[10]);
        else if (timerFox > 44 && timerFox <= 48)
            charakterFox = CustomMath.matMultVek(j, r, charakterbeginFox[11]);
        else if (timerFox > 48 && timerFox <= 52)
            charakterFox = CustomMath.matMultVek(j, r, charakterbeginFox[12]);
        else if (timerFox > 52 && timerFox <= 56)
            charakterFox = CustomMath.matMultVek(j, r, charakterbeginFox[13]);
        else if (timerFox > 56 && timerFox <= 60)
            charakterFox = CustomMath.matMultVek(j, r, charakterbeginFox[14]);
        else if (timerFox > 60 && timerFox <= 64)
            charakterFox = CustomMath.matMultVek(j, r, charakterbeginFox[15]);
        else if (timerFox > 64 && timerFox <= 68)
            charakterFox = CustomMath.matMultVek(j, r, charakterbeginFox[16]);
        else if (timerFox > 68 && timerFox <= 72)
            charakterFox = CustomMath.matMultVek(j, r, charakterbeginFox[17]);
        else if (timerFox > 72 && timerFox <= 76)
            charakterFox = CustomMath.matMultVek(j, r, charakterbeginFox[18]);
        else if (timerFox > 76 && timerFox <= 80)
            charakterFox = CustomMath.matMultVek(j, r, charakterbeginFox[19]);
        else if (timerFox > 80 && timerFox <= 84)
            charakterFox = CustomMath.matMultVek(j, r, charakterbeginFox[20]);
        else if (timerFox > 84 && timerFox <= 88)
            charakterFox = CustomMath.matMultVek(j, r, charakterbeginFox[21]);
        else if (timerFox > 88 && timerFox <= 92)
            charakterFox = CustomMath.matMultVek(j, r, charakterbeginFox[22]);
        else if (timerFox > 92 && timerFox <= 96)
            charakterFox = CustomMath.matMultVek(j, r, charakterbeginFox[23]);
        else if (timerFox > 96 && timerFox <= 100)
            charakterFox = CustomMath.matMultVek(j, r, charakterbeginFox[24]);
        else if (timerFox > 100 && timerFox <= 104)
            charakterFox = CustomMath.matMultVek(j, r, charakterbeginFox[25]);
        else if (timerFox > 104 && timerFox <= 108)
            charakterFox = CustomMath.matMultVek(j, r, charakterbeginFox[26]);
        else if (timerFox > 108 && timerFox <= 112)
            charakterFox = CustomMath.matMultVek(j, r, charakterbeginFox[27]);
        else if (timerFox > 112 && timerFox <= 116)
            charakterFox = CustomMath.matMultVek(j, r, charakterbeginFox[28]);
        else if (timerFox > 116 && timerFox <= 120)
            charakterFox = CustomMath.matMultVek(j, r, charakterbeginFox[29]);
        else if (timerFox > 120 && timerFox <= 124)
            charakterFox = CustomMath.matMultVek(j, r, charakterbeginFox[30]);
        else if (timerFox > 124 && timerFox <= 128)
            charakterFox = CustomMath.matMultVek(j, r, charakterbeginFox[31]);
        //else if(timerFox > 96 && timerFox <= 97)
        //    charakterFox=CustomMath.matMultVek(j,r,charakterbeginFox[32]);
        timerFox++;
        if (timerFox > 128)
            timerFox = 0;


        //Weitergabe der Punkte an die GPU
        gl.glBindVertexArray(vaoName[wallPoints.rows() + 3]);
        gl.glBindBuffer(GL.GL_ELEMENT_ARRAY_BUFFER, iboName[wallPoints.rows() + 3]);

        int[] cubeIndices = foxIndices;

        // integers use 4 bytes in Java
        gl.glBufferData(GL.GL_ELEMENT_ARRAY_BUFFER, cubeIndices.length * 4,
                IntBuffer.wrap(cubeIndices), GL.GL_DYNAMIC_DRAW);

        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vboName[wallPoints.rows() + 3]);
        gl.glBufferData(GL.GL_ARRAY_BUFFER, charakterFox.length * 4,
                FloatBuffer.wrap(charakterFox), GL.GL_DYNAMIC_DRAW);

        // Pointer for the vertex shader to the position information per vertex
        gl.glEnableVertexAttribArray(0);
        gl.glVertexAttribPointer(0, 3, GL.GL_FLOAT, false, 6 * 4, 0);
        // Pointer for the vertex shader to the normal information per vertex
        gl.glEnableVertexAttribArray(1);
        gl.glVertexAttribPointer(1, 3, GL.GL_FLOAT, false, 6 * 4, 3 * 4);

        pmvMatrix.glPushMatrix();
        displayObject(gl, wallPoints.rows() + 3);
        pmvMatrix.glPopMatrix();

        //Michelle Wetscheck
        //Vergangene Zeit wird berechnet und angezeigt
        timer();

    }


    /**
     * bearbeitet von: Bela Korb, Sebastian Schmidt
     * <br>
     * Zum Anzeigen von Waenden, Boden, Decke und Fuchs
     * @param gl
     * @param i Objektindex
     */
    private void displayObject(GL3 gl, int i) {
        if (i == wallPoints.rows() + 3) {
            gl.glUseProgram(shaderProgram1.getShaderProgramID());
        } else {
            gl.glUseProgram(shaderProgram2.getShaderProgramID());
        }

        // Transfer the PVM-Matrix (model-view and projection matrix) to the vertex shader
        gl.glUniformMatrix4fv(0, 1, false, pmvMatrix.glGetPMatrixf());
        gl.glUniformMatrix4fv(1, 1, false, pmvMatrix.glGetMvMatrixf());
        // transfer parameters of light source
        //Weitergabe Licht, Material, Textureigenschafte
        for (int j = 0; j < numberOfLights; j++) {
            gl.glUniform4fv(j + 14, 1, lightPoints[randomLightIndexes[j]], 0);
            gl.glUniform1f(j + 25, intensitys[j]);

        }
        gl.glUniform4fv(3, 1, light0.getAmbient(), 0);
        gl.glUniform4fv(4, 1, light0.getDiffuse(), 0);
        gl.glUniform4fv(5, 1, light0.getSpecular(), 0);

        // gl.glUniform1f(2, light0.getIntesity());
        // transfer material parameters
        gl.glUniform1f(24, lightflickering);
        gl.glUniform4fv(6, 1, material0.getAmbient(), 0);
        gl.glUniform4fv(7, 1, material0.getDiffuse(), 0);
        gl.glUniform4fv(8, 1, material0.getSpecular(), 0);
        gl.glUniform1f(9, material0.getShininess());


        gl.glUniform4fv(10, 1, material1.getAmbient(), 0);
        gl.glUniform4fv(11, 1, material1.getDiffuse(), 0);
        gl.glUniform4fv(12, 1, material1.getSpecular(), 0);
        gl.glUniform1f(13, material1.getShininess());


        gl.glBindVertexArray(vaoName[i]);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textureIndex[i]);

        //gl.glUniform1i(12, 0);

        // Draws the elements in the order defined by the index buffer object (IBO)
        if (i == wallPoints.rows() + 3) {
            gl.glDrawElements(GL.GL_TRIANGLES, foxIndices.length, GL.GL_UNSIGNED_INT, 0);
        } else {
            gl.glDrawElements(GL.GL_TRIANGLE_STRIP, Box.noOfIndicesForBox(), GL.GL_UNSIGNED_INT, 0);
        }
    }

    /**
     * Autor: Michelle Wetscheck
     * Erweitert durch: Sebastian Schmidt
     * <br><b>
     * Der Time greift auf die aktuelle Zeit des Computer zu
     * <br>
     * Sobald das Spiel gestartet wird, fängt der Timer bei 0 an
     * und zeigt die benötigte Zeit zum lösen des Labyrinths an
     * <br>
     * Wenn das Ziel erreicht wurde oder Pause gedrückt wird, stoppt er wieder
     */

    public void timer() {
        //von Michelle Wetscheck
        long millisec = 0;
        long sec = 0;
        long minutes = 0;
        long hours = 0;

        if (oldTime != 0) {
            currentTime = System.currentTimeMillis();
            if (interactionHandler.isEscKeyPressed()) {
                oldTime = System.currentTimeMillis();
            }

            runningTime += currentTime - oldTime;


            millisec = runningTime;
            millisec = millisec % 1000;

            sec = runningTime / 1000;
            sec = sec % 60;


            minutes = runningTime / 60000;
            minutes = minutes % 60;

            hours = runningTime / 3600000;
            hours = hours % 60;


        }
        oldTime = System.currentTimeMillis();
        String hoursNew = "" + hours;
        String minutesNew = "" + minutes;
        String secNew = "" + sec;
        String millisecNew = "" + millisec;
        //ende Michelle Wetscheck
        //von Sebastian Schmidt
        if (hours < 10) {
            hoursNew = "0" + hours;
        }
        if (minutes < 10) {
            minutesNew = "0" + minutes;
        }
        if (sec < 10) {
            secNew = "0" + sec;
        }
        if (millisec < 10) {
            millisecNew = "00" + millisec;
        } else if (millisec < 100) {
            millisecNew = "0" + millisec;
        }
        time = "Zeit:" + hoursNew + ":" + minutesNew + ":" + secNew + ":" + millisecNew;
        GameWindow.countdown.setText(time);
        //ende Sebastian Schmidt
    }

    /**
     * bearbeitet Bela Korb
     *
     * Zum Anzeigen der Fackel
     * @param gl
     * @param i Objektindex
     */
    private void displayTorch(GL3 gl, int i) {

        gl.glUseProgram(shaderProgram1.getShaderProgramID());


        // Transfer the PVM-Matrix (model-view and projection matrix) to the vertex shader
        //Weiterhabe Licht, Material Eigenschaften
        gl.glUniformMatrix4fv(0, 1, false, pmvMatrix.glGetPMatrixf());
        gl.glUniformMatrix4fv(1, 1, false, pmvMatrix.glGetMvMatrixf());
        // transfer parameters of light source
        for (int j = 0; j < numberOfLights; j++) {
            gl.glUniform4fv(j + 14, 1, lightPoints[randomLightIndexes[j]], 0);
            gl.glUniform1f(j + 25, intensitys[j]);
        }
        gl.glUniform4fv(3, 1, light0.getAmbient(), 0);
        gl.glUniform4fv(4, 1, light0.getDiffuse(), 0);
        gl.glUniform4fv(5, 1, light0.getSpecular(), 0);

        // transfer material parameters
        gl.glUniform1f(24, lightflickering);


        gl.glUniform4fv(10, 1, material2.getAmbient(), 0);
        gl.glUniform4fv(11, 1, material2.getDiffuse(), 0);
        gl.glUniform4fv(12, 1, material2.getSpecular(), 0);
        gl.glUniform1f(13, material2.getShininess());


        gl.glBindVertexArray(vaoName[i]);

        // Draws the elements in the order defined by the index buffer object (IBO)

        gl.glDrawElements(GL.GL_TRIANGLES, objImport.getTorchInd().length, GL.GL_UNSIGNED_INT, 0);

    }

    /**
     * bearbeitet von Bela Korb
     * <br>
     * Zum Anzeigen der Falle
     * @param gl
     * @param i Objektindex
     */
    private void displayTrap(GL3 gl, int i) {

        gl.glUseProgram(shaderProgram1.getShaderProgramID());


        // Transfer the PVM-Matrix (model-view and projection matrix) to the vertex shader
        gl.glUniformMatrix4fv(0, 1, false, pmvMatrix.glGetPMatrixf());
        gl.glUniformMatrix4fv(1, 1, false, pmvMatrix.glGetMvMatrixf());
        // transfer parameters of light source
        //Weitergabe von Licht, Materialeigenschaften
        for (int j = 0; j < numberOfLights; j++) {
            gl.glUniform4fv(j + 14, 1, lightPoints[randomLightIndexes[j]], 0);
            gl.glUniform1f(j + 25, intensitys[j]);

        }
        gl.glUniform4fv(3, 1, light0.getAmbient(), 0);
        gl.glUniform4fv(4, 1, light0.getDiffuse(), 0);
        gl.glUniform4fv(5, 1, light0.getSpecular(), 0);

        // transfer material parameters
        gl.glUniform1f(24, lightflickering);


        gl.glUniform4fv(10, 1, material2.getAmbient(), 0);
        gl.glUniform4fv(11, 1, material2.getDiffuse(), 0);
        gl.glUniform4fv(12, 1, material2.getSpecular(), 0);
        gl.glUniform1f(13, material2.getShininess());


        gl.glBindVertexArray(vaoName[i]);


        // Draws the elements in the order defined by the index buffer object (IBO)

        gl.glDrawElements(GL.GL_TRIANGLES, objImport.getTrapInd().length, GL.GL_UNSIGNED_INT, 0);

    }

    /**
     * bearbeitet von Bela Korb
     *<br>
     * Zum Anzeigen von der Kiste
     *
     * @param gl
     * @param i
     */
    private void displayChest(GL3 gl, int i) {

        gl.glUseProgram(shaderProgram3.getShaderProgramID());


        // Transfer the PVM-Matrix (model-view and projection matrix) to the vertex shader
        gl.glUniformMatrix4fv(0, 1, false, pmvMatrix.glGetPMatrixf());
        gl.glUniformMatrix4fv(1, 1, false, pmvMatrix.glGetMvMatrixf());
        // transfer parameters of light source
        for (int j = 0; j < numberOfLights; j++) {
            gl.glUniform4fv(j + 14, 1, lightPoints[randomLightIndexes[j]], 0);
            gl.glUniform1f(j + 25, intensitys[j]);
        }
        gl.glUniform4fv(3, 1, light0.getAmbient(), 0);
        gl.glUniform4fv(4, 1, light0.getDiffuse(), 0);
        gl.glUniform4fv(5, 1, light0.getSpecular(), 0);


        // transfer material parameters
        gl.glUniform1f(24, lightflickering);


        gl.glUniform4fv(10, 1, material3.getAmbient(), 0);
        gl.glUniform4fv(11, 1, material3.getDiffuse(), 0);
        gl.glUniform4fv(12, 1, material3.getSpecular(), 0);
        gl.glUniform1f(13, material3.getShininess());


        gl.glBindVertexArray(vaoName[i]);


        //gl.glUniform1i(12, 0);

        // Draws the elements in the order defined by the index buffer object (IBO)

        gl.glDrawElements(GL.GL_TRIANGLES, objImport.getChestInd().length, GL.GL_UNSIGNED_INT, 0);

    }

    /**
     * Implementation of the OpenGL EventListener (GLEventListener) method
     * called when the OpenGL window is resized.
     * @param drawable The OpenGL drawable
     * @param x
     * @param y
     * @param width
     * @param height
     */
    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL3 gl = drawable.getGL().getGL3();

        pmvMatrix.glMatrixMode(PMVMatrix.GL_PROJECTION);
        pmvMatrix.glLoadIdentity();
        pmvMatrix.gluPerspective(45f, (float) width / (float) height, 0.1f, 1000f);
    }

    /**
     * Implementation of the OpenGL EventListener (GLEventListener) method
     * called when OpenGL canvas ist destroyed.
     * @param drawable
     */
    @Override
    public void dispose(GLAutoDrawable drawable) {
        System.out.println("Deleting allocated objects, incl. shader program.");
        GL3 gl = drawable.getGL().getGL3();

        // Detach and delete shader program
        gl.glUseProgram(0);
        shaderProgram1.deleteShaderProgram();
        shaderProgram2.deleteShaderProgram();

        // deactivate VAO and VBO
        gl.glBindVertexArray(0);
        gl.glDisableVertexAttribArray(0);
        gl.glDisableVertexAttribArray(1);

        gl.glDisable(GL.GL_CULL_FACE);
        gl.glDisable(GL.GL_DEPTH_TEST);

        System.exit(0);
    }
    //ohne Sinn, war zum Debuggen
    public static void setLightPosition(int i, float lightPosition) {
        for (int j = 0; j < numberOfTorches; j++) {
            switch (i) {
                case 0:

                    ShapesRendererPP.lightPosition[i][0] = lightPosition;
                    break;
                case 1:
                    ShapesRendererPP.lightPosition[i][1] = lightPosition;
                    break;
                case 2:
                    ShapesRendererPP.lightPosition[i][2] = lightPosition;
                    break;
            }
        }
    }


    public GL3 getGll() {
        return gl;
    }

    public void setGll(GL3 gll) {
        this.gl = gll;
    }
}
