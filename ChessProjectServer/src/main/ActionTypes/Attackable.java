package main.ActionTypes;

import main.Game;
import main.MoveReference;
import main.Piece;
import main.Position;
import main.Area;
import main.AttackableReference;

public class Attackable extends Action{
    private Area attackableArea;
    private int timePresent;
    public Attackable(ActionType type, String[] parameters) {
        super(type);
        /*for(String s : parameters) {
            System.out.println(s);
        }*/
        if(parameters.length != 3){
            //System.out.println("thing8");
            throw new Error("unsupported arguments for atkbl");
        } else if(!parameters[1].equals("for")) {
            throw new Error("unsupported arguments for atkbl");
        }
        
        this.attackableArea = new Area(parameters[0]);
        this.timePresent = Integer.parseInt(parameters[2]);
    }

    public void executeAction(Game game, Piece piece, MoveReference moveReference) {
        AttackableReference reference = new AttackableReference(piece.uuid, game.getTurn(), this.timePresent);
        for(Position pos : this.attackableArea.getEncompassingPositions()) {
            pos = piece.translatePosition(
            game.translatePosition(
                moveReference.translatePosition(pos), piece.getPlayer()
                )
            );
            game.addAttackableReference(reference, pos);
        }
    }
}
