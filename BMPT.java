import java.io.IOException;

class BMPT
{

    public static void main(String[] args) throws IOException
    {
        BMPT a = new BMPT();
        Bitmap b = new Bitmap();
        b.loadFromFile(args[0]);
        a.printBitmap(b, 0);
    }

    public BMPT()
    {
        //
    }

    private static char[] getCharSet(String set)
    {
        char[] output = {'.', ',', '-', '~',
                         ':', ';', '!', '}',
                         '(', ']', '$', '#',
                         'R', 'B', 'N', 'M'};
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
        char[] set = getCharSet("default");
        System.out.print(set[pixelToValue(curPixel) / (255 / (set.length))]);
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

