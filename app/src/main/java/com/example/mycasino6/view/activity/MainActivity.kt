package com.example.mycasino6.view.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.mycasino6.R
import com.example.mycasino6.model.constant.APP_PREFERENCES
import com.example.mycasino6.model.constant.LAST_DAY
import com.example.mycasino6.model.constant.MAIN
import com.example.mycasino6.model.constant.MY_CASH
import com.example.mycasino6.model.constant.MY_NAME
import com.example.mycasino6.model.constant.MY_SEX

class MainActivity : AppCompatActivity() {

    var navController : NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MAIN = this
        navController = Navigation.findNavController(this,R.id.id_nav_host)

    }

    //функция получения последнего дня,когда юзеб брал приз
    fun getLastDay() : String {
        return getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE).getString(LAST_DAY,"").toString()
    }

    //функция установки последнего дня,когда юзер брал приз
    fun setCurrentDay(day : String) {
        getSharedPreferences(APP_PREFERENCES,Context.MODE_PRIVATE).edit().putString(LAST_DAY,day).apply()
    }

    //функция получения своих денег
    fun getMyCash() : Int {
        return getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE).getInt(MY_CASH, 0)
    }

    //функция добавления денег
    fun addCash(cash : Int) {
        val preferences = getSharedPreferences(APP_PREFERENCES,Context.MODE_PRIVATE).getInt(MY_CASH,0) + cash
        getSharedPreferences(APP_PREFERENCES,Context.MODE_PRIVATE).edit().putInt(MY_CASH,preferences).apply()
    }

    //функция покупки чего либо
    fun minusCash(cash : Int) {
        val preferences = getSharedPreferences(APP_PREFERENCES,Context.MODE_PRIVATE).getInt(MY_CASH,0) - cash
        getSharedPreferences(APP_PREFERENCES,Context.MODE_PRIVATE).edit().putInt(MY_CASH,preferences).apply()
    }

    //функция установки имени
    fun setMyName(name : String) {
        getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE).edit().putString(MY_NAME,name).apply()
    }

    //функция получения имени
    fun getMyName() : String {
        return getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE).getString(MY_NAME,"").toString()
    }

    //функция получения пола
    fun getMySex() : String {
        return getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE).getString(MY_SEX,"").toString()
    }

}