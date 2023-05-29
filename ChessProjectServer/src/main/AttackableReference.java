package main;

import java.util.UUID;

public class AttackableReference {
    public final UUID referencedPiece;
    public final int instantiatedOnTurn;
    public final int expiresAfter;

    public AttackableReference(UUID referencedPiece) {
        this.referencedPiece = referencedPiece;
        this.instantiatedOnTurn = 0;
        this.expiresAfter = -1;
    }

    public AttackableReference(UUID referencedPiece, int expiresAfter) {
        this.referencedPiece = referencedPiece;
        this.instantiatedOnTurn = 0;
        this.expiresAfter = expiresAfter;
    }
    
    public AttackableReference(UUID referencedPiece, int instantiatedOnTurn, int expiresAfter) {
        this.referencedPiece = referencedPiece;
        this.instantiatedOnTurn = instantiatedOnTurn;
        this.expiresAfter = expiresAfter;
    }

    public boolean hasExpired(int currentTurn) {
        return this.expiresAfter != -1 && this.instantiatedOnTurn+this.expiresAfter <= currentTurn;
    }
}