package main.RequirementTypes;

import java.util.HashSet;

import main.Area;
import main.Game;
import main.Move;
import main.MoveReference;
import main.Piece;
import main.Position;

public class AreaContainsPiece extends Requirement {
    enum ContainsType {
        CONT,
        CONTONLY,
        NOTCONT,
        NOTCONTONLY,
        CONTONE,
        NOTCONTONE
    }
    private Area area;
    private ContainsType type;
    private String pieceId;
    public AreaContainsPiece(RequirementType type, String[] parameters) {
        super(type);
        this.area = new Area(parameters[0]);
        this.type = ContainsType.valueOf(parameters[1]);
        this.pieceId = parameters[2];
    }

    public boolean isMet(Game game, Piece piece, Move move, MoveReference moveReference) {
        boolean output = false;
        //System.out.println("thing3");
        //System.out.println(moveReference);
        //System.out.println(this.area);
        switch(this.type) {
            case CONTONLY:
            case NOTCONTONLY:
            for(Position pos : this.area.getEncompassingPositions()) {
                pos = piece.translatePosition(
                    game.translatePosition(
                        moveReference.translatePosition(pos), piece.getPlayer()
                        )
                    );
                HashSet<Piece> pieces = game.getPieces(pos);
                if(pieces.size() > 1 && this.pieceId.equals("*")) {
                    output = true;
                    break;
                }
                //System.out.println("printing pieces");
                for(Piece p : pieces) {
                    //System.out.println(p);
                    //System.out.println(p.pid);
                    if(
                        (this.pieceId.equals("op") && p.getPlayer() != piece.getPlayer()) || 
                        (this.pieceId.equals("ally") && p.getPlayer() == piece.getPlayer()) || 
                        p.pid.equals(this.pieceId)) {
                        output = true;
                    } else {
                        output = false;
                        break;
                    }
                }
            }
            break;
            case CONTONE:
            case NOTCONTONE:
            int matches = 0;
            for(Position pos : this.area.getEncompassingPositions()) {
                pos = piece.translatePosition(
                    game.translatePosition(
                        moveReference.translatePosition(pos), piece.getPlayer()
                        )
                    );
                HashSet<Piece> pieces = game.getPieces(pos);
                if(pieces.size() > 1 && this.pieceId.equals("*")) {
                    output = false;
                    break;
                }
                for(Piece p : pieces) {
                    if(
                        (this.pieceId.equals("op") && p.getPlayer() != piece.getPlayer()) || 
                        (this.pieceId.equals("ally") && p.getPlayer() == piece.getPlayer()) || 
                        (this.pieceId.equals("*")) ||
                        p.pid.equals(this.pieceId)) {
                        matches+=1;
                    }
                }
            }
            output = (matches==1);
            break;
            case CONT:
            case NOTCONT:
            for(Position pos : this.area.getEncompassingPositions()) {
                //System.out.println("thing10");
                //System.out.println(this.area);
                //System.out.println(pos);
                pos = piece.translatePosition(
                    game.translatePosition(
                        moveReference.translatePosition(pos), piece.getPlayer()
                        )
                    );
                //System.out.println(pos);
                HashSet<Piece> pieces = game.getPieces(pos);
                if(pieces.size() >= 1 && this.pieceId.equals("*")) {
                    output = true;
                    //System.out.println(output);
                    break;
                }
                for(Piece p : pieces) {
                    if(
                        (this.pieceId.equals("op") && p.getPlayer() != piece.getPlayer()) || 
                        (this.pieceId.equals("ally") && p.getPlayer() == piece.getPlayer()) || 
                        p.pid.equals(this.pieceId)) {
                        output = true;
                        break;
                    }
                }
            }
            break;
            default:
            return false;
        }
        switch(this.type) {
            case NOTCONT:
            case NOTCONTONE:
            case NOTCONTONLY:
            output = !output;
            default:
            break;
        }
        //System.out.println(output);
        return output;
    }

    public String toString() {
        return "(" + this.area + ", " + this.type + ", " + this.pieceId + ")";
    }
}
