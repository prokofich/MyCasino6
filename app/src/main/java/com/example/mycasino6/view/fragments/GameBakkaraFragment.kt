package com.example.mycasino6.view.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.example.mycasino6.R
import com.example.mycasino6.constant.*
import kotlinx.android.synthetic.main.fragment_game_bakkara.*
import kotlinx.coroutines.*

class GameBakkaraFragment : Fragment() {

    private var myOutcomeBet = ""
    private var myBet = 0
    private var coefficient = 0

    private var job1:Job = Job()

    private var listRandom6Card = emptyList<String>()

    private var myListCard = mutableListOf<String>()
    private var myListPoints = mutableListOf<Int>()
    private var opponentListCard = mutableListOf<String>()
    private var opListPoints = mutableListOf<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_game_bakkara, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //загрузка аватаров
        loadImage(urlImageGameDiller,id_game_img1_banker)
        loadMyImage()

        //установка имени
        id_game_name_me.text = MAIN.getMyName()

        myOutcomeBet = requireArguments().getString(MY_OUTCOME_BET)!!
        myBet = requireArguments().getInt(MY_BET)
        coefficient = requireArguments().getInt(COEFFICIENT)

        //выход в меню
        id_game_button_finish.setOnClickListener {
            MAIN.navController.navigate(R.id.action_gameBakkaraFragment_to_menuFragment)
        }

