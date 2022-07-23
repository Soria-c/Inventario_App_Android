package com.example.invwhttp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.invwhttp.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.Serializable

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTitle("Inventario")
        binding.btnEnviar.setOnClickListener {

            val intent = Intent(this, activity_result_2::class.java)
            startActivity(intent)
            //searchByName("products", intent)}


        }


        //  private fun getRetrofit(): Retrofit{

        //     return Retrofit.Builder()
        //         .baseUrl("http://192.168.1.14:5000/")
        //         .addConverterFactory(GsonConverterFactory.create())
        //         .build()
        // }

        //  private fun searchByName(query:String, intent: Intent){

        //      CoroutineScope(Dispatchers.IO).launch {
        //           val call = getRetrofit().create(APIService::class.java).getProductsByName("$query")
        //           val data: List<ClaseResponse>? = call.body()
        //          runOnUiThread {
        //             if (call.isSuccessful){
        //               val its = data
        //                intent.putExtra("PRODUCTS", its as Serializable)
        //               startActivity(intent)

        //           }else{
        //              showError()
        //         }
        //      }

        //    }

        //}

        // private fun showError() {
        //   Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
        // }


        // private fun checkInput() {
        //if (binding.etText.text.toString().isNotEmpty()){
        //  val intent = Intent(this, ResultActivity::class.java)
        //  searchByName(binding.etText.text.toString(), intent)

        //val itemlist = searchByName(binding.etText.text.toString())


        //    } else {
        //      Toast.makeText(this, "Campo vacio", Toast.LENGTH_SHORT).show()
        //   }
        //   }
    }
}
