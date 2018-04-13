package com.rcorrent.minhasmetas

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.rcorrent.minhasmetas.Models.Meta
import java.text.SimpleDateFormat
import java.util.*

class MetaAdapter : RecyclerView.Adapter<MetaAdapter.MetaViewHolder>{

    var listaDeMeta = ArrayList<Meta>()
    var context: Context
    val itemClickListener: ItemClickListener
    val sdf = SimpleDateFormat("dd/MM/yyyy")

    constructor(context: Context, listaDeMeta: ArrayList<Meta>, itemClickListener: ItemClickListener) : super() {
        this.listaDeMeta = listaDeMeta
        this.context = context
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MetaViewHolder { //inflar o layout aqui
        val metaItem = LayoutInflater.from(context).inflate(R.layout.meta_item, parent, false)
        val metaViewHolder = MetaViewHolder(metaItem)
        return metaViewHolder
    }

    override fun getItemCount(): Int {
        return listaDeMeta.size
    }

    override fun onBindViewHolder(holder: MetaViewHolder, position: Int) {

        val meta = listaDeMeta.get(position)

        //variavel que recebe a data limite da meta
        val dataLimiteDaMeta = Calendar.getInstance()
        dataLimiteDaMeta.time = meta.dataLimite

        //variavel que recebe a data atual
        val dataAtual = Calendar.getInstance()
        dataAtual.time = Date()

        //calculo da variação dos dias e dos anos
        val variacaoDosDias = dataLimiteDaMeta.get(Calendar.DAY_OF_YEAR) - dataAtual.get(Calendar.DAY_OF_YEAR)
        val variacaoDosAnos = dataLimiteDaMeta.get(Calendar.YEAR) - dataAtual.get(Calendar.YEAR)

        holder.tvName!!.text = meta.nome
        holder.tvDescricao!!.text = meta.descricao
        holder.tvDataLimite!!.text = sdf.format(meta.dataLimite)

//        holder.tvDescricao.append("\nAno: ${variacaoDosAnos}\nDias: ${variacaoDosDias}")
        //se a meta está concluída
        if(meta.concluido!!){
            holder.status!!.text = "Meta concluída em ${sdf.format(meta.dataFinalizado)}"
            holder.container!!.setBackgroundResource(R.drawable.background_concluido)
        }
        //se a meta esta atrasada; anos ou dias menores que 0
        else if (variacaoDosAnos < 0){
            if(variacaoDosDias <= 0 ){
                holder.status!!.text = ("Data excedida em ${variacaoDosAnos*365*-1 - variacaoDosDias} dia(s)")
                holder.container!!.setBackgroundResource(R.drawable.background_vencido)
            }else{
                holder.status!!.text = ("Restam ${variacaoDosAnos*365*-1 - variacaoDosDias} dia(s)")
            }

            //holder.status!!.append("${variacaoDosDias} dia(s)")
        }else if(variacaoDosAnos == 0 && variacaoDosDias < 0){
            holder.status!!.text = ("Data excedida em ${variacaoDosDias*-1} dia(s)")
            holder.container!!.setBackgroundResource(R.drawable.background_vencido)
        }

        //se a meta acabar no mesmo dia
        else if(variacaoDosAnos == 0 && variacaoDosDias == 0){
            holder.status!!.text = "A meta termina hoje!!"
        }
         //se faltar dias para terminar a meta
        else{
            holder.status!!.text = "Restam "
            if(variacaoDosAnos > 0){
                holder.status!!.append("${variacaoDosAnos*365*-1 + variacaoDosDias} dia(s)")
            }
            if(variacaoDosDias > 0){
                holder.status!!.append("${variacaoDosDias} dias")
            }
        }


    }

    //instancia os itens do layout (meta_itens)que serão utilizados para preencher
    //os campos na main_activity
    inner class MetaViewHolder: RecyclerView.ViewHolder, View.OnClickListener{

        val tvName: TextView?
        val tvDescricao: TextView?
        val tvDataLimite: TextView?
        val status: TextView?
        val container: LinearLayout?
        val botao: ImageView?

        constructor(itemView: View?) : super(itemView) {
            this.tvName = itemView?.findViewById<TextView>(R.id.tvNamel)
            this.tvDescricao = itemView?.findViewById<TextView>(R.id.tvDescricaoL)
            this.tvDataLimite = itemView?.findViewById(R.id.tvDataLimite)
            this.status = itemView?.findViewById<TextView>(R.id.tvStatus)
            this.container = itemView?.findViewById(R.id.ll_container)
            this.botao = itemView?.findViewById<ImageView>(R.id.imgSeta)

            itemView?.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val itemClicado = adapterPosition
            itemClickListener.onItemClick(itemClicado)
        }

    }

    interface ItemClickListener{
        fun onItemClick(position: Int)
    }

}