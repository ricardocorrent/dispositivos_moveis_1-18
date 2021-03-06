package com.rcorrent.clima

import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import com.rcorrent.*
import org.json.JSONObject

class MainActivity : AppCompatActivity(), PrevisaoAdapter.ItemClickListener {

    var previsaoAdapter : PrevisaoAdapter? = null

    var toast: Toast? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Toast.makeText(this, "Criado", Toast.LENGTH_SHORT).show()
        carregarDadosDoClima()

        previsaoAdapter = PrevisaoAdapter(this, null, this)
        val lm = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        rv_clima.adapter = previsaoAdapter
        rv_clima.layoutManager = lm
    }

    override fun onItemClick(position: Int) {
        val previsao = previsaoAdapter!!.getDadosClima()!!.get(position)
        toast?.cancel()
        toast = Toast.makeText(this, "Previsão: $previsao", Toast.LENGTH_SHORT)
        toast?.show()

        val intent = Intent(this, DetalhesActivity::class.java)

        intent.putExtra("dados", previsao)

        startActivity(intent)

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
            carregarDadosDoClima()
        }
        if(item?.itemId == R.id.acao_mapa){
            mostrarMapa()
        }
        return super.onOptionsItemSelected(item)
    }

    fun exibirResultado(){
        rv_clima.visibility = View.VISIBLE
        tv_mensagem_erro.visibility = View.INVISIBLE
        pb_aguarde.visibility = View.INVISIBLE
    }

    fun exibirMensagemErro(){
        rv_clima.visibility = View.INVISIBLE
        tv_mensagem_erro.visibility = View.VISIBLE
        pb_aguarde.visibility = View.INVISIBLE
    }

    fun exibirProgressBar(){
        rv_clima.visibility = View.INVISIBLE
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
                previsaoAdapter?.setDadosClima(JsonUtils.getSimplesStringsDeClimaDoJson(this@MainActivity, resultado))
                exibirResultado()
            }else{
                exibirMensagemErro()
            }
        }
    }

    fun mostrarMapa(){
        val endereco = "Campo Mourão, Paraná, Brasil"

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
}
