const int led_pin1 =  A1; 
const int led_pin2 =  A3;
const int button_stop =  10;
const int button_dot = A4;
const int button_line = 4;
const int button_space = 8;

boolean button_1_flag = 0;
unsigned long last_press;

// variables will change:
int button_dot_state = 0;
int button_line_state = 0;
int button_space_state = 0;
int button_stop_state = 0;

void setup() {
   // initialize the LED pin as an output:
  Serial.begin(9600);
  pinMode(led_pin1, OUTPUT);
  pinMode(led_pin2, OUTPUT);
  pinMode(button_dot, INPUT_PULLUP);
  pinMode(button_line, INPUT_PULLUP);
  pinMode(button_space, INPUT_PULLUP);
    pinMode(button_stop, INPUT_PULLUP);

  
}

void loop() {
 
     button_dot_state = digitalRead(button_dot);
     button_line_state = digitalRead(button_line);
     button_space_state = digitalRead(button_space);
     button_stop_state = digitalRead(button_stop);
  // check if the pushbutton is pressed. If it is, the buttonState is HIGH:
  if (button_dot_state == 0 && button_1_flag == 0 && millis() - last_press > 400) {
    // turn LED on:
    digitalWrite(led_pin1, HIGH);
     button_dot_state = 1;
     Serial.write(".");
     last_press = millis();
     button_1_flag = 1;
     
  }
  else{
     digitalWrite(led_pin1, LOW);
    }
    if(button_dot_state == 1 && button_1_flag == 1){
      button_1_flag = 0;
     }
  if (button_line_state == 0 && button_1_flag == 0 && millis() - last_press > 400) {
    // turn LED on:
    digitalWrite(led_pin2, HIGH);
     button_line_state = 1;
      Serial.write("-");
     last_press = millis();
     button_1_flag = 1;
  }
  else{
    digitalWrite(led_pin2, LOW);
    }
    if(button_line_state == 1 && button_1_flag == 1){
      button_1_flag = 0;
     }
  if (button_space_state == 0 && button_1_flag == 0 && millis() - last_press > 400) {
    // turn LED on:
     button_space_state = 1;
     Serial.write("?");
     last_press = millis();
     button_1_flag = 1;
  }

  if(button_space_state == 1 && button_1_flag == 1){
      button_1_flag = 0;
     }
if (button_stop_state == 0 && button_1_flag == 0 && millis() - last_press > 400){
  button_stop_state = 1;
     Serial.write(" ");
     last_press = millis();
     button_1_flag = 1;
  }
  if(button_stop_state == 1 && button_1_flag == 1){
      button_1_flag = 0;
     }
}
