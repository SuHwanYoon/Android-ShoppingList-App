plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "yoon.tutorials.shoppinglistapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "yoon.tutorials.shoppinglistapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // google maps comopse 라이브러리
    implementation("com.google.maps.android:maps-compose:2.15.0")
    // google maps compose 라이브러리로, Compose UI에서 Google Maps를 사용할 수 있도록 도와줍니다.
    // Google Maps 라이브러리로, Google Maps API를 사용하여 지도 기능을 구현할 수 있도록 도와줍니다.
    implementation("com.google.android.gms:play-services-maps:18.1.0")


    // AndroidX Lifecycle 라이브러리로, 생명주기 인식 컴포넌트를 사용하여 UI와 데이터의 생명주기를 관리할 수 있도록 도와줍니다.
    implementation("androidx.lifecycle:lifecycle-viewmodel-android:2.6.2")
    // Retrofit 라이브러리로, RESTful API와의 통신을 쉽게 할 수 있도록 도와줍니다.
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    // Gson Converter 라이브러리로, JSON 데이터를 Java 객체로 변환하거나 그 반대의 작업을 쉽게 할 수 있도록 도와줍니다.
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    //  OkHttp 라이브러리로, HTTP 요청을 쉽게 할 수 있도록 도와줍니다.
    implementation("androidx.navigation:navigation-compose:2.7.4")



    // 아래의 dependecy에 대해서 설명하는 주석달아줘
    // - androidx.core:core-ktx:1.13.1: AndroidX Core KTX 라이브러리로, Kotlin 확장 기능을 제공하여 Android API를 더 쉽게 사용할 수 있도록 도와줍니다.
    implementation("androidx.core:core-ktx:1.13.1")
    // - androidx.lifecycle:lifecycle-runtime-ktx:2.8.5: AndroidX Lifecycle 라이브러리로, 생명주기 인식 컴포넌트를 사용하여 UI와 데이터의 생명주기를 관리할 수 있도록 도와줍니다.
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.5")
    // - androidx.lifecycle:lifecycle-viewmodel-compose:2.8.5: AndroidX Lifecycle ViewModel Compose 라이브러리로, Compose UI와 함께 사용할 수 있는 ViewModel을 제공합니다.
    implementation("androidx.activity:activity-compose:1.9.2")

    // - androidx.activity:activity-compose:1.9.2: AndroidX Activity Compose 라이브러리로, Compose UI를 Activity와 통합할 수 있도록 도와줍니다.
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}