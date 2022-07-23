package com.example.invwhttp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.invwhttp.databinding.ActivityResult2Binding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.Serializable
import java.lang.Exception

class activity_result_2 : AppCompatActivity() {

    private lateinit var binding: ActivityResult2Binding
    private lateinit var adapter: ListProductsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResult2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        setTitle("Productos")

        //val products: MutableList<ClaseResponse> =  intent.getSerializableExtra("PRODUCTS") as MutableList<ClaseResponse>
        //val intent = Intent(this, activity_result_2::class.java)
        binding.btnAdd.setOnClickListener { searchByName(binding.etInputText.text.toString()) }
        binding.srRefresh.setOnRefreshListener {
            searchByName("")
        }
        //initRecyclerView(products)

    }

    override fun onResume() {
        try {
            searchByName("")
        } catch (e: java.net.ProtocolException) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }

        super.onResume()
    }


    private fun getRetrofit(): Retrofit {

        return Retrofit.Builder()
            .baseUrl("http://192.168.1.14:5000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun searchByName(query: String) {

        CoroutineScope(Dispatchers.IO).launch {
            if (query.isNotEmpty()) {
                getRetrofit().create(APIService::class.java).createProduct("products/$query")
            }
            val call = getRetrofit().create(APIService::class.java).getProductsByName("products")
            val data: GlobalResponse? = call.body()
            if (binding.srRefresh.isRefreshing) {
                binding.srRefresh.isRefreshing = false
            }
            runOnUiThread {
                if (call.isSuccessful) {
                    val its = data
                    if (query.isNotEmpty()) {
                        success()
                    }
                    //adapter.notifyDataSetChanged()
                    initRecyclerView(its as GlobalResponse)
                    //intent.putExtra("PRODUCTS", its as Serializable)
                    //startActivity(intent)

//
                } else {
                    showError()
                }
                binding.etInputText.text = null
                hideKeyboard()
            }

        }

    }


    private fun deleteByName(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            getRetrofit().create(APIService::class.java).deleteProduct("products/${query}")
            val call = getRetrofit().create(APIService::class.java).getProductsByName("products")
            val data: GlobalResponse? = call.body()
            runOnUiThread {
                if (call.isSuccessful) {
                    val its = data
                    success2()
                    adapter.notifyDataSetChanged()
                    initRecyclerView(its as GlobalResponse)
                    //intent.putExtra("PRODUCTS", its as Serializable)
                    //startActivity(intent)
//
                } else {
                    showError()
                }
                binding.etInputText.text = null
                hideKeyboard()
            }

        }
    }

    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.viewRoot.windowToken, 0)
    }

    private fun success() {
        Toast.makeText(this, "Se agrego producto", Toast.LENGTH_SHORT).show()
    }

    private fun success2() {
        Toast.makeText(this, "Se elimino producto junto con sus items", Toast.LENGTH_SHORT).show()
    }

    private fun showError() {
        Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
    }

    private fun initRecyclerView(productz: GlobalResponse) {
        binding.tvGlobalEarnings.text = "Ganancia: ${productz.global_earnings.toString()}"
        binding.tvGlobalPrice.text = "Precio: ${productz.global_price.toString()}"
        binding.tvGlobalSell.text = "Venta: ${productz.global_sell_price.toString()}"
        binding.tvGlobalStock.text = "Stock: ${productz.global_stock.toString()}"
        adapter = ListProductsAdapter(productz)
        adapter.setOnItemClickListener(object : ListProductsAdapter.onItemClickListener {
            override fun onItemClickView(position: Int) {
                val intent2: Intent = Intent(this@activity_result_2, ResultActivity::class.java)


                intent2.putExtra("CLASS_NAME", productz.Products[position].name)
                startActivity(intent2)
            }

            override fun onBtnDelClick(position: Int) {
                deleteByName(productz.Products[position].name)
            }

        })
        binding.rView2.layoutManager = LinearLayoutManager(this)
        binding.rView2.adapter = adapter


    }


}