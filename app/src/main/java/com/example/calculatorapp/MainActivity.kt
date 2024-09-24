package com.example.calculatorapp
import android.widget.Button
import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity

import net.objecthunter.exp4j.ExpressionBuilder

import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.calculatorapp.ui.theme.CalculatorAppTheme

class MainActivity : ComponentActivity() {
private lateinit var display: TextView
private var lastNumeric: Boolean = false
private var stateError: Boolean = false
private var lastDot: Boolean = false

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    display = findViewById(R.id.tvDisplay)

    // Numeric buttons
    findViewById<Button>(R.id.btn0).setOnClickListener { onDigitClick("0") }
    findViewById<Button>(R.id.btn1).setOnClickListener { onDigitClick("1") }
    findViewById<Button>(R.id.btn2).setOnClickListener { onDigitClick("2") }
    findViewById<Button>(R.id.btn3).setOnClickListener { onDigitClick("3") }
    findViewById<Button>(R.id.btn4).setOnClickListener { onDigitClick("4") }
    findViewById<Button>(R.id.btn5).setOnClickListener { onDigitClick("5") }
    findViewById<Button>(R.id.btn6).setOnClickListener { onDigitClick("6") }
    findViewById<Button>(R.id.btn7).setOnClickListener { onDigitClick("7") }
    findViewById<Button>(R.id.btn8).setOnClickListener { onDigitClick("8") }
    findViewById<Button>(R.id.btn9).setOnClickListener { onDigitClick("9") }

    // Operator buttons
    findViewById<Button>(R.id.btnAdd).setOnClickListener { onOperatorClick("+") }
    findViewById<Button>(R.id.btnSubtract).setOnClickListener { onOperatorClick("-") }
    findViewById<Button>(R.id.btnMultiply).setOnClickListener { onOperatorClick("*") }
    findViewById<Button>(R.id.btnDivide).setOnClickListener { onOperatorClick("/") }

    // Other buttons
    findViewById<Button>(R.id.btnEquals).setOnClickListener { onEqual() }
    findViewById<Button>(R.id.btnDot).setOnClickListener { onDecimalPoint() }
    findViewById<Button>(R.id.btnclear).setOnClickListener { onClear() }

}

private fun onDigitClick(digit: String) {
    if (stateError) {
        display.text = digit
        stateError = false
    } else {
        display.append(digit)
    }
    lastNumeric = true
}

private fun onOperatorClick(operator: String) {
    if (lastNumeric && !stateError) {
        display.append(operator)
        lastNumeric = false
        lastDot = false
    }
}

private fun onDecimalPoint() {
    if (!lastDot && !stateError) {
        display.append(".")
        lastNumeric = false
        lastDot = true
    }
}

private fun onEqual() {
    if (lastNumeric && !stateError) {
        val expression = display.text.toString()
        try {
            val result = evaluate(expression)
            val format = "%.${6}f"
            display.text= String.format(format, result.toDouble())
            lastDot = true
        } catch (e: Exception) {
            display.text =expression
            stateError = true
            lastNumeric = false
        }
    }
}
    private fun onClear() {
        display.text="0"
        lastNumeric=false
        lastDot=false
        stateError=false
        lastNumeric = false
    }
    fun evaluate(expression: String): Double {
        val exp = ExpressionBuilder(expression).build()
        return exp.evaluate()
    }
}