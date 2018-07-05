import java.io.IOException;

class BMPT
{

    public static void main(String[] args) throws IOException
    {
        BMPT a = new BMPT();
        Bitmap b = new Bitmap();
        b.loadFromFile("example.bmp");
        a.printBitmap(b, 0);
    }

    public BMPT()
    {
        //
    }

    private static char[] getCharSet(String set)
    {
        char[] output = {'.', ',', '-', '~',
                         ':', ';', '!', '/',
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
                printPixel(bmp.getPixelAt(x, y));
            }
            System.out.print("\n");
        }
    }

    private void printPixel(Pixel curPixel)
    {
        char[] set = getCharSet("default");
        System.out.print(set[pixelToValue(curPixel) / (256 / (set.length))]);
    }

    private int pixelToValue(Pixel curPixel)
    {
        return (curPixel.getR() + curPixel.getG() + curPixel.getB()) / 3;
    }
}
