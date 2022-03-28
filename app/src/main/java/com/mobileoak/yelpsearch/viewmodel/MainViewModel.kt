package com.mobileoak.yelpsearch.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobileoak.yelpsearch.model.BusinessRepository
import com.mobileoak.yelpsearch.network.ApiInterface
import com.mobileoak.yelpsearch.network.SearchResponseObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

sealed class SearchState {
    object ReadyForInput: SearchState()
    object Waiting: SearchState()
    object ReadyToShowResults: SearchState()
    data class Error(val title: String, val message: String): SearchState()

}

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: BusinessRepository): ViewModel() {
    private val _state = MutableLiveData<SearchState>().apply {
        value = SearchState.ReadyForInput
    }

    val state : LiveData<SearchState>
        get() = _state

    fun onSearchClicked(location: String, subject: String) {
        when {
            location.isEmpty() -> {
                _state.postValue(SearchState.Error("Error", "Location cannot be blank"))
            }
            subject.isEmpty() -> {
                _state.postValue(SearchState.Error("Error", "Subject cannot be blank"))
            }
            else -> {
                viewModelScope.launch {
                    callYelpForSearchResults(location, subject)
                }
            }
        }
    }

    private fun callYelpForSearchResults(location: String, subject: String) {
        _state.postValue(SearchState.Waiting)
        try {
            val apiInterface = ApiInterface.create().searchBusinesses(
                authKey = ApiInterface.AUTH_KEY,
                location = location,
                searchTerm = subject
            )
            apiInterface.enqueue(object : Callback<SearchResponseObject> {
                override fun onResponse(
                    call: Call<SearchResponseObject>,
                    response: Response<SearchResponseObject>
                ) {
                    val body = response.body()
                    if (body != null) {
                        repository.listOfBusinesses = body.listOfBusinesses
                        _state.postValue(SearchState.ReadyToShowResults)
                    } else {
                        _state.postValue(SearchState.Error("Error", "No results found"))
                    }
                }

                override fun onFailure(call: Call<SearchResponseObject>, t: Throwable) {
                    _state.postValue(SearchState.Error("Error", "Network error: " + t.message))
                }
            })

        } catch (exception: Exception) {
            _state.postValue(SearchState.Error("Error", "Network error: " + exception.message))
        }
    }
}

