import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Fuer Developer-Modus
 */
public class Window {
    private JSlider hellSlider;
    private JSlider konSlider;

    public JCheckBox getRotH() {
        return rotH;
    }

    public JCheckBox getGruenH() {
        return gruenH;
    }

    public JCheckBox getBlauH() {
        return blauH;
    }

    public JCheckBox getRotK() {
        return rotK;
    }

    public JCheckBox getGruenK() {
        return gruenK;
    }

    public JCheckBox getBlauK() {
        return blauK;
    }

    private JCheckBox rotH;
    private JCheckBox gruenH;
    private JCheckBox blauH;
    private JCheckBox rotK;
    private JCheckBox gruenK;
    private JCheckBox blauK;
    private JButton saveButton;
    private JButton to64;
    private JButton to8;
    private JLabel helligkeitLabel;
    private JLabel kontrastLabel;

    public JSlider getHellSlider() {
        return hellSlider;
    }

    public JSlider getKonSlider() {
        return konSlider;
    }
    private JButton gaussfilterButton;
    private JButton medianfilterButton;
    private JButton mittelwertfilterButton;
    public JPanel windowView;
    private JButton sobelButton;
    private JButton robertSGradientButton;
    private JButton cannyButton;
    private JButton invertButton;
    private JButton fouriertransformationButton;
    private JButton houghTransFuerKreiseButton;
    private JButton countCoinsButton;
    private JButton linienButton;
    private JSlider slider1;
    private JSlider slider2;
    private JButton visualStartButton;
    private JSlider slider3;
    private JSlider slider4;
    private JSlider slider5;
    private JButton lowpassButton;
    private JButton invertFourierButton;
    private JButton fullStart;
    private JButton hierarchyButton;
    private JButton colorDetButton;
    private JButton scaleButton;


    public void setProcess(ImageProcessing process) {
        this.process = process;
    }


    private ImageProcessing process;


    public Window() {
        hellSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {

                process.setProcessedImgMat(process.aendereHelligkeit(process.getImgMat(), hellSlider.getValue()));
            }
        });
        konSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                process.setProcessedImgMat(process.aendereKontrast(process.getImgMat(), konSlider.getValue()));
            }
        });
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                process.setImgMat(process.getProcessedImgMat());
                process.getImgPanel1().setImage(process.Mat2BufferedImage(process.getImgMat()));
                process.writeImage(process.getProcessedImgMat(), "ProcessedImage.png");
            }
        });
        to64.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                process.setProcessedImgMat(process.processTo64(process.getImgMat()));
            }
        });
        to8.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                process.setProcessedImgMat(process.processTo8(process.getImgMat()));
            }
        });


        gaussfilterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                process.setProcessedImgMat((process.gauss(process.getImgMat())));
            }
        });
        medianfilterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                process.setProcessedImgMat((process.median(process.getImgMat())));
            }
        });
        mittelwertfilterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                process.setProcessedImgMat((process.mittelWert(process.getImgMat())));
            }
        });
        sobelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                process.setProcessedImgMat((process.sobel(process.getImgMat())));
            }
        });
        robertSGradientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                process.setProcessedImgMat((process.gradient(process.getImgMat())));
            }
        });
        cannyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                process.setProcessedImgMat((process.canny(process.getImgMat())));
            }
        });
        invertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                process.setProcessedImgMat((process.invert(process.getImgMat())));
            }
        });
        fouriertransformationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                process.setProcessedImgMat((process.fouriertransformation(process.getImgMat())));
            }
        });
        houghTransFuerKreiseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                process.setProcessedImgMat((process.circles(process.getImgMat())));
            }
        });
        countCoinsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                process.setProcessedImgMat((process.countCoins(process.getImgMat())));
            }
        });
        linienButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                process.setProcessedImgMat((process.lines(process.getImgMat())));
            }
        });
        slider1.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                process.setMinThreshhold(slider1.getValue());

            }
        });
        slider2.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                process.setLinesThreshold(slider2.getValue());
            }
        });
        visualStartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GameWindow();
            }
        });
        slider3.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                ShapesRendererPP.setLightPosition(0,slider3.getValue());
            }
        });
        slider4.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                ShapesRendererPP.setLightPosition(1,slider4.getValue());
            }
        });
        slider5.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                ShapesRendererPP.setLightPosition(2,slider5.getValue());
            }
        });
        lowpassButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                process.setProcessedImgMat(process.lowPassFilter(process.getImgMat()));
            }
        });
        invertFourierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                process.setProcessedImgMat(process.invertFourier(process.getImgMat()));
            }
        });
        colorDetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                process.setProcessedImgMat(process.testColor(process.getImgMat()));

            }
        });
        fullStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                process.colorPoints(process.getImgMat());

                process.setProcessedImgMat((process.gauss(process.getImgMat())));
                process.setImgMat(process.getProcessedImgMat());
                process.getImgPanel1().setImage(process.Mat2BufferedImage(process.getImgMat()));
                process.writeImage(process.getProcessedImgMat(), "ProcessedImage.png");

                process.setProcessedImgMat((process.canny(process.getImgMat())));
                process.setImgMat(process.getProcessedImgMat());
                process.getImgPanel1().setImage(process.Mat2BufferedImage(process.getImgMat()));
                process.writeImage(process.getProcessedImgMat(), "ProcessedImage.png");

                process.setProcessedImgMat((process.gauss(process.getImgMat())));
                process.setImgMat(process.getProcessedImgMat());
                process.getImgPanel1().setImage(process.Mat2BufferedImage(process.getImgMat()));
                process.writeImage(process.getProcessedImgMat(), "ProcessedImage.png");

                process.setProcessedImgMat((process.lines(process.getImgMat())));
                //process.setImgMat(process.getProcessedImgMat());
                //process.getImgPanel1().setImage(process.Mat2BufferedImage(process.getImgMat()));
                process.writeImage(process.getProcessedImgMat(), "ProcessedImage.png");

                new GameWindow();
            }
        });
        hierarchyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                process.setProcessedImgMat(process.contoursHierarchy(process.getImgMat()));

            }
        });

        scaleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                process.setProcessedImgMat(process.scale(process.getImgMat()));
            }
        });
    }

}
