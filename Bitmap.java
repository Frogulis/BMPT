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
        Bitmap bmp = new Bitmap();
        bmp.loadFromFile("example.bmp");
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

    private byte[] readFile(String filename) throws IOException
    {
        Path path = Paths.get(filename);
        return Files.readAllBytes(path);
    }

    private void readPixels(byte[] bytes, int offset)
    {
        int cursor = offset;
        int padding = 4 - (imageWidth * colourDepth) % 4;
        if (padding == 4)
        {
            padding = 0;
        }
        this.pixels = new Pixel[imageWidth][];
        for (int x = 0; x < imageWidth; x++)
        {
            this.pixels[x] = new Pixel[imageHeight];
        
        }
        for (int y = imageHeight - 1; y >= 0; y--)
        {
            for (int x = 0; x < imageWidth; x++)
            {
                byte[] curPixel = subData(bytes, cursor, this.colourDepth / 8);
                this.pixels[x][y] = getPixelFromRaw(curPixel);
                cursor += this.colourDepth;
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
            output = (output << 8) | bytes[cursor];
        }
        return output;
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
