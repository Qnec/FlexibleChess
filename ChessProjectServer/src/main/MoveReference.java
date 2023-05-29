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

    public MoveReference getTranslatedSelf(Game game, Piece piece) {
        Position finish = piece.translatePosition(game.translatePosition(this.translatePosition(this.finish), piece.getPlayer()));
        Position start = piece.translatePosition(game.translatePosition(this.translatePosition(this.start), piece.getPlayer()));
        MoveReference output = new MoveReference(start, finish, this.pieceUUID, this.moveIndex);
        return output;
    }

    public MoveReference untranslateSelf(Game game, Piece p) {
        Position finish = this.finish.subtract(p.getPosition());
        finish.relativeTo = RelativeTo.START;
        finish = game.translatePosition(finish, p.getPlayer());
        Position start = this.start.subtract(p.getPosition());
        start.relativeTo = RelativeTo.START;
        start = game.translatePosition(start, p.getPlayer());
        MoveReference output = new MoveReference(start, finish, this.pieceUUID, this.moveIndex);
        return output;
    }

    public Position translatePosition(Position p) {
        /*if(this.start.relativeTo != RelativeTo.GAME || this.finish.relativeTo != RelativeTo.GAME) {
            throw new Error("move reference positions not translated prior to use")
        }*/
        switch(p.relativeTo) {
            case START:
            p.relativeTo = this.start.relativeTo;
            return p.add(this.start);
            case FINAL:
            p.relativeTo = this.finish.relativeTo;
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
