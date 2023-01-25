package main;

public class Variable {
    public enum VariableScope {
        Game,
        Piece
    }
    public VariableScope scope;
    public String variableName;
    public int value = 0;
    public Variable(VariableScope scope, String variableName) {
        this.scope = scope;
        this.variableName = variableName;
    }

    public Variable(String parameters) {
        String[] variableParts = parameters.split("\\.");
        //System.out.println(variableParts.length);
        //for(int i = 0; i < variableParts.length; i++) {System.out.println("thing " + variableParts[i]);}
        this.scope = Variable.getVariableScope(variableParts[0]);
        this.variableName = variableParts[1];
    }

    public static Variable PieceVariableInitialize(String[] parameters) {
        Variable out = new Variable(VariableScope.Piece, parameters[0]);
        out.value = Integer.parseInt(parameters[2]);
        return out;
    }

    public static VariableScope getVariableScope(String c) {
        switch(c) {
            case "s":
            return VariableScope.Piece;
            case "g":
            return VariableScope.Game;
            default:
                throw new Error("Unsupported variable scope");
        }
    }
}
