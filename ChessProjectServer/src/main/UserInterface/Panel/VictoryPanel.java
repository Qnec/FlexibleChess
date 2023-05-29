package main.UserInterface.Panel;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.Game;
import main.PieceType;

public class VictoryPanel extends JPanel {
    JLabel winnerText = new JLabel();
    JLabel winnerIcon = new JLabel();
    int winner;
    public VictoryPanel(Dimension dimensions, Integer pixelsPerSquare, Game game) {
        super();
        //System.out.println("thing3");
        //this.setBackground(new Color());
        this.winner = game.getWinner();
        this.winnerText.setText("Winner is Player " + this.winner);
        this.winnerText.setFont(new Font("Serif", Font.PLAIN, 30));
        System.out.println(this.winner);
        PieceType firstRoyalty = null;
        ArrayList<String> pieceTypeIDs = game.gt.getPieceTypes();
        for(String id : pieceTypeIDs) {
            if(game.gt.getPiece(id).getRoyaltyGroup() != -1) {
                firstRoyalty = game.gt.getPiece(id);
            }
        }
        this.setSize(dimensions);
        this.add(this.winnerText);
        if(firstRoyalty != null) {
            BufferedImage playerImage = firstRoyalty.getPieceIconForColor(game.gt.getPlayerColor(this.winner));
            //BufferedImage pieceColorImage = new BufferedImage(unmodifiedImage.getWidth(), unmodifiedImage.getHeight(), unmodifiedImage.getType());
            Dimension squareDimensions = new Dimension(pixelsPerSquare, pixelsPerSquare);
            Image scaledImage = playerImage.getScaledInstance(squareDimensions.width, squareDimensions.height, Image.SCALE_DEFAULT);
            this.winnerIcon.setIcon(new ImageIcon(scaledImage));
            this.add(this.winnerIcon);
        }
    }
}
