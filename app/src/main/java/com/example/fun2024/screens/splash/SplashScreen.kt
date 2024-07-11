package com.example.fun2024.screens.splash

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
import android.view.WindowManager
import androidx.annotation.ColorInt
import androidx.navigation.fragment.findNavController
import com.example.fun2024.R
import com.example.fun2024.databinding.ScreenSplashBinding
import com.google.android.material.navigation.NavigationBarView

    class SplashScreen :androidx.fragment.app.Fragment(R.layout.screen_splash) {
        private var countDownTimer: CountDownTimer? = null
        private var binding: ScreenSplashBinding? = null


        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            binding = ScreenSplashBinding.inflate(inflater, container, false)
            return binding!!.root
        }

        @SuppressLint("NewApi", "ObsoleteSdkInt")
        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            // bu status barni backgrountiga



           // requireActivity(). window.getDecorView().getWindowInsetsController()!!.setSystemBarsAppearance(APPEARANCE_LIGHT_STATUS_BARS, APPEARANCE_LIGHT_STATUS_BARS);
          //  requireActivity().window.getDecorView().getWindowInsetsController()!!.setSystemBarsAppearance(0, APPEARANCE_LIGHT_STATUS_BARS);
//
//            requireActivity(). window.getDecorView().setSystemUiVisibility(0);
//           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//                requireActivity().window.getDecorView().getWindowInsetsController()!!.setSystemBarsAppearance(0, APPEARANCE_LIGHT_STATUS_BARS);
//            }

//
//
//            requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//            requireActivity().window.statusBarColor = Color.parseColor("#F1FAEE")

           //bu status bardagi yozuvlarga, wifi, charge, clock rangiga
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requireActivity().window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }




            //bottom navigationni backgroundinga
            requireActivity().window.navigationBarColor = Color.parseColor("#f5f5f5")




            android.os.Handler().postDelayed({
                findNavController().navigate(R.id.action_splashScreen_to_homeScreen)
            }, 1500)
        }


}