package com.example.randomweather.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.domain.DomainError
import com.example.domain.models.LocationInfo
import com.example.domain.fold
import com.example.randomweather.di.IODispatcher
import com.example.usecases.GetNewLocationInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
        private val getNewLocationInfo: GetNewLocationInfo,
        @IODispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    val currentlocationInfo: LiveData<LocationInfo> = getNewLocationInfo.flowResult.asLiveData()

    private val mutableUErrors = MutableLiveData<DomainError>()
    val uiErrors: LiveData<DomainError> = mutableUErrors

    fun getNewLocation() {
        viewModelScope.launch {
            withContext(ioDispatcher) { getNewLocationInfo() }.fold(::showError) {}
        }
    }

    private fun showError(domainError: DomainError){
        mutableUErrors.value = domainError
    }
}