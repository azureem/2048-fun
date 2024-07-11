package com.example.fun2024.screens.game

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.fun2024.R
import com.example.fun2024.data.SideEnum
import com.example.fun2024.databinding.ScreenGameBinding
import com.example.fun2024.domain.AppRepositoryImpl
import com.example.fun2024.screens.MySharedPreferences
import com.example.fun2024.screens.dialogs.Congrats
import com.example.fun2024.screens.dialogs.GameOver
import com.example.fun2024.screens.dialogs.GoHome
import com.example.fun2024.screens.dialogs.Restart
import com.example.fun2024.utils.MyBackgroundUtil
import com.example.fun2024.utils.MyTouchListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameScreen : Fragment(R.layout.screen_game) {

    private val binding by viewBinding(ScreenGameBinding::bind)
    private val views = ArrayList<AppCompatTextView>(16)
    private val repository = AppRepositoryImpl.getInstance()
    private lateinit var myPref: SharedPreferences
    private lateinit var scoreTextView: AppCompatTextView
    private lateinit var recordTextView: AppCompatTextView
    private var myShar = MySharedPreferences
    var finish = false


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//        requireActivity().window.statusBarColor = Color.parseColor("#F1FAEE")


        requireActivity().window.navigationBarColor = Color.parseColor("#F1FAEE")
        myPref = requireContext().getSharedPreferences("SHARED", Context.MODE_PRIVATE)
        requireActivity().onBackPressedDispatcher.addCallback(requireActivity(), callback)
        binding.gohome.setOnClickListener {
            goBack()
        }

        loadViews()
        attachTouchListener()
        val savedMatrix = myShar.getState()
        Log.d("GGG", "Matrix: ${savedMatrix.joinToString("\n") { it.contentToString() }}")
        if (savedMatrix.all { it.all { it == 0 } }) {
            repository.resetGame()
            Log.d("GGG", "oxaxaxxxax")
            repository.saveGameData()
        } else {
            repository.matrix = savedMatrix
            Log.d("GGG", "elelele: ")
        }
        loadData()
        scoreTextView = binding.score as AppCompatTextView
        recordTextView = binding.record as AppCompatTextView
        binding.restart.setOnClickListener {
            restartDialog()
        }
        updateScore()
        restartGame()
       // checkCongrats()
    }

//    private fun checkCongrats() {
//        val hasHighTile = repository.getMatrix().any { row -> row.contains(32) } // Adjust the value (e.g., 64) as needed
//        val savedMatrix = repository.getMatrix().clone() // Create a copy of the current matrix
//
//        // Perform swipe action (e.g., moveLeft, moveRight, etc.)
//
//        // Check if the matrix has changed after the swipe
//        val hasChanged = !Arrays.deepEquals(savedMatrix, repository.getMatrix());
//
//        if (hasChanged && hasHighTile) {
//            repository.congratsFlag = true;
//            val congratsDialog = Congrats();
//            congratsDialog.show(parentFragmentManager, "");
//        }
//    }


    private fun checkCongrats() {
        if (repository.congratsFlag) {
          //  val tile = repository.getMatrix().any { row -> row.contains(32) }
          //  if (tile) {
                repository.congratsFlag = false
                val congratsDialog = Congrats()
                congratsDialog.show(parentFragmentManager, "")
          //  }
        }
    }

    private fun restartDialog() {
        var dialog = Restart()
        dialog.show(parentFragmentManager, "")
        dialog.onClickYes {
            restartGame()
        }
    }

    private fun loadViews() {
        for (i in 0 until binding.mainView.childCount) {
            val line = binding.mainView[i] as LinearLayoutCompat
            for (j in 0 until line.childCount) {
                views.add(line[j] as AppCompatTextView)
            }
        }


    }

    @SuppressLint("ClickableViewAccessibility")
    private fun attachTouchListener() {
        val myTouchListener = MyTouchListener(requireContext())
        myTouchListener.setActionSideEnumListener {

            when (it) {
                SideEnum.DOWN -> {
                    repository.moveDown()
                   // checkCongrats()
                }

                SideEnum.RIGHT -> {
                    repository.moveRight()
                 //   checkCongrats()
                }

                SideEnum.UP -> {
                    repository.moveUp()
                  //  checkCongrats()
                }

                SideEnum.LEFT -> {
                    repository.moveLeft()
                  //  checkCongrats()
                }
            }
            updateScore()
            updateRecord()
            finish = repository.finish()
            gameOver()
            loadData()


        }
        binding.mainView.setOnTouchListener(myTouchListener)
    }

    fun gmOver() {
        if (finish) {
            val builder = AlertDialog.Builder(requireActivity())
                .setMessage("Game is Over!")
                .setPositiveButton("Restart") { dialog, id ->
                    restartGame()
                    dialog.dismiss() // Dismiss the dialog when Restart is clicked
                }
            builder.setCancelable(false)
            val dialog = builder.create()
            dialog.show()
        }
    }

    fun gameOver() {
        if (finish) {
            var score = repository.getScore()
            var dialog = GameOver()
            dialog.show(parentFragmentManager, "")
            dialog.onClickStart {
                restartGame()
            }
            dialog.getScore(score)
        }


    }

    fun perfect() {
        var score = repository.getScore()
        if (score == 2048) {
            val job =
                CoroutineScope(Dispatchers.Main).launch { // Launch a coroutine on the main thread
                    binding.perfect.visibility = View.VISIBLE
                    delay(2000) // Wait for 2 seconds
                    binding.perfect.visibility = View.GONE
                }
        }
    }


    fun goBack() {
        var dialog = GoHome()
        dialog.show(parentFragmentManager, "")
        dialog.onClickYes {
            findNavController().navigateUp()
        }
    }

    private fun loadData() {
        val matrix = repository.getMatrix()
        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                if (matrix[i][j] == 0) views[i * 4 + j].text = ""
                else views[i * 4 + j].text = "${matrix[i][j]}"

                views[i * 4 + j].setBackgroundResource(MyBackgroundUtil.backgroundByAmount(matrix[i][j]))
            }
        }
    }

    private fun updateScore() {
        scoreTextView.text = "${repository.getScore()}"
    }

    private fun updateRecord() {
        recordTextView.text = repository.record().toString()
    }

    fun restartGame() {
        repository.resetGame()
        repository.resetTheScore()
        // myPref.edit().clear().apply()
        updateScore()
        updateRecord()
        loadData()
        repository.congratsFlag = false


    }

    override fun onPause() {
        super.onPause()
        repository.saveGameData()
        myShar.saveScore(repository.getScore())
        myShar.saveRecord(repository.record())

    }

    override fun onResume() {
        super.onResume()
        val loadedMatrix = myShar.getState()
        repository.matrix = loadedMatrix
        loadData()
        repository.setScore(myShar.getScore())
        updateScore()
        repository.setRecord(myShar.getRecord())
        updateRecord()
    }


    private val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
        }
    }


}