package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Piece {
    private String gid;
    public String pid;
    private GameType gt;
    private PieceType pt;
    private Position p;
    private int playerNum;
    private HashMap<String, Variable> variables = new HashMap<>();
    public final UUID uuid;

    public Piece(String gt, String pt, int pNum, Position p) {
        this.uuid = UUID.randomUUID();
        this.gid = gt;
        this.pid = pt;
        this.playerNum = pNum;
        this.p = p;
        this.gt = GameType.getGame(this.gid);
        this.pt = this.gt.getPiece(this.pid);
        for(Variable v : this.pt.variables) {
            variables.put(v.variableName, new Variable(v.scope, v.variableName, v.value));
        }
    }

    public Position getPosition() {
        return this.p;
    }

    public Variable getVariable(String variableName) {
        return this.variables.get(variableName);
    }

    public int getPlayer() {
        return this.playerNum;
    }

    public void setPosition(Position p) {
        this.p = p;
    }

    public ArrayList<MoveReference> getAvailableMoves(Game game) {
        ArrayList<MoveReference> output = new ArrayList<MoveReference>();
        for(int i = 0; i < this.pt.moves.length; i++) {
            Move move = this.pt.moves[i];
            output.addAll(move.getValidMoveReferences(game, this, i));
        }
        return output;
    }

    public Position translatePosition(Position p) {
        switch(p.relativeTo) {
            case START:
            case FINAL:
            case GAME:
            default:
            throw new Error("Unknown position relativeTo type");
        }
    }
}