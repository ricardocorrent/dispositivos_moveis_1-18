package com.rcorrent.buscadorgithub

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lerJson()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.action_buscar){
            buscarNoGit()
            //Toast.makeText(applicationContext, "Clicou", Toast.LENGTH_SHORT).show()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun buscarNoGit(){
        var path_url = pt_busca.text
        var urlCaminho = NetworkUtils.construirUrl(path_url.toString())

        GithubBuscaTask().execute(urlCaminho)
        tv_url.text = urlCaminho.toString()

    }

    fun exibirResultado(){
        tv_github_resultado.visibility = View.VISIBLE
        tv_mensagem_erro.visibility = View.INVISIBLE
        bp_aguarde.visibility = View.INVISIBLE
    }

    fun exibirMensagemErro(){
        tv_github_resultado.visibility = View.INVISIBLE
        tv_mensagem_erro.visibility = View.VISIBLE
        bp_aguarde.visibility = View.INVISIBLE
    }

    fun exibirProgressBar(){
        tv_github_resultado.visibility = View.INVISIBLE
        tv_mensagem_erro.visibility = View.INVISIBLE
        bp_aguarde.visibility = View.VISIBLE
    }

    fun lerJson(){
        val dadosJson = """
            {
              "temperatura":{
                "minima":11.34,
                "maxima": 19.31
              },
              "clima":{
                "id":801,
                "condicao":"Núvens",
                "descricao":"Poucas núvens"
              },
              "pressao":1023.51,
              "umidade":87
            }

            """
        val objetoPrevisao = JSONObject(dadosJson)
        val objetoClima = objetoPrevisao.getJSONObject("clima")
        val condicao = objetoClima.getString("condicao")

        Log.d("Resultado", "Condição $condicao")
    }

    inner class GithubBuscaTask : AsyncTask<URL, Void, String>(){

        override fun onPreExecute() {
            exibirProgressBar()
        }

        override fun doInBackground(vararg params: URL?): String? {
            try {
                val url = params[0]

                if(url != null){
                    return NetworkUtils.obterRespostaDaUrlHttp(url)
                }

            } catch (e : Exception){
                e.printStackTrace()
            }

            return null
        }

        override fun onPostExecute(result: String?) {
            if (result != null){
                tv_github_resultado.text = ""

                val objJson = JSONObject(result)

                val items = objJson.getJSONArray("items")

//                for (i in 0 until items.length()){
//                    val repo = items.getJSONObject(i)
//                    val repoName = repo.getString("name")
//                    tv_github_resultado.append(repoName + "\n\n")
//                }
                tv_github_resultado.text = result

                exibirResultado()
            } else {
                exibirMensagemErro()
            }
        }

    }

}
