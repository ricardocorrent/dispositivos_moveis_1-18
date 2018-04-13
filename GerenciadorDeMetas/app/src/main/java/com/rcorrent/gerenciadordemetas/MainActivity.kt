package com.rcorrent.gerenciadordemetas

import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.support.annotation.RequiresApi
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.format.DateFormat
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import android.widget.Toast
import com.rcorrent.gerenciadordemetas.Model.Meta
import com.rcorrent.gerenciadordemetas.Utils.DataUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_meta_valida_detalhe.view.*

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@RequiresApi(Build.VERSION_CODES.N)
class MainActivity : AppCompatActivity(), MetaAdapter.ItemClickListener {

    var listaDeMeta = ArrayList<Meta>()
//    var listaDeMeta = arrayListOf<Meta>()

    var toast: Toast? = null

    val sdf = SimpleDateFormat("dd/MM/yyyy")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listarView()


        btnAdd.setOnClickListener {
            val intent = Intent(this, MetaAdicionarActivity::class.java)
            this.startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()

        if(intent.extras != null){
            val bundle = intent.extras
            if(bundle.getString("operacao").equals("Adicionar")){
                this.listaDeMeta.add(Meta(bundle.getString("nome"),bundle.getString("descricao"), (sdf.parse(bundle.getString("dataLimite"))), null, false))
                intent = null
            }

        }

        listarView()
    }

    fun listarView(){
        val lm = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val adapter = MetaAdapter(this, listaDeMeta, this)

        gvListaMeta.layoutManager = lm
        gvListaMeta.adapter = adapter
    }

    override fun onItemClick(position: Int) {
        val itemClicado = listaDeMeta.get(position)
        toast = Toast.makeText(this, "Numero clicado ${itemClicado.nome}", Toast.LENGTH_SHORT)
        toast?.show()

        val intent = Intent(this, MetaValidaDetalheActivity::class.java)
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
