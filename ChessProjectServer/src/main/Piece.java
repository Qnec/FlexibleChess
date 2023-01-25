package main;

import java.util.HashMap;

public class Piece {
    private String gt;
    private String pt;
    private Position p;
    private int playerNum;
    private HashMap<Variable, Integer> variables = new HashMap<Variable, Integer>();
    public Piece(String gt, String pt, int pNum, Position p) {
        this.gt = gt;
        this.pt = pt;
        this.playerNum = pNum;
        this.p = p;
        for(Variable v : GameType.getGame(this.gt).getPiece(this.pt).variables) {
            variables.put(new Variable(v.scope, v.variableName), v.value);
        }
    }
}