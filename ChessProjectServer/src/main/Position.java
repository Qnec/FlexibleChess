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

    public Position(double x, double y, RelativeTo relativeTo) {
        this.x = (int)(x+0.5);
        this.y = (int)(y+0.5);
        this.relativeTo = relativeTo;
    }

    public Position(int x, int y, RelativeTo relativeTo) {
        this.x = x;
        this.y = y;
        this.relativeTo = relativeTo;
    }

    public Position(String p, RelativeTo relativeTo) {
        String[] ps = p.split(",");
        this.relativeTo = relativeTo;
        this.x = Integer.parseInt(ps[0]);
        this.y = Integer.parseInt(ps[1]);
    }

    public Position(String p) {
        this.relativeTo = Position.getRelativeTo(p.substring(0,1));
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
        return new Position(this.x + other.x, this.y + other.y, this.relativeTo);
    }

    public Position multiply(Position other) {
        return new Position(this.x*other.x, this.y*other.y, this.relativeTo);
    }

    public Position subtract(Position other) {
        return new Position(this.x - other.x, this.y - other.y, this.relativeTo);
    }

    public Position flipX() {
        return new Position(this.x, -this.y, this.relativeTo);
    }

    public Position flipY() {
        return new Position(-this.x, this.y, this.relativeTo);
    }

    public Position scale(double factor) {
        return new Position(this.x*factor, this.y*factor, this.relativeTo);
    }

    public Position flipXAbout(double n) {
        return new Position((double)this.x, n-(this.y-n), this.relativeTo);
    }

    public Position flipYAbout(double n) {
        return new Position(n-(this.x-n), (double)this.y, this.relativeTo);
    }

    public Position scaleAbout(Position other, double factor) {
        return this.subtract(other).scale(factor).add(other);
    }
}

