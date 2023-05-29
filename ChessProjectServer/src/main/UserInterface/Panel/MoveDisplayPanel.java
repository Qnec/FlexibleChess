package main.UserInterface.Panel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.JPanel;

import main.Game;
import main.MoveReference;
import main.Position;

public class MoveDisplayPanel extends JPanel {
    private Dimension dimensions;
    private Integer pixelsPerSqare;
    private Game game;
    private Dimension squareDimensions;
    private ArrayList<Component> addedHighlights = new ArrayList<>();
    public MoveDisplayPanel(Dimension dimensions, Integer pixelsPerSquare, Game game) {
        super();
        this.dimensions = dimensions;
        this.pixelsPerSqare = pixelsPerSquare;
        this.game = game;
        this.squareDimensions = new Dimension(this.pixelsPerSqare, this.pixelsPerSqare);
        
        super.setLayout(null);
        this.setOpaque(false);
        this.setSize(dimensions);
    }

    public void displayMoves(ArrayList<MoveReference> references) {
        for(Component highlight : this.addedHighlights) {
            this.remove(highlight);
        }
        HashSet<Position> positions = new HashSet<>();
        for(MoveReference reference : references) {
            positions.add(reference.finish);
            //System.out.println(reference.finish);
            //System.out.println(reference.finish.hashCode());
        }
        //System.out.println(positions);
        for(Position pos : positions) {
            JPanel highlight = new JPanel();
            highlight.setBackground(new Color(0,255,0,63));
            highlight.setSize(this.squareDimensions);
            highlight.setPreferredSize(this.squareDimensions);
            highlight.setMinimumSize(this.squareDimensions);
            highlight.setMaximumSize(this.squareDimensions);
            highlight.setLocation(pos.x*this.pixelsPerSqare, pos.y*this.pixelsPerSqare);
            this.addedHighlights.add(highlight);
            this.add(highlight);
        }
        this.repaint();
    }
}
