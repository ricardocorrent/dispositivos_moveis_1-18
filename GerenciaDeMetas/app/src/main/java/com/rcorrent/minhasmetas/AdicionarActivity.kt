package com.rcorrent.minhasmetas

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.rcorrent.minhasmetas.Models.Meta
import com.rcorrent.minhasmetas.Utils.ArquivoTextoUtils
import com.rcorrent.minhasmetas.Utils.JsonUtils
import kotlinx.android.synthetic.main.activity_adicionar.*
import java.text.SimpleDateFormat

class AdicionarActivity : AppCompatActivity() {

    val sdf = SimpleDateFormat("dd/MM/yyyy")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adicionar)

        btnAdicionar.setOnClickListener {
            Toast.makeText(this,"Adicionar", Toast.LENGTH_SHORT).show()
            if(salvarNovaMeta()){
                finish()
            }
        }

    }

    fun salvarNovaMeta(): Boolean{
        if(validarMeta()){
            val dataLimite = sdf.parse(etDataLimite.text.toString())
            val novaMeta = Meta(
                    etNome.text.toString(),
                    etDescricao.text.toString(),
                    dataLimite,
                    null,
                    false
                    )

            var arrayJson = ArquivoTextoUtils().carregarArquivo(this)
            var arrayDeMeta = ArrayList<Meta>()

            if(arrayJson != null){
                arrayDeMeta = JsonUtils().jsonArrayParaArrayMeta(arrayJson)!!
            }
            arrayDeMeta.add(novaMeta)
            arrayJson = JsonUtils().arrayMetaParaJsonArray(arrayDeMeta)
            ArquivoTextoUtils().gravarArquivo(this, arrayJson)
            return true
        }else {
            return false
        }
    }

    fun validarMeta(): Boolean{
        try {
            if(etNome.text.toString().equals("")){
                Toast.makeText(this,"Informe nome", Toast.LENGTH_LONG).show()
                return false
            }
            return true

        }catch (e: Exception){
            e.printStackTrace()
            Toast.makeText(this,"Data inválida", Toast.LENGTH_SHORT).show()
            return false
        }

    }
}
