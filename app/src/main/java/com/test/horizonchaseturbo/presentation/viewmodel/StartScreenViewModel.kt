package com.test.horizonchaseturbo.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.horizonchaseturbo.domain.repository.GameRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartScreenViewModel @Inject constructor(
    private val repository: GameRepository
) : ViewModel() {

    private val _bestScore = MutableStateFlow(0)
    val bestScore: StateFlow<Int> get() = _bestScore

    init {
        fetchBestScore()
    }

    private fun fetchBestScore() {
        viewModelScope.launch {
            _bestScore.value = repository.getBestScore()
        }
    }

    private fun setBestScore(newScore: Int) {
        if (newScore > _bestScore.value) {
            repository.setBestScore(newScore)
            _bestScore.value = newScore
        }
    }
}