package com.example.fun2024.screens

import android.content.Context
import android.content.SharedPreferences

class MySharedPreferences {

    companion object {
        private val PREFS_NAME = "2048Prefs"
        private val MATRIX_KEY = "matrixKey"

        private lateinit var pref: SharedPreferences

        fun init(context: Context) {
            if (!(Companion::pref.isInitialized)) pref =
                context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        }


        fun saveGame(matrixString: String) {
            val editor = pref.edit()

            editor.putString(MATRIX_KEY, matrixString)
            editor.apply()
        }

        fun getState(): Array<Array<Int>> {
            val matrixString = pref.getString(MATRIX_KEY, null)
            var matrix = arrayOf(
                arrayOf(0, 0, 0, 0),
                arrayOf(0, 0, 0, 0),
                arrayOf(0, 0, 0, 0),
                arrayOf(0, 0, 0, 0)
            )

            if (matrixString != null) {
                val values = matrixString.split(",").map { it.toInt() }
                matrix = Array(4) { i -> Array(4) { j -> values[i * 4 + j] } }
            }

            return matrix
        }


        fun saveScore(score: Int) {
            val editor = pref.edit()
            editor.putInt("SCORE_KEY", score)
            editor.apply()
        }

        fun getScore(): Int {
            return pref.getInt("SCORE_KEY", 0)
        }


        fun saveRecord(record: Int) {
            val editor = pref.edit()
            editor.putInt("RECORD_KEY", record)
            editor.apply()
        }

        fun getRecord(): Int {
            return pref.getInt("RECORD_KEY", 0)
        }




    }


}