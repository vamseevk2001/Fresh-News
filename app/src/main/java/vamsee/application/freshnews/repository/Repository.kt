package vamsee.application.freshnews.repository

import retrofit2.Response
import vamsee.application.freshnews.api.RetrofitInstance
import vamsee.application.freshnews.modal.News
import vamsee.application.freshnews.modal.apiResponce

class Repository {
    suspend fun getNews(): Response<apiResponce>{
        return RetrofitInstance.api.getNews()
    }
}