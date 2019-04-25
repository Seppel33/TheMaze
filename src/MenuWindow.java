import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Timer;

/**Autor: Sebastian Schmidt
 * Menü-Klasse regelt das Aussehen und den Verlauf des UIs
 */
public class MenuWindow extends JFrame {
    private Timer loadScreenTimer;
    private int first = 0;
    private ImageProcessing processing;
    private JLabel gameName;
    private File folderInput2 = new File("res\\menuImage.png");
    private BufferedImage folderImageBackdrop;

    public MenuWindow() {

        this.setUndecorated(true);
        this.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
        this.setVisible(true);
        this.setLayout(null);
        try {
            folderImageBackdrop = ImageIO.read(folderInput2);
        } catch (Exception e) {
            System.out.println("Error- Could'nt read ManualImage");
        }
        //this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        startMenu();
        this.pack();
    }

    private void startMenu() {
        JPanel menuPanel = new JPanel();
        menuPanel.setSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
        menuPanel.setLayout(null);
        gameName = new JLabel();
        gameName.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 350, 0, 700, 300);
        gameName.setHorizontalAlignment(JLabel.CENTER);
        gameName.setText("The Maze");
        gameName.setFont(new Font("Helvetica", Font.PLAIN, 150));
        gameName.setForeground(new Color(255, 111, 0));
        //gameName.setBorder(null);
        this.getContentPane().add(gameName);

