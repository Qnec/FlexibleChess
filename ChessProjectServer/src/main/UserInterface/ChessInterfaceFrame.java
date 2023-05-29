package main.UserInterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;

import main.Game;
import main.GameType;
import main.MoveReference;
import main.Position;
import main.UserInterface.Panel.BackgroundPanel;
import main.UserInterface.Panel.ButtonPanel;
import main.UserInterface.Panel.CenteredContainingPanel;
import main.UserInterface.Panel.MoveDisplayPanel;
import main.UserInterface.Panel.PieceDisplayPanel;
import main.UserInterface.Panel.VictoryPanel;

public class ChessInterfaceFrame extends JFrame {
    private Game game;
    private GameType gt;
    private JLayeredPane paneContainer;
    private BackgroundPanel backgroundPanel;
    private PieceDisplayPanel pieceDisplayPanel;
    private MoveDisplayPanel moveDisplayPanel;
    private ButtonPanel buttonPanel;
    private CenteredContainingPanel victoryContainingPanel;
    private Dimension maxDimensions = new Dimension(800, 800);
    private Dimension dimensions;
    private Dimension windowDimensions;
    //private double gameAspectRatio;
    private Integer pixelsPerSquare;
    private Container contentPane;

    private Position selectedSquare;
    private ArrayList<MoveReference> availabeReferences = new ArrayList<>();
    private HashMap<Position, ArrayList<MoveReference>> availableReferencesByPosition = new HashMap<>();
    private boolean ended = false;

    public ChessInterfaceFrame(String gameID) {
        super(gameID);
        this.game = new Game(gameID);
        this.gt = game.gt;
        //this.gt = new GameType("/home/jason/Documents/Projects/FlexibleChess/modern_xiangqi");
        //this.game = new Game("modern_xiangqi");
        
        
        //this.gameAspectRatio = (double)this.gt.dimensions.x/(double)this.gt.dimensions.y;
        this.pixelsPerSquare = (int)maxDimensions.getWidth()/Math.max(this.gt.dimensions.x,this.gt.dimensions.y);
        this.dimensions = new Dimension(this.gt.dimensions.x*this.pixelsPerSquare, this.gt.dimensions.y*this.pixelsPerSquare);
        //System.out.println(this.pixelsPerSquare);
        //System.out.println(this.dimensions);
        
        
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(this.dimensions);
        this.contentPane = this.getContentPane();
        //this.setLayout(new BorderLayout());
        this.setLayout(new BorderLayout());
        this.paneContainer = new JLayeredPane();
        this.add(this.paneContainer, BorderLayout.CENTER);
        
        this.backgroundPanel = new BackgroundPanel(dimensions, this.gt);
        this.pieceDisplayPanel = new PieceDisplayPanel(dimensions, this.pixelsPerSquare, this.game);
        this.moveDisplayPanel = new MoveDisplayPanel(dimensions, this.pixelsPerSquare, this.game);
        this.buttonPanel = new ButtonPanel(dimensions, this.pixelsPerSquare, this.gt);
        //this.paneContainer.setSize(dimensions);
        //this.backgroundPanel.setSize(dimensions);
        //this.pieceDisplayPanel.setSize(dimensions);
        //this.buttonPanel.setSize(dimensions);
        
        //this.backgroundPanel.setBackground(Color.BLUE);
        //this.pieceDisplayPanel.setBackground(Color.GREEN);
        //this.buttonPanel.setBackground(Color.RED);
        
        //this.backgroundPanel.setOpaque(true);
        //this.pieceDisplayPanel.setOpaque(false);
        //this.buttonPanel.setOpaque(true);
        
        this.paneContainer.add(this.backgroundPanel, Integer.valueOf(0), 0);
        this.paneContainer.add(this.pieceDisplayPanel, Integer.valueOf(1), 0);
        this.paneContainer.add(this.moveDisplayPanel, Integer.valueOf(2), 0);
        this.paneContainer.add(this.buttonPanel, Integer.valueOf(3), 0);
        this.buttonPanel.addButtons();
        this.setVisible(true);
        this.windowDimensions = new Dimension(this.dimensions.width+this.getInsets().left+this.getInsets().right, this.dimensions.height+this.getInsets().bottom+this.getInsets().top);
        this.setSize(this.windowDimensions);
        //this.pack();
        //this.moveDisplayPanel.displayMoves(this.game.getAvailableMoves(0));
    }

    public void clickAt(Position p) {
        if(this.ended) {
            return;
        }
        //System.out.println(p);
        if(this.availableReferencesByPosition.containsKey(p)) {
            this.selectedSquare = null;
            MoveReference selectedMoveReference = this.availableReferencesByPosition.get(p).get(0);
            //System.out.println(selectedMoveReference);
            this.executeMove(selectedMoveReference);
        } else {
            if(this.game.getPieces(p).size() > 0) {
                this.selectedSquare = p;
            } else {
                this.selectedSquare = null;
            }
        }
        this.getSelectedSquaresMoves();
        this.displaySelectedSquaresMoves();
    }

    public void executeMove(MoveReference move) {
        game.executeMove(move);
        this.pieceDisplayPanel.addPieces();
        //System.out.println(game.getWinner());
        if(game.getWinner() != -1) {
            this.ended = true;
            this.instantiateAndDisplayVictory();
        }
        //this.pieceDisplayPanel.repaint();
        //this.moveDisplayPanel.repaint();
        //this.buttonPanel.repaint();
        //this.repaint();
    }

    public void getSelectedSquaresMoves() {
        this.availableReferencesByPosition = new HashMap<>();
        this.availabeReferences = new ArrayList<>();
        if(this.selectedSquare != null) {
            this.availabeReferences = this.game.getAvailableMoves(this.selectedSquare);
            for(MoveReference reference : this.availabeReferences) {
                if(!this.availableReferencesByPosition.containsKey(reference.finish)) {
                    this.availableReferencesByPosition.put(reference.finish, new ArrayList<>());
                }
                this.availableReferencesByPosition.get(reference.finish).add(reference);
            }
        }
    }

    public void displaySelectedSquaresMoves() {
        this.moveDisplayPanel.displayMoves(this.availabeReferences);
    }

    public void instantiateAndDisplayVictory() {
        //System.out.println("thing");
        this.victoryContainingPanel = new CenteredContainingPanel(this.dimensions, new VictoryPanel(new Dimension((int)this.dimensions.getWidth()/2, (int)this.dimensions.getWidth()/2), this.pixelsPerSquare, game));
        this.paneContainer.add(this.victoryContainingPanel, Integer.valueOf(4));
    }
}
