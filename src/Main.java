import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Main {
    public static GUI gui;
    public static File selectedFile;
    private static final int MASK = 0xFF;
    public static void main(String args[]){
        gui = new GUI();
    }

    public static String convertPic() {
        int height=0;
        int width=0;
        DataInputStream inBMP=null;
        try{
        inBMP = new DataInputStream(new FileInputStream(selectedFile));

        //On va lire le début de l'entête
        inBMP.skipBytes(18);

        //Maintenant, on lit la largeur et la hauteur de l'image
        width = readInt(inBMP);
        height = readInt(inBMP);
        System.out.println("Width = "+width+"    Height="+height);

        //On saute les données inutiles del'entête 28
        inBMP.skipBytes(528);} catch (Exception e){
            System.err.println("Couldn't read file");
        }
        String finalFile ="";
        int sup = (width * 3) % 4;
        width = width/8;
        finalFile+="{";
        for(int i=0; i<height; i++){
            finalFile+="{";
            for(int j=0; j<width; j++){
                finalFile+=codeInt(inBMP)+",";
            }
            finalFile = finalFile.substring(0, finalFile.length() -1);
            finalFile+="},";
            /*try {
                inBMP.skipBytes(sup);
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        }
        finalFile = finalFile.substring(0, finalFile.length() -1);
        finalFile+="}";
        System.out.print(finalFile);
        return finalFile;
    }

    private static int readInt(DataInputStream in){
        byte[] b = new byte[4];
        int result = 0;

        try{
            in.read(b);
            result = b[0] & MASK;
            result = result + ((b[1] & MASK) << 8);
            result = result + ((b[2] & MASK) << 16);
            result = result + ((b[3] & MASK) << 24);
        }
        catch(Exception e){
            System.err.println("Idk can't read file anymore");
        }

        return result;
    }
    private static String codeInt(DataInputStream in){
        byte[] b = new byte[4];
        String string = "0b";
        try{
            in.read(b);
            string+=convertBin(b[0]);
            string+=convertBin(b[1]);
            string+=convertBin(b[2]);
            string+=convertBin(b[3]);
            in.read(b);
            string+=convertBin(b[0]);
            string+=convertBin(b[1]);
            string+=convertBin(b[2]);
            string+=convertBin(b[3]);
        }
        catch(Exception e){
            System.err.println("Idk can't read file anymore");
        }
        return string;
    }
    private static String convertBin(byte chunk){
        if(chunk != 0){
            return "1";
        }else{
            return "0";
        }
    }
}
