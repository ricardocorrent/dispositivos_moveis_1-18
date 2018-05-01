package com.rcorrent.ciclodevida

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        Log.e("Start:", "___ Startando ___")
    }

    override fun onResume() {
        super.onResume()
        Log.e("Resume:", "___ Resume ___")
    }

    override fun onPause() {
        super.onPause()
        Log.e("Pause:", "___ Pausando ___")
    }

    override fun onStop() {
        super.onStop()
        Log.e("Stop:", "___ Stopando ___")
    }

    override fun onRestart() {
        super.onRestart()
        Log.e("Restart:", "___ Restartando ___")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("Destroy:", "___ Destroindo ___")
    }
}
