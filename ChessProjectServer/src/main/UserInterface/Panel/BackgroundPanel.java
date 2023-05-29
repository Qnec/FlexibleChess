package main.UserInterface.Panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.GameType;

public class BackgroundPanel extends JPanel {
    private Dimension dimensions;
    private GameType gt;
    private JLabel background;
    private Image rescaledBackground;
    public BackgroundPanel(Dimension dimensions, GameType gt) {
        super();
        this.dimensions = dimensions;
        this.gt = gt;
        //System.out.println(this.dimensions);
        this.setSize(this.dimensions);
        //System.out.println(gt.background);
        this.rescaledBackground = gt.background.getScaledInstance(this.dimensions.width, this.dimensions.height, Image.SCALE_DEFAULT);
        this.background = new JLabel(new ImageIcon(this.rescaledBackground));
        this.background.setSize(this.dimensions);
        this.background.setPreferredSize(this.dimensions);
        this.background.setMinimumSize(this.dimensions);
        this.background.setMaximumSize(this.dimensions);
        this.background.setPreferredSize(this.dimensions);
        //this.setBackground(Color.RED);
        this.setLayout(null);
        this.background.setLocation(0,0);
        this.add(this.background);
        this.setVisible(true);
        this.setOpaque(true);
    }
}
