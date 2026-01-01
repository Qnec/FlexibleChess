package main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;

public class PieceType {
    private String pieceID = null;
    public final Variable[] variables;
    public final Move[] moves;
    public final HashMap<Area, String[]> promotionAreas = new HashMap<Area, String[]>();
    private BufferedImage pieceIconTemplate;
    private HashMap<Integer, BufferedImage> pieceIconByColor = new HashMap<>();
    private int royaltyGroup = -1;
    public PieceType(ConfigFile conf) {
        List<Variable> varArray = new ArrayList<Variable>();
        List<Move> movesArray = new ArrayList<Move>();
        //this.pieceID = lines[0];
        for(String key : conf.configuration.keySet()) {
            switch(key) {
                case "":
                this.pieceID = conf.configuration.get(key)[0];
                break;
                case "CONFIG":
                for(String lineStr : conf.configuration.get(key)) {
                    String[] line = lineStr.split(" ");
                    if(line[0].equals("promote") && line.length >= 3) {
                        Area a = new Area(line[1]);
                        String[] to = Arrays.copyOfRange(line, 2, line.length);
                        this.promotionAreas.put(a, to);
                        //System.out.println("promotion");
                    } else if(line[0].equals("icon")) {
                        try {
							System.out.println(conf.filePath.getParent().toString()+line[1]);
                            this.pieceIconTemplate = ImageIO.read(new File(conf.filePath.getParent().toString()+"/"+line[1]));
                        } catch (IOException e) {
                            throw new Error("Failed to read piece icon image from file");
                        }
                    } else if(line[0].equals("royalty") && line.length == 2) {
                        this.royaltyGroup = Integer.valueOf(line[1]);
                    }
                }
                case "VARIABLES":
                for(String lineStr : conf.configuration.get(key)) {
                    String[] line = lineStr.split(" ");
                    if(line.length == 3) {
                        varArray.add(Variable.PieceVariableInitialize(line));
                    }
                }
                break;
                case "MOVES":
                for(String lineStr : conf.configuration.get(key)) {
                    //System.out.println(lineStr);
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

    public BufferedImage getPieceIconForColor(int color) {
        if(!this.pieceIconByColor.containsKey(color)) {
            this.generatePieceIconForPlayer(color);
        }
        return this.pieceIconByColor.get(color);
    }

    public void generatePieceIconForPlayer(int color) {
        int width = this.pieceIconTemplate.getWidth();
        int height = this.pieceIconTemplate.getHeight();
        BufferedImage newIcon = new BufferedImage(width, height, this.pieceIconTemplate.getType());
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                /*int p = this.pieceIconTemplate.getRGB(x, y);
                int a = (p>>24)&0xff;
                int r = (p>>16)&0xff;
                int g = (p>>8)&0xff;
                int b = p&0xff;
                r = 255-r;
                g = 255-g;
                b = 255-b;
                p = (a<<24)|(r<<16)|(g<<8)|(b);*/
                //newIcon.setRGB(x, y, p);
                /*if(this.pieceIconTemplate.getRGB(x, y) != 0) {
                    System.out.println(x + "," + y);
                    System.out.println(this.pieceIconTemplate.getRGB(x, y));
                    System.out.println(color);
                }*/
                newIcon.setRGB(x, y, this.pieceIconTemplate.getRGB(x, y)&color);
            }
        }
        //System.out.println(this.pieceID);
        //System.out.println(t);
        //System.out.println(color);
        //System.out.println(newIcon);
        this.pieceIconByColor.put(color, newIcon);
    }

    public static PieceType readFileIntoPieceType(String filename) {
        return new PieceType(ConfigFile.parseConfigFile(filename));
    }

    public String getPieceID() {
        return this.pieceID;
    }

    public int getRoyaltyGroup() {
        return this.royaltyGroup;
    }

    @Override
    public String toString() {
        /*private String pieceID;
        private Variable[] variables;
        private Move[] moves;
        private HashMap<Area, String[]> promotionAreas; */
        return pieceID + ", " + this.variables.length + ", " + this.moves.length + ", " + this.promotionAreas.size();
    }
}