        //переход в загрузку игры
        id_game_button_restart.setOnClickListener {
            MAIN.navController.navigate(R.id.action_gameBakkaraFragment_to_loadingGameFragment)
        }


        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            if(job1.isActive){
                job1.cancel()
            }
            MAIN.navController.navigate(R.id.action_gameBakkaraFragment_to_loadingGameFragment)
        }





        id_game_button_go.setOnClickListener {
            job1 = CoroutineScope(Dispatchers.Main).launch {

                it.isVisible = false

                listRandom6Card = listAllCard.shuffled().slice(0..5)
                myListCard.add(listRandom6Card[0])
                myListCard.add(listRandom6Card[1])
                myListPoints.add(mapAllCard[myListCard[0]]!!)
                myListPoints.add(mapAllCard[myListCard[1]]!!)
                opponentListCard.add(listRandom6Card[2])
                opponentListCard.add(listRandom6Card[3])
                opListPoints.add(mapAllCard[opponentListCard[0]]!!)
                opListPoints.add(mapAllCard[opponentListCard[1]]!!)

                loadCard()
                delay(2000)
                showPoint()
                delay(2000)
                if(checkCleanVictory()){
                    //есть чистая
                    checkCleanVictoryMyOrDiller()
                }else{
                    check3Card()
                    delay(2000)
                    checkWin()
                }

            }

        }



    }

    private fun loadImage(url:String,id:ImageView){
        Glide.with(requireContext())
            .load(url)
            .into(id)
    }

    private fun loadMyImage(){
        if(MAIN.getMySex()=="male"){
            loadImage(urlImageGameMan,id_game_img1_me)
        }else{
            loadImage(urlImageGameGirl,id_game_img1_me)
        }
    }

    private fun loadCard(){
        Glide.with(requireContext())
            .load(nameServer+myListCard[0])
            .into(id_game_img2_me)
        Glide.with(requireContext())
            .load(nameServer+myListCard[1])
            .into(id_game_img3_me)
        Glide.with(requireContext())
            .load(nameServer+opponentListCard[0])
            .into(id_game_img2_banker)
        Glide.with(requireContext())
            .load(nameServer+opponentListCard[1])
            .into(id_game_img3_banker)
    }

    //проверка чистой победы у кого либо
    private fun checkCleanVictory(): Boolean {
        return ((myListPoints.sum())%10 > 7 && (opListPoints.sum())%10 < 8) ||
                ((opListPoints.sum())%10 > 7 && (myListPoints.sum())%10 < 8)
    }

    //проверка чистой победы у меня и диллера
    private fun checkCleanVictoryMyOrDiller(){
        if((myListPoints.sum())%10>7 && (opListPoints.sum())%10<8){
            //моя чистая победа
            paintMe()
            showToast("your clean victory!")
            if(myOutcomeBet=="win"){
                showToast("you guessed right with the outcome of the game and get ${myBet*coefficient}")
                MAIN.addCash(myBet*coefficient)
                visibleButtonRestartAndFinish()
            }else{
                showToast("you did not guess the outcome of the game and lose ${myBet}")
                visibleButtonRestartAndFinish()
            }
        }
        if((opListPoints.sum())%10>7 && (myListPoints.sum())%10<8){
            //мой чистый проигрыш
            paintBanker()
            showToast("your net loss")
            if(myOutcomeBet=="loss"){
                showToast("you guessed right with the outcome of the game and get ${myBet*coefficient}")
                MAIN.addCash(myBet*coefficient)
                visibleButtonRestartAndFinish()
            }else{
                showToast("you did not guess the outcome of the game and lose ${myBet}")
                visibleButtonRestartAndFinish()
            }
        }

    }

    //показать очки
    @SuppressLint("SetTextI18n")
    private fun showPoint(){
        id_game_tv_points_me.text = "${myListPoints[0]}+${myListPoints[1]}=${(myListPoints.sum())%10}"
        id_game_tv_points_diller.text = "${opListPoints[0]}+${opListPoints[1]}=${(opListPoints.sum())%10}"
    }

    //показать очки
    @SuppressLint("SetTextI18n")
    private fun showPointUpdate(){

        if(myListCard.size == 2){
            id_game_tv_points_me.text = "${myListPoints[0]}+${myListPoints[1]}=${(myListPoints.sum())%10}"
        }else{
            id_game_tv_points_me.text = "${myListPoints[0]}+${myListPoints[1]}+${myListPoints[2]}=${(myListPoints.sum())%10}"
        }

        if(opponentListCard.size == 2){
            id_game_tv_points_diller.text = "${opListPoints[0]}+${opListPoints[1]}=${(opListPoints.sum())%10}"
        }else{
            id_game_tv_points_diller.text = "${opListPoints[0]}+${opListPoints[1]}+${opListPoints[2]}=${(opListPoints.sum())%10}"
        }

    }

    //проверка нужна ли кому нибудь третья карта
    private fun check3Card(){

        if((myListPoints.sum())%10<6 && (opListPoints.sum())%10<8) {
            //мне полагается 3 карта
            myListCard.add(listRandom6Card[4])
            myListPoints.add(mapAllCard[myListCard[2]]!!)
            loadImage(nameServer+myListCard[2],id_game_img4_me)
        }

        if((opListPoints.sum())%10<6 && (myListPoints.sum())%10<8){
            //диллеру полагается 3 карта
            opponentListCard.add(listRandom6Card[5])
            opListPoints.add(mapAllCard[opponentListCard[2]]!!)
            loadImage(nameServer+opponentListCard[2],id_game_img4_banker)
        }

        if(myListCard.size==3){
            if((mapAllCard[myListCard[2]]==9 || mapAllCard[myListCard[2]]==1) && (opListPoints.sum())%10<4 && opponentListCard.size==2){
                //диллеру полагается 3 карта
                opponentListCard.add(listRandom6Card[5])
                opListPoints.add(mapAllCard[opponentListCard[2]]!!)
                loadImage(nameServer+opponentListCard[2],id_game_img4_banker)
            }
        }

        if(myListCard.size==3){
            if((mapAllCard[myListCard[2]]==8) && (opListPoints.sum())%10<3 && opponentListCard.size==2){
                //диллеру полагается 3 карта
                opponentListCard.add(listRandom6Card[5])
                opListPoints.add(mapAllCard[opponentListCard[2]]!!)
                loadImage(nameServer+opponentListCard[2],id_game_img4_banker)
            }
        }

        if(myListCard.size==3){
            if((mapAllCard[myListCard[2]]==6 || mapAllCard[myListCard[2]]==7) && (opListPoints.sum())%10<7 && opponentListCard.size==2){
                //диллеру полагается 3 карта
                opponentListCard.add(listRandom6Card[5])
                opListPoints.add(mapAllCard[opponentListCard[2]]!!)
                loadImage(nameServer+opponentListCard[2],id_game_img4_banker)
            }
        }

        if(myListCard.size==3){
            if((mapAllCard[myListCard[2]]==4 || mapAllCard[myListCard[2]]==5) && (opListPoints.sum())%10<6 && opponentListCard.size==2){
                //диллеру полагается 3 карта
                opponentListCard.add(listRandom6Card[5])
                opListPoints.add(mapAllCard[opponentListCard[2]]!!)
                loadImage(nameServer+opponentListCard[2],id_game_img4_banker)
            }
        }

        if(myListCard.size==3){
            if((mapAllCard[myListCard[2]]==2 || mapAllCard[myListCard[2]]==3) && (opListPoints.sum())%10<5 && opponentListCard.size==2){
                //диллеру полагается 3 карта
                opponentListCard.add(listRandom6Card[5])
                opListPoints.add(mapAllCard[opponentListCard[2]]!!)
                loadImage(nameServer+opponentListCard[2],id_game_img4_banker)
            }
        }

        showPointUpdate()
    }

    private fun checkWin(){

        if((myListPoints.sum())%10 > (opListPoints.sum())%10){
            //победа
            paintMe()
            showToast("you have won!")
            if(myOutcomeBet=="win"){
                showToast("you guessed right with the outcome of the game and get ${myBet*coefficient}")
                MAIN.addCash(myBet*coefficient)
                visibleButtonRestartAndFinish()
            }else{
                showToast("you did not guess the outcome of the game and lose ${myBet}")
                visibleButtonRestartAndFinish()
            }
        }
        if((myListPoints.sum())%10 < (opListPoints.sum())%10){
            //проигрыш
            paintBanker()
            showToast("you've lost")
            if(myOutcomeBet=="loss"){
                showToast("you guessed right with the outcome of the game and get ${myBet*coefficient}")
                MAIN.addCash(myBet*coefficient)
                visibleButtonRestartAndFinish()
            }else{
                showToast("you did not guess the outcome of the game and lose ${myBet}")
                visibleButtonRestartAndFinish()
            }
        }
        if((myListPoints.sum())%10 == (opListPoints.sum())%10){
            //ничья
            showToast("a draw!")
            paintBanker()
            paintMe()
            if(myOutcomeBet=="draw"){
                showToast("you guessed right with the outcome of the game and get ${myBet*coefficient}")
                MAIN.addCash(myBet*coefficient)
                visibleButtonRestartAndFinish()
            }else{
                showToast("you did not guess the outcome of the game and lose ${myBet}")
                visibleButtonRestartAndFinish()
            }

        }
    }

    private fun showToast(message:String){
        Toast.makeText(requireContext(),message,Toast.LENGTH_LONG).show()
    }

    private fun visibleButtonRestartAndFinish(){
        id_game_cs_button_finish_and_restart.isVisible = true
    }

    private fun paintBanker(){
        id_game_cs_banker.setBackgroundResource(R.color.black_green)
    }

    private fun paintMe(){
        id_game_cs_me.setBackgroundResource(R.color.black_green)
    }


}