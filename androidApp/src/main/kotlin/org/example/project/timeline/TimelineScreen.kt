package org.example.project.timeline

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimelineScreen(
    viewModel: TimelineViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    LaunchedEffect(Unit) {
        viewModel.loadEntries()
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopAppBar(
            title = { Text("タイムライン") },
            actions = {
                IconButton(onClick = { viewModel.toggleFilterDialog() }) {
                    Icon(Icons.Default.FilterList, contentDescription = "フィルター")
                }
            }
        )

        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            
            uiState.entries.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "まだ日記がありません",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
            
            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(uiState.filteredEntries) { entry ->
                        DiaryEntryCard(
                            entry = entry,
                            onClick = { viewModel.selectEntry(entry.id) }
                        )
                    }
                }
            }
        }
    }

    if (uiState.showFilterDialog) {
        FilterDialog(
            currentFilter = uiState.currentFilter,
            onDismiss = { viewModel.toggleFilterDialog() },
            onFilterApplied = { filter -> 
                viewModel.applyFilter(filter)
                viewModel.toggleFilterDialog()
            }
        )
    }
}

@Composable
private fun DiaryEntryCard(
    entry: DiaryEntryUi,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = entry.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                
                Text(
                    text = SimpleDateFormat("MM/dd", Locale.getDefault())
                        .format(Date(entry.createdAt)),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Text(
                text = entry.content,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (entry.mood != null) {
                    Chip(
                        onClick = { },
                        label = { Text("気分: ${entry.mood}/10") }
                    )
                }
                
                if (entry.weather.isNotBlank()) {
                    Chip(
                        onClick = { },
                        label = { Text(entry.weather) }
                    )
                }
            }
        }
    }
}

@Composable
private fun FilterDialog(
    currentFilter: TimelineFilter,
    onDismiss: () -> Unit,
    onFilterApplied: (TimelineFilter) -> Unit
) {
    var selectedFilter by remember { mutableStateOf(currentFilter) }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("フィルター") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("期間で絞り込み:")
                
                TimelineFilter.values().forEach { filter ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedFilter == filter,
                            onClick = { selectedFilter = filter }
                        )
                        Text(
                            text = filter.displayName,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onFilterApplied(selectedFilter) }
            ) {
                Text("適用")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("キャンセル")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Chip(
    onClick: () -> Unit,
    label: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    SuggestionChip(
        onClick = onClick,
        label = label,
        modifier = modifier
    )
}