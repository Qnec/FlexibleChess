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
        NOTCONTONLY
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
        switch(this.type) {
            case CONTONLY:
            case NOTCONTONLY:
            for(Position pos : area.getEncompassingPositions()) {
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
                for(Piece p : pieces) {
                    if(
                        (this.pieceId.equals("op") && p.getPlayer() != piece.getPlayer()) || 
                        (this.pieceId.equals("self") && p.getPlayer() == piece.getPlayer()) || 
                        p.pid.equals(this.pieceId)) {
                        output = true;
                    } else {
                        output = false;
                        break;
                    }
                }
            }
            break;
            case CONT:
            case NOTCONT:
            for(Position pos : area.getEncompassingPositions()) {
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
                for(Piece p : pieces) {
                    if(
                        (this.pieceId.equals("op") && p.getPlayer() != piece.getPlayer()) || 
                        (this.pieceId.equals("self") && p.getPlayer() == piece.getPlayer()) || 
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
            case NOTCONTONLY:
            output = !output;
            default:
            break;
        }
        return output;
    }
}
