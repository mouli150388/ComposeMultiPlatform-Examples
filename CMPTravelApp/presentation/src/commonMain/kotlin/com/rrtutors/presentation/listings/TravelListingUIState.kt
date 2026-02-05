package com.rrtutors.presentation.listings

import com.rrtutors.domain.model.TravelListing

data class TravelListingUIState(
    val listing: List<TravelListing> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
) {
    val hasListing : Boolean
        get() = listing.isNotEmpty()
    val showEmptyList : Boolean
        get() = !isLoading && !hasListing && listing.isEmpty()

}