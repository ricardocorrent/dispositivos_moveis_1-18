package com.rcorrent.gerenciadordemetas.Utils

import com.rcorrent.gerenciadordemetas.Model.Meta
import org.json.JSONObject
import java.text.SimpleDateFormat

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

    fun jsonParaMeta(json: JSONObject):Meta?{
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



}