package com.example.appcalculadora

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var tvOperation: TextView
    private lateinit var tvResult: TextView

    private var currentNumber = ""
    private var firstNumber = 0.0
    private var operation = ""
    private var newNumber = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.inicio)

        // Inicializar vistas
        tvOperation = findViewById(R.id.tvOperation)
        tvResult = findViewById(R.id.tvResult)

        // Configurar botones de números
        setupNumberButtons()

        // Configurar botones de operaciones
        setupOperationButtons()

        // Configurar botones especiales
        setupSpecialButtons()
    }

    private fun setupNumberButtons() {
        val numberButtons = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        )

        numberButtons.forEach { buttonId ->
            findViewById<Button>(buttonId).setOnClickListener {
                val number = (it as Button).text.toString()
                appendNumber(number)
            }
        }

        // Botón punto decimal
        findViewById<Button>(R.id.btnDot).setOnClickListener {
            appendDecimal()
        }
    }

    private fun setupOperationButtons() {
        findViewById<Button>(R.id.btnPlus).setOnClickListener { setOperation("+") }
        findViewById<Button>(R.id.btnMinus).setOnClickListener { setOperation("-") }
        findViewById<Button>(R.id.btnMultiply).setOnClickListener { setOperation("×") }
        findViewById<Button>(R.id.btnDivide).setOnClickListener { setOperation("÷") }
        findViewById<Button>(R.id.btnEquals).setOnClickListener { calculate() }
    }

    private fun setupSpecialButtons() {
        findViewById<Button>(R.id.btnAC).setOnClickListener { clearAll() }
        findViewById<Button>(R.id.btnPlusMinus).setOnClickListener { toggleSign() }
        findViewById<Button>(R.id.btnPercent).setOnClickListener { calculatePercent() }
    }

    private fun appendNumber(number: String) {
        if (newNumber) {
            currentNumber = number
            newNumber = false
        } else {
            currentNumber += number
        }
        tvResult.text = currentNumber
    }

    private fun appendDecimal() {
        if (newNumber) {
            currentNumber = "0."
            newNumber = false
        } else if (!currentNumber.contains(".")) {
            currentNumber += "."
        }
        tvResult.text = currentNumber
    }

    private fun setOperation(op: String) {
        if (currentNumber.isNotEmpty()) {
            firstNumber = currentNumber.toDouble()
            operation = op
            tvOperation.text = "$firstNumber $operation"
            currentNumber = ""
            newNumber = true
        }
    }

    private fun calculate() {
        if (currentNumber.isNotEmpty() && operation.isNotEmpty()) {
            val secondNumber = currentNumber.toDouble()
            val result = when (operation) {
                "+" -> firstNumber + secondNumber
                "-" -> firstNumber - secondNumber
                "×" -> firstNumber * secondNumber
                "÷" -> if (secondNumber != 0.0) firstNumber / secondNumber else Double.POSITIVE_INFINITY
                else -> secondNumber
            }

            tvOperation.text = "$firstNumber $operation $secondNumber ="
            tvResult.text = if (result == Double.POSITIVE_INFINITY) "Error" else formatResult(result)

            currentNumber = result.toString()
            operation = ""
            newNumber = true
        }
    }

    private fun clearAll() {
        currentNumber = ""
        firstNumber = 0.0
        operation = ""
        newNumber = true
        tvOperation.text = ""
        tvResult.text = "0"
    }

    private fun toggleSign() {
        if (currentNumber.isNotEmpty()) {
            val number = currentNumber.toDouble()
            currentNumber = (-number).toString()
            tvResult.text = currentNumber
        }
    }

    private fun calculatePercent() {
        if (currentNumber.isNotEmpty()) {
            val number = currentNumber.toDouble()
            val result = number / 100
            tvResult.text = formatResult(result)
            currentNumber = result.toString()
            newNumber = true
        }
    }

    private fun formatResult(result: Double): String {
        return if (result == result.toLong().toDouble()) {
            result.toLong().toString()
        } else {
            String.format("%.2f", result).removeSuffix(".00").removeSuffix("0")
        }
    }
}
