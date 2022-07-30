package com.shahriyor.android_declarative.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import com.shahriyor.android_declarative.model.TVShowDetails
import com.shahriyor.android_declarative.repository.TVShowRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val tvShowRepository: TVShowRepository) :
    ViewModel() {
    val isLoading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    val tvShowDetails = MutableLiveData<TVShowDetails>()

    fun apiTVShowDetails(q: Int) {
//        isLoading.value = true
        CoroutineScope(Dispatchers.IO).launch {
            val response = tvShowRepository.apiTVShowDetails(q)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val resp = response.body()
                    tvShowDetails.postValue(resp)
//                    isLoading.value=false
                } else {
                    onError("Error : ${response.message()}")
                }
            }
        }
    }

    private fun onError(message: String) {
        errorMessage.value = message
//        isLoading.value = false
    }

}