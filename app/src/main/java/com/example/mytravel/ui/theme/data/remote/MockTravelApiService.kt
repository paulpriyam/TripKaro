package com.example.mytravel.ui.theme.data.remote

import com.example.mytravel.ui.theme.data.model.*
import kotlinx.coroutines.delay
import retrofit2.Response
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * Mock implementation of TravelApiService that returns dummy data
 */
class MockTravelApiService : TravelApiService {

    // Trips data
    private val trips = listOf(
        Trip(
            id = "trip1",
            name = "Paris Adventure",
            destination = "Paris, France",
            startDate = "2025-06-10",
            endDate = "2025-06-17",
            budget = 5000.0,
            remaining = 2800.0,
            memberCount = 3,
            image = "https://images.unsplash.com/photo-1499856871958-5b9357976b82?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=2340&q=80"
        ),
        Trip(
            id = "trip2",
            name = "Tokyo Exploration",
            destination = "Tokyo, Japan",
            startDate = "2025-07-15",
            endDate = "2025-07-25",
            budget = 8000.0,
            remaining = 4980.0,
            memberCount = 4,
            image = "https://images.unsplash.com/photo-1540959733332-eab4deabeeaf?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=2371&q=80"
        ),
        Trip(
            id = "trip3",
            name = "New York Weekend",
            destination = "New York, USA",
            startDate = "2025-08-01",
            endDate = "2025-08-03",
            budget = 3000.0,
            remaining = 1450.0,
            memberCount = 2,
            image = "https://images.unsplash.com/photo-1496442226666-8d4d0e62e6e9?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=2370&q=80"
        )
    )

    // Trip members
    private val tripMembers = mapOf(
        "trip1" to listOf(
            TripMember(id = "user1", name = "You", avatar = "https://randomuser.me/api/portraits/women/44.jpg", role = "ADMIN"),
            TripMember(id = "user2", name = "Alex Johnson", avatar = "https://randomuser.me/api/portraits/men/32.jpg", role = "MEMBER"),
            TripMember(id = "user3", name = "Taylor Swift", avatar = "https://randomuser.me/api/portraits/women/68.jpg", role = "MEMBER")
        ),
        "trip2" to listOf(
            TripMember(id = "user1", name = "You", avatar = "https://randomuser.me/api/portraits/women/44.jpg", role = "ADMIN"),
            TripMember(id = "user4", name = "Chris Evans", avatar = "https://randomuser.me/api/portraits/men/91.jpg", role = "MEMBER"),
            TripMember(id = "user5", name = "Emma Watson", avatar = "https://randomuser.me/api/portraits/women/28.jpg", role = "MEMBER"),
            TripMember(id = "user6", name = "Tom Holland", avatar = "https://randomuser.me/api/portraits/men/65.jpg", role = "MEMBER")
        ),
        "trip3" to listOf(
            TripMember(id = "user1", name = "You", avatar = "https://randomuser.me/api/portraits/women/44.jpg", role = "ADMIN"),
            TripMember(id = "user7", name = "Robert Downey Jr", avatar = "https://randomuser.me/api/portraits/men/94.jpg", role = "MEMBER")
        )
    )

