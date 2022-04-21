package vamsee.application.freshnews

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Response
import vamsee.application.freshnews.modal.News
import vamsee.application.freshnews.modal.apiResponce
import vamsee.application.freshnews.repository.Repository

class MainViewModel(private val repository: Repository): ViewModel() {
    val news: MutableLiveData<Response<apiResponce>> = MutableLiveData()
    fun getNews(){
        viewModelScope.launch {
            val response = repository.getNews()
            news.value = response
        }
    }

}