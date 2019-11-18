package com.example.practappsql

import Adaptador
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_intem.*
import org.w3c.dom.Text
import java.io.IOException
import java.net.URL
import java.util.ArrayList
import javax.security.auth.login.LoginException


class MainActivity : AppCompatActivity() {


    private var titulo: TextView? = null
    private var prueba: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prueba?.setMovementMethod(ScrollingMovementMethod())
        setContentView(R.layout.activity_main)
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        val recyclerView: RecyclerView = findViewById(R.id.reciclercoches)
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)


    }


    fun consulta(view: View) {

        val coches = ArrayList<Coches>()
        try {
            val gson = Gson()


            val json = leerUrl("http://iesayala.ddns.net/andrestr93/php/json.php")

            val coche = gson.fromJson(json, ArrayCoches::class.java)
            for (item in coche.coches!!.iterator()) {

                coches.add(Coches(item.Marca, item.Modelo, item.Matricula, item.Image))


                val adaptador = Adaptador(coches)


                reciclercoches.adapter = adaptador
            }
        } catch (e: Exception) {
            Log.d("RESULTADO", "error")
            prueba?.text = "Error"
            Toast.makeText(this, "errror", Toast.LENGTH_SHORT).show()
        }


    }


    fun insertar(v: View) {

        if ((editmarca.length() > 0) and (editmatricula.length() > 0) and (editmodelo.length() > 0) and (editurl.length() > 0)) {
            val url ="http://iesayala.ddns.net/andrestr93/php/insertcoche.php/?Marca="+editmarca.text.toString()+"&Modelo="+editmodelo.text.toString()+"&Matricula="+editmatricula.text.toString()+"&Image="+editurl.text.toString()
            leerUrl(url)

            editmarca.setText("")
            editmodelo.setText("")
            editmatricula.setText("")
            editurl.setText("")
            

        }


    }

    /*

     fun insertar(v : View){
        if ((editTextMarca.length()>0) and (editTextModelo.length()>0) and (editTextPrecio.length()>0)  ) {
            val url =
            leerUrl(url)
        }
    }
     */


    private fun leerUrl(urlString: String): String {

        val response = try {
            URL(urlString)

                .openStream()
                .bufferedReader()
                .use { it.readText() }
        } catch (e: IOException) {
            "Error with ${e.message}."
        }

        return response
    }
}















