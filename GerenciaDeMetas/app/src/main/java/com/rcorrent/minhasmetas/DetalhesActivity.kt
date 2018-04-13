package com.rcorrent.minhasmetas

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.rcorrent.minhasmetas.Models.Meta
import com.rcorrent.minhasmetas.Utils.ArquivoTextoUtils
import com.rcorrent.minhasmetas.Utils.JsonUtils
import kotlinx.android.synthetic.main.activity_detalhes.*
import org.json.JSONArray
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class DetalhesActivity : AppCompatActivity() {

//    var json = ArquivoTextoUtils().carregarArquivo(this)
//    var listaDeMeta = JsonUtils().jsonArrayParaArrayMeta(json!!)

    var json : JSONArray? = null
    var listaDeMeta : ArrayList<Meta>? = null

    var meta = Meta()
    val sdf = SimpleDateFormat("dd/MM/yyyy")
    var index : Int? = null
    var operacao: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes)

        btnGravar.isEnabled = false

        val bundle = intent.extras

        tvName.text.clear()
        tvName.append(bundle.getString("nome"));

        tvDescricao.text.clear()
        tvDescricao.append(bundle.getString("descricao"));

        etDataLimite.text.clear()
        etDataLimite.append(bundle.getString("dataLimite"))

        this.index = bundle.getInt("itemPosition")
        this.json = ArquivoTextoUtils().carregarArquivo(this)
        this.listaDeMeta = JsonUtils().jsonArrayParaArrayMeta(json!!)

        if(bundle.getString("dataFinalizado") != null){
            tvDataConclusao.visibility = View.VISIBLE
            etDataConclusao.visibility = View.VISIBLE
            etDataConclusao.text = bundle.getString("dataFinalizado")
            btnEditar.isEnabled = false

        }else{
            //só edito as metas abertas
            btnEditar.isEnabled = true
        }

        if (bundle.getBoolean("concluido")){
            cbConcluido.isChecked = true
            cbConcluido.isEnabled = false
            btnExcluir.isEnabled = true
        }else{
            btnExcluir.isEnabled = false
        }


        cbConcluido.setOnClickListener{clicarConcluido()}

        btnGravar.setOnClickListener{clicarGravar()}

        btnExcluir.setOnClickListener{clicarExcluir()}

        btnEditar.setOnClickListener{clicarEditar()}

    }

    fun setarMeta(): Boolean{
        try {

            this.meta.nome = tvNamel.text.toString()
            this.meta.descricao = tvDescricaoL.text.toString()
            this.meta.dataLimite = sdf.parse(etDataLimite.text.toString())
            this.meta.concluido = cbConcluido.isChecked
            this.meta.dataFinalizado = sdf.parse(etDataConclusao.text.toString())
            return true
        }catch (e: Exception) {
            return false
        }
    }

    fun clicarEditar(){
        this.operacao = 1
        if (btnEditar.isEnabled) {
            btnExcluir.isEnabled = false
            btnGravar.isEnabled = true
            cbConcluido.isEnabled = false
            tvName.isEnabled = true
            tvDescricao.isEnabled = true
            etDataLimite.isEnabled = true
        }

    }

    fun clicarExcluir(){
        if (setarMeta()){
            this.listaDeMeta!!.removeAt(this.index!!)
            ArquivoTextoUtils().gravarArquivo(this, JsonUtils().arrayMetaParaJsonArray(this.listaDeMeta!!))
            finish()
        }
    }

    fun clicarGravar(){
        if(operacao == 2) {
            if (setarMeta()) {
                this.listaDeMeta!![this.index!!] = this.meta
                ArquivoTextoUtils().gravarArquivo(this, JsonUtils().arrayMetaParaJsonArray(this.listaDeMeta!!))
                finish()
            }
        } else{
            if(gravarEdicao())finish()
        }
    }

    fun clicarConcluido(){

        if(cbConcluido.isChecked) {
            btnExcluir.isEnabled = false
            btnGravar.isEnabled = true
            btnEditar.isEnabled = false
            tvDataConclusao.visibility = View.VISIBLE
            etDataConclusao.visibility = View.VISIBLE
            val data = Calendar.getInstance()
            etDataConclusao.text = sdf.format(data.time).toString()
        }else{
            btnExcluir.isEnabled = false
            btnGravar.isEnabled = false
            btnEditar.isEnabled = true
            tvDataConclusao.visibility = View.INVISIBLE
            etDataConclusao.visibility = View.INVISIBLE
        }
        this.operacao = 2
    }

    fun gravarEdicao(): Boolean{
        try {
            val dataLimite = sdf.parse(etDataLimite.text.toString())
            var novaMeta = Meta(
                    tvName.text.toString(),
                    tvDescricao.text.toString(),
                    dataLimite,
                    null,
                    false
            )
            this.listaDeMeta!![this.index!!] = novaMeta
            var arrayJson = ArquivoTextoUtils().carregarArquivo(this)
            arrayJson = JsonUtils().arrayMetaParaJsonArray(this.listaDeMeta!!)
            ArquivoTextoUtils().gravarArquivo(this, arrayJson)
            return true
        }catch (e: Exception){
            e.printStackTrace()
            return false
        }

    }
}
