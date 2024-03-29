import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.serialization)
    alias(libs.plugins.sqldelight)
    alias(libs.plugins.buildKonfig)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    sourceSets {
        androidMain.dependencies {
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)

            // Google Maps
            implementation(libs.google.maps)

            // Ktor
            implementation(libs.ktor.client.okhttp)

            // SQLDelight
            implementation(libs.sqldelight.android)
        }
        iosMain.dependencies {
            // Ktor
            implementation(libs.ktor.client.darwin)

            // SQLDelight
            implementation(libs.sqldelight.native)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            //implementation(compose.material) // TODO check, why this is needed for iOS
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(compose.materialIconsExtended)

            // Voyager navigation library
            implementation(libs.bundles.voyager)

            // Supabase
            implementation(project.dependencies.platform(libs.supabase.bom))
            implementation(libs.bundles.supabase)

            // Ktor
            implementation(libs.ktor.client.core)

            // Kermit logger
            implementation(libs.kermit)

            // SQLDelight
            implementation(libs.sqldelight.coroutines)
        }
    }
}

android {
    namespace = "com.challenge.app"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    buildFeatures {
        buildConfig = true
    }

    val mapsApiKey = gradleLocalProperties(rootDir).getProperty("MAPS_API_KEY")

    defaultConfig {
        applicationId = "com.challenge.app"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        manifestPlaceholders["MAPS_API_KEY"] = mapsApiKey
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    dependencies {
        debugImplementation(libs.compose.ui.tooling)
    }
}

sqldelight {
    databases {
        create("ChallengeAppDB") {
            packageName.set("com.challenge.app")
        }
    }
}

buildkonfig {
    packageName = "com.challenge.app"

    val supabaseApiKey = gradleLocalProperties(rootDir).getProperty("SUPABASE_API_KEY")

    defaultConfigs {
        buildConfigField(STRING, "SUPABASE_API_KEY", supabaseApiKey)
    }
}

