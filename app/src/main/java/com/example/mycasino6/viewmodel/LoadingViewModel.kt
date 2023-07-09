package com.example.mycasino6.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycasino6.api.Repository
import com.example.mycasino6.model.ResponseText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class LoadingViewModel:ViewModel() {

    val repo = Repository()
    val Text: MutableLiveData<Response<ResponseText>> = MutableLiveData()

    fun getTextLoading(){
        viewModelScope.launch(Dispatchers.IO) {
            val responce = repo.getTextLoading()
            withContext(Dispatchers.Main){
                Text.value = responce
            }
        }
    }

}