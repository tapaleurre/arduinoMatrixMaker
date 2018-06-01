import com.sun.org.apache.xpath.internal.operations.Bool;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;

public class Main {
    public static GUI gui;
    public static File selectedFile;
    private static final int MASK = 0xFF;
    public static void main(String args[]){
        gui = new GUI();
    }

    public static String convertPic() {
        /*int height=0;
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
            for(int j=0; j<width; j++){
                finalFile+=codeInt(inBMP)+",";
            }
            //finalFile = finalFile.substring(0, finalFile.length() -1);
            //finalFile+="},";
            /*try {
                inBMP.skipBytes(sup);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        finalFile = finalFile.substring(0, finalFile.length() -1);
        finalFile+="}";
        System.out.print(finalFile);
        return finalFile;*/
        ImageBMP imageFile = ImageBMP.readFromFileAtPath(Main.selectedFile.getAbsolutePath());
        BufferedImage imageinfo = imageFile.convertToSystemImage();
        int height = imageinfo.getHeight();
        int width = imageinfo.getWidth();
        int way = 1;
        LinkedList<Boolean> systemImage = imageFile.convertToBoolList();
        String file = "{";
        for(int h = 0; h < height; h++){
            if(way == 1){
                for(int w = 0; w < width; w=w+8){
                    //System.out.println("x:"+w+" y:"+h);
                    file+=makeBinaryRepresentation(systemImage.get(h*width+w),
                            systemImage.get(h*width+w+1),
                            systemImage.get(h*width+w+2),
                            systemImage.get(h*width+w+3),
                            systemImage.get(h*width+w+4),
                            systemImage.get(h*width+w+5),
                            systemImage.get(h*width+w+6),
                            systemImage.get(h*width+w+7))+",";
                }
            }else{
                for(int w = width-1; w >= 0; w=w-8){
                    //System.out.println("x:"+w+" y:"+h);
                    file+=makeBinaryRepresentation(systemImage.get(h*width+w),
                            systemImage.get(h*width+w-1),
                            systemImage.get(h*width+w-2),
                            systemImage.get(h*width+w-3),
                            systemImage.get(h*width+w-4),
                            systemImage.get(h*width+w-5),
                            systemImage.get(h*width+w-6),
                            systemImage.get(h*width+w-7))+",";
                }
            }
            way = way*-1;
        }
        file = file.substring(0, file.length() -1);
        file+="}";
        System.out.println(file);
        return file;
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
    private static String convertBin(int chunk){
        if(chunk != 0){
            return "1";
        }else{
            return "0";
        }
    }
    private static String convertBin(Boolean chunk){
        if(chunk.booleanValue()){
            return "1";
        }else{
            return "0";
        }
    }
    private static String makeBinaryRepresentation(boolean b1,boolean b2,boolean b3,boolean b4,boolean b5,boolean b6,boolean b7,boolean b8){
        String result="0b";
            result += ""+b2o(b1)+b2o(b2)+b2o(b3)+b2o(b4)+b2o(b5)+b2o(b6)+b2o(b7)+b2o(b8);
        return result;
    }
    private static int b2o(boolean b){
        int i = 0;
        if(b){
            i=1;
        }
        return i;
    }
}
