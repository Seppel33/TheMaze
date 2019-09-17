import java.nio.ByteBuffer;

import static com.jogamp.opengl.GL.*;
import static com.jogamp.opengl.GL.GL_NEAREST;
import static com.jogamp.opengl.GL.GL_REPEAT;
import static com.jogamp.opengl.GL.GL_RGB;
import static com.jogamp.opengl.GL.GL_TEXTURE_2D;
import static com.jogamp.opengl.GL.GL_TEXTURE_MAG_FILTER;
import static com.jogamp.opengl.GL.GL_TEXTURE_MIN_FILTER;
import static com.jogamp.opengl.GL.GL_TEXTURE_WRAP_S;
import static com.jogamp.opengl.GL.GL_TEXTURE_WRAP_T;
import static com.jogamp.opengl.GL.GL_UNPACK_ALIGNMENT;
import static com.jogamp.opengl.GL.GL_UNSIGNED_BYTE;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL3;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;


public class TextureLoader {
    private static ByteBuffer[] buffer = new ByteBuffer[3];
    private static String[] filePathName= new String[3];
    private static Mat[] imageMat = new Mat[3];


    public TextureLoader(GL3 gl, int texName, int tex) {
        textureLoader(gl, texName, tex);
    }


    /**
     * Autor: Bela Korb
     *
     * Die verschiedenen Texturen werden in Buffer eingeladen
     */
    public static void textureBufferLoader() {

        filePathName[0] = "res\\wall2.jpg";
        filePathName[1] = "res\\roof.jpg";
        filePathName[2] = "res\\steinboden564.jpg";
        for (int i = 0;i<3;i++){
            imageMat[i] = Imgcodecs.imread(filePathName[i]);
        }
        int index = 0;
        for (int k = 0; k < 3; k++) {
            buffer[k] = ByteBuffer.allocateDirect(3 * imageMat[k].cols() * imageMat[k].rows());
            for (int i = 0; i < imageMat[k].rows(); i++) {
                for (int j = 0; j < imageMat[k].cols(); j++) {
                    for (int l = 0; l < 3; l++) {
                        buffer[k].put(index++, (byte) imageMat[k].get(i, j)[l]);
                    }
                }
            }
            index = 0;
        }
    }
    public static void deleteBuffers() {

        for (int i = 0; i < 3; i++) {
            buffer[i] = null;
        }
    }

    /**
     * Autor: Michelle Wetscheck, Bela Korb
     * angelehnt an: https://learnopengl.com/Getting-started/Textures
     * @param gl
     * @param texObjektnumber
     * @param tex
     * <b>
     * Die Methode beschreibt, wie die Texturen an das Objekt gebunden werden sollen,
     * bzw. wie sie es "umwicklen" sollen
     * <b>
     * Es wird auch angegeben wie sich die Textur wiederholen soll
     * <b>
     */

    public void textureLoader(GL3 gl, int texObjektnumber, int tex) {
        /*Alter Ansatz für den Buffer:
        Create Buffer with capacity of BGR and width/height
        ByteBuffer buffer = ByteBuffer.allocateDirect(3 * Image.getWidth() * Image.getHeight());

        Create a textures / textures ID
        int textureID = glGenTextures();
        gl.glActiveTexture(GL_TEXTURE0);*/
        gl.glBindTexture(GL_TEXTURE_2D, texObjektnumber);




        gl.glGenerateMipmap(GL_TEXTURE_2D);
        gl.glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

        //Textur wird wiederholt wenn Texturkoordinaten groesser als 1 sind
        gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

        //Naher Texturen werden leicht weichgezeichnet, man sieht keine vereinzelten
        gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);


        //Die jeweilige Textur wird mit der richtigen groesse and die Grafikkarte weitergegeben

        gl.glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, imageMat[tex].cols(), imageMat[tex].rows(), 0, GL_BGR, GL_UNSIGNED_BYTE, buffer[tex]);


    }

}

