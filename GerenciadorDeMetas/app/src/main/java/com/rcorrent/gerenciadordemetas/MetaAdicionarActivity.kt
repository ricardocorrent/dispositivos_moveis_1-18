package com.rcorrent.gerenciadordemetas

import android.annotation.TargetApi
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_meta_adicionar.*

class MetaAdicionarActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.N)
    val sdf = SimpleDateFormat("dd/MM/yyyy")

    @TargetApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meta_adicionar)

        btnAdicionar.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("nome", etNome.text.toString())
            intent.putExtra("descricao", etDescricao.text.toString())
            intent.putExtra("dataLimite", etDataLimite.text.toString())
            intent.putExtra("operacao", "Adicionar")
            this.startActivity(intent)
            finish()
        }
    }
}