    // Itinerary items
    private val itineraryItems = mapOf(
        "trip1" to listOf(
            // Day 1
            ItineraryItem(id = "item1", title = "Arrival at Charles de Gaulle Airport", date = "2025-06-10", startTime = "09:00 AM", endTime = "10:30 AM", type = "MORNING", location = "Charles de Gaulle Airport", day = 1),
            ItineraryItem(id = "item2", title = "Hotel Check-in", date = "2025-06-10", startTime = "12:00 PM", endTime = "01:00 PM", type = "AFTERNOON", location = "Le Grand Hotel Paris", day = 1),
            ItineraryItem(id = "item3", title = "Dinner at Le Jules Verne", date = "2025-06-10", startTime = "07:00 PM", endTime = "09:00 PM", type = "EVENING", location = "Eiffel Tower, 2nd floor", day = 1),

            // Day 2
            ItineraryItem(id = "item4", title = "Louvre Museum Tour", date = "2025-06-11", startTime = "10:00 AM", endTime = "01:00 PM", type = "MORNING", location = "Louvre Museum", day = 2),
            ItineraryItem(id = "item5", title = "Seine River Cruise", date = "2025-06-11", startTime = "03:00 PM", endTime = "04:30 PM", type = "AFTERNOON", location = "Pont de l'Alma", day = 2),
            ItineraryItem(id = "item6", title = "Moulin Rouge Show", date = "2025-06-11", startTime = "08:00 PM", endTime = "10:00 PM", type = "EVENING", location = "Moulin Rouge", day = 2),

            // Day 3
            ItineraryItem(id = "item7", title = "Eiffel Tower Visit", date = "2025-06-12", startTime = "09:00 AM", endTime = "11:30 AM", type = "MORNING", location = "Eiffel Tower", day = 3),
            ItineraryItem(id = "item8", title = "Lunch at Cafe de Flore", date = "2025-06-12", startTime = "12:30 PM", endTime = "02:00 PM", type = "AFTERNOON", location = "Cafe de Flore", day = 3),
            ItineraryItem(id = "item9", title = "Shopping at Champs-Élysées", date = "2025-06-12", startTime = "03:00 PM", endTime = "06:00 PM", type = "AFTERNOON", location = "Champs-Élysées", day = 3)
        ),
        "trip2" to listOf(
            // Day 1
            ItineraryItem(id = "item10", title = "Arrival at Haneda Airport", date = "2025-07-15", startTime = "10:00 AM", endTime = "11:30 AM", type = "MORNING", location = "Haneda Airport", day = 1),
            ItineraryItem(id = "item11", title = "Hotel Check-in", date = "2025-07-15", startTime = "01:00 PM", endTime = "02:00 PM", type = "AFTERNOON", location = "Park Hyatt Tokyo", day = 1),
            ItineraryItem(id = "item12", title = "Shibuya Crossing Experience", date = "2025-07-15", startTime = "04:00 PM", endTime = "06:00 PM", type = "EVENING", location = "Shibuya Crossing", day = 1),

            // Day 2
            ItineraryItem(id = "item13", title = "Tokyo Skytree Visit", date = "2025-07-16", startTime = "09:00 AM", endTime = "11:00 AM", type = "MORNING", location = "Tokyo Skytree", day = 2),
            ItineraryItem(id = "item14", title = "Lunch at Ichiran Ramen", date = "2025-07-16", startTime = "12:00 PM", endTime = "01:30 PM", type = "AFTERNOON", location = "Ichiran Ramen, Shibuya", day = 2),
            ItineraryItem(id = "item15", title = "Akihabara Electric Town Tour", date = "2025-07-16", startTime = "02:30 PM", endTime = "05:30 PM", type = "AFTERNOON", location = "Akihabara", day = 2)
        ),
        "trip3" to listOf(
            // Day 1
            ItineraryItem(id = "item16", title = "Arrival at JFK Airport", date = "2025-08-01", startTime = "08:00 AM", endTime = "09:30 AM", type = "MORNING", location = "JFK Airport", day = 1),
            ItineraryItem(id = "item17", title = "Times Square Visit", date = "2025-08-01", startTime = "11:00 AM", endTime = "01:00 PM", type = "MORNING", location = "Times Square", day = 1),
            ItineraryItem(id = "item18", title = "Broadway Show: Hamilton", date = "2025-08-01", startTime = "07:00 PM", endTime = "10:00 PM", type = "EVENING", location = "Richard Rodgers Theatre", day = 1),

            // Day 2
            ItineraryItem(id = "item19", title = "Statue of Liberty Ferry", date = "2025-08-02", startTime = "09:00 AM", endTime = "12:00 PM", type = "MORNING", location = "Battery Park", day = 2),
            ItineraryItem(id = "item20", title = "Lunch at Katz's Delicatessen", date = "2025-08-02", startTime = "01:00 PM", endTime = "02:30 PM", type = "AFTERNOON", location = "Katz's Delicatessen", day = 2),
            ItineraryItem(id = "item21", title = "Central Park Walk", date = "2025-08-02", startTime = "03:30 PM", endTime = "05:30 PM", type = "AFTERNOON", location = "Central Park", day = 2)
        )
    )

