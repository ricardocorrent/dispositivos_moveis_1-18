package com.rcorrent.gerenciadordemetas

import android.annotation.TargetApi
import android.content.Context
import java.text.SimpleDateFormat
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v7.widget.RecyclerView
import java.text.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.rcorrent.gerenciadordemetas.Model.Meta

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MetaViewHolder {
        val metaListItem = LayoutInflater.from(context).inflate(R.layout.meta_ticket, parent, false)
        val metaViewHolder = MetaViewHolder(metaListItem)
        return metaViewHolder
    }

    override fun getItemCount(): Int {
        return listaDeMeta.size
    }

    override fun onBindViewHolder(holder: MetaViewHolder, position: Int) {
        val meta = listaDeMeta.get(position)

        val diasDataLimite = Calendar.getInstance()
        diasDataLimite.time = meta.dataLimite
        val dataAtual = Calendar.getInstance()
        dataAtual.time = Date()
        val variacaoDosDias = diasDataLimite.get(Calendar.DAY_OF_YEAR)-dataAtual.get(Calendar.DAY_OF_YEAR)
        val anos = diasDataLimite.get(Calendar.YEAR) - dataAtual.get(Calendar.YEAR)

        val dtLimite = sdf.format(meta.dataLimite)

        holder.tvName?.text = meta.nome
        holder.tvDescricao?.text = meta.descricao
//        val dataAtual = Date()

        if (meta.concluido == true && meta.dataFinalizado != null) {
            if(meta.dataFinalizado!! > meta.dataLimite!!){
                holder.tvDataLimite?.text = ("Data limite: ${dtLimite}")
                holder.status?.text = "Concluído em atrazo ${sdf.format(meta.dataFinalizado)}"
//                var dias = meta.dataFinalizado.time - aux
                holder.container?.setBackgroundResource(R.drawable.background_concluido)
                holder.botao?.setImageResource(R.drawable.right_arrow_red)
            }else {
                holder.tvDataLimite?.text = ("Data limite: ${dtLimite}")
                holder.status?.text = "Concluído em ${sdf.format(meta.dataFinalizado)}"
                holder.container?.setBackgroundResource(R.drawable.background_concluido)
                holder.botao?.setImageResource(R.drawable.right_arrow_green)
            }

        }else if(meta.concluido == false && variacaoDosDias > 0){
            holder.tvDataLimite?.text = ("Data limite: ${dtLimite}")
            holder.status?.text = "Aberto"
            holder.container?.setBackgroundResource(R.drawable.background)
            holder.botao?.setImageResource(R.drawable.right_arrow_green)

        }else if(meta.concluido == false && variacaoDosDias < 0){
            holder.tvDataLimite?.text = ("Data limite: ${dtLimite}")
            holder.status?.text = "Atrasado"
            holder.container?.setBackgroundResource(R.drawable.background_vencido)
            holder.botao?.setImageResource(R.drawable.right_arrow_red)
        }

    }


    //instancia os itens do layout que serão utilizados para
    inner class MetaViewHolder: RecyclerView.ViewHolder, View.OnClickListener{

        val tvName: TextView?
        val tvDescricao: TextView?
        val tvDataLimite: TextView?
        val status: TextView?
        val container: LinearLayout?
        val botao: ImageView?

        constructor(itemView: View?) : super(itemView) {
            this.tvName = itemView?.findViewById<TextView>(R.id.tvName)
            this.tvDescricao = itemView?.findViewById<TextView>(R.id.tvDescricao)
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