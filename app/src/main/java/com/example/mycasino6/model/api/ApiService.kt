package com.example.mycasino6.model.api

import com.example.mycasino6.model.ResponceWebView
import com.example.mycasino6.model.ResponseText
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("splash.php")
    suspend fun setPostParametersPhone(
        @Field("phone_name") phone_name:String,
        @Field("locale") locale:String,
        @Field("unique") unique:String
    ): Response<ResponceWebView>

    @GET("13/textMenu.json")
    suspend fun getTextForMenu():Response<ResponseText>

    @GET("13/textSettings.json")
    suspend fun getTextForSettings():Response<ResponseText>

    @GET("13/textLoading.json")
    suspend fun getTextForLoading():Response<ResponseText>

}