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

    public static Pixel getPixelFromRGBA(int r, int b, int g, int a)
    {
        return new Pixel(r, g, b, a);
    }

    public static Pixel getPixelFromHSVA(int h, int s, int v, int a)
    {
        //conversion magic
        return new Pixel(r, g, b, a);
    }

    public Pixel()
    {
        this.changedFlag = true;
    }

    public Pixel(int r, int g, int b, int a)
    {
        Pixel();
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public int getR()

    public int getG()

    public int getB()

    public int getH()
    {
        updateHSV();
        return this.h;
    }

    public int getS()

    public int getV()

    private void updateHSV()
    {
        if (this.changedFlag)
        {
            //generate hsv values
            this.changedFlag = false;
        }
    }
}
