package main.ActionTypes;

import main.Position;

public class Combination extends Action{
    private Position initialPosition;
    private Position finalPosition;
    public Combination(ActionType type, String[] parameters) {
        super(type);
        if(parameters.length != 1 || parameters.length != 3){
            throw new Error("unsupported arguments for combi");
        } else if(parameters[1].equals("to")) {
            throw new Error("unsupported arguments for combi");
        }
        
        this.initialPosition = new Position(parameters[0]);
        this.finalPosition = new Position(parameters[2]);
    }
}