    // Expenses with contributions based on the provided Expense data class
    private val expenses = mapOf(
        "trip1" to listOf(
            Expense(
                id = "exp1",
                title = "Flights to Paris",
                amount = 850.0,
                date = "2025-06-10",
                category = "TRANSPORT",
                paidBy = "user1",
                isShared = true,
                contributions = listOf(
                    Contribution(userId = "user1", userName = "You", amount = 283.33),
                    Contribution(userId = "user2", userName = "Alex Johnson", amount = 283.33),
                    Contribution(userId = "user3", userName = "Taylor Swift", amount = 283.34)
                )
            ),
            Expense(
                id = "exp2",
                title = "Hotel Reservation",
                amount = 1200.0,
                date = "2025-06-10",
                category = "ACCOMMODATION",
                paidBy = "user2",
                isShared = true,
                contributions = listOf(
                    Contribution(userId = "user1", userName = "You", amount = 400.0),
                    Contribution(userId = "user2", userName = "Alex Johnson", amount = 400.0),
                    Contribution(userId = "user3", userName = "Taylor Swift", amount = 400.0)
                )
            ),
            Expense(
                id = "exp3",
                title = "Dinner at Le Jules Verne",
                amount = 350.0,
                date = "2025-06-10",
                category = "FOOD",
                paidBy = "user3",
                isShared = true,
                contributions = listOf(
                    Contribution(userId = "user1", userName = "You", amount = 116.67),
                    Contribution(userId = "user2", userName = "Alex Johnson", amount = 116.67),
                    Contribution(userId = "user3", userName = "Taylor Swift", amount = 116.66)
                )
            ),
            Expense(
                id = "exp4",
                title = "Louvre Museum Tickets",
                amount = 60.0,
                date = "2025-06-11",
                category = "ACTIVITIES",
                paidBy = "user1",
                isShared = true,
                contributions = listOf(
                    Contribution(userId = "user1", userName = "You", amount = 20.0),
                    Contribution(userId = "user2", userName = "Alex Johnson", amount = 20.0),
                    Contribution(userId = "user3", userName = "Taylor Swift", amount = 20.0)
                )
            ),
            Expense(
                id = "exp5",
                title = "Seine River Cruise",
                amount = 90.0,
                date = "2025-06-11",
                category = "ACTIVITIES",
                paidBy = "user1",
                isShared = true,
                contributions = listOf(
                    Contribution(userId = "user1", userName = "You", amount = 30.0),
                    Contribution(userId = "user2", userName = "Alex Johnson", amount = 30.0),
                    Contribution(userId = "user3", userName = "Taylor Swift", amount = 30.0)
                )
            )
        ),
        "trip2" to listOf(
            Expense(
                id = "exp6",
                title = "Flights to Tokyo",
                amount = 1200.0,
                date = "2025-07-15",
                category = "TRANSPORT",
                paidBy = "user1",
                isShared = true,
                contributions = listOf(
                    Contribution(userId = "user1", userName = "You", amount = 300.0),
                    Contribution(userId = "user4", userName = "Chris Evans", amount = 300.0),
                    Contribution(userId = "user5", userName = "Emma Watson", amount = 300.0),
                    Contribution(userId = "user6", userName = "Tom Holland", amount = 300.0)
                )
            ),
            Expense(
                id = "exp7",
                title = "Hotel in Tokyo",
                amount = 1800.0,
                date = "2025-07-15",
                category = "ACCOMMODATION",
                paidBy = "user4",
                isShared = true,
                contributions = listOf(
                    Contribution(userId = "user1", userName = "You", amount = 450.0),
                    Contribution(userId = "user4", userName = "Chris Evans", amount = 450.0),
                    Contribution(userId = "user5", userName = "Emma Watson", amount = 450.0),
                    Contribution(userId = "user6", userName = "Tom Holland", amount = 450.0)
                )
            ),
            Expense(
                id = "exp8",
                title = "Tokyo Metro Passes",
                amount = 80.0,
                date = "2025-07-15",
                category = "TRANSPORT",
                paidBy = "user5",
                isShared = true,
                contributions = listOf(
                    Contribution(userId = "user1", userName = "You", amount = 20.0),
                    Contribution(userId = "user4", userName = "Chris Evans", amount = 20.0),
                    Contribution(userId = "user5", userName = "Emma Watson", amount = 20.0),
                    Contribution(userId = "user6", userName = "Tom Holland", amount = 20.0)
                )
            ),
            Expense(
                id = "exp9",
                title = "Dinner at Sukiyabashi Jiro",
                amount = 300.0,
                date = "2025-07-15",
                category = "FOOD",
                paidBy = "user6",
                isShared = false,
                contributions = null
            )
        ),
        "trip3" to listOf(
            Expense(
                id = "exp10",
                title = "Flights to New York",
                amount = 450.0,
                date = "2025-08-01",
                category = "TRANSPORT",
                paidBy = "user1",
                isShared = true,
                contributions = listOf(
                    Contribution(userId = "user1", userName = "You", amount = 225.0),
                    Contribution(userId = "user7", userName = "Robert Downey Jr", amount = 225.0)
                )
            ),
            Expense(
                id = "exp11",
                title = "Hotel in Manhattan",
                amount = 600.0,
                date = "2025-08-01",
                category = "ACCOMMODATION",
                paidBy = "user7",
                isShared = true,
                contributions = listOf(
                    Contribution(userId = "user1", userName = "You", amount = 300.0),
                    Contribution(userId = "user7", userName = "Robert Downey Jr", amount = 300.0)
                )
            ),
            Expense(
                id = "exp12",
                title = "Hamilton Tickets",
                amount = 400.0,
                date = "2025-08-01",
                category = "ACTIVITIES",
                paidBy = "user1",
                isShared = true,
                contributions = listOf(
                    Contribution(userId = "user1", userName = "You", amount = 200.0),
                    Contribution(userId = "user7", userName = "Robert Downey Jr", amount = 200.0)
                )
            ),
            Expense(
                id = "exp13",
                title = "Taxi Rides",
                amount = 120.0,
                date = "2025-08-02",
                category = "TRANSPORT",
                paidBy = "user7",
                isShared = false,
                contributions = null
            )
        )
    )

