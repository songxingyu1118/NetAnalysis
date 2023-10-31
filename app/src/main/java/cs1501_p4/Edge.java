package cs1501_p4;

public class Edge
{
    public final int COPPER_SPEED = 230000000;
    public final int OPTICAL_SPEED = 200000000;
    public STE ste;
    public String type;
    public int band;
    public int length;
    public double time;
    public int u;
    public int w;

    public Edge(int u, int w, String type, int band, int length)
    {
        this.ste = new STE(u, w);
        this.u = u;
        this.w = w;
        this.type = type;
        this.band = band;
        this.length = length;
        if(type.equals("copper")) this.time = (double)length / (double)COPPER_SPEED;
        else this.time = (double)length / (double)OPTICAL_SPEED;
    }

    public Edge()
    {
        this.ste = null;
        this.u = 0;
        this.w = 0;
        this.type = "disconnect";
        this.length = 0;
        this.band = 0;
        this.time = Double.MAX_VALUE;
    }

    public boolean isCopper() {return type.equals("copper");}

    public boolean equals(Edge e) {return this.ste.equals(e.ste);}

    public String toString() {return this.ste.toString();}
}