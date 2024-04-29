package com.example.mycasino6.model.api

import com.example.mycasino6.model.ResponceWebView
import com.example.mycasino6.model.ResponseText
import retrofit2.Response

class Repository {

    suspend fun setParametersPhone(phoneName : String , locale : String , unique : String) : Response <ResponceWebView> {
        return RetrofitInstance.api.setPostParametersPhone(phoneName , locale , unique)
    }

    suspend fun getTextMenu(): Response<ResponseText> {
        return RetrofitInstance.api.getTextForMenu()
    }

    suspend fun getTextSettings(): Response<ResponseText> {
        return RetrofitInstance.api.getTextForSettings()
    }

    suspend fun getTextLoading(): Response<ResponseText> {
        return RetrofitInstance.api.getTextForLoading()
    }

}