    // Bookings
    private val bookings = mapOf(
        "trip1" to mapOf(
            "flights" to listOf(
                mapOf(
                    "id" to "booking1",
                    "type" to "FLIGHT",
                    "airline" to "Air France",
                    "flightNumber" to "AF1234",
                    "departure" to mapOf(
                        "airport" to "JFK",
                        "city" to "New York",
                        "date" to "2025-06-10",
                        "time" to "06:30 AM"
                    ),
                    "arrival" to mapOf(
                        "airport" to "CDG",
                        "city" to "Paris",
                        "date" to "2025-06-10",
                        "time" to "08:00 PM"
                    ),
                    "bookingReference" to "AFXYZ123",
                    "passengers" to listOf("user1", "user2", "user3"),
                    "price" to 850.0,
                    "currency" to "USD"
                ),
                mapOf(
                    "id" to "booking2",
                    "type" to "FLIGHT",
                    "airline" to "Air France",
                    "flightNumber" to "AF5678",
                    "departure" to mapOf(
                        "airport" to "CDG",
                        "city" to "Paris",
                        "date" to "2025-06-17",
                        "time" to "10:00 AM"
                    ),
                    "arrival" to mapOf(
                        "airport" to "JFK",
                        "city" to "New York",
                        "date" to "2025-06-17",
                        "time" to "12:30 PM"
                    ),
                    "bookingReference" to "AFXYZ456",
                    "passengers" to listOf("user1", "user2", "user3"),
                    "price" to 950.0,
                    "currency" to "USD"
                )
            ),
            "accommodations" to listOf(
                mapOf(
                    "id" to "booking3",
                    "type" to "ACCOMMODATION",
                    "name" to "Le Grand Hotel Paris",
                    "address" to "2 Rue Scribe, 75009 Paris, France",
                    "checkIn" to mapOf(
                        "date" to "2025-06-10",
                        "time" to "03:00 PM"
                    ),
                    "checkOut" to mapOf(
                        "date" to "2025-06-17",
                        "time" to "11:00 AM"
                    ),
                    "bookingReference" to "LGHP789012",
                    "guests" to listOf("user1", "user2", "user3"),
                    "price" to 1200.0,
                    "currency" to "USD"
                )
            ),
            "activities" to listOf(
                mapOf(
                    "id" to "booking4",
                    "type" to "ACTIVITY",
                    "name" to "Skip-the-Line: Louvre Museum Guided Tour",
                    "location" to "Louvre Museum, Paris",
                    "date" to "2025-06-11",
                    "startTime" to "10:00 AM",
                    "endTime" to "01:00 PM",
                    "bookingReference" to "LMGT345",
                    "participants" to listOf("user1", "user2", "user3"),
                    "price" to 60.0,
                    "currency" to "EUR"
                ),
                mapOf(
                    "id" to "booking5",
                    "type" to "ACTIVITY",
                    "name" to "Moulin Rouge Show with Champagne",
                    "location" to "82 Boulevard de Clichy, 75018 Paris",
                    "date" to "2025-06-11",
                    "startTime" to "08:00 PM",
                    "endTime" to "10:00 PM",
                    "bookingReference" to "MR789",
                    "participants" to listOf("user1", "user2", "user3"),
                    "price" to 120.0,
                    "currency" to "EUR"
                )
            )
        ),
        "trip2" to mapOf(
            "flights" to listOf(
                mapOf(
                    "id" to "booking6",
                    "type" to "FLIGHT",
                    "airline" to "All Nippon Airways",
                    "flightNumber" to "NH1009",
                    "departure" to mapOf(
                        "airport" to "LAX",
                        "city" to "Los Angeles",
                        "date" to "2025-07-15",
                        "time" to "01:30 PM"
                    ),
                    "arrival" to mapOf(
                        "airport" to "HND",
                        "city" to "Tokyo",
                        "date" to "2025-07-16",
                        "time" to "05:30 PM"
                    ),
                    "bookingReference" to "ANA456789",
                    "passengers" to listOf("user1", "user4", "user5", "user6"),
                    "price" to 1200.0,
                    "currency" to "USD"
                ),
                mapOf(
                    "id" to "booking7",
                    "type" to "FLIGHT",
                    "airline" to "Japan Airlines",
                    "flightNumber" to "JL062",
                    "departure" to mapOf(
                        "airport" to "NRT",
                        "city" to "Tokyo",
                        "date" to "2025-07-25",
                        "time" to "04:45 PM"
                    ),
                    "arrival" to mapOf(
                        "airport" to "LAX",
                        "city" to "Los Angeles",
                        "date" to "2025-07-25",
                        "time" to "11:30 AM"
                    ),
                    "bookingReference" to "JAL123456",
                    "passengers" to listOf("user1", "user4", "user5", "user6"),
                    "price" to 1150.0,
                    "currency" to "USD"
                )
            ),
            "accommodations" to listOf(
                mapOf(
                    "id" to "booking8",
                    "type" to "ACCOMMODATION",
                    "name" to "Park Hyatt Tokyo",
                    "address" to "3-7-1-2 Nishishinjuku, Shinjuku-ku, Tokyo, 163-1055",
                    "checkIn" to mapOf(
                        "date" to "2025-07-16",
                        "time" to "03:00 PM"
                    ),
                    "checkOut" to mapOf(
                        "date" to "2025-07-25",
                        "time" to "12:00 PM"
                    ),
                    "bookingReference" to "PHT987654",
                    "guests" to listOf("user1", "user4", "user5", "user6"),
                    "price" to 1800.0,
                    "currency" to "USD"
                )
            )
        ),
        "trip3" to mapOf(
            "flights" to listOf(
                mapOf(
                    "id" to "booking9",
                    "type" to "FLIGHT",
                    "airline" to "Delta Air Lines",
                    "flightNumber" to "DL123",
                    "departure" to mapOf(
                        "airport" to "SFO",
                        "city" to "San Francisco",
                        "date" to "2025-08-01",
                        "time" to "06:00 AM"
                    ),
                    "arrival" to mapOf(
                        "airport" to "JFK",
                        "city" to "New York",
                        "date" to "2025-08-01",
                        "time" to "02:30 PM"
                    ),
                    "bookingReference" to "DL789012",
                    "passengers" to listOf("user1", "user7"),
                    "price" to 450.0,
                    "currency" to "USD"
                ),
                mapOf(
                    "id" to "booking10",
                    "type" to "FLIGHT",
                    "airline" to "United Airlines",
                    "flightNumber" to "UA456",
                    "departure" to mapOf(
                        "airport" to "JFK",
                        "city" to "New York",
                        "date" to "2025-08-03",
                        "time" to "07:15 PM"
                    ),
                    "arrival" to mapOf(
                        "airport" to "SFO",
                        "city" to "San Francisco",
                        "date" to "2025-08-03",
                        "time" to "10:45 PM"
                    ),
                    "bookingReference" to "UA345678",
                    "passengers" to listOf("user1", "user7"),
                    "price" to 480.0,
                    "currency" to "USD"
                )
            ),
            "accommodations" to listOf(
                mapOf(
                    "id" to "booking11",
                    "type" to "ACCOMMODATION",
                    "name" to "The Plaza Hotel",
                    "address" to "768 5th Ave, New York, NY 10019",
                    "checkIn" to mapOf(
                        "date" to "2025-08-01",
                        "time" to "03:00 PM"
                    ),
                    "checkOut" to mapOf(
                        "date" to "2025-08-03",
                        "time" to "12:00 PM"
                    ),
                    "bookingReference" to "PH123456",
                    "guests" to listOf("user1", "user7"),
                    "price" to 600.0,
                    "currency" to "USD"
                )
            ),
            "activities" to listOf(
                mapOf(
                    "id" to "booking12",
                    "type" to "ACTIVITY",
                    "name" to "Hamilton Broadway Show",
                    "location" to "Richard Rodgers Theatre, 226 W 46th St, New York",
                    "date" to "2025-08-01",
                    "startTime" to "07:00 PM",
                    "endTime" to "10:00 PM",
                    "bookingReference" to "HAM789",
                    "participants" to listOf("user1", "user7"),
                    "price" to 400.0,
                    "currency" to "USD"
                )
            )
        )
    )

