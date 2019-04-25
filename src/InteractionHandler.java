import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Set;

public class InteractionHandler implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener{

    private float angleXaxis = 0f;
    private float angleYaxis = 0f;
    private float angleXaxisInc = 1f;
    private float angleYaxisInc = 1f;

    private boolean leftMouseButtonPressed = false;
    private boolean rightMouseButtonPressed = false;
    private Point lastMouseLocation = new Point(Toolkit.getDefaultToolkit().getScreenSize().width/2, Toolkit.getDefaultToolkit().getScreenSize().height/2);
    // Taking care of the screen size (mapping of mouse coordinates to angle/translation)
    private final float mouseRotationFactor = 0.03f;
    private final float mouseTranslationFactor = 0.1f;
    private final float mouseWheelScrollFactor = 10f;
    private float cameraSpeed = 0.1f;

    private boolean wKeyPressed = false;
    private boolean aKeyPressed = false;
    private boolean sKeyPressed = false;
    private boolean dKeyPressed = false;
    private static boolean escKeyPressed = false;
    private boolean kKeyPressed = false;


    /**
     * Standard constructor for creation of the interaction handler.
     */
    public InteractionHandler() {
        escKeyPressed= false;
    }

    public float getAngleXaxis() {
        return angleXaxis;
    }

    public float getAngleYaxis() {
        return angleYaxis;
    }

    public void setAngleYaxis(float angleYaxis) {
        this.angleYaxis = angleYaxis;
    }

    private final Set<Character> pressed = new HashSet<Character>();

    /**
     * Autor Sebastian Schmidt
     * Erkennt Tastendrücke und reagiert dementsprechend
     */
    @Override
    public synchronized void keyPressed(KeyEvent e) {
        pressed.add(e.getKeyChar());
        if (pressed.size() > 1) {
            // More than one key is currently pressed.
            // Iterate over pressed to get the keys.
            pressed.forEach((a) -> {
                System.out.println("Pressed: " + a);
                switch (a){
                    case 'w':
                        wKeyPressed = true;
                        break;
                    case 's':
                        sKeyPressed = true;
                        break;
                    case 'a':
                        aKeyPressed = true;
                        break;
                    case 'd':
                        dKeyPressed = true;
                        break;
                    case KeyEvent.VK_ESCAPE:
                        if(escKeyPressed){
                            escKeyPressed = false;
                            Main.g.disposeIngameMenu();
                            Main.g.canvas.requestFocus();
                        }else{
                            escKeyPressed = true;
                            Main.g.displayIngameMenu();
                        }
                        break;
                    case 'k':
                        if(kKeyPressed){
                            kKeyPressed = false;
                        }else{
                            kKeyPressed = true;
                        }
                        break;
                }
            });
        }else{
            switch (e.getKeyCode()){
                case KeyEvent.VK_W:
                    wKeyPressed = true;
                    break;
                case KeyEvent.VK_S:
                    sKeyPressed = true;
                    break;
                case KeyEvent.VK_A:
                    aKeyPressed = true;
                    break;
                case KeyEvent.VK_D:
                    dKeyPressed = true;
                    break;
                case KeyEvent.VK_ESCAPE:
                    if(escKeyPressed){
                        escKeyPressed = false;
                        Main.g.disposeIngameMenu();
                        Main.g.canvas.requestFocus();
                    }else{
                        escKeyPressed = true;
                        Main.g.displayIngameMenu();
                    }
                    break;
                case KeyEvent.VK_K:
                    if(kKeyPressed){
                        kKeyPressed = false;

                    }else{
                        kKeyPressed = true;

                    }
                    break;
            }
        }
    }


    /**
     * Autor Sebastian Schmidt
     * Erkennt losgelassene Tasten
     * @param e KeyEvent
     */
    @Override
    public synchronized void keyReleased(KeyEvent e) {

        pressed.remove(e.getKeyChar());
        switch(e.getKeyChar()){
            case 'w':
                wKeyPressed =false;
                break;
            case 'a':
                aKeyPressed =false;
                break;
            case 's':
                sKeyPressed =false;
                break;
            case 'd':
                dKeyPressed =false;
                break;
        }
    }


