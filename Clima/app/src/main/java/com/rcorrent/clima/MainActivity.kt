package com.rcorrent.clima

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import com.rcorrent.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Toast.makeText(this, "Criado", Toast.LENGTH_SHORT).show()
        //carregarDadosDoClima()
    }

    fun carregarDadosDoClima(){
        val localizacao = ClimaPreferencias.getLocalizacaoSalva(this)
        BuscarClimaTask().execute(localizacao)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.clima, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.acao_atualizar){
            dados_clima.text = ""
            carregarDadosDoClima()
        }
        return super.onOptionsItemSelected(item)
    }

    fun exibirResultado(){
        dados_clima.visibility = View.VISIBLE
        tv_mensagem_erro.visibility = View.INVISIBLE
        pb_aguarde.visibility = View.INVISIBLE
    }

    fun exibirMensagemErro(){
        dados_clima.visibility = View.INVISIBLE
        tv_mensagem_erro.visibility = View.VISIBLE
        pb_aguarde.visibility = View.INVISIBLE
    }

    fun exibirProgressBar(){
        dados_clima.visibility = View.INVISIBLE
        tv_mensagem_erro.visibility = View.INVISIBLE
        pb_aguarde.visibility = View.VISIBLE
    }

    inner class BuscarClimaTask(): AsyncTask<String, Void, String>(){

        override fun onPreExecute() {
            exibirProgressBar()
        }

        override fun doInBackground(vararg params: String): String? {
            try {
                val localizacao = params[0];
                val url = NetworkUtils.construirUrl(localizacao)

                if (url != null){
                    val resultado = NetworkUtils.obterRespostaDaUrlHttp(url)
                    return resultado
                }
            } catch (e: Exception){
                e.printStackTrace()
            }
            return null
        }

        override fun onPostExecute(resultado: String?) {

            if(resultado != null){

                val json = JsonUtils.getSimplesStringsDeClimaDoJson(this@MainActivity, resultado);

                for (i in 0 until json!!.size){

                    val texto = json.get(i).split("-")
                    dados_clima.append("Data: ${texto[0]}\n")
                    dados_clima.append("Condição: ${texto[1]}\n")
                    dados_clima.append("Temperatura: ${texto[2]}\n")
                    dados_clima.append("Umidade: ${texto[3]}\n")
                    dados_clima.append("\n")
                }
                exibirResultado()
            }else{
                exibirMensagemErro()
            }

        }

    }






}
