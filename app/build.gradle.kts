import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-parcelize")
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
}

// define major, minor, patch for version code and version name
val major = 1
val minor = 0
val patch = 0

android {
    namespace = "com.example.dapurbundabahagia"
    compileSdk = 32

    defaultConfig {
        applicationId = "com.example.dapurbundabahagia"
        minSdk = 21
        targetSdk = 32
        versionCode = (major * 10000) + (minor * 100) + patch
        versionName = "$major.$minor.$patch"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")

            val properties = Properties().apply {
                load(rootProject.file("/config/local.properties").reader())
            }

            // build config for BASE_URL
            buildConfigField(
                "String",
                "BASE_URL",
                "${properties["BASE_URL"]}"
            )

        }

        getByName("debug") {
            isShrinkResources = false
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
            isDebuggable = true
            isMinifyEnabled = false

            val properties = Properties().apply {
                load(rootProject.file("/config/local.properties").reader())
            }

            // build config for BASE_URL
            buildConfigField(
                "String",
                "BASE_URL",
                "${properties["BASE_URL"]}"
            )
        }

    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    hilt {
        enableAggregatingTask = true
    }


    packagingOptions {
        resources.excludes.add("META-INF/INDEX.LIST")
        resources.excludes.add("META-INF/io.netty.versions.properties")
    }
}

dependencies {
    // default implementation
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.5.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Google Mobile Services
    // implementation("com.google.android.gms:play-services-auth:20.5.0")

    // splash screen
    implementation("androidx.core:core-splashscreen:1.0.1")

    //Logging
    implementation("com.jakewharton.timber:timber:5.0.1")
    implementation("com.orhanobut:logger:2.2.0")

    // Dagger-hilt
    implementation("com.google.dagger:hilt-android:2.42")
    kapt("com.google.dagger:hilt-android-compiler:2.42")
    kapt("androidx.hilt:hilt-compiler:1.0.0")
    implementation("android.arch.lifecycle:extensions:1.1.1")

    // navigation graph
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.3")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.3")

    // Preference Data Store
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // Corutine
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.5")

    // dots indicator
    implementation("com.tbuonomo:dotsindicator:4.2")

    // Coroutine Lifecycle Scopes
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.2.0")

    // Activity KTX for viewModels()
    implementation("androidx.activity:activity-ktx:1.1.0")

    // retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")

    //Circle Image
    implementation("de.hdodenhof:circleimageview:3.1.0")

    // glide
    implementation("com.github.bumptech.glide:glide:4.12.0")

    // horizontal step view android
    implementation ("com.github.shuhart:stepview:1.5.1")

    // HiveMQ MQTT
    implementation("com.hivemq:hivemq-mqtt-client:1.2.2")
    implementation(platform("com.hivemq:hivemq-mqtt-client-websocket:1.2.2"))

    // Lottie Animation
    implementation("com.airbnb.android:lottie:5.2.0")

    // test implementation
    testImplementation("junit:junit:4.13.2")

    // android test implementation
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}

// copy debug apk when build project is done
tasks.register("copyAPKDebug", Copy::class) {
    dependsOn("test")
    val soureDir = layout.buildDirectory.dir("outputs/apk/debug/app-debug.apk")
    val destDir = "$rootDir/apk"
    from(soureDir)
    into(destDir)
    rename("app-debug.apk", "dapurBundaBahagiaDebug.apk")

    // Untuk cek apakah aplikasi ada virus atau tidak, bisa diliath dari MD5 yang sudah di generate
    doLast {
        val filePath = File(destDir, "dapurBundaBahagiaDebug.apk")
        ant.withGroovyBuilder {
            "checksum"("file" to filePath.path)
        }
    }
}

tasks.whenTaskAdded {
    if (this.name == "assembleDebug") {
        this.finalizedBy("copyAPKDebug")
    }
}