package com.example.mycasino6.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import com.example.mycasino6.R
import com.example.mycasino6.model.constant.MAIN
import kotlinx.android.synthetic.main.fragment_prizes.*
import kotlinx.coroutines.*
import org.threeten.bp.LocalDate

class PrizesFragment : Fragment() {

    private var winCash = emptyList<Int>()
    private var listNumber = listOf(1,2,3,4,5,6,7,8,9)
    private var job1: Job = Job()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_prizes, container, false)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //проверка "прошли ли сутки после последнего приза"
        if(LocalDate.now().toString() == MAIN.getLastDay()){
            MAIN.navController?.navigate(R.id.action_prizesFragment_to_menuFragment)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //закрытие приложение по нажатии на кнопку НАЗАД
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            if(job1.isActive){
                job1.cancel()
            }
            MAIN.finishAffinity()
        }

        //нажатие на кнопку В МЕНЮ
        id_prizes_button_into_menu.setOnClickListener {
            if(winCash.isNotEmpty()){
                MAIN.navController?.navigate(R.id.action_prizesFragment_to_menuFragment)
            }else{
                Toast.makeText(requireContext(),"win a cash prize first",Toast.LENGTH_SHORT).show()
            }
        }

        //нажатие на кнопку ПОЛУЧИТЬ ДЕНЬГИ
        id_prizes_button_get.setOnClickListener {
            job1 = CoroutineScope(Dispatchers.Main).launch {
                id_prizes_button_get.isEnabled = false
                winCash = listNumber.shuffled().slice(0..2)
                delay(1000)
                id_prizes_tv1.text = winCash[0].toString()
                delay(1000)
                id_prizes_tv2.text = winCash[1].toString()
                delay(1000)
                id_prizes_tv3.text = winCash[2].toString()
                id_prizes_button_into_menu.isEnabled = true
                Toast.makeText(requireContext(),"+${winCash[0]*100+winCash[1]*10+winCash[2]}$",Toast.LENGTH_SHORT).show()
                MAIN.addCash(winCash[0]*100+winCash[1]*10+winCash[2])
                MAIN.setCurrentDay(LocalDate.now().toString())
            }

        }

    }

}