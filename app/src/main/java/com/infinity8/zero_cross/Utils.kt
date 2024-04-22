package com.infinity8.zero_cross

import android.content.Context
import android.text.InputType
import android.view.Gravity
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import android.widget.Toast

fun checkRowCol(
    button1: Button,
    button2: Button,
    button3: Button
) =
    button1.text.toString()
        .isNotEmpty() && button1.text.toString() == button2.text.toString() && button2.text.toString() == button3.text.toString()

fun buttonFunctionality(
    button: Button,
    params: GridLayout.LayoutParams,
    i: Int,
    j: Int,
    onClick: (Button) -> Unit
) {
    button.apply {
        layoutParams = params
        gravity = Gravity.CENTER
        inputType = InputType.TYPE_NULL
        tag = intArrayOf(i, j)
        textSize = 30f
        setOnClickListener {
            onClick(this)
        }
    }
}

fun <T> Context.showToast(message: T) =
    Toast.makeText(this, message.toString(), Toast.LENGTH_LONG).show()

fun TextView.updateScoreAndReset(counter: Int, clearFields:()->Unit): Int {
    val newCounter = counter + 1
    this.text = newCounter.toString()
    clearFields()
    return newCounter
}

fun Boolean.toPlayer(): String = if (this) "X" else "O"
fun Boolean.toWinText(): String = if (this) "Player 1 win" else "Player 2 win"
