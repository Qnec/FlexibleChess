package main.ActionTypes;

import java.util.HashSet;

import main.ConditionOperator;
import main.Game;
import main.MoveReference;
import main.Piece;
import main.Position;
import main.Variable;
import main.Variable.VariableScope;

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

    public void executeAction(Game game, Piece piece, MoveReference moveReference) {
        //System.out.println("thing5");
        //System.out.println(this.variable);
        if(this.variable.variablePositionReference == null && this.variable.scope == VariableScope.Piece) {
            //System.out.println("thing4.1");
            Variable v = piece.getVariable(this.variable.variableName);
            if(v != null) {
                v.value = Set.evaluate(this.operator, v.value, this.value);
                return;
            }
        }
        Position variablePositionReference = piece.translatePosition(
            game.translatePosition(
                moveReference.translatePosition(this.variable.variablePositionReference.getCopy()), piece.getPlayer()
                )
            );
        //System.out.println(moveReference.start);
        //System.out.println(variablePositionReference);
        HashSet<Piece> set = game.getPieces(variablePositionReference);
        //int value = 0;
        Variable v = new Variable(VariableScope.Piece, "default", 0);
        Piece variablePiece = null;
        if(this.variable.scope == VariableScope.Game) {
            //v = game.getVariable(this.variable.variableName);
            System.out.println("if this triggers bad");
        } else {
            if(set.size() != 1) {
                throw new Error("More than one piece in area specified for variable by position");
            } else {
                for(Piece p: set) {
                    variablePiece = p;
                    v = p.getVariable(this.variable.variableName);
                    if(v == null) {
                        throw new Error("piece does not have such variable");
                    }
                }
            }
        }
        
        //System.out.println(variablePiece.getVariable(this.variable.variableName));
        v.value = Set.evaluate(this.operator, v.value, this.value);
        //System.out.println((this.operator == null) + ", " + v.value + ", " + this.value);
    }

    public static int evaluate(OperatorType operator, int value1, int value2) {
        switch(operator) {
            case PlusEquals:
                return value1+value2;
            case MinusEquals:
                return value1-value2;
            case Assignment:
                return value2;
            default:
                throw new Error("Unhandled set operator");
        }
    }
}
