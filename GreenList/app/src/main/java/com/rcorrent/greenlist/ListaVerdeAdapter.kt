package com.rcorrent.greenlist

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class ListaVerdeAdapter : RecyclerView.Adapter<ListaVerdeAdapter.NumeroViewHolder> {

    val context: Context
    val lista: List<Int>
    val itemClickListener: ItemClickListener

    constructor(context: Context, list: List<Int>, itemClickListener: ItemClickListener) {
        this.context = context
        this.lista = list
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumeroViewHolder {
        val numeroListItem = LayoutInflater.from(context).inflate(R.layout.numero_list_item, parent, false)
        val numeroViewHolder = NumeroViewHolder(numeroListItem)
        return numeroViewHolder
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: NumeroViewHolder, position: Int) {
        val numero = lista.get(position)

        holder.tv_item_numero?.text = numero.toString()
        val cor = ColorUtils.getViewHolderBackgroundColor(context, position)
        holder.tv_item_numero?.setBackgroundColor(cor)
    }

    inner class NumeroViewHolder : RecyclerView.ViewHolder, View.OnClickListener {

        val tv_item_numero: TextView?

        constructor(itemView: View?) : super(itemView) {
            tv_item_numero = itemView?.findViewById<TextView>(R.id.tv_item_numero)

            itemView?.setOnClickListener(this)
        }

        override fun onClick(v: View?){
            val posicaoClicada = adapterPosition
            itemClickListener.onItemClick(posicaoClicada)
        }

    }

    interface ItemClickListener{
        fun onItemClick(position: Int)
    }
}