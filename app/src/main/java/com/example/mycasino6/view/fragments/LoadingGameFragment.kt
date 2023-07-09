package com.example.mycasino6.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProvider
import com.example.mycasino6.R
import com.example.mycasino6.constant.*
import com.example.mycasino6.viewmodel.LoadingViewModel
import kotlinx.android.synthetic.main.fragment_loading_game.*

class LoadingGameFragment : Fragment() {

    private var myOutcomeBet = ""
    private var coefficient = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_loading_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //загрузка текста с сервера
        val loadingViewModel = ViewModelProvider(this)[LoadingViewModel::class.java]
        loadingViewModel.getTextLoading()
        loadingViewModel.Text.observe(viewLifecycleOwner){ TEXT ->
            id_loading_tv1.text = TEXT.body()!!.text
        }

        //выход в меню
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            MAIN.navController.navigate(R.id.action_loadingGameFragment_to_menuFragment)
        }

        //ставка на победу
        id_loading_button_win.setOnClickListener {
            coefficient = 2
            myOutcomeBet = WIN
            it.setBackgroundResource(R.drawable.background_button_loading_red)
            id_loading_button_nichya.setBackgroundResource(R.drawable.background_button1_reg)
            id_loading_button_loss.setBackgroundResource(R.drawable.background_button1_reg)
        }

        //ставка на ничью
        id_loading_button_nichya.setOnClickListener {
            coefficient = 8
            myOutcomeBet = DRAW
            it.setBackgroundResource(R.drawable.background_button_loading_red)
            id_loading_button_win.setBackgroundResource(R.drawable.background_button1_reg)
            id_loading_button_loss.setBackgroundResource(R.drawable.background_button1_reg)
        }

        //ставка на проигрыш
        id_loading_button_loss.setOnClickListener {
            coefficient = 3
            myOutcomeBet = LOSS
            it.setBackgroundResource(R.drawable.background_button_loading_red)
            id_loading_button_win.setBackgroundResource(R.drawable.background_button1_reg)
            id_loading_button_nichya.setBackgroundResource(R.drawable.background_button1_reg)
        }

        //перейти к игру
        id_loading_button_next.setOnClickListener {
            if(id_loading_et1.text.toString().isNotEmpty()){
                if(myOutcomeBet!=""){
                    if(id_loading_et1.text.toString().toInt()>10){
                        if(id_loading_et1.text.toString().toInt()< MAIN.getMyCash()){
                            val bundle = Bundle()
                            bundle.putString(MY_OUTCOME_BET,myOutcomeBet)
                            bundle.putInt(COEFFICIENT,coefficient)
                            bundle.putInt(MY_BET,id_loading_et1.text.toString().toInt())
                            MAIN.minusCash(id_loading_et1.text.toString().toInt())
                            MAIN.navController.navigate(R.id.action_loadingGameFragment_to_gameBakkaraFragment,bundle)
                        }else{
                            Toast.makeText(requireContext(),"you don't have enough money",Toast.LENGTH_SHORT).show()
                        }
                    }else{
                        Toast.makeText(requireContext(),"the minimum bet is $10",Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(requireContext(),"choose the outcome of the bet",Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(requireContext(),"enter your bid amount",Toast.LENGTH_SHORT).show()
            }
        }



    }

}