package com.example.mycasino6.view.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProvider
import com.example.mycasino6.R
import com.example.mycasino6.model.constant.MAIN
import com.example.mycasino6.viewmodel.MenuViewModel
import kotlinx.android.synthetic.main.fragment_menu.*

class MenuFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    @SuppressLint("SetTextI18n")
    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        id_menu_tv_my_cash.text = MAIN.getMyCash().toString()+"$"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //загрузка текста с сервера
        val menuViewModel = ViewModelProvider(this)[MenuViewModel::class.java]

        menuViewModel.getTextMenu()

        menuViewModel.text.observe(viewLifecycleOwner){
            id_menu_tv_description_game.text = it.body()!!.text
        }

        //закрытие приложение по нажатии на кнопку НАЗАД
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            MAIN.finishAffinity()
        }

        //кнопка начать игру
        id_menu_button_start.setOnClickListener {
            MAIN.navController?.navigate(R.id.action_menuFragment_to_loadingGameFragment)
        }

        //кнопка настройки
        id_menu_button_settings.setOnClickListener {
            MAIN.navController?.navigate(R.id.action_menuFragment_to_settingsFragment)
        }

        //кнопка выхода
        id_menu_button_exit.setOnClickListener {
            MAIN.finishAffinity()
        }

    }

}