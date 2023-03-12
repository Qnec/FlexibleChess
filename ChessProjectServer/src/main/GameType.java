package main;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import main.Position.RelativeTo;

public class GameType {
    enum MoveModificationBehaviorType {
        FLIP
    }

    enum RoyaltyBehavior {
        CHECK
    }

    enum WinBehavior {
        EXTINCTION
    }


    private static HashMap<String, GameType> gameTypes = new HashMap<String, GameType>();
    private HashMap<String, PieceType> pieceTypes = new HashMap<String, PieceType>();
    private HashMap<String, ArrayList<Area>> pieceStartingPositions = new HashMap<String, ArrayList<Area>>();
    public String gameID;
    public Position dimensions;
    public int pCount;
    public int nMoves;
    public MoveModificationBehaviorType perSideMoveModificationBehav;
    public RoyaltyBehavior royaltyBehav;
    public WinBehavior winBehav;
    //private 
    public GameType(String gameFolderStr) {
        Path gameFolderPath = Paths.get(gameFolderStr);
        File gameFolder = gameFolderPath.toFile();
        File[] files = gameFolder.listFiles();
        File gameDataFile = null;// = Paths.get(gameFolderPath.toString() + gameFolderPath.getFileName() + ".gd").toFile();
        ArrayList<File> pieceFileArray = new ArrayList<File>();
        for(int i = 0; i < files.length; i++) {
            String fileName = files[i].getName();
            if(fileName.equals(gameFolderPath.getFileName() + ".gd")) {
                gameDataFile = files[i];
            } else if(fileName.substring(fileName.length()-3).equals(".pd")){
                pieceFileArray.add(files[i]);
            }
        }
        //ArrayList<PieceType> pieces = new ArrayList<PieceType>();
        //this.pieceTypes = new HashMap<String, PieceType>();
        for(File file : pieceFileArray) {
            PieceType cpt = new PieceType(ConfigFile.parseConfigFile(file));
            System.out.println(cpt);
            this.pieceTypes.put(cpt.getPieceID(), cpt);
        }
        //this.pieceTypes = pieces.toArray(new PieceType[0]);
        ConfigFile gameConfig = ConfigFile.parseConfigFile(gameDataFile);
        for(String key : gameConfig.configuration.keySet()) {
            String[] section = gameConfig.configuration.get(key);
            //System.out.println(key);
            switch(key) {
                case "":
                this.gameID = section[0];
                break;
                case "PLAYERS":
                for(int i = 0; i < section.length; i++) {
                    String[] line = section[i].split(" ");
                    if(line.length <= 2) {
                        if(line[0].equals("count")) {
                            this.pCount=Integer.parseInt(line[1]);
                        } else if(line[0].equals("moves")) {
                            this.nMoves=Integer.parseInt(line[1]);
                        }
                    }
                }
                break;
                case "PIECES":
                for(int i = 0; i < section.length; i++) {
                    String[] line = section[i].split(" ");
                    if(line.length <= 2) {
                        if(line[0].equals("perSideMoveModification")) {
                            this.perSideMoveModificationBehav = MoveModificationBehaviorType.valueOf(line[1]);
                        }
                    }
                }
                break;
                case "GAME":
                for(int i = 0; i < section.length; i++) {
                    String[] line = section[i].split(" ");
                    if(line.length == 2) {
                        if(line[0].equals("royalty")) {
                            this.royaltyBehav = RoyaltyBehavior.valueOf(line[1]);
                        } else if(line[0].equals("win")) {
                            this.winBehav = WinBehavior.valueOf(line[1]);
                        } else if(line[0].equals("dimensions")) {
                            this.dimensions = new Position(line[1], RelativeTo.GAME);
                        }
                    }
                }
                break;
                case "SETUP":
                for(int i = 0; i < section.length; i++) {
                    //System.out.println(section[i]);
                    String[] line = section[i].split(" ");
                    if(line.length == 2) {
                        //System.out.println(line[1]);
                        if(!this.pieceStartingPositions.containsKey(line[0])) {
                            this.pieceStartingPositions.put(line[0], new ArrayList<Area>());
                        }
                        //System.out.println(line[0]);
                        Area a = new Area(line[1]);
                        //System.out.println(line[1]);
                        //System.out.println(a);
                        this.pieceStartingPositions.get(line[0]).add(a);
                    }
                }
                break;
                default:
                break;
            }
        }
        GameType.gameTypes.put(this.gameID, this);
    }

    @Override
    public String toString() {
        return this.dimensions + ", " + this.pieceStartingPositions.size() + ", " + this.pieceTypes.size() + ", " + this.gameID + ", " + this.nMoves + ", " + this.pCount + ", " + this.perSideMoveModificationBehav + ", " + this.royaltyBehav + ", " + this.winBehav;
    }

    public static GameType getGame(String gid) {
        return GameType.gameTypes.get(gid);
    }

    public PieceType getPiece(String pid) {
        return this.pieceTypes.get(pid);
    }

    public ArrayList<String> getPieceTypes() {
        ArrayList<String> pieceTypes = new ArrayList<String>();
        for(String key : this.pieceTypes.keySet()) {
            pieceTypes.add(key);
        }
        return pieceTypes;
    }

    public ArrayList<Area> getPieceStartingPositions(String pid) {
        if(this.pieceStartingPositions.containsKey(pid)) {
            return this.pieceStartingPositions.get(pid);
        } else {
            return new ArrayList<Area>();
        }
    }
}
