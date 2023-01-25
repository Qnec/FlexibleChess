package main;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class GameType {
    private static HashMap<String, GameType> gameTypes;
    private HashMap<String, PieceType> pieceTypes;
    private String gameID;
    private String pCount;
    private String nMoves;
    private String perSideMoveModificationBehav;
    private String royaltyBehav;
    private String winBehav;
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
        for(File file : pieceFileArray) {
            PieceType cpt = new PieceType(ConfigFile.parseConfigFile(file));
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
                            this.pCount= line[1];
                        } else if(line[0].equals("moves")) {
                            this.nMoves = line[1];
                        }
                    }
                }    
                case "PIECES":
                for(int i = 0; i < section.length; i++) {
                    String[] line = section[i].split(" ");
                    if(line.length <= 2) {
                        if(line[0].equals("perSideMoveModification")) {
                            this.perSideMoveModificationBehav = line[1];
                        }
                    }
                }
                case "GAME":
                for(int i = 0; i < section.length; i++) {
                    String[] line = section[i].split(" ");
                    if(line.length <= 2) {
                        if(line[0].equals("royalty")) {
                            this.royaltyBehav = line[1];
                        } else if(line[0].equals("win")) {
                            this.winBehav = line[1];
                        }
                    }
                }
                default:
                break;
            }
        }
        GameType.gameTypes.put(this.gameID, this);
    }

    @Override
    public String toString() {
        return this.pieceTypes.size() + ", " + this.gameID + ", " + this.nMoves + ", " + this.pCount + ", " + this.perSideMoveModificationBehav + ", " + this.royaltyBehav + ", " + this.winBehav;
    }

    public static GameType getGame(String gid) {
        return GameType.gameTypes.get(gid);
    }

    public PieceType getPiece(String pid) {
        return this.pieceTypes.get(pid);
    }
}
