package Ov1;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Anders on 26.01.2016.
 */
public class ControlPanel extends JPanel{

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

        JSlider separationSlider = new JSlider(0, 100);
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 1.0;
        add(separationSlider, c);

        JSlider alignmentSlider = new JSlider(0, 100);
        c.gridx = 1;
        c.gridy = 1;
        c.weightx = 1.0;
        add(alignmentSlider, c);

        JSlider cohesionSlider = new JSlider(0, 100);
        c.gridx = 2;
        c.gridy = 1;
        c.weightx = 1.0;
        add(cohesionSlider, c);
    }
}
