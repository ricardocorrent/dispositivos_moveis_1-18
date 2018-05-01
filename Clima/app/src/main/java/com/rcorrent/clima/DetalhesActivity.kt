package com.rcorrent.clima

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ShareCompat
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_detalhes.*

class DetalhesActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes)

        if(intent.hasExtra("dados")){
            val dados = intent.getStringExtra("dados").split("-")
            tv_exibir_previsao.text = ""
            tv_exibir_previsao.append("Data: ${dados[0]}\n")
            tv_exibir_previsao.append("Condição: ${dados[1]}\n")
            tv_exibir_previsao.append("Temperatura: ${dados[2]}\n")
        }


        //tv_exibir_previsao.text = intent.getStringExtra("dados")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detalhes, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.acao_compartilhar){
            compartilhar()
        }
        return super.onOptionsItemSelected(item)
    }

    fun compartilhar(){
        val tipoDeMidia = "text/plain"
        val titulo = "Compartilhar TEMPO"
        val texto = tv_exibir_previsao.text.toString()

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
