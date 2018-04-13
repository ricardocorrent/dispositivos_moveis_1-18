package com.rcorrent.minhasmetas.Utils

import java.text.SimpleDateFormat
import com.rcorrent.minhasmetas.Models.Meta
import org.json.JSONArray
import org.json.JSONObject

class JsonUtils{

    val sdf = SimpleDateFormat("dd/MM/yyyy")

    fun metaParaJson(meta: Meta): JSONObject?{
        var jsonMeta = JSONObject()
        try {
            jsonMeta.put("nome", meta.nome)
            jsonMeta.put("descricao", meta.descricao)
            jsonMeta.put("dataLimite", sdf.parse(meta.dataLimite.toString()))
            if(meta.dataFinalizado == null){
                jsonMeta.put("dataFinalizado", "")
            }else{
                jsonMeta.put("dataFinalizado", sdf.parse(meta.dataFinalizado.toString()))
            }
            jsonMeta.put("concluido", meta.concluido)
            return jsonMeta
        }catch (e: Exception) {
            return null
        }
    }

    fun jsonParaMeta(json: JSONObject): Meta?{
        try {
            var dataConclusao: String? = null
            if(json.getBoolean("concluido")){
                dataConclusao = json.getString("dataFinalizado")
            }
            var meta = Meta(json.getString("nome"),
                    json.getString("descricao"),
                    sdf.parse(json.getString("dataFinalizado")),
                    sdf.parse(dataConclusao),
                    json.getBoolean("concluido"))
            return meta
        }catch (e: Exception) {
            return null
        }
    }

    fun arrayMetaParaJsonArray(arrayMeta: ArrayList<Meta>): JSONArray?{
        try {
            var jsonArray = JSONArray()

            for (i in arrayMeta){
                var metaJson = JSONObject()

                var metaConcluida = i.concluido

                metaJson.put("nome", i.nome)
                metaJson.put("descricao", i.descricao)
                metaJson.put("dataLimite", sdf.format(i.dataLimite))
                if(metaConcluida!!){
                    metaJson.put("dataFinalizado", sdf.format(i.dataFinalizado))
                } else {
                    metaJson.put("dataFinalizado", "")
                }
                metaJson.put("concluido", i.concluido)

                jsonArray.put(metaJson)
            }
            return jsonArray
        }catch (e: Exception){
            e.printStackTrace()
            return null
        }
    }

    fun jsonArrayParaArrayMeta(arrayJson: JSONArray): ArrayList<Meta>?{
        try {
            var listaDeMetas = ArrayList<Meta>()
            for (i in 0..(arrayJson.length() - 1)) {
                var metaJson = arrayJson.getJSONObject(i)
                var metaConcluida = metaJson.getBoolean("concluido")
                var dataFinalizado = if(metaConcluida) sdf.parse(metaJson.getString("dataFinalizado")) else null
                var meta = Meta (
                        metaJson.getString("nome"),
                        metaJson.getString("descricao"),
                        sdf.parse(metaJson.getString("dataLimite")),
                        dataFinalizado,
                        metaConcluida
                )
                listaDeMetas.add(meta)
            }
            return listaDeMetas
        }catch (e: Exception){
            e.printStackTrace()
            return null
        }
    }
}