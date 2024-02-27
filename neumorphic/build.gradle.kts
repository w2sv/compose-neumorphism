import org.jetbrains.compose.ExperimentalComposeLibrary

plugins {
    kotlin("multiplatform")
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetpack.compose)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = JavaVersion.VERSION_17.toString()
            }
        }
    }

    jvm("desktop") {
        compilations.all {
            kotlinOptions.jvmTarget = JavaVersion.VERSION_17.toString()
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    )
        .forEach { iosTarget ->
            iosTarget.binaries.framework {
                baseName = "neumorphic"
                isStatic = true
            }
        }

    sourceSets {
        commonMain {
            dependencies {
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material)
                @OptIn(ExperimentalComposeLibrary::class)
                api(compose.components.resources)
                api(libs.androidx.annotation)
                api(libs.skiko.common)
            }
        }
        androidMain {
            dependencies {
                api(libs.androidx.core.ktx)
                api(libs.compose.ui)
                api(libs.compose.ui.tooling.preview)
                api(libs.androidx.activity.compose)
            }
        }
        val desktopMain by getting {
            dependencies {
                api(compose.desktop.currentOs)
            }
        }
    }
}

android {
    namespace = "com.gandiva.neumorphism"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

//    libraryVariants.all {
//        outputs.all {
//            packageLibraryProvider {
//                archiveFileName.set(getArtifactName())
//            }
//        }
//    }
}

//fun getArtifactName(): String {
//    val versionName = project.rootProject.extra["artifactVersion"] as String
//    return "neumorphic-$versionName.aar"
//}
//
//apply(from = "./publish-android-library.gradle")
