package main;

public class Variable {
    public enum VariableScope {
        Game,
        Piece
    }
    public VariableScope scope;
    public Position variablePositionReference;
    public String variableName;
    public int value = 0;
    public Variable(VariableScope scope, String variableName) {
        this.scope = scope;
        this.variableName = variableName;
    }

    public Variable(VariableScope scope, String variableName, int value) {
        this.scope = scope;
        this.variableName = variableName;
        this.value = value;
    }

    public Variable(String parameters) {
        String[] variableParts = parameters.split("\\.");
        //System.out.println(variableParts.length);
        //for(int i = 0; i < variableParts.length; i++) {System.out.println("thing " + variableParts[i]);}
        //System.out.println(parameters);
        
        if(variableParts[0].length() > 1) {
            this.scope = VariableScope.Piece;
            this.variablePositionReference = new Position(variableParts[0]);
            
        } else {
            this.scope = Variable.getVariableScope(variableParts[0]);
            //this.variablePositionReference = new Position(0, 0, RelativeTo.START);
        }
        

        this.variableName = variableParts[1];
        //System.out.println(this.variablePositionReference);
        //System.out.println(this);
    }

    public static Variable PieceVariableInitialize(String[] parameters) {
        Variable out = new Variable(VariableScope.Piece, parameters[0]);
        out.value = Integer.parseInt(parameters[2]);
        return out;
    }

    public static VariableScope getVariableScope(String c) {
        //System.out.println(c);
        switch(c) {
            case "s":
            return VariableScope.Piece;
            case "g":
            return VariableScope.Game;
            default:
                throw new Error("Unsupported variable scope");
        }
    }

    @Override
    public String toString() {
        return this.scope.name() + ", " + this.variableName + ", at " + (this.variablePositionReference == null ? "null" : "" + this.variablePositionReference);
    }
}
