package com.rcorrent.gerenciadordemetas.Utils

import java.text.SimpleDateFormat
import java.util.*

class DataUtils {

    val formatoData = SimpleDateFormat("dd/MM/yyyy")

    fun stringToDate(data: String): Date{
        val dataAuxiliar = formatoData.parse(data)
        return dataAuxiliar
    }

    fun dateToString(data: Date): String{
        val dataAuxiliar = formatoData.format(data)
        return dataAuxiliar.toString()
    }

}