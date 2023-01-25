package main.ActionTypes;

import java.util.Arrays;

public abstract class Action {
    public enum ActionType {
        SET,
        ATK,
        ATKBL,
        COMBI
    }
    private ActionType type;
    public Action(ActionType type) {
        this.type = type;
    }

    public ActionType getType() {
        return this.type;
    }

    public static Action parseAction(String[] str) {
        ActionType type = ActionType.valueOf(str[0]);
        switch(type) {
            case SET:
            return new Set(type, Arrays.copyOfRange(str, 1, str.length));
            case ATK:
            return new Attack(type, Arrays.copyOfRange(str, 1, str.length));
            case ATKBL:
            return new Attackable(type, Arrays.copyOfRange(str, 1, str.length));
            case COMBI:
            return new Combination(type, Arrays.copyOfRange(str, 1, str.length));
            default:
            throw new Error("not a type of action");
        }
    }

    public static boolean isAction(String str) {
        try {
            ActionType.valueOf(str);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    } 
}
