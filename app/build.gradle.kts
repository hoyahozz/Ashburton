plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.compose.compiler)
  alias(libs.plugins.kotlin.serialization)
  alias(libs.plugins.ktlint)
  alias(libs.plugins.ksp)
  alias(libs.plugins.androidx.room)
  alias(libs.plugins.compose.screenshot)
  alias(libs.plugins.androidx.baselineprofile)
}

android {
  experimentalProperties["android.experimental.enableScreenshotTest"] = true
  namespace = "com.hoyahozz.ashburton"
  compileSdk = 37
  defaultConfig {
    applicationId = "com.hoyahozz.ashburton"
    minSdk = 26
    targetSdk = 37
    versionCode = 1
    versionName = "1.0"
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"))
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }
  buildFeatures {
    compose = true
    aidl = false
    buildConfig = false
    shaders = false
  }

  packaging {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
  }
}

kotlin {
  jvmToolchain(17)
}

dependencies {
  val composeBom = platform(libs.androidx.compose.bom)
  implementation(composeBom)
  androidTestImplementation(composeBom)

  // Core Android dependencies
  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.lifecycle.runtime.ktx)
  implementation(libs.androidx.activity.compose)

  // Compose
  implementation(libs.androidx.compose.ui)
  implementation(libs.androidx.compose.material3)
  // Tooling
  debugImplementation(libs.androidx.compose.ui.tooling)
  // Instrumented tests
  androidTestImplementation(libs.androidx.compose.ui.test.junit4)
  debugImplementation(libs.androidx.compose.ui.test.manifest)

  // Instrumented tests: jUnit rules and runners
  androidTestImplementation(libs.androidx.test.core)
  androidTestImplementation(libs.androidx.test.ext.junit)
  androidTestImplementation(libs.androidx.test.runner)
  androidTestImplementation(libs.androidx.test.espresso.core)
  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.room.runtime)
  androidTestImplementation(libs.androidx.sqlite.framework)
  androidTestImplementation(libs.kotlinx.coroutines.test)
  kspAndroidTest(libs.androidx.room.compiler)
  screenshotTestImplementation(composeBom)
  screenshotTestImplementation(libs.androidx.compose.ui.tooling)
  screenshotTestImplementation(libs.androidx.compose.ui.tooling.preview)
  screenshotTestImplementation(libs.screenshot.validation.api)

  // Navigation
  implementation(libs.androidx.navigation3.ui)
  implementation(libs.androidx.navigation3.runtime)
  implementation(libs.androidx.profileinstaller)
  baselineProfile(project(":benchmark"))
}

ktlint {
  version.set(libs.versions.ktlint)
}

room3 {
  schemaDirectory(layout.buildDirectory.dir("generated/roomSchemas").get().asFile.path)
}

baselineProfile {
  automaticGenerationDuringBuild = false
  saveInSrc = true
}
