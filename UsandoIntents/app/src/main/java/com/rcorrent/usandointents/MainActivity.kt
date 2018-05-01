package com.rcorrent.usandointents

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ShareCompat
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_enviar.setOnClickListener {

            val intent = Intent(this, SegundaActivity::class.java)
            intent.putExtra("texto", et_mensagem.text.toString())
            startActivity(intent)

        }

        btn_abrir_site.setOnClickListener {
            //ação a ser feita
            val action = Intent.ACTION_VIEW
            //endereço
            val webpage = Uri.parse("http://www.globo.com")
            val intent = Intent(action, webpage)
            //verifica se a ação pode ser executada
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            }

        }

    }

    fun abrirMapa(view: View){
//        val endereco = "Av. Irmãos Pereira, 670 - Centro, Campo Mourão – PR"
        val endereco = et_endereco.text.toString()
        // construindo a URI
        val builder = Uri.Builder()
                .scheme("geo")
                .path("0,0")
                .appendQueryParameter("q", endereco)
        val uriEndereco = builder.build()
        val intent = Intent(Intent.ACTION_VIEW, uriEndereco)
        // verifica se a ação pode ser atendida
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    fun compartilhar(view: View){
        val tipoDeMidia = "text/plain"
        val titulo = "aprendendo a compartilhar"
        val texto = et_mensagem.text.toString()

        val intentCompartilhar = ShareCompat.IntentBuilder
                .from(this)
                .setType(tipoDeMidia)
                .setChooserTitle(titulo)
                .setText(texto)
                .intent

        if(intentCompartilhar.resolveActivity(packageManager) != null){
            startActivity(intentCompartilhar)
        }
    }


}
