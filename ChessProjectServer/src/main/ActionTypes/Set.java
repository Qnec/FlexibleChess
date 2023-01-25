package main.ActionTypes;

import main.Variable;

public class Set extends Action{
    enum OperatorType {
        PlusEquals,
        MinusEquals,
        Assignment
    }
    private Variable variable;
    private OperatorType operator;
    private int value;
    public Set(ActionType type, String[] parameters) {
        super(type);
        switch(parameters[1]) {
            case "+=":
            this.operator = OperatorType.PlusEquals;
            break;
            case "-=":
            this.operator = OperatorType.MinusEquals;
            break;
            case "=":
            this.operator = OperatorType.Assignment;
            break;
            default:
                throw new Error("Unsupported set operator");
        }
        
        this.variable = new Variable(parameters[0]);
        this.value = Integer.parseInt(parameters[2]);
    }
}
