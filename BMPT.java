package bmpt;

import java.io.IOException;
import java.util.List;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionException;
import joptsimple.OptionSpec;

class BMPT
{
    String charSetName;
    int termWidth;

    public static void main(String[] args) throws IOException
    {
        OptionParser parser = new OptionParser();
        OptionSpec<Integer> opt_w = parser.accepts("w").withRequiredArg().ofType(Integer.class);
        OptionSpec<String> opt_c = parser.accepts("c").withRequiredArg().ofType(String.class);
        OptionSpec<String> nonOptions = parser.nonOptions("image file name").ofType(String.class).describedAs("filename");
        OptionSet options;
        try {
             options = parser.parse(args);
        }
        catch (OptionException | NullPointerException ex) {
            System.out.println(ex.getMessage());
            return;
        }
        String charSet = "default";
        int termWidth = 0;
        try
        {
            if (options.has(opt_w))
            {
                termWidth = options.valueOf(opt_w);
            }
            if (options.has(opt_c))
            {
                charSet = options.valueOf(opt_c);
            }
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage() + "\nInteger argument required");
            return;
        }
        BMPT a = new BMPT(charSet, termWidth);
        Bitmap b = new Bitmap();
        List noa = options.nonOptionArguments();
        if (noa.size() != 1)
        {
            System.out.println("Exactly one filename required.");
            return;
        }
        b.loadFromFile((String) noa.get(0));
        Pixel p = b.getPixelAt(30, 30);
        a.printBitmap(b);
    }

    public BMPT(String charSetName, int termWidth)
    {
        this.charSetName = charSetName;
        this.termWidth = termWidth;
    }

    private char[] getCharSet()
    {
        switch(this.charSetName)
        {
            case "16":
                return charSet16();
            case "32":
            case "default":
            default:
                return charSet32();
        }
    }

    private char[] charSet16()
    {
        char[] output = {'.', ',', '-', '~',
                         ':', ';', '!', '}',
                         '(', ']', '$', '#',
                         'R', 'B', 'N', 'M'};
        return output;
    }

    private char[] charSet32()
    {
        char[] output = {'-', '`', '.', ',', 185, 178, 179, 186,
                         '~', '+', 'i', 238, '=', 'c', 'u', 'v',
                         'e', 252, 251, '}', 169, 174, '$', '@',
                         '#', '2', '0', '8', 'M', 195, 202, 208};
        return output;
    }

    public void printBitmap(Bitmap bmp)
    {
        int maxWidth;
        if (this.termWidth == 0 || bmp.getImageWidth() < this.termWidth)
        {
            maxWidth = bmp.getImageWidth();
        }
        else
        {
            maxWidth = this.termWidth;
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
        char[] set = getCharSet();
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

