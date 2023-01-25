package main;

public class ConditionOperator {
    public enum OperatorType {
        LESSTHAN,
        GREATERTHAN,
        LESSTHANOREQUALTO,
        GREATERTHANOREQUALTO,
        EQUALTO,
        NOTEQUALTO,
        AND,
        OR,
        NAND,
        NOR,
        XOR
    }
    public OperatorType operator;
    public ConditionOperator(String operator) {
        this.operator = OperatorType.valueOf(operator);
    }

    public static OperatorType aToType(String arg) {
        switch(arg) {
            case "<":
            return OperatorType.LESSTHAN;
            case ">":
            return OperatorType.GREATERTHAN;
            case "<=":
            return OperatorType.LESSTHANOREQUALTO;
            case ">=":
            return OperatorType.GREATERTHANOREQUALTO;
            case "==":
            return OperatorType.EQUALTO;
            case "!=":
            return OperatorType.NOTEQUALTO;
            case "&&":
            return OperatorType.AND;
            case "||":
            return OperatorType.OR;
            case "^^":
            return OperatorType.XOR;    
            default:
                throw new Error("Unsupported comparison operator");
        }
    }
}
