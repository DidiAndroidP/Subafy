package com.subafy.subafy.src.features.auction.presentation.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.subafy.subafy.src.features.auction.domain.entities.ParticipantFinalItemUI
import com.subafy.subafy.src.features.auction.domain.usecase.GetAuctionParticipantsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ParticipantsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getAuctionParticipantsUseCase: GetAuctionParticipantsUseCase
) : ViewModel() {

    private val auctionId: String = savedStateHandle["auctionId"] ?: ""

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _rawParticipants = MutableStateFlow<List<ParticipantFinalItemUI>>(emptyList())

    val participants: StateFlow<List<ParticipantFinalItemUI>> = combine(
        _rawParticipants,
        _searchQuery
    ) { list, query ->
        if (query.isBlank()) list
        else list.filter {
            it.nickname.contains(query, ignoreCase = true) ||
                    it.id.contains(query, ignoreCase = true)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    init {
        fetchParticipants()
    }

    private fun fetchParticipants() {
        viewModelScope.launch {
            _isLoading.value = true
            getAuctionParticipantsUseCase(auctionId).onSuccess { domainList ->
                _rawParticipants.value = domainList.mapIndexed { index, participant ->
                    ParticipantFinalItemUI(
                        id        = participant.id,
                        nickname  = participant.nickname,
                        avatarUrl = participant.avatarUrl,
                        bidCount  = participant.bidCount,
                        isActive  = participant.isActive,
                        lastBidAt   = participant.lastBidAt,
                    )
                }
            }.onFailure {
                _rawParticipants.value = emptyList()
            }
            _isLoading.value = false
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }
}