package com.hoyahozz.ashburton.navigation

import androidx.compose.runtime.mutableStateOf
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import org.junit.Assert.assertEquals
import org.junit.Test

class AshburtonNavigatorTest {
  @Test
  fun backPopsTheSelectedTabBeforeReturningToMatches() {
    val state =
      AshburtonNavigationState(
        startRoute = MatchesRoot,
        currentTopLevelRoute = mutableStateOf<NavKey>(MatchesRoot),
        backStacks =
        TopLevelRoutes.associateWith { route ->
          NavBackStack<NavKey>(route)
        },
      )
    val navigator = AshburtonNavigator(state)

    navigator.navigate(SquadRoot)
    navigator.navigate(PlayerDetail("player-7"))
    navigator.navigate(MatchesRoot)
    navigator.navigate(MatchDetail("match-42"))

    assertEquals(
      listOf(MatchesRoot, MatchDetail("match-42")),
      state.backStacks.getValue(MatchesRoot).toList(),
    )
    assertEquals(
      listOf(SquadRoot, PlayerDetail("player-7")),
      state.backStacks.getValue(SquadRoot).toList(),
    )

    navigator.goBack()
    assertEquals(MatchesRoot, state.currentTopLevelRoute)
    assertEquals(listOf(MatchesRoot), state.backStacks.getValue(MatchesRoot).toList())

    navigator.navigate(SquadRoot)
    navigator.goBack()
    assertEquals(SquadRoot, state.currentTopLevelRoute)
    assertEquals(listOf(SquadRoot), state.backStacks.getValue(SquadRoot).toList())

    navigator.goBack()
    assertEquals(MatchesRoot, state.currentTopLevelRoute)
  }
}
