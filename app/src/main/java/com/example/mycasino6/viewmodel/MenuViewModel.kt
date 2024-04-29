package com.example.mycasino6.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycasino6.model.api.Repository
import com.example.mycasino6.model.ResponseText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class MenuViewModel : ViewModel() {

    private val repository = Repository()
    val text: MutableLiveData<Response<ResponseText>> = MutableLiveData()

    fun getTextMenu(){
        viewModelScope.launch(Dispatchers.IO) {
            val responce = repository.getTextMenu()
            withContext(Dispatchers.Main){
                text.value = responce
            }
        }
    }

}