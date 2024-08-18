package com.example.navegacao1.model.dados

data class Usuario(
    var id: String? = null, // Este campo armazenará o ID do documento Firestore
    val nome: String = "",
    val senha: String = ""
)

