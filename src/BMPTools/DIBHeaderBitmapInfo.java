package BMPTools;

public class DIBHeaderBitmapInfo extends DIBHeader
{
    public Coords imageSizeInPixels;
    public short planes;
    public short bitsPerPixel;
    public int compression;
    public int imageSizeInBytes;
    public Coords pixelsPerMeter;
    public int numberOfColorsInPalette;
    public int numberOfColorsUsed;

    public DIBHeaderBitmapInfo()
    {
        super("BitmapInfo", 40);
    }

    public DIBHeaderBitmapInfo
            (
                    int sizeInBytes,
                    Coords imageSizeInPixels,
                    short planes,
                    short bitsPerPixel,
                    int compression,
                    int imageSizeInBytes,
                    Coords pixelsPerMeter,
                    int numberOfColorsInPalette,
                    int numberOfColorsUsed
            )
    {
        this();

        this.sizeInBytes = sizeInBytes;
        this.imageSizeInPixels = imageSizeInPixels;
        this.planes = planes;
        this.bitsPerPixel = bitsPerPixel;
        this.compression = compression;
        this.imageSizeInBytes = imageSizeInBytes;
        this.pixelsPerMeter = pixelsPerMeter;
        this.numberOfColorsInPalette = numberOfColorsInPalette;
        this.numberOfColorsUsed = numberOfColorsUsed;

        if (this.imageSizeInBytes == 0)
        {
            this.imageSizeInBytes =
                    this.imageSizeInPixels.x
                            * this.imageSizeInPixels.y
                            * this.bitsPerPixel
                            / 8;
        }
    }

    public String toString()
    {
        String returnValue =
                "<DIBHeader "
                        + "size='" + this.sizeInBytes + "' "
                        + "imageSizeInPixels='"
                        + this.imageSizeInPixels.x + ","
                        + this.imageSizeInPixels.y + "' "
                        + "planes='" + this.planes + "' "
                        + "bitsPerPixel='" + this.bitsPerPixel + "' "
                        + "compression='" + this.compression + "' "
                        + "imageSizeInBytes='" + this.imageSizeInBytes + "' "
                        + "pixelsPerMeter='"
                        + this.pixelsPerMeter.x + ","
                        + this.pixelsPerMeter.y + "' "
                        + "numberOfColorsInPalette='" + this.numberOfColorsInPalette + "' "
                        + "numberOfColorsUsed='" + this.numberOfColorsUsed + "' "
                        + "/>";

        return returnValue;
    }

    // DIBHeader members

    public int bitsPerPixel()
    {
        return this.bitsPerPixel;
    }

    public DIBHeader readFromStream(DataInputStreamLittleEndian reader)
    {
        DIBHeader dibHeader = null;

        try
        {
            dibHeader = new DIBHeaderBitmapInfo
                    (
                            this.sizeInBytes, // dibHeaderSize;
                            // imageSizeInPixels
                            new Coords
                                    (
                                            reader.readInt(),
                                            reader.readInt()
                                    ),
                            reader.readShort(), // planes;
                            reader.readShort(), // bitsPerPixel;
                            reader.readInt(), // compression;
                            reader.readInt(), // imageSizeInBytes;
                            // pixelsPerMeter
                            new Coords
                                    (
                                            reader.readInt(),
                                            reader.readInt()
                                    ),
                            reader.readInt(), // numberOfColorsInPalette
                            reader.readInt() // numberOfColorsUsed
                    );
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return dibHeader;
    }

    public int imageSizeInBytes()
    {
        return this.imageSizeInBytes;
    }

    public Coords imageSizeInPixels()
    {
        return this.imageSizeInPixels;
    }

    public void writeToStream(DataOutputStreamLittleEndian writer)
    {
        try
        {
            writer.writeInt(this.sizeInBytes);
            writer.writeInt(this.imageSizeInPixels.x);
            writer.writeInt(this.imageSizeInPixels.y);

            writer.writeShort(this.planes);
            writer.writeShort(this.bitsPerPixel);
            writer.writeInt(this.compression);
            writer.writeInt(this.imageSizeInBytes);

            writer.writeInt(this.pixelsPerMeter.x);
            writer.writeInt(this.pixelsPerMeter.y);

            writer.writeInt(numberOfColorsInPalette);
            writer.writeInt(numberOfColorsUsed);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}