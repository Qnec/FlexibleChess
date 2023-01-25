package main;
public class Position {
    enum RelativeTo {
        START,
        FINAL,
        GAME
    }
    public int x,y;
    public RelativeTo relativeTo;
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
        this.relativeTo = RelativeTo.GAME;
    }

    public Position(String p) {
        Position.getRelativeTo(p.substring(0,1));
        String[] ps = p.substring(1).split(",");
        this.x = Integer.parseInt(ps[0]);
        this.y = Integer.parseInt(ps[1]);
    }
    
    public static RelativeTo getRelativeTo(String str) {
        switch(str) {
            case "s":
            return RelativeTo.START;
            case "f":
            return RelativeTo.FINAL;
            case "g":
            return RelativeTo.GAME;
            default:
            throw new Error("Unsupported relative position");
        }
    }

    @Override
    public String toString() {
        return "(" + this.x + "," + this.y + ")";
}

    public Position add(Position other) {
        return new Position(this.x + other.x, this.y + other.y);
    }
}

