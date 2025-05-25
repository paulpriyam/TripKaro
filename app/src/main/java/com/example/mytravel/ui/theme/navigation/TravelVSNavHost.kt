package com.example.mytravel.ui.theme.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mytravel.ui.theme.ui.screens.bookings.BookingsScreen
import com.example.mytravel.ui.theme.ui.screens.expenses.ExpenseScreen
import com.example.mytravel.ui.theme.ui.screens.itinerary.ItineraryScreen
import com.example.mytravel.ui.theme.ui.screens.members.MembersScreen
import com.example.mytravel.ui.theme.ui.screens.profile.ProfileScreen

@Composable
fun TravelVSNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "members/trip123" // default trip ID for demo
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(
            route = "members/{tripId}",
            arguments = listOf(navArgument("tripId") { type = NavType.StringType })
        ) { backStackEntry ->
            val tripId = backStackEntry.arguments?.getString("tripId") ?: "trip123"
            MembersScreen(
                tripId = tripId,
                navigateToItinerary = { navController.navigate("itinerary/$tripId") },
                navigateToExpenses = { navController.navigate("expenses/$tripId") },
                navigateToBookings = { navController.navigate("bookings/$tripId") },
                navigateToProfile = { navController.navigate("profile") }
            )
        }
        
        composable(
            route = "itinerary/{tripId}",
            arguments = listOf(navArgument("tripId") { type = NavType.StringType })
        ) { backStackEntry ->
            val tripId = backStackEntry.arguments?.getString("tripId") ?: "trip123"
            ItineraryScreen(
                tripId = tripId,
                navigateToMembers = { navController.navigate("members/$tripId") },
                navigateToExpenses = { navController.navigate("expenses/$tripId") },
                navigateToBookings = { navController.navigate("bookings/$tripId") },
                navigateToProfile = { navController.navigate("profile") }
            )
        }
        
        composable(
            route = "expenses/{tripId}",
            arguments = listOf(navArgument("tripId") { type = NavType.StringType })
        ) { backStackEntry ->
            val tripId = backStackEntry.arguments?.getString("tripId") ?: "trip123"
            ExpenseScreen(
                tripId = tripId,
                navigateToMembers = { navController.navigate("members/$tripId") },
                navigateToItinerary = { navController.navigate("itinerary/$tripId") },
                navigateToBookings = { navController.navigate("bookings/$tripId") },
                navigateToProfile = { navController.navigate("profile") }
            )
        }
        
        composable(
            route = "bookings/{tripId}",
            arguments = listOf(navArgument("tripId") { type = NavType.StringType })
        ) { backStackEntry ->
            val tripId = backStackEntry.arguments?.getString("tripId") ?: "trip123"
            BookingsScreen(
                tripId = tripId,
                navigateToMembers = { navController.navigate("members/$tripId") },
                navigateToItinerary = { navController.navigate("itinerary/$tripId") },
                navigateToExpenses = { navController.navigate("expenses/$tripId") },
                navigateToProfile = { navController.navigate("profile") }
            )
        }
        
        composable(route = "profile") {
            ProfileScreen(onNavigateBack = { navController.popBackStack() })
        }
    }
}
