package com.example.mycasino6.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProvider
import com.example.mycasino6.R
import com.example.mycasino6.constant.MAIN
import com.example.mycasino6.viewmodel.MenuViewModel
import com.example.mycasino6.viewmodel.SettingsViewModel
import kotlinx.android.synthetic.main.fragment_menu.*
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //показ имени
        id_set_et_name.hint = MAIN.getMyName()

        //загрузка текста с сервера
        val settingsViewModel = ViewModelProvider(this)[SettingsViewModel::class.java]
        settingsViewModel.getTextSettings()
        settingsViewModel.Text.observe(viewLifecycleOwner){ TEXT ->
            id_set_tv_description.text = TEXT.body()!!.text
        }

        //возвращение в меню по нажатии на кнопку НАЗАД
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            MAIN.navController.navigate(R.id.action_settingsFragment_to_menuFragment)
        }


        //кнопка возврата в меню
        id_set_into_menu.setOnClickListener {
            MAIN.navController.navigate(R.id.action_settingsFragment_to_menuFragment)
        }

        //пересохранение имени
        id_set_button_change_name.setOnClickListener {
            if(id_set_button_change_name.text == "change name"){
                id_set_button_change_name.text = "save"
                id_set_et_name.isEnabled = true
            }else{
                id_set_button_change_name.text = "change name"
                id_set_et_name.isEnabled = false
                MAIN.setMyName(id_set_et_name.text.toString())
                id_set_et_name.text.clear()
                id_set_et_name.hint = MAIN.getMyName()
            }
        }


    }



}