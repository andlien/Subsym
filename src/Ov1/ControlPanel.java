package Ov1;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Random;
import java.util.regex.Matcher;

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
        add(alignmentText, c);

        JLabel cohesionText = new JLabel("Cohesion");
        c.gridx = 2;
        c.gridy = 0;
        add(cohesionText, c);

        JLabel separationValue = new JLabel();
        c.gridx = 0;
        c.gridy = 1;
        add(separationValue, c);

        JLabel alignmentValue = new JLabel();
        c.gridx = 1;
        c.gridy = 1;
        add(alignmentValue, c);

        JLabel cohesionValue = new JLabel();
        c.gridx = 2;
        c.gridy = 1;
        add(cohesionValue, c);

        separationSlider = new JSlider(0, 100);
        separationSlider.setValue(20);
        separationSlider.addChangeListener(new LabelChangeListener(separationValue));
        c.gridx = 0;
        c.gridy = 2;
        add(separationSlider, c);

        alignmentSlider = new JSlider(0, 100);
        alignmentSlider.setValue(20);
        alignmentSlider.addChangeListener(new LabelChangeListener(alignmentValue));
        c.gridx = 1;
        c.gridy = 2;
        add(alignmentSlider, c);

        cohesionSlider = new JSlider(0, 100);
        cohesionSlider.setValue(30);
        cohesionSlider.addChangeListener(new LabelChangeListener(cohesionValue));
        c.gridx = 2;
        c.gridy = 2;
        add(cohesionSlider, c);

        Random random = new Random();

        Button addObstacle = new Button("Add obstacle");
        addObstacle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainProgram.objects.listIterator().add(new Obstacle(random.nextInt(MainProgram.board.getWidth()), random.nextInt(MainProgram.board.getHeight())));
            }
        });
        c.gridx = 0;
        c.gridy = 3;
        add(addObstacle, c);
        Button addPredator = new Button("Add predator");
        addPredator.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainProgram.objects.listIterator().add(new Predator(random.nextInt(MainProgram.board.getWidth()), random.nextInt(MainProgram.board.getHeight())));
            }
        });
        c.gridx = 2;
        c.gridy = 3;
        add(addPredator, c);

        Button deletaAllObstacles = new Button("Delete obstacles");
        deletaAllObstacles.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainProgram.deleteObstacles = true;
            }
        });
        c.gridx = 0;
        c.gridy = 4;
        add(deletaAllObstacles, c);
        Button deleteAllPredators = new Button("Delete predators");
        deleteAllPredators.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainProgram.deletePredators = true;
            }
        });
        c.gridx = 2;
        c.gridy = 4;
        add(deleteAllPredators, c);

        separationValue.setText(String.valueOf(separationSlider.getValue()));
        alignmentValue.setText(String.valueOf(alignmentSlider.getValue()));
        cohesionValue.setText(String.valueOf(cohesionSlider.getValue()));
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

    public class LabelChangeListener implements ChangeListener {

        private JLabel label;

        public LabelChangeListener(JLabel label) {
            this.label = label;
        }

        @Override
        public void stateChanged(ChangeEvent e) {
            label.setText(String.valueOf(((JSlider)e.getSource()).getValue()));
        }
    }
}
