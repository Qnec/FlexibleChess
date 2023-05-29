package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class Game {
    private HashMap<UUID, Piece> pieceByUUID = new HashMap<UUID, Piece>();
    private String gid;
    public GameType gt;
    private ArrayList<ArrayList<Piece>> pieces = new ArrayList<ArrayList<Piece>>();
    //sprivate ArrayList<String> pieceTypes = new ArrayList<String>();
    private ArrayList<ArrayList<HashSet<Piece>>> grid = new ArrayList<>();
    
    private ArrayList<ArrayList<HashSet<AttackableReference>>> attackablesGrid = new ArrayList<>();

    private HashMap<Integer, Integer> royaltyCountByPlayer = new HashMap<>();
    
    private int turnNumber = 0;

    public Game(String gameTypeID) {
        this.gid = gameTypeID;
        this.gt = GameType.getGame(gid);
        //this.pieceTypes = gt.getPieceTypes();
        for(int x = 0; x < gt.dimensions.x; x++) {
            this.grid.add(new ArrayList<HashSet<Piece>>());
            this.attackablesGrid.add(new ArrayList<HashSet<AttackableReference>>());
            for(int y = 0; y < gt.dimensions.y; y++) {
                this.grid.get(x).add(new HashSet<Piece>());
                this.attackablesGrid.get(x).add(new HashSet<AttackableReference>());
            }
        }
        this.setupPieces();
    }

    public ArrayList<ArrayList<Piece>> getPieceArrayByPlayer() {
        return this.pieces;
    }

    public boolean executeMove(MoveReference moveReference) {
        //System.out.println(moveReference);
        Piece piece = this.getPieceByUUID(moveReference.pieceUUID);
        //System.out.println(piece);
        //System.out.println(moveReference.pieceUUID);
        //System.out.println(this.pieceByUUID);
        //System.out.println(this);
        MoveReference untranslatedReference = moveReference.untranslateSelf(this, piece);
        
        if(this.getCurrentPlayer() == piece.getPlayer()) {
            boolean output = piece.executeMove(this, untranslatedReference);
            this.incrementToNextPlayer();
            return output;
        } else {
            return false;
        }
    }

    public void incrementToNextPlayer() {
        ArrayList<Integer> remainingPlayers = this.getRemainingPlayers();
        do {
            this.turnNumber++;
            //System.out.println(remainingPlayers);
            //System.out.println(this.getCurrentPlayer());
            //System.out.println(this.turnNumber);
            //break;
        } while(!remainingPlayers.contains(this.getCurrentPlayer()));
    }

    public int getTurn() {
        return this.turnNumber;
    }

    public int getCurrentPlayer() {
        return this.turnNumber%2;
    }

    public void decrementRoyaltyCount(int player) {
        this.royaltyCountByPlayer.put(player, this.royaltyCountByPlayer.get(player)-1);
        //this.getWinner();
    }

    public ArrayList<Integer> getRemainingPlayers() {
        ArrayList<Integer> output = new ArrayList<>();
        for(Integer player : this.royaltyCountByPlayer.keySet()) {
            System.out.println(this.royaltyCountByPlayer);
            if(this.royaltyCountByPlayer.get(player) > 0) {
                output.add(player);
            }
        }
        return output;
    }

    public int getWinner() {
        //System.out.println(this.royaltyCountByPlayer);
        ArrayList<Integer> remainingPlayers = this.getRemainingPlayers();
        if(remainingPlayers.size()>1) {
            return -1;
        } else {
            return remainingPlayers.get(0);
        }
    }

    public boolean addPiece(Piece p) {
        if(pieceByUUID.containsKey(p.uuid)) {
            return false;
        }
        int player = p.getPlayer();
        pieces.get(player).add(p);
        this.grid.get(p.getPosition().x).get(p.getPosition().y).add(p);
        this.pieceByUUID.put(p.uuid, p);
        return true;
    }

    public void addAttackableReference(AttackableReference reference, Position p) {
        this.attackablesGrid.get(p.x).get(p.y).add(reference);
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
            //System.out.println(p);
            output.addAll(this.getAvailableMoves(p));
        }
        return output;
    }

    public ArrayList<MoveReference> getAvailableMoves(Position pos) {
        ArrayList<MoveReference> output = new ArrayList<>();
        for(Piece p : this.getPieces(pos)) {
            //System.out.println(p);
            output.addAll(this.getAvailableMoves(p));
        }
        return output;
    }
    
    public ArrayList<MoveReference> getAvailableMoves(Piece p) {
        if(p.getPlayer() == this.getCurrentPlayer()) {
            return p.getAvailableMoves(this);
        } else {
            return new ArrayList<>();
        }
    }

    public HashSet<Piece> getPieces(Position p) {
        if(p.x < 0 || p.x >= this.gt.dimensions.x || p.y < 0 || p.y >= this.gt.dimensions.y) {
            return new HashSet<Piece>();
        } else {
            return this.grid.get(p.x).get(p.y);
        }
    }

    public Piece getPieceByUUID(UUID uuid) {
        return this.pieceByUUID.get(uuid);
    }

    public HashSet<AttackableReference> getAttackableReferences(Position p) {
        if(p.x < 0 || p.x >= this.gt.dimensions.x || p.y < 0 || p.y >= this.gt.dimensions.y) {
            return new HashSet<AttackableReference>();
        } else {
            return this.attackablesGrid.get(p.x).get(p.y);
        }
    }

    public HashSet<Piece> getAttackables(Position pos) {
        if(pos.x < 0 || pos.x >= this.gt.dimensions.x || pos.y < 0 || pos.y >= this.gt.dimensions.y) {
            return new HashSet<Piece>();
        }
        HashSet<Piece> output = new HashSet<Piece>();
        HashSet<Piece> gridPieces = this.getPieces(pos);
        for(Piece p : gridPieces) {
            if(p.getTakenBy() == -1) {
                output.add(p);
            }
        }
        HashSet<AttackableReference> references = this.getAttackableReferences(pos);
        for(AttackableReference reference : references) {
            Piece piece = this.getPieceByUUID(reference.referencedPiece);
            if(reference.hasExpired(this.turnNumber) && piece.getTakenBy() == -1) {
                //System.out.println("thing9");
                output.add(piece);
            }
        }
        return output;
    }

    public Position translateWithPerSideMoveModification(Position pos, int pNum) {
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

    public Position translatePosition(Position pos, int pNum) {
        switch(this.gt.perSideMoveModificationBehav) {
            case FLIP:
            if(pNum == 0) {
                return pos;
            } else if(pNum == 1) {
                switch(pos.relativeTo) {
                    case GAME:
                    return pos;
                    //return pos.flipXAbout(((double)gt.dimensions.y/2.0)-0.5);
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
        //this.pieceTypes = gt.getPieceTypes();
        for(int player = 0; player < gt.pCount; player++) {
            pieces.add(new ArrayList<Piece>());
            this.royaltyCountByPlayer.put(player, 0);
            for(String pieceSetupString : this.gt.pieceStartingPositions.keySet()) {
                //System.out.println("thing" + pieceSetupString);
                String[] pieceSetupArray = pieceSetupString.split(" ");
                String pid = pieceSetupArray[0];
                for(Area a : gt.getPieceStartingPositions(pieceSetupString)) {
                    //System.out.println(a);
                    for(Position p: a.getEncompassingPositions()) {
                        //System.out.println(pid);
                        //pieces.get(player).add(new Piece(this.gid, pid, player, this.translatePosition(p, player)));
                        boolean success = false;
                        do {
                            Piece piece = new Piece(this.gid, pid, player, this.translateWithPerSideMoveModification(p, player));
                            for(int variableIterator = 1; variableIterator < pieceSetupArray.length; variableIterator+=2) {
                                piece.getVariable(pieceSetupArray[variableIterator]).value = Integer.valueOf(pieceSetupArray[variableIterator+1]);
                            }
                            success = this.addPiece(piece);
                            if(success && piece.getPieceType().getRoyaltyGroup() != -1) {
                                this.royaltyCountByPlayer.put(player, this.royaltyCountByPlayer.get(player)+1);
                            }
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
                int n = 0;
                for(Piece p : count) {
                    if(p.getTakenBy() == -1) {
                        n+=1;
                    }
                }
                output+=n;
            }
            output+="\n";
        }
        return output;
    }
}
