package com.example.atividade1mobile

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

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
    }

    //função que envia o nome do jogador e inicia o jogo
    fun jogar(view: View){
        val nome = findViewById<EditText>(R.id.nomeEditText).text.toString();

        if(nome.isEmpty()){
            Toast.makeText(this, "Não pode enviar o nome em branco", Toast.LENGTH_SHORT).show()
        }
        else {
            val intent = Intent(this, Jogar::class.java);

            val jogador = Jogador(
                nome = nome,
                pontuacao = 0
            )

            intent.putExtra("jogador", jogador);

            startActivity(intent);
        }
    }
}