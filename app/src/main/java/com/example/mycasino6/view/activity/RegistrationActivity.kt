package com.example.mycasino6.view.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.RadioButton
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.mycasino6.R
import com.example.mycasino6.constant.APP_PREFERENCES
import com.example.mycasino6.constant.MY_NAME
import com.example.mycasino6.constant.MY_SEX
import com.example.mycasino6.constant.urlImageRegistration
import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationActivity : AppCompatActivity() {

    private var mySex = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        //загрузка картинки
        Glide.with(this)
            .load(urlImageRegistration)
            .into(id_reg_img)

        //установка полноэкранного режима
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        //нажатие на определенный пол(мужской,женский)
        id_reg_dadiogroup.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId != -1) {
                val radioButton: RadioButton = findViewById(checkedId)
                mySex = radioButton.text.toString()
            }
        }

        //нажатие на кнопку завершения
        id_reg_button_next.setOnClickListener {
            if(id_reg_et_name.text.toString()!=""){
                if(mySex!=""){
                    setMySex(mySex)
                    setMyName(id_reg_et_name.text.toString())
                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this,"you haven't chosen your gender",Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this,"you didn't enter a name",Toast.LENGTH_SHORT).show()
            }
        }



    }

    //выход по нажатии на кнопку НАЗАД
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

    //функция установки пола
    private fun setMySex(sex:String){
        val pref = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        pref.edit()
            .putString(MY_SEX,sex)
            .apply()
    }

    //функция установки имени
    private fun setMyName(name:String){
        val pref = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        pref.edit()
            .putString(MY_NAME,name)
            .apply()
    }


}