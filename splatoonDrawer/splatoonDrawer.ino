#include <avr/pgmspace.h>
#define PRESS 9
#define DOWN 10
#define RIGHT 11
#define LEFT 12
#define UP 13
#define TIMING = 500

const char[][] image PROGMEM={{0x00,0x95},{0x02,0xFF}};
const width = 320;
const height = 120;

void press(int button){
    digitalWrite(button,HIGH);
    delay(TIMING);
    digitalWrite(button,LOW);  
  }
void sendPixel(boolean pixel, char way){
  if(pixel){
    press(PRESS);
  }
  if(way == 1){
    press(RIGHT);
  }else{
    press(LEFT);
    }
}

void setup() {
  // put your setup code here, to run once:
  pinMode(PRESS,OUTPUT);
  pinMode(DOWN,OUTPUT);
  pinMode(RIGHT,OUTPUT);
  pinMode(LEFT,OUTPUT);
  pinMode(UP,OUTPUT);
  digitalWrite(PRESS,HIGH);
  digitalWrite(DOWN,HIGH);
  digitalWrite(RIGHT,HIGH);
  digitalWrite(LEFT,HIGH);
  digitalWrite(UP,HIGH);
}

void loop() {
  // put your main code here, to run repeatedly:
    /*digitalWrite(PRESS,LOW);
    delay(timing);
    digitalWrite(PRESS,HIGH);
    digitalWrite(DOWN,LOW);
    delay(timing);
    digitalWrite(DOWN,HIGH);  
    digitalWrite(RIGHT,LOW);
    delay(timing);
    digitalWrite(RIGHT,HIGH);
    digitalWrite(LEFT,LOW);
    delay(timing);
    digitalWrite(LEFT,HIGH);
    digitalWrite(UP,LOW);
    delay(timing);
    digitalWrite(UP,HIGH);*/
    int i;
    int j;
    int k;
    int translation;
    char way = 1;
    for(i=0; i<height; i++){
      for(j=0; j<width; j++){
        for(k = 7; 0 <= k; k --){
           if(way==-1){
             translation = width - i;
             pixel = (image[translation][j] << k) & 0x80;//parcours charque bit du plus lourd au plus léger
           }else{
             pixel = (image[i][j] >> k) & 0x01;//parcours charque bit du plus lourd au plus léger
           }
           sendPixel(image[translation][i],way);
         }
      }
    way = way*(-1);
    press(DOWN);
    }
}