    @Override
    /**
     * Implements one method of the interface KeyListener
     */
    public void keyTyped(KeyEvent e) { }


    @Override
    /**
     * Implements one method of the interface MouseListener
     */
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    /**
     * Implements one method of the interface MouseListener
     */
    public void mousePressed(MouseEvent e) {
        int pressedButton = e.getButton();
        switch (pressedButton) {
            case MouseEvent.BUTTON1:
                leftMouseButtonPressed = true;
                break;
            case MouseEvent.BUTTON3:
                rightMouseButtonPressed = true;
                break;
        }
    }

    @Override
    /**
     * Implements one method of the interface MouseListener
     */
    public void mouseReleased(MouseEvent e) {
        int releasedButton = e.getButton();

        switch (releasedButton) {
            case MouseEvent.BUTTON1:
                leftMouseButtonPressed = false;
                break;
            case MouseEvent.BUTTON3:
                rightMouseButtonPressed = false;
                break;
        }
    }

    @Override
    /**
     * Implements one method of the interface MouseListener
     */
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    /**
     * Implements one method of the interface MouseListener
     */
    public void mouseExited(MouseEvent e) {
    }

    /**Autor Sebastian Schmidt
     * Berechnet die relative Mausbewegung und setzt sie zurück in die Mitte des Bildschirms
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        if(!escKeyPressed) {
            Point currentMouseLocation = e.getLocationOnScreen();
            double deltaX = currentMouseLocation.getX() - lastMouseLocation.getX();
            double deltaY = currentMouseLocation.getY() - lastMouseLocation.getY();

            System.out.println("deltaX= " + deltaX + " deltaY= " + deltaY);
            angleYaxis += angleYaxisInc * mouseRotationFactor * deltaX;
            angleXaxis += angleXaxisInc * mouseRotationFactor * deltaY;
            if (angleXaxis > 77) {
                angleXaxis = 77;
            } else if (angleXaxis < -13) {
                angleXaxis = -13;
            }
            try {
                Robot r = new Robot();
                r.mouseMove(Toolkit.getDefaultToolkit().getScreenSize().width/2, Toolkit.getDefaultToolkit().getScreenSize().height/2);
            } catch (AWTException a) {
                a.printStackTrace();
            }
        }
    }

    /**Autor Sebastian Schmidt
     * Berechnet die relative Mausbewegung und setzt sie zurück in die Mitte des Bildschirms
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        if(!escKeyPressed) {
            Point currentMouseLocation = e.getLocationOnScreen();
            double deltaX = currentMouseLocation.getX() - lastMouseLocation.getX();
            double deltaY = currentMouseLocation.getY() - lastMouseLocation.getY();
            angleYaxis += angleYaxisInc * mouseRotationFactor * deltaX;
            angleXaxis += angleXaxisInc * mouseRotationFactor * deltaY;
           // System.out.println("angleXAxis: " + angleXaxis);
            if (angleXaxis > 77) {
                angleXaxis = 77;
            } else if (angleXaxis < -13) {
                angleXaxis = -13;
            }
            try {
                Robot r = new Robot();
                r.mouseMove(Toolkit.getDefaultToolkit().getScreenSize().width/2, Toolkit.getDefaultToolkit().getScreenSize().height/2);
                
            } catch (AWTException a) {
                a.printStackTrace();
            }
        }
    }

    /**
     * Implements one method of the interface MouseMWheelMovedListener
     */
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

    }

    public boolean iswKeyPressed() {
        return wKeyPressed;
    }

    public boolean isaKeyPressed() {
        return aKeyPressed;
    }

    public boolean issKeyPressed() {
        return sKeyPressed;
    }

    public boolean isdKeyPressed() {
        return dKeyPressed;
    }

    public boolean iskKeyPressed() {
        return kKeyPressed;
    }

    public boolean isEscKeyPressed() {
        return escKeyPressed;
    }
    public static void setIsEscKeyPressed(boolean pressed){
        escKeyPressed = pressed;
    }
}
