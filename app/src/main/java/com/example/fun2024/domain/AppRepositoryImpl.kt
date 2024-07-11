package com.example.fun2024.domain

import android.annotation.SuppressLint
import com.example.fun2024.screens.MySharedPreferences
import com.example.fun2024.screens.dialogs.Congrats
import kotlin.random.Random

class AppRepositoryImpl private constructor() {

    private var score = 0
    private var record = 0
    var congratsFlag = false


    companion object {
        private lateinit var instance: AppRepositoryImpl

        fun getInstance(): AppRepositoryImpl {
            if (!::instance.isInitialized) {
                instance = AppRepositoryImpl()
            }
            return instance
        }
    }

    @get:JvmName("getMatrixProperty")
    var matrix = arrayOf(
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0)
    )

    private var addElementAmount = 2

    init {
        addElement()
        addElement()
    }

     fun atFirst(){
        addElement()
        addElement()
    }

    fun getMatrix(): Array<Array<Int>> {
        return matrix
    }


    fun getScore(): Int = score

    private fun increaseScore(points: Int) {
        score += points
    }

    // bbu boshida 2 ni qo'shib beradi
    private fun addElement() {
        val empty = ArrayList<Pair<Int, Int>>()
        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                if (matrix[i][j] == 0) empty.add(Pair(i, j))
            }
        }

        if (empty.isEmpty()) return
        val randomIndex = Random.nextInt(0, empty.size)
        val findPairByRandomIndex = empty[randomIndex]
        matrix[findPairByRandomIndex.first][findPairByRandomIndex.second] = addElementAmount
    }


    fun createBasicMatrix() = arrayOf(
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0)
    )

    @SuppressLint("SuspiciousIndentation")


    fun moveLeft() {
        val newMatrix = createBasicMatrix()
        var index: Int
        var isAdded: Boolean
        for (i in matrix.indices) {
            index = 0
            isAdded = false

            for (j in matrix[i].indices) {
                if (matrix[i][j] == 0) continue
                if (newMatrix[i][0] == 0) {
                    newMatrix[i][0] = matrix[i][j]
                    continue
                }
                if (newMatrix[i][index] == matrix[i][j] && !isAdded) {
                    newMatrix[i][index] *= 2
                    increaseScore(newMatrix[i][index])
                    isAdded = true
                    if (newMatrix[i][index] == 32) {
                             congratsFlag = true

                    }
                } else {
                    newMatrix[i][++index] = matrix[i][j]
                    isAdded = false
                }
            }
        }

        matrix = newMatrix
        addElement()
    }

    fun saveGameData() {
        val matrixString = matrix.flatten().joinToString(",")
        MySharedPreferences.saveGame(matrixString)

    }


    fun moveRight() {
        val newMatrix = createBasicMatrix()
        var index: Int
        var isAdded: Boolean

        for (i in matrix.indices) {
            index = 3
            isAdded = false

            for (j in matrix[i].size - 1 downTo 0) {
                if (matrix[i][j] == 0) continue
                if (newMatrix[i][3] == 0) {
                    newMatrix[i][3] = matrix[i][j]
                    continue
                }

                if (newMatrix[i][index] == matrix[i][j] && !isAdded) {
                    newMatrix[i][index] *= 2
                    increaseScore(newMatrix[i][index])
                    isAdded = true

                    if (newMatrix[i][index] == 32  ) {
                        congratsFlag = true
                    }
                } else {
                    newMatrix[i][--index] = matrix[i][j]
                    isAdded = false
                }
            }
        }

        matrix = newMatrix
        addElement()


    }


    fun moveUp() {
        val newMatrix = createBasicMatrix()
        var index: Int
        var isAdded: Boolean

        for (i in matrix.indices) {
            index = 0
            isAdded = false
            for (j in matrix[i].indices) {
                if (matrix[j][i] == 0) continue
                if (newMatrix[0][i] == 0) {
                    newMatrix[0][i] = matrix[j][i]
                    continue
                }

                if (newMatrix[index][i] == matrix[j][i] && !isAdded) {
                    newMatrix[index][i] *= 2
                    increaseScore(newMatrix[index][i])
                    isAdded = true

                    if (newMatrix[i][index] == 32 ) {
                        congratsFlag = true
                    }
                } else {
                    newMatrix[++index][i] = matrix[j][i]
                    isAdded = false
                }
            }

        }
        matrix = newMatrix
        addElement()

    }

    fun moveDown() {
        val newMatrix = createBasicMatrix()
        var index: Int
        var isAdded: Boolean

        for (i in matrix.indices) {
            index = 3
            isAdded = false

            for (j in matrix[i].size - 1 downTo 0) {
                if (matrix[j][i] == 0) continue
                if (newMatrix[3][i] == 0) {
                    newMatrix[3][i] = matrix[j][i]
                    continue
                }

                if (newMatrix[index][i] == matrix[j][i] && !isAdded) {
                    newMatrix[index][i] *= 2
                    increaseScore(newMatrix[index][i])
                    isAdded = true

                    if (newMatrix[i][index] == 32) {
                        congratsFlag = true
                    }
                } else {
                    newMatrix[--index][i] = matrix[j][i]
                    isAdded = false
                }
            }
        }

        matrix = newMatrix
        addElement()
    }

    fun finish(): Boolean {
        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                if (matrix[i][j] == 0) return false
            }
        }
        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                if (j < matrix[i].size - 1 && matrix[i][j] == matrix[i][j + 1]) return false
                if ((i < matrix.size - 1) && (matrix[i][j] == matrix[i + 1][j])) {
                    return false
                }
            }
        }
        return true
    }


    fun resetGame() {
        matrix = createBasicMatrix()
        addElement()
        addElement()
    }

    fun resetTheScore() {
        score = 0
    }

    fun setScore(value: Int) {
        score = value
    }

    fun record(): Int {
        if (score > record) {
            record = score
            return record
        } else return record
    }


    fun setRecord(newRecord: Int) {
        record = newRecord
    }

}


