package main;
public class Server {
    public static void main(String[] args) throws Exception {
        //System.out.println(new Position(5, 7));
        //System.out.println(new Range(5, 7));
        //System.out.println(new Range(7, 5));
        //System.out.println(new Area(new Position(5, 7), new Position( 20, 5)));
        //Move m = Move.parseMove("JUMP s1,1 ATK f0,-1 ACP f0,0 CONTONLY modern_chess_pawn VMC s.dashed == 1 VMC s.moves == 1 SET s.moves += 1");
        //PieceType p = PieceType.readFileIntoPieceType("modern_chess_pawn.pd");
        GameType g = new GameType("/home/jason/Documents/ChessProjectServerJava/modern_chess");
        System.out.println(g);
    }
}
