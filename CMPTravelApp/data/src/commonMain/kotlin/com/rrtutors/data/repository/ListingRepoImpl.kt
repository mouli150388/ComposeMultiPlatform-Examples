package com.rrtutors.data.repository

import com.rrtutors.data.datasource.DummyDataSource
import com.rrtutors.data.mappers.TravelListMapper
import com.rrtutors.domain.model.TravelListing
import com.rrtutors.domain.repository.ListingRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map

class ListingRepoImpl(val dataSource: DummyDataSource): ListingRepo {
    val _listing= MutableStateFlow<List<TravelListing>>(emptyList())
    val listing: StateFlow<List<TravelListing>> = _listing.asStateFlow()

    override fun getTravelListings(): Flow<List<TravelListing>> {
        return dataSource.listing.map {
            val domainModel=TravelListMapper.toDomainList(it)
            _listing.value= domainModel
            listing.value
        }

    }

    override fun getTravelListingById(id: String): Flow<TravelListing> {
        return listing.map {it->
            it.find { it.id == id } as TravelListing
        }
    }
}