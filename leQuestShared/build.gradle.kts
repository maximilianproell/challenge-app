import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.serialization)
    alias(libs.plugins.buildKonfig)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
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
            baseName = "LeQuestShared"
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

            // Camera and barcode scanner
            implementation(libs.bundles.android.camerax)
            implementation(libs.mlkit.barcode.scanning)

            // Android permissions for compose
            implementation(libs.accompanist.permissions)

            // Compose destinations navigation framework
            implementation(libs.compose.destinations.core)

            implementation(libs.koin.android)
            implementation(libs.koin.androidx.navigation)
        }
        iosMain.dependencies {
            // Ktor
            implementation(libs.ktor.client.darwin)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(compose.materialIconsExtended)

            // Supabase
            implementation(project.dependencies.platform(libs.supabase.bom))
            implementation(libs.bundles.supabase)

            // Ktor
            implementation(libs.ktor.client.core)

            // Kermit logger
            implementation(libs.kermit)

            // KMP ViewModel
            implementation(libs.lifecycle.viewmodel)

            // Koin DI framework
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)

            // Room database
            implementation(libs.room.runtime)
            implementation(libs.androidx.sqlite.bundled)
        }
    }
}

android {
    namespace = "com.lequest.app"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    buildFeatures {
        buildConfig = true
    }

    val mapsApiKey = gradleLocalProperties(rootDir, providers).getProperty("MAPS_API_KEY")

    defaultConfig {
        applicationId = "com.lequest.app"
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

dependencies {
    add("kspAndroid", libs.room.compiler)
    add("kspAndroid", libs.compose.destinations.ksp)

    add("kspIosSimulatorArm64", libs.room.compiler)
    add("kspIosX64", libs.room.compiler)
    add("kspIosArm64", libs.room.compiler)
}

room {
    schemaDirectory("$projectDir/schemas")
}

buildkonfig {
    packageName = "com.lequest.app"

    val supabaseApiKey = gradleLocalProperties(rootDir, providers).getProperty("SUPABASE_API_KEY")

    defaultConfigs {
        buildConfigField(STRING, "SUPABASE_API_KEY", supabaseApiKey)
    }
}

