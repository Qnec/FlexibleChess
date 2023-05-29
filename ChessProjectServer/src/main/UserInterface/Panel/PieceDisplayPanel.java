package main.UserInterface.Panel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.Area;
import main.Position;
import main.Game;
import main.Piece;
import main.PieceType;

public class PieceDisplayPanel extends JPanel {
    Dimension dimensions;
    Integer pixelsPerSqare;
    Game game;
    JPanel testPanel;
    Dimension squareDimensions;
    HashMap<PieceType, HashMap<Dimension, HashMap<Integer, Image>>> modifiedImageByPieceTypeDimensionAndPlayer = new HashMap<>();
    ArrayList<Component> addedIcons = new ArrayList<>();
    public PieceDisplayPanel(Dimension dimensions, Integer pixelsPerSquare, Game game) {
        super();
        this.dimensions = dimensions;
        this.pixelsPerSqare = pixelsPerSquare;
        this.game = game;
        this.squareDimensions = new Dimension(pixelsPerSquare, pixelsPerSquare);
        super.setLayout(null);
        this.setSize(dimensions);
        this.setOpaque(false);
        //this.setVisible(false);
        //this.setBackground(new Color(0,0,0,0));
        
        /*testPanel = new JPanel();
        testPanel.setLocation(0, 0);
        testPanel.setSize(this.squareDimensions);
        testPanel.setPreferredSize(this.squareDimensions);
        testPanel.setMinimumSize(this.squareDimensions);
        testPanel.setMaximumSize(this.squareDimensions);
        testPanel.setBackground(Color.RED);
        this.add(testPanel);*/
        this.addPieces();
        
        //System.out.println(this.getComponentCount());
    }

    public void addPieces() {
        for(Component icon : this.addedIcons) {
            this.remove(icon);
        }
        HashMap<Position, ArrayList<Piece>> piecesToDrawByPosition = new HashMap<>();
        ArrayList<ArrayList<Piece>> pieces = this.game.getPieceArrayByPlayer();
        for(int player = 0; player < pieces.size(); player++) {
            for(Piece piece : pieces.get(player)) {
                Position pos = piece.getPosition();
                if(piece.getTakenBy() != -1) {
                    continue;
                }
                if(!piecesToDrawByPosition.containsKey(pos)) {
                    piecesToDrawByPosition.put(pos, new ArrayList<Piece>());
                }
                piecesToDrawByPosition.get(pos).add(piece);
            }
        }
        for(Position pos : piecesToDrawByPosition.keySet()) {
            for(Piece piece : piecesToDrawByPosition.get(pos)) {
                //System.out.println(piece.getPieceType().pieceIcon);
                Image icon = getPieceImage(piece);
                //System.out.println(icon);
                JLabel iconComponent = new JLabel(new ImageIcon(icon));
                //JPanel iconComponent = new JPanel();
                //iconComponent.setBackground(Color.BLUE);
                iconComponent.setSize(this.squareDimensions);
                iconComponent.setPreferredSize(this.squareDimensions);
                iconComponent.setMinimumSize(this.squareDimensions);
                iconComponent.setMaximumSize(this.squareDimensions);
                iconComponent.setLocation(pos.x*this.pixelsPerSqare, pos.y*this.pixelsPerSqare);
                //System.out.println(this.squareDimensions);
                //System.out.println(iconComponent.getLocation());
                //System.out.println(iconComponent);
                //iconComponent.setOpaque(true);
                this.addedIcons.add(iconComponent);
                this.add(iconComponent);
                //System.out.println("added");
            }
        }
        /*JPanel testPanel2 = new JPanel();
        testPanel2.setLocation(200, 200);
        testPanel2.setSize(this.squareDimensions);
        testPanel2.setPreferredSize(this.squareDimensions);
        testPanel2.setMinimumSize(this.squareDimensions);
        testPanel2.setMaximumSize(this.squareDimensions);
        testPanel2.setBackground(Color.RED);
        this.add(testPanel2);*/
        //this.repaint();
        //this.validate();
    }

    public Image getPieceImage(Piece piece) {
        if(!modifiedImageByPieceTypeDimensionAndPlayer.containsKey(piece.getPieceType())) {
            modifiedImageByPieceTypeDimensionAndPlayer.put(piece.getPieceType(),new HashMap<Dimension, HashMap<Integer, Image>>());
        }
        if(!modifiedImageByPieceTypeDimensionAndPlayer.get(piece.getPieceType()).containsKey(this.squareDimensions)) {
            modifiedImageByPieceTypeDimensionAndPlayer.get(piece.getPieceType()).put(this.squareDimensions, new HashMap<Integer,Image>());
        }
        if(!modifiedImageByPieceTypeDimensionAndPlayer.get(piece.getPieceType()).get(this.squareDimensions).containsKey(piece.getPlayer())) {
            //System.out.println(piece.getPlayer());
            BufferedImage playerImage = piece.getPieceType().getPieceIconForColor(this.game.gt.getPlayerColor(piece.getPlayer()));
            //BufferedImage pieceColorImage = new BufferedImage(unmodifiedImage.getWidth(), unmodifiedImage.getHeight(), unmodifiedImage.getType());
            
            Image scaledImage = playerImage.getScaledInstance(this.squareDimensions.width, this.squareDimensions.height, Image.SCALE_DEFAULT);
            modifiedImageByPieceTypeDimensionAndPlayer.get(piece.getPieceType()).get(this.squareDimensions).put(piece.getPlayer(), scaledImage);
        }
        //System.out.println(modifiedImage);
        return modifiedImageByPieceTypeDimensionAndPlayer.get(piece.getPieceType()).get(this.squareDimensions).get(piece.getPlayer());
    }
}
