package com.example.mytravel.ui.theme.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.mytravel.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TravelVSTopAppBar(
    title: String,
    onNavigateBack: (() -> Unit)? = null
) {
    TopAppBar(
        title = @Composable{ Text(text = title) },
        navigationIcon = {
            if (onNavigateBack != null) {
                @Composable{
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Navigate back"
                        )
                    }
                }
            } else null
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    )
}

@Composable
fun TravelVSBottomNavigation(
    currentRoute: String,
    onNavigateToTrips: () -> Unit,
    onNavigateToItinerary: () -> Unit,
    onNavigateToExpenses: () -> Unit,
    onNavigateToBookings: () -> Unit,
    onNavigateToProfile: () -> Unit
) {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Settings, contentDescription = null) },
            label = { Text(stringResource(R.string.screen_members)) },
            selected = currentRoute.contains("members"),
            onClick = onNavigateToTrips
        )
        
        NavigationBarItem(
            icon = { Icon(Icons.Default.Call, contentDescription = null) },
            label = { Text(stringResource(R.string.screen_itinerary)) },
            selected = currentRoute.contains("itinerary"),
            onClick = onNavigateToItinerary
        )
        
        NavigationBarItem(
            icon = { Icon(Icons.Default.MoreVert, contentDescription = null) },
            label = { Text(stringResource(R.string.screen_expenses)) },
            selected = currentRoute.contains("expenses"),
            onClick = onNavigateToExpenses
        )
        
        NavigationBarItem(
            icon = { Icon(Icons.Default.Favorite, contentDescription = null) },
            label = { Text(stringResource(R.string.screen_bookings)) },
            selected = currentRoute.contains("bookings"),
            onClick = onNavigateToBookings
        )
        
        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = null) },
            label = { Text(stringResource(R.string.screen_profile)) },
            selected = currentRoute.contains("profile"),
            onClick = onNavigateToProfile
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripMemberItem(
    name: String,
    avatar: String?,
    role: String,
    onItemClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar placeholder (would use Coil in implementation)
            Surface(
                modifier = Modifier.size(40.dp),
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primary
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = name.first().toString(),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = role,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            
            IconButton(onClick = onItemClick) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More options"
                )
            }
        }
    }
}


