package com.marcuspereira.appinvesty

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import java.text.NumberFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val tvInvestmentResult = findViewById<TextView>(R.id.tv_final_investment)
        val tvIncome = findViewById<TextView>(R.id.tv_income)
        val tieMonthlyContribution = findViewById<TextInputEditText>(R.id.tie_monthly)
        val tieNumberOfMonths = findViewById<TextInputEditText>(R.id.tie_months)
        val tieFees = findViewById<TextInputEditText>(R.id.tie_fees)

        val btnClean = findViewById<Button>(R.id.btn_clean)
        val btnCalculate = findViewById<Button>(R.id.btn_calculate)

        btnCalculate.setOnClickListener {
            val feesStr: Double = tieFees.text.toString().toDouble()
            val numMonthsStr: Double = tieNumberOfMonths.text.toString().toDouble()
            val monthlyContributionStr: Double = tieMonthlyContribution.text.toString().toDouble()

            val formatter = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

            val fees = feesStr / 100
            val monthlyContribution = monthlyContributionStr * ((Math.pow(
                1 + fees,
                numMonthsStr
            ) - 1) / fees) * (1 + fees)
            val initialGrowth = monthlyContributionStr * Math.pow(1 + fees, numMonthsStr)
            val result = initialGrowth + monthlyContribution
            val performance = result - (numMonthsStr * monthlyContributionStr)

            val performanceFormat = formatter.format(performance)
            val resultFormat = formatter.format(result)

            tvIncome.text = performanceFormat
            tvInvestmentResult.text = resultFormat
        }

        btnClean.setOnClickListener {
            tvInvestmentResult.text = "0.0"
            tvIncome.text = "0.0"
            tieMonthlyContribution.setText("")
            tieNumberOfMonths.setText("")
            tieFees.setText("")
        }

    }
}