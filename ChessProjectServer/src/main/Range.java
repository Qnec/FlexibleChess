package main;
public class Range {
    private int min, max;
    public Range(int v1, int v2) {
        this.min = Math.min(v1, v2);
        this.max = Math.max(v1,v2);
    }

    public Range(int v) {
        this.min = v;
        this.max = v;
    }

    public int size() {
        return (this.min-this.max)+1;
    }

    public int getMin() {
        return this.min;
    }

    public int getMax() {
        return this.max;
    }

    public boolean contains(int n) {
        return (this.min <= n && this.max >= n);
    }

    @Override
    public String toString() {
        return "from " + this.min + " to " + this.max;
    }
}
