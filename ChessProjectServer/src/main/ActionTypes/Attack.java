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
        Position translatedPosition = piece.translatePosition(game.translatePosition(moveReference.translatePosition(this.position), piece.getPlayer()));
        HashSet<Piece> pieces = game.getAttackables(translatedPosition);
        for(Piece p : pieces) {
            p.take(piece.getPlayer());
        }
        game.getPieces(translatedPosition).clear();
    }
}
