package com.rcorrent.gerenciadordemetas.Model

import java.util.*

class Meta {

    var nome: String? = null
    var descricao: String? = null
    var dataLimite: Date? = null
    var dataFinalizado: Date? = null
    var concluido: Boolean? = null

    constructor(nome: String?, descricao: String?, dataLimite: Date?, dataFinalizado: Date?, concluido: Boolean?) {
        this.nome = nome
        this.descricao = descricao
        this.dataLimite = dataLimite
        this.dataFinalizado = dataFinalizado
        this.concluido = concluido
    }

//    fun isVencida():Boolean {
//
//    }
}