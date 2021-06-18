package com.example.marsrealestate.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marsrealestate.models.Mars
import com.example.marsrealestate.network.MarsApi.retrofitService
import com.example.marsrealestate.models.ResultsFilter
import kotlinx.coroutines.launch

class OverviewViewModel: ViewModel() {

    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response

    private val _property = MutableLiveData<List<Mars>>()
    val property: LiveData<List<Mars>>
        get() = _property

    private val _status = MutableLiveData<MarsApiStatus>()
    val status: LiveData<MarsApiStatus>
        get() = _status

    enum class MarsApiStatus{LOADING, ERROR, DONE}

    init {
        getMarsRealEstateProperties(ResultsFilter.ALL)
    }

    fun getMarsRealEstateProperties(filter: ResultsFilter) {
        _status.value = MarsApiStatus.LOADING
        viewModelScope.launch {
            try {
                val receivedResponse = retrofitService.getProperties(filter.filterBy)
                _response.value = "Success: ${receivedResponse.size} Mars properties retrieved"
                if (receivedResponse.isNotEmpty()) {
                    _property.value = receivedResponse
                    _status.value = MarsApiStatus.DONE
                }
            } catch (e: Exception) {
                _response.value = "Failure: ${e.message}"
                _status.value = MarsApiStatus.ERROR
            }
        }
    }
}