package com.rcorrent.clima

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class PrevisaoAdapter: RecyclerView.Adapter<PrevisaoAdapter.PrevisaoViewHolder>{

    val context: Context
    private var dadosClima : Array<String?>?
    var itemClickListener: ItemClickListener

//    constructor(context: Context, dadosClima: Array<String?>?) : super() {
//        this.dadosClima = dadosClima
//        this.context = context
//    }

    constructor(context: Context, dadosClima: Array<String?>?, itemClickListener: ItemClickListener) : super() {
        this.context = context
        this.dadosClima = dadosClima
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrevisaoViewHolder {
        val dadosClimaItem = LayoutInflater.from(context).inflate(R.layout.previsao_lista_item, parent, false)
        val dadosViewHolder = PrevisaoViewHolder(dadosClimaItem)
        return dadosViewHolder
    }

    override fun getItemCount(): Int {
        val dados = dadosClima

        if (dados != null) {
            return dados.size
        }

        return 0

    }

    override fun onBindViewHolder(holder: PrevisaoViewHolder, position: Int) {
        val dados = dadosClima?.get(position)!!.split("-")
        holder.tvDadosPrevisao?.text = ""
        holder.tvDadosPrevisao?.append("Data: ${dados[0]}\n")
        holder.tvDadosPrevisao?.append("Condição: ${dados[1]}\n")
        holder.tvDadosPrevisao?.append("Temperatura: ${dados[2]}\n")
    }

    fun setDadosClima(dados: Array<String?>?){
        this.dadosClima = dados
        notifyDataSetChanged()
    }
    fun getDadosClima() : Array<String?>? {
        return dadosClima
    }



    interface ItemClickListener{
        fun onItemClick(position: Int)
    }

    inner class PrevisaoViewHolder: RecyclerView.ViewHolder, View.OnClickListener{

        val tvDadosPrevisao: TextView

        constructor(itemView: View) : super(itemView) {
            tvDadosPrevisao = itemView.findViewById<TextView>(R.id.tv_dados_previsao)

            itemView.setOnClickListener {
                itemClickListener.onItemClick(adapterPosition)
            }
        }

        override fun onClick(v: View?){
            val posicaoClicada = adapterPosition
            itemClickListener!!.onItemClick(posicaoClicada)
        }


    }
}