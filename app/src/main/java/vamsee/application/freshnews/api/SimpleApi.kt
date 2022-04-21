package vamsee.application.freshnews.api
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import vamsee.application.freshnews.modal.News
import vamsee.application.freshnews.modal.apiResponce

interface SimpleApi {
    @GET("top-headlines/category/health/in.json")
    suspend fun getNews(): Response<apiResponce>
}