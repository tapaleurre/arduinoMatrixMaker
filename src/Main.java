import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import BMPTools.*;

public class Main {
    public static GUI gui;
    public static File selectedFile;
    public static void main(String args[]){
        gui = new GUI();
    }

    public static String convertPic() {
        ImageBMP imageFile = ImageBMP.readFromFileAtPath(Main.selectedFile.getAbsolutePath());
        BufferedImage imageInfo = imageFile.convertToSystemImage();
        int height = imageInfo.getHeight();
        int width = imageInfo.getWidth();
        int way = 1;
        LinkedList<Boolean> systemImage = imageFile.convertToBoolList();
        String file = "{";
        for(int h = 0; h < height; h++){
            if(way == 1){
                for(int w = 0; w < width; w=w+8){
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
        new File("upload_this_to_your_arduino").mkdirs();
        new File("upload_this_to_your_arduino/upload_this_to_your_arduino.ino").delete();
        file = file.substring(0, file.length() -1);
        file+="}";
        try {
            PrintWriter writer = new PrintWriter("upload_this_to_your_arduino/upload_this_to_your_arduino.ino", "UTF-8");
            writer.write(makeArduinoCode(file));
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println(file);
        return file;
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
    private static String makeArduinoCode(String image){
        String result = "#include <avr/pgmspace.h>\n" +
                "#define PRESS 9\n" +
                "#define DOWN 10\n" +
                "#define RIGHT 11\n" +
                "#define LEFT 12\n" +
                "#define UP 13\n" +
                "#define TIMING 45\n" +
                "#define SWITCH 8\n" +
                "\n" +
                "const byte image[] PROGMEM="+image+";\n" +
                "const int width = 320;\n" +
                "const int height = 120;\n" +
                "//const int width = 80;\n" +
                "//const int height = 2;\n" +
                "void press(int button){\n" +
                "    digitalWrite(button,LOW);\n" +
                "    delay(TIMING);\n" +
                "    digitalWrite(button,HIGH);  \n" +
                "  }\n" +
                "void pressTwo(int button1, int button2){\n" +
                "  delay(TIMING);\n" +
                "  digitalWrite(button1,LOW);\n" +
                "  delay(TIMING);\n" +
                "  digitalWrite(button1, HIGH);\n" +
                "  digitalWrite(button2, LOW);\n" +
                "  delay(TIMING);\n" +
                "  digitalWrite(button2,HIGH);\n" +
                "  \n" +
                "}\n" +
                "void sendPixel(boolean pixel, char way){\n" +
                "  if(pixel){\n" +
                "    press(PRESS);\n" +
                "  }else{\n" +
                "    delay(TIMING);\n" +
                "  }\n" +
                "  delay(TIMING);\n" +
                "  press(way);\n" +
                "  delay(TIMING);\n" +
                "}\n" +
                "\n" +
                "void setup() {\n" +
                "  // put your setup code here, to run once:\n" +
                "  pinMode(PRESS,OUTPUT);\n" +
                "  pinMode(DOWN,OUTPUT);\n" +
                "  pinMode(RIGHT,OUTPUT);\n" +
                "  pinMode(LEFT,OUTPUT);\n" +
                "  pinMode(UP,OUTPUT);\n" +
                "  pinMode(SWITCH,INPUT);\n" +
                "  digitalWrite(PRESS,HIGH);\n" +
                "  digitalWrite(DOWN,HIGH);\n" +
                "  digitalWrite(RIGHT,HIGH);\n" +
                "  digitalWrite(LEFT,HIGH);\n" +
                "  digitalWrite(UP,HIGH);\n" +
                "  Serial.begin(9600);\n" +
                "}\n" +
                "\n" +
                "void loop() {\n" +
                "  // put your main code here, to run repeatedly:\n" +
                "    if(digitalRead(SWITCH) == HIGH){\n" +
                "    int i=0;\n" +
                "    int j=0;\n" +
                "    short k;\n" +
                "    int translation;\n" +
                "    char way = 1;\n" +
                "    boolean pixel=true;\n" +
                "    int len = height*(width/8);\n" +
                "    for(i=0; i< len ; i++){\n" +
                "        for(k = 7; 0 <= k; k--){\n" +
                "             pixel = (pgm_read_byte_near(image+i) >> k) & 0b00000001;//parcours charque bit du plus lourd au plus léger\n" +
                "           if(way == -1){\n" +
                "            sendPixel(pixel,LEFT);\n" +
                "           }else{\n" +
                "            sendPixel(pixel,RIGHT);\n" +
                "           }\n" +
                "      }\n" +
                "\n" +
                "      j=j+8;\n" +
                "      if(j >= width){\n" +
                "        sendPixel(pixel, DOWN);\n" +
                "        way = -way;\n" +
                "        j=0;\n" +
                "      }\n" +
                "//    pixel=!pixel;\n" +
                "    }\n" +
                "   }\n" +
                "   delay(200);\n" +
                "\n" +
                "}";
        return result;
    }
}
