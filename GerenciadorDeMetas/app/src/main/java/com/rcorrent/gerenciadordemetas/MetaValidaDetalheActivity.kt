package com.rcorrent.gerenciadordemetas

import android.annotation.TargetApi
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_meta_valida_detalhe.*
import java.time.LocalDate
import java.util.*

@TargetApi(Build.VERSION_CODES.N)
class MetaValidaDetalheActivity : AppCompatActivity() {

    val sdf = SimpleDateFormat("dd/MM/yyyy")

    @TargetApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meta_valida_detalhe)

        btnGravar.isEnabled = false
        btnEditar.isEnabled = false

        val bundle = intent.extras

        tvName.text = bundle.getString("nome")
        tvDescricao.text = bundle.getString("descricao")
        etDataLimite.text = bundle.getString("dataLimite")

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
        }

        cbConcluido.setOnClickListener {
            btnGravar.isEnabled = true
            if(cbConcluido.isChecked) {
                tvDataConclusao.visibility = View.VISIBLE
                etDataConclusao.visibility = View.VISIBLE
                val data = Date()
                etDataConclusao.text = sdf.format(data).toString()
            }else{
                tvDataConclusao.visibility = View.INVISIBLE
                etDataConclusao.visibility = View.INVISIBLE
            }
        }

        btnExcluir.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("nome", tvName.text.toString())
            intent.putExtra("descricao", tvDescricao.text.toString())
            intent.putExtra("dataLimite", etDataLimite.text.toString())
            intent.putExtra("dataFinalizado", etDataConclusao.text)
            intent.putExtra("concluido", cbConcluido.toString())
            intent.putExtra("operacao", "Excluir")
            this.startActivity(intent)
            finish()
        }

    }
}
