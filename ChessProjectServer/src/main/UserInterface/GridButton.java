package main.UserInterface;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.SwingUtilities;

import main.Position;
import main.Server;

public class GridButton extends JButton {
    private Position pos;
    public GridButton(Position p, Dimension squareDimensions) {
        super();
        this.pos = p;
        this.setBackground(new Color(0,0,0,0));
        this.setSize(squareDimensions);
        this.setPreferredSize(squareDimensions);
        this.setMinimumSize(squareDimensions);
        this.setMaximumSize(squareDimensions);
        this.setLocation(p.x*squareDimensions.width, p.y*squareDimensions.width);
        this.setBorderPainted(false);
        this.setFocusPainted(false);
        this.setOpaque(false);
        this.addActionListener(new ActionListener() {
            private Position pos;
            private ChessInterfaceFrame parent;
            @Override
            public void actionPerformed(ActionEvent e) {
                Server.frame.clickAt(this.pos);
                //this.parent.clickAt(this.pos);
                //SwingUtilities.getWindowAncestor(this);
                //System.out.println(this.pos);
            }
            
            private ActionListener init(Position pos) { //, Component parent
                this.pos = pos;
                this.parent = (ChessInterfaceFrame)parent;
                return this;
            }
        }.init(this.pos));//, SwingUtilities.getWindowAncestor(this)
    }
}
