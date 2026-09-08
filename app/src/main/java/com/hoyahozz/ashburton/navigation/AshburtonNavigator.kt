package com.hoyahozz.ashburton.navigation

class AshburtonNavigator(private val state: AshburtonNavigationState) {
  fun navigate(route: AshburtonRoute) {
    if (route is TopLevelRoute) {
      state.currentTopLevelRoute = route
      return
    }

    state.backStacks.getValue(state.currentTopLevelRoute).add(route)
  }

  fun goBack() {
    val currentBackStack = state.backStacks.getValue(state.currentTopLevelRoute)
    if (currentBackStack.last() == state.currentTopLevelRoute) {
      state.currentTopLevelRoute = state.startRoute
    } else {
      currentBackStack.removeLastOrNull()
    }
  }
}
