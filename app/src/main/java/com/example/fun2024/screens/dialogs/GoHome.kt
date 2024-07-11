package com.example.fun2024.screens.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.example.fun2024.databinding.DialogGoHomeBinding

class GoHome : DialogFragment() {

    private var binding: DialogGoHomeBinding? = null

    private var onClickYes: (() -> Unit)? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogGoHomeBinding.inflate(inflater, container, false)
        return binding!!.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      //  requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        //requireActivity().window.statusBarColor = Color.parseColor("#66000000")

        binding!!.backNo.setOnClickListener {
            dismiss()
        }

        binding!!.backYes.setOnClickListener {
            onClickYes!!.invoke()
            dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        val window = dialog!!.window
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

    fun onClickYes(block: () -> Unit) {
        this.onClickYes = block
    }

}