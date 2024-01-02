package com.madhavsolanki.quizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.madhavsolanki.quizapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    // Here we create a list of quiz Model
    lateinit var quizModelList : MutableList<QuizModel>

    lateinit var adapter: QuizListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        quizModelList = mutableListOf();
        getDataFromFirebase()

    }

    private fun setupRecyclerView(){
        adapter = QuizListAdapter(quizModelList)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    private fun getDataFromFirebase(){
        // Dummy data
        val listQuestionModel = mutableListOf<QuestionModel>()
        listQuestionModel.add(QuestionModel("What is android", mutableListOf("Language", "OS", "Product", "None"), "os"))
        listQuestionModel.add(QuestionModel("What owns android", mutableListOf("Apple", "Samsung", "Google", "Microsoft"), "Google"))
        listQuestionModel.add(QuestionModel("which assistant  android uses", mutableListOf("Kindle", "ECHO", "Siri", "Google Assistant"), "Google Assistant"))

        quizModelList.add(QuizModel("1","Programming","All basic programming Questions","20:00",listQuestionModel))
        quizModelList.add(QuizModel("2","Physics","All basic Physics Questions","20:00",listQuestionModel))
        quizModelList.add(QuizModel("3","Chemistry","All basic Chemistry Questions","20:00 ",listQuestionModel))

        setupRecyclerView()
    }
}


