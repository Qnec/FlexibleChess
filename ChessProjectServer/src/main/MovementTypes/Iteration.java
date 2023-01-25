package main.MovementTypes;

import main.Position;

public class Iteration extends Movement {
    private int iterations;
    public Iteration(MovementType type, Position movement, boolean interruptible, int iterations) {
        super(type, movement, interruptible);
        this.iterations = iterations;
    }

    public Iteration(MovementType type, String[] parameters) {
        super(type, new Position(parameters[0]), (parameters.length >= 2 && parameters[1].equals("interruptible")));
        this.iterations = Integer.parseInt(parameters[2]);
    }
}
