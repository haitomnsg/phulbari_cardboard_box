# Phulbari - Flower Classification & Moisture Detection

Phulbari is an AI-powered mobile application that integrates deep learning and IoT technology to classify flowers and automate plant care. It uses Convolutional Neural Networks (CNNs) for flower classification and an IoT-based irrigation system to monitor soil moisture levels and water plants when necessary.

## Features

- **Flower Classification**: Uses a deep learning model (ResNet101V2) trained on 100 flower species to accurately identify flowers from images.
- **Plant Care Information**: Provides detailed information about flowers, including their care requirements like watering schedules, sunlight exposure, and propagation methods.
- **Automated Watering System**: Utilizes an ESP32 microcontroller and soil moisture sensors to monitor plant hydration levels and automatically activate watering when needed.
- **Mobile Application**: Built using Java and TensorFlow Lite for real-time flower recognition and IoT device control.
- **Cloud Integration**: Uses Firebase to store and display real-time moisture data and irrigation history.

## Screenshots

1. **Android App**

![hjhfdlk](https://github.com/user-attachments/assets/80197696-1e50-4983-b739-58644ebc9714)

2. **IoT Device**

![1741266668788](https://github.com/user-attachments/assets/44115187-0ab4-4778-806f-baae835f8319)

3. **AI Model**

Accuracy and Loss 

![Frame 3](https://github.com/user-attachments/assets/1e2d5a21-1c02-44f8-acf9-f60d6e29cf42)

Model Architecture

![model_architecture (Small)](https://github.com/user-attachments/assets/70e0c945-6566-40a4-85fd-76859c96497c)

## Technologies Used

### AI Model:
- **Deep Learning Framework**: TensorFlow & Keras
- **Architecture**: ResNet101V2
- **Dataset**: 100 flower classes, 300 images per class (collected via Web Scraping & PlantNet)

### Mobile Application:
- **Platform**: Android (Java, Android Studio)
- **Camera Integration**: CameraX API for capturing images
- **Local Storage**: SQLite for storing user preferences and plant details

### IoT Integration:
- **Microcontroller**: ESP32
- **Sensors**: Soil Moisture Sensor, DHT11 Temperature and Humidity Sensor
- **Watering Mechanism**: DC Mini Water Pump controlled via Relay Module
- **Cloud Services**: Firebase Realtime Database for sensor data storage

## System Architecture

1. **AI-based Classification**:
   - User captures an image of a flower using the mobile app.
   - The TensorFlow Lite model classifies the flower and retrieves related information.
2. **IoT-based Watering**:
   - Soil moisture sensor reads real-time data.
   - If moisture is below the threshold, the ESP32 activates the water pump.
   - The app displays real-time updates from Firebase.

## Installation and Usage

### Prerequisites:
- Android 7.0+ device
- Internet connection for Firebase synchronization
- ESP32 microcontroller setup with sensors (for IoT functionality)

### Steps to Run the Application:
1. Clone this repository:
   ```sh
   git clone https://github.com/yourusername/Phulbari.git
   ```
2. Open the project in **Android Studio**.
3. Build and install the APK on your Android device.
4. Connect the IoT hardware setup (ESP32 + sensors) and ensure it’s linked to Firebase.
5. Launch the app, capture a flower image, and receive classification and care information.
6. Monitor soil moisture levels and let the system automate watering.

## Challenges Faced
- Kernel crashes and module compatibility issues
- Limited dataset for Nepalese flower species
- Delayed access to IoT hardware for testing

## Future Enhancements
- Expand flower classification dataset for better accuracy
- Support multiple languages, including Nepali
- Add scheduled irrigation and customizable watering settings
- Introduce an e-commerce feature for plant purchases

## Contributors
- **Ashish Gupta**
- **Bibek Neupane**
- **Jayed Alam Mansur**

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## References
- Praveen Lamba & Krishan Kumar, 2024 - ResNet Pre-Trained Networks for Flower Classification
- Djarot Hindarto & Nadia Amalia, 2023 - Flower Recognition using CNNs

