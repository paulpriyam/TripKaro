package com.example.mytravel.ui.theme.ui.screens.profile

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mytravel.ui.theme.data.model.User
import com.example.mytravel.ui.theme.ui.components.ErrorScreen
import com.example.mytravel.ui.theme.ui.components.LoadingScreen
import com.example.mytravel.ui.theme.ui.components.TravelVSTopAppBar
import com.example.travelvs.ui.theme.TravelVSTheme
import com.example.mytravel.ui.theme.utils.UiState

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val userState by viewModel.userState.collectAsState()
    val showShareCodeDialog by viewModel.showShareCodeDialog.collectAsState()
    
    // In a real app, we'd get this from a logged-in user session
    val currentUserId = "user123"
    
    LaunchedEffect(currentUserId) {
        viewModel.fetchUserProfile(currentUserId)
    }
    
    Scaffold(
        topBar = {
            TravelVSTopAppBar(
                title = "Profile",
                onNavigateBack = onNavigateBack
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (userState) {
                is UiState.Loading -> LoadingScreen()
                is UiState.Error -> ErrorScreen((userState as UiState.Error).message)
                is UiState.Success -> {
                    val user = (userState as UiState.Success<User>).data
                    ProfileContent(
                        user = user,
                        onShareCodeClick = { viewModel.showShareCode() }
                    )
                }
            }
        }
    }
    
    if (showShareCodeDialog && userState is UiState.Success) {
        val user = (userState as UiState.Success<User>).data
        ShareCodeDialog(
            shareCode = user.shareCode ?: "No share code available",
            onDismiss = { viewModel.hideShareCode() }
        )
    }
}

@Composable
fun ProfileContent(user: User, onShareCodeClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Profile Picture
        if (user.avatar != null) {
            // In a real app, we'd use Coil to load the image
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
                    .padding(2.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = user.name.first().toString(),
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        } else {
            // Default avatar with first letter of name
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
                    .padding(2.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = user.name.first().toString(),
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Name
        Text(
            text = user.name,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Age
        user.age?.let {
            Text(
                text = "$it years old",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
            
            Spacer(modifier = Modifier.height(8.dp))
        }
        
        // Email
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = user.email,
                style = MaterialTheme.typography.bodyLarge
            )
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Share Code Button
        Button(
            onClick = onShareCodeClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Share,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Share Traveler Code")
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Additional options could be added here
        ProfileOptionItem(
            icon = Icons.Default.Settings,
            title = "Settings",
            onClick = { /* Navigate to settings */ }
        )
        
        ProfileOptionItem(
            icon = Icons.Default.Notifications,
            title = "Notifications",
            onClick = { /* Navigate to notifications */ }
        )
        
        ProfileOptionItem(
            icon = Icons.Default.Lock,
            title = "Privacy",
            onClick = { /* Navigate to privacy settings */ }
        )
        
        ProfileOptionItem(
            icon = Icons.Default.ExitToApp,
            title = "Log Out",
            onClick = { /* Log out user */ },
            tint = MaterialTheme.colorScheme.error
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileOptionItem(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit,
    tint: Color = MaterialTheme.colorScheme.primary
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = tint
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
            )
        }
    }
}

@Composable
fun ShareCodeDialog(shareCode: String, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Your Traveler Code",
                    style = MaterialTheme.typography.titleLarge
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = shareCode,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "Share this code with friends to add you to their trips",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Button(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Done")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileContentPreview() {
    val previewUser = User(
        id = "user123",
        name = "Liam Bennett",
        avatar = null,
        age = 28,
        email = "liam.bennett@example.com",
        shareCode = "TRVL-LB123"
    )
    
    TravelVSTheme {
        Surface {
            ProfileContent(
                user = previewUser,
                onShareCodeClick = {}
            )
        }
    }
}
