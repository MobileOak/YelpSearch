package com.mobileoak.yelpsearch.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobileoak.yelpsearch.model.BusinessRepository
import com.mobileoak.yelpsearch.network.Business
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

sealed class ResultsState {
    object Empty: ResultsState()
    data class Loaded(val data: List<Business>): ResultsState()
}

@HiltViewModel
class ResultsViewModel @Inject constructor(repository: BusinessRepository): ViewModel() {
    private val _state = MutableLiveData<ResultsState>().apply {
        value = ResultsState.Empty
    }

    val state : LiveData<ResultsState>
        get() = _state

    init {
        _state.postValue(ResultsState.Loaded(repository.listOfBusinesses))
    }
}