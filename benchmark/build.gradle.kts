plugins {
  alias(libs.plugins.android.test)
  alias(libs.plugins.androidx.baselineprofile)
  alias(libs.plugins.ktlint)
}

android {
  namespace = "com.hoyahozz.ashburton.benchmark"
  compileSdk = 37

  defaultConfig {
    minSdk = 26
    targetSdk = 37
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  targetProjectPath = ":app"

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }
}

kotlin {
  jvmToolchain(17)
}

baselineProfile {
  skipBenchmarksOnEmulator = false
}

ktlint {
  version.set(libs.versions.ktlint)
}

dependencies {
  implementation(libs.androidx.benchmark.macro.junit4)
}
