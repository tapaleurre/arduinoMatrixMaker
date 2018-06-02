package BMPTools;

import java.io.DataOutputStream;
import java.io.IOException;

public class DataOutputStreamLittleEndian {
    private DataOutputStream systemStream;

    public DataOutputStreamLittleEndian(DataOutputStream systemStream)
    {
        this.systemStream = systemStream;
    }

    public void close() throws IOException
    {
        this.systemStream.close();
    }

    public void write(byte[] bytesToWriteFrom) throws IOException
    {
        this.systemStream.write(bytesToWriteFrom);
    }

    public void writeInt(int valueToWrite) throws IOException
    {
        this.systemStream.writeInt(ByteOrder.reverse(valueToWrite));
    }

    public void writeLong(long valueToWrite) throws IOException
    {
        this.systemStream.writeLong(ByteOrder.reverse(valueToWrite));
    }

    public void writeShort(short valueToWrite) throws IOException
    {
        this.systemStream.writeShort(ByteOrder.reverse(valueToWrite));
    }

    public void writeString(String stringToWrite) throws IOException
    {
        this.systemStream.writeBytes(stringToWrite);
    }
}
