package com.madhavsolanki.quizapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.madhavsolanki.quizapp.databinding.ActivityQuizBinding

class QuizActivity : AppCompatActivity() {

    // We have to pass the Questions from one activity to another so here we use companion object
    companion object{
        var questionModelList: List<QuestionModel> = listOf()
        var time : String = ""
    }
    private lateinit var binding:ActivityQuizBinding
    var currentQuestionIndex = 0 ;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadQuestion()
        startTimer()
    }
    private fun startTimer() {
        // Split the time into minutes and seconds
        val timeParts = time.split(":")

        // Extract minutes and seconds
        val minutes = timeParts[0].toInt()
        val seconds = timeParts[1].toInt()

        // Calculate total time in milliseconds
        val totalTimeInMillis = (minutes * 60 + seconds) * 1000L

        object : CountDownTimer(totalTimeInMillis, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                val seconds = millisUntilFinished / 1000
                val minutes = seconds / 60
                val remainingSeconds = seconds % 60
                binding.timerIndicatorTextview.text = String.format("%02d:%02d", minutes, remainingSeconds)
            }

            override fun onFinish() {
                // finish the quiz
            }
        }.start()
    }

//    private fun startTimer(){
//        val totalTimeInMillis = time.toInt() * 60 * 1000L
//        object : CountDownTimer(totalTimeInMillis,1000L){
//            override fun onTick(millisUntilFinished: Long) {
//                val seconds = millisUntilFinished / 1000
//                val minutes = seconds / 60
//                val remainingSeconds = seconds % 60
//                binding.timerIndicatorTextview.text = String.format("%02d:%02d", minutes,remainingSeconds)
//            }
//
//            override fun onFinish() {
//                // finish the quiz
//            }
//
//        }.start()
//    }

    @SuppressLint("SetTextI18n")
    private fun loadQuestion(){
        binding.apply {
            questionIndicatorTextview.text = "Question ${currentQuestionIndex+1 }/  ${questionModelList.size}"
            questionProgressIndicator.progress = (currentQuestionIndex.toFloat() / questionModelList.size.toFloat() * 100).toInt()

            questionTextview.text = questionModelList[currentQuestionIndex].question
            btn0.text = questionModelList[currentQuestionIndex].options?.get(0)
            btn1.text = questionModelList[currentQuestionIndex].options?.get(1)
            btn2.text = questionModelList[currentQuestionIndex].options?.get(2)
            btn3.text = questionModelList[currentQuestionIndex].options?.get(3)

        }
    }

}