package com.example.mytravel.ui.theme.ui.screens.members

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mytravel.R
import com.example.mytravel.ui.theme.data.model.TripMember
import com.example.mytravel.ui.theme.ui.components.ErrorScreen
import com.example.mytravel.ui.theme.ui.components.LoadingScreen
import com.example.mytravel.ui.theme.ui.components.TravelVSBottomNavigation
import com.example.mytravel.ui.theme.ui.components.TravelVSTopAppBar
import com.example.mytravel.ui.theme.ui.components.TripMemberItem
import com.example.travelvs.ui.theme.TravelVSTheme
import com.example.mytravel.ui.theme.utils.UiState

@Composable
fun MembersScreen(
    tripId: String,
    viewModel: MembersViewModel = hiltViewModel(),
    navigateToItinerary: () -> Unit,
    navigateToExpenses: () -> Unit,
    navigateToBookings: () -> Unit,
    navigateToProfile: () -> Unit
) {
    val membersState by viewModel.membersState.collectAsState()
    val showAddDialog by viewModel.showAddDialog.collectAsState()
    
    LaunchedEffect(tripId) {
        viewModel.fetchMembers(tripId)
    }
    
    Scaffold(
        topBar = {
            TravelVSTopAppBar(title = stringResource(R.string.trip_members))
        },
        bottomBar = {
            TravelVSBottomNavigation(
                currentRoute = "members",
                onNavigateToTrips = { /* Already on this screen */ },
                onNavigateToItinerary = navigateToItinerary,
                onNavigateToExpenses = navigateToExpenses,
                onNavigateToBookings = navigateToBookings,
                onNavigateToProfile = navigateToProfile
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.showAddMemberDialog() }
            ) {
                Icon(Icons.Default.Add, contentDescription = stringResource(R.string.add_member))
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (membersState) {
                is UiState.Loading -> LoadingScreen()
                is UiState.Error -> ErrorScreen((membersState as UiState.Error).message)
                is UiState.Success -> {
                    val members = (membersState as UiState.Success<List<TripMember>>).data
                    MembersContent(members = members)
                }
            }
        }
    }
    
    if (showAddDialog) {
        AddMemberDialog(
            tripId = tripId,
            viewModel = viewModel,
            onDismiss = { viewModel.hideAddMemberDialog() }
        )
    }
}

@Composable
fun MembersContent(members: List<TripMember>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(members) { member ->
            TripMemberItem(
                name = member.name,
                avatar = member.avatar,
                role = member.role,
                onItemClick = { /* Handle member click */ }
            )
        }
    }
}

@Composable
fun AddMemberDialog(
    tripId: String,
    viewModel: MembersViewModel,
    onDismiss: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    val addMemberState by viewModel.addMemberState.collectAsState()
    
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.add_member),
                        style = MaterialTheme.typography.titleLarge
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = addMemberState is UiState.Error
                )
                
                if (addMemberState is UiState.Error) {
                    Text(
                        text = (addMemberState as UiState.Error).message,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 8.dp, top = 4.dp)
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Button(
                    onClick = { viewModel.addMember(tripId, email) },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = email.isNotEmpty() && addMemberState !is UiState.Loading
                ) {
                    if (addMemberState is UiState.Loading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = MaterialTheme.colorScheme.onPrimary,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text(text = stringResource(R.string.save))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MembersContentPreview() {
    val previewMembers = listOf(
        TripMember("1", "Ethan Carter", null, "Organizer"),
        TripMember("2", "Sophia Bennett", null, "Guest"),
        TripMember("3", "Liam Harper", null, "Guest"),
        TripMember("4", "Olivia Foster", null, "Guest"),
        TripMember("5", "Noah Parker", null, "Guest")
    )
    
    TravelVSTheme {
        Surface {
            MembersContent(members = previewMembers)
        }
    }
}
