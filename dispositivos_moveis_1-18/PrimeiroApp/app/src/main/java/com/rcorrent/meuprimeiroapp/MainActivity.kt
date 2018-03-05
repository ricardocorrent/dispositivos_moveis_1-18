package com.rcorrent.meuprimeiroapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        val EXTRA_MENSAGEM = "com.rcorrent.meuprimeiroapp.MESSAGE";
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /*function to send message*/
    fun enviarMensagem(view: View){
        //escrever msg de "alerta" na tela
        Toast.makeText(this, "Clicked!", Toast.LENGTH_SHORT).show()

        val intent = Intent(this, ExibirMensagemActivity::class.java)
        val message = editText.text.toString()
        intent.putExtra(EXTRA_MENSAGEM, message)
        startActivity(intent)
    }
}
