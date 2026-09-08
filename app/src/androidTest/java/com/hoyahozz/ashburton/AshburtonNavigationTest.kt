package com.hoyahozz.ashburton

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.StateRestorationTester
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.v2.runComposeUiTest
import com.hoyahozz.ashburton.theme.AshburtonTheme
import org.junit.Test

@OptIn(ExperimentalTestApi::class)
class AshburtonNavigationTest {
  @Test
  fun switchingTabsKeepsEachDetailScreen() = runComposeUiTest {
    setContent { AshburtonTheme { AshburtonApp() } }

    onNodeWithText("Squad").performClick()
    onNodeWithText("Open player").performClick()
    onNodeWithText("Player detail").assertIsDisplayed()

    onNodeWithText("Matches").performClick()
    onNodeWithText("Open match").performClick()
    onNodeWithText("Match detail").assertIsDisplayed()

    onNodeWithText("Squad").performClick()
    onNodeWithText("Player detail").assertIsDisplayed()
  }

  @Test
  fun restoringStateReopensTheSelectedStory() = runComposeUiTest {
    val restorationTester = StateRestorationTester(this)
    restorationTester.setContent { AshburtonTheme { AshburtonApp() } }

    onNodeWithText("Stories").performClick()
    onNodeWithText("Open story").performClick()
    onNodeWithText("Story detail").assertIsDisplayed()

    restorationTester.emulateSaveAndRestore()

    onNodeWithText("Story detail").assertIsDisplayed()
    onNodeWithText("ID: sample-story").assertIsDisplayed()
  }
}
