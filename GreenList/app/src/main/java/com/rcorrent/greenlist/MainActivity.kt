package com.rcorrent.greenlist

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ListaVerdeAdapter.ItemClickListener {

    val numeros = listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17);

    var toast: Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val lm = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val adapter = ListaVerdeAdapter(this, numeros, this)

        rv_lista_numerica.layoutManager = lm
        rv_lista_numerica.adapter = adapter

    }

    override fun onItemClick(position: Int) {
        toast?.cancel()

        val numeroClicado = numeros.get(position)
        toast = Toast.makeText(this, "Numero clicado $numeroClicado", Toast.LENGTH_SHORT)
        toast?.show()
    }

}
