package com.hoyahozz.ashburton.benchmark

import androidx.benchmark.macro.junit4.BaselineProfileRule
import org.junit.Rule
import org.junit.Test

class BaselineProfileGenerator {
  @get:Rule val baselineProfileRule = BaselineProfileRule()

  @Test
  fun generate() = baselineProfileRule.collect(
    packageName = TARGET_PACKAGE,
    includeInStartupProfile = true,
  ) {
    pressHome()
    startActivityAndWait()
  }
}

private const val TARGET_PACKAGE = "com.hoyahozz.ashburton"
