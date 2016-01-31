package Ov1;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Anders on 26.01.2016.
 */
public class ControlPanel extends JPanel{

    private JSlider separationSlider;
    private JSlider alignmentSlider;
    private JSlider cohesionSlider;

    public ControlPanel() {
        super(new GridBagLayout());


        GridBagConstraints c = new GridBagConstraints();

        JLabel separationText = new JLabel("Separation");
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0;
        add(separationText, c);

        JLabel alignmentText = new JLabel("Alignment");
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 1.0;
        add(alignmentText, c);

        JLabel cohesionText = new JLabel("Cohesion");
        c.gridx = 2;
        c.gridy = 0;
        c.weightx = 1.0;
        add(cohesionText, c);

        separationSlider = new JSlider(0, 100);
        separationSlider.setValue(10);
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 1.0;
        add(separationSlider, c);

        alignmentSlider = new JSlider(0, 100);
        alignmentSlider.setValue(10);
        c.gridx = 1;
        c.gridy = 1;
        c.weightx = 1.0;
        add(alignmentSlider, c);

        cohesionSlider = new JSlider(0, 100);
        cohesionSlider.setValue(10);
        c.gridx = 2;
        c.gridy = 1;
        c.weightx = 1.0;
        add(cohesionSlider, c);
    }

    public JSlider getSeparationSlider() {
        return separationSlider;
    }

    public JSlider getAlignmentSlider() {
        return alignmentSlider;
    }

    public JSlider getCohesionSlider() {
        return cohesionSlider;
    }
}
