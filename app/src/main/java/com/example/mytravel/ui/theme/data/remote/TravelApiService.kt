package com.example.mytravel.ui.theme.data.remote

import com.example.mytravel.ui.theme.data.model.Expense
import com.example.mytravel.ui.theme.data.model.ItineraryItem
import com.example.mytravel.ui.theme.data.model.Trip
import com.example.mytravel.ui.theme.data.model.TripMember
import com.example.mytravel.ui.theme.data.model.User
import retrofit2.Response
import retrofit2.http.*

interface TravelApiService {
    // User endpoints
    @GET("users/{userId}")
    suspend fun getUser(@Path("userId") userId: String): Response<User>
    
    // Trip endpoints
    @GET("trips")
    suspend fun getTrips(): Response<List<Trip>>
    
    @GET("trips/{tripId}")
    suspend fun getTrip(@Path("tripId") tripId: String): Response<Trip>
    
    // Trip members
    @GET("trips/{tripId}/members")
    suspend fun getTripMembers(@Path("tripId") tripId: String): Response<List<TripMember>>
    
    @POST("trips/{tripId}/members")
    suspend fun addTripMember(
        @Path("tripId") tripId: String,
        @Body member: Map<String, String>
    ): Response<TripMember>
    
    // Itinerary
    @GET("trips/{tripId}/itinerary")
    suspend fun getTripItinerary(@Path("tripId") tripId: String): Response<List<ItineraryItem>>
    
    // Expenses
    @GET("trips/{tripId}/expenses")
    suspend fun getTripExpenses(@Path("tripId") tripId: String): Response<List<Expense>>
    
    @POST("trips/{tripId}/expenses")
    suspend fun addExpense(
        @Path("tripId") tripId: String,
        @Body expense: Map<String, Any>
    ): Response<Expense>
    
    // Bookings
    @GET("trips/{tripId}/bookings")
    suspend fun getTripBookings(@Path("tripId") tripId: String): Response<Map<String, List<Any>>>
}
