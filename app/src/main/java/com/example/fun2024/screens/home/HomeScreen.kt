package com.example.fun2024.screens.home

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.fun2024.R
import com.example.fun2024.databinding.ScreenGameBinding
import com.example.fun2024.databinding.ScreenHomeBinding

class HomeScreen: Fragment(R.layout.screen_home) {

    private val binding by viewBinding (ScreenHomeBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        requireActivity().window.navigationBarColor = Color.parseColor("#F1FAEE")

        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        requireActivity().window.statusBarColor = Color.parseColor("#F1FAEE")

        binding.playGameBtn.setOnClickListener {
            findNavController().navigate(R.id.action_homeScreen_to_gameScreen)
        }
        binding.infoGameBtn.setOnClickListener {
            findNavController().navigate(R.id.action_homeScreen_to_infoScreen)
        }
    }
}