package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class Game {
    private HashMap<UUID, Piece> pieceByUUID = new HashMap<UUID, Piece>();
    private String gid;
    GameType gt;
    private ArrayList<ArrayList<Piece>> pieces = new ArrayList<ArrayList<Piece>>();
    private ArrayList<String> pieceTypes = new ArrayList<String>();
    private ArrayList<ArrayList<HashSet<Piece>>> grid = new ArrayList<>();

    public Game(String gameTypeID) {
        this.gid = gameTypeID;
        this.gt = GameType.getGame(gid);
        this.pieceTypes = gt.getPieceTypes();
        for(int x = 0; x < gt.dimensions.x; x++) {
            this.grid.add(new ArrayList<HashSet<Piece>>());
            for(int y = 0; y < gt.dimensions.y; y++) {
                this.grid.get(x).add(new HashSet<Piece>());

            }
        }
        this.setupPieces();
    }

    public boolean addPiece(Piece p) {
        if(pieceByUUID.containsKey(p.uuid)) {
            return false;
        }
        int player = p.getPlayer();
        pieces.get(player).add(p);
        this.grid.get(p.getPosition().x).get(p.getPosition().y).add(p);
        return true;
    }

    public void movePiece(Piece p, Position newPos) {
        Position initPosition = p.getPosition();
        this.grid.get(initPosition.x).get(initPosition.y).remove(p);
        p.setPosition(newPos);
        this.grid.get(p.getPosition().x).get(p.getPosition().y).add(p);
    }

    public ArrayList<MoveReference> getAvailableMoves(int pNum) {
        ArrayList<MoveReference> output = new ArrayList<>();
        for(Piece p : pieces.get(pNum)) {
            System.out.println(p);
            output.addAll(this.getAvailableMoves(pNum, p));
        }
        return output;
    }
    
    public ArrayList<MoveReference> getAvailableMoves(int pNum, Piece p) {
        return p.getAvailableMoves(this);
    }

    public HashSet<Piece> getPieces(Position p) {
        if(p.x < 0 || p.x >= this.gt.dimensions.x || p.y < 0 || p.y >= this.gt.dimensions.y) {
            return new HashSet<Piece>();
        } else {
            return this.grid.get(p.x).get(p.y);

        }
    }

    public HashSet<Piece> getAttackables(Position p) {
        if(p.x < 0 || p.x >= this.gt.dimensions.x || p.y < 0 || p.y >= this.gt.dimensions.y) {
        } else {
            
        }
        return new HashSet<Piece>();
    }

    public Position translatePosition(Position pos, int pNum) {
        switch(this.gt.perSideMoveModificationBehav) {
            case FLIP:
            if(pNum == 0) {
                return pos;
            } else if(pNum == 1) {
                switch(pos.relativeTo) {
                    case GAME:
                    return pos.flipXAbout(((double)gt.dimensions.y/2.0)-0.5);
                    case START:
                    case FINAL:
                    return pos.flipX();
                }
            }
            break;
            default:
            throw new Error("Unknown per side move modification type");    
        }
        return new Position(0,0);
    }

    /*public Position translatePosition(Position pos) {

    }*/

    public void setupPieces() {
        GameType gt = GameType.getGame(gid);
        this.pieceTypes = gt.getPieceTypes();
        for(int player = 0; player < gt.pCount; player++) {
            pieces.add(new ArrayList<Piece>());
            for(String pid : pieceTypes) {
                //System.out.println(pid);
                for(Area a : gt.getPieceStartingPositions(pid)) {
                    //System.out.println(a);
                    for(Position p: a.getEncompassingPositions()) {
                        //System.out.println(p);
                        //pieces.get(player).add(new Piece(this.gid, pid, player, this.translatePosition(p, player)));
                        boolean success = false;
                        do {
                            success = this.addPiece(new Piece(this.gid, pid, player, this.translatePosition(p, player)));
                        } while(!success);
                    }
                }
            }    
        }
    }
    
    @Override
    public String toString() {
        /*Position dimensions = GameType.getGame(this.gid).dimensions;
        ArrayList<ArrayList<Integer>> grid = new ArrayList<ArrayList<Integer>>();
        for(int y = 0; y < dimensions.y; y++) {
            grid.add(new ArrayList<Integer>());
            for(int x = 0; x < dimensions.x; x++) {
                grid.get(y).add(0);
            }
        }
        for(ArrayList<Piece> playerArray : this.pieces) {
            for(Piece p: playerArray) {
                Position pp = p.getPosition();
                //System.out.println(pp);
                Integer count = grid.get(pp.y).get(pp.x);
                count++;
                grid.get(pp.y).remove(pp.x);
                grid.get(pp.y).add(pp.x, count);
            }    
        }*/
        String output = "";
        for(ArrayList<HashSet<Piece>> column: this.grid) {
            for(HashSet<Piece> count: column) {
                output+=count.size();
            }
            output+="\n";
        }
        return output;
    }
}
