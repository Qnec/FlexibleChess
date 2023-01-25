package main.RequirementTypes;

import main.ConditionOperator;
import main.Variable;

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
}
