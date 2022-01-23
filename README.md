# Security_Feed
* Different User can login using google account
* Can share Post as text as well as Image
* Can Like the post
* Can logout
<h3>DEMO</h3>



# Open Source Library
* [Firebase](https://firebase.google.com/)
* [Glide](https://github.com/bumptech/glide)

# Things we used while making this application
* Firebase RealTime Database
* Firebase Storage
* Firebase Authentication
* RecyclerView
* MVVM
* Data Binding
* Kotlin Coroutines

# Tech Stack ✨
* Kotlin
* Android Studio
* Firebase
* Git


# Clone this Repo To Your System Using Android Studio
* Open your Android Studio then go to the File > New > Project from Version Control.
* After clicking on the Project from Version Control a pop-up screen will arise. In the Version control choose Git from the drop-down menu.
* Then at last paste the link in the URL and choose your Directory. Click on the Clone button and you are done.


# Dependencies
```

plugins {
    id 'com.android.application'
    id 'kotlin-android'
    id 'com.google.gms.google-services'
    id 'kotlin-android-extensions'
    id 'kotlin-kapt'
}
android{
    buildFeatures{
        dataBinding true
    }
}


implementation 'androidx.core:core-ktx:1.7.0'
implementation 'androidx.appcompat:appcompat:1.4.1'
implementation 'com.google.android.material:material:1.5.0'
implementation 'androidx.constraintlayout:constraintlayout:2.1.3'
implementation 'com.google.firebase:firebase-auth-ktx:21.0.1'
implementation 'com.google.android.gms:play-services-auth:20.0.1'
implementation 'androidx.legacy:legacy-support-v4:1.0.0'
implementation 'com.google.firebase:firebase-firestore-ktx:24.0.0'
implementation 'com.google.firebase:firebase-database-ktx:20.0.3'
implementation 'com.google.firebase:firebase-storage-ktx:20.0.0'
testImplementation 'junit:junit:4.+'
androidTestImplementation 'androidx.test.ext:junit:1.1.3'
androidTestImplementation 'androidx.test.espresso:espresso-core:3.4.0'

//Glide
implementation 'com.github.bumptech.glide:glide:4.12.0'
```
