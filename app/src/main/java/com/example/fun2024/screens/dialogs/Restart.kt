package com.example.fun2024.screens.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.fun2024.databinding.DialogRestartBinding

class Restart : DialogFragment() {
    private var binding: DialogRestartBinding? = null

    private var onClickYes :(()->Unit) ? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogRestartBinding.inflate(inflater, container, false)
        return binding!!.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding!!.restartNo.setOnClickListener {
            dismiss()
        }

        binding!!.restartYes.setOnClickListener {
            onClickYes!!.invoke()
            dismiss()
        }
    }


     fun onClickYes(block:() ->Unit){
        this.onClickYes = block
    }


    override fun onStart() {
        super.onStart()
        val window = dialog!!.window
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }
}