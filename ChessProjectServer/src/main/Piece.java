package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import main.Position.RelativeTo;

public class Piece {
    private String gid;
    public String pid;
    private GameType gt;
    private PieceType pt;
    private Position p;
    private int playerNum;
    private HashMap<String, Variable> variables = new HashMap<>();
    public final UUID uuid;
    private int takenBy = -1;

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
        System.out.println(output.size());
        System.out.println(output);
        return output;
    }

    public Position translatePosition(Position pos) {
        switch(pos.relativeTo) {
            case START:
            pos.relativeTo = RelativeTo.GAME;
            return pos.add(this.p);
            case GAME:
            return pos;
            case FINAL:
            default:
            throw new Error("Unknown or unhandled position relativeTo type");
        }
    }

    public int getTakenBy() {
        return this.takenBy;
    }

    public boolean take(int player) {
        if(this.takenBy != -1) {
            this.takenBy = player;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return this.gid + ", " + this.pid + ", " + this.playerNum + ", " + this.p + ", " + this.uuid;
    }
}