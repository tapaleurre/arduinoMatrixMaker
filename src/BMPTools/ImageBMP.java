package BMPTools;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.LinkedList;

public class ImageBMP {
    public String filePath;
    public FileHeader fileHeader;
    public DIBHeader dibHeader;
    public int[] colorTable;
    public byte[] pixelData;

    public ImageBMP
            (
                    String filePath,
                    FileHeader fileHeader,
                    DIBHeader dibHeader,
                    int[] colorTable,
                    byte[] pixelData
            )
    {
        this.filePath = filePath;
        this.fileHeader = fileHeader;
        this.dibHeader = dibHeader;
        this.colorTable = colorTable;
        this.pixelData = pixelData;
    }

    public BufferedImage convertToSystemImage()
    {
        // hack
        // We're assuming things about the color model in this method
        // that may not necessarily be true in all .BMP files.

        Coords imageSizeInPixels = this.dibHeader.imageSizeInPixels();

        java.awt.image.BufferedImage returnValue;

        returnValue = new java.awt.image.BufferedImage
                (
                        imageSizeInPixels.x,
                        imageSizeInPixels.y,
                        java.awt.image.BufferedImage.TYPE_INT_ARGB
                );

        int bitsPerPixel = this.dibHeader.bitsPerPixel();
        int bytesPerPixel = bitsPerPixel / 8;
        int colorOpaqueBlackAsArgb = 0xFF << bytesPerPixel * 8;

        for (int y = 0; y < imageSizeInPixels.y; y++)
        {
            for (int x = 0; x < imageSizeInPixels.x; x++)
            {
                int bitOffsetForPixel =
                        (
                                (imageSizeInPixels.y - y - 1) // invert y
                                        * imageSizeInPixels.x
                                        + x
                        )
                                * bitsPerPixel;

                int byteOffsetForPixel = bitOffsetForPixel / 8;

                int pixelColorArgb = colorOpaqueBlackAsArgb;
                for (int b = 0; b < bytesPerPixel; b++)
                {
                    pixelColorArgb += (this.pixelData[byteOffsetForPixel + b] & 0xFF) << (8 * b);
                }

                returnValue.setRGB
                        (
                                x,
                                y,
                                pixelColorArgb
                        );
            }
        }

        return returnValue;
    }

    /**
     * Converts each pixel in a boolean list
     * One bit, one pixel, it's now monochrome.
     * Top line is stored first
     * Odd lines are stored from left to right and even lines from right to left
     * @return Boolean list containing all pixels
     */
    public LinkedList<Boolean> convertToBoolList()
    {
        /* We're assuming things about the color model in this method
        that may not necessarily be true in all .BMP files.
        */

        Coords imageSizeInPixels = this.dibHeader.imageSizeInPixels();

        LinkedList<Boolean> returnValue = new LinkedList<>();

        int bitsPerPixel = this.dibHeader.bitsPerPixel();
        int bytesPerPixel = bitsPerPixel / 8;

        for (int y = 0; y < imageSizeInPixels.y; y++)
        {
            for (int x = 0; x < imageSizeInPixels.x; x++)
            {
                int bitOffsetForPixel =
                        (
                                (imageSizeInPixels.y - y - 1) // invert y
                                        * imageSizeInPixels.x
                                        + x
                        )
                                * bitsPerPixel;

                int byteOffsetForPixel = bitOffsetForPixel / 8;

                boolean pixel = true;
                for (int b = 0; b < bytesPerPixel; b++)
                {
                    if ((this.pixelData[byteOffsetForPixel + b] & 0xFF) << (8 * b) != 0) {
                        pixel = false;
                    }
                }
                returnValue.add(pixel);
            }
        }

        return returnValue;
    }

    public static ImageBMP readFromFileAtPath(String filePathToReadFrom)
    {
        ImageBMP returnValue = null;

        try
        {
            DataInputStreamLittleEndian reader = new DataInputStreamLittleEndian
                    (
                            new DataInputStream
                                    (
                                            new FileInputStream(filePathToReadFrom)
                                    )
                    );

            FileHeader fileHeader = FileHeader.readFromStream
                    (
                            reader
                    );

            DIBHeader dibHeader = DIBHeader.buildFromStream(reader);

            int[] colorTable = dibHeader.readColorTable(reader);

            int numberOfBytesInPixelData = dibHeader.imageSizeInBytes();

            byte[] pixelData = new byte[numberOfBytesInPixelData];

            reader.read(pixelData);

            returnValue = new ImageBMP
                    (
                            filePathToReadFrom,
                            fileHeader,
                            dibHeader,
                            colorTable,
                            pixelData
                    );

            reader.close();
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }

        return returnValue;
    }

    public void writeToFileAtPath()
    {
        try
        {
            DataOutputStreamLittleEndian writer = new DataOutputStreamLittleEndian
                    (
                            new DataOutputStream
                                    (
                                            new FileOutputStream(this.filePath)
                                    )
                    );

            this.fileHeader.writeToStream(writer);

            this.dibHeader.writeToStream(writer);

            writer.write(this.pixelData);

            writer.close();
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }
}
