package com.rcorrent.climaprova

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.rcorrent.climaprova.Utils.NetworkUtils
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.clima, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.acao_atualizar){
            //buscarNoGit()
            lerJson()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun lerJson(){
        val dadosJson = """
                        {"clima":[
                          {
                            "id":"01",
                            "local":
                            {"cidade":"cascavel",
                              "estado":"paraná",
                              "pais":"brasil"
                            },

                            "dados":
                            {"temperatura": 30,
                              "temp_maxima": 35,
                              "temp_minima": 25
                            }
                          },
                          {
                            "id":"02",
                            "local":
                            {"cidade":"campo mourão",
                              "estado":"paraná",
                              "pais":"brasil"
                            },

                            "dados":
                            {"temperatura": 28,
                              "temp_maxima": 37,
                              "temp_minima": 27
                            }
                          },
                          {
                            "id":"03",
                            "local":
                            {"cidade":"campo grande",
                              "estado":"mato grosso do sul",
                              "pais":"brasil"
                            },

                            "dados":
                            {"temperatura": 35,
                              "temp_maxima": 42,
                              "temp_minima": 31
                            }
                          }

                          ]
                        }


            """

        val objetoClima = JSONObject(dadosJson)
        val clima = objetoClima.getJSONArray("clima")

        for(i in 0 until (clima.length())){
            val item = clima.getJSONObject(i)
            val loc = item.getJSONObject("local")
            dados_clima.append("id: ${item.get("id")}\nCidade: ${loc.getString("cidade")}\n\n")

        }
        //dados_clima.text = objetoClima.toString()
        exibirResultado()

    }

    fun buscarNoGit(){
        var path_url: String = "https://gist.githubusercontent.com/ricardocorrent/5567697652eb0df360aa6a535606db4f/raw/ce9caeaa4f7364e5f3c8dbd42705e72b18025e3b/json_example"
        //var urlCaminho = NetworkUtils.construirUrl(path_url)

        BuscarClimaTask().execute(path_url)
    }
    var texto = String()

    inner class BuscarClimaTask(): AsyncTask<String, Void, String>(){
        override fun onPreExecute() {
            exibirProgressBar()
        }

        override fun doInBackground(vararg params: String): String? {
            try {
                val url = URL("https://gist.githubusercontent.com/ricardocorrent/5567697652eb0df360aa6a535606db4f/raw/ce9caeaa4f7364e5f3c8dbd42705e72b18025e3b/json_example")
                println(url)
                var im = BufferedReader(InputStreamReader(url.openStream()))
                texto = im.toString()

//                val url = params[0]
//                println("URL ---- $url")
//
//                if(url != null){
//                    return NetworkUtils.obterRespostaDaUrlHttp(url)
//                }
                println(texto)
            }catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }

        override fun onPostExecute(result: String?) {
            if (result != null){
                val objJson = JSONObject(result)

                val clima = objJson.getJSONArray("clima")
                dados_clima.text = clima.toString()
                exibirResultado()
            } else {
                exibirMensagemErro()
            }
        }
    }

    fun exibirProgressBar(){
        dados_clima.visibility = View.INVISIBLE
        mensagemErro.visibility = View.INVISIBLE
        pb_aguardar.visibility = View.VISIBLE
    }
    fun exibirMensagemErro(){
        dados_clima.visibility = View.INVISIBLE
        mensagemErro.visibility = View.VISIBLE
        pb_aguardar.visibility = View.INVISIBLE
    }
    fun exibirResultado(){
        dados_clima.visibility = View.VISIBLE
        mensagemErro.visibility = View.INVISIBLE
        pb_aguardar.visibility = View.INVISIBLE
    }
}
