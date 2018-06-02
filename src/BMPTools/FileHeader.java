package BMPTools;


import java.io.IOException;

public class FileHeader {

    // 14 bytes

    public String signature;
    public int fileSize;
    public short reserved1;
    public short reserved2;
    public int fileOffsetToPixelArray;

    public FileHeader
            (
                    String signature,
                    int fileSize,
                    short reserved1,
                    short reserved2,
                    int fileOffsetToPixelArray
            )
    {
        this.signature = signature;
        this.fileSize = fileSize;
        this.reserved1 = reserved1;
        this.reserved2 = reserved2;
        this.fileOffsetToPixelArray = fileOffsetToPixelArray;
    }

    public static FileHeader readFromStream(DataInputStreamLittleEndian reader)
    {
        FileHeader returnValue = null;

        try
        {
            returnValue = new FileHeader
                    (
                            reader.readString(2), // signature
                            reader.readInt(), // fileSize,
                            reader.readShort(), // reserved1
                            reader.readShort(), // reserved2
                            reader.readInt() // fileOffsetToPixelArray
                    );
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }

        return returnValue;
    }

    public void writeToStream(DataOutputStreamLittleEndian writer)
    {
        try
        {
            writer.writeString(this.signature);
            writer.writeInt(this.fileSize);
            writer.writeShort(this.reserved1);
            writer.writeShort(this.reserved2);
            writer.writeInt(fileOffsetToPixelArray);
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    public String toString()
    {
        String returnValue =
                "<FileHeader "
                        + "signature='" + this.signature + "' "
                        + "fileSize='" + this.fileSize + "' "
                        + "fileOffsetToPixelArray ='" + this.fileOffsetToPixelArray + "' "
                        + "/>";

        return returnValue;
    }
}