    // Simulate network delay
    private suspend fun addDelay(millis: Long = 500) {
        delay(millis)
    }

    override suspend fun getUser(userId: String): Response<User> {
        addDelay()
        // Mock user data based on userId
        return when (userId) {
            "user1" -> Response.success(
                User(
                    id = "user1",
                    name = "You",
                    email = "you@example.com",
                    avatar = "https://randomuser.me/api/portraits/women/44.jpg",
                    age = 24,
                    shareCode = "user1",
                )
            )
            "user2" -> Response.success(
                User(
                    id = "user2",
                    name = "Alex Johnson",
                    email = "alex.johnson@example.com",
                    avatar = "https://randomuser.me/api/portraits/men/32.jpg",
                    age = 24,
                    shareCode = "user2",
                )
            )
            "user3" -> Response.success(
                User(
                    id = "user3",
                    name = "Taylor Swift",
                    email = "taylor.swift@example.com",
                    avatar = "https://randomuser.me/api/portraits/women/68.jpg",
                    age = 24,
                    shareCode = "user3",
                )
            )
            "user4" -> Response.success(
                User(
                    id = "user4",
                    name = "Chris Evans",
                    email = "chris.evans@example.com",
                    avatar = "https://randomuser.me/api/portraits/men/91.jpg",
                    age = 24,
                    shareCode = "user4",
                )
            )
            "user5" -> Response.success(
                User(
                    id = "user5",
                    name = "Emma Watson",
                    email = "emma.watson@example.com",
                    avatar = "https://randomuser.me/api/portraits/women/28.jpg",
                    age = 24,
                    shareCode = "user5",
                )
            )
            "user6" -> Response.success(
                User(
                    id = "user6",
                    name = "Tom Holland",
                    email = "tom.holland@example.com",
                    avatar = "https://randomuser.me/api/portraits/men/65.jpg",
                    age = 24,
                    shareCode = "user6",
                )
            )
            "user7" -> Response.success(
                User(
                    id = "user7",
                    name = "Robert Downey Jr",
                    email = "robert.downey@example.com",
                    avatar = "https://randomuser.me/api/portraits/men/94.jpg",
                    age = 24,
                    shareCode = "user7",
                )
            )
            else -> Response.error(404, okhttp3.ResponseBody.create(null, "User not found"))
        }
    }

