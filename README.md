# 💬 ChatApp — WhatsApp-Inspired Demo Application  
![Android](https://img.shields.io/badge/Platform-Android-green?logo=android)
![Language](https://img.shields.io/badge/Language-Java-blue?logo=openjdk)
![Firebase](https://img.shields.io/badge/Backend-Firebase-orange?logo=firebase)
![License](https://img.shields.io/badge/License-MIT-lightgrey)
![Status](https://img.shields.io/badge/Status-Demo%20Project-yellow)

---

## 🧠 Overview  
**ChatApp** is a WhatsApp-inspired **real-time chat application** built for Android using **Firebase** as the backend.  
It demonstrates secure **OTP-based login**, **real-time messaging**, **profile management**, and **status/story sharing**, all inside a clean and modern UI.  

---

## 🚀 Features  

| Category | Description |
|-----------|-------------|
| 🔐 **Secure Login** | Login using your **11-digit phone number** and verify via **Firebase OTP** |
| 💬 **Real-Time Chat** | Instantly send and receive messages using Firebase Realtime Database |
| 👤 **User Profiles** | Add or update your **profile picture** and **name** |
| 📸 **Stories / Status** | Share images as stories and view others’ statuses (similar to WhatsApp/Instagram) |
| ✨ **Shimmer Loading** | Displays shimmer animations while chats and users are loading for a smooth UX |

---

## 🧩 Tech Stack  

**Frontend:** Android (Java, XML)  
**Backend:** Firebase  

**Firebase Services Used:**  
- 🔐 **Firebase Authentication** → For OTP verification  
- 💬 **Firebase Realtime Database** → For storing chats and users  
- ☁️ **Firebase Storage** → For profile and status images  

---

## 🛠️ Libraries Used  

```gradle
// OTP Input View
implementation("com.github.mukeshsolanki.android-otpview-pinview:otpview:3.1.0")

// Circle Image View for profile pictures
implementation("de.hdodenhof:circleimageview:3.1.0")

// Glide for image loading
implementation("com.github.bumptech.glide:glide:5.0.5")

// Reaction popup for message emojis
implementation("com.github.pgreze:android-reactions:1.6")

// Circular status indicator
implementation("com.github.3llomi:CircularStatusView:V1.0.3")

// Story view like Instagram/WhatsApp
implementation("com.github.OMARIHAMZA:StoryView:1.0.2-alpha")

// Shimmer effect for loading
implementation("com.facebook.shimmer:shimmer:0.5.0")
```

---

## 📲 App Flow  

1. **Login / OTP Verification**  
   - Enter your 11-digit mobile number  
   - Receive and verify OTP  
2. **Profile Setup**  
   - Upload your name and profile picture  
   - Stored securely in Firebase  
3. **Chat Interface**  
   - View all registered users  
   - Send & receive messages in real-time  
4. **Status Sharing**  
   - Upload stories visible to all users  
   - View others’ statuses with smooth transitions  

---

## 🧑‍💻 How to Run  

1. Clone the repository  
   ```bash
   git clone https://github.com/amanalis/ChatApp.git
   cd ChatApp
   ```
2. Open the project in **Android Studio**  
3. Connect your **Firebase project** (via Tools → Firebase Assistant)  
4. Enable **Phone Authentication** in Firebase Console  
5. Run the app on an emulator or Android device  

---

## 🧠 Future Improvements  
🔹 Implement **push notifications** using Firebase Cloud Messaging (FCM)  
🔹 Add **group chat** functionality  
🔹 Include **dark mode** support  
🔹 Optimize UI for tablets  

---

## 👨‍💻 Developed By  

**Aman Ali**  
🎓 BSCS Graduate — SZABIST (Class of 2024)  
💼 Android Developer  

🔗 **LinkedIn:** [linkedin.com/in/aman-alisalim](https://www.linkedin.com/in/aman-alisalim)  
💻 **GitHub:** [github.com/amanalis](https://github.com/amanalis)  

---

## 📜 License  
This project is licensed under the **MIT License** — feel free to use, modify, and learn from it.  
