package com.test.horizonchaseturbo.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.horizonchaseturbo.domain.usecases.GetBestScoreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartScreenViewModel @Inject constructor(
    private val getBestScoreUseCase: GetBestScoreUseCase
) : ViewModel() {

    private val _bestScore = MutableLiveData<Int>()
    val bestScore: LiveData<Int> get() = _bestScore

    init {
        fetchBestScore()
    }

    private fun fetchBestScore() {
        viewModelScope.launch {
            _bestScore.value = getBestScoreUseCase.execute()
        }
    }
}