package com.madhavsolanki.quizapp

data class QuizModel(
    val id:String?= null,
    val title:String?= null,
    val subtitle:String?= null,
    val time:String?= null,
    val questionList: List<QuestionModel>

){
    constructor():this("","","","", emptyList())
}

data class QuestionModel(
        val question:String ?= null,
        val options:List<String>?= null,
        val correct:String?=null
        )
{
    constructor():this("", emptyList(),"")
}