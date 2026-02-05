package com.rrtutors.data.datasource

import com.rrtutors.data.model.TravelListingDto
import com.rrtutors.domain.model.TravelListing
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DummyDataSource {

    val _travelListings = MutableStateFlow(createDummyDataSource())
val listing=_travelListings.asStateFlow()

    private fun createDummyDataSource (): List<TravelListingDto> {
        return listOf(
            TravelListingDto(
                id = "1",
                title = "Title 1",
                description = "Description 1",
                location = "Location 1",
                imageUrl = "Image URL 1",
                pricePerNight = 100.0,
                rating = 4.5,
                amenities = listOf("Amenity 1", "Amenity 2"),
                hostname = "Host 1",
                isFavorite = true,

            ),
            TravelListingDto(
                id = "2",
                title = "Title 2",
                description = "Description 2",
                location = "Location 2",
                imageUrl = "Image URL 2",
                pricePerNight = 200.0,
                rating = 4.0,
                amenities = listOf("Amenity 3", "Amenity 4"),
                hostname = "Host 2",



        ),
        )
    }
}