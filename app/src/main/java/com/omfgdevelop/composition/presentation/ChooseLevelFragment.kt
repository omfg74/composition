package com.omfgdevelop.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.omfgdevelop.composition.R
import com.omfgdevelop.composition.databinding.FragmentChooseLevelBinding
import com.omfgdevelop.composition.domain.entity.Level

class ChooseLevelFragment : Fragment() {


    private var _binding: FragmentChooseLevelBinding? = null
    private val binding: FragmentChooseLevelBinding
        get() = _binding ?: throw RuntimeException("binding is null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChooseLevelBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()

    }

    private fun setListeners() {
        with(binding) {
            buttonLevelTest.setOnClickListener { navigateToGameFragment(Level.TEST) }
            buttonLevelEasy.setOnClickListener { navigateToGameFragment(Level.EASY) }
            buttonLevelNormal.setOnClickListener { navigateToGameFragment(Level.NORMAL) }
            buttonLevelHard.setOnClickListener { navigateToGameFragment(Level.HARD) }
        }
    }

    private fun navigateToGameFragment(level: Level) {
        findNavController().navigate(ChooseLevelFragmentDirections.actionChooseLevelFragmentToGameFragment(level))

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}