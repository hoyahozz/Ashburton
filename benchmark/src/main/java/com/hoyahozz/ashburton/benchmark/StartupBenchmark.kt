package com.hoyahozz.ashburton.benchmark

import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.StartupTimingMetric
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import org.junit.Rule
import org.junit.Test

class StartupBenchmark {
  @get:Rule val benchmarkRule = MacrobenchmarkRule()

  @Test
  fun coldStartup() = benchmarkRule.measureRepeated(
    packageName = TARGET_PACKAGE,
    metrics = listOf(StartupTimingMetric()),
    compilationMode = CompilationMode.None(),
    startupMode = StartupMode.COLD,
    iterations = 10,
    setupBlock = { pressHome() },
  ) {
    startActivityAndWait()
  }
}

private const val TARGET_PACKAGE = "com.hoyahozz.ashburton"
