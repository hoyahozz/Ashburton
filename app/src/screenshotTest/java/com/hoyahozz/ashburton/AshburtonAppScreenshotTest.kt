package com.hoyahozz.ashburton

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigationevent.compose.LocalNavigationEventDispatcherOwner
import androidx.navigationevent.compose.rememberNavigationEventDispatcherOwner
import com.android.tools.screenshot.PreviewTest
import com.hoyahozz.ashburton.theme.AshburtonTheme

@PreviewTest
@Preview(
  name = "Matches home",
  widthDp = 360,
  heightDp = 800,
  locale = "en-rGB",
  fontScale = 1.0f,
  uiMode = Configuration.UI_MODE_NIGHT_NO,
  showBackground = true,
  backgroundColor = 0xFFFFFFFF,
)
@Composable
fun AshburtonAppScreenshot() {
  val navigationOwner = rememberNavigationEventDispatcherOwner(parent = null)
  CompositionLocalProvider(LocalNavigationEventDispatcherOwner provides navigationOwner) {
    AshburtonTheme(
      darkTheme = false,
      dynamicColor = false,
    ) {
      AshburtonApp()
    }
  }
}
