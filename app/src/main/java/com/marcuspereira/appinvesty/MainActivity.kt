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
        val resultadoInvestimento = findViewById<TextView>(R.id.tv_InvestimentoFinal)
        val rendimentos = findViewById<TextView>(R.id.tv_Rendimentos)
        val aporteMensal = findViewById<TextInputEditText>(R.id.ti_Mensal)
        val qtdMeses = findViewById<TextInputEditText>(R.id.ti_Meses)
        val juros = findViewById<TextInputEditText>(R.id.ti_Juros)

        val btnLimpar = findViewById<Button>(R.id.btn_Limpar)
        val btnCalcular = findViewById<Button>(R.id.btn_Calcular)

        btnCalcular.setOnClickListener {
            val jurosStr: Double = juros.text.toString().toDouble()
            val qtdMesesStr: Double = qtdMeses.text.toString().toDouble()
            val aporteMensalStr: Double = aporteMensal.text.toString().toDouble()

            val formatador = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

            val juros = jurosStr / 100
            val crescimentoAporte= aporteMensalStr * ((Math.pow(1 + juros, qtdMesesStr) - 1) / juros) * (1 + juros)
            val crescimentoInicial = aporteMensalStr * Math.pow(1 + juros, qtdMesesStr)
            val resultado = crescimentoInicial + crescimentoAporte
            val rendimento = resultado - (qtdMesesStr * aporteMensalStr)

            val rendimentoFormatado = formatador.format(rendimento)
            val resultadoFormatado = formatador.format(resultado)

            rendimentos.text = rendimentoFormatado
            resultadoInvestimento.text = resultadoFormatado
        }

        btnLimpar.setOnClickListener {
            resultadoInvestimento.text = "0.0"
            rendimentos.text = "0.0"
            aporteMensal.setText("")
            qtdMeses.setText("")
            juros.setText("")
        }

    }
}