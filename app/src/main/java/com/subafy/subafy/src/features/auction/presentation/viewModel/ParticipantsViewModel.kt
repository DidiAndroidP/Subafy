package com.subafy.subafy.src.features.dashboard.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.subafy.subafy.src.features.auction.domain.usecase.GetParticipantsUseCase
import com.subafy.subafy.src.features.dashboard.presentation.screens.ParticipantItemUi
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
    private val getParticipantsUseCase: GetParticipantsUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _rawParticipants = MutableStateFlow<List<ParticipantItemUi>>(emptyList())

    val participants: StateFlow<List<ParticipantItemUi>> = combine(
        _rawParticipants,
        _searchQuery
    ) { list, query ->
        if (query.isBlank()) {
            list
        } else {
            list.filter {
                it.nickname.contains(query, ignoreCase = true) ||
                        it.id.contains(query, ignoreCase = true)
            }
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

            val result = getParticipantsUseCase()

            result.onSuccess { domainList ->
                val uiList = domainList.mapIndexed { index, participant ->
                    ParticipantItemUi(
                        id = participant.id,
                        nickname = participant.nickname,
                        avatarUrl = participant.avatarUrl,
                        bidCount = participant.bidCount,
                        isActive = participant.isActive,
                        timeAgo = participant.lastBidAt,
                        isWinner = index == 0
                    )
                }
                _rawParticipants.value = uiList
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