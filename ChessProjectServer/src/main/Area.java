package main;
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
        if(area.charAt(0) == '[') {
            String[] rangeStr = area.substring(1).split("]")[0].split(",");
            r1 = new Range(Integer.parseInt(rangeStr[0]), Integer.parseInt(rangeStr[1]));
        } else {
            r1 = new Range(Integer.parseInt(area.split(",")[0]));
        }
        int splitIndex = area.indexOf(area.indexOf(','), ',');
        String secondRange = area.substring(splitIndex+1);
        if(secondRange.charAt(0) == '[') {
            String[] rangeStr = secondRange.substring(1).split("]")[0].split(",");
            r2 = new Range(Integer.parseInt(rangeStr[0]), Integer.parseInt(rangeStr[1]));
        } else {
            r2 = new Range(Integer.parseInt(secondRange.split(",")[0]));
        }
        this.x = r1;
        this.y = r2;
    }

    public boolean contains(Position p) {
        //return (this.p.y <= p.y && this.p.y+this.dimensions.y > p.y && this.p.y <= p.y && this.p.y+this.dimensions.y > p.y);
        return (this.x.contains(p.x) && this.y.contains(p.y));
    }

    @Override
    public String toString() {
        //return "from " + this.p + " to " + this.p.add(this.dimensions);
        return "x " + this.x + " and y "+ this.y;
    }
}
