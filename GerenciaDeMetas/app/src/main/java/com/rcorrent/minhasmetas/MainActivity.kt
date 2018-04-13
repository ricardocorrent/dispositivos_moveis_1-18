package com.rcorrent.minhasmetas

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.rcorrent.minhasmetas.Models.Meta
import com.rcorrent.minhasmetas.Utils.ArquivoTextoUtils
import com.rcorrent.minhasmetas.Utils.JsonUtils
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.ArrayList

class MainActivity : AppCompatActivity(), MetaAdapter.ItemClickListener {

    var listaDeMeta: ArrayList<Meta>? = null
    var toast: Toast? = null

    val sdf = SimpleDateFormat("dd/MM/yyyy")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listarView()

        btnAdd.setOnClickListener {
            var intent = Intent(this, AdicionarActivity::class.java)
            startActivity(intent)
        }


    }

    override fun onResume() {
        listarView()
        super.onResume()
    }

    fun listarView(){

        val arrayJson = ArquivoTextoUtils().carregarArquivo(this)

        if(arrayJson == null || arrayJson.length() == 0){
            ArquivoTextoUtils().gravarArquivo(this, null)
        }else{
            this.listaDeMeta = JsonUtils().jsonArrayParaArrayMeta(arrayJson!!)
            val lm = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            val adapter = MetaAdapter(this, listaDeMeta!!, this)
            gvListaMeta.layoutManager = lm
            gvListaMeta.adapter = adapter
        }
        //setContentView(R.layout.activity_main)
    }

    override fun onItemClick(position: Int) {
        val itemClicado = listaDeMeta!!.get(position)
        toast = Toast.makeText(this, "Numero clicado ${position}", Toast.LENGTH_SHORT)
        toast?.show()

        val intent = Intent(this, DetalhesActivity::class.java)
        intent.putExtra("nome", itemClicado.nome)
        intent.putExtra("descricao", itemClicado.descricao)
        intent.putExtra("dataLimite", sdf.format(itemClicado.dataLimite))
        if(itemClicado.dataFinalizado != null) {
            intent.putExtra("dataFinalizado", sdf.format(itemClicado.dataFinalizado))
        }
        intent.putExtra("concluido", itemClicado.concluido)
        intent.putExtra("itemPosition", position)

        this.startActivity(intent)
    }
}
