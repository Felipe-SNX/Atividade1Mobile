package com.example.atividade1mobile

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Jogador(
    val nome: String,
    val pontuacao: Int
): Parcelable
