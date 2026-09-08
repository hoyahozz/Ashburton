package com.hoyahozz.ashburton.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSerializable
import androidx.compose.runtime.setValue
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberDecoratedNavEntries
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.runtime.serialization.NavKeySerializer
import androidx.savedstate.compose.serialization.serializers.MutableStateSerializer

val TopLevelRoutes: List<TopLevelRoute> =
  listOf(MatchesRoot, SquadRoot, StoriesRoot, MoreRoot)

@Composable
fun rememberAshburtonNavigationState(
  startRoute: TopLevelRoute = MatchesRoot,
  topLevelRoutes: List<TopLevelRoute> = TopLevelRoutes,
): AshburtonNavigationState {
  val currentTopLevelRoute =
    rememberSerializable(
      startRoute,
      topLevelRoutes,
      serializer = MutableStateSerializer(NavKeySerializer()),
    ) {
      mutableStateOf<NavKey>(startRoute)
    }
  val backStacks = topLevelRoutes.associateWith { route -> rememberNavBackStack(route) }

  return remember(startRoute, topLevelRoutes) {
    AshburtonNavigationState(
      startRoute = startRoute,
      currentTopLevelRoute = currentTopLevelRoute,
      backStacks = backStacks,
    )
  }
}

class AshburtonNavigationState(
  val startRoute: TopLevelRoute,
  currentTopLevelRoute: MutableState<NavKey>,
  val backStacks: Map<TopLevelRoute, NavBackStack<NavKey>>,
) {
  private var savedTopLevelRoute: NavKey by currentTopLevelRoute
  var currentTopLevelRoute: TopLevelRoute
    get() = savedTopLevelRoute as TopLevelRoute
    set(value) {
      savedTopLevelRoute = value
    }

  @Composable
  fun decoratedEntries(entryProvider: (NavKey) -> NavEntry<NavKey>): List<NavEntry<NavKey>> {
    val decoratedEntries =
      backStacks.mapValues { (_, backStack) ->
        rememberDecoratedNavEntries(
          backStack = backStack,
          entryDecorators = listOf(rememberSaveableStateHolderNavEntryDecorator()),
          entryProvider = entryProvider,
        )
      }

    val visibleTopLevelRoutes =
      if (currentTopLevelRoute == startRoute) {
        listOf(startRoute)
      } else {
        listOf(startRoute, currentTopLevelRoute)
      }
    return visibleTopLevelRoutes.flatMap { route -> decoratedEntries[route].orEmpty() }
  }
}
