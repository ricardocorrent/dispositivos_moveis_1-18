package com.rcorrent.prova1bimestre

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.mn_calcular){
            calcular()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun calcular(): Boolean{

        if(validar()){
            val b = (etBase.text.toString()).toDouble()
            val a = (etAltura.text.toString()).toDouble()
            val p = (etProfundidade.text.toString()).toDouble()
            val area: Double = b * a
            var volume : Double = a * b * p

            tvArea.text = "Área: ${area}"
            tvVolume.text = "Volume: ${volume}"
            return true
        }
        return false

    }

    fun validar(): Boolean{
        if(etBase.text.toString() == ""){
            Toast.makeText(this, "Informe um valor para Base", Toast.LENGTH_SHORT).show()
            return false
        }
        if(etAltura.text.toString() == ""){
            Toast.makeText(this, "Informe um valor para Altura", Toast.LENGTH_SHORT).show()
            return false
        }
        if(etProfundidade.text.toString() == ""){
            Toast.makeText(this, "Informe um valor para Base", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}
