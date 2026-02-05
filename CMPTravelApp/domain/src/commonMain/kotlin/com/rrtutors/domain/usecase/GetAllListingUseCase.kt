package com.rrtutors.domain.usecase

import com.rrtutors.domain.model.TravelListing
import com.rrtutors.domain.repository.ListingRepo
import kotlinx.coroutines.flow.Flow

class GetAllListingUseCase(private val listingRepo: ListingRepo) {
    operator fun invoke(): Flow<List<TravelListing>> {
        return listingRepo.getTravelListings()
    }
}


