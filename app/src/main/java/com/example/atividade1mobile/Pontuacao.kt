package com.example.atividade1mobile

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Pontuacao : AppCompatActivity() {
    private lateinit var reiniciarButton: Button; //Botão que inicia tudo do início

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pontuacao)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var tela = findViewById<View>(R.id.main);
        tela.setBackgroundColor(ContextCompat.getColor(this, com.google.android.material.R.color.design_default_color_background))

        //pega os dados e exibe na tela
        val bundle = intent.extras
        if(bundle != null) {
            val jogador = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle.getParcelable("jogador", Jogador::class.java)
            } else {
                bundle.getParcelable("jogador");
            }

            if(jogador != null){
                val player = findViewById<TextView>(R.id.jogadorTextView);
                player.text = "Jogador: ${jogador.nome}"
                val pontuacao = findViewById<TextView>(R.id.acertosTextView);
                pontuacao.text = "Pontuação Final: ${jogador.pontuacao*20}"
            }
        }
        reiniciarButton = findViewById<Button>(R.id.reiniciarButton);

        //listener para reiniciar o jogo do início
        reiniciarButton.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java);
            startActivity(intent);
        }
    }
}