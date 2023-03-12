package main.RequirementTypes;

import java.util.HashSet;

import main.ConditionOperator;
import main.Game;
import main.Move;
import main.Piece;
import main.Variable;
import main.Variable.VariableScope;

public class VariableMeetsCondition extends Requirement {
    private Variable variable;
    private ConditionOperator.OperatorType operator;
    private int value;
    public VariableMeetsCondition(RequirementType type, String[] parameters) {
        super(type);
        this.variable = new Variable(parameters[0]);
        this.operator = ConditionOperator.aToType(parameters[1]);
        this.value = Integer.parseInt(parameters[2]);
    }

    public boolean isMet(Game game, Piece piece, Move move) {
        HashSet<Piece> set = game.getPieces(game.translatePosition(this.variable.variablePositionReference, piece.getPlayer()));
        //int value = 0;
        Variable v = new Variable(VariableScope.Piece, "default", 0);
        if(this.variable.scope == VariableScope.Game) {
            //v = game.getVariable(this.variable.variableName);
            System.out.println("if this triggers bad");
        } else {
            if(set.size() != 1) {
                return false;
            } else {
                for(Piece p: set) {
                    v = p.getVariable(this.variable.variableName);
                    if(v == null) {
                        return false;
                    }
                }
            }
        }
        System.out.println((this.operator == null) + ", " + v.value + ", " + this.value);
        return ConditionOperator.evaluate(this.operator, v.value, this.value);
        //return false;
    }
}
