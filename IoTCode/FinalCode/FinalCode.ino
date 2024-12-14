#include <Arduino.h>

#if defined(ESP32)
#include <WiFi.h>
#elif defined(ESP8266)
#include <ESP8266WiFi.h>
#endif

#include <Firebase_ESP_Client.h>

// Provide the token generation process info.
#include "addons/TokenHelper.h"
// Provide the RTDB payload printing info and other helper functions.
#include "addons/RTDBHelper.h"

// Insert your network credentials
#define WIFI_SSID "Haitomns Groups"
#define WIFI_PASSWORD "12345678"

// Insert Firebase project API Key and URL
#define DATABASE_URL "https://phulbari-haitomns-default-rtdb.asia-southeast1.firebasedatabase.app"
#define API_KEY "AIzaSyAx-Oh4TzU5QQfKk2mPsF47fWLyeSW4GCs"

// Define the analog pin for the soil moisture sensor
const int soilMoisturePin = 34;  // Adjust to the correct pin
// Define the pin for the relay
const int relayPin = 5; // GPIO 5 corresponds to D5 on some ESP32 boards

// Define Firebase Data object
FirebaseData fbdo;
FirebaseAuth auth;
FirebaseConfig config;

// Interval set to 15 minutes in milliseconds
unsigned long sendDataPrevMillis = 0;
const unsigned long sendInterval = 60000; // 15 minutes in milliseconds
bool signupOK = false;

void setup() {
  Serial.begin(115200);
  
  // Set relay pin as output
  pinMode(relayPin, OUTPUT);
  digitalWrite(relayPin, HIGH); // Turn off relay initially (assuming LOW triggers the relay)
  
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.print("Connecting to Wi-Fi");
  while (WiFi.status() != WL_CONNECTED) {
    Serial.print(".");
    delay(300);
  }
  Serial.println();
  Serial.print("Connected with IP: ");
  Serial.println(WiFi.localIP());
  Serial.println();

  // Assign the API key and RTDB URL
  config.api_key = API_KEY;
  config.database_url = DATABASE_URL;

  // Sign up for Firebase
  if (Firebase.signUp(&config, &auth, "", "")) {
    Serial.println("Sign-up successful");
    signupOK = true;
  } else {
    Serial.printf("Sign-up error: %s\n", config.signer.signupError.message.c_str());
  }

  // Assign the callback function for the token generation
  config.token_status_callback = tokenStatusCallback;  // see addons/TokenHelper.h

  // Initialize Firebase
  Firebase.begin(&config, &auth);
  Firebase.reconnectWiFi(true);
}

void loop() {
  // Check if Firebase is ready and it’s time to water the plant
  if (Firebase.ready() && signupOK && (millis() - sendDataPrevMillis > sendInterval || sendDataPrevMillis == 0)) {
    sendDataPrevMillis = millis();

    // Read the soil moisture value
    int soilMoistureValue = analogRead(soilMoisturePin);

    // Print the soil moisture value to the Serial Monitor
    Serial.print("Soil Moisture Value: ");
    Serial.println(soilMoistureValue);

    // Water the plant for 30 seconds
    digitalWrite(relayPin, LOW); // Turn on relay to water the plant
    Serial.println("Watering the plant for 30 seconds...");
    delay(5000); // Water for 30 seconds
    digitalWrite(relayPin, HIGH); // Turn off the relay
    Serial.println("Watering completed.");

    // Create a unique path using the push() function to auto-generate a unique key
    String path = "/wateringData/";

    // Create a JSON object to hold the data
    FirebaseJson json;
    json.set("wateringDuration", 30); // Send the watering duration (in seconds)
    json.set("timestamp/.sv", "timestamp"); // Firebase's server-side timestamp

    // Send watering data to Firebase
    if (Firebase.RTDB.pushJSON(&fbdo, path.c_str(), &json)) {
      Serial.println("Watering data sent successfully to Firebase");
      Serial.println("PATH: " + fbdo.dataPath());
      Serial.println("TYPE: " + fbdo.dataType());
    } else {
      Serial.println("Failed to send watering data");
      Serial.println("REASON: " + fbdo.errorReason());
    }

    // Also send soil moisture data as before
    path = "/sensorData/";
    json.clear();
    json.set("soilMoisture", soilMoistureValue);
    json.set("timestamp/.sv", "timestamp"); // Firebase's server-side timestamp

    // Send soil moisture data to Firebase
    if (Firebase.RTDB.pushJSON(&fbdo, path.c_str(), &json)) {
      Serial.println("Soil moisture data sent successfully to Firebase");
      Serial.println("PATH: " + fbdo.dataPath());
      Serial.println("TYPE: " + fbdo.dataType());
    } else {
      Serial.println("Failed to send soil moisture data");
      Serial.println("REASON: " + fbdo.errorReason());
    }
  }
}
