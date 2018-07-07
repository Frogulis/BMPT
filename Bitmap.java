import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.Path;

class Bitmap
{
    private int colourDepth;
    private int imageWidth;
    private int imageHeight;
    private Pixel[][] pixels;

    public static void main(String[] args) throws IOException
    {
        Bitmap b = new Bitmap();
        b.test();
    }

    public Bitmap()
    {
        this.pixels = null;
    }

    public void test()
    {
        byte[] c = {(byte) 255, (byte) 0, (byte) 0, (byte) 0};
        System.out.println(bytesToInt_Little(c));
    }

    public void loadFromFile(String filename) throws IOException
    {
        /*byte[] test = {(byte) 0, (byte) 0, (byte) 0, (byte) 1};
        printBinary(test);
        System.out.println(bytesToInt(test));*/
        byte[] rawData = readFile(filename);
        this.colourDepth = extractInt(rawData, 28, 2);
        this.imageWidth = extractInt(rawData, 18, 4);
        this.imageHeight = extractInt(rawData, 22, 4);
        System.out.println(extractInt(rawData, 2, 4));
        System.out.println(this.colourDepth + " " +
            this.imageWidth + " " +
            this.imageHeight);

        int pixelsOffset = extractInt(rawData, 10, 4);
        System.out.println(pixelsOffset);
        readPixels(rawData, pixelsOffset);
    }

    public Pixel getPixelAt(int x, int y)
    {
        if (x >= this.imageWidth || y >= this.imageHeight || this.pixels == null)
        {
            throw new ArrayIndexOutOfBoundsException(x + ", " + y);
        }
        else
        {
            return this.pixels[x][y];
        }
    }

    public int getImageWidth()
    {
        return this.imageWidth;
    }

    public int getImageHeight()
    {
        return this.imageHeight;
    }

    public int getColourDepth()
    {
        return this.colourDepth;
    }

    private byte[] readFile(String filename) throws IOException
    {
        Path path = Paths.get(filename);
        return Files.readAllBytes(path);
    }

    private void readPixels(byte[] bytes, int offset)
    {
        int cursor = offset;
        int padding = 4 - (imageWidth * (colourDepth / 8)) % 4;
        if (padding == 4)
        {
            padding = 0;
        }
        this.pixels = new Pixel[imageWidth][imageHeight];
        for (int y = imageHeight - 1; y >= 0; y--)
        {
            for (int x = 0; x < imageWidth; x++)
            {
                byte[] curPixel = subData(bytes, cursor, this.colourDepth / 8);
                this.pixels[x][y] = getPixelFromRaw(curPixel);
                cursor += this.colourDepth / 8;
            }
            cursor += padding;
        }
    }

    private Pixel getPixelFromRaw(byte[] pixelData)
    {
        int r = 0;
        int g = 0;
        int b = 0;
        int a = 0;
        r = extractInt(pixelData, 0, 1);
        g = extractInt(pixelData, 1, 1);
        b = extractInt(pixelData, 2, 1);
        if (pixelData.length == 4)
        {
            a = extractInt(pixelData, 3, 1);
        }
        //System.out.println(r + " " + g + " " + b);
        return new Pixel(r, g, b, a);
    }

    private int extractInt(byte[] bytes, int offset, int length)
    {
        return bytesToInt_Little(subData(bytes, offset, length));
    }

    private byte[] subData(byte[] bytes, int offset, int length)
    {
        byte[] output = new byte[length];
        for (int cursor = offset; cursor < offset + length; cursor++)
        {
            output[cursor - offset] = bytes[cursor];
        }
        return output;
    }

    private int bytesToInt_Big(byte[] bytes)
    {
        int output = 0;
        for (int cursor = 0; cursor < bytes.length; cursor++)
        {
            output = (output >> 8) | bytes[cursor];
        }
        return output;
    }

    private int bytesToInt_Little(byte[] bytes)
    {
        int output = 0;
        for (int cursor = bytes.length - 1; cursor >= 0; cursor--)
        {
            output = (output << 8) | ((int) bytes[cursor] & 0xFF);
        }
        if (output >= 0)
        {
            return output;
        }
        else
        {
            return toTwosComplement(output);
        }
    }

    private int toTwosComplement(int n)
    {
        return ~n + 1;
    }

    private void printBinary(byte[] bytes)
    {
        for (int cursor = 0; cursor < bytes.length; cursor++)
        {
            for (int i = 7; i >= 0; i--)
            {
                int selected = bytes[cursor] & (int) Math.pow(2,i);
                if (selected == 0)
                {
                    System.out.print("0");
                }
                else
                {
                    System.out.print("1");
                }
            }
            System.out.print(".");
        }
        System.out.print("\n");
    }
}
