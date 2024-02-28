package com.example.guesstheword.dependencies

import android.app.Application

class MyApplication : Application() {

    //contenedor de dependencias manuales.
    private lateinit var _appContainer : Appcontainer
    val appcontainer get() = _appContainer

    //Inicialización del container para que pueda recibir correctamente el contexto.
    override fun onCreate() {
        super.onCreate()
        _appContainer = Appcontainer(this)
    }
}