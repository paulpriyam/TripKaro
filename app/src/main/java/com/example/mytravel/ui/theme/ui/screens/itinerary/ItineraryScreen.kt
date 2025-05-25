package com.example.mytravel.ui.theme.ui.screens.itinerary

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mytravel.R
import com.example.mytravel.ui.theme.data.model.ItineraryItem
import com.example.mytravel.ui.theme.ui.components.ErrorScreen
import com.example.mytravel.ui.theme.ui.components.LoadingScreen
import com.example.mytravel.ui.theme.ui.components.TravelVSBottomNavigation
import com.example.mytravel.ui.theme.ui.components.TravelVSTopAppBar
import com.example.travelvs.ui.theme.TravelVSTheme
import com.example.mytravel.ui.theme.utils.UiState
// Replace Accompanist pager with Compose Foundation pager
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.filled.ThumbUp
import kotlinx.coroutines.launch


@Composable
fun ItineraryScreen(
    tripId: String,
    viewModel: ItineraryViewModel = hiltViewModel(),
    navigateToMembers: () -> Unit,
    navigateToExpenses: () -> Unit,
    navigateToBookings: () -> Unit,
    navigateToProfile: () -> Unit
) {
    val itineraryState by viewModel.itineraryState.collectAsState()
    val selectedDay by viewModel.selectedDay.collectAsState()
    
    LaunchedEffect(tripId) {
        viewModel.fetchItinerary(tripId)
    }
    
    Scaffold(
        topBar = {
            TravelVSTopAppBar(title = stringResource(R.string.screen_itinerary))
        },
        bottomBar = {
            TravelVSBottomNavigation(
                currentRoute = "itinerary",
                onNavigateToTrips = navigateToMembers,
                onNavigateToItinerary = { /* Already on this screen */ },
                onNavigateToExpenses = navigateToExpenses,
                onNavigateToBookings = navigateToBookings,
                onNavigateToProfile = navigateToProfile
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (itineraryState) {
                is UiState.Loading -> LoadingScreen()
                is UiState.Error -> ErrorScreen((itineraryState as UiState.Error).message)
                is UiState.Success -> {
                    val itineraryByDay = (itineraryState as UiState.Success<Map<Int, List<ItineraryItem>>>).data
                    if (itineraryByDay.isEmpty()) {
                        EmptyItineraryContent()
                    } else {
                        ItineraryContent(
                            itineraryByDay = itineraryByDay,
                            selectedDay = selectedDay,
                            onDaySelected = { viewModel.setSelectedDay(it) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ItineraryContent(
    itineraryByDay: Map<Int, List<ItineraryItem>>,
    selectedDay: Int,
    onDaySelected: (Int) -> Unit
) {
    val sortedDays = itineraryByDay.keys.sorted()
    val pagerState = rememberPagerState(
        initialPage = sortedDays.indexOf(selectedDay).coerceAtLeast(0),
        pageCount = { sortedDays.size }
    )
    val scope = rememberCoroutineScope()
    
    LaunchedEffect(selectedDay) {
        if (sortedDays.contains(selectedDay)) {
            val dayIndex = sortedDays.indexOf(selectedDay)
            if (dayIndex >= 0) {
                pagerState.animateScrollToPage(dayIndex)
            }
        }
    }
    
    Column {
        // Day tabs
        ScrollableTabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.primary,
            edgePadding = 16.dp
        ) {
            sortedDays.forEachIndexed { index, day ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                        onDaySelected(day)
                    },
                    text = {
                        Text(
                            text = stringResource(R.string.day_format, day),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                )
            }
        }
        
        // Day content
        HorizontalPager(
            state = pagerState
        ) { page ->
            val dayNumber = sortedDays[page]
            val dayItems = itineraryByDay[dayNumber] ?: emptyList()
            
            Column(modifier = Modifier.fillMaxSize()) {
                DayItineraryContent(dayItems = dayItems)
            }
        }
    }
    
    // When page changes, update the selected day
    LaunchedEffect(pagerState.currentPage) {
        if (sortedDays.isNotEmpty() && pagerState.currentPage < sortedDays.size) {
            onDaySelected(sortedDays[pagerState.currentPage])
        }
    }
}

@Composable
fun DayItineraryContent(dayItems: List<ItineraryItem>) {
    val groupedItems = remember(dayItems) {
        dayItems.groupBy { it.type }
    }
    
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (groupedItems.containsKey("MORNING")) {
            item {
                Text(
                    text = stringResource(R.string.morning),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            items(groupedItems["MORNING"]!!) { item ->
                ItineraryItemCard(item = item)
            }
        }
        
        if (groupedItems.containsKey("AFTERNOON")) {
            item {
                Text(
                    text = stringResource(R.string.afternoon),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            items(groupedItems["AFTERNOON"]!!) { item ->
                ItineraryItemCard(item = item)
            }
        }
        
        if (groupedItems.containsKey("EVENING")) {
            item {
                Text(
                    text = stringResource(R.string.evening),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            items(groupedItems["EVENING"]!!) { item ->
                ItineraryItemCard(item = item)
            }
        }
    }
}

@Composable
fun ItineraryItemCard(item: ItineraryItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon based on location type (could be enhanced)
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleMedium
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.ThumbUp,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${item.startTime} - ${item.endTime}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
                
                if (item.location != null) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = item.location,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }
        }
    }
}

@Composable
fun EmptyItineraryContent() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "No itinerary items yet.",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ItineraryItemPreview() {
    val previewItem = ItineraryItem(
        id = "1",
        title = "Eiffel Tower",
        date = "2023-06-15",
        startTime = "9:00 AM",
        endTime = "12:00 PM",
        type = "MORNING",
        location = "Champ de Mars, Paris",
        day = 1
    )
    
    TravelVSTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            ItineraryItemCard(item = previewItem)
        }
    }
}

