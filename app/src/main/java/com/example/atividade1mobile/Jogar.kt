package com.example.atividade1mobile

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.w3c.dom.Text
import kotlin.toString

class Jogar : AppCompatActivity() {
    private lateinit var dadosJogador: Jogador
    private lateinit var confirmarProximoBotao: Button;
    private lateinit var contador: TextView; //textview que mostra qual é o número da pergunta
    private lateinit var respostaEscolhida: TextView;
    private lateinit var expressaoTextView: TextView;
    private lateinit var tela: View;

    private var perguntaAtual = 0
    private val totalPerguntas = 5 // o número total de perguntas
    private var respostaConfirmada = false; //Se a pergunta foi respondida ou não
    private var acertos = 0;
    private var numero1 = 0;
    private var numero2 = 0;
    private var sinal = "+";
    var resultado: Int = 0;

    private val operacao = listOf(
        "+",
        "-"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_jogar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tela = findViewById<View>(R.id.main);
        expressaoTextView = findViewById<TextView>(R.id.expressaoTextView);
        contador = findViewById<TextView>(R.id.numQuestTextView);
        confirmarProximoBotao = findViewById<Button>(R.id.nextButton);
        respostaEscolhida = findViewById<EditText>(R.id.respostaEditText) //resposta do jogador
        val textCorreta = findViewById<TextView>(R.id.respostaCorretaTextView); //Local que aparece a resposta correta
        val textResposta = findViewById<TextView>(R.id.resultadoRespostaTextView); //Local no qual aparece se a resposta estava certa ou errada

        carregarPergunta();

        confirmarProximoBotao.setOnClickListener {
            //Se ainda não foi enviada nenhuma resposta
            if(!respostaConfirmada) {
                val res = respostaEscolhida.text.toString(); //pega a resposta do campo

                if (res.isEmpty()) Toast.makeText(this, "Não pode enviar o campo em branco", Toast.LENGTH_SHORT).show() //Se estiver vazia retorna um Toast com erro
                else {
                    val respostaCorreta = verificarResposta(res) //envia para a função que verifica se a resposta é verdadeira

                    if (respostaCorreta) {
                        tela.setBackgroundColor(ContextCompat.getColor(this, R.color.verde))
                        textResposta.text = "Resposta Correta!!!"
                        acertos++;
                    }
                    else {
                        tela.setBackgroundColor(ContextCompat.getColor(this, R.color.vermelho))
                        textResposta.text = "Resposta Incorreta!!!"
                    }
                    textCorreta.text = "A Resposta correta é: " + resultado.toString();
                    confirmarProximoBotao.text = "Próximo";
                    respostaConfirmada = true;
                }
            }
            else{
                textResposta.text = ""
                textCorreta.text = ""
                respostaEscolhida.text = ""
                tela.setBackgroundColor(ContextCompat.getColor(this, com.google.android.material.R.color.design_default_color_background))
                carregarPergunta();
            }
            // Fecha o teclado após a ação do botão de próximo
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if (currentFocus != null) {
                imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
            }
        }

        //Recebe os dados enviados da tela anterior
        val bundle = intent.extras
        if(bundle != null) {

            val jogador = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle.getParcelable("jogador", Jogador::class.java)
            } else {
                bundle.getParcelable("jogador");
            }
            //Atribui esses dados a uma variável para ser utilizada e depois mandada para tela de placar
            if(jogador != null) dadosJogador = jogador
        }

    }

    //função que carrega a pergunta
    private fun carregarPergunta() {
        var continuar: Boolean = true;
        perguntaAtual++; //muda o número da pergunta
        if (perguntaAtual <= totalPerguntas) {
            //Faz as alterações nos textos e coloca a pergunta como não respondida
            confirmarProximoBotao.text = "Responder";
            respostaConfirmada = false;
            contador.text = "Pergunta $perguntaAtual de $totalPerguntas";

            while(continuar) {
                numero1 = (0..99).random();
                numero2 = (0..99).random();
                sinal = operacao.random();

                if(sinal.equals("-")) resultado = numero1 - numero2;
                else resultado = numero1 + numero2;

                if(resultado >= 0){
                    var string = numero1.toString() + " " + sinal +  " " + numero2.toString();
                    expressaoTextView.text = string;
                    continuar = false;
                }

            }
        }
        else{
            val intent = Intent(this, Pontuacao::class.java);

            val jogador = Jogador(
                nome = dadosJogador.nome.toString(),
                pontuacao = acertos
            )

            intent.putExtra("jogador", jogador);

            startActivity(intent);
        }
    }

    //Função que verifica se a resposta é verdadeira
    private fun verificarResposta(res: String): Boolean{
        val resposta = res.toString().toInt();
        if(resposta == resultado) return true;
        else return false;
    }
}