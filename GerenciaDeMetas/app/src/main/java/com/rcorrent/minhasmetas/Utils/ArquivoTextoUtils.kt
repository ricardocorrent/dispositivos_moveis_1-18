package com.rcorrent.minhasmetas.Utils

import android.content.Context
import android.widget.Toast
import org.json.JSONArray
import java.io.FileInputStream
import java.io.FileOutputStream

class ArquivoTextoUtils{
    companion object {
        val NOME_DO_ARQUIVO = "lista_de_metas"
    }

    fun carregarArquivo(context: Context): JSONArray?{
        try {
            val arquivo : FileInputStream = context.openFileInput(NOME_DO_ARQUIVO)
            var str = StringBuilder()
            str.setLength(0)

            var linha = arquivo.bufferedReader().readLine()

            while (linha != null){
                str.append(linha).append("\n")
                linha = arquivo.bufferedReader().readLine()
            }

            return if(str.length > 0) JSONArray(str.toString()) else null
        }catch (e: Exception){
            e.printStackTrace()
            Toast.makeText(context, "Arquivo inexistente", Toast.LENGTH_LONG).show()
            return null
        }
    }

    fun gravarArquivo(context: Context, jsonArray: JSONArray?){
        try {
            val arquivo : FileOutputStream = context.openFileOutput(NOME_DO_ARQUIVO, Context.MODE_PRIVATE)
            if (jsonArray == null){
                arquivo.write("".toByteArray())
                arquivo.close()
                return
            }

            val jsonArrayString = jsonArray.toString()
            arquivo.write(jsonArrayString.toByteArray())
            arquivo.close()
        }catch (e: Exception){
            e.printStackTrace()
            Toast.makeText(context, "Não foi poss", Toast.LENGTH_LONG).show()
        }
    }
}