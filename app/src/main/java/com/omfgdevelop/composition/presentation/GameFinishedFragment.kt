package com.omfgdevelop.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.omfgdevelop.composition.R
import com.omfgdevelop.composition.databinding.FragmentGameFinishedBinding
import com.omfgdevelop.composition.domain.entity.GameResult

class GameFinishedFragment : Fragment() {


    private val args by navArgs<GameFinishedFragmentArgs>()

    private var _binding: FragmentGameFinishedBinding? = null
    private val binding: FragmentGameFinishedBinding
        get() = _binding ?: throw RuntimeException()


    private fun bindViews() {
        with(binding) {
            emojiResult.setImageResource(getSmileResId())
            tvRequiredAnswers.text = String.format(
                getString(R.string.required_score),
                args.gameResult.gameSettings.minCountOfRightAnswers
            )
            tvScoreAnswers.text =
                String.format(
                    getString(R.string.score_answers),
                    args.gameResult.countOfRightAnswers
                )
            tvRequiredPercentage.text = String.format(
                getString(R.string.required_percentage),
                args.gameResult.gameSettings.minCountOfRightAnswers
            )
            tvScorePercentage.text =
                String.format(getString(R.string.score_percentage), getPercentOfRightAnswers())
        }

    }

    private fun getPercentOfRightAnswers() = with(args.gameResult) {
        if (countOfQuestions == 0) {
            0
        } else {
            ((countOfRightAnswers / countOfRightAnswers.toDouble()) * 100).toInt()
        }
    }

    private fun getSmileResId(): Int {
        return if (args.gameResult.winner) {
            R.drawable.ic_smile
        } else {
            R.drawable.ic_sad
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        bindViews()
    }

    private fun setListeners() {
        binding.buttonRetry.setOnClickListener {
            retryGame()
        }
    }


    private fun retryGame() {
        findNavController().popBackStack()
    }

}