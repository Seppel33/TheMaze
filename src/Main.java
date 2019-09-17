
import org.opencv.core.Core;

/**
 * Autor: Sebastian Schmidt
 * <br><br>
 * Es werden Vier Klassenübergreifende Static Variablen erstellt
 * <br>
 * Das Hauptmenue-Fenster wird erstellt
 */

public class Main {
    public static MenuWindow m;
    public static GameWindow g;
    public static int loadProgress = 0;
    public static String versionString = "Version " + "0.02.84";

    public static void main(String[] args) {
        // Load OpenCV libraries and start program
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        m = new MenuWindow();

    }

}
