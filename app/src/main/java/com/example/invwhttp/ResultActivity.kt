package com.example.invwhttp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.invwhttp.databinding.ActivityResultBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.Serializable

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    private lateinit var adapter: ProductsAdapter
    //private val product = ClaseResponse()
    //private val itemz = mutableListOf<ItemsResponse>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTitle("")
        val className: String? = intent.getStringExtra("CLASS_NAME")
        binding.btnAdd.setOnClickListener {
            val intent2 = Intent(this, AddItemActivity::class.java)
            intent2.putExtra("class_name2", className)
            startActivity(intent2)
        }
        binding.srRefresh2.setOnRefreshListener {
            searchByName("", className)
        }


        //initRecyclerView(items)

    }

    override fun onResume() {
        val className: String? = intent.getStringExtra("CLASS_NAME")
        searchByName("", className)
        super.onResume()
    }

    private fun getRetrofit(): Retrofit {

        return Retrofit.Builder()
            .baseUrl("http://192.168.1.14:5000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun searchByName(query: String, className: String?) {

        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(APIService::class.java)
                .getProductByName("products/${className}")
            val data: ClaseResponse? = call.body()
            if (binding.srRefresh2.isRefreshing) {
                binding.srRefresh2.isRefreshing = false
            }
            runOnUiThread {
                if (call.isSuccessful) {
                    val its = data as ClaseResponse

                    //adapter.notifyDataSetChanged()
                    initRecyclerView(its)
                    //intent.putExtra("PRODUCTS", its as Serializable)
                    //startActivity(intent)
//
                } else {
                    //showError()
                }
                //binding.etInputText.text = null
                hideKeyboard()
            }

        }

    }

    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.viewRoot2.windowToken, 0)
    }

    private fun deleteByName(query: String, itemName: String) {
        CoroutineScope(Dispatchers.IO).launch {
            getRetrofit().create(APIService::class.java)
                .deleteProduct("products/${query}/${itemName}")
            val call =
                getRetrofit().create(APIService::class.java).getProductByName("products/${query}")
            val data: ClaseResponse? = call.body()
            runOnUiThread {
                if (call.isSuccessful) {
                    val its = data as ClaseResponse
                    success(itemName)
                    //adapter.notifyDataSetChanged()
                    initRecyclerView(its)
                } else {
                    //showError()
                }
                //binding.etInputText.text = null
                //hideKeyboard()
            }

        }
    }

    private fun success(itemName: String) {
        Toast.makeText(this, "Se elimino el item ${itemName}", Toast.LENGTH_SHORT).show()
    }

    private fun modifyItem(
        className: String?,
        item_name: String?,
        price: Float?,
        stock: Int,
        sell_price: Float?
    ) {

        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(APIService::class.java).modifiyItem(
                "products/${className}/${item_name}",
                ItemInfo(price, stock, sell_price)
            )
            val call2 = getRetrofit().create(APIService::class.java)
                .getProductByName("products/${className}")
            val data = call.body()
            val data3: ClaseResponse? = call2.body()
            runOnUiThread {
                if (call.isSuccessful) {
                    val data2 = data as CreateItemResponse
                    val its = data3 as ClaseResponse
                    initRecyclerView(its)
                    success2(data2.message)

                } else {
                    erroradd2()
                }

                //val data: ClaseResponse? = call.body()
                //runOnUiThread {
                // if (call.isSuccessful){
                //    val its = data as ClaseResponse

                //adapter.notifyDataSetChanged()
                //initRecyclerView(its)
                //intent.putExtra("PRODUCTS", its as Serializable)
                //startActivity(intent)
//
                //  }else{
                //showError()
                //  }
                //binding.etInputText.text = null
                //hideKeyboard()
            }
        }

    }

    private fun erroradd2() {
        Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
    }

    private fun success2(item_name: String) {
        Toast.makeText(this, item_name, Toast.LENGTH_SHORT).show()
    }

    private fun initRecyclerView(items: ClaseResponse) {
        //binding.tvname.text = items.name
        setTitle(items.name)
        binding.tvPrice.text = "Precio: ${items.total_class_price}"
        binding.tvStock.text = "Stock: ${items.total_class_stock}"
        binding.tvSell.text = "Venta: ${items.total_class_sell_price}"
        binding.tvEarnings.text = "Ganancia: ${items.total_class_earnings}"

        //binding.viewRoot2 = items.name

        adapter = ProductsAdapter(items.items)
        adapter.setOnItemClickListener(object : ProductsAdapter.onItemClickListener {
            override fun onBtnModifyTap(position: Int) {
                val intent3 = Intent(this@ResultActivity, ItemModifyActivity::class.java)
                intent3.putExtra("CLASS_NAME2", items.name)
                intent3.putExtra("ITEM_NAME2", items.items[position].name)
                startActivity(intent3)
            }

            override fun onBtnDelTap(position: Int) {
                deleteByName(items.name, items.items[position].name)
            }

            override fun onBtnAddTap(position: Int, value: String) {
                val i = items.items[position]
                modifyItem(items.name, i.name, null, i.stock + value.toInt(), null)
            }

            override fun onBtnSubTap(position: Int, value: String) {
                val i = items.items[position]
                modifyItem(items.name, i.name, null, i.stock - value.toInt(), null)
            }
        })
        binding.rView.layoutManager = LinearLayoutManager(this)
        binding.rView.adapter = adapter
    }


}