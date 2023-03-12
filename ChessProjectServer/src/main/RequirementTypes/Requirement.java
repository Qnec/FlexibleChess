package main.RequirementTypes;

import java.util.Arrays;

import main.Game;
import main.Move;
import main.Piece;

public abstract class Requirement {
    public enum RequirementType {
        ACP,
        VMC
    }
    private RequirementType type;
    public Requirement(RequirementType type) {
        this.type = type;
    }

    public RequirementType getType() {
        return this.type;
    }

    public static Requirement parseRequirement(String[] str) {
        RequirementType type = RequirementType.valueOf(str[0]);
        switch(type) {
            case ACP:
            return new AreaContainsPiece(type, Arrays.copyOfRange(str, 1, str.length));
            case VMC:
            return new VariableMeetsCondition(type, Arrays.copyOfRange(str, 1, str.length));
            default:
            throw new Error("not a type of requirement");
        }
    }

    public static boolean isReqirement(String str) {
        try {
            RequirementType.valueOf(str);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public abstract boolean isMet(Game game, Piece piece, Move move);
}
