package com.example.mytravel.ui.theme.ui.screens.bookings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mytravel.ui.theme.data.model.AccommodationBooking
import com.example.mytravel.ui.theme.data.model.ActivityBooking
import com.example.mytravel.ui.theme.data.model.FlightBooking
import com.example.mytravel.ui.theme.data.repository.BookingsData
import com.example.mytravel.ui.theme.ui.components.ErrorScreen
import com.example.mytravel.ui.theme.ui.components.LoadingScreen
import com.example.mytravel.R
import com.example.mytravel.ui.theme.ui.components.TravelVSBottomNavigation
import com.example.mytravel.ui.theme.ui.components.TravelVSTopAppBar
import com.example.travelvs.ui.theme.TravelVSTheme
import com.example.mytravel.ui.theme.utils.UiState

@Composable
fun BookingsScreen(
    tripId: String,
    viewModel: BookingsViewModel = hiltViewModel(),
    navigateToMembers: () -> Unit,
    navigateToItinerary: () -> Unit,
    navigateToExpenses: () -> Unit,
    navigateToProfile: () -> Unit
) {
    val bookingsState by viewModel.bookingsState.collectAsState()
    val selectedTabIndex by viewModel.selectedTabIndex.collectAsState()
    
    LaunchedEffect(tripId) {
        viewModel.fetchBookings(tripId)
    }
    
    Scaffold(
        topBar = {
            TravelVSTopAppBar(title = stringResource(R.string.screen_bookings))
        },
        bottomBar = {
            TravelVSBottomNavigation(
                currentRoute = "bookings",
                onNavigateToTrips = navigateToMembers,
                onNavigateToItinerary = navigateToItinerary,
                onNavigateToExpenses = navigateToExpenses,
                onNavigateToBookings = { /* Already on this screen */ },
                onNavigateToProfile = navigateToProfile
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (bookingsState) {
                is UiState.Loading -> LoadingScreen()
                is UiState.Error -> ErrorScreen((bookingsState as UiState.Error).message)
                is UiState.Success -> {
                    val bookings = (bookingsState as UiState.Success<BookingsData>).data
                    BookingsContent(
                        bookings = bookings,
                        selectedTabIndex = selectedTabIndex,
                        onTabSelected = { viewModel.selectTab(it) }
                    )
                }
            }
        }
    }
}

@Composable
fun BookingsContent(
    bookings: BookingsData,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primary
        ) {
            Tab(
                selected = selectedTabIndex == 0,
                onClick = { onTabSelected(0) },
                text = { Text(stringResource(R.string.flights)) },
                icon = { Icon(Icons.Default.Call, contentDescription = null) }
            )
            
            Tab(
                selected = selectedTabIndex == 1,
                onClick = { onTabSelected(1) },
                text = { Text(stringResource(R.string.accommodations)) },
                icon = { Icon(Icons.Default.Home, contentDescription = null) }
            )
            
            Tab(
                selected = selectedTabIndex == 2,
                onClick = { onTabSelected(2) },
                text = { Text(stringResource(R.string.activities)) },
                icon = { Icon(Icons.Default.AccountCircle, contentDescription = null) }
            )
        }
        
        when (selectedTabIndex) {
            0 -> FlightsContent(flights = bookings.flights)
            1 -> AccommodationsContent(accommodations = bookings.accommodations)
            2 -> ActivitiesContent(activities = bookings.activities)
        }
    }
}

@Composable
fun FlightsContent(flights: List<FlightBooking>) {
    if (flights.isEmpty()) {
        EmptyBookingsContent("No flights booked yet.")
        return
    }
    
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(flights) { flight ->
            FlightItem(flight = flight)
        }
    }
}

@Composable
fun AccommodationsContent(accommodations: List<AccommodationBooking>) {
    if (accommodations.isEmpty()) {
        EmptyBookingsContent("No accommodations booked yet.")
        return
    }
    
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(accommodations) { accommodation ->
            AccommodationItem(accommodation = accommodation)
        }
    }
}

@Composable
fun ActivitiesContent(activities: List<ActivityBooking>) {
    if (activities.isEmpty()) {
        EmptyBookingsContent("No activities booked yet.")
        return
    }
    
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(activities) { activity ->
            ActivityItem(activity = activity)
        }
    }
}

@Composable
fun EmptyBookingsContent(message: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
    }
}

@Composable
fun FlightItem(flight: FlightBooking) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = flight.from,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = flight.departureTime,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
                
                Icon(
                    imageVector = Icons.Default.ExitToApp,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = flight.to,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = flight.arrivalTime,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }
            
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = flight.airline,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = flight.flightNumber,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccommodationItem(accommodation: AccommodationBooking) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = accommodation.name,
                style = MaterialTheme.typography.titleLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = accommodation.location,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
            
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Check-in",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = accommodation.checkIn,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                
                Column {
                    Text(
                        text = "Check-out",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = accommodation.checkOut,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityItem(activity: ActivityBooking) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = activity.title,
                style = MaterialTheme.typography.titleLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = activity.location,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Call,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = activity.date,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.ThumbUp,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${activity.startTime} - ${activity.endTime}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FlightItemPreview() {
    val flightBooking = FlightBooking(
        id = "1",
        title = "NYC to LA Flight",
        from = "New York",
        to = "Los Angeles",
        departureTime = "10:00 PM",
        arrivalTime = "2:00 PM",
        airline = "American Airlines",
        flightNumber = "AA-1234"
    )
    
    TravelVSTheme {
        Surface {
            FlightItem(flight = flightBooking)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AccommodationItemPreview() {
    val accommodationBooking = AccommodationBooking(
        id = "1",
        title = "Grand Hotel Stay",
        name = "The Grand Hotel",
        checkIn = "Jun 15, 2023",
        checkOut = "Jun 20, 2023",
        location = "Central Park, New York",
        image = null
    )
    
    TravelVSTheme {
        Surface {
            AccommodationItem(accommodation = accommodationBooking)
        }
    }
}

