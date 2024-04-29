package com.example.mycasino6.view.activity

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.mycasino6.R
import com.example.mycasino6.model.constant.APP_PREFERENCES
import com.example.mycasino6.model.constant.ID
import com.example.mycasino6.model.constant.MY_NAME
import com.example.mycasino6.model.constant.urlImageSplash
import com.example.mycasino6.viewmodel.SplashViewModel
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //загрузка фоновой картинки
        Glide.with(this)
            .load(urlImageSplash)
            .into(id_splash_img)

        val splashViewModel = ViewModelProvider(this)[SplashViewModel::class.java]

        val namePhone = Build.MODEL.toString()
        val locale = Locale.getDefault().displayLanguage.toString()
        var id = ""

        if (getMyId()!=""){
            id = getMyId()
        }else{
            id = UUID.randomUUID().toString()
            setMyId(id)
        }

        splashViewModel.setPostParametersPhone(namePhone,locale,id)

        splashViewModel.webViewUrl.observe(this){ responce ->
            when(responce.body()!!.url){
                "no" -> { goToMainPush() }
                "nopush" -> { goToMainNoPush() }
                else -> { goToWeb(responce.body()!!.url) }
            }
        }

    }

    //функция получения имени
    private fun getMyName() : String {
        return getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE).getString(MY_NAME,"").toString()
    }

    private fun getMyId():String{
        return getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE).getString(ID,"").toString()
    }

    private fun setMyId(id:String){
        getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE).edit().putString(ID,id).apply()
    }

    private fun goToMainPush() {
        CoroutineScope(Dispatchers.Main).launch {
            delay(4000)
            if(getMyName() != ""){
                val intent = Intent(this@SplashActivity,MainActivity::class.java)
                startActivity(intent)
            }else{
                val intent = Intent(this@SplashActivity,RegistrationActivity::class.java)
                startActivity(intent)
            }

        }
    }

    private fun goToMainNoPush() {
        CoroutineScope(Dispatchers.Main).launch {
            delay(4000)
            if(getMyName()!=""){
                val intent = Intent(this@SplashActivity,MainActivity::class.java)
                intent.putExtra("url","nopush")
                startActivity(intent)
            }else{
                val intent = Intent(this@SplashActivity,RegistrationActivity::class.java)
                intent.putExtra("url","nopush")
                startActivity(intent)
            }

        }
    }

    private fun goToWeb(url:String) {
        CoroutineScope(Dispatchers.Main).launch {
            delay(4000)
            val intent = Intent(this@SplashActivity,WebViewActivity::class.java)
            intent.putExtra("url",url)
            startActivity(intent)
        }
    }

}