import java.io.IOException;

class BMPT
{

    public static void main(String[] args) throws IOException
    {
        BMPT a = new BMPT();
        Bitmap b = new Bitmap();
        b.loadFromFile(args[0]);
        Pixel p = b.getPixelAt(30, 30);
        System.out.println(p.getR() + " " + p.getG() + " " + p.getB());
        a.printBitmap(b, 0);

    }

    public BMPT()
    {
        double v = (double)  pixelToValue(new Pixel(255,255,255,0));
        //double v = (int) 255;
        System.out.println(v / (255.0 / 31.0));
    }

    private static char[] getCharSet(String set)
    {
        switch(set)
        {
            case "16":
            case "default":
                return charSet16();
            case "32":
                return charSet32();
            default:
                return charSet16();
        }
    }

    private static char[] charSet16()
    {
        char[] output = {'.', ',', '-', '~',
                         ':', ';', '!', '}',
                         '(', ']', '$', '#',
                         'R', 'B', 'N', 'M'};
        return output;
    }

    private static char[] charSet32()
    {
        char[] output = {'-', '`', '.', ',', 185, 178, 179, 186,
                         '~', '+', 'i', 238, '=', 'c', 'u', 'v',
                         'e', 252, 251, '}', 169, 174, '$', '@',
                         '#', '2', '0', '8', 'M', 195, 202, 208};
        return output;
    }

    public void printBitmap(Bitmap bmp, int termWidth)
    {
        int maxWidth;
        if (termWidth == 0 || bmp.getImageWidth() < termWidth)
        {
            maxWidth = bmp.getImageWidth();
        }
        else
        {
            maxWidth = termWidth;
        }
        for (int y = 0; y < bmp.getImageHeight(); y++)
        {
            for (int x = 0; x < maxWidth; x++)
            {
                printPixel(bmp.getPixelAt(x, y), 1);
            }
            System.out.print("\n");
        }
    }

    private void printPixel(Pixel curPixel, int spacer)
    {
        char[] set = getCharSet("32");
        double converted = (double) pixelToValue(curPixel) / (255.0 / ((double) set.length - 1));
        System.out.print(set[(int) converted]);
        for (int i = 0; i < spacer; i++)
        {
            System.out.print(" ");
        }
    }

    private int pixelToValue(Pixel curPixel)
    {
        int h = highest(curPixel.getR(), curPixel.getG(), curPixel.getB());
        int l = lowest(curPixel.getR(), curPixel.getG(), curPixel.getB());
        return (h + l) / 2;
        //return h;
    }

    private int highest(int a, int b, int c)
    {
        if (a > b)
            if (a > c)
                return a;
            else
                return c;
        else
            if (b > c)
                return b;
            else
                return c;
    }

    private int lowest(int a, int b, int c)
    {
        if (a < b)
            if (a < c)
                return a;
            else
                return c;
        else
            if (b < c)
                return b;
            else
                return c;
    }
}

