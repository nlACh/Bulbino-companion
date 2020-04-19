#include <Wire.h>
#include "SSD1306Wire.h"
#include <WS2812FX.h>
#include <ESP8266WiFi.h>
#include <ESP8266WebServer.h>
#include <ArduinoJson.h>

#define SDA 13
#define SCL 12
#define ADDR 0x3c

#define LED_PIN 14
#define NUM_LED 26


// Server stuff
#define STATIC_IP
#ifdef STATIC_IP
  IPAddress ip(192,168,0,40); // Use whatever you like. This will be deprecated very soon!
  IPAddress gateway(192,168,0,1);
  IPAddress subnet(255,255,255,0);
#endif

#define TIMEOUT 15000
const char* ssid ="xxx"; // Write the ssid (string)
const char* pswd ="xxx: Return of Xander Cage"; // Write the password (string)

SSD1306Wire disp(ADDR, SDA, SCL);
WS2812FX fx = WS2812FX(NUM_LED, LED_PIN, NEO_GRBW+NEO_KHZ800);
ESP8266WebServer server(80);

unsigned long init_start;

void setup() {
  // put your setup code here, to run once:
  //disp.init();
  Serial.begin(115200);
  fx.init();
  fx.setBrightness(200);
  fx.setSpeed(300);
  fx.setMode(1); // Rainbow cycle
  fx.start();
  Serial.println("\nStarting WiFi");
  wifi_setup();
/* 
#####################################################
#  Webserver Functions                              #
##################################################### 
*/
  server.on("/", handleRoot);
  server.on("/rainbow", handle12);
  server.on("/off", off);
  server.on("/post", HTTP_GET, handlePost);
  server.onNotFound(noHandle);
  server.begin();
  Serial.println("Server satrted on port 80");
}

void wifi_setup(){
  WiFi.mode(WIFI_STA);
  WiFi.begin(ssid, pswd);
  #ifdef STATIC_IP
    WiFi.config(ip, gateway, subnet);
  #endif
  
  while(WiFi.status() != WL_CONNECTED){
    delay(1000);
    Serial.print(".");
  }
  Serial.print("Connected to: "); Serial.println(ssid);
  fx.setColor(0x00FF00);
  Serial.println(WiFi.localIP());
}

void loop() {
  init_start = millis();
  server.handleClient();
  fx.service();
}

/* #####################################################
#  Webserver Functions
##################################################### */
void handleRoot(){
  server.send(200, "application/json", "\r\n\r\n{\"status\":\"OK\"}");
  fx.setMode(2);
  fx.setColor(0xFF0000);
}

void noHandle(){
  server.send(404, "application/json", "\r\n\r\n{\"status\":\"NOT_HERE!\"}");
}

void handle12(){
  fx.setMode(12);
  server.send(200, "application/json", "\r\n\r\n{\"status\":\"RAINBOW!\"}");
}

void off(){
  fx.setBrightness(0);
  server.send(200, "application/json", "\r\n\r\n{\"status\":\"OFF\"}");
}

void handlePost(){
  if(! server.hasArg("mode") || ! server.hasArg("value")
  || server.arg("mode")==NULL || server.arg("value")==NULL){
    server.send(400, "application/json", "\r\n\r\n{\"status\":\"NOT_OK_b\"}");
    Serial.println("Received wrong post req");
    return;
  }
  else
    Serial.print("Received: "+server.arg("mode")+"\t"+server.arg("value"));
  if(server.arg("mode")=="b"){ 
    fx.setBrightness(server.arg("value").toInt());
    server.send(200, "application/json", "\r\n\r\n{\"status\":\"OK_b\"}");
    //fx.setMode(0);
    }
  if(server.arg("mode")=="m" && server.hasArg("value")){
    fx.setMode(server.arg("value").toInt());
    fx.setColor(fx.Color(255,255,255,255));
    server.send(200, "application/json", "\r\n\r\n{\"status\":\"OK_m_0\"}");
  }
}
