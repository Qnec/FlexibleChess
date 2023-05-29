package main.UserInterface.Panel;

import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

public class CenteredContainingPanel extends JPanel {
    public CenteredContainingPanel(Dimension dimensions, JPanel toContain) {
        super();
        //System.out.println("thing2");
        this.setSize(dimensions);
        this.setOpaque(false);
        this.setLayout(new GridBagLayout());
        this.add(toContain);
    }    
}
