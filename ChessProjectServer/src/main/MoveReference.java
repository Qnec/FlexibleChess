package main;

import java.util.UUID;

import main.Position.RelativeTo;

public class MoveReference {
    public Position start;
    public Position finish;
    public UUID pieceUUID;
    public int moveIndex;
    
    public MoveReference(Position start, Position finish, UUID pieceUUID, int moveIndex) {
        this.start = start;
        this.finish = finish;
        this.pieceUUID = pieceUUID;
        this.moveIndex = moveIndex;
    }

    public void translateSelf(Game game, Piece piece) {
        this.finish = piece.translatePosition(game.translatePosition(this.translatePosition(this.finish), piece.getPlayer()));
        this.start = piece.translatePosition(game.translatePosition(this.translatePosition(this.start), piece.getPlayer()));
    }

    public Position translatePosition(Position p) {
        switch(p.relativeTo) {
            case START:
            return p.add(this.start);
            case FINAL:
            p.relativeTo = RelativeTo.START;
            return p.add(this.finish);
            case GAME:
            return p;
            default:
            throw new Error("Unknown position relativeTo type");
        }
    }


    @Override
    public String toString() {
        return "(" + this.start + ", " + this.finish + ", " + this.moveIndex + ", " + this.pieceUUID + ")";
    }
}
