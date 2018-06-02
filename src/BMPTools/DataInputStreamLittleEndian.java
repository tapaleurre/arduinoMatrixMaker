package BMPTools;

import java.io.DataInputStream;
import java.io.IOException;

public class DataInputStreamLittleEndian {
    private DataInputStream systemStream;

    public DataInputStreamLittleEndian(DataInputStream systemStream)
    {
        this.systemStream = systemStream;
    }

    public void close() throws IOException
    {
        this.systemStream.close();
    }

    public void read(byte[] bytesToReadInto) throws IOException
    {
        this.systemStream.read(bytesToReadInto);

        // not necessary?
        //ByteOrder.reverse(bytesToReadInto);
    }

    public int readInt() throws IOException
    {
        return ByteOrder.reverse(this.systemStream.readInt());
    }

    public long readLong() throws IOException
    {
        return ByteOrder.reverse(this.systemStream.readLong());
    }

    public short readShort() throws IOException
    {
        return ByteOrder.reverse(this.systemStream.readShort());
    }

    public String readString(int numberOfCharacters) throws IOException
    {
        byte[] bytesRead = new byte[numberOfCharacters];

        this.systemStream.read(bytesRead);

        return new String(bytesRead);
    }
}
