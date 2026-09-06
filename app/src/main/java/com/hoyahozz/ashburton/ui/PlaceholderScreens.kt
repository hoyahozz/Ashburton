package com.hoyahozz.ashburton.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun RootPlaceholderScreen(
  title: String,
  description: String,
  modifier: Modifier = Modifier,
  actionLabel: String? = null,
  onAction: (() -> Unit)? = null,
) {
  Column(
    modifier = modifier.fillMaxSize().padding(32.dp),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Text(
      text = title,
      style = MaterialTheme.typography.headlineLarge,
      textAlign = TextAlign.Center,
    )
    Spacer(Modifier.height(12.dp))
    Text(
      text = description,
      style = MaterialTheme.typography.bodyLarge,
      textAlign = TextAlign.Center,
    )
    if (actionLabel != null && onAction != null) {
      Spacer(Modifier.height(24.dp))
      Button(onClick = onAction) { Text(actionLabel) }
    }
  }
}

@Composable
fun DetailPlaceholderScreen(
  title: String,
  stableId: String,
  modifier: Modifier = Modifier,
) {
  Column(
    modifier = modifier.fillMaxSize().padding(32.dp),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Text(
      text = title,
      style = MaterialTheme.typography.headlineLarge,
      textAlign = TextAlign.Center,
    )
    Spacer(Modifier.height(12.dp))
    Text(
      text = "ID: $stableId",
      style = MaterialTheme.typography.bodyLarge,
      textAlign = TextAlign.Center,
    )
  }
}
