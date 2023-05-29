package main.ActionTypes;

import java.util.HashSet;

import main.Game;
import main.MoveReference;
import main.Piece;
import main.Position;

public class Attack extends Action{
    private Position position;
    public Attack(ActionType type, String[] parameters) {
        super(type);
        this.position = new Position(parameters[0]);
    }

    public void executeAction(Game game, Piece piece, MoveReference moveReference) {
        Position translatedPosition = piece.translatePosition(game.translatePosition(moveReference.translatePosition(this.position.getCopy()), piece.getPlayer()));
        HashSet<Piece> pieces = game.getAttackables(translatedPosition);
        for(Piece p : pieces) {
            //System.out.println(p);
            //p.getPlayer()
            boolean success = p.take(piece.getPlayer());
            //System.out.println(success);
            //System.out.println(p.getPieceType().getRoyaltyGroup());
            if(success && p.getPieceType().getRoyaltyGroup() != -1) {
                //System.out.println("thing");
                game.decrementRoyaltyCount(p.getPlayer());
            }
        }
        game.getPieces(translatedPosition).clear();
    }
}