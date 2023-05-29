package main.UserInterface.Panel;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import main.GameType;
import main.Position;
import main.UserInterface.GridButton;

public class ButtonPanel extends JPanel {
    private Dimension dimensions;
    private Integer pixelsPerSqare;
    private GameType gt;
    private Dimension squareDimensions;
    private ArrayList<ArrayList<GridButton>> buttonGrid = new ArrayList<>();
    public ButtonPanel(Dimension dimensions, Integer pixelsPerSquare, GameType gt) {
        super();
        this.dimensions = dimensions;
        this.pixelsPerSqare = pixelsPerSquare;
        this.gt = gt;
        this.squareDimensions = new Dimension(this.pixelsPerSqare, this.pixelsPerSqare);

        super.setLayout(null);
        this.setOpaque(false);
        this.setSize(dimensions);
        //this.addButtons();
    }

    public void addButtons() {
        for(int x = 0; x < this.gt.dimensions.x; x++) {
            this.buttonGrid.add(new ArrayList<>());
            for(int y = 0; y < this.gt.dimensions.y; y++) {
                GridButton button = new GridButton(new Position(x,y), this.squareDimensions);
                this.buttonGrid.get(x).add(button);
                this.add(button);
            }
        }
    }


}
