package main.MovementTypes;

import java.util.ArrayList;

import main.Position;

public class Iteration extends Movement {
    private int iterations;
    public Iteration(MovementType type, Position movement, boolean interruptible, int iterations) {
        super(type, movement, interruptible);
        this.iterations = iterations;
    }

    public Iteration(MovementType type, String[] parameters) {
        super(type, new Position(parameters[0]), (parameters.length >= 2 && parameters[1].equals("interruptible")));
        /*for(String i : parameters) {
            System.out.println(i);
        }*/
        if(parameters.length > 2) {
            this.iterations = Integer.parseInt(parameters[2]);
        } else {
            this.iterations = Integer.parseInt(parameters[1]);
        }

    }

    public ArrayList<Position> getPossibleMovementPositions() {
        ArrayList<Position> output = new ArrayList<>();
        for(int i = 0; i < iterations; i++) {
            output.add(this.relativePosition.scale((double)i));
        }
        return output;
    }
}
