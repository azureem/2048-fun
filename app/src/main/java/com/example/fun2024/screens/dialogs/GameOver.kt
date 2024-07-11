package com.example.fun2024.screens.dialogs

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.example.fun2024.R
import com.example.fun2024.databinding.DialogGameOverBinding
import com.example.fun2024.databinding.ScreenGameBinding

class GameOver : DialogFragment() {
    private var binding : DialogGameOverBinding?  = null
    private var onClickStart : (()->Unit)?= null
    private var score =0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DialogGameOverBinding.inflate(inflater, container, false)
        return  binding!!.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding!!.loseStart.setOnClickListener {
            onClickStart!!.invoke()
            dismiss()
        }
        binding!!.finalScore.text = score.toString()

    }

    override fun onStart() {
        super.onStart()
        val window = dialog!!.window
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }
     fun onClickStart(block:()->Unit){
        this.onClickStart = block
    }
    fun getScore( score: Int){
        this.score = score
    }
}