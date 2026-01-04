package main;

import main.UserInterface.ChessInterfaceFrame;

public class Server {
    public static ChessInterfaceFrame frame;
    //static JFrame frame = new JFrame("Chess");
    //static JLayeredPane lPane = new JLayeredPane();
    //static JPanel layer1 = new JPanel();
    //static JPanel layer2 = new JPanel();
    //static Dimension dimensions = new Dimension(1000, 1000);
    public static void main(String[] args) {
        //for(String s : args) {System.out.println(s);} //I was debugging argv because I didn't realize java discarded argv[0]
        GameType gt = new GameType(args[0]);
        frame = new ChessInterfaceFrame(gt.gameID);
        //System.out.println(new Position(5, 7));
        //System.out.println(new Range(5, 7));
        //System.out.println(new Range(7, 5));
        //System.out.println(new Area(new Position(5, 7), new Position( 20, 5)));
        //Move m = Move.parseMove("JUMP s1,1 ATK f0,-1 ACP f0,0 CONTONLY modern_chess_pawn VMC s.dashed == 1 VMC s.moves == 1 SET s.moves += 1");
        //Move m = Move.parseMove("JUMP s0,2 ACP f0,[-1,0] NOTCONT * VMC s.dashed == 0 VMC s.moves == 0 SET s.moves += 1 SET s.dashed = 1");
        //System.out.println(m);
        //PieceType p = PieceType.readFileIntoPieceType("/home/jason/Documents/Projects/FlexibleChess/modern_chess/modern_chess_pawn.pd");
        //System.out.println(p);
        //GameType gt = new GameType("/home/jason/Documents/Projects/FlexibleChess/modern_chess");
        //Game g = new Game("modern_chess");
        //System.out.println(g);
        //System.out.println(g.getAvailableMoves(g));
        //System.out.println(new Area("g[7,0],1"));
        /*Piece whitePawn = g.getPieces(new Position(2,1, RelativeTo.GAME)).iterator().next();
        Piece blackKnight = g.getPieces(new Position(1,7, RelativeTo.GAME)).iterator().next();
        Piece blackPawn = g.getPieces(new Position(1,6, RelativeTo.GAME)).iterator().next();
        Piece whiteKnight = g.getPieces(new Position(1,0, RelativeTo.GAME)).iterator().next();
        /*ArrayList<MoveReference> references = g.getAvailableMoves(blackPawn); 

        System.out.println("thing");
        System.out.println(references);
        MoveReference reference = references.get(0);
        System.out.println(reference);
        g.executeMove(g.getAvailableMoves(whitePawn).get(0));
        System.out.println(g.executeMove(reference));
        g.executeMove(g.getAvailableMoves(whitePawn).get(0));
        g.executeMove(g.getAvailableMoves(blackKnight).get(0));
        g.executeMove(g.getAvailableMoves(whitePawn).get(0));
        g.executeMove(g.getAvailableMoves(blackKnight).get(2));
        g.executeMove(g.getAvailableMoves(whitePawn).get(0));
        g.executeMove(g.getAvailableMoves(blackPawn).get(1));
        g.executeMove(g.getAvailableMoves(whiteKnight).get(0));
        g.executeMove(g.getAvailableMoves(blackKnight).get(0));
        System.out.println(g);
        g.executeMove(g.getAvailableMoves(whitePawn).get(1));
        System.out.println(g);
        */
        /*System.out.println(g);
        Piece whiteKing = g.getPieces(new Position(4,0, RelativeTo.GAME)).iterator().next();
        g.executeMove(g.getAvailableMoves(whiteKing).get(6));
        Piece blackRook = g.getPieces(new Position(0,7, RelativeTo.GAME)).iterator().next();
        g.executeMove(g.getAvailableMoves(blackRook).get(6));
        System.out.println(g);*/
        /*frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(dimensions);
        Container contentPane = frame.getContentPane();
        frame.setLayout(new BorderLayout());
        frame.add(lPane, BorderLayout.CENTER);
        lPane.setSize(dimensions);
        layer1.setSize(dimensions);
        layer2.setSize(dimensions);
        layer1.setBackground(Color.BLUE);
        layer2.setBackground(Color.GREEN);
        layer1.setOpaque(true);
        layer2.setOpaque(true);
        lPane.add(layer1, Integer.valueOf(0), 0);
        lPane.add(layer2, Integer.valueOf(1), 0);
        frame.setVisible(true);*/

        //frame.dispose();
    }
}
