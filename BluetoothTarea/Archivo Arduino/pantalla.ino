#include <LiquidCrystal.h> // incluimos la libreria de la pantalla lcd
LiquidCrystal lcd (8,9,     4,5,6,7); //reconocemos los pines de uso
String texto="Hola!";


void setup() {
  lcd.begin(16,2); // Reconocemos y empezamos el LCD
  lcd.print("Ramoncito");
  Serial.begin(9600);
}
void loop() {
  lcd.setCursor (0,0); // (Columna fila) 
  lcd.setCursor(0,0);
  
  if(Serial.available()>0){
      if(Serial.peek()=='T'){
        Serial.read();
        texto=Serial.readString();
        texto=texto.substring(0,texto.length());
        lcd.clear();
        Serial.print(texto);
        lcd.print(texto);
        lcd.setCursor(0,0);
      }
      else{
        if(Serial.peek()=='C'){
          Serial.read();
          lcd.clear();
          texto="";
        }
      }
  }
  while(Serial.available()>0){
    Serial.read();
  }
}