        JButton settingsButton = new JButton("Einstellungen");
        settingsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        settingsButton.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 100, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - 25, 200, 50);
        settingsButton.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        settingsButton.setBorder(null);
        settingsButton.setOpaque(true);
        settingsButton.setBackground(new Color(196, 62, 0));
        settingsButton.setForeground(new Color(255, 255, 255));
        settingsButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                remove(menuPanel);
                startSettings();
                repaint();
            }

            @Override
            public void mouseEntered(MouseEvent arg0) {
                settingsButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                settingsButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        menuPanel.add(settingsButton);


        JButton playButton = new JButton("Spielen");
        playButton.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        playButton.setBorder(null);
        playButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        playButton.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 100, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - 125, 200, 50);
        playButton.setOpaque(true);
        playButton.setBackground(new Color(196, 62, 0));
        playButton.setForeground(new Color(255, 255, 255));
        playButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                remove(menuPanel);
                startPlaySelectPanel();
                repaint();
            }

            @Override
            public void mouseEntered(MouseEvent arg0) {
                playButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                playButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        menuPanel.add(playButton);


        JButton exitButton = new JButton("Beenden");
        exitButton.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 100, Toolkit.getDefaultToolkit().getScreenSize().height / 2 + 75, 200, 50);
        exitButton.setBorder(null);
        exitButton.setOpaque(true);
        exitButton.setBackground(new Color(196, 62, 0));
        exitButton.setForeground(new Color(255, 255, 255));
        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                System.out.println("EXIT CLICKED");
                System.exit(0);
            }

            @Override
            public void mouseEntered(MouseEvent arg0) {
                exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                exitButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        menuPanel.add(exitButton);

        JLabel backgroundLabel = new JLabel();
        backgroundLabel.setBounds(0, 0, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
        backgroundLabel.setBorder(null);
        backgroundLabel.setIcon(new ImageIcon(folderImageBackdrop));
        menuPanel.add(backgroundLabel);

        this.getContentPane().add(menuPanel);
        repaint();
    }

    private void startPlaySelectPanel() {
        JPanel playSelectPanel = new JPanel();
        playSelectPanel.setSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
        playSelectPanel.setLayout(null);

        JButton newLabyrinth = new JButton("Neues Labyrinth");
        newLabyrinth.setAlignmentX(Component.CENTER_ALIGNMENT);
        newLabyrinth.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 100, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - 25, 200, 50);
        newLabyrinth.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        newLabyrinth.setBorder(null);
        newLabyrinth.setOpaque(true);
        newLabyrinth.setBackground(new Color(196, 62, 0));
        newLabyrinth.setForeground(new Color(255, 255, 255));
        newLabyrinth.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                processing = new ImageProcessing("fullStart");
                if (!(processing.getImgMat() == null)) {
                    if (processing.analysePicture()) {
                        remove(playSelectPanel);
                        startUserInput();
                        repaint();
                    } else {
                        remove(playSelectPanel);
                        displayWarning();
                        repaint();
                    }
                } else {
                    playSelectPanel.requestFocus();
                }

            }

            @Override
            public void mouseEntered(MouseEvent arg0) {
                newLabyrinth.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                newLabyrinth.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        playSelectPanel.add(newLabyrinth);


        JButton loadLabyrinth = new JButton("Labyrinth laden");
        loadLabyrinth.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        loadLabyrinth.setBorder(null);
        loadLabyrinth.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadLabyrinth.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 100, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - 125, 200, 50);
        loadLabyrinth.setOpaque(true);
        loadLabyrinth.setBackground(new Color(196, 62, 0));
        loadLabyrinth.setForeground(new Color(255, 255, 255));
        loadLabyrinth.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
                // Choose file path and file name via a file selector box
                int returnVal = fileChooser.showOpenDialog(Main.m);
                File selectedFile = fileChooser.getSelectedFile();
                String filePathName = selectedFile.getPath();
                // End: Choose file path and file name via a file selector box
                String f = filePathName.substring(filePathName.indexOf(".")+1, filePathName.length());
                if (f.equals("txt")) {
                    DataManager.loadMaze(filePathName);
                    if (Main.g == null) {
                        Main.g = new GameWindow();
                        Main.m.setVisible(false);
                    } else {
                        Main.g = new GameWindow();
                        Main.g.setVisible(true);

                        Main.m.setVisible(false);
                    }
                    remove(playSelectPanel);
                    startMenu();
                    repaint();
                }
            }

            @Override
            public void mouseEntered(MouseEvent arg0) {
                loadLabyrinth.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                loadLabyrinth.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        playSelectPanel.add(loadLabyrinth);


        JButton backButton = new JButton("Zurück");
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 100, Toolkit.getDefaultToolkit().getScreenSize().height / 2 + 75, 200, 50);
        backButton.setBorder(null);
        backButton.setOpaque(true);
        backButton.setBackground(new Color(196, 62, 0));
        backButton.setForeground(new Color(255, 255, 255));
        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                remove(playSelectPanel);
                startMenu();
                repaint();
            }

            @Override
            public void mouseEntered(MouseEvent arg0) {
                backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                backButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        playSelectPanel.add(backButton);

        JLabel backgroundLabel = new JLabel();
        backgroundLabel.setBounds(0, 0, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
        backgroundLabel.setBorder(null);
        backgroundLabel.setIcon(new ImageIcon(folderImageBackdrop));
        playSelectPanel.add(backgroundLabel);

        this.getContentPane().add(playSelectPanel);
    }

    private void displayWarning() {
        JPanel warningPanel = new JPanel();
        warningPanel.setSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
        warningPanel.setLayout(null);
        warningPanel.requestFocus();

        JLabel manualLabel = new JLabel();

        JButton understoodButton = new JButton("Verstanden");

        JButton showManual = new JButton("Spielanleitung");

        manualLabel.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 400, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - 500, 800, 1000);
        manualLabel.setBorder(null);
        manualLabel.setVisible(false);
        File folderInput = new File("res\\Anleitung_Maze.jpg");
        BufferedImage folderImage;
        try {
            folderImage = ImageIO.read(folderInput);
            manualLabel.setIcon(new ImageIcon(folderImage));
        } catch (Exception e) {
            System.out.println("Error- Could'nt read ManualImage");
        }

        //manualLabel.setIcon(new ImageIcon("res\\Anleitung_Maze_new.jpg"));
        manualLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                manualLabel.setVisible(false);
                showManual.setVisible(true);
                understoodButton.setVisible(true);
                gameName.setVisible(true);
                repaint();
            }

            @Override
            public void mouseEntered(MouseEvent arg0) {
                manualLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                manualLabel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        warningPanel.add(manualLabel);


        showManual.setAlignmentX(Component.CENTER_ALIGNMENT);
        showManual.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 300, Toolkit.getDefaultToolkit().getScreenSize().height / 2 + 125, 600, 50);
        showManual.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        showManual.setBorder(null);
        showManual.setOpaque(true);
        showManual.setBackground(new Color(196, 62, 0));
        showManual.setForeground(new Color(255, 255, 255));
        showManual.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {

                showManual.setVisible(false);
                understoodButton.setVisible(false);
                gameName.setVisible(false);
                manualLabel.setVisible(true);
                repaint();
            }

            @Override
            public void mouseEntered(MouseEvent arg0) {
                showManual.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                showManual.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        warningPanel.add(showManual);


        understoodButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        understoodButton.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 300, Toolkit.getDefaultToolkit().getScreenSize().height / 2 + 200, 600, 50);
        understoodButton.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        understoodButton.setBorder(null);
        understoodButton.setOpaque(true);
        understoodButton.setBackground(new Color(196, 62, 0));
        understoodButton.setForeground(new Color(255, 255, 255));
        understoodButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                remove(warningPanel);
                startPlaySelectPanel();
                repaint();
            }

            @Override
            public void mouseEntered(MouseEvent arg0) {
                understoodButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                understoodButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        warningPanel.add(understoodButton);

        JTextPane warningTextPane = new JTextPane();
        warningTextPane.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 300, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - 200, 600, 300);
        warningTextPane.setBorder(null);
        warningTextPane.setBackground(new Color(176, 0, 32));
        warningTextPane.setOpaque(true);
        warningTextPane.setEnabled(false);
        warningTextPane.setDisabledTextColor(new Color(255, 255, 255));
        String warningText = "Leider ist etwas schiefgelaufen :(" + '\n' + '\n' +
                "Beim erkennen des Bildes ist etwas Fehlgeschlagen. Bitte stelle sicher, dass das Labyrinth auf dem Bild erkennbar ist," +
                " oder überprüfe," + '\n' + " ob alle notwendigen Punkte eingezeichnet sind." + '\n' + " Welche das sind, siehst du in der Anleitung.";
        StyledDocument doc = warningTextPane.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        warningTextPane.setText(warningText);
        warningTextPane.setFont(new Font("Helvetica", Font.PLAIN, 24));
        warningPanel.add(warningTextPane);

        JLabel backgroundLabel = new JLabel();
        backgroundLabel.setBounds(0, 0, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
        backgroundLabel.setBorder(null);
        backgroundLabel.setIcon(new ImageIcon(folderImageBackdrop));
        warningPanel.add(backgroundLabel);

        this.getContentPane().add(warningPanel);
    }

    private void startSettings() {
        JPanel settingsPanel = new JPanel();
        settingsPanel.setSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
        settingsPanel.setLayout(null);

        JButton manual = new JButton("Spielanleitung");
        JButton backButton = new JButton("Zurück");
        JButton developerStart = new JButton("Starte Entwicklermodus");

        JLabel team = new JLabel("Das Team");
        team.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width - 400, 350, 300, 50);
        team.setBorder(null);
        team.setHorizontalAlignment(JLabel.CENTER);
        team.setFont(new Font("Helvetica", Font.BOLD, 30));
        team.setForeground(new Color(255, 171, 0));

        JLabel furkan = new JLabel("Furkan Calisir");
        furkan.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width - 400, 650, 300, 50);
        furkan.setBorder(null);
        furkan.setHorizontalAlignment(JLabel.CENTER);
        furkan.setFont(new Font("Helvetica", Font.ITALIC, 20));
        furkan.setOpaque(true);
        furkan.setForeground(new Color(0, 0, 0));
        furkan.setBackground(new Color(255, 255, 255));

        JLabel michelle = new JLabel("Michelle Wetscheck");
        michelle.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width - 400, 750, 300, 50);
        michelle.setBorder(null);
        michelle.setHorizontalAlignment(JLabel.CENTER);
        michelle.setFont(new Font("Helvetica", Font.ITALIC, 20));
        michelle.setOpaque(true);
        michelle.setForeground(new Color(0, 0, 0));
        michelle.setBackground(new Color(255, 255, 255));

        JLabel bela = new JLabel("Béla Korb");
        bela.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width - 400, 450, 300, 50);
        bela.setBorder(null);
        bela.setHorizontalAlignment(JLabel.CENTER);
        bela.setFont(new Font("Helvetica", Font.ITALIC, 20));
        bela.setOpaque(true);
        bela.setForeground(new Color(0, 0, 0));
        bela.setBackground(new Color(255, 255, 255));

        JLabel sebastian = new JLabel("Sebastian Schmidt");
        sebastian.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width - 400, 550, 300, 50);
        sebastian.setBorder(null);
        sebastian.setHorizontalAlignment(JLabel.CENTER);
        sebastian.setFont(new Font("Helvetica", Font.ITALIC, 20));
        sebastian.setOpaque(true);
        sebastian.setForeground(new Color(0, 0, 0));
        sebastian.setBackground(new Color(255, 255, 255));

        JLabel manualLabel = new JLabel();
        manualLabel.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 400, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - 500, 800, 1000);
        manualLabel.setBorder(null);
        manualLabel.setVisible(false);
        File folderInput = new File("res\\Anleitung_Maze.jpg");
        BufferedImage folderImage;
        try {
            folderImage = ImageIO.read(folderInput);
            manualLabel.setIcon(new ImageIcon(folderImage));
        } catch (Exception e) {
            System.out.println("Error- Could'nt read ManualImage");
        }
        manualLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                manualLabel.setVisible(false);
                manual.setVisible(true);
                backButton.setVisible(true);
                gameName.setVisible(true);
                developerStart.setVisible(true);
                repaint();
            }

            @Override
            public void mouseEntered(MouseEvent arg0) {
                manualLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                manualLabel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        settingsPanel.add(manualLabel);

        developerStart.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        developerStart.setAlignmentX(Component.CENTER_ALIGNMENT);
        developerStart.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 100, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - 125, 200, 50);
        developerStart.setBorder(null);
        developerStart.setOpaque(true);
        developerStart.setBackground(new Color(196, 62, 0));
        developerStart.setForeground(new Color(255, 255, 255));
        developerStart.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                processing = new ImageProcessing();
                if (!(processing.getImgMat() == null)) {
                    processing.startDeveloperMode();
                } else {
                    settingsPanel.requestFocus();
                }
            }

            @Override
            public void mouseEntered(MouseEvent arg0) {
                developerStart.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                developerStart.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        settingsPanel.add(developerStart);

        manual.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        manual.setBorder(null);
        manual.setAlignmentX(Component.CENTER_ALIGNMENT);
        manual.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 100, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - 25, 200, 50);
        manual.setOpaque(true);
        manual.setBackground(new Color(196, 62, 0));
        manual.setForeground(new Color(255, 255, 255));
        manual.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                manualLabel.setVisible(true);
                manual.setVisible(false);
                developerStart.setVisible(false);
                backButton.setVisible(false);
                gameName.setVisible(false);
                repaint();
            }

            @Override
            public void mouseEntered(MouseEvent arg0) {
                manual.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                manual.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        settingsPanel.add(manual);

        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 100, Toolkit.getDefaultToolkit().getScreenSize().height / 2 + 75, 200, 50);
        backButton.setBorder(null);
        backButton.setOpaque(true);
        backButton.setBackground(new Color(196, 62, 0));
        backButton.setForeground(new Color(255, 255, 255));
        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                remove(settingsPanel);
                startMenu();
                repaint();
            }

            @Override
            public void mouseEntered(MouseEvent arg0) {
                backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                backButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        settingsPanel.add(backButton);

        settingsPanel.add(sebastian);
        settingsPanel.add(furkan);
        settingsPanel.add(bela);
        settingsPanel.add(michelle);
        settingsPanel.add(team);

        JLabel version = new JLabel(Main.versionString);
        version.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width - 100, Toolkit.getDefaultToolkit().getScreenSize().height - 20, 100, 20);
        version.setForeground(new Color(255, 171, 0));
        version.setFont(new Font("Helvetica", Font.PLAIN, 12));
        settingsPanel.add(version);

        JLabel backgroundLabel = new JLabel();
        backgroundLabel.setBounds(0, 0, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
        backgroundLabel.setBorder(null);
        backgroundLabel.setIcon(new ImageIcon(folderImageBackdrop));
        settingsPanel.add(backgroundLabel);

        this.getContentPane().add(settingsPanel);
    }

    private void startUserInput() {
        JPanel userInputPanel = new JPanel();
        userInputPanel.setSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
        userInputPanel.setLayout(null);
        userInputPanel.requestFocus();

        JTextField input = new JTextField("Labyrinth Name");

        JButton continueButton = new JButton("Weiter");
        continueButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        continueButton.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 300, Toolkit.getDefaultToolkit().getScreenSize().height / 2 + 125, 600, 50);
        continueButton.setBorder(null);
        continueButton.setOpaque(true);
        continueButton.setBackground(new Color(196, 62, 0));
        continueButton.setForeground(new Color(255, 255, 255));
        continueButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                try {
                    DataManager.saveMaze(input.getText());
                }catch (Exception e){
                    System.err.println("Unable to save Maze");
                }
                if (Main.g == null) {
                    Main.g = new GameWindow();
                    Main.m.setVisible(false);
                } else {
                    Main.g = new GameWindow();
                    Main.g.setVisible(true);

                    Main.m.setVisible(false);
                }
                remove(userInputPanel);
                startMenu();
                repaint();
            }

            @Override
            public void mouseEntered(MouseEvent arg0) {
                continueButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                continueButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        userInputPanel.add(continueButton);


        input.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 300, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - 25, 600, 50);
        input.setBorder(null);
        input.setBackground(new Color(255, 171, 0));
        input.setOpaque(true);
        input.setForeground(new Color(0, 0, 0));
        input.setFont(new Font("Helvetica", Font.PLAIN, 24));
        input.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                input.setText("");
                repaint();
            }
            @Override
            public void mouseEntered(MouseEvent arg0) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });
        userInputPanel.add(input);

        JLabel backgroundLabel = new JLabel();
        backgroundLabel.setBounds(0, 0, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
        backgroundLabel.setBorder(null);
        backgroundLabel.setIcon(new ImageIcon(folderImageBackdrop));
        userInputPanel.add(backgroundLabel);

        this.getContentPane().add(userInputPanel);
    }
}
