plugins {
  alias(libs.plugins.android.application) apply false
  alias(libs.plugins.compose.compiler) apply false
  alias(libs.plugins.kotlin.serialization) apply false
  alias(libs.plugins.ktlint)
  alias(libs.plugins.ksp) apply false
  alias(libs.plugins.androidx.room) apply false
  alias(libs.plugins.compose.screenshot) apply false
  alias(libs.plugins.android.test) apply false
  alias(libs.plugins.androidx.baselineprofile) apply false
}

ktlint {
  version.set(libs.versions.ktlint)
}
