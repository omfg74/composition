package com.omfgdevelop.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.omfgdevelop.composition.R
import com.omfgdevelop.composition.databinding.FragmentGameBinding
import com.omfgdevelop.composition.domain.entity.GameResult
import com.omfgdevelop.composition.domain.entity.Level
import com.omfgdevelop.composition.presentation.viewModel.GameFragmentViewModel

class GameFragment : Fragment() {

    private lateinit var level: Level

    private lateinit var viewModel: GameFragmentViewModel

    companion object {

        const val NAME: String = "game_fragment"
        private const val KEY_LEVEL = "level"
        fun newInstance(level: Level): GameFragment {
            return GameFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_LEVEL, level)
                }
            }
        }
    }

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("binding is null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[GameFragmentViewModel::class.java]
        setListeners()
        startGame()
    }

    private fun setListeners() {
        with(binding) {
            tvOption1.setOnClickListener {
                viewModel.onChooseAnswer(
                    tvOption1.text.toString().toInt()
                )
            }
            tvOption2.setOnClickListener {
                viewModel.onChooseAnswer(
                    tvOption2.text.toString().toInt()
                )
            }
            tvOption3.setOnClickListener {
                viewModel.onChooseAnswer(
                    tvOption3.text.toString().toInt()
                )
            }
            tvOption4.setOnClickListener {
                viewModel.onChooseAnswer(
                    tvOption4.text.toString().toInt()
                )
            }
            tvOption5.setOnClickListener {
                viewModel.onChooseAnswer(
                    tvOption5.text.toString().toInt()
                )
            }
            tvOption6.setOnClickListener {
                viewModel.onChooseAnswer(
                    tvOption6.text.toString().toInt()
                )
            }
        }
        viewModel.question.observe(viewLifecycleOwner) {
            binding.tvSum.text = it.sum.toString()

            binding.tvLeftNumber.text = it.visibleNumber.toString()
            binding.tvOption1.text = it.options[0].toString()
            binding.tvOption2.text = it.options[1].toString()
            binding.tvOption3.text = it.options[2].toString()
            binding.tvOption4.text = it.options[3].toString()
            binding.tvOption5.text = it.options[4].toString()
            binding.tvOption6.text = it.options[5].toString()

        }

        viewModel.progressAnswers.observe(viewLifecycleOwner) {
            binding.tvAnswersProgress.text = it

        }


    }

    private fun startGame() {
        viewModel.startGame(level)
    }

    private fun navigateToGameFinishedFragment(gameResult: GameResult) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, GameFinishedFragment.newInstance(gameResult))
            .addToBackStack(GameFinishedFragment.NAME)
            .commit()
    }

    private fun parseArgs() {
        requireArguments().getParcelable<Level>(KEY_LEVEL)?.let {
            level = it
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}