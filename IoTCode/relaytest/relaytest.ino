#define relay1 5

void setup(){
  pinMode(relay1, OUTPUT);
}

void loop(){
  digitalWrite(relay1, LOW);
  delay(1000);
  digitalWrite(relay1, HIGH);
  delay(1000);
}