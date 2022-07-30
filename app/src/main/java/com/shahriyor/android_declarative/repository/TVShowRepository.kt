package com.shahriyor.android_declarative.repository

import com.shahriyor.android_declarative.data.db.TVShowDao
import com.shahriyor.android_declarative.data.network.TVSHowService
import com.shahriyor.android_declarative.model.TVShow
import javax.inject.Inject

class TVShowRepository @Inject constructor(
    private val tvshowService: TVSHowService,
    private val tvShowDao: TVShowDao
) {

    suspend fun apiTVShowPopular(page: Int) = tvshowService.apiTVShowPopular(page)
    suspend fun apiTVShowDetails(q: Int) = tvshowService.apiTVShowDetails(q)

    suspend fun getTVShowFromDB() = tvShowDao.getTVShowsFromDB()
    suspend fun insertTVShowToDB(tvShow: TVShow) = tvShowDao.insertTVShowToDB(tvShow)
    suspend fun deleteTVShowFromDB() = tvShowDao.deleteTvShowsFromDB()

}