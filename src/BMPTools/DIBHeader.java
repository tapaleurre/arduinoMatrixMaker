package BMPTools;

import java.io.IOException;

public abstract class DIBHeader {

    public String name;
    public int sizeInBytes;

    public DIBHeader(String name, int sizeInBytes)
    {
        this.name = name;
        this.sizeInBytes = sizeInBytes;
    }

    public static class Instances
    {
        public static DIBHeader BitmapInfo = new DIBHeaderBitmapInfo();
        //public static DIBHeader BitmapV5 = new DIBHeaderV5();
    }

    public static DIBHeader buildFromStream(DataInputStreamLittleEndian reader)
    {
        DIBHeader returnValue = null;

        try
        {
            int dibHeaderSizeInBytes = reader.readInt();

            // hack
            if (dibHeaderSizeInBytes == 40)
            {
                returnValue = new DIBHeaderBitmapInfo().readFromStream(reader);
            }
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }

        return returnValue;
    }

    public int[] readColorTable(DataInputStreamLittleEndian reader)
    {
        // todo
        return new int[] {};
    }

    // abstract method headers

    public abstract int bitsPerPixel();
    public abstract DIBHeader readFromStream(DataInputStreamLittleEndian reader);
    public abstract int imageSizeInBytes();
    public abstract Coords imageSizeInPixels();
    public abstract void writeToStream(DataOutputStreamLittleEndian reader);
}
