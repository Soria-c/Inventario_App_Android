package com.example.invwhttp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.invwhttp.databinding.ActivityItemModifyBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ItemModifyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityItemModifyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemModifyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTitle("Modificar Item")

        val class_name = intent.getStringExtra("CLASS_NAME2")
        val item_name = intent.getStringExtra("ITEM_NAME2")
        binding.btnItemModSend.setOnClickListener {
            var price: Float? = null
            var stock: Int? = null
            var sell_price: Float? = null
            if (binding.etItemModPrice.text.isNotEmpty()) {
                price = binding.etItemModPrice.text.toString().toFloat()
            }
            if (binding.etItemModStock.text.isNotEmpty()) {
                stock = binding.etItemModStock.text.toString().toInt()
            }
            if (binding.etItemModSell.text.isNotEmpty()) {
                sell_price = binding.etItemModSell.text.toString().toFloat()
            }
            modifyItem(class_name, item_name, price, stock, sell_price)
        }

    }

    private fun getRetrofit(): Retrofit {

        return Retrofit.Builder()
            .baseUrl("http://192.168.1.14:5000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun modifyItem(
        className: String?,
        item_name: String?,
        price: Float?,
        stock: Int?,
        sell_price: Float?
    ) {

        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(APIService::class.java).modifiyItem(
                "products/${className}/${item_name}",
                ItemInfo(price, stock, sell_price)
            )
            val data = call.body()
            runOnUiThread {
                if (call.isSuccessful) {
                    val data2 = data as CreateItemResponse
                    success(data2.message)

                } else {
                    erroradd()
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

    private fun erroradd() {
        Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
    }

    private fun success(item_name: String) {
        Toast.makeText(this, item_name, Toast.LENGTH_SHORT).show()
    }

}