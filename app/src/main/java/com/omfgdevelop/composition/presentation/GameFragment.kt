package com.omfgdevelop.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.omfgdevelop.composition.R
import com.omfgdevelop.composition.databinding.FragmentGameBinding
import com.omfgdevelop.composition.domain.entity.GameResult
import com.omfgdevelop.composition.domain.entity.GameSettings
import com.omfgdevelop.composition.domain.entity.Level

class GameFragment : Fragment() {

    private lateinit var level: Level

    companion object {

        const val NAME: String="game_fragment"
        private const val KEY_LEVEL = "level"
        fun newInstance(level: Level): GameFragment {
            return GameFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(KEY_LEVEL, level)
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
        setListeners()
    }

    private fun setListeners() {
        with(binding) {
            tvSum.setOnClickListener {
                navigateToGameFinishedFragment(
                    GameResult(
                        true, 0, 0,
                        GameSettings(0, 0, 0, 0)
                    )
                )
            }
        }
    }

    private fun navigateToGameFinishedFragment(gameResult: GameResult) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, GameFinishedFragment.newInstance(gameResult))
            .addToBackStack(GameFinishedFragment.NAME)
            .commit()
    }

    private fun parseArgs() {
        level = requireArguments().getSerializable(KEY_LEVEL) as Level
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}