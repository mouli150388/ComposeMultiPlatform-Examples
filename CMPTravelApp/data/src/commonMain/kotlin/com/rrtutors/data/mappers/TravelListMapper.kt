package com.rrtutors.data.mappers

import com.rrtutors.data.model.TravelListingDto
import com.rrtutors.domain.model.TravelListing

object TravelListMapper {
    fun toDomain(dto: TravelListingDto): TravelListing
    {
        return TravelListing(
            id = dto.id,
            title = dto.title,
            description = dto.description,
            location = dto.location,
            imageUrl = dto.imageUrl,
            pricePerNight = dto.pricePerNight,
            rating = dto.rating,
            amenities = dto.amenities?: emptyList(),
            hostname = dto.hostname,
            isFavorite = dto.isFavorite
        )
    }
    fun toDomainList(dtoList: List<TravelListingDto>): List<TravelListing>
    {
        return dtoList.map { toDomain(it) }
    }
    fun toDto(domain: TravelListing): TravelListingDto
    {
        return TravelListingDto(

            id = domain.id,
            title = domain.title,
            description = domain.description,
            location = domain.location,
            imageUrl = domain.imageUrl,
            pricePerNight = domain.pricePerNight,
            rating = domain.rating,
            amenities = domain.amenities,
            hostname = domain.hostname,
            isFavorite = domain.isFavorite
        )

    }
}