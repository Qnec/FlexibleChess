package main.MovementTypes;

import main.Position;

public class Jump extends Movement{
    public Jump(MovementType type, Position movement, boolean interruptible) {
        super(type, movement, interruptible);
    }

    public Jump(MovementType type, String[] parameters) {
        super(type, new Position(parameters[0]), (parameters.length >= 2 && parameters[1].equals("interruptible")));
    }
}
