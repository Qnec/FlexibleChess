package main;

import java.util.ArrayList;

public class Area {
    private Range x, y;
    private Position.RelativeTo relativeTo;
    public Area(Position p, Position dimensions) {
        this.x = new Range(p.x, p.x+dimensions.x-1);
        this.y = new Range(p.y, p.y+dimensions.y-1);
    }

    public Area(String area) {
        this.relativeTo = Position.getRelativeTo(area.substring(0,1));
        area = area.substring(1);
        Range r1, r2;
        //String relativePosition = area.substring(0, 1);
        //area = area.substring(1);
        int splitIndex;
        if(area.charAt(0) == '[') {
            splitIndex = area.indexOf(',', area.indexOf(',')+1);
            String[] rangeStr = area.substring(1).split("]")[0].split(",");
            r1 = new Range(Integer.parseInt(rangeStr[0]), Integer.parseInt(rangeStr[1]));
        } else {
            splitIndex = area.indexOf(',');
            r1 = new Range(Integer.parseInt(area.split(",")[0]));
        }
        String secondRange = area.substring(splitIndex+1);
        if(secondRange.charAt(0) == '[') {
            String[] rangeStr = secondRange.substring(1).split("]")[0].split(",");
            r2 = new Range(Integer.parseInt(rangeStr[0]), Integer.parseInt(rangeStr[1]));
        } else {
            r2 = new Range(Integer.parseInt(secondRange.split(",")[0]));
        }
        //System.out.println(area);
        //System.out.println(secondRange);
        //System.out.println(r1);
        //System.out.println(r2);
        this.x = r1;
        this.y = r2;
    }

    public ArrayList<Position> getEncompassingPositions() {
        ArrayList<Position> positions = new ArrayList<Position>();
        for(int x = this.x.getMin(); x <= this.x.getMax(); x++) {
            for(int y = this.y.getMin(); y <= this.y.getMax(); y++) {
                positions.add(new Position(x,y, this.relativeTo));
            }
        }
        return positions;
    }

    public boolean contains(Position p) {
        //return (this.p.y <= p.y && this.p.y+this.dimensions.y > p.y && this.p.y <= p.y && this.p.y+this.dimensions.y > p.y);
        //System.out.println(this.x);
        //System.out.println(p.x);
        //System.out.println(this.x.contains(p.x));
        return (this.x.contains(p.x) && this.y.contains(p.y));
    }

    @Override
    public String toString() {
        //return "from " + this.p + " to " + this.p.add(this.dimensions);
        return "x " + this.x + " and y " + this.y + " relative to " + this.relativeTo.name();
    }
}
