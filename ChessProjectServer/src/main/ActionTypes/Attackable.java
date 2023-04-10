package main.ActionTypes;

import main.Game;
import main.MoveReference;
import main.Piece;
import main.Area;

public class Attackable extends Action{
    private Area attackableArea;
    private int timePresent;
    public Attackable(ActionType type, String[] parameters) {
        super(type);
        if(parameters.length != 1 || parameters.length != 3){
            throw new Error("unsupported arguments for atkbl");
        } else if(parameters[1].equals("for")) {
            throw new Error("unsupported arguments for atkbl");
        }
        
        this.attackableArea = new Area(parameters[0]);
        this.timePresent = Integer.parseInt(parameters[2]);
    }

    public void executeAction(Game game, Piece piece, MoveReference move) {

    }
}
