package main;

import java.util.UUID;

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
}
