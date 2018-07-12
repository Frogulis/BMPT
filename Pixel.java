package bmpt;

class Pixel
{
    private int r;
    private int g;
    private int b;
    private int a; //for the future
    private int h;
    private int s;
    private int v;
    private boolean changedFlag;

    /*public static Pixel getPixelFromRGBA(int r, int b, int g, int a)
    {
        return new Pixel(r, g, b, a);
    }

    /*public static Pixel getPixelFromHSVA(int h, int s, int v, int a)
    {
        //conversion magic
        return new Pixel(r, g, b, a);
    }*/

    public Pixel(int r, int g, int b, int a)
    {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
        updateHSV();
    }

    public int getR()
    {
        return this.r;
    }

    public int getG()
    {
        return this.g;
    }

    public int getB()
    {
        return this.b;
    }

    public int getA()
    {
        return this.a;
    }

    public int getH()
    {
        return this.h;
    }

    public int getS()
    {
        return this.s;
    }

    public int getV()
    {
        return this.v;
    }

    private void updateHSV()
    {
        //generate hsv from rgb
    }
}
