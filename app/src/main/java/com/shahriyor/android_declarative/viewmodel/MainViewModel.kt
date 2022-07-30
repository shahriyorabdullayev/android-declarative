package com.shahriyor.android_declarative.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.shahriyor.android_declarative.model.TVShow
import com.shahriyor.android_declarative.model.TVShowPopular
import com.shahriyor.android_declarative.repository.TVShowRepository
import com.shahriyor.android_declarative.source.MovieSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val tvShowRepository: TVShowRepository) :
    ViewModel() {

    companion object{
        val isLoading = MutableLiveData<Boolean>()
    }


    val errorMessage = MutableLiveData<String>()
    val tvShowsFromApi = MutableLiveData<ArrayList<TVShow>>()
    val tvShowPopular = MutableLiveData<TVShowPopular>()

    var pages: Int = 0

    val movie : Flow<PagingData<TVShow>> = Pager(PagingConfig(pages)){
        MovieSource(repository = tvShowRepository)
    }.flow.cachedIn(viewModelScope)

    init {
        apiTVShowPopular(1)
    }

    fun apiTVShowPopular(page: Int) {
        isLoading.value = true
        CoroutineScope(Dispatchers.IO).launch {
            val response = tvShowRepository.apiTVShowPopular(page)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val resp = response.body()!!
                    tvShowPopular.postValue(resp)
                    tvShowsFromApi.postValue(resp.tv_shows)
                    isLoading.value = false
                    pages = resp.pages
                } else {
                    onError("Error : ${response.message()}")
                }
            }
        }
    }

    private fun onError(message: String) {
        errorMessage.value = message
        isLoading.value = false
    }

    fun insertTVShowToDB(tvShow: TVShow) {
        viewModelScope.launch {
            tvShowRepository.insertTVShowToDB(tvShow)
        }
    }
}