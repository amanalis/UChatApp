# 💬 ChatApp — WhatsApp-Inspired Demo Application  
![Android](https://img.shields.io/badge/Platform-Android-green?logo=android)
![Language](https://img.shields.io/badge/Language-Java-blue?logo=openjdk)
![Firebase](https://img.shields.io/badge/Backend-Firebase-orange?logo=firebase)
![License](https://img.shields.io/badge/License-MIT-lightgrey)
![Status](https://img.shields.io/badge/Status-Demo%20Project-yellow)

## 🧠 Overview
**ChatApp** is a modern, WhatsApp-inspired Android application built using **Firebase** and **Java**, with a **Python backend** for sending push notifications via Firebase Cloud Messaging (FCM).

It provides secure OTP-based login, real-time messaging, group chats, user presence detection, typing indicators, remote UI updates, and smooth user experience with shimmer loading and story sharing — all integrated seamlessly with Firebase services.

---

## 🚀 Features

| Category | Description |
|-----------|--------------|
| 🔐 **Secure Login** | Login using your phone number and verify via **Firebase OTP Authentication** |
| 💬 **Real-Time Chat** | One-on-one and **group messaging** powered by **Firebase Realtime Database** |
| 📢 **Push Notifications** | Real-time notifications using **Firebase Cloud Messaging** with **Python Flask backend** and **Volley** |
| 👤 **User Profiles** | Create, edit, and view profiles — including other users’ profiles |
| 🟢 **Online & Typing Status** | Instantly see if users are online or currently typing |
| 📸 **Stories / Status** | Upload stories (images) visible to all users, similar to WhatsApp/Instagram |
| 🎨 **Remote Customization** | Dynamic **theme and toolbar updates** using **Firebase Remote Config** |
| ✨ **Shimmer Loading** | Elegant shimmer animation for smoother UX while chats and data load |
| 👥 **Group Chat Support** | Create and participate in group conversations with shared messages |
| ☁️ **Cloud Storage** | Store profile pictures and statuses securely using **Firebase Storage** |

---

## 🧩 Tech Stack

### 🖥️ Frontend
- **Language:** Java (Android)
- **UI Layout:** XML
- **Libraries:**
  ```gradle
  // OTP Input View
  implementation("com.github.mukeshsolanki.android-otpview-pinview:otpview:3.1.0")

  // Circle Image View
  implementation("de.hdodenhof:circleimageview:3.1.0")

  // Glide for Image Loading
  implementation("com.github.bumptech.glide:glide:5.0.5")

  // Reaction Popup
  implementation("com.github.pgreze:android-reactions:1.6")

  // Circular Status Indicator
  implementation("com.github.3llomi:CircularStatusView:V1.0.3")

  // Story View (like WhatsApp)
  implementation("com.github.OMARIHAMZA:StoryView:1.0.2-alpha")

  // Shimmer Effect
  implementation("com.facebook.shimmer:shimmer:0.5.0")

  // Volley for HTTP Requests
  implementation("com.android.volley:volley:1.2.1")
  ```

### ☁️ Backend (Firebase + Python)
- **Firebase Authentication** → OTP verification  
- **Firebase Realtime Database** → Store messages, users, presence, and typing status  
- **Firebase Cloud Messaging (FCM)** → Push notifications  
- **Firebase Storage** → Images for profile and status  
- **Firebase Remote Config** → Dynamic toolbar/theme customization  
- **Python Flask Server** → FCM backend service for notifications  

---

## 📲 App Flow

### 🔹 **Login / OTP Verification**
1. Enter your 11-digit mobile number  
2. Firebase sends OTP  
3. Verify the OTP to log in securely  

### 🔹 **Profile Setup**
- Add your name and profile picture  
- Data stored securely in Firebase Realtime Database  

### 🔹 **Chat Interface**
- See online users and typing indicators  
- Send and receive messages instantly  
- Create and join group chats  

### 🔹 **Status Sharing**
- Upload image-based stories  
- View others' statuses with a smooth transition  

---

## ⚙️ How to Run

1. **Clone the repository**
   ```bash
   git clone https://github.com/amanalis/ChatApp.git
   cd ChatApp
   ```

2. **Open in Android Studio**

3. **Connect your Firebase Project**
   - Go to `Tools → Firebase Assistant`
   - Connect Authentication, Realtime Database, and Cloud Messaging

4. **Enable Phone Authentication**
   - Firebase Console → Authentication → Sign-in Method → Enable **Phone**

5. **Run the Python Notification Server**
   ```bash
   python server.py
   ```

6. **Build & Run the App** on an emulator or physical device.

---

## 🧠 Future Enhancements
- 🔹 Message delivery/read receipts (double-tick system)
- 🔹 Cloud-based group management
- 🔹 End-to-end encryption
- 🔹 Voice/video calling
- 🔹 Dark mode UI support

---

## 👨‍💻 Developed By

**Aman Ali**  
🎓 BSCS Graduate — SZABIST (Class of 2024)  
💼 Android Developer (Flutter | Firebase | Fintech)  

🔗 [LinkedIn](https://linkedin.com/in/aman-alisalim)  
💻 [GitHub](https://github.com/amanalis)  
🎨 [Behance](https://www.behance.net/amanali00)

---

## 📜 License
This project is licensed under the **MIT License** — feel free to use, modify, and learn from it.
