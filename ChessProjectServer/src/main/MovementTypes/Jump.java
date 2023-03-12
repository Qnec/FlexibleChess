package main.MovementTypes;

import java.util.ArrayList;

import main.Position;

public class Jump extends Movement{
    public Jump(MovementType type, Position movement, boolean interruptible) {
        super(type, movement, interruptible);
    }

    public Jump(MovementType type, String[] parameters) {
        super(type, new Position(parameters[0]), (parameters.length >= 2 && parameters[1].equals("interruptible")));
    }

    public ArrayList<Position> getPossibleMovementPositions() {
        ArrayList<Position> output = new ArrayList<Position>();
        output.add(this.relativePosition);
        return output;
        
    }
}
