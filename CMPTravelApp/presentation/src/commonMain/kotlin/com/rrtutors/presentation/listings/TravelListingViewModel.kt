package com.rrtutors.presentation.listings
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rrtutors.domain.usecase.GetAllListingUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TravelListingViewModel(val getAllListingUseCase: GetAllListingUseCase): ViewModel() {
    private val _uiState = MutableStateFlow(TravelListingUIState())
    val uiState: StateFlow<TravelListingUIState> = _uiState.asStateFlow()
    init {
        loadTravelListing()
    }
    fun loadTravelListing() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            getAllListingUseCase.invoke().collect {

                _uiState.value=_uiState.value.copy(listing = it, isLoading = false,null)
            }


        }

    }
    val listing= getAllListingUseCase()
}


