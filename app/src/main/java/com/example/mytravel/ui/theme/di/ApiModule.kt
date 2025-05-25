package com.example.mytravel.ui.theme.di

import com.example.mytravel.ui.theme.data.remote.MockTravelApiService
import com.example.mytravel.ui.theme.data.remote.TravelApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MockAPI

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideTravelApiService(): TravelApiService {
        // Return the mock implementation
        return MockTravelApiService()
    }
}
