package com.hoyahozz.ashburton

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.hoyahozz.ashburton.navigation.AshburtonNavigator
import com.hoyahozz.ashburton.navigation.MatchDetail
import com.hoyahozz.ashburton.navigation.MatchesRoot
import com.hoyahozz.ashburton.navigation.MoreRoot
import com.hoyahozz.ashburton.navigation.PlayerDetail
import com.hoyahozz.ashburton.navigation.SquadRoot
import com.hoyahozz.ashburton.navigation.StoriesRoot
import com.hoyahozz.ashburton.navigation.StoryDetail
import com.hoyahozz.ashburton.navigation.TopLevelRoute
import com.hoyahozz.ashburton.navigation.rememberAshburtonNavigationState
import com.hoyahozz.ashburton.ui.DetailPlaceholderScreen
import com.hoyahozz.ashburton.ui.RootPlaceholderScreen

private data class TopLevelDestination(
  val route: TopLevelRoute,
  val label: String,
  val iconLabel: String,
)

private val topLevelDestinations =
  listOf(
    TopLevelDestination(MatchesRoot, "Matches", "M"),
    TopLevelDestination(SquadRoot, "Squad", "S"),
    TopLevelDestination(StoriesRoot, "Stories", "N"),
    TopLevelDestination(MoreRoot, "More", "⋯"),
  )

@Composable
fun AshburtonApp() {
  val navigationState = rememberAshburtonNavigationState()
  val navigator = remember(navigationState) { AshburtonNavigator(navigationState) }
  val entryProvider =
    entryProvider<NavKey> {
      entry<MatchesRoot> {
        RootPlaceholderScreen(
          title = "Matches",
          description = "Upcoming fixtures and recent results.",
          actionLabel = "Open match",
          onAction = { navigator.navigate(MatchDetail("sample-match")) },
        )
      }
      entry<MatchDetail> { route ->
        DetailPlaceholderScreen(title = "Match detail", stableId = route.matchId)
      }
      entry<SquadRoot> {
        RootPlaceholderScreen(
          title = "Squad",
          description = "Explore the current first-team squad.",
          actionLabel = "Open player",
          onAction = { navigator.navigate(PlayerDetail("sample-player")) },
        )
      }
      entry<PlayerDetail> { route ->
        DetailPlaceholderScreen(title = "Player detail", stableId = route.playerId)
      }
      entry<StoriesRoot> {
        RootPlaceholderScreen(
          title = "Stories",
          description = "Follow stories with visible source context.",
          actionLabel = "Open story",
          onAction = { navigator.navigate(StoryDetail("sample-story")) },
        )
      }
      entry<StoryDetail> { route ->
        DetailPlaceholderScreen(title = "Story detail", stableId = route.storyId)
      }
      entry<MoreRoot> {
        RootPlaceholderScreen(
          title = "More",
          description = "Settings, sources, policies, and app information.",
        )
      }
    }

  Scaffold(
    contentWindowInsets = WindowInsets(0.dp),
    bottomBar = {
      NavigationBar {
        topLevelDestinations.forEach { destination ->
          NavigationBarItem(
            selected = navigationState.currentTopLevelRoute == destination.route,
            onClick = { navigator.navigate(destination.route) },
            icon = { Text(destination.iconLabel) },
            label = { Text(destination.label) },
          )
        }
      }
    },
  ) { innerPadding ->
    NavDisplay(
      entries = navigationState.decoratedEntries(entryProvider),
      onBack = navigator::goBack,
      modifier = Modifier.fillMaxSize().padding(innerPadding),
    )
  }
}
