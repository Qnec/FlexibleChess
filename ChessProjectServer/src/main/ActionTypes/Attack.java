package main.ActionTypes;

import main.Position;

public class Attack extends Action{
    private Position position;
    public Attack(ActionType type, String[] parameters) {
        super(type);
        this.position = new Position(parameters[0]);
    }
}
