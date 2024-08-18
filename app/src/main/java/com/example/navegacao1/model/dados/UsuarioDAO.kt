package com.example.navegacao1.model.dados

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

// Classe que gerencia a interação com o Firestore
class UsuarioDAO {

    private val db = FirebaseFirestore.getInstance()

    fun buscar(callback: (List<Usuario>) -> Unit) {
        db.collection("usuarios").get()
            .addOnSuccessListener { document ->
                val usuarios = document.map { doc ->
                    val usuario = doc.toObject<Usuario>()
                    usuario.id = doc.id // Atribuindo o ID do documento ao campo `id`
                    usuario
                }
                callback(usuarios)
            }
            .addOnFailureListener {
                callback(emptyList())
            }
    }

    fun buscarPorNome(nome: String, callback: (Usuario?) -> Unit) {
        db.collection("usuarios").whereEqualTo("nome", nome).get()
            .addOnSuccessListener { document ->
                if (!document.isEmpty) {
                    val doc = document.documents[0]
                    val usuario = doc.toObject<Usuario>()
                    usuario?.id = doc.id // Atribuindo o ID do documento ao campo `id`
                    callback(usuario)
                } else {
                    callback(null)
                }
            }
            .addOnFailureListener {
                callback(null)
            }
    }

    fun buscarPorId(id: String, callback: (Usuario?) -> Unit) {
        db.collection("usuarios").document(id).get()
            .addOnSuccessListener { document ->
                val usuario = document.toObject<Usuario>()
                usuario?.id = document.id // Atribuindo o ID do documento ao campo `id`
                callback(usuario)
            }
            .addOnFailureListener {
                callback(null)
            }
    }


    // Método para adicionar um novo usuário
    fun adicionar(usuario: Usuario, callback: (Usuario) -> Unit) {
        val document = db.collection("usuarios").document()
        usuario.id = document.id
        document.set(usuario)
            .addOnSuccessListener {
                callback(usuario)
            }
            .addOnFailureListener {
                // Tratamento de erro, se necessário
            }
    }

}
