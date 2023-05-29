package main.RequirementTypes;

import java.util.HashSet;

import main.ConditionOperator;
import main.Game;
import main.Move;
import main.MoveReference;
import main.Piece;
import main.Position;
import main.Variable;
import main.Position.RelativeTo;
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

    public boolean isMet(Game game, Piece piece, Move move, MoveReference moveReference) {
        //System.out.println("thing4");
        //System.out.println(game);
        //System.out.println(moveReference);
        //System.out.println(move);
        //System.out.println("thing4.2");
        //System.out.println(this.variable);
        //System.out.println("thing4.3");
        //System.out.println(this.variable.scope.name());
        //System.out.println(this.variable.variableName);

        if(this.variable.variablePositionReference == null && this.variable.scope == VariableScope.Piece) {
            //System.out.println("thing4.1");
            Variable v = piece.getVariable(this.variable.variableName);
            if(v != null) {
                if(v.variableName.equals("deltaTurn")) {
                    v.value = game.getTurn()-v.value;
                }
                return ConditionOperator.evaluate(this.operator, v.value, this.value);
            }
        }
        Position variablePositionReference = piece.translatePosition(
            game.translatePosition(
                moveReference.translatePosition(this.variable.variablePositionReference.getCopy()), piece.getPlayer()
                )
            );
        //System.out.println(variablePositionReference);
        HashSet<Piece> set = game.getPieces(variablePositionReference);
        //int value = 0;
        Variable v = new Variable(VariableScope.Piece, "default", 0);
        if(this.variable.scope == VariableScope.Game) {
            //v = game.getVariable(this.variable.variableName);
            //System.out.println("if this triggers bad");
        } else {
            //System.out.println(set);
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
        //System.out.println(this.operator + ", " + v.value + ", " + this.value);
        if(v.variableName.equals("deltaTurn")) {
            v.value = game.getTurn()-v.value;
        }
        if(v.variableName.equals("y")) {
            v.value = game.translateWithPerSideMoveModification(new Position(0,v.value, RelativeTo.GAME), piece.getPlayer()).y;
        }
        return ConditionOperator.evaluate(this.operator, v.value, this.value);
        //return false;
    }
}
