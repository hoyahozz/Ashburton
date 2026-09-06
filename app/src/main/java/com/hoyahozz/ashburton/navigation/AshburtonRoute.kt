package com.hoyahozz.ashburton.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface AshburtonRoute : NavKey

sealed interface TopLevelRoute : AshburtonRoute

@Serializable data object MatchesRoot : TopLevelRoute

@Serializable data class MatchDetail(val matchId: String) : AshburtonRoute

@Serializable data object SquadRoot : TopLevelRoute

@Serializable data class PlayerDetail(val playerId: String) : AshburtonRoute

@Serializable data object StoriesRoot : TopLevelRoute

@Serializable data class StoryDetail(val storyId: String) : AshburtonRoute

@Serializable data object MoreRoot : TopLevelRoute
