package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class PieceType {
    private String pieceID = null;
    public final Variable[] variables;
    public final Move[] moves;
    public final HashMap<Area, String[]> promotionAreas = new HashMap<Area, String[]>();

    private enum PieceTypeFileSections {
        CONFIG,
        VARIABLES,
        MOVES,
        NONE
    };
    public PieceType(ConfigFile conf) {
        List<Variable> varArray = new ArrayList<Variable>();
        List<Move> movesArray = new ArrayList<Move>();
        //this.pieceID = lines[0];
        for(String key : conf.configuration.keySet()) {
            switch(key) {
                case "":
                this.pieceID = conf.configuration.get(key)[0];
                break;
                case "Config":
                for(String lineStr : conf.configuration.get(key)) {
                    String[] line = lineStr.split(" ");
                    if(line[0].equals("promote") && line.length >= 3) {
                        Area a = new Area(line[1]);
                        String[] to = Arrays.copyOfRange(line, 2, line.length);
                        this.promotionAreas.put(a, to);
                        System.out.println("promotion");
                    }    
                }
                case "Variables":
                for(String lineStr : conf.configuration.get(key)) {
                    String[] line = lineStr.split(" ");
                    if(line.length == 3) {
                        varArray.add(Variable.PieceVariableInitialize(line));
                    }
                }
                break;
                case "Moves":
                for(String lineStr : conf.configuration.get(key)) {
                    movesArray.add(Move.parseMove(lineStr));
                }
                break;
                default:
                break;
            }
        }
        if(this.pieceID == null) {
            throw new Error("no piece identifier provided");
        }
        this.variables = varArray.toArray(new Variable[0]);
        this.moves = movesArray.toArray(new Move[0]);
    }

    public static PieceType readFileIntoPieceType(String filename) {
        return new PieceType(ConfigFile.parseConfigFile(filename));
    }

    public String getPieceID() {
        return this.pieceID;
    }

    @Override
    public String toString() {
        /*private String pieceID;
        private Variable[] variables;
        private Move[] moves;
        private HashMap<Area, String[]> promotionAreas; */
        return pieceID + ", " + variables.length + ", " + moves.length + ", " + promotionAreas.size();
    }
}