    override suspend fun getTrips(): Response<List<Trip>> {
        addDelay()
        return Response.success(trips)
    }

    override suspend fun getTrip(tripId: String): Response<Trip> {
        addDelay()
        val trip = trips.find { it.id == tripId }
        return if (trip != null) {
            Response.success(trip)
        } else {
            Response.error(404, okhttp3.ResponseBody.create(null, "Trip not found"))
        }
    }

    override suspend fun getTripMembers(tripId: String): Response<List<TripMember>> {
        addDelay()
        val members = tripMembers[tripId]
        return if (members != null) {
            Response.success(members)
        } else {
            Response.error(404, okhttp3.ResponseBody.create(null, "Trip members not found"))
        }
    }

    override suspend fun addTripMember(tripId: String, member: Map<String, String>): Response<TripMember> {
        addDelay(1000) // Longer delay to simulate adding a member
        val email = member["email"] ?: return Response.error(400, okhttp3.ResponseBody.create(null, "Email is required"))
        val name = member["name"] ?: "New Member"

        val newMember = TripMember(
            id = "user${UUID.randomUUID().toString().substring(0, 8)}",
            name = name,
            avatar = "https://randomuser.me/api/portraits/men/${(10..99).random()}.jpg",
            role = "MEMBER"
        )

        return Response.success(newMember)
    }

