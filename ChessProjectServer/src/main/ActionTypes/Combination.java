package main.ActionTypes;

import main.Game;
import main.MoveReference;
import main.Piece;
import main.Position;

public class Combination extends Action{
    private Position initialPosition;
    private Position finalPosition;
    public Combination(ActionType type, String[] parameters) {
        super(type);
        /*for(String i : parameters) {
            System.out.println(i);
        }*/
        if(parameters.length != 2){
            throw new Error("unsupported arguments for combi");
        }
        this.initialPosition = new Position(parameters[0]);
        this.finalPosition = new Position(parameters[1]);
    }

    public void executeAction(Game game, Piece piece, MoveReference moveReference) {
        Position initialPositionTranslated = piece.translatePosition(
            game.translatePosition(
                moveReference.translatePosition(this.initialPosition.getCopy()), piece.getPlayer()
                )
            );
        Position finalPositionTranslated = piece.translatePosition(
            game.translatePosition(
                moveReference.translatePosition(this.finalPosition.getCopy()), piece.getPlayer()
                )
            );
        for(Piece p : game.getPieces(initialPositionTranslated)) {
            game.movePiece(p, finalPositionTranslated);
        }
    }
}
