package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import main.Position.RelativeTo;
import main.Variable.VariableScope;

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
    private int lastTurn = -1;
    private boolean needsToBePromoted = false;
    private Area promotionArea = null;

    public Piece(String gt, String pt, int pNum, Position p) {
        this.uuid = UUID.randomUUID();
        this.gid = gt;
        this.pid = pt;
        this.playerNum = pNum;
        this.p = p;
        this.gt = GameType.getGame(this.gid);
        this.pt = this.gt.getPiece(this.pid);
        this.variables = new HashMap<String, Variable>();
        for(Variable v : this.pt.variables) {
            variables.put(v.variableName, new Variable(v.scope, v.variableName, v.value));
        }
    }

    public PieceType getPieceType() {
        return this.pt;
    }

    public void setPieceType(PieceType pt) {
        this.pt = pt;
        this.variables = new HashMap<String, Variable>();
        for(Variable v : this.pt.variables) {
            variables.put(v.variableName, new Variable(v.scope, v.variableName, v.value));
        }
    }

    public Area findContainingPromotionArea() {
        for(Area area : this.pt.promotionAreas.keySet()) {
            if(area.contains(this.p)) {
                return area;
            }
        }
        return null;
    }

    public Position getPosition() {
        return this.p;
    }

    public Variable getVariable(String variableName) {
        if(variableName.equals("deltaTurn")) {
            Variable v = new Variable(VariableScope.Piece, variableName);
            v.value = this.lastTurn;
            return v;
        } else if(variableName.equals("x")) {
            Variable v = new Variable(VariableScope.Piece, variableName);
            v.value = this.p.x;
            return v;
        } else if(variableName.equals("y")) {
            Variable v = new Variable(VariableScope.Piece, variableName);
            v.value = this.p.y;
            return v;
        }
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
        //System.out.println(output.size());
        //System.out.println(output);
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
        if(this.takenBy == -1 && player != this.getPlayer()) {
            this.takenBy = player;
            return true;
        }
        return false;
    }

    public boolean executeMove(Game game, MoveReference moveReference) {
        //System.out.println("thing6");
        Move move = this.pt.moves[moveReference.moveIndex];
        if(move.isMoveValid(game, this, move, moveReference)) {
            if(move.executeMove(game, this, move, moveReference)) {
                this.lastTurn = game.getTurn();
                Area promotionArea = this.findContainingPromotionArea();
                if(promotionArea != null) {
                    this.needsToBePromoted = true;
                    this.promotionArea = promotionArea;
                }
                return true;
            }
        }
        return false;
    }

    public String[] getPromotionPossibilities() {
        if(this.pt.promotionAreas.containsKey(this.promotionArea)) {
            return this.pt.promotionAreas.get(this.promotionArea);
        } else {
            String[] output = {};
            return output;
        }
    }

    @Override
    public String toString() {
        return this.gid + ", " + this.pid + ", " + this.playerNum + ", " + this.p + ", " + this.uuid;
    }
}
