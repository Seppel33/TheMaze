import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;


import javax.imageio.ImageIO;
import javax.swing.*;

import com.jogamp.nativewindow.WindowClosingProtocol;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

public class GameWindow extends JFrame{
    private static final long serialVersionUID = 1L;
    // Define constants for the top-level container
    private static String TITLE = "Shapes start Code Main Window - Programmable Pipeline";
    private static final int FPS = 60; // animator's target frames per second
    public static JLabel countdown;
    public GLCanvas canvas;
    private final FPSAnimator animator;
    private JButton backToMenu;
    private JButton backToGame;
    private JButton showManual;
    private JLabel manualLabel;
    private Cursor blankCursor;
    private GLCapabilities capabilities;
    private JPanel endscreen;
    private Timer animation = new Timer();
    private int timerCount=0;
    private JLabel wonSmall;
    private JLabel wonMiddle;
    private JLabel wonBig;

    /**
     * Autor: Sebastian Schmidt
     * Spiel-UI regelt das Aussehen und den Verlauf des UI
     * 
     */
    public GameWindow() {
        manualLabel = new JLabel();

        manualLabel.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width/2-400,Toolkit.getDefaultToolkit().getScreenSize().height/2-500, 800, 1000);
        manualLabel.setBorder(null);
        manualLabel.setVisible(false);
        File folderInput = new File("res\\Anleitung_Maze.jpg");
        BufferedImage folderImage;
        try {
            folderImage = ImageIO.read(folderInput);
            manualLabel.setIcon(new ImageIcon(folderImage));
        }catch (Exception e){
            System.err.println("Error- Could'nt read ManualImage");
        }
        //manualLabel.setIcon(new ImageIcon("res\\Anleitung_Maze_new.jpg"));
        manualLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                manualLabel.setVisible(false);
                displayIngameMenu();
                canvas.requestFocus();
                repaint();
            }

            @Override
            public void mouseEntered(MouseEvent arg0) {
                Main.g.getContentPane().setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                Main.g.getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        });
        this.getContentPane().add(manualLabel);

        countdown = new JLabel();
        countdown.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 150, 0, 300, 50);
        countdown.setOpaque(true);
        countdown.setHorizontalAlignment(JLabel.CENTER);
        countdown.setFont(new Font("Arial", Font.PLAIN, 32));
        countdown.setBackground(new Color(140,0,0));
        countdown.setForeground(new Color(255, 255, 255));
        countdown.setText("");
        this.getContentPane().add(countdown);

        initializeEndscreen();

        backToMenu = new JButton("Zurück zum Hauptmenü");
        backToMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
        backToMenu.setBounds( Toolkit.getDefaultToolkit().getScreenSize().width/2-100,Toolkit.getDefaultToolkit().getScreenSize().height/2+75, 200, 50);
        backToMenu.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        backToMenu.setBorder(null);
        backToMenu.setOpaque(true);
        backToMenu.setBackground(new Color(196,62,0));
        backToMenu.setForeground(new Color(255,255,255));
        backToMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                Main.m.setVisible(true);
                Main.m.requestFocusInWindow();
                if (animator.isStarted()) animator.stop();
                Main.m.repaint();
                Main.g.setVisible(false);
                repaint();
            }

            @Override
            public void mouseEntered(MouseEvent arg0) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        this.getContentPane().add(backToMenu);
        backToMenu.setVisible(false);

        backToGame = new JButton("Zurück zum Spiel");
        backToGame.setAlignmentX(Component.CENTER_ALIGNMENT);
        backToGame.setBounds( Toolkit.getDefaultToolkit().getScreenSize().width/2-100,Toolkit.getDefaultToolkit().getScreenSize().height/2-125, 200, 50);
        backToGame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        backToGame.setBorder(null);
        backToGame.setOpaque(true);
        backToGame.setBackground(new Color(196,62,0));
        backToGame.setForeground(new Color(255,255,255));
        backToGame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                disposeIngameMenu();
                InteractionHandler.setIsEscKeyPressed(false);
            }

            @Override
            public void mouseEntered(MouseEvent arg0) {
                //Main.g.getContentPane().setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                //Main.g.getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        });
        this.getContentPane().add(backToGame);
        backToGame.setVisible(false);

        showManual = new JButton("Spielanleitung");
        showManual.setAlignmentX(Component.CENTER_ALIGNMENT);
        showManual.setBounds( Toolkit.getDefaultToolkit().getScreenSize().width/2-100,Toolkit.getDefaultToolkit().getScreenSize().height/2-25, 200, 50);
        showManual.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        showManual.setBorder(null);
        showManual.setOpaque(true);
        showManual.setBackground(new Color(196,62,0));
        showManual.setForeground(new Color(255,255,255));
        showManual.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                backToMenu.setVisible(false);
                showManual.setVisible(false);
                backToGame.setVisible(false);
                manualLabel.setVisible(true);
                repaint();
            }

            @Override
            public void mouseEntered(MouseEvent arg0) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        this.getContentPane().add(showManual);
        showManual.setVisible(false);

        // Setup an OpenGL context for the Canvas
        // Setup OpenGL to use the programmable pipeline

        // Setting to OpenGL 3
        GLProfile profile = GLProfile.get(GLProfile.GL3);
        capabilities = new GLCapabilities(profile);
        capabilities.setSampleBuffers(true);
        capabilities.setNumSamples(16);
        // Create the OpenGL rendering canvas
        this.setUndecorated(true);
        this.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));

        // Transparent 16 x 16 pixel cursor image.
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        // Create a new blank cursor.
        blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
        // Set the blank cursor to the JFrame.

        this.getContentPane().setCursor(blankCursor);

        canvas = new ShapesRendererPP(capabilities);
        canvas.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
        canvas.setDefaultCloseOperation(WindowClosingProtocol.WindowClosingMode.DO_NOTHING_ON_CLOSE);
        // Create an animator that drives the canvas display() at the specified
        // frame rate.
        animator = new FPSAnimator(canvas, FPS, true);

        // Create the top-level container frame
        //this.getContentPane().add(canvas);
        this.pack();
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setVisible(true);
        repaint();
        animator.start(); // start the animation loop	// TODO Auto-generated constructor stub
        // OpenGL: request focus for canvas
        canvas.requestFocusInWindow();
    }
    public void displayIngameMenu(){
        this.getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        backToMenu.setVisible(true);
        showManual.setVisible(true);
        backToGame.setVisible(true);
        this.getContentPane().revalidate();
        this.getContentPane().repaint();
    }
    public void disposeIngameMenu(){
        // Transparent 16 x 16 pixel cursor image.
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        // Create a new blank cursor.
        blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
        // Set the blank cursor to the JFrame.

        Main.g.canvas.requestFocus();
        backToMenu.setVisible(false);
        showManual.setVisible(false);
        backToGame.setVisible(false);
        manualLabel.setVisible(false);
        this.getContentPane().setCursor(blankCursor);
        this.getContentPane().revalidate();
        this.getContentPane().repaint();
    }
    public void restartCanvas(){
        canvas = new ShapesRendererPP(capabilities);
        animator.start();
        canvas.requestFocusInWindow();
        this.repaint();
    }
    public void endScreen(){
        if(animator.isStarted()) animator.stop();
        countdown.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 150, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - 25, 300, 50);
        this.getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        canvas.setVisible(false);
        endscreen.setVisible(true);
        endscreen.requestFocus();
        this.getContentPane().revalidate();
        this.getContentPane().repaint();

        animation.scheduleAtFixedRate(winAnimation, 0, 150);
        /*
        try {
            Robot robot = new Robot();
            // Simulate a key press
            robot.keyPress(KeyEvent.VK_ALT);
            robot.keyRelease(KeyEvent.VK_ALT);

        } catch (AWTException e) {
            e.printStackTrace();
        }
        */

    }

    private void initializeEndscreen(){
        endscreen = new JPanel();
        endscreen.isOptimizedDrawingEnabled();
        endscreen.setSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
        endscreen.setLayout(null);
        wonSmall = new JLabel("GEWONNEN");
        wonSmall.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width/2-300, 100, 600, 100);
        wonSmall.setBorder(null);
        wonSmall.setHorizontalAlignment(JLabel.CENTER);
        wonSmall.setFont(new Font("Helvetica", Font.BOLD, 50));
        wonSmall.setForeground(new Color(255,171,0));
        wonSmall.setVisible(false);

        wonMiddle = new JLabel("GEWONNEN");
        wonMiddle.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width/2-300, 100, 600, 100);
        wonMiddle.setBorder(null);
        wonMiddle.setHorizontalAlignment(JLabel.CENTER);
        wonMiddle.setFont(new Font("Helvetica", Font.BOLD, 70));
        wonMiddle.setForeground(new Color(255,196,0));
        wonMiddle.setVisible(false);

        wonBig = new JLabel("GEWONNEN");
        wonBig.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width/2-300, 100, 600, 100);
        wonBig.setBorder(null);
        wonBig.setHorizontalAlignment(JLabel.CENTER);
        wonBig.setFont(new Font("Helvetica", Font.BOLD, 90));
        wonBig.setForeground(new Color(255,215,64));
        wonBig.setVisible(false);

        JLabel text = new JLabel("Deine benötigte Zeit:");
        text.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width/2-150, Toolkit.getDefaultToolkit().getScreenSize().height/2-125, 300, 50);
        text.setBorder(null);
        text.setHorizontalAlignment(JLabel.CENTER);
        text.setFont(new Font("Helvetica", Font.PLAIN, 20));
        text.setForeground(new Color(255,171,0));

        backToMenu = new JButton("Zurück zum Hauptmenü");
        backToMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
        backToMenu.setBounds( Toolkit.getDefaultToolkit().getScreenSize().width/2-100,Toolkit.getDefaultToolkit().getScreenSize().height/2+75, 200, 50);
        backToMenu.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        backToMenu.setBorder(null);
        backToMenu.setOpaque(true);
        backToMenu.setBackground(new Color(196,62,0));
        backToMenu.setForeground(new Color(255,255,255));
        backToMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                Main.m.setVisible(true);
                Main.m.requestFocusInWindow();
                if (animator.isStarted()) animator.stop();
                Main.m.repaint();
                Main.g.setVisible(false);
                repaint();
            }

            @Override
            public void mouseEntered(MouseEvent arg0) {
                Main.g.getContentPane().setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                Main.g.getContentPane().setCursor(Cursor.getDefaultCursor());
            }
        });

        endscreen.add(backToMenu);
        endscreen.add(wonSmall);
        endscreen.add(wonMiddle);
        endscreen.add(wonBig);
        endscreen.add(text);

        JLabel backgroundLabel = new JLabel();
        backgroundLabel.setBounds(0,0, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
        backgroundLabel.setBorder(null);
        File folderInput2 = new File("res\\menuImage.png");
        BufferedImage folderImageBackdrop;
        try {
            folderImageBackdrop = ImageIO.read(folderInput2);
            backgroundLabel.setIcon(new ImageIcon(folderImageBackdrop));
        }catch (Exception e){
            System.out.println("Error- Could'nt read ManualImage");
        }

        endscreen.add(backgroundLabel);
        this.getContentPane().add(endscreen);
        endscreen.setVisible(false);
    }
    private TimerTask winAnimation = new TimerTask() {
        @Override
        public void run() {
            switch (timerCount){
                case 0:
                    wonSmall.setVisible(true);
                    wonBig.setVisible(false);
                    timerCount++;
                    break;
                case 1:
                    wonMiddle.setVisible(true);
                    wonSmall.setVisible(false);
                    timerCount++;
                    break;
                case 2:
                    wonBig.setVisible(true);
                    wonMiddle.setVisible(false);
                    timerCount = 0;
            }
            repaint();
        }
    };
    public void stopLoadTimer(){
        animation.cancel();
        animation.purge();
    }
}