package com.omfgdevelop.composition.presentation

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.omfgdevelop.composition.R
import com.omfgdevelop.composition.domain.entity.GameResult

@BindingAdapter("requiredAnswers")
fun bindRequiredAnswers(textView: TextView, count: Int) {

    textView.text = String.format(
        textView.context.getString(R.string.required_score), count
    )
}

@BindingAdapter("requiredPercent")
fun bindRequiredPercent(textView: TextView, count: Int) {

    textView.text = String.format(
        textView.context.getString(R.string.required_percentage), count
    )
}

@BindingAdapter("countOfRightAnswers")
fun bindCountOfRightAnswers(textView: TextView, count: Int) {

    textView.text = String.format(
        textView.context.getString(R.string.score_answers), count
    )
}

@BindingAdapter("scorePercentage")
fun bingScorePercentage(textView: TextView, gameResult: GameResult) {
    textView.text =
        String.format(
            textView.context.getString(R.string.score_percentage),
            getPercentOfRightAnswers(gameResult)
        )
}

fun getPercentOfRightAnswers(gameResult: GameResult) = with(gameResult) {
    if (countOfQuestions == 0) {
        0
    } else {
        ((countOfRightAnswers / countOfRightAnswers.toDouble()) * 100).toInt()
    }
}

@BindingAdapter("image")
fun bindImage(imageView: ImageView, good: Boolean) {
    if (good) {
        imageView.setImageResource(R.drawable.ic_smile)
    } else {
        imageView.setImageResource(R.drawable.ic_sad)
    }
}