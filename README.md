<h1 align=center> ChatGEMINI 📲</h1>  

<p align=center>
   [![official project](http://jb.gg/badges/official.svg)](https://confluence.jetbrains.com/display/ALL/JetBrains+on+GitHub)
   [![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
</p>

<p> 
  <img src="./screenshots/cover.png" alt="cover">
</p>


A chatbot multiplatform app (Android, iOS and Desktop) built with Compose Multiplatform and powered by [Gemini Pro API](https://ai.google.dev/docs).


### Before you Start

Before you run the project, you need to get an API key from https://ai.google.dev in order to communicate with the Gemini API.
Once the key is obtained, assign it the the constant `API_KEY` in the `GeminiService.kt` file:

```kotlin
const val API_KEY = "YOUR_API_KEY"
```
