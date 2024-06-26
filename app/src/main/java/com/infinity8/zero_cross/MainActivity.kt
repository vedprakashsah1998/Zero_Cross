package com.infinity8.zero_cross

import android.os.Bundle
import android.text.InputType
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.infinity8.zero_cross.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val buttons = Array(3) { arrayOfNulls<Button>(3) }
    private var player1Turn = true
    private lateinit var binding: ActivityMainBinding
    private var counter1 = 0
    private var counter2 = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        for (i in 0 until 3) {
            for (j in 0 until 3) {
                val button = Button(this)
                val params = GridLayout.LayoutParams()
                params.apply {
                    rowSpec = GridLayout.spec(i, GridLayout.FILL, 1f)
                    columnSpec = GridLayout.spec(j, GridLayout.FILL, 1f)
                }
                buttonFunctionality(button, params, i, j, onClick = {
                    buttonClicked(it)
                })

                buttons[i][j] = button
                binding.gridLayout.addView(button)
            }
        }
    }

    private fun buttonClicked(button: Button) {
        if (button.text.toString().isEmpty()) {
            button.text = if (player1Turn) "X" else "O"
            player1Turn = !player1Turn
        }
        checkWinAndShowMsg(player1Turn)
    }

    private fun checkWinAndShowMsg(player1Turn: Boolean) {
        if (checkForWin()) {
            val winnerSymbol = player1Turn.toPlayer()
            showMsg(player1Turn.toWinText())
            if (winnerSymbol == "X") {
                counter1 = binding.win1Txt.updateScoreAndReset(counter1) {
                    buttons.clearFields()
                }
            } else {
                counter2 = binding.win2Txt.updateScoreAndReset(counter2) {
                    buttons.clearFields()
                }

            }
            return
        } else if (isAllFieldsEmpty() && !checkForWin()) {
            showMsg("Draw")
        }
    }

    private fun showMsg(message: String) {
        showToast(message)
        buttons.clearFields()
    }

    private fun checkForWin(): Boolean {
        for (i in 0 until 3) {
            if (checkRowCol(buttons[i][0]!!, buttons[i][1]!!, buttons[i][2]!!)) {
                return true
            }
        }

        for (i in 0 until 3) {
            if (checkRowCol(buttons[0][i]!!, buttons[1][i]!!, buttons[2][i]!!)) {
                return true
            }
        }
        if (checkRowCol(buttons[0][0]!!, buttons[1][1]!!, buttons[2][2]!!)) {
            return true
        }
        if (checkRowCol(buttons[0][2]!!, buttons[1][1]!!, buttons[2][0]!!)) {
            return true
        }
        return false
    }

    private fun isAllFieldsEmpty(): Boolean {
        for (i in 0 until 3) {
            for (j in 0 until 3) {
                if (buttons[i][j]?.text.toString().isEmpty()) {
                    return false
                }
            }
        }
        return true
    }

}