package BMPTools;

public class ByteOrder
{
    public static void reverse(byte[] bytesToConvert)
    {
        int numberOfBytes = bytesToConvert.length;
        int numberOfBytesHalf = numberOfBytes / 2;

        for (int b = 0; b < numberOfBytesHalf; b++)
        {
            byte byteFromStart = bytesToConvert[b];
            bytesToConvert[b] = bytesToConvert[numberOfBytes - 1 - b];
            bytesToConvert[numberOfBytes - 1 - b] = byteFromStart;
        }
    }

    public static int reverse(int intToReverse)
    {
        byte[] intAsBytes = new byte[]
                {
                        (byte)(intToReverse & 0xFF),
                        (byte)((intToReverse >> 8 ) & 0xFF),
                        (byte)((intToReverse >> 16) & 0xFF),
                        (byte)((intToReverse >> 24) & 0xFF),
                };

        intToReverse =
                (
                        (intAsBytes[3] & 0xFF)
                                + ((intAsBytes[2] & 0xFF) << 8 )
                                + ((intAsBytes[1] & 0xFF) << 16)
                                + ((intAsBytes[0] & 0xFF) << 24)
                );

        return intToReverse;
    }

    public static long reverse(long valueToReverse)
    {
        byte[] valueAsBytes = new byte[]
                {
                        (byte)(valueToReverse & 0xFF),
                        (byte)((valueToReverse >> 8 ) & 0xFF),
                        (byte)((valueToReverse >> 16) & 0xFF),
                        (byte)((valueToReverse >> 24) & 0xFF),
                        (byte)((valueToReverse >> 32) & 0xFF),
                        (byte)((valueToReverse >> 40) & 0xFF),
                        (byte)((valueToReverse >> 48 ) & 0xFF),
                        (byte)((valueToReverse >> 56) & 0xFF),
                };

        long returnValue = (valueAsBytes[7] & 0xFF);
        returnValue += ((valueAsBytes[6] & 0xFF) << 8 );
        returnValue += ((valueAsBytes[5] & 0xFF) << 16);
        returnValue += ((valueAsBytes[4] & 0xFF) << 24);
        returnValue += ((valueAsBytes[3] & 0xFF) << 32);
        returnValue += ((valueAsBytes[2] & 0xFF) << 40);
        returnValue += ((valueAsBytes[1] & 0xFF) << 48 );
        returnValue += ((valueAsBytes[0] & 0xFF) << 56);

        return returnValue;
    }

    public static short reverse(short valueToReverse)
    {
        byte[] valueAsBytes = new byte[]
                {
                        (byte)(valueToReverse & 0xFF),
                        (byte)((valueToReverse >> 8 ) & 0xFF),
                };

        valueToReverse = (short)
                (
                        ((valueAsBytes[1] & 0xFF))
                                + ((valueAsBytes[0] & 0xFF) << 8 )
                );

        return valueToReverse;
    }
}