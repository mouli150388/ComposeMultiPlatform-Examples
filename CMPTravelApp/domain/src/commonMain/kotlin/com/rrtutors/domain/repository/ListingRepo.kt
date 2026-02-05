package com.rrtutors.domain.repository

import com.rrtutors.domain.model.TravelListing
import kotlinx.coroutines.flow.Flow

interface ListingRepo {
     fun getTravelListings(): Flow<List<TravelListing>>
     fun getTravelListingById(id: String): Flow<TravelListing>
}