    override suspend fun getTripItinerary(tripId: String): Response<List<ItineraryItem>> {
        addDelay()
        val itinerary = itineraryItems[tripId]
        return if (itinerary != null) {
            Response.success(itinerary)
        } else {
            Response.error(404, okhttp3.ResponseBody.create(null, "Itinerary not found"))
        }
    }

    override suspend fun getTripExpenses(tripId: String): Response<List<Expense>> {
        addDelay()
        val tripExpenses = expenses[tripId]
        return if (tripExpenses != null) {
            Response.success(tripExpenses)
        } else {
            Response.error(404, okhttp3.ResponseBody.create(null, "Expenses not found"))
        }
    }

    override suspend fun addExpense(tripId: String, expense: Map<String, Any>): Response<Expense> {
        addDelay(1000) // Longer delay to simulate adding an expense
        val title = expense["title"] as? String ?: return Response.error(400, okhttp3.ResponseBody.create(null, "Title is required"))
        val amount = expense["amount"] as? Double ?: return Response.error(400, okhttp3.ResponseBody.create(null, "Amount is required"))
        val category = expense["category"] as? String ?: "MISC"
        val paidBy = expense["paidBy"] as? String ?: "user1"
        val isShared = expense["isShared"] as? Boolean ?: false

        val newExpense = Expense(
            id = "exp${UUID.randomUUID().toString().substring(0, 8)}",
            title = title,
            amount = amount,
            date = SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date()),
            category = category,
            paidBy = paidBy,
            isShared = isShared,
            contributions = if (isShared) {
                listOf(Contribution(userId = "user1", userName = "You", amount = amount))
            } else null
        )

        return Response.success(newExpense)
    }

    override suspend fun getTripBookings(tripId: String): Response<Map<String, List<Any>>> {
        addDelay()
        val tripBookings = bookings[tripId]
        return if (tripBookings != null) {
            Response.success(tripBookings)
        } else {
            Response.error(404, okhttp3.ResponseBody.create(null, "Bookings not found"))
        }
    }
}
