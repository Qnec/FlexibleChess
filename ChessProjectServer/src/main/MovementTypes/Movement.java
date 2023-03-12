package main.MovementTypes;

import main.Position;

import java.util.ArrayList;
import java.util.Arrays;
//import java.util.Arrays.copyofrange;

public abstract class Movement {
    public enum MovementType {
        ITER,
        JUMP
    }
    protected MovementType type;
    protected Position relativePosition;
    protected boolean interruptible;
    public Movement(MovementType type, Position relativePosition, boolean interruptible) {
        this.type = type;
        this.relativePosition = relativePosition;
        this.interruptible = interruptible;
    }

    public static Movement parseMovement(String[] str) {
        MovementType type = MovementType.valueOf(str[0]);
        switch(type) {
            case ITER:
            return new Iteration(type, Arrays.copyOfRange(str, 1, str.length));
            case JUMP:
            return new Jump(type, Arrays.copyOfRange(str, 1, str.length));
            default:
            throw new Error("not a type of movement");
        }
    }

    public static boolean isMovement(String str) {
        try {
            MovementType.valueOf(str);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    } 

    public abstract ArrayList<Position> getPossibleMovementPositions();
}
