package com.example.invwhttp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.invwhttp.databinding.ActivityAddItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AddItemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddItemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTitle("Crear Item")
        val class_name: String? = intent.getStringExtra("class_name2")

        binding.btnItemSend.setOnClickListener {
            val item_name = binding.etItemAddName.text.toString()
            val price = binding.etItemAddPrice.text.toString()
            val stock = binding.etItemAddStock.text.toString()
            val sell_price = binding.etItemAddSell.text.toString()

            if (item_name.isNotEmpty() && price.isNotEmpty() && stock.isNotEmpty() && sell_price.isNotEmpty()) {
                createItem(
                    class_name,
                    item_name,
                    price.toFloat(),
                    stock.toInt(),
                    sell_price.toFloat()
                )
            } else {
                Toast.makeText(this, "Llenar todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

    }


    private fun getRetrofit(): Retrofit {

        return Retrofit.Builder()
            .baseUrl("http://192.168.1.14:5000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun createItem(
        className: String?,
        item_name: String,
        price: Float,
        stock: Int,
        sell_price: Float
    ) {

        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(APIService::class.java).createItem(
                "products/${className}/${item_name}",
                ItemInfo(price, stock, sell_price)
            )
            val data = call.body()
            runOnUiThread {
                if (call.isSuccessful) {
                    val data2 = data as CreateItemResponse
                    success(data2.message)
                    binding.etItemAddName.text = null
                    binding.etItemAddStock.text = null
                    binding.etItemAddPrice.text = null
                    binding.etItemAddSell.text = null
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
    